package app

import app.updated_shapes.CircleShape
import app.updated_shapes.EllipseShape
import app.updated_shapes.Shape
import app.updated_shapes.SquareShape
import app.utils.JsonUtils
import app.utils.Utils

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