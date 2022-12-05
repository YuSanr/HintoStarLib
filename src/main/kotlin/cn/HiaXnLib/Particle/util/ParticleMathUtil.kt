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
    /**
     * 旋转一个点
     * 把这个点按照2个角度旋转 某个弧度
     * 进行旋转时 通过一个点(对称轴上的点) 获取实际角度
     * 具体获取方式看
     * @see toPointYaw 取2点水平角差
     * @see toPointPitch 取2点垂直差
     * @param yaw 水平旋转角 弧度
     * @param pitch 垂直旋转角
     */
    fun rotationPoint(locList: List<RelativeLocation>, yaw:Double, pitch:Double):List<RelativeLocation>{
        /**
         * 原始XY圆
         * x = cos a *r
         * y = sin a *r
         * 套用XZ圆
         * x = cos b *r
         * y = 0
         * z = sin b *r
         * b 为水平角
         * a 为垂直角
         * 此时 在XY圆上 点距离Y轴的长度为 x 既 cos a *r
         * 因此
         * 得出下列式子
         * r = loc.length()
         * x = cos b * cos a * loc.length()
         * y = sin a * loc.length()
         * z = sin b * cos a * loc.length()
         */
        for (loc in locList) {
            val yawAdded = yaw + getYawFromLocation(loc)
            val pitchAdded = pitch + getPitchRad(loc)
            val r = loc.length()
            val x = cos(yawAdded) * cos(pitchAdded) * r
            val y = sin(pitchAdded) * r
            val z = sin(yawAdded) * cos(pitchAdded) * r
            loc.x = x
            loc.y = y
            loc.z = z
        }
        return locList
    }

    /**
     * 获取到2个点的垂直角的偏差
     * @param loc 起点
     * @param toLoc 终点
     * 起点的垂直角 + 该函数的结果 = 终点的垂直角
     */
    fun toPointPitch(loc: RelativeLocation, toLoc:RelativeLocation):Double{
        return getPitchRad(toLoc) - getPitchRad(loc)
    }
    /**
     * 获取到2个点的水平角的偏差
     * @param loc 起点
     * @param toLoc 终点
     * 起点的水平角 + 该函数的结果 = 终点的水平角
     */
    fun toPointYaw(loc:RelativeLocation, toLoc:RelativeLocation):Double{
        return getYawFromLocation(toLoc) - getYawFromLocation(loc)
    }

    fun getYawFromLocation(loc:RelativeLocation):Double{
        var x = loc.x
        var z = loc.z
        if (x in -0.000000001 .. 0.000000001){
            x = 0.0
        }
        if (z in -0.000000001 .. 0.000000001){
            z = 0.0
        }
        return atan2(z,x)
    }

    fun getPitchRad(loc:RelativeLocation):Double{
        val sp = getYawFromLocation(loc)
        if (loc.y == 0.0 && loc.x == 0.0 && loc.z == 0.0) return 0.0
        val sq = sqrt(loc.x.pow(2)+loc.z.pow(2))
        return if(sp in 0.0 .. PI /2){
            if(loc.x>=0 && loc.z>=0){
                atan2(loc.y,sq)
            }else{
                atan2(loc.y,-sq)
            }
        }else if(sp in PI /2 .. PI){
            if(loc.x<=0 && loc.z>=0){
                atan2(loc.y,sq)
            }else{
                atan2(loc.y,-sq)
            }
        }else if(sp in -PI .. -PI /2){
            if(loc.x<=0 && loc.z<=0){
                atan2(loc.y,sq)
            }else{
                atan2(loc.y,-sq)
            }
        }else {
            if(loc.x<=0 && loc.z<=0){
                atan2(loc.y,sq)
            }else{
                atan2(loc.y,-sq)
            }
        }
    }
}
