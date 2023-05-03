package app.screens.drawing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

class DrawingView {

    private val drawingViewModel = DrawingViewModel()

    @Composable
    fun DrawingScreen(
        modifier: Modifier = Modifier
    ) {
        LaunchedEffect(Unit) {
            drawingViewModel.getJsonFromFile()
        }

        DisposableEffect(Unit) {
            DisposableEffectScope().onDispose {
                drawingViewModel.saveJsonToFile()
            }
        }

        val drawingUiState by drawingViewModel.drawingUiState.collectAsState()

        Column(modifier = modifier) {
            Canvas(
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectDragGestures { change, _ -> drawingViewModel.onDraw(change.position) }
                    }
            ) {
                if (drawingUiState.isCanDrawing) {
                    for (i in 1 until drawingUiState.points.size) {
                        drawLine(
                            color = Color.Black,
                            start = drawingUiState.points[i - 1],
                            end = drawingUiState.points[i],
                            strokeWidth = drawingUiState.strokeWith
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { drawingViewModel.onClear() }) {
                    Text(text = "Clear")
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Slider(
                    value = drawingUiState.strokeWith,
                    onValueChange = { drawingViewModel.onStrokeWidthChange(it) },
                    valueRange = 1f..50f
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = drawingUiState.points.size.toString())
            }
        }
    }
}