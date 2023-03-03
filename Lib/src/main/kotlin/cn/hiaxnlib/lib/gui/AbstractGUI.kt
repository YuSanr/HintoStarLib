package cn.hiaxnlib.lib.gui

import cn.hiaxnlib.lib.event.GuiCloseEvent
import cn.hiaxnlib.lib.event.GuiOpenEvent
import cn.hiaxnlib.lib.manner.GUIManner
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

abstract class AbstractGUI (val title:String,val size:Int,val owner:Player,var moved:Boolean){
    val uid = GuiUID.randomUID()
    var inventory:Inventory? = null
    private var buttons = Array<AbstractButton>(size){ AirButton() }
    abstract fun onGUIOpen(event:GuiOpenEvent)
    abstract fun onGUIClose(event: GuiCloseEvent)
    abstract fun clone():AbstractGUI
    fun setButton(button:AbstractButton?,slot:Int){
        if (slot>=size){
            println("Button $button 注册失败 原因: Slot超出容器范围")
            return
        }
        if (button == null){
            buttons[slot] = AirButton()
        }
        buttons[slot] = button!!
    }
    fun getButton(slot:Int):AbstractButton?{
        if (buttons.size-1 < slot || slot<0){
            return null
        }
        return buttons[slot]
    }
    private fun updateInventory(){
        if (inventory == null){
            inventory = Bukkit.createInventory(owner,size,"$title $uid")
            for (i in buttons.indices){
                inventory!!.contents[i] = buttons[i].item
            }
        }else {
            for (i in buttons.indices){
                inventory!!.contents[i] = buttons[i].item
            }
        }
        if (GUIManner.isHiaXnGui(owner.openInventory)){
            owner.openInventory.topInventory.contents = inventory!!.contents
        }
    }
    fun openGUI(){
        if (inventory == null){
            updateInventory()
            // 防止GUI冲突 先关闭上一个GUI
            owner.player!!.closeInventory()
            owner.openInventory(inventory!!)
            Bukkit.getServer().pluginManager.callEvent(GuiOpenEvent(this))
        }else{
            updateInventory()
        }
    }
}