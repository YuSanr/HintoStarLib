package cn.hiaxnlib.particle.Exception

class ParticleStyleTimeNotInitException(var reason:String):Exception(reason) {
    constructor():this("ParticleStyle interval time is not initialization")
}