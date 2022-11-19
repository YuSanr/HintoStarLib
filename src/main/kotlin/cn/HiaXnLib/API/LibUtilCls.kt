package cn.HiaXnLib.API

import cn.HiaXnLib.NMS.item.ItemNMS
import com.fasterxml.jackson.databind.ObjectMapper
import cn.HiaXnLib.item.utils.ItemStackUtils
class LibUtilCls : LibUtil {
    override fun getItemUtil(): ItemStackUtils {
        return ItemStackUtils()
    }
    @Deprecated("过时的 请使用getItemUtil", ReplaceWith("getItemUtil()", "item.utils.ItemStackUtils"))
    override fun getItemNMS(): ItemNMS {
        return ItemNMS()
    }
    override fun getObjectMapper(): ObjectMapper {
        return ObjectMapper()
    }
}