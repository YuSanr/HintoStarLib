package cn.HiaXnLib.command

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticlePair
import cn.HiaXnLib.Particle.ParticleOwner.Barrages.SwordBarrage
import cn.HiaXnLib.Particle.ParticleOwner.Barrages.SwordTrackBarrage
import cn.HiaXnLib.Particle.ParticleOwner.Barrages.util.BarrageUtil
import cn.HiaXnLib.Particle.ParticleOwner.ParticlePlayer
import cn.HiaXnLib.Particle.ParticleOwner.ParticlePoint
import cn.HiaXnLib.Particle.ParticleStyle.ArrowTrackParticleStyle
import cn.HiaXnLib.Particle.ParticleStyle.SwordParticleStyle
import cn.HiaXnLib.Particle.RelativeLocation
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import cn.HiaXnLib.Particle.util.ParticleUtil
import cn.HiaXnLib.main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*
import kotlin.math.PI

class testCommand:CommandExecutor {
    override fun onCommand(sender: CommandSender?, p1: Command?, p2: String?, args: Array<out String>): Boolean {

        if (sender !is Player){
            return true
        }
        sender as Player

        // 上三个
//        Bukkit.getScheduler().runTaskTimer(main.getInstance(),{
//            val dic = ParticleUtil.getPlayerEyeVector(sender)
//            val up_left = ParticleMathUtil.rotationPoint(listOf(RelativeLocation(4.0,0.0,0.0)),-3*PI/4 + sender.eyeLocation.yaw,0.0)[0]
//            val up_mid = ParticleMathUtil.rotationPoint(listOf(RelativeLocation(4.0,0.0,0.0)),-2*PI/4+ sender.eyeLocation.yaw,0.0)[0].also { it.y = 3.0 }
//            val up_right= ParticleMathUtil.rotationPoint(listOf(RelativeLocation(4.0,0.0,0.0)),-PI/4+ sender.eyeLocation.yaw,0.0)[0]
//            val left_UP_Loc  = locAddRelative(sender.location.clone(),up_left)
//            left_UP_Loc.y += 3
//            val right_UP_Loc  = locAddRelative(sender.location.clone(),up_right)
//            right_UP_Loc.y += 3
//            val mid_UPLoc  = locAddRelative(sender.location.clone(),up_mid)
//            mid_UPLoc.y += 3
//            val up_left_sword = SwordBarrage(left_UP_Loc, UUID.randomUUID(), ParticlePlayer(sender), 80.0, dic)
//            val up_mid_sword = SwordBarrage(mid_UPLoc, UUID.randomUUID(), ParticlePlayer(sender), 80.0, dic)
//            val up_right_sword = SwordBarrage(right_UP_Loc, UUID.randomUUID(), ParticlePlayer(sender), 80.0, dic)
//            BarrageUtil.spawnBarrage(up_left_sword)
//            BarrageUtil.spawnBarrage(up_right_sword)
//            BarrageUtil.spawnBarrage(up_mid_sword)
//        },1L,40L)
//        sender.sendMessage("指令执行成功！！！！")
        if (args.isEmpty()){
            return true
        }
        val target = Bukkit.getPlayer(args[0])
        Bukkit.getScheduler().runTaskTimer(main.getInstance(),
            {
            val trackSword = SwordTrackBarrage(sender.location.clone(),UUID.randomUUID(),ParticlePlayer(sender),20.0,target)
                BarrageUtil.spawnBarrage(trackSword)
        },0,10)
        return true
    }
    fun locAddRelative(location: Location,relative: RelativeLocation):Location{
        location.x += relative.x
        location.y += relative.y
        location.z += relative.z
        return location
    }
}