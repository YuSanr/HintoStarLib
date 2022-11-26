package cn.HiaXnLib.Particle.util

import org.bukkit.Location
import org.bukkit.util.Vector

object ParticleMathUtil {
    /**
     * 通过2个坐标制作向量来表达直线
     * @param pointNumber 用于表示这个直线的粒子个数
     */
    fun getLineLocation(startLocation:Location,endLocation:Location,pointNumber:Int = 200):ArrayList<Location>{
        val list = ArrayList<Location>()
        // 取直线向量
        val lineVector = Vector(endLocation.x - startLocation.x,endLocation.y - startLocation.y,endLocation.z - startLocation.z)
        val addVector = lineVector.clone().multiply(1/pointNumber)
        list.add(startLocation.clone())
        for (i in 1 until pointNumber){
            val clone = startLocation.clone()
            clone.x += addVector.x
            clone.y += addVector.y
            clone.z += addVector.z
            list.clone()
        }
        return list
    }

    /**
     * 根据圆的参数方程 得出XZ轴上的圆的表达式 -> List
     * @param pointNumber 用于表示这个圆的粒子个数
     */
    fun getXZCircleLocation(heartLocal:Location,radius:Double,pointNumber: Int):ArrayList<Location>{
        val list = ArrayList<Location>()
        val addRad = 360.0/pointNumber
        var nowRad = 0.0
        for (i in 1 .. pointNumber){
            val rad = Math.toRadians(nowRad)
            nowRad+=addRad
            val sin = Math.sin(rad)
            val cos = Math.cos(rad)
            val clone = heartLocal.clone()
            clone.z += sin*radius
            clone.x += cos *radius
            list.add(clone)
        }
        return list
    }
    /**
     * 根据圆的参数方程 得出XY轴上的圆的表达式 -> List
     * @param pointNumber 用于表示这个圆的粒子个数
     */
    fun getXYCircleLocation(heartLocal:Location,radius:Double,pointNumber: Int):ArrayList<Location>{
        val list = ArrayList<Location>()
        val addRad = 360.0/pointNumber
        var nowRad = 0.0
        for (i in 1 .. pointNumber){
            val rad = Math.toRadians(nowRad)
            nowRad+=addRad
            val sin = Math.sin(rad)
            val cos = Math.cos(rad)
            val clone = heartLocal.clone()
            clone.y += sin*radius
            clone.x += cos *radius
            list.add(clone)
        }
        return list
    }
    /**
     * 根据圆的参数方程 得出ZY轴上的圆的表达式 -> List
     * @param pointNumber 用于表示这个圆的粒子个数
     */
    fun getZYCircleLocation(heartLocal:Location,radius:Double,pointNumber: Int):ArrayList<Location>{
        val list = ArrayList<Location>()
        val addRad = 360.0/pointNumber
        var nowRad = 0.0
        for (i in 1 .. pointNumber){
            val rad = Math.toRadians(nowRad)
            nowRad+=addRad
            val sin = Math.sin(rad)
            val cos = Math.cos(rad)
            val clone = heartLocal.clone()
            clone.y += sin*radius
            clone.z += cos *radius
            list.add(clone)
        }
        return list
    }

    /*
     * 以下代码来自PlayerParticle
     */
    /**
     * Rotates a vector around the X axis at an angle
     *
     * @param v Starting vector
     * @param angle How much to rotate
     * @return The starting vector rotated
     */
    fun rotateAroundAxisX(v: Vector, angle: Double): Vector? {
        val y: Double
        val z: Double
        val cos: Double
        val sin: Double
        cos = Math.cos(angle)
        sin = Math.sin(angle)
        y = v.y * cos - v.z * sin
        z = v.y * sin + v.z * cos
        return v.setY(y).setZ(z)
    }

    /**
     * Rotates a vector around the Y axis at an angle
     *
     * @param v Starting vector
     * @param angle How much to rotate
     * @return The starting vector rotated
     */
    fun rotateAroundAxisY(v: Vector, angle: Double): Vector? {
        val x: Double
        val z: Double
        val cos: Double
        val sin: Double
        cos = Math.cos(angle)
        sin = Math.sin(angle)
        x = v.x * cos + v.z * sin
        z = v.x * -sin + v.z * cos
        return v.setX(x).setZ(z)
    }

    /**
     * Rotates a vector around the Z axis at an angle
     *
     * @param v Starting vector
     * @param angle How much to rotate
     * @return The starting vector rotated
     */
    fun rotateAroundAxisZ(v: Vector, angle: Double): Vector? {
        val x: Double
        val y: Double
        val cos: Double
        val sin: Double
        cos = Math.cos(angle)
        sin = Math.sin(angle)
        x = v.x * cos - v.y * sin
        y = v.x * sin + v.y * cos
        return v.setX(x).setY(y)
    }
}