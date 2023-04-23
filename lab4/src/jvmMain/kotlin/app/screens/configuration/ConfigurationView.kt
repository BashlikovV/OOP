package app.screens.configuration

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import app.screens.circle.CircleViewModel
import app.screens.drawing.DrawingViewModel
import app.shapes.Circle
import app.shapes.Drawing

class ConfigurationView {

    private val configurationViewModel = ConfigurationViewModel()

    @Composable
    fun ConfigurationScreen(
        modifier: Modifier = Modifier
    ) {
        val configurationUiState by configurationViewModel.configurationUiState.collectAsState()

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
            ) {
                Canvas(modifier = Modifier.fillMaxWidth()) {
                    when(configurationUiState.clazz) {
                        Circle::class.java -> {
                            val circleUiState: Circle
                            val state = CircleViewModel().apply {
                                getBinaryFromFile()
                            }
                            circleUiState = state.circleUiState.value
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
                        Drawing::class.java -> {
                            val drawingUiState: Drawing
                            val state = DrawingViewModel().apply {
                                getJsonFromFile()
                            }
                            drawingUiState = state.drawingUiState.value
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
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                TextField(
                    value = configurationUiState.inputState,
                    onValueChange = {
                        configurationViewModel.onInputChange(it)
                    }
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { configurationViewModel.onOpenFile(
                    "app.shapes.${configurationUiState.inputState}"
                ) }) {
                    Text(text = "Load file")
                }
            }
        }
    }
}