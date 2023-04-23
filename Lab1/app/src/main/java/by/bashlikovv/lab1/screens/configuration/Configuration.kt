package by.bashlikovv.lab1.screens.configuration

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.TextOverflow
import by.bashlikovv.lab1.screens.circle.CircleView
import by.bashlikovv.lab1.screens.drawing.DrawingView

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
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
    val className = Class.forName("by.bashlikovv.lab1.screens.${titles[state].first}.${titles[state].second}")
//    val loader = URLClassLoader.newInstance(arrayOf(
//        File(
//            "/home/bashlykovvv/Bsuir/OOP/Lab1/app/build/intermediates/compile_app_classes_jar/debug/classes.jar"
//        ).toURI().toURL()
//    ))

//    val filesDir = LocalContext.current.cacheDir
//    val loader = URLClassLoader.newInstance(arrayOf(
//        File(
//            filesDir,
//            "classes.jar"
//        ).toURI().toURL()
//    ))
//    val className =
//        loader.loadClass("by.bashlikovv.lab1.screens.${titles[state].first}.${titles[state].second}")

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
            when(className) {
                CircleView::class.java -> {
                    className.newInstance().CircleScreen()
                    Text("S")
                }
                DrawingView::class.java -> {
                    className.newInstance().DrawingScreen()
                    Text("D")
                }
            }
        }
    }
}