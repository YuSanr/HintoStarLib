package cn.hiaxnlib.lib.utils

import cn.hiaxnlib.particle.RelativeLocation
import org.bukkit.Location
import org.bukkit.entity.Player
import kotlin.collections.ArrayList
import org.bukkit.util.Vector
import kotlin.math.*

object MathUtil {
    /**
     * 通过2个坐标制作向量来表达直线
     * @param pointNumber 用于表示这个直线的粒子个数
     */
    fun getLineLocation(startLocation: RelativeLocation, endLocation: RelativeLocation, pointNumber:Int = 100):ArrayList<RelativeLocation>{
        val list = ArrayList<RelativeLocation>()
        // 取直线向量
        val lineVector = Vector(endLocation.x - startLocation.x,endLocation.y - startLocation.y,endLocation.z - startLocation.z)
        val addVector = lineVector.clone().multiply(1.0/pointNumber)
        list.add(startLocation.clone())
        for (i in 1 .. pointNumber) {
            startLocation.x += addVector.x
            startLocation.y += addVector.y
            startLocation.z += addVector.z
            list.add(startLocation.clone())
        }
        return list
    }
    /**
     * 根据圆的参数方程 得出XZ轴上的圆的表达式 -> List
     * @param pointNumber 用于表示这个圆的粒子个数
     */
    fun getXZCircleLocation(heartLocal: Location, radius:Double, pointNumber: Int):ArrayList<Location>{
        val list = ArrayList<Location>()
        val addRad = Math.toRadians(360.0/pointNumber)
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
    fun getXYCircleLocation(heartLocal: Location, radius:Double, pointNumber: Int):ArrayList<Location>{
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
    fun getZYCircleLocation(heartLocal: Location, radius:Double, pointNumber: Int):ArrayList<Location>{
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
    fun getXYLoveLocation(origin: Location, size:Double, pointNumber: Int):ArrayList<Location>{
        val list = ArrayList<Location>()
        var rad = 0.0
        val addRad = Math.toRadians(360.0/pointNumber)
        for (i in 1..pointNumber){
            val x = (2* cos(rad) - cos(2*rad))*size
            val y = (2* sin(rad) - sin(2*rad))*size
            val clone = origin.clone()
            clone.x += x
            clone.y += y
            rad += addRad
            list.add(clone)
        }
        return list
    }
    fun getXZLoveLocation(origin: Location, size:Double, pointNumber: Int):ArrayList<Location>{
        val list = ArrayList<Location>()
        var rad = 0.0
        val addRad = Math.toRadians(360.0/pointNumber)
        for (i in 1..pointNumber){
            val x = (2* cos(rad) - cos(2*rad))*size
            val z = (2* sin(rad) - sin(2*rad))*size
            val clone = origin.clone()
            clone.x += x
            clone.z += z
            rad += addRad
            list.add(clone)
        }
        return list
    }
    fun getYZLoveLocation(origin: Location, size:Double, pointNumber: Int):ArrayList<Location>{
        val list = ArrayList<Location>()
        var rad = 0.0
        val addRad = Math.toRadians(360.0/pointNumber)
        for (i in 1..pointNumber){
            val z = (2* cos(rad) - cos(2*rad))*size
            val y = (2* sin(rad) - sin(2*rad))*size
            val clone = origin.clone()
            clone.y += y
            clone.z += z
            rad += addRad
            list.add(clone)
        }
        return list
    }

    /**
     * from new bing
     * 将一个相对位置按照axis旋转 n度
     * @param angle 角度 采用的是弧度制
     */
    private fun rotateVector(point: RelativeLocation, axis: RelativeLocation, angle: Double): RelativeLocation {
        val angleInRadians = Math.toRadians(angle.toDouble())
        val sinAngle = sin(angleInRadians / 2)
        val cosAngle = cos(angleInRadians / 2)
        val rotationAxis = axis.normalize() * sinAngle
        val rotationQuaternion = Quaternion(cosAngle, rotationAxis.x, rotationAxis.y, rotationAxis.z)
        val conjugateQuaternion = rotationQuaternion.conjugate()
        val vectorQuaternion = Quaternion(0.0, point.x, point.y, point.z)
        val rotatedVectorQuaternion = rotationQuaternion * vectorQuaternion * conjugateQuaternion
        return RelativeLocation(rotatedVectorQuaternion.x, rotatedVectorQuaternion.y, rotatedVectorQuaternion.z)
    }

    class Quaternion(var w: Double, var x: Double, var y: Double, var z: Double) {
        fun conjugate():Quaternion = Quaternion(w,-x,-y,-z)
        operator fun times(that: Float): Quaternion = Quaternion(
            this.w*that,
            this.x*that,
            this.y*that,
            this.z*that
        )

        operator fun times(that: Quaternion): Quaternion = Quaternion(
            this.w*that.w - this.x*that.x - this.y*that.y - this.z*that.z,
            this.x*that.w + this.w*that.x - this.z*that.y + this.y*that.z,
            this.y*that.w + this.z*that.x + this.w*that.y - this.x*that.z,
            this.z*that.w - this.y*that.x + this.x*that.y + this.w*that.z
        )
    }


    /**
     * 向量图形绕轴旋转N度
     * @param angle 角度 采用的是弧度制
     */
    fun rotateAsAxis(locList: List<RelativeLocation>,axis: RelativeLocation,angle: Double):List<RelativeLocation>{
        for (loc in locList) {
            val rotatePoint = rotateVector(loc,axis,angle)
            loc.also {
                it.x = rotatePoint.x
                it.y = rotatePoint.y
                it.z = rotatePoint.z
            }
        }
        return locList
    }

    /**
     * 让图形的对称轴指向某个点(图形跟着转变)
     */
    fun rotatePointsToPoint(locList:List<RelativeLocation>, toPoint: RelativeLocation, axis: RelativeLocation):List<RelativeLocation>{
        if (axis.length() in -0.00001..0.000001){
            return locList
        }
        val yaw = toPointYaw(axis,toPoint)
        val pitch = toPointPitch(axis,toPoint)
        return rotatePoints(locList,yaw,pitch)
    }
    /**
     * 让图形的对称轴指向某个点(图形跟着转变)
     */
    fun rotatePointsToPoint(locList:List<RelativeLocation>, orgin:Location ,toPoint: Location, axis: RelativeLocation):List<RelativeLocation>{
        if (axis.length() in -0.00001..0.000001){
            return locList
        }
        val relToPoint = RelativeLocation.toRelativeLocation(orgin,toPoint)
        val yaw = toPointYaw(axis,relToPoint)
        val pitch = toPointPitch(axis,relToPoint)
        return rotatePoints(locList,yaw,pitch)
    }
    /**
     * 将这个点集合的对称轴[一般设置的Yaw pitch是由对称轴对某个点进行差角的计算] 顺时针旋转 yaw pitch角度 [+上了原角]
     * 旋转一个点
     * 把这个点按照2个角度旋转 某个弧度
     * 进行旋转时 通过一个点(对称轴上的点) 获取实际角度
     * 具体获取方式看
     * @see toPointYaw 取2点水平角差 (获取起点与终点两个轴之间的角度偏差 Yaw)
     * @see toPointPitch 取2点垂直差  (获取起点与终点两个轴之间的角度偏差 Pitch)
     * @param yaw 水平旋转角 弧度
     * @param pitch 垂直旋转角
     */
        fun rotatePoints(locList: List<RelativeLocation>, yaw:Double, pitch:Double):List<RelativeLocation>{
            /**
             * 原始XY圆
             * x = cos a *r
             * y = sin a *r
             * 套用XZ圆
             * x = cos b *r1
             * y = 0
             * z = sin b *r1
             * b 为水平角
             * a 为垂直角
             * 此时 在XY圆上 点距离Y轴的长度 r1 为 x 既 cos a *r r1 = cos a * r
             * 因此
             * 得出下列式子
             * r = loc.length()
             * x = cos b * cos a * r
             * y = sin a * r
             * z = sin b * cos a * r
             */
            for (loc in locList) {
                val offsetX = locationOffsetXRotationCircle(loc, yaw)
                val offsetZ = locationOffsetZRotationCircle(loc, yaw)
                // 垂直角需要加上原角
                val pitchAdded = pitch + getPitchRad(loc)
                // 这里需要的是水平旋转YAW弧度之后到垂 -直轴的距离
                // FIXED
                val r = sqrt((loc.x * cos(yaw) - loc.z * sin(yaw) - offsetX).pow(2) + loc.y.pow(2) + (loc.z * cos(yaw) + loc.x * sin(yaw) - offsetZ).pow(2))
    //            val r = loc.length()
                val x = cos(yaw) * cos(pitchAdded) * r + offsetX
                val y = sin(pitchAdded) * r
                val z = sin(yaw) * cos(pitchAdded) * r + offsetZ
                loc.x = x
                loc.y = y
                loc.z = z
            }
            return locList
        }
    /**
     * 取位置与旋转轴的垂直交点
     * 用于方便旋转
     * X 坐标
     * @param yaw 对称轴要加的角度
     */
    private fun locationOffsetXRotationCircle(loc: RelativeLocation,yaw: Double):Double{
        // 计算偏移X轴时 输入的是原坐标(旋转前)
        // 需要水平旋转YAW弧度之后在进行计算
        val x = loc.x * cos(yaw) - loc.z * sin(yaw)
        val z = loc.z * cos(yaw) + loc.x * sin(yaw)
        return if ((yaw == PI /2) || (yaw == -PI /2)){
            x
        }else if ((yaw in -0.0000001 .. 0.0000001)||(yaw in -PI -0.0000001 .. -PI +0.0000001)){
            0.0
        }else{
            val k1 = tan(yaw)
            val k2 = tan(yaw - PI /2)
            (z-k1*x)/(k2-k1)
        }
    }
    /**
     * 取位置与旋转轴的垂直交点
     * 用于方便旋转
     * Z 坐标
     * @param yaw 对称轴要加的角度
     */
    private fun locationOffsetZRotationCircle(loc: RelativeLocation,yaw: Double):Double{
        return if ((yaw == PI /2) || (yaw == -PI /2)){
            0.0
        }else if ((yaw in -0.0000001 .. 0.0000001)||(yaw in -PI -0.0000001 .. -PI +0.0000001)){
            loc.z
        }else{
            val k2 = tan(yaw - PI /2)
            locationOffsetXRotationCircle(loc,yaw) *k2
        }
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

    private fun getYawFromLocation(loc:RelativeLocation):Double{
        var x = loc.x
        var z = loc.z
        if (z in -0.000000001 .. 0.000000001){
            z = 0.0
        }
        return atan2(z,x)
    }

    private fun getPitchRad(loc:RelativeLocation):Double{
        // 可以用when代替 但是懒得换了:(
        val sp = getYawFromLocation(loc)
        if (loc.y == 0.0 && loc.x == 0.0 && loc.z == 0.0) return 0.0
//        return atan2(loc.y,axisX)
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
            if(loc.x>=0 && loc.z<=0){
                atan2(loc.y,sq)
            }else{
                atan2(loc.y,-sq)
            }
        }
    }
    fun getPlayerEyeVector(player: Player):Vector{
        return player.eyeLocation.direction
    }
}