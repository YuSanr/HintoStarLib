package cn.HiaXnLib.Particle

import org.bukkit.Effect

// 只描述一个粒子数据
class HiaXnParticle(var data: ParticleData, var config: ParticleConfig){
    constructor(config: ParticleConfig):this(ParticleData(0,0),config)
    // 一个POJO类
    class ParticleData(var id:Int,var data:Int)
    // 存储粒子基本设置的
    class ParticleConfig(var type:Effect,var speed:Float,var count:Int,var radius:Int,var offX:Float,var offY:Float,var offZ:Float){
        constructor(type: Effect,speed: Float,count: Int,radius: Int):this(type,speed,count,radius,0.0f,0.0f,0.0f)
        constructor(type: Effect):this(type,0f,10,64,0.0f,0.0f,0.0f)
    }
}