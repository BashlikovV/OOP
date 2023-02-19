package by.bashlikovv.lab1.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

open class Circle(
    x: Float = 0f,
    y: Float = 0f,
    color: Color = Color.White,
    translateL: Float = 0f,
    translateR: Float = 0f,
    val radius: Float = 0f,
) : Square(x = x, y = y, color = color, translateL = translateL, translateR = translateR) {

    companion object {
        const val PI = 3.1415927f
    }

    val center: Offset
        get() = Offset(x, y)

    fun copy(
        x: Float = this.x,
        y: Float = this.y,
        color: Color = this.color,
        translateL: Float = this.translateL,
        translateR: Float = this.translateR,
        radius: Float = this.radius,
    ): Circle {
        return Circle(x, y, color, translateL, translateR, radius)
    }

    /**
     * [getSquare] - virtual method
     * */
    override fun getSquare() = PI * radius * radius
}