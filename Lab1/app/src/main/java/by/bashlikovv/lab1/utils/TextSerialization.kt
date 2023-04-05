package by.bashlikovv.lab1.utils

import java.io.Serializable

interface TextSerialization {

    fun getTextFromFile()

    fun saveTextToFile()

    fun toString(obj: Serializable): String

    fun fromString(str: String): Any
}