package cn.HiaXnLib.API

import cn.HiaXnLib.NMS.Gui.GUIUtil
import cn.HiaXnLib.NMS.Gui.ItemClickAction
import cn.HiaXnLib.NMS.item.ItemNMS
import cn.HiaXnLib.NMS.item.itemUtil
import com.fasterxml.jackson.databind.ObjectMapper
import cn.HiaXnLib.item.utils.ItemStackUtils

interface LibUtil {
    fun getItemUtil(): ItemStackUtils
    @Deprecated("过时的 请使用getItemUtil()")
    fun getItemNMS(): ItemNMS
    // 创建一个点击执行
    @Deprecated("过时的", ReplaceWith("ItemClickAction()", "cn.HiaXnLib.NMS.Gui.ItemClickAction"))
    fun createItemClickAction(): ItemClickAction
    fun getGUIUtil():GUIUtil
    // 获取将对象转换为JSON的工具
    fun getObjectMapper():ObjectMapper
}