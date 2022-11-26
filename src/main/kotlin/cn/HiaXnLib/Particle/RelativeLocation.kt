package cn.HiaXnLib.Particle


// 描述粒子之间的相对位置的类
class RelativeLocation(var x:Double,var y:Double,var z:Double){
    override fun toString(): String {
        return "RelativeLocation(x=$x, y=$y, z=$z)"
    }
    fun clone(): RelativeLocation {
        return RelativeLocation(x,y,z)
    }
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