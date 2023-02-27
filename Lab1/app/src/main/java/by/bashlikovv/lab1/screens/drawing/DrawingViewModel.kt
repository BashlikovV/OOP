package by.bashlikovv.lab1.screens.drawing

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import by.bashlikovv.lab1.shapes.Drawing
import com.godaddy.android.colorpicker.HsvColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DrawingViewModel : ViewModel() {

    private val _drawingUiState = MutableStateFlow(Drawing())
    val drawingUiState = _drawingUiState.asStateFlow()

    fun onDraw(newValue: Offset) {
        val newPoints = _drawingUiState.value.points.toMutableList().apply {
            add(newValue)
        }
        _drawingUiState.update { it.copy(input = newValue, points = newPoints) }
        if (_drawingUiState.value.points.size > 1) {
            _drawingUiState.update { it.copy(isCanDrawing = true) }
        }
    }

    fun onClear() {
        _drawingUiState.update { it.copy(points = emptyList(), input = Offset(0f, 0f)) }
    }

    fun onStrokeWidthChange(newValue: Float) {
        _drawingUiState.update { it.copy(strokeWith = newValue) }
    }

    fun onColorChange(newValue: HsvColor) {
        _drawingUiState.update { it.copy(color = newValue.toColor()) }
    }
}