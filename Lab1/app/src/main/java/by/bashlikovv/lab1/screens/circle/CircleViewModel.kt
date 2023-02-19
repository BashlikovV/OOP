package by.bashlikovv.lab1.screens.circle

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import by.bashlikovv.lab1.shapes.Circle
import kotlinx.coroutines.flow.update

class CircleViewModel : ViewModel() {

    private val _circleUiState = MutableStateFlow(Circle())
    val circleUiState = _circleUiState.asStateFlow()

    fun applyCircle(circle: Circle) {
        _circleUiState.update {
            circle
        }
    }

    fun onTranslateRChange(newValue: Float) {
        _circleUiState.update {
            it.copy(translateR = newValue, radius = it.radius)
        }
    }

    fun onTranslateLChange(newValue: Float) {
        _circleUiState.update {
            it.copy(translateL = newValue, radius = it.radius)
        }
    }

    fun onRadiusChange(newValue: Float) {
        _circleUiState.update {
            it.copy(radius = newValue)
        }
    }
}