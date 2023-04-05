package by.bashlikovv.lab1.shapes

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import java.io.Serializable

class Oval(
    color: Color = Color.White,
    x: Float = 0f,
    y: Float = 0f,
    width: Float = 0f,
    height: Float = 0f,
    val dimension: Size = Size(width, height),
    val scaleX: Float = 1f,
    val scaleY: Float = 1f
) : Circle(x = x, y = y, color = color), Serializable {

    constructor() : this(Color.White)

    companion object {
        const val PI = 3.1415927f
    }

    fun copy(
        color: Color = this.color,
        x: Float = this.x,
        y: Float = this.y,
        scaleX: Float = this.scaleX,
        scaleY: Float = this.scaleY,
        dimension: Size = this.dimension
    ): Oval {
        return Oval(color, x, y, scaleX =  scaleX, scaleY =  scaleY, dimension = dimension)
    }

    /**
     * [getSquare] - virtual method
     * */
    override fun getSquare() = PI * dimension.width * dimension.height
}