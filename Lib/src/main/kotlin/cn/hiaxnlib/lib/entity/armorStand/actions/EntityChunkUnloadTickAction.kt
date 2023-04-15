package cn.hiaxnlib.lib.entity.armorStand.actions

interface EntityChunkUnloadTickAction {
    /**
     * 在root节点所在的区块卸载时
     * 服务器会以 1tick 一次的频率执行这个函数
     */
    fun onUnloadTickAction()
}