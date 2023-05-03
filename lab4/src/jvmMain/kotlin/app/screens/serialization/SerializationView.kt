package app.screens.serialization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.Repository
import app.updated_shapes.Shape
import app.utils.JsonUtils
import app.utils.Utils

class SerializationView {

    @Composable
    fun SerializationScreen(modifier: Modifier = Modifier) {

        var salt by rememberSaveable { mutableStateOf("") }
        var shapeName by rememberSaveable { mutableStateOf("") }

        Scaffold(
            modifier = modifier,
            topBar = { SerializationTopBar() }
        ) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = salt,
                    onValueChange = {
                        salt = it
                    },
                    label = { Text("Salt") }
                )
                TextField(
                    value = shapeName,
                    onValueChange = {
                        shapeName = it
                    },
                    label = { Text("Next shape name") }
                )
                Button(
                    onClick = {
                        if (Repository.type) {
                            //Json
                            Utils.convertJsonToXml()
                            JsonUtils.serialize()
                        } else {
                            //Xml
                            Utils.convertXmlToJson()
                        }
                    },
                    content = { Text("Process") }
                )
                Button(
                    onClick = {
                        Repository.shapes.add(Shape(name = shapeName))
                    },
                    content = { Text("Add shape") }
                )
            }
        }
    }

    @Composable
    fun SerializationTopBar(modifier: Modifier = Modifier.fillMaxWidth()) {
        var type by rememberSaveable { mutableStateOf(true) }
        var sd by rememberSaveable { mutableStateOf(true) }

        Row(modifier = modifier) {
            Row(
                modifier = Modifier.weight(0.5f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = type,
                    onCheckedChange = {
                        type = !type
                        Repository.type = type
                    }
                )
                Text(text = if (type) "Json" else "Xml")
            }
            Row(
                modifier = Modifier.weight(0.5f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = sd,
                    onCheckedChange = {
                        sd = !sd
                        Repository.sb = sd
                    }
                )
                Text(text = if (sd) "Serialize" else "Deserialize")
            }
        }
    }
}