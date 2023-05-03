package app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import app.screens.circle.CircleView
import app.screens.configuration.ConfigurationView
import app.screens.drawing.DrawingView
import app.screens.serialization.SerializationView
import app.updated_shapes.CircleShape
import app.updated_shapes.Shape

@Composable
fun MainActivityContent(
    modifier: Modifier = Modifier
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf(
        "circle" to "CircleView",
        "drawing" to "DrawingView",
        "configuration" to "ConfigurationView",
        "serialization" to "SerializationView"
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TabRow(state) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = state == index,
                        onClick = { state = index },
                        text = {
                            Text(text = title.first, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                    )
                }
            }
        }
    ) {
        Box(modifier = modifier) {
            when(state) {
                0 -> {
                    CircleView().CircleScreen(modifier = Modifier.fillMaxSize())
                }
                1 -> {
                    DrawingView().DrawingScreen(modifier = Modifier.fillMaxSize())
                }
                2 -> {
                    ConfigurationView().ConfigurationScreen(modifier = Modifier.fillMaxSize())
                }
                3 -> {
                    SerializationView().SerializationScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}