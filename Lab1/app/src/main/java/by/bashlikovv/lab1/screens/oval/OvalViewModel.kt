package by.bashlikovv.lab1.screens.oval

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import by.bashlikovv.lab1.shapes.Oval
import by.bashlikovv.lab1.utils.TextSerialization
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.*
import java.util.*

class OvalViewModel(
    private val context: Context
) : ViewModel(), Serializable, TextSerialization {

    private val _ovalUiState = MutableStateFlow(Oval())
    val ovalUiState = _ovalUiState.asStateFlow()

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTextFromFile() {
        val file = File(context.filesDir, OVAL_FILE_NAME)
        val fileInputStream = FileInputStream(file)
        try {
            val tmp = fileInputStream.readAllBytes().decodeToString()
            val state = fromString(tmp) as Oval
            _ovalUiState.update { state }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileInputStream.close()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun saveTextToFile() {
        val file = File(context.filesDir, OVAL_FILE_NAME)
        if (!file.exists()) {
            file.createNewFile()
        }
        val fileOutputStream = FileOutputStream(file)
        val tmp = _ovalUiState.value
        try {
            fileOutputStream.write(toString(tmp).encodeToByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileOutputStream.close()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun toString(obj: Serializable): String {
        val result: String
        val outputStream = ByteArrayOutputStream()
        var objectOutputStream: ObjectOutputStream? = null

        try {
            objectOutputStream = ObjectOutputStream(outputStream)
            objectOutputStream.writeObject(obj)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            result = Base64.getEncoder().encodeToString(outputStream.toByteArray())
            objectOutputStream?.close()
            outputStream.close()
        }

        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun fromString(str: String): Any {
        val data = Base64.getDecoder().decode(str)
        val byteArrayInputStream = ByteArrayInputStream(data)
        var objectInputStream: ObjectInputStream? = null
        val result: Any?

        try {
            objectInputStream = ObjectInputStream(byteArrayInputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            result = objectInputStream?.readObject()
            objectInputStream?.close()
            byteArrayInputStream.close()
        }

        return result ?: Oval()
    }
}