package cn.HiaXnLib.API

import cn.HiaXnLib.NMS.Gui.GUIUtil
import cn.HiaXnLib.NMS.Gui.ItemClickAction
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
    @Deprecated("过时的,请使用HiaXnItemStack 并为其设置物品方法")
    override fun createItemClickAction(): ItemClickAction {
        return ItemClickAction()
    }
    override fun getGUIUtil(): GUIUtil {
        return GUIUtil()
    }

    override fun getObjectMapper(): ObjectMapper {
        return ObjectMapper()
    }

}