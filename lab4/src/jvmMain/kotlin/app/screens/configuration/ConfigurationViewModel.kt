package app.screens.configuration

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.net.URLClassLoader

class ConfigurationViewModel {

    private val _configurationUiState = MutableStateFlow(ConfigurationUiState())
    val configurationUiState = _configurationUiState.asStateFlow()

    fun onOpenFile(className: String) {
        val dialog = FileDialog(
            Frame(),
            "Select input file",
            FileDialog.LOAD
        )
        dialog.isVisible = true
        val path = dialog.directory + dialog.file

        val file = File(path)
        val loader = URLClassLoader(arrayOf(file.toURI().toURL()))
        val tmp = loader.loadClass(className)
        _configurationUiState.update { it.copy(clazz = tmp) }
    }

    fun onInputChange(newValue: String) {
        _configurationUiState.update { it.copy(inputState = newValue) }
    }
}