package by.bashlikovv.lab1.screens.oval

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
import by.bashlikovv.lab1.shapes.Oval
import by.bashlikovv.lab1.utils.viewModelCreator

@Composable
fun OvalScreen(
    modifier: Modifier = Modifier
) {
    val contex = LocalContext.current as ComponentActivity
    val ovalViewModel: OvalViewModel by contex.viewModelCreator {
        OvalViewModel(contex)
    }

    LaunchedEffect(Unit) {
        ovalViewModel.applyOval(Oval(
            color = Color.Green,
            width = 50f,
            height = 100f,
            x = 150f,
            y = 150f,
            scaleX = 1f,
            scaleY = 1f
        ))
        ovalViewModel.getJsonFromFile()
    }
    DisposableEffect(Unit) {
        DisposableEffectScope().onDispose {
            ovalViewModel.saveJsonToFile()
        }
    }

    val ovalUiState by ovalViewModel.ovalUiState.collectAsState()

    Column (modifier = modifier) {
        Canvas(modifier = Modifier.height(500.dp)) {
            withTransform({
                translate(left = ovalUiState.translateL, top = ovalUiState.translateR)
                scale(scaleX = ovalUiState.scaleX, scaleY = ovalUiState.scaleY)
            }) {
                drawOval(
                    color = ovalUiState.color,
                    topLeft = ovalUiState.getTopLeft(),
                    size = ovalUiState.dimension
                )
            }
        }
        Column {
            Text(text = "ScaleX: ")
            Slider(
                value = ovalUiState.scaleX,
                onValueChange = {
                    ovalViewModel.onScaleXChange(it)
                },
                valueRange = (0f..1f),
                onValueChangeFinished = {
                    ovalViewModel.onScaleXChange(ovalUiState.scaleX)
                },
                steps = 10
            )
        }
        Column {
            Text(text = "ScaleY: ")
            Slider(
                value = ovalUiState.scaleY,
                onValueChange = {
                    ovalViewModel.onScaleYChange(it)
                },
                valueRange = (0f..1f),
                onValueChangeFinished = {
                    ovalViewModel.onScaleYChange(ovalUiState.scaleY)
                },
                steps = 10
            )
        }
        Column {
            Text(text = "Height: ")
            Slider(
                value = ovalUiState.dimension.height,
                onValueChange = {
                    ovalViewModel.onHeightChange(it)
                },
                valueRange = (0f..360f),
                onValueChangeFinished = {
                    ovalViewModel.onHeightChange(ovalUiState.dimension.height)
                }
            )
        }
        Column {
            Text(text = "Width: ")
            Slider(
                value = ovalUiState.dimension.width,
                onValueChange = {
                    ovalViewModel.onWidthChange(it)
                },
                valueRange = (0f..360f),
                onValueChangeFinished = {
                    ovalViewModel.onWidthChange(ovalUiState.dimension.width)
                }
            )
        }
    }
}