package by.bashlikovv.lab1.utils

import com.google.gson.Gson

interface JsonSerialization {

    val gson: Gson

    fun <T>String.parseJson(clazz: Class<T>): T {
        return gson.fromJson(this, clazz)
    }

    fun <T> T.parseToJson(): String {
        return gson.toJson(this)
    }

    fun getJsonFromFile()

    fun saveJsonToFile()
}