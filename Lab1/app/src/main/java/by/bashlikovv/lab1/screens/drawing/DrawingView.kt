package by.bashlikovv.lab1.screens.drawing

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import by.bashlikovv.lab1.utils.viewModelCreator
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor

class DrawingView {

    @Composable
    fun DrawingScreen(
        modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current as ComponentActivity
        val drawingViewModel: DrawingViewModel by context.viewModelCreator {
            DrawingViewModel(context)
        }

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
                modifier = modifier
                    .height(500.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, _ -> drawingViewModel.onDraw(change.position) }
                    }
            ) {
                if (drawingUiState.isCanDrawing) {
                    for (i in 1 until drawingUiState.points.size) {
                        drawLine(
                            color = drawingUiState.color,
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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ClassicColorPicker(
                    showAlphaBar = false,
                    modifier = Modifier.height(100.dp),
                    color = HsvColor.from(drawingUiState.color)
                ) {
                    drawingViewModel.onColorChange(it)
                }
            }
        }
    }
}