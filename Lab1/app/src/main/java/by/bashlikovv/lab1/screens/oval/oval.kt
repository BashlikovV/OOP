package by.bashlikovv.lab1.screens.oval

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import by.bashlikovv.lab1.shapes.Oval
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp

@Composable
fun OvalScreen(
    modifier: Modifier = Modifier,
    ovalViewModel: OvalViewModel = viewModel()
) {
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