package cn.hiaxnlib.lib.entity.armorStand.actions

interface EntityTickAction {
    /**
     * 在root节点所在的区块加载时
     * 服务器会以 1tick 一次的频率执行这个函数
     */
    fun tickAction()
}