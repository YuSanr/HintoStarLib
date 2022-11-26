package cn.HiaXnLib.Particle.Exception

class ParticleStyleTimeNotInitException(var reason:String):Exception(reason) {
    constructor():this("ParticleStyle interval time is not initialization")
}