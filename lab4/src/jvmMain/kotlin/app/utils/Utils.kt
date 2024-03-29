package app.utils

import app.Repository
import app.updated_shapes.Shape
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.w3c.dom.Node
import java.io.File
import java.net.URLClassLoader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

object Utils {

    fun convertXmlToJson() {
        val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val doc = docBuilder.parse(File(Repository.XML_FILE_NAME))
        doc.documentElement.normalize()

        val jsonArray = mutableListOf<JsonObject>()

        val objectNodes = doc.getElementsByTagName("object")
        for (i in 0 until objectNodes.length) {
            val objectNode = objectNodes.item(i) as org.w3c.dom.Element
            val jsonObject = JsonObject()

            val propertyNodes = objectNode.childNodes
            for (j in 0 until propertyNodes.length) {
                val propertyNode = propertyNodes.item(j)
                if (propertyNode.nodeType == Node.ELEMENT_NODE) {
                    val propertyName = propertyNode.nodeName
                    var propertyValue: Any? = null
                    when (propertyNode.childNodes.length) {
                        1 -> {
                            val textContent = propertyNode.textContent
                            propertyValue = if (textContent == "true" || textContent == "false") {
                                textContent.toBoolean()
                            } else {
                                try {
                                    textContent.toInt()
                                } catch (e: NumberFormatException) {
                                    try {
                                        textContent.toDouble()
                                    } catch (e: NumberFormatException) {
                                        textContent
                                    }
                                }
                            }
                        }

                        else -> parseList(propertyNode)
                    }
                    when (propertyValue) {
                        is Boolean -> jsonObject.addProperty(propertyName, propertyValue)
                        is Char -> jsonObject.addProperty(propertyName, propertyValue)
                        is Number -> jsonObject.addProperty(propertyName, propertyValue)
                        is String -> jsonObject.addProperty(propertyName, propertyValue)
                    }
                }
            }

            jsonArray.add(jsonObject)
        }

        val gson = GsonBuilder().registerTypeHierarchyAdapter(Shape::class.java, JsonUtils.Serializer()).setPrettyPrinting().create()
        File(Repository.JSON_FILE_NAME).writeText(gson.toJson(jsonArray))
    }

    private fun parseList(node: Node): List<Any> {
        val list = mutableListOf<Any>()

        val itemNodes = node.childNodes
        for (i in 0 until itemNodes.length) {
            val itemNode = itemNodes.item(i)
            if (itemNode.nodeType == Node.ELEMENT_NODE && itemNode.nodeName == "item") {
                val textContent = itemNode.textContent
                val itemValue = try {
                    textContent.toInt()
                } catch (e: NumberFormatException) {
                    try {
                        textContent.toDouble()
                    } catch (e: NumberFormatException) {
                        textContent
                    }
                }
                list.add(itemValue)
            }
        }

        return list
    }

    fun convertJsonToXml() {
        val gson = GsonBuilder().registerTypeHierarchyAdapter(Shape::class.java, JsonUtils.Serializer()).setPrettyPrinting().create()
        val jsonArray = gson.fromJson(File(Repository.JSON_FILE_NAME).readText(), Array<Any>::class.java)

        val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val doc = docBuilder.newDocument()

        val rootElement = doc.createElement("root")
        doc.appendChild(rootElement)

        jsonArray.forEach { jsonObject ->
            val objectElement = doc.createElement("object")
            rootElement.appendChild(objectElement)

            jsonObject as Map<*, *>
            jsonObject.forEach { (key, value) ->
                val propertyElement = doc.createElement(key.toString())
                objectElement.appendChild(propertyElement)

                when (value) {
                    is String -> propertyElement.appendChild(doc.createTextNode(value))
                    is Number -> propertyElement.appendChild(doc.createTextNode(value.toString()))
                    is Boolean -> propertyElement.appendChild(doc.createTextNode(value.toString()))
                    is List<*> -> value.forEach { item ->
                        val itemElement = doc.createElement("item")
                        itemElement.appendChild(doc.createTextNode(item.toString()))
                        propertyElement.appendChild(itemElement)
                    }

                    is Map<*, *> -> value.forEach { (subKey, subValue) ->
                        val subElement = doc.createElement(subKey.toString())
                        propertyElement.appendChild(subElement)

                        if (subValue is List<*>) {
                            subValue.forEach { item ->
                                val itemElement = doc.createElement("item")
                                itemElement.appendChild(doc.createTextNode(item.toString()))
                                subElement.appendChild(itemElement)
                            }
                        } else {
                            subElement.appendChild(doc.createTextNode(subValue.toString()))
                        }
                    }
                }
            }
        }

        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("indent", "yes")
        transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4")
        transformer.transform(DOMSource(doc), StreamResult(File(Repository.XML_FILE_NAME)))
    }

    fun reflectAccess(path1: String, path2: String): Class<*>? {
        var className: Class<*>? = null

        try {
            className = Class.forName(path1)
        } catch (e: Exception) {
            println("Searching for the plugin")
            try {
                val pluginFile = File("/home/bashlykovvv/Bsuir/OOP/lab4/src/jvmMain/kotlin/app/plugin.jar")
                val classLoader = URLClassLoader(arrayOf(pluginFile.toURI().toURL()))
                className = classLoader.loadClass(path2)
            } catch (e: Exception) {
                println("The content wasn't found")
            }
        }
        return className
    }
}