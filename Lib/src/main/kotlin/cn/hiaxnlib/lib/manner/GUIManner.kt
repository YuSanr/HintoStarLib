package cn.hiaxnlib.lib.manner

import cn.hiaxnlib.lib.gui.AbstractGUI
import cn.hiaxnlib.lib.gui.GuiUID
import org.bukkit.inventory.InventoryView
import java.util.UUID

class GUIManner {
    companion object{
        private val register:HashMap<GuiUID,AbstractGUI> = HashMap()
        val playerOpenGUI = HashMap<UUID,AbstractGUI>()
        fun registerGUI(gui: AbstractGUI){
            register[gui.uid] = gui
        }
        fun isHiaXnGui(inventoryView: InventoryView):Boolean{
            val title = inventoryView.title
            val split = title.split(" ")
            val uid = split[split.size -1]
            return register.containsKey(GuiUID(uid))
        }
    }

}