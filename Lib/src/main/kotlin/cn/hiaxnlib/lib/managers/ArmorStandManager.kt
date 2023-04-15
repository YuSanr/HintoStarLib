package cn.hiaxnlib.lib.managers

import cn.hiaxnlib.lib.data.ArmorStandData
import cn.hiaxnlib.lib.data.EntityData
import cn.hiaxnlib.lib.entity.armorStand.ArmorStandNode
import cn.hiaxnlib.lib.entity.armorStand.actions.EntityAIAction
import cn.hiaxnlib.lib.entity.armorStand.actions.EntityChunkUnloadTickAction
import cn.hiaxnlib.lib.entity.armorStand.actions.EntityRandomAction
import cn.hiaxnlib.lib.entity.armorStand.actions.EntityTickAction
import cn.hiaxnlib.lib.entity.armorStand.annotations.ArmorStandNodeDataFile
import cn.hiaxnlib.lib.entity.armorStand.utils.ChunkUtil
import cn.hiaxnlib.lib.owner.OwnerEntity
import cn.hiaxnlib.lib.version.VersionUtil

import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import java.util.Stack
import java.util.UUID
import kotlin.math.max
import kotlin.random.Random

/**
 * 组合盔甲架的管理类
 */
class ArmorStandManager {
    /**
     * 方便获取的
     */
    private var groups = HashMap<UUID,ArmorStandNode>()

    /**
     * key EntityUUID value Parent Node Entity UUID & root Node Entity UUID
     */
    private var nodes = HashMap<UUID,UUID>()
    private var random = Random(11451419198102256)
    private var tick = 0
    private var randomTick = max(10,random.nextInt(80))
    private var entityData = EntityData()
    var spawnEntityQueue = ArrayList<ArmorStandNode>()
    fun getStandUUID(node:ArmorStandNode):UUID{
        val root = node.getRoot()
        return root.armorStand!!.uniqueId
    }
    fun getArmorStandGroupsMap():HashMap<UUID,ArmorStandNode>{
        return groups
    }
    fun getArmorStandRootUUIDMap():HashMap<UUID,UUID>{
        return nodes
    }

    /**
     * 通过RootID获取的Root盔甲架对象
     * 方便事件使用的
     */
    fun getStandGroup(uuid:UUID):ArmorStandNode?{
        val rootID = nodes[uuid]?:let{
            return null
        }
        return groups[rootID]
    }
    /**
     * 通过EntityUUID 获取的对应的盔甲架对象节点的父节点
     * 方便事件使用的
     */
    fun getParentNode(uuid:UUID):ArmorStandNode?{
        val root = getStandGroup(uuid)?:return null
        for (subNode in root.getAllNodesBuffered()) {
            if (uuid == subNode.entityUUID!!){
                return subNode
            }
        }
        return null
    }
    fun getNodeFromEntityUUID(uuid: UUID):ArmorStandNode?{
        val root = getStandGroup(uuid)?:return null
        for (body in root.getBodies()) {
            if (body.key.entityUUID == uuid) {
                return body.key
            }
        }
        return null
    }

    /**
     * 卸载所有实体
     * 在disable时使用
     */
    fun unloadAllEntity(){
        saveEntityData()
        for (group in groups) {
            group.value.entityUnload()
        }
    }
    /**
     * 保存实体数据
     */
    fun saveEntityData(){
        val dataArrays = ArrayList<ArmorStandNode>()
        for (node in groups) {
            val cls = node.value::class.java
            if(cls.isAnnotationPresent(ArmorStandNodeDataFile::class.java)){
                dataArrays.add(node.value)
            }
        }
        val armorDataList = ArrayList<ArmorStandData>().also {
            for (dataArray in dataArrays) {
                it.add(ArmorStandData.toData(dataArray))
            }
        }
        entityData.saveData(armorDataList)
    }
    fun loadEntity(){
        for (data in entityData.getData()) {
            val root = data.toNode()
            spawnEntityQueue.add(root)
        }
    }
    fun spawnEntity(node: ArmorStandNode, local: Location){
        respawnEntity(node,local)
        node.spawnAction()
    }
    fun respawnEntity(node:ArmorStandNode,local: Location){
        node.armorStand = local.world.spawnEntity(local.clone(),EntityType.ARMOR_STAND) as ArmorStand
        node.armorStand!!.removeWhenFarAway = false
        node.location = node.armorStand!!.location
        node.setEntityStyle(node.armorStand!!)
        node.isSpawned = true
        node.entityUUID = node.armorStand!!.uniqueId
        groups[node.entityUUID!!] = node
        nodes[node.entityUUID!!] = node.entityUUID!!
        respawnBody(node)
    }

