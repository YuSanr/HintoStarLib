package cn.hiaxnlib.particle

import org.bukkit.Location

abstract class HiaXnParticleStyle(private var intervalTick:Int){

    /**
     * 获取当前tick(自己定义) 下的粒子组位置关系Map
     */
    abstract fun getTickGroupMap():HashMap<RelativeLocation,HiaXnParticleGroup>
    /**
     * 更新粒子的tick (帧)
     */
    abstract fun updateParticleTick()
    /**
     * 检测这个粒子动画是否结束
     */
    abstract fun checkOver():Boolean
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
    fun display(location:Location){
       // 显示当前Tick的粒子
        for (entry in getTickGroupMap()) {
            val loc = location.clone().also {
                it.x += entry.key.x
                it.y += entry.key.y
                it.z += entry.key.z
            }
            entry.value.display(loc)
        }
    }
}