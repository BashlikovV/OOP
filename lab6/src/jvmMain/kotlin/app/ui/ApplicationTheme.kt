package app.ui

import androidx.compose.ui.graphics.Color
import theme.Theme

object ApplicationTheme {
    val themes = listOf(
        object : Theme {
            override val primaryColor: Color
                get() = Color.Blue
            override val secondaryColor: Color
                get() = Color.Green
            override val backgroundColor: Color
                get() = Color.White
        },
        object : Theme {
            override val primaryColor: Color
                get() = Color.LightGray
            override val secondaryColor: Color
                get() = Color.Blue
            override val backgroundColor: Color
                get() = Color.White

        },
        object : Theme {
            override val primaryColor: Color
                get() = Color.Black
            override val secondaryColor: Color
                get() = Color.LightGray
            override val backgroundColor: Color
                get() = Color.White

        }
    )
}