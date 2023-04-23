package app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import app.screens.circle.CircleView
import app.screens.configuration.ConfigurationView
import by.bashlikovv.lab1.screens.drawing.DrawingView

@Composable
fun MainActivityContent(
    modifier: Modifier = Modifier
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf(
        "circle" to "CircleView",
        "drawing" to "DrawingView",
        "configuration" to "ConfigurationView"
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
            }
        }
    }
}