package app.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import java.io.Serializable

open class Circle(
    x: Float = 0f,
    y: Float = 0f,
    color: Color = Color.White,
    translateL: Float = 0f,
    translateR: Float = 0f,
    val center: Offset = Offset(x, y),
    val radius: Float = 0f,
) : Square(x = x, y = y, color = color, translateL = translateL, translateR = translateR), Serializable {

    constructor() : this(0f, 0f, Color.White, 0f, 0f, Offset(0f, 0f),0f)

    companion object {
        const val PI = 3.1415927f
    }

    fun copy(
        x: Float = this.x,
        y: Float = this.y,
        color: Color = this.color,
        translateL: Float = this.translateL,
        translateR: Float = this.translateR,
        radius: Float = this.radius,
        center: Offset = this.center
    ): Circle {
        return Circle(x, y, color, translateL, translateR, center, radius)
    }

    /**
     * [getSquare] - virtual method
     * */
    override fun getSquare() = PI * radius * radius
}