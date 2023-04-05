package by.bashlikovv.lab1.screens.circle

import android.content.Context
import androidx.lifecycle.ViewModel
import by.bashlikovv.lab1.shapes.Circle
import by.bashlikovv.lab1.utils.BinarySerialization
import by.bashlikovv.lab1.utils.JsonSerialization
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class CircleViewModel(
    private val context: Context
) : ViewModel(), JsonSerialization, Serializable, BinarySerialization {

    private val _circleUiState = MutableStateFlow(Circle())
    val circleUiState = _circleUiState.asStateFlow()

    override val gson = Gson()

    override fun <T>String.parseJson(clazz: Class<T>): T {
        return gson.fromJson(this, clazz)
    }

    override fun <T> T.parseToJson(): String {
        return gson.toJson(this)
    }

    override fun saveJsonToFile() {
        val file = File(context.filesDir, CIRCLE_FILE_NAME)
        if (!file.exists()) {
            file.createNewFile()
        }
        val fileOutputStream = FileOutputStream(file)
        val tmp = _circleUiState.value
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
        const val CIRCLE_FILE_NAME = "serialization.circle"
    }

    override fun getJsonFromFile() {
        val file = File(context.filesDir, CIRCLE_FILE_NAME)
        if (file.exists()) {
            val fileInputStream = FileInputStream(file)
            val str = fileInputStream.readAllBytes().decodeToString()
            if (str.isNotEmpty()) {
                try {
                    val loadedState = str.parseJson(Circle::class.java)
                    _circleUiState.update { loadedState }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    fileInputStream.close()
                }
            }
        }
    }

    override fun geBinaryFromFile() {
        val file = File(context.filesDir, CIRCLE_FILE_NAME)
        val fileInputStream = FileInputStream(file)
        val objInputStream = ObjectInputStream(fileInputStream)
        try {
            val tmp = objInputStream.readObject() as Circle
            _circleUiState.update { tmp }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
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