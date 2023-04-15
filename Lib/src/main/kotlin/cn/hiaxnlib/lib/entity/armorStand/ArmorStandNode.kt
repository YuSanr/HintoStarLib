package cn.hiaxnlib.lib.entity.armorStand

import cn.hiaxnlib.lib.entity.armorStand.utils.ArmorStandSeeBox
import cn.hiaxnlib.lib.entity.armorStand.utils.ChunkUtil
import cn.hiaxnlib.lib.managers.ArmorStandManager
import cn.hiaxnlib.lib.owner.Owner
import cn.hiaxnlib.lib.utils.MathUtil
import cn.hiaxnlib.lib.version.VersionUtil
import cn.hiaxnlib.particle.RelativeLocation
import com.sun.org.apache.xpath.internal.operations.Bool
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.MetadataValue
import org.bukkit.util.Vector
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * @param standOwnerUUID 盔甲架的所属者的UUID
 * @param parentRelative 和父节点的相对位置
 * @param maxHealth 实体最大生命值(指适用与Root节点)
 * @param seeBox 实体AI可视范围 在可视范围以内的实体会被作为参数执行AIAction
 */

abstract class ArmorStandNode(
    var parentNode:ArmorStandNode?,
    var standOwnerUUID: UUID?,
    var seeBox: ArmorStandSeeBox,
    /**
     * 与Root 或者 父节点的相对位置
     */
    val parentRelative:RelativeLocation,
    /**
     * 最大血量
     * 设定为 -1时无敌(判定<0)
     */
    val maxHealth:Double
): Owner {
    var entityUUID:UUID? = null // = null as Spawned = false
    private var health = maxHealth
    var location = Location(null,0.0,0.0,0.0)
    var armorStand:ArmorStand? = null
    private val subNodes = HashMap<ArmorStandNode,RelativeLocation>()
    var isSpawned = false
    var dead = false
    val maxDamageTick = 8
    var damageTick = maxDamageTick
    // 判定所在的区块是否存在过卸载 并且没有被重新加载
    var unload = false
    // 默认情况下 盔甲架不会移动
    private var direction = Vector()
    private var speed = 0.0
    companion object{
        @JvmStatic
        fun getEntity(uuid:UUID,world: World):Entity?{
            for (entity in world.entities) {
                if (entity.uniqueId == uuid){
                    return entity
                }
            }
            return null
        }
    }
    /**
     * 缓存的AllNodes
     * 缓解获取压力
     */
    private var bufferedAllNode = ArrayList<ArmorStandNode>()
    val entityUtil =  VersionUtil.getEntityUtil()
    /**
     * 用于JSON反序列化 序列化 的一个空的构造函数
     */
    constructor():this(null,null,ArmorStandSeeBox(32.0), RelativeLocation(),20.0)
    fun damage(damage:Double,from:ArmorStandNode?){
        if (!isSpawned) return
        if (getRoot().damageTick >0) return
        if (maxHealth <0) return
        if (isRoot()){
            health -= damage
            damageTick = maxDamageTick
            damageAction(damage,null,from)
            if (health<=0){
                health = 0.0
                entityDeath()
            }
        }else{
            val root = getRoot()
            root.health -= damage
            root.damageTick = maxDamageTick
            damageAction(damage,null,from)
            if (root.health<=0){
                root.health = 0.0
                root.entityDeath()
            }
        }
    }
    final fun damage(damage:Double,entity: Entity,from:ArmorStandNode?){
        if (!isSpawned) return
        if (getRoot().damageTick >0) return
        if (maxHealth <0) return
        if (isRoot()){
            health -= damage
            damageTick = maxDamageTick
            damageAction(damage,entity,from)
            if (health<=0){
                health = 0.0
                entityDeath()
            }
        }else{
            val root = getRoot()
            root.health -= damage
            root.damageTick = maxDamageTick
            damageAction(damage,entity,from)
            if (root.health<=0){
                root.health = 0.0
                root.entityDeath(entity)
            }
        }
    }

    /**
     * 卸载实体
     * (采用Remove 请在关服的时候使用 否则会误判实体死亡)
     */
    final fun entityUnload(){
        for (allNode in getAllNodesBuffered()) {
            ChunkUtil.loadChunk(allNode.location,true)
            val entity = getEntity(allNode.getUUID(),allNode.location.world)?:continue
            entity.remove()
        }
    }
    final fun entityDeath(){
        armorStand?:return
        for (allNode in getAllNodes()) {
            allNode.armorStand!!.remove()
        }
        deathAction(null)
        dead = true
    }
    final fun entityDeath(killer:Entity){
        armorStand?:return
        for (allNode in getAllNodes()) {
            allNode.armorStand!!.remove()
        }
        deathAction(killer)
        dead = true
    }

    /**
     * 设置实体隐身
     */
    fun setEntityInvisible(b:Boolean){
        armorStand?:return
        armorStand!!.isVisible = b
    }
    /**
     * 转换为真实伤害的算法 自己写
     */
    abstract fun toRealDamage(damage: Double):Double

    /**
     * 对实体的一些基础参数进行设置
     * 并且在构造函数调用
     */
    abstract fun setEntityStyle(entity:ArmorStand)

    /**
     * 出生事件
     * 这个实体被生成了执行这个函数
     */
    abstract fun spawnAction()


    /**
     * 盔甲架组合受伤的发生的事件
     */
    abstract fun damageAction(damage:Double,damager: Entity?,from:ArmorStandNode?)

    /**
     * 组合盔甲架死亡会执行的事件
     */
    abstract fun deathAction(killer:Entity?)


    /**
     * @param relativeLocation 是ArmorStandNode里面的参数 RelativeLocation
     */
    fun removeBody(relativeLocation: RelativeLocation):ArmorStandNode?{
        var removed:ArmorStandNode? = null
        for (subNode in subNodes) {
            if (subNode.key.parentRelative == relativeLocation) {
                removed = subNode.key
            }
        }
        subNodes.remove(removed)
        updateBufferedNodes()
        updateRelativeLocation()
        return removed
    }
    /**
     * @param relativeLocation 是ArmorStandNode里面的参数 RelativeLocation
     */
    fun getBody(relativeLocation: RelativeLocation):ArmorStandNode?{
        for (subNode in subNodes) {
            if (subNode.key.parentRelative == relativeLocation) {
                return subNode.key
            }
        }
        return null
    }

    /**
     * 判断这个盔甲架节点是否属于Root节点 （核心）
     */
    fun isRoot():Boolean{
        return parentNode == null
    }

    /**
     * 获取核心节点
     */
    fun getRoot():ArmorStandNode{
        /**
         * 二叉树果然没白学 草
         */
        if (isRoot()){
            return this
        }else{
            var node = this
            while(node.parentNode != null){
                node = node.parentNode!!
            }
            return node
        }
    }
    fun isLeave():Boolean{
        return subNodes.size == 0
    }
    fun getBodies():HashMap<ArmorStandNode,RelativeLocation>{
        return subNodes
    }
    fun setHealth(health:Double){
        this.health = health
        if(health<=0) {
            entityDeath()
        }
    }
    fun getHealth():Double{
        return this.health
    }


    /**
     * 只适用于这个node节点下main的子节点
     * @param angle 弧度制
     * @param axis 绕旋转的轴
     */

    fun rotateAsAxis(node:ArmorStandNode,axis: RelativeLocation,angle:Double){
        MathUtil.rotateAsAxis(ArrayList(node.subNodes.values),axis,angle)
    }

    /**
     * 适用于整个组合的绕轴旋转
     * @param node 递归用 默认为Root节点
     * @param angle 弧度制
     * @param axis 绕旋转的轴
     */
    fun rotateAllAsAxis(node:ArmorStandNode = getRoot(),axis: RelativeLocation,angle: Double){
        if (node.isLeave()) return
        MathUtil.rotateAsAxis(ArrayList(node.subNodes.values),axis,angle)
        for(subNode in node.subNodes){
            rotateAllAsAxis(subNode.key,axis,angle)
        }
    }
    /**
     * 将这个盔甲架组合的对称轴旋转至point点(只影响到当前节点和子节点)
     */
    fun rotateGroupTo(node:ArmorStandNode,point:Location,axis:RelativeLocation){
        val relToPoint = RelativeLocation.toRelativeLocation(node.location,point)
        MathUtil.rotatePointsToPoint(ArrayList(subNodes.values),relToPoint,axis)
    }

    /**
     * 关系到整个组合的旋转 (这里修改的都是相对位置)
     */
    fun rotateAllGroupTo(node: ArmorStandNode = getRoot(), point:Location, axis:RelativeLocation){
        val relToPoint = RelativeLocation.toRelativeLocation(node.getRoot().location,point)
        if (node.isLeave()){
            return
        }
        MathUtil.rotatePointsToPoint(ArrayList(node.subNodes.values),relToPoint,axis)
        for (subNode in node.subNodes) {
            rotateAllGroupTo(subNode.key,point,axis)
        }
    }

    /**
     * @param node 节点 用于递归 默认Root
     * 每Tick执行一次
     */
    fun teleportToRealLocation(node:ArmorStandNode = getRoot()){
        val stack = Stack<ArmorStandNode>()
        stack.push(node)
        node.armorStand!!.teleport(location.clone())
        while (stack.isNotEmpty()){
            val root = stack.pop()
            for (subNode in root.subNodes) {
                subNode.key.location = Location(node.location.world,node.location.x,node.location.y,node.location.z).also {it.add(subNode.value.toVector())}
                subNode.key.armorStand!!.teleport(subNode.key.location)
                stack.push(subNode.key)
            }
        }
    }
    /**
     *  使用迭代方式
     */
    fun getAllNodes(node: ArmorStandNode = getRoot()):ArrayList<ArmorStandNode>{
        val result = ArrayList<ArmorStandNode>()
        val stack = Stack<ArmorStandNode>()
        stack.push(node)
        while (stack.isNotEmpty()){
            val root = stack.pop()
            for (subNode in root.subNodes) {
                stack.push(subNode.key)
            }
            result.add(root)
        }
        return result
    }
    fun getAllNodesBuffered(node:ArmorStandNode = getRoot()):ArrayList<out ArmorStandNode>{
        if (node.bufferedAllNode.isEmpty()){
           node.bufferedAllNode = getAllNodes(node)
        }
        return node.bufferedAllNode
    }
    fun updateBufferedNodes(){
        bufferedAllNode = getAllNodes(this)
    }
    fun updateBufferedRootNodes(){
        getRoot().bufferedAllNode = getAllNodes()
    }
    fun updateRelativeLocation(){
        val stack = Stack<ArmorStandNode>()
        stack.push(getRoot())
        while (stack.isNotEmpty()){
            val root = stack.pop()
            val buffered = HashMap<ArmorStandNode,RelativeLocation>(root.subNodes)
            for (entry in buffered) {
                root.subNodes[entry.key] = entry.key.parentRelative
                stack.push(entry.key)
            }
        }
    }
    /**
     * 重新生成引用所有的实体 这个函数通常在实体引用失败时执行
     */
    fun updateAllEntity(){
        if (!isSpawned) return
        updateRelativeLocation()
        entityUnload()
        for (node in getRoot().bufferedAllNode) {
            node.armorStand = location.world.spawnEntity(location,EntityType.ARMOR_STAND) as ArmorStand
            node.entityUUID = node.armorStand!!.uniqueId
            node.setEntityStyle(node.armorStand!!)
        }
        relinkAllEntity()
    }

    /**
     * 重新引用失去引用的实体
     * 实体所在的区块被卸载时
     * 实体会无法操作
     * 由于实体仍然存在于世界 并且UUID没有发生改变
     * 于是使用UUID重新获取实体
     */
    fun relinkAllEntity():Boolean{
        if (!isSpawned) return true
        if (dead) return true
        updateRelativeLocation()
        updateBufferedRootNodes()
        // 对所有的实体进行重新引用
        val manager = VersionUtil.getArmorStandManager()
        for (armorStandNode in getRoot().getAllNodesBuffered()) {
            if (!ChunkUtil.isLoad(armorStandNode.armorStand!!.location)){
                return false
            }
            var link:Entity? =  getEntity(armorStandNode.entityUUID!!,armorStandNode.location.world)
            if (link == null){
                val bufferuuids = HashMap<UUID,UUID>(VersionUtil.getArmorStandManager().getArmorStandRootUUIDMap())
                ChunkUtil.loadChunk(armorStandNode.location,true)
                link = armorStandNode.location.world.spawnEntity(armorStandNode.location,EntityType.ARMOR_STAND) as ArmorStand
                link.removeWhenFarAway = false
                armorStandNode.setEntityStyle(link)
                val newUUID = link.uniqueId
                if (armorStandNode.isRoot()){
                    for (uuid in bufferuuids) {
                        if (uuid.value==armorStandNode.entityUUID ){
                            manager.getArmorStandRootUUIDMap()[uuid.key] = newUUID
                        }
                    }
                    manager.getArmorStandGroupsMap().remove(armorStandNode.entityUUID)
                    manager.getArmorStandGroupsMap()[newUUID] = armorStandNode
                }else{
                   val rootID =  armorStandNode.getRoot().entityUUID!!
                    manager.getArmorStandRootUUIDMap().remove(armorStandNode.entityUUID)
                    manager.getArmorStandRootUUIDMap()[newUUID] = rootID
                }
                armorStandNode.entityUUID = newUUID
            }
            armorStandNode.armorStand = link as ArmorStand
        }
        return true
    }

    /**
     * 获取所有节点
     * 判定这些节点所在的区块是否存在未加载的区块
     * @return 不存在返回true
     */
    fun getAllNodesChunkLoad():Boolean{
        for (node in getAllNodesBuffered()) {
            if (!ChunkUtil.isLoad(node.location)){
                return false
            }
        }
        return true
    }
    fun setHeadItem(itemStack: ItemStack){
        armorStand?:return
        armorStand!!.equipment.helmet = itemStack
    }
    fun setBodyItem(itemStack: ItemStack){
        armorStand?:return
        armorStand!!.equipment.chestplate = itemStack
    }
    fun setLegItem(itemStack: ItemStack){
        armorStand?:return
        armorStand!!.equipment.leggings = itemStack

    }
    fun setBootsItem(itemStack: ItemStack){
        armorStand?:return
        armorStand!!.equipment.boots = itemStack
    }
    fun getHeadItem():ItemStack?{
        armorStand?:return null
        return armorStand!!.equipment.helmet
    }
    fun getBodyItem():ItemStack?{
        armorStand?:return null
        return armorStand!!.equipment.chestplate
    }
    fun getLegItem():ItemStack?{
        armorStand?:return null
        return armorStand!!.equipment.leggings
    }
    fun getBootsItem():ItemStack?{
        armorStand?:return null
        return armorStand!!.equipment.boots
    }
    fun getSpeed():Double{
        return this.speed
    }
    fun getDirection():Vector{
        return direction
    }

    final fun setSpeed(speed:Double,direction:Vector){
        if (speed>20.0){
            this.speed = 20.0
        }else{
            this.speed = speed
        }
        this.direction = direction.multiply(this.speed/direction.length())
    }
    final fun setSpeed(speed:Double){
        if (speed>20.0){
            this.speed = 20.0
        }else{
            this.speed = speed
        }
        this.direction = direction.multiply(this.speed/direction.length())
    }
    final fun setDirection(direction: Vector){
        this.direction = direction.multiply(this.speed/direction.length())
    }
    final fun nextLocation(){
        if (speed >0 && this.direction.length()>0){
            this.location.add(direction)
        }
    }

    override fun getOwnerLocation(): Location {
        return location
    }

    override fun getUUID(): UUID {
        return armorStand!!.uniqueId
    }

    override fun getOwner(): Owner {
        return this
    }
}