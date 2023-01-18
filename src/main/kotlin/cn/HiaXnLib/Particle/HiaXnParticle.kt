package cn.HiaXnLib.Particle

import org.bukkit.Particle

// 只描述一个粒子数据
class HiaXnParticle(var config: ParticleConfig){
    // 一个POJO类
    //   class ParticleData(var datas:IntArray)
    // 存储粒子基本设置的
    /**
     * @param extra 对应 spigot 1.9+的 extra参数
     * @param data 粒子数据 默认为NULL
     * @see Particle.getDataType data依赖于此结果
     */
    class ParticleConfig(var type:Particle,var extra:Double,var count:Int,var offX:Double,var offY:Double,var offZ:Double,var data:Any? = null){
        constructor(type: Particle, extra: Double, count: Int, data: Any? = null):this(type,extra,count,0.0,0.0,0.0,data)
        constructor(type: Particle):this(type,0.0,10,0.0,0.0,0.0)
    }
}