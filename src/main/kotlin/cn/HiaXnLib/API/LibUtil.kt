package cn.HiaXnLib.API

import cn.HiaXnLib.NMS.item.ItemNMS
import com.fasterxml.jackson.databind.ObjectMapper
import cn.HiaXnLib.item.utils.ItemStackUtils

interface LibUtil {
    fun getItemUtil(): ItemStackUtils
    @Deprecated("过时的 请使用getItemUtil()")
    fun getItemNMS(): ItemNMS
    // 创建一个点击执行
    // 获取将对象转换为JSON的工具
    fun getObjectMapper():ObjectMapper
}