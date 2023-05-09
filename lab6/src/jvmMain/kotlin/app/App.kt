package app

import androidx.compose.runtime.Composable
import theme.Theme

interface App {

    val theme: Theme

    @Composable
    fun getContent(onThemeChange: () -> Unit)
}