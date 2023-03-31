package cn.hiaxnlib.lib.gui

import java.util.Random

class GuiUID(val uid:String) {
    companion object{
        /**
         * 32位的随机UID
         */
        fun toUID(cls:Class<out AbstractGUI>):GuiUID{
            val hashCodeSTR = "${cls.`package`.toString().hashCode()}${cls.name.hashCode()}"
            val bufferUID = StringBuffer("§")
            for (c in hashCodeSTR) {
                bufferUID.append("$c§")
            }
            bufferUID.deleteCharAt(bufferUID.length-1)
            return GuiUID(bufferUID.toString())
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GuiUID

        if (uid != other.uid) return false

        return true
    }

    override fun hashCode(): Int {
        return uid.hashCode()
    }

    override fun toString(): String {
        return uid
    }

}