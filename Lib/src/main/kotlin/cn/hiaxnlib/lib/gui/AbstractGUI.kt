package cn.hiaxnlib.lib.gui

import cn.hiaxnlib.lib.event.GuiCloseEvent
import cn.hiaxnlib.lib.event.GuiOpenEvent
import cn.hiaxnlib.lib.manner.GUIManner
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

abstract class AbstractGUI (val title:String,val size:Int,val owner:Player,var moved:Boolean){
    val uid = GuiUID.toUID(this::class.java)
    var inventory:Inventory? = null
    private var buttons = Array<AbstractButton>(size){ AirButton() }
    abstract fun onGUIOpen(event:GuiOpenEvent)
    abstract fun onGUIClose(event: GuiCloseEvent)
    fun setButton(button:AbstractButton?,slot:Int){
        if (slot>=size){
            println("Button $button 注册失败 原因: Slot超出容器范围")
            return
        }
        if (button == null){
            buttons[slot] = AirButton()
            return
        }
        buttons[slot] = button
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
        }
        for (i in buttons.indices){
            inventory!!.setItem(i,buttons[i].item)
        }
        if (GUIManner.isHiaXnGui(owner.openInventory) && GUIManner.getUID(owner.openInventory) == this.uid && owner.openInventory.topInventory.size == size){
            for (index in buttons.indices) {
                owner.openInventory.topInventory.setItem(index,buttons[index].item)
            }
        }
    }
    fun openGUI(){
        if (!GUIManner.isRegister(uid)){
            GUIManner.registerGUI(uid)
        }
        if (GUIManner.isHiaXnGui(owner.openInventory) && GUIManner.getUID(owner.openInventory) == this.uid){
            // 视为更新操作
            updateInventory()
            return
        }
        updateInventory()
        owner.player!!.closeInventory()
        owner.openInventory(inventory!!)
        GUIManner.playerOpenGUI[owner.uniqueId] = this
        val event = GuiOpenEvent(this)
        Bukkit.getServer().pluginManager.callEvent(event)
        onGUIOpen(event)
    }
}