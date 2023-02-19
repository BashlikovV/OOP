package by.bashlikovv.lab1.screens.oval

import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import by.bashlikovv.lab1.shapes.Oval
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OvalViewModel : ViewModel() {

    private val _ovalUiState = MutableStateFlow(Oval())
    val ovalUiState = _ovalUiState.asStateFlow()

    fun applyOval(oval: Oval) {
        _ovalUiState.update { oval }
    }

    fun onScaleXChange(newValue: Float) {
        _ovalUiState.update { it.copy(scaleX = newValue) }
    }

    fun onScaleYChange(newValue: Float) {
        _ovalUiState.update { it.copy(scaleY = newValue) }
    }

    fun onHeightChange(newHeight: Float) {
        _ovalUiState.update { it.copy(dimension = Size(width = it.dimension.width, height = newHeight)) }
    }

    fun onWidthChange(newWidth: Float) {
        _ovalUiState.update { it.copy(dimension = Size(width = newWidth, height = it.dimension.height)) }
    }
}