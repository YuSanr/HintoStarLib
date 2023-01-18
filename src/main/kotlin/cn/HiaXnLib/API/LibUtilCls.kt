package cn.HiaXnLib.API

import com.fasterxml.jackson.databind.ObjectMapper
import cn.HiaXnLib.item.utils.ItemStackUtils
class LibUtilCls : LibUtil {
    override fun getItemUtil(): ItemStackUtils {
        return ItemStackUtils()
    }

    override fun getObjectMapper(): ObjectMapper {
        return ObjectMapper()
    }
}