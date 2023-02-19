package by.bashlikovv.lab1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityViewModel : ViewModel() {

    private val _mainActivityUiState = MutableStateFlow(MainActivityUiState())
    val mainActivityUiState = _mainActivityUiState.asStateFlow()

    fun onSelectItem(newItem: Screens) {
        _mainActivityUiState.update { currentState ->
            currentState.copy(
                selectedItem = newItem
            )
        }
    }
}