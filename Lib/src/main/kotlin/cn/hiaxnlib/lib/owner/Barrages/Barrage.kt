package cn.hiaxnlib.lib.owner.Barrages

import cn.hiaxnlib.lib.particle.particleOwner.Owner
import cn.hiaxnlib.particle.HiaXnParticleStyle
import org.bukkit.Location
import org.bukkit.util.Vector
import java.util.UUID

abstract class Barrage(var location:Location, var barrageOwner:Owner, private var speed:Double, private var direction:Vector, var style:HiaXnParticleStyle, var g:Boolean, val uuid:UUID = UUID.randomUUID()):Owner {
    companion object{
        /**
         *重力加速度 /tick
         */
        const val g = 0.3
    }
    override fun getOwner(): Owner {
        return this
    }

    override fun getParticleLocation(): Location {
        return location
    }
    fun setDirection(vector: Vector){
        direction = vector.multiply(speed/vector.length())
    }
    fun getDirection():Vector{
        return direction
    }

    /**
     * 这里输入的speed的单位是秒
     * 而弹幕里用的是tick
     */
    fun setSpeed(speed: Double){
        //将秒的速度转换为tick
        this.speed = speed/20.0
    }
    fun getSpeed():Double{
        return speed
    }
    fun nextLocation(){
        location.apply {
            x += direction.x
            y += direction.y
            z += direction.z
        }
        if (g){
            direction.add(Vector(0.0,-Companion.g,0.0))
        }
    }

    /**
     * 更新粒子样式的状态
     */
    fun updateStyleTick(){
        style.updateParticleTick()
    }
    fun displayParticle(){
        style.display(location)
    }
    /**
     * 检测弹幕是否击中
     * 未击中任何请返回Null
     */
    abstract fun checkBarrageHit():Owner?

    /**
     * 击中做出的需要处理的事件
     */
    abstract fun hitMethod(owner:Owner)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Barrage
        return other.uuid == uuid
    }

    override fun hashCode(): Int {
        var result = location.hashCode()
        result = 31 * result + barrageOwner.hashCode()
        result = 31 * result + speed.hashCode()
        result = 31 * result + direction.hashCode()
        result = 31 * result + style.hashCode()
        result = 31 * result + g.hashCode()
        result = 31 * result + uuid.hashCode()
        return result
    }

}