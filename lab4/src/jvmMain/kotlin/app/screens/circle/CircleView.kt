package app.screens.circle

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import app.shapes.Circle

class CircleView {

    private var circleViewModel: CircleViewModel = CircleViewModel()

    @Composable
    fun CircleScreen(
        modifier: Modifier = Modifier
    ) {

        LaunchedEffect(Unit) {
            circleViewModel.applyCircle(
                Circle(
                    x = 150f,
                    y = 150f,
                    color = Color.Green,
                    radius = 50f
                )
            )
            circleViewModel.getBinaryFromFile()
        }

        DisposableEffect(Unit) {
            DisposableEffectScope().onDispose {
                circleViewModel.saveBinaryToFile()
            }
        }

        val circleUiState by circleViewModel.circleUiState.collectAsState()

        Column(modifier = modifier) {
            Canvas(modifier = Modifier.height(300.dp)) {
                withTransform({
                    rotate(degrees = circleUiState.rotate)
                    translate(left = circleUiState.translateL, top = circleUiState.translateR)
                }) {
                    drawCircle(
                        color = circleUiState.color,
                        radius = circleUiState.radius,
                        center = circleUiState.center
                    )
                }
            }
            Column(
                content = {
                    Text(text = "TranslationR: ")
                    Slider(
                        value = circleUiState.translateR,
                        onValueChange = {
                            circleViewModel.onTranslateRChange(it)
                        },
                        valueRange = (-75f..500f),
                        onValueChangeFinished = {
                            circleViewModel.onTranslateRChange(circleUiState.translateR)
                        },
                    )
                },
            )
            Column {
                Text(text = "TranslationL: ")
                Slider(
                    value = circleUiState.translateL,
                    onValueChange = {
                        circleViewModel.onTranslateLChange(it)
                    },
                    valueRange = (0f..500f),
                    onValueChangeFinished = {
                        circleViewModel.onTranslateLChange(circleUiState.translateL)
                    }
                )
            }
            Column {
                Text(text = "Radius: ")
                Slider(
                    value = circleUiState.radius,
                    onValueChange = {
                        circleViewModel.onRadiusChange(it)
                    },
                    valueRange = (0f..500f),
                    onValueChangeFinished = {
                        circleViewModel.onRadiusChange(circleUiState.radius)
                    }
                )
            }
        }
    }
}