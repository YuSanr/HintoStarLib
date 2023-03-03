package cn.hiaxnlib.lib.item

import cn.hiaxnlib.lib.version.VersionUtil

class HiaXnTag<T>(val value:T) {
    fun toNMSNBTTagObject():Any?{
        return VersionUtil.getNBTTagUtil().toNMSNBTTagObject(this)
    }
}