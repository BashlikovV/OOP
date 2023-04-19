package by.bashlikovv.lab1.screens.configuration

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import by.bashlikovv.lab1.screens.circle.CircleView
import by.bashlikovv.lab1.screens.drawing.DrawingView
import by.bashlikovv.lab1.screens.oval.OvalView
import by.bashlikovv.lab1.screens.square.SquareView
import java.io.File
import java.net.URLClassLoader

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigurationScreen(
    modifier: Modifier = Modifier
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf(
        "circle" to "CircleView",
        "drawing" to "DrawingView",
        "oval" to "OvalView",
        "square" to "SquareView"
    )
    val pluginFile = File(
        "/home/bashlykovvv/Bsuir/OOP/Lab1/app/src/main/java/by/bashlikovv/lab1/screens.jar"
    )
    val className = try {
        Class.forName("by.bashlikovv.lab1.screens.${titles[state].first}.${titles[state].second}")
    } catch (e: Exception) {
        val classLoader = URLClassLoader(arrayOf(pluginFile.toURI().toURL()))
        classLoader.loadClass("screens.${titles[state].first}.${titles[state].second}")
    }

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
        Column(modifier = Modifier.padding(it)) {
            when (className) {
                CircleView::class.java -> {
                    className.newInstance().CircleScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                DrawingView::class.java -> {
                    className.newInstance().DrawingScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                OvalView::class.java -> {
                    className.newInstance().OvalScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                SquareView::class.java -> {
                    className.newInstance().SquareScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}