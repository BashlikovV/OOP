package by.bashlikovv.lab1.screens.square

import androidx.lifecycle.ViewModel
import by.bashlikovv.lab1.shapes.Square
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SquareViewModel : ViewModel() {

    private val _squareUiState = MutableStateFlow(Square())
    val squareUiState = _squareUiState.asStateFlow()

    fun onRotateChange(newValue: Float) {
        _squareUiState.update {
            it.copy(rotate = newValue)
        }
    }

    fun applySquare(square: Square) {
        _squareUiState.update {
            square
        }
    }

    fun onTranslateRChange(x: Float) {
        _squareUiState.update {
            it.copy(translateR = x)
        }
    }

    fun onTranslateLChange(x: Float) {
        _squareUiState.update {
            it.copy(translateL = x)
        }
    }
}