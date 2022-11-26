package cn.HiaXnLib.Particle

import org.bukkit.Location
import java.util.LinkedList

abstract class HiaXnParticleStyle(){

    /**
     * 上一个点与下一个点之间的间隔
     * 单位 tick
     * @see setIntervalTime
     */
    private var  intervalTick:Int = -1
    /**
     * @param location 原点 所有styleMap的轨迹的相对位置的原点
     * 一般为Minecraft的世界坐标原点
     */
    abstract fun getParticleList(location:Location):LinkedList<HiaXnParticleGroup>
    /**
     * 更新粒子所在的位置
     */
    abstract fun updateParticleLocation()
    /**
     * 强制覆写的取消策略
     * 防止无休止的循环进行下去
     * 如果就是需要无休止的循环
     * 则 return false即可
     * 返回值为true时终止播放
     */
    abstract fun cancelStrategy():Boolean
    /**
     * 上一个点与下一个点之间的间隔
     * 单位 tick
     */
    fun setIntervalTime(time:Int){
        this.intervalTick = time
    }
    fun getIntervalTime():Int{
        return intervalTick
    }
}