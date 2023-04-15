package cn.hiaxnlib.particle.Exception

class ParticleNotInitException(var reason:String):Exception(reason) {
    /**
     * 当粒子或者粒子样式未初始化就调用display方法时 抛出该异常
     */
    constructor():this("ParticleGroup or ParticleStyle is not initialization")
}