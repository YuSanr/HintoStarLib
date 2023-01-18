package cn.HiaXnLib.API.POJO

import cn.HiaXnLib.exception.NBTTagBaseTypeException
import cn.HiaXnLib.utils.NMSUtil


abstract class HiaXnNBTBaseBuilder<T>(var value:T){
    abstract fun toNBTTag():Any
    companion object{
        @JvmStatic
        fun <T>newNBTBaseBuilder(value:T):HiaXnNBTBaseBuilder<T>{
            value?:throw NBTTagBaseTypeException("null is not a NBTTag")
            val cls = Class.forName("cn.HiaXnLib.Lib.${NMSUtil.getVersion()}.POJO.LibNBTBaseBuilder")
            val instance = cls.getConstructor(value!!::class.java).newInstance(value)
            if (instance !is HiaXnNBTBaseBuilder<*>){
                throw NBTTagBaseTypeException("${value!!::class.java.typeName} is not a NBTTag")
            }
            return cls.getConstructor(value!!::class.java).newInstance(value) as HiaXnNBTBaseBuilder<T>
        }
    }
}