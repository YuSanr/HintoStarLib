package cn.hiaxnlib.lib.data

//import java.io.ObjectInputStream
//import java.io.ObjectOutputStream
import cn.hiaxnlib.lib.version.VersionUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter

class EntityData {
    private val plugin = VersionUtil.getHiaXnLibPlugin()
    //存Groups
    private val file = File(plugin.dataFolder,"/data/entityData.json")
    private val folder = File(plugin.dataFolder,"/data/")
    private val gson = Gson()
    init {
        if (!file.exists()){
            folder.mkdirs()
            file.createNewFile()
        }
    }
    fun saveData(data: ArrayList<ArmorStandData>){
        saveToJson(toJson(data))
    }
    fun getData():ArrayList<ArmorStandData>{
        val type = object:TypeToken<ArrayList<ArmorStandData>>(){}.type
        return gson.fromJson(loadJson(),type)
    }
    private fun toJson(group:ArrayList<ArmorStandData>):String{
        return gson.toJson(group)
    }
    private fun loadJson():String{
        val input= FileInputStream(file)
        val bytes = ByteArray(2048)
        var len = 0
        val strBuilder = StringBuilder()
        while(input.read(bytes).also { len = it ;return@also} != -1 ){
            strBuilder.append(String(bytes,0,len))
        }
        input.close()
        return strBuilder.toString()
    }
    private fun saveToJson(json:String){
        val output = BufferedWriter(FileWriter(file))
        output.write(json)
        output.flush()
        output.close()
    }

}