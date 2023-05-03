package app

import app.utils.JsonUtils
import shapes.CircleShape
import shapes.EllipseShape
import shapes.Shape
import shapes.SquareShape

object Repository {

    const val XML_FILE_NAME = "file.xml"
    const val JSON_FILE_NAME = "file.json"

    val shapes = mutableListOf<Shape>()

    init {
        shapes.apply {
            add(CircleShape())
            add(SquareShape())
            add(EllipseShape())
        }
        JsonUtils.serialize()
    }

    fun reInitData() {
        shapes.apply {
            clear()
            add(CircleShape())
            add(SquareShape())
            add(EllipseShape())
        }
    }

    var type = true
    var sb = true
}