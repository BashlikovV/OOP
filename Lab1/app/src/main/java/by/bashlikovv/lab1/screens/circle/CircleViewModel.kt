package by.bashlikovv.lab1.screens.circle

import android.content.Context
import androidx.lifecycle.ViewModel
import by.bashlikovv.lab1.shapes.Circle
import by.bashlikovv.lab1.utils.BinarySerialization
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.*

class CircleViewModel(
    private val context: Context
) : ViewModel(), Serializable, BinarySerialization {

    private val _circleUiState = MutableStateFlow(Circle())
    val circleUiState = _circleUiState.asStateFlow()

    companion object {
        const val CIRCLE_FILE_NAME = "serialization.circle"
    }

    override fun getBinaryFromFile() {
        val file = File(context.filesDir, CIRCLE_FILE_NAME)
        val fileInputStream = FileInputStream(file)
        var objInputStream: ObjectInputStream? = null
        try {
            objInputStream = ObjectInputStream(fileInputStream)
            val tmp = objInputStream.readObject() as Circle
            _circleUiState.update { tmp }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            objInputStream?.close()
            fileInputStream.close()
        }
    }

    override fun saveBinaryToFile() {
        val file = File(context.filesDir, CIRCLE_FILE_NAME)
        if (!file.exists()) {
            file.createNewFile()
        }
        val fileOutputStream = FileOutputStream(file)
        val objOutputStream = ObjectOutputStream(fileOutputStream)
        val tmp = _circleUiState.value
        try {
            objOutputStream.writeObject(tmp)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            objOutputStream.close()
            fileOutputStream.close()
        }
    }

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