package cn.hiaxnlib.lib.gui

import java.util.Random

class GuiUID(val uid:String) {
    companion object{
        private const val randomChar = "0123456789abcdef"
        /**
         * 32位的随机UID
         */
        fun randomUID():GuiUID{
            val rd = Random()
            val bufferUID = StringBuffer("§")
            for (i in 0..31){
                bufferUID.append("${randomChar[rd.nextInt(randomChar.length)]}§")
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