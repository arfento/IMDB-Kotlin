package com.ims.imdb_kotlin.db

import androidx.room.TypeConverter

class IdsToStringConverter {
    @TypeConverter
    fun listToStringConverters(ids: List<Int>) : String{
        var str = "";
        for (i in ids){
            str += "$i,"
        }
        return if (str == ""){
            str
        }else str.substring(0, str.length-1)
    }

    @TypeConverter
    fun StringToList(x:String)  : List<Int>{
        val list: MutableList<Int> = ArrayList()
        when{
            x.isEmpty() -> return list
            x.length == 1 ->{
                list.add(x.toInt())
            }
            else -> {
                for (i in x.split(",").toTypedArray()){
                    list.add(i.toInt())
                }
            }
        }
        return list
    }
}