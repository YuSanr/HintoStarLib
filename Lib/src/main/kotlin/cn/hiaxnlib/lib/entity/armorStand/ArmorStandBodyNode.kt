package cn.hiaxnlib.lib.entity.armorStand

import cn.hiaxnlib.lib.entity.armorStand.utils.ArmorStandSeeBox
import cn.hiaxnlib.particle.RelativeLocation
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import java.util.*

abstract class ArmorStandBodyNode(
    parentNode: ArmorStandNode?,
    standOwnerUUID: UUID?,
    seeBox: ArmorStandSeeBox,
    parentRelative: RelativeLocation,
    maxHealth: Double
) : ArmorStandNode(parentNode, standOwnerUUID, seeBox, parentRelative, maxHealth) {
    override fun toRealDamage(damage: Double): Double {
        return 0.0
    }
    override fun spawnAction() {
    }

    override fun damageAction(damage: Double, damager: Entity?,from:ArmorStandNode?) {
    }

    override fun deathAction(killer: Entity?) {
    }
}