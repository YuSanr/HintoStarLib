package cn.hiaxnlib.lib.utils

import cn.hiaxnlib.particle.RelativeLocation
import kotlin.collections.ArrayList
import org.bukkit.util.Vector

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
}