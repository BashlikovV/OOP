package by.bashlikovv.lab1.screens.configuration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import by.bashlikovv.lab1.shapes.Circle
import by.bashlikovv.lab1.shapes.Drawing
import by.bashlikovv.lab1.shapes.Oval
import by.bashlikovv.lab1.shapes.Square

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigurationScreen(
    modifier: Modifier = Modifier
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Circle", "Drawing", "Oval", "Square")
    val className = Class.forName("by.bashlikovv.lab1.shapes.${titles[state]}")

    Scaffold(
        modifier = modifier,
        topBar = {
            TabRow(state) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = state == index,
                        onClick = { state = index },
                        text = {
                            Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                    )
                }
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            when (className) {
                Circle::class.java -> {
                    Text("Circle")
                    val clazz = className.newInstance()
                }
                Drawing::class.java -> {
                    Text("Draw")
                }
                Oval::class.java -> {
                    Text("Oval")
                }
                Square::class.java -> {
                    Text("Square")
                }
            }
        }
    }
}