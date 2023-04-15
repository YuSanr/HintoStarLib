package cn.hiaxnlib.lib.entity.armorStand.actions

import cn.hiaxnlib.lib.owner.Owner

interface EntityAIAction{
    /**
     * Entity为距离以内的所有Entity都会执行这个方法 除了自己
     * 发现实体AI操作
     * 在这里可以使用Barrage进行远程攻击什么的
     * 冷却自己写
     * Entity在可视范围内时 以1tick的速度执行这个函数
     */
    fun aiAction(owner: Owner)
}