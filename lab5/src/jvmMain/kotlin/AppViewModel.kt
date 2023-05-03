import app.Repository
import app.utils.JsonUtils
import encoder.Encoder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import shapes.CircleShape
import shapes.SquareShape
import utils.Utils
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.util.*
import javax.swing.JOptionPane

class AppViewModel {

    private val _appUiState = MutableStateFlow(AppUiState())
    val appUiState = _appUiState.asStateFlow()

    private val encoder = Encoder()

    companion object {
        const val POLYNOMIAL_POWER = 38
    }

    fun onSelectFileClicked(field: Int) {
        val dialog = FileDialog(
            Frame(),
            if (field == 0) "Select input file" else "Select output file",
            if (field == 0) FileDialog.LOAD else FileDialog.SAVE
        )
        dialog.directory = "/home/bashlykovvv/Downloads/"
        dialog.isVisible = true
        val path = dialog.directory + dialog.file

        when (field) {
            0 -> {
                val file = File(path)

                if (!file.exists()) {
                    showMessage("Input file does not exists")
                } else {
                    _appUiState.update { it.copy(inputFile = file) }
                }
            }
            1 -> {
                val file = File(path)

                if (!file.exists()) {
                    file.createNewFile()
                }

                _appUiState.update { it.copy(outputFile = file) }
            }
        }
    }

    private fun preprocess(str: String): String {
        val upper = str.uppercase(Locale.getDefault())
        return upper.filter { it in "01" }
    }

    fun onProcessClicked(startValue: String) {
        val outputPath = _appUiState.value.outputFile.absolutePath
        val inputPath = _appUiState.value.inputFile.absolutePath
        val key = preprocess(startValue)

        if (inputPath.isEmpty() || outputPath.isEmpty() || key.length != POLYNOMIAL_POWER) {
            showMessage("Please fill in all the fields")
            return
        }

        encoder.encode(
            polynomialPowers = intArrayOf(POLYNOMIAL_POWER, 6, 5, 1),
            initialKey = key,
            pathToSrcFile = _appUiState.value.inputFile.absolutePath,
            pathToResFile = _appUiState.value.outputFile.absolutePath
        )

        showMessage("Success")
    }

    private fun showMessage(message: String) {
        JOptionPane.showMessageDialog(Frame(), message)
    }

    fun onProcessParserClicked(mode: Boolean) {
        if (mode) {
            Utils.convertXmlToJson()
        } else {
            Utils.convertJsonToXml()
        }
    }

    fun onAddCircle() {
        Repository.shapes.add(CircleShape(
            color = "Random color ${(0..10).random()}",
            name = "Random name ${(0..10).random()}",
            size = (0..1000).random()
        ))
        JsonUtils.serialize()
    }

    fun onAddSquare() {
        Repository.shapes.add(SquareShape(
            color = "Random color ${(0..10).random()}",
            name = "Random name ${(0..10).random()}",
            size = (0..1000).random()
        ))
        JsonUtils.serialize()
    }
}