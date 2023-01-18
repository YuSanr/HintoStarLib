package cn.HiaXnLib.API.POJO

abstract class HiaXnNBTBaseBuilder<T>(var value:T){
    abstract fun toNBTTag():Any
}