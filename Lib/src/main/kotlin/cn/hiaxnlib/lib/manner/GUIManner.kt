package cn.hiaxnlib.lib.manner

import cn.hiaxnlib.lib.gui.AbstractGUI
import cn.hiaxnlib.lib.gui.GuiUID
import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryView
import java.util.UUID

class GUIManner {
    companion object{
        private val register:ArrayList<GuiUID> = ArrayList()
        val playerOpenGUI = HashMap<UUID,AbstractGUI>()
        fun registerGUI(uid:GuiUID){
            register.add(uid)
        }
        fun registerGUI(gui:AbstractGUI){
            register.add(gui.uid)
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
    }

}