package app

import androidx.compose.material.*
import androidx.compose.runtime.*
import theme.Theme

class AppImpl(override val theme: Theme) : App {

    private val titles = listOf("Menu", "Order", "Account", "Card")

    @Composable
    override fun getContent(onThemeChange: () -> Unit) {
        var state by remember { mutableStateOf(0) }
        var appContent: AppContent? by remember { mutableStateOf(null) }

        LaunchedEffect(Unit) {
            appContent = AppContent(ArrayList())
        }

        Scaffold(
            topBar = {
                TabRow(
                    selectedTabIndex = state,
                    backgroundColor = theme.primaryColor,
                    contentColor = theme.secondaryColor
                ) {
                    titles.forEachIndexed {index, title ->
                        Tab(
                            selected = state == index,
                            onClick = { state = index },
                            text = { Text(text = title) }
                        )
                    }
                }
            },
            floatingActionButton = {
                Button(
                    onClick = { onThemeChange() },
                    content = { Text("ChangeTheme") },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = theme.primaryColor,
                        contentColor = theme.secondaryColor
                    )
                )
            },
            backgroundColor = theme.backgroundColor
        ) {
            if (appContent != null) {
                appContent!!.getContent()
            }
        }
    }
}