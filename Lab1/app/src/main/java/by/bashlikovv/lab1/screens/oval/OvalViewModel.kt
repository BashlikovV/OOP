package by.bashlikovv.lab1.screens.oval

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import by.bashlikovv.lab1.screens.circle.CircleViewModel
import by.bashlikovv.lab1.shapes.Drawing
import by.bashlikovv.lab1.shapes.Oval
import by.bashlikovv.lab1.utils.JsonSerialization
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class OvalViewModel(
    private val context: Context
) : ViewModel(), JsonSerialization {

    private val _ovalUiState = MutableStateFlow(Oval())
    val ovalUiState = _ovalUiState.asStateFlow()

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
        val file = File(context.filesDir, OVAL_FILE_NAME)
        if (file.exists()) {
            val fileInputStream = FileInputStream(file)
            val str = fileInputStream.readAllBytes().decodeToString()
            if (str.isNotEmpty()) {
                try {
                    val loadedState = str.parseJson(Oval::class.java)
                    _ovalUiState.update { loadedState }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    fileInputStream.close()
                }
            }
        }
    }

    override fun saveJsonToFile() {
        val file = File(context.filesDir, OVAL_FILE_NAME)
        if (!file.exists()) {
            file.createNewFile()
        }
        val fileOutputStream = FileOutputStream(file)
        val tmp = _ovalUiState.value
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
        const val OVAL_FILE_NAME = "serialization.oval"
    }

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