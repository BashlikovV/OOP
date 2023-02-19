package by.bashlikovv.lab1.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

open class Square(
    val x: Float = 0f,
    val y: Float = 0f,
    val side: Float = 0f,
    val color: Color = Color.White,
    val rotate: Float = 0f,
    val translateR: Float = 0f,
    val translateL: Float = 0f
) : Any() {

    fun getTopLeft(): Offset {
        return Offset(this.x, this.y)
    }

    fun getSize(): Size {
        return Size(this.side, this.side)
    }

    fun copy(
        x: Float = this.x,
        y: Float = this.y,
        side: Float = this.side,
        color: Color = this.color,
        rotate: Float = this.rotate,
        translateR: Float = this.translateR,
        translateL: Float = this.translateL
    ): Square {
        return Square(x, y, side, color, rotate, translateR, translateL)
    }

    /**
     * [getSquare] - virtual method
     * */
    open fun getSquare() = side * side
}