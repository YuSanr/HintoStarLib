package cn.hiaxnlib.lib.managers

import cn.hiaxnlib.lib.gui.AbstractGUI
import cn.hiaxnlib.lib.gui.GuiUID
import org.bukkit.inventory.InventoryView
import java.util.UUID

class GUIManager {
    companion object{
        private val register:ArrayList<GuiUID> = ArrayList()
        val playerOpenGUI = HashMap<UUID,AbstractGUI>()
        fun registerGUI(uid:GuiUID){
            register.add(uid)
        }
        fun registerGUI(gui:AbstractGUI){
            register.add(gui.uid)
        }
        fun registerGUI(cls:Class<out AbstractGUI>){
            register.add(GuiUID.toUID(cls))
        }
        fun isHiaXnGui(inventoryView: InventoryView):Boolean{
            val uid = getUID(inventoryView)
            return register.contains(uid)
        }
        fun getUID(inventoryView: InventoryView):GuiUID{
            val title = inventoryView.title
            val split = title.split(" ")
            val uid = split[split.size -1]
            return GuiUID(uid)
        }
        fun isRegister(uid:GuiUID):Boolean{
            return register.contains(uid)
        }
    }
}