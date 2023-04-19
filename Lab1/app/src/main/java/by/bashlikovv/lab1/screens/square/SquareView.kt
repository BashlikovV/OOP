package by.bashlikovv.lab1.screens.square

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import by.bashlikovv.lab1.shapes.Square
import by.bashlikovv.lab1.utils.viewModelCreator

class SquareView {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun SquareScreen(
        modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current as ComponentActivity
        val squareViewModel: SquareViewModel by context.viewModelCreator {
            SquareViewModel(context)
        }
        LaunchedEffect(Unit) {
            squareViewModel.applySquare(
                Square(
                    x = 150f,
                    y = 150f,
                    side = 450f,
                    color = Color.Green
                )
            )
            squareViewModel.getJsonFromFile()
        }
        DisposableEffect(Unit) {
            DisposableEffectScope().onDispose {
                squareViewModel.saveJsonToFile()
            }
        }
        val squareUiState by squareViewModel.squareUiState.collectAsState()

        Column (modifier = modifier) {
            Canvas(modifier = Modifier.height(500.dp)) {
                withTransform({
                    rotate(degrees = squareUiState.rotate)
                    translate(left = squareUiState.translateL, top = squareUiState.translateR)
                }) {
                    drawRect(
                        color = squareUiState.color,
                        topLeft = squareUiState.getTopLeft(),
                        size = squareUiState.getSize()
                    )
                }
            }
            Column {
                Text(text = "Rotation: ")
                Slider(
                    value = squareUiState.rotate,
                    onValueChange = {
                        squareViewModel.onRotateChange(it)
                    },
                    valueRange = (0f..360f),
                    onValueChangeFinished = {
                        squareViewModel.onRotateChange(squareUiState.rotate)
                    }
                )
            }
            Column {
                Text(text = "TranslationR: ")
                Slider(
                    value = squareUiState.translateR,
                    onValueChange = {
                        squareViewModel.onTranslateRChange(it)
                    },
                    valueRange = (-75f..500f),
                    onValueChangeFinished = {
                        squareViewModel.onTranslateRChange(squareUiState.translateR)
                    }
                )
            }
            Column {
                Text(text = "TranslationL: ")
                Slider(
                    value = squareUiState.translateL,
                    onValueChange = {
                        squareViewModel.onTranslateLChange(it)
                    },
                    valueRange = (0f..500f),
                    onValueChangeFinished = {
                        squareViewModel.onTranslateLChange(squareUiState.translateL)
                    }
                )
            }
        }
    }
}