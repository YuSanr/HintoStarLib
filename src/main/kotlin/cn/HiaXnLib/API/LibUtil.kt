package cn.HiaXnLib.API

import com.fasterxml.jackson.databind.ObjectMapper
import cn.HiaXnLib.item.utils.ItemStackUtils

interface LibUtil {
    fun getItemUtil(): ItemStackUtils
    // 创建一个点击执行
    // 获取将对象转换为JSON的工具
    fun getObjectMapper():ObjectMapper
}