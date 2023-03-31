package cn.hiaxnlib.particle

import kotlin.math.pow
import kotlin.math.sqrt


// 描述粒子之间的相对位置的类
data class RelativeLocation(var x:Double,var y:Double,var z:Double){
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
    fun dot(other: RelativeLocation): Double {
        return x * other.x + y * other.y + z * other.z
    }
    fun times(scalar: Double): RelativeLocation {
        return RelativeLocation(x * scalar, y * scalar, z * scalar)
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