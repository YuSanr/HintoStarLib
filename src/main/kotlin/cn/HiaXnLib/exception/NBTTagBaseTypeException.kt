package cn.HiaXnLib.exception

class NBTTagBaseTypeException(var msg:String): Exception(msg) {
    constructor():this("this type is not supposed as NBTTagBase")
}