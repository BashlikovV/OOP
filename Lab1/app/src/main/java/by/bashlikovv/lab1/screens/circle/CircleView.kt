package by.bashlikovv.lab1.screens.circle

import androidx.activity.ComponentActivity
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
import by.bashlikovv.lab1.utils.viewModelCreator

class CircleView {
    @Composable
    fun CircleScreen(
        modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current as ComponentActivity
        val circleViewModel: CircleViewModel by context.viewModelCreator {
            CircleViewModel(context)
        }

        LaunchedEffect(Unit) {
            circleViewModel.applyCircle(by.bashlikovv.lab1.shapes.Circle(
                x = 150f,
                y = 150f,
                color = Color.Green,
                radius = 50f
            ))
            circleViewModel.getBinaryFromFile()
        }

        DisposableEffect(Unit) {
            DisposableEffectScope().onDispose {
                circleViewModel.saveBinaryToFile()
            }
        }

        val circleUiState by circleViewModel.circleUiState.collectAsState()

        Column (modifier = modifier) {
            Canvas(modifier = Modifier.height(500.dp)) {
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