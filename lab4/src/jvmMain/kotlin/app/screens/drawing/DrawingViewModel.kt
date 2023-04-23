package app.screens.drawing

import androidx.compose.ui.geometry.Offset
import app.shapes.Drawing
import app.utils.JsonSerialization
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class DrawingViewModel : JsonSerialization {

    private val _drawingUiState = MutableStateFlow(Drawing())
    val drawingUiState = _drawingUiState.asStateFlow()

    override val gson = Gson()

    override fun <T>String.parseJson(clazz: Class<T>): T {
        return gson.fromJson(this, clazz)
    }

    override fun <T> T.parseToJson(): String {
        return gson.toJson(this)
    }

    override fun getJsonFromFile() {
        val file = File(DRAWING_FILE_NAME)
        if (file.exists()) {
            val fileInputStream = FileInputStream(file)
            val str = fileInputStream.readBytes().decodeToString()
            if (str.isNotEmpty()) {
                try {
                    val loadedState = str.parseJson(Drawing::class.java)
                    _drawingUiState.update { loadedState }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    fileInputStream.close()
                }
            }
        }
    }

    override fun saveJsonToFile() {
        val file = File(DRAWING_FILE_NAME)
        if (!file.exists()) {
            file.createNewFile()
        }
        val fileOutputStream = FileOutputStream(file)
        val tmp = drawingUiState.value
        try {
            val json = tmp.parseToJson()
            fileOutputStream.write(json.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileOutputStream.close()
        }
    }

    companion object {
        const val DRAWING_FILE_NAME = "serialization.drawing"
    }

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
}