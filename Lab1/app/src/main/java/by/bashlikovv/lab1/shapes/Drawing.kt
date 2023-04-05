package by.bashlikovv.lab1.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import java.io.Serializable

data class Drawing(
    val color: Color = Color.White,
    val points: List<Offset> = listOf(),
    val input: Offset = Offset(0f, 0f),
    val isCanDrawing: Boolean = false,
    val strokeWith: Float = 1f,
) : Serializable {
    constructor() : this(Color.White)
}
