package cn.HiaXnLib.Particle

import org.bukkit.Location

/**
 * @param size 弹幕的大小 不可修改
 * @param nowLocation 当前位置
 */
abstract class AbstractBarrage (private val size:Double, var nowLocation:Location,val checkSizeStrategy:()->Boolean){
    // 弹幕绑定的效果
    var bindParticleStyleGroup:AbstractHiaXnParticleStyleGroup? = null
    // 弹幕轨迹的初始移动速度 block/s Max 20
    private var speed = 1.0
    // 弹幕轨迹的加速度
    private var a = 0.0
    // 获取弹幕的轨迹列表
    // 需要提供一个参数为末坐标
    // ->及轨迹的终点
    abstract fun getTrajectory(endLocation:Location):ArrayList<Location>
    /**
     * 设置弹幕的初始移动速度
     */
    fun setBarrageSpeed(speed:Double){
        this.speed = Math.min(20.0,speed)
    }
    fun setAcceleration(a:Double){
        this.a = a
    }
}