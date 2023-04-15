package cn.hiaxnlib.lib.entity.armorStand.actions

interface EntityRandomAction {
    /**
     * 在Root节点所在的区块加载时
     * 服务器会以随机tick的频率执行这个函数
     */
    fun randomTick()
}