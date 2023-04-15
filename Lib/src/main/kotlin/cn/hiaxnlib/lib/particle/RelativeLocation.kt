package cn.hiaxnlib.particle

import org.bukkit.Location
import org.bukkit.util.Vector
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * 描述粒子之间相对位置的类
 * 相对位置 又名向量
 * 草
 */
data class RelativeLocation(var x:Double,var y:Double,var z:Double){
    companion object{

        fun toRelativeLocation(start:Location,end:Location):RelativeLocation{
            return RelativeLocation(end.x - start.x, end.y-start.y, end.z-start.z)
        }
        fun toRelativeLocation(vector: Vector):RelativeLocation{
            return RelativeLocation(vector.x,vector.y,vector.z)
        }
        fun toVector(relativeLocation: RelativeLocation):Vector{
            return Vector(relativeLocation.x,relativeLocation.y,relativeLocation.z)
        }
    }
    constructor():this(0.0,0.0,0.0)
    constructor(x:Int,y:Int,z:Int):this(x.toDouble(),y.toDouble(),z.toDouble())
    constructor(x:Float,y:Float,z:Float):this(x.toDouble(),y.toDouble(),z.toDouble())
    constructor(x:Long,y:Long,z:Long):this(x.toDouble(),y.toDouble(),z.toDouble())
    operator fun minus(other: RelativeLocation): RelativeLocation {
        return RelativeLocation(x - other.x, y - other.y, z - other.z)
    }

    // 添加归一化函数
    fun normalize(): RelativeLocation {
        val length = Math.sqrt(x * x + y * y + z * z)
        return RelativeLocation(x / length, y / length, z / length)
    }
    override fun toString(): String {
        return "RelativeLocation(x=$x, y=$y, z=$z)"
    }
    fun clone(): RelativeLocation {
        return RelativeLocation(x,y,z)
    }

    /**
     * 向量点乘
     */
    fun dot(other: RelativeLocation): Double {
        return x * other.x + y * other.y + z * other.z
    }
    operator fun times(scalar: Double): RelativeLocation {
        return RelativeLocation(x * scalar, y * scalar, z * scalar)
    }
    operator fun plus(other: RelativeLocation): RelativeLocation {
        return RelativeLocation(x + other.x, y + other.y, z + other.z)
    }
    fun toVector():Vector{
        return Vector(x,y,z)
    }
    /**
     * 向量叉乘
     */
    fun cross(vector:RelativeLocation):RelativeLocation{
        return RelativeLocation(
            y*vector.z-z*vector.y,
            z*vector.x - x*vector.z,
            x*vector.y - y*vector.x
        )
    }
    fun length() = sqrt(x.pow(2) + y.pow(2) + z.pow(2))
    fun distance(relativeLocation: RelativeLocation) =
        sqrt((x-relativeLocation.x).pow(2) + (y-relativeLocation.y).pow(2) + (z - relativeLocation.z).pow(2))
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RelativeLocation

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

}