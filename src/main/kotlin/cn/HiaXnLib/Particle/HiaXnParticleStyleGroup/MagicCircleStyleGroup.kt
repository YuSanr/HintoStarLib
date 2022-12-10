package cn.HiaXnLib.Particle.HiaXnParticleStyleGroup

import cn.HiaXnLib.Particle.ParticleOwner.Owner
import org.bukkit.Location

class MagicCircleStyleGroup:AbstractParticleStyleGroup() {

    // 该样式组由
    // 5个点 -- 五条直线
    // 一个在XZ轴上的圆组成
    // 其中这5个点绕着圆旋转 同时影响着直线
    // 一个示例样式组
    /**
     * 样式调用
     */
    override fun display(owner:Owner) {

    }
}