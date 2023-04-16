package by.bashlikovv.lab1

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import by.bashlikovv.lab1.screens.circle.CircleScreen
import by.bashlikovv.lab1.screens.configuration.ConfigurationScreen
import by.bashlikovv.lab1.screens.drawing.DrawingScreen
import by.bashlikovv.lab1.screens.oval.OvalScreen
import by.bashlikovv.lab1.screens.square.SquareScreen

enum class Screens {
    SQUARE,
    CIRCLE,
    OVAL,
    DRAWING,
    CONFIGURATION
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainActivityContent(
    modifier: Modifier = Modifier,
    mainActivityViewModel: MainActivityViewModel = viewModel()
) {
    val navHostController = rememberNavController()

    val mainActivityUiState by mainActivityViewModel.mainActivityUiState.collectAsState()
    val screenWidth = LocalConfiguration.current.screenWidthDp

    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colors.background
    ) {
        NavigationBarItem(
            selected = mainActivityUiState.selectedItem.name == Screens.SQUARE.name,
            onClick = {
                mainActivityViewModel.onSelectItem(Screens.SQUARE)
                navHostController.navigate(Screens.SQUARE.name)
            },
            icon = { Text(text = "SQUARE", modifier = Modifier.padding(2.5.dp)) },
            modifier = Modifier.width((screenWidth / 5).dp)
        )
        NavigationBarItem(
            selected = mainActivityUiState.selectedItem.name == Screens.CIRCLE.name,
            onClick = {
                mainActivityViewModel.onSelectItem(Screens.CIRCLE)
                navHostController.navigate(Screens.CIRCLE.name)

            },
            icon = { Text(text = "CIRCLE", modifier = Modifier.padding(2.5.dp)) },
            modifier = Modifier.width((screenWidth / 5).dp)
        )
        NavigationBarItem(
            selected = mainActivityUiState.selectedItem.name == Screens.OVAL.name,
            onClick = {
                mainActivityViewModel.onSelectItem(Screens.OVAL)
                navHostController.navigate(Screens.OVAL.name)
            },
            icon = { Text(text = "OVAL", modifier = Modifier.padding(2.5.dp)) },
            modifier = Modifier.width((screenWidth / 5).dp)
        )
        NavigationBarItem(
            selected = mainActivityUiState.selectedItem.name == Screens.DRAWING.name,
            onClick = {
                mainActivityViewModel.onSelectItem(Screens.DRAWING)
                navHostController.navigate(Screens.DRAWING.name)
            },
            icon = { Text(text = "DRAWING", modifier = Modifier.padding(2.5.dp)) },
            modifier = Modifier.width((screenWidth / 5).dp)
        )
        NavigationBarItem(
            selected = mainActivityUiState.selectedItem.name == Screens.CONFIGURATION.name,
            onClick = {
                mainActivityViewModel.onSelectItem(Screens.CONFIGURATION)
                navHostController.navigate(Screens.CONFIGURATION.name)
            },
            icon = { Text(text = "CONFIGURATION", modifier = Modifier.padding(2.5.dp)) },
            modifier = Modifier.width((screenWidth / 5).dp)
        )
    }
    NavHost(
        navController = navHostController,
        startDestination = Screens.SQUARE.name,
        modifier = Modifier.padding(top = 75.dp)
    ) {
        composable(Screens.SQUARE.name) {
            SquareScreen(modifier = Modifier.fillMaxWidth())
        }
        composable(Screens.CIRCLE.name) {
            CircleScreen(modifier = Modifier.fillMaxWidth())
        }
        composable(Screens.OVAL.name) {
            OvalScreen(modifier = Modifier.fillMaxWidth())
        }
        composable(Screens.DRAWING.name) {
            DrawingScreen(modifier = Modifier.fillMaxWidth())
        }
        composable(Screens.CONFIGURATION.name) {
            ConfigurationScreen(modifier = Modifier.fillMaxWidth())
        }
    }
}