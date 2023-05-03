package app.utils

import app.Repository
import app.updated_shapes.Shape
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.lang.reflect.Type

object JsonUtils {

    class Serializer : JsonSerializer<Shape> {
        override fun serialize(item: Shape, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
            val jsonObject = JsonObject()
            jsonObject.addProperty("size", item.size)
            jsonObject.addProperty("name", item.name)
            jsonObject.addProperty("color", item.color)
            jsonObject.addProperty("className", item.javaClass.name)
            return jsonObject
        }
    }

    fun serialize() {
        val gson = GsonBuilder().registerTypeHierarchyAdapter(Shape::class.java, Serializer()).setPrettyPrinting().create()
        val type: TypeToken<MutableList<Shape>> = object : TypeToken<MutableList<Shape>>() {}
        val file = File(Repository.JSON_FILE_NAME)
        if (!file.exists()) {
            file.createNewFile()
        }
        val json = gson.toJson(Repository.shapes, type.type)
        val writer = FileWriter(file)
        writer.use { it.write(json) }
        println("List was serialized")
    }

    fun deserialize() {
        val gson = GsonBuilder()
            .registerTypeHierarchyAdapter(Shape::class.java, Serializer())
            .setPrettyPrinting()
            .create()

        try {
            val file = File(Repository.JSON_FILE_NAME)
            val reader = java.io.FileReader(file)
            reader.use {
                val json = gson.fromJson(it, JsonArray::class.java)
                val shapes: MutableList<Shape> = ArrayList()
                for (element in json) {
                    val item = element.asJsonObject
                    val size = item.get("size").asInt
                    val color = item.get("color").asString
                    val name = item.get("className").asString
                    val shapeName = item.get("name").asString
                    val className = Utils.reflectAccess(name, name)
                    if (className != null) {
                        val obj = className.newInstance() as Shape
                        obj.color = color
                        obj.size = size
                        obj.name = shapeName
                        shapes.add(obj)
                    }
                }
                Repository.shapes.addAll(shapes)
                println("List was deserialized")
            }
        } catch (e: Exception) {
            Repository.reInitData()
            println("Error. Default list is loaded")
        }
    }
}