import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun App(appViewModel: AppViewModel) {
    MaterialTheme {
        Scaffold {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxHeight(0.5f)) {
                    EncoderView(appViewModel = appViewModel)
                }
                Row(modifier = Modifier.fillMaxHeight(0.5f)) {
                    ParserView(appViewModel = appViewModel)
                }
            }
        }
    }
}

@Composable
fun EncoderView(appViewModel: AppViewModel) {
    val appUiState by appViewModel.appUiState.collectAsState()
    var startValueState by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Input path",
                modifier = Modifier.weight(0.1f)
            )
            TextField(
                value = appUiState.inputFile.absolutePath,
                onValueChange = { },
                readOnly = true,
                singleLine = true,
                modifier = Modifier.weight(0.7f)
            )
            Button(
                onClick = { appViewModel.onSelectFileClicked(0) },
                content = { Text("Select file") },
                modifier = Modifier.weight(0.2f)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Output path",
                modifier = Modifier.weight(0.1f)
            )
            TextField(
                value = appUiState.outputFile.absolutePath,
                onValueChange = { },
                readOnly = true,
                singleLine = true,
                modifier = Modifier.weight(0.7f)
            )
            Button(
                onClick = { appViewModel.onSelectFileClicked(1) },
                content = { Text("Select file") },
                modifier = Modifier.weight(0.2f)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Start value",
                modifier = Modifier.weight(0.1f)
            )
            TextField(
                value = startValueState,
                onValueChange = {
                    startValueState = it
                },
                singleLine = true,
                modifier = Modifier.weight(0.7f)
            )
            Button(
                onClick = { appViewModel.onProcessClicked(startValueState) },
                content = { Text("Process") },
                modifier = Modifier.weight(0.2f)
            )
        }
    }
}

@Composable
fun ParserView(appViewModel: AppViewModel) {
    var jeState by rememberSaveable { mutableStateOf(true) }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = jeState,
                onCheckedChange = { jeState = !jeState }
            )
            Button(
                onClick = { appViewModel.onAddCircle() },
                content = { Text("add Circle") },
                modifier = Modifier.weight(0.2f)
            )
            Button(
                onClick = { appViewModel.onAddSquare() },
                content = { Text("add Square") },
                modifier = Modifier.weight(0.2f)
            )
            Button(
                onClick = { appViewModel.onProcessParserClicked(jeState) },
                content = { Text(if (jeState) "to Json" else "to Xml") },
                modifier = Modifier.weight(0.2f)
            )
        }
    }
}