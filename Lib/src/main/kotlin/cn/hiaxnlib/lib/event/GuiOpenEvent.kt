package cn.hiaxnlib.lib.event

import cn.hiaxnlib.lib.gui.AbstractGUI
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GuiOpenEvent(val gui: AbstractGUI):Event() {
    companion object{
        val handler = HandlerList()
        @JvmStatic
        fun getHandlerList():HandlerList{
            return handler
        }
    }
    override fun getHandlers(): HandlerList {
        return handler
    }
}