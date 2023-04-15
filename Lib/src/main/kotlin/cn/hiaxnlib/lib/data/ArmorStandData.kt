package cn.hiaxnlib.lib.data

import cn.hiaxnlib.lib.entity.armorStand.ArmorStandNode
import cn.hiaxnlib.lib.entity.armorStand.annotations.ArmorStandField
import cn.hiaxnlib.lib.entity.armorStand.annotations.ArmorStandNodeDataFile
import cn.hiaxnlib.lib.entity.armorStand.utils.ArmorStandSeeBox
import cn.hiaxnlib.lib.exception.ArmorStandNotDataException
import org.bukkit.Location
import org.bukkit.util.Vector
import java.util.UUID

class ArmorStandData(
    var ownerUUID:UUID?,
    var health:Double,
    var dead:Boolean,
    //
    @Transient var location:Location,
    var seeBox:ArmorStandSeeBox,
    var speed:Double,
    var direction:Vector,
    /**
     * 方便反序列化
     */
    var classPath:String
){
    var locationSerialize = HashMap<String,Any?>()
    var otherFields = HashMap<String,Any?>()
    constructor():this(null,0.0,false,Location(null,0.0,0.0,0.0), ArmorStandSeeBox(),0.0,Vector(),"")
    companion object{
        fun toData(node:ArmorStandNode):ArmorStandData{
            if (!node::class.java.isAnnotationPresent(ArmorStandNodeDataFile::class.java)){
                throw ArmorStandNotDataException()
            }
            val data = ArmorStandData()
            val path = node::class.java.name
            data.classPath = path
            data.ownerUUID = node.standOwnerUUID
            data.dead = node.dead
            data.location = node.location
            data.seeBox = node.seeBox
            data.health = node.getHealth()
            data.speed = node.getSpeed()
            data.direction = node.getDirection()
            for (field in node::class.java.declaredFields) {
                if (field.isAnnotationPresent(ArmorStandField::class.java)){
                    field.isAccessible = true
                    data.otherFields[field.name] = field.get(node)
                }
            }
            data.locationSerialize = data.location.serialize() as HashMap<String, Any?>
            return data
        }
    }
    fun load(node:ArmorStandNode){
        if (!node::class.java.isAnnotationPresent(ArmorStandNodeDataFile::class.java)){
            throw ArmorStandNotDataException()
        }
        val path = node::class.java.name
        this.classPath = path
        this.ownerUUID = node.standOwnerUUID
        this.dead = node.dead
        this.location = node.location
        this.seeBox = node.seeBox
        this.health = node.getHealth()
        this.locationSerialize = this.location.serialize() as HashMap<String, Any?>
        this.speed = node.getSpeed()
        this.direction = node.getDirection()
        for (field in node::class.java.declaredFields) {
            if (field.isAnnotationPresent(ArmorStandField::class.java)){
                field.isAccessible = true
                this.otherFields[field.name] = field.get(node)
            }
        }
    }
    fun toNode():ArmorStandNode{
        val cls = Class.forName(classPath)
        val node = cls.getConstructor().newInstance() as ArmorStandNode
        node.isSpawned = false
        node.location = location
        node.setHealth(health)
        node.dead = dead
        node.standOwnerUUID = ownerUUID
        node.seeBox = seeBox
        node.location = Location.deserialize(this.locationSerialize)
        node.setSpeed(node.getSpeed(),node.getDirection())
        for (otherField in otherFields) {
            cls.getDeclaredField(otherField.key).also {
                it.isAccessible = true
                if(it.type.toString() == "int"){
                    //别问 问就是逆天GSON转INT的时候给我变成了Double
                    it.set(node,otherField.value.toString().toDouble().toInt())
                }else if (it.type.toString() == "long"){
                    it.set(node,otherField.value.toString().toDouble().toLong())
                }else if (it.type.toString() == "short"){
                    it.set(node, otherField.value.toString().toDouble().toInt().toShort())
                }else if (it.type.toString() == "byte"){
                    it.set(node, otherField.value.toString().toDouble().toInt().toByte())
                }else{
                    it.set(node,otherField.value)
                }
            }
        }
        return node
    }
}