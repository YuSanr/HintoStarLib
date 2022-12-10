package cn.HiaXnLib.Particle.HiaXnParticleStyleGroup

import cn.HiaXnLib.Particle.ParticleOwner.Owner
import org.bukkit.Location

abstract class AbstractParticleStyleGroup() {
    abstract fun display(owner:Owner)
}