    /**
     * 根据Root生成身体
     */
    fun respawnBody(node:ArmorStandNode){
        val stack = Stack<ArmorStandNode>()
        stack.push(node)
        while (stack.isNotEmpty()){
            val root = stack.pop()
            for (subNode in root.getBodies()) {
                subNode.key.armorStand = root.location.world.spawnEntity(subNode.key.parentNode!!.location.clone().add(subNode.value.toVector()),EntityType.ARMOR_STAND) as ArmorStand
                subNode.key.location = subNode.key.armorStand!!.location
                subNode.key.armorStand!!.removeWhenFarAway = false
                subNode.key.setEntityStyle(subNode.key.armorStand!!)
                subNode.key.entityUUID = subNode.key.armorStand!!.uniqueId
                subNode.key.isSpawned = true
                nodes[subNode.key.entityUUID!!] = node.getUUID()
                stack.push(subNode.key)
            }
        }
        node.teleportToRealLocation()
    }
    fun removeUnlinkedEntity(node: ArmorStandNode){
        for (armorStandNode in node.getAllNodesBuffered()) {
            val entity = ArmorStandNode.getEntity(armorStandNode.getUUID(),armorStandNode.location.world)?:continue
            entity.remove()
            nodes.remove(entity.uniqueId)
        }
        groups.remove(node.getUUID())
    }
    fun standTask() {
        Bukkit.getServer().scheduler.runTaskTimer(VersionUtil.getHiaXnLibPlugin(),
            Runnable {
                var deletedGroup: ArmorStandNode? = null
                for (group in HashMap<UUID,ArmorStandNode>(groups)) {
                    if (!group.value.isSpawned) continue
                    if (group.value.dead || group.value.armorStand!!.isDead) {
                        deletedGroup = group.value
                        continue
                    }
                    // 实体失效但是实体所在的区块加载
                    if ((group.value.unload && ChunkUtil.isLoad(group.value.location))){
                        group.value.unload = false
                        group.value.damageTick = 8
                        removeUnlinkedEntity(group.value)
                        respawnEntity(group.value,group.value.location)
                    }
                    if (ChunkUtil.isLoad(group.value.location) && (group.value.armorStand!!.isValid)){
                        group.value.damageTick = max(0, group.value.damageTick - 1)
                        group.value.teleportToRealLocation()
                        group.value.nextLocation()
                        if (group.value is EntityTickAction) {
                            (group.value as EntityTickAction).tickAction()
                        }
                        val seeBox = group.value.seeBox
                        if (group.value is EntityAIAction) {
                            for (entity in group.value.armorStand!!.getNearbyEntities(seeBox.x, seeBox.y, seeBox.z)) {
                                if (entity is LivingEntity) {
                                    val ai = group.value as EntityAIAction
                                    if (seeBox.ball) {
                                        if (entity.location.distance(group.value.armorStand!!.location) <= seeBox.r) {
                                            ai.aiAction(OwnerEntity(entity))
                                        }
                                    } else {
                                        ai.aiAction(OwnerEntity(entity))
                                    }
                                }
                            }
                        }
                        if (tick >= randomTick) {
                            randomTick = max(10, random.nextInt(160))
                            tick = 0
                            if (group.value is EntityRandomAction) {
                                (group.value as EntityRandomAction).randomTick()
                            }
                        }
                        tick++
                    }else if (group.value is EntityChunkUnloadTickAction) {
                        group.value.unload = true
                        (group.value as EntityChunkUnloadTickAction).onUnloadTickAction()
                    }else{
                        group.value.unload = true
                    }
                }
                deletedGroup ?: return@Runnable
                groups.remove(getStandUUID(deletedGroup))
            }, 0, 1
        )
    }

}