package app.updated_shapes

import app.updated_shapes.Shape

class SquareShape(
    color: String = "Red",
    name: String = "Square",
    size: Int = 500
) : Shape(color = color, name = name, size = size)