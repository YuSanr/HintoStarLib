package cn.HiaXnLib.Lib.v1_8_R3.POJO

import cn.HiaXnLib.API.POJO.HiaXnNBTBaseBuilder
import cn.HiaXnLib.exception.NBTTagBaseTypeException
import net.minecraft.server.v1_8_R3.NBTBase
import net.minecraft.server.v1_8_R3.NBTTagByte
import net.minecraft.server.v1_8_R3.NBTTagByteArray
import net.minecraft.server.v1_8_R3.NBTTagDouble
import net.minecraft.server.v1_8_R3.NBTTagFloat
import net.minecraft.server.v1_8_R3.NBTTagInt
import net.minecraft.server.v1_8_R3.NBTTagIntArray
import net.minecraft.server.v1_8_R3.NBTTagList
import net.minecraft.server.v1_8_R3.NBTTagLong
import net.minecraft.server.v1_8_R3.NBTTagShort
import net.minecraft.server.v1_8_R3.NBTTagString

class LibNBTBaseBuilder<T>(value:T): HiaXnNBTBaseBuilder<T>(value) {
    override fun toNBTTag(): Any {
        if (value == null){
            throw NBTTagBaseTypeException("null is not a NBT value")
        }
        return toNBTBase(value!!)
    }
    private fun toNBTBase(value:Any):NBTBase{
        return when(val typeName = value::class.java.typeName){
            "int"->NBTTagInt(value as Int)
            "double"->NBTTagDouble(value as Double)
            "intarray"->NBTTagIntArray(value as IntArray)
            "byte"->NBTTagByte(value as Byte)
            "bytearray"->NBTTagByteArray(value as ByteArray)
            "long"->NBTTagLong(value as Long)
            "short"->NBTTagShort(value as Short)
            "float"->NBTTagFloat(value as Float)
            "string"->NBTTagString(value as String)
            "arraylist"-> {
                val list = value as ArrayList<*>
                val tagList = NBTTagList()
                for (any in list) {
                    tagList.add(toNBTBase(any))
                }
                tagList
            }
            else-> throw NBTTagBaseTypeException("type$typeName is not a NBT value!")
        }
    }
}