package by.bashlikovv.lab1.screens.square

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import by.bashlikovv.lab1.shapes.Square
import by.bashlikovv.lab1.utils.JsonSerialization
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SquareViewModel(
    private val context: Context
) : ViewModel(), JsonSerialization {

    private val _squareUiState = MutableStateFlow(Square())
    val squareUiState = _squareUiState.asStateFlow()

    override val gson = Gson()

    override fun <T>String.parseJson(clazz: Class<T>): T {
        return gson.fromJson(this, clazz)
    }

    override fun <T> T.parseToJson(): String {
        return gson.toJson(this)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun getJsonFromFile() {
        val file = File(context.filesDir, SQUARE_FILE_NAME)
        if (file.exists()) {
            val fileInputStream = FileInputStream(file)
            val str = fileInputStream.readBytes().decodeToString()
            if (str.isNotEmpty()) {
                try {
                    val loadedState = str.parseJson(Square::class.java)
                    _squareUiState.update { loadedState }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    fileInputStream.close()
                }
            }
        }
    }

    override fun saveJsonToFile() {
        val file = File(context.filesDir, SQUARE_FILE_NAME)
        if (!file.exists()) {
            file.createNewFile()
        }
        val fileOutputStream = FileOutputStream(file)
        val tmp = _squareUiState.value
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
        const val SQUARE_FILE_NAME = "serialization.square"
    }

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