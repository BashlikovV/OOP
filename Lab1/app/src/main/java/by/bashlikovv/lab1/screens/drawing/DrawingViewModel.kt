package by.bashlikovv.lab1.screens.drawing

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import by.bashlikovv.lab1.shapes.Drawing
import by.bashlikovv.lab1.utils.JsonSerialization
import com.godaddy.android.colorpicker.HsvColor
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class DrawingViewModel(
    private val context: Context
) : ViewModel(), JsonSerialization {

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
        val file = File(context.filesDir, DRAWING_FILE_NAME)
        if (file.exists()) {
            val fileInputStream = FileInputStream(file)
            val str = fileInputStream.readAllBytes().decodeToString()
            if (str.isNotEmpty()) {
                try {
                    val loadedState = str.parseJson(Drawing::class.java)
                    _drawingUiState.update { loadedState }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun saveJsonToFile() {
        val file = File(context.filesDir, DRAWING_FILE_NAME)
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

    fun onColorChange(newValue: HsvColor) {
        _drawingUiState.update { it.copy(color = newValue.toColor()) }
    }
}