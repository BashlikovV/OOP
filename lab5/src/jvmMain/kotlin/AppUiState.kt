import java.io.File

data class AppUiState(
    val inputFile: File = File(""),
    val outputFile: File = File("")
)
