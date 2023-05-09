package app

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import app.utils.IObservable
import app.utils.IObserver
import javax.swing.JButton
import javax.swing.JFrame

class AppContent(override var observers: ArrayList<IObserver>) : IObservable {

    private var param = "default value"
        set(value) {
            field = value
            sendUpdateEvent(value)
        }

    private  val frame = JFrame().apply {
        setBounds(200, 20, 200, 200)
        title = ""
        isVisible = true
    }

    private val testValue = object : IObserver {
        var data = "empty string"
        val btn = JButton().apply {
            text = "Remove"
        }

        override fun update(value: String) {
            data = value
            if (!frame.isVisible) {
                frame.isVisible = true
                frame.add(btn, 0)
            }
            frame.title = value
        }
    }

    init {
        observers.add(testValue)
    }

    @Composable
    fun getContent() {
        var inputState by remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxHeight(0.2f)) {
                TextField(
                    value = inputState,
                    onValueChange = {
                        inputState = it
                        param = it
                    }
                )
            }
            Row(modifier = Modifier.fillMaxHeight(0.2f)) {
                Button(
                    onClick = {
                        observers.add(object : IObserver {
                            val ref = this
                            val btn = JButton().apply {
                                text = "remove"
                                addActionListener {
                                    observers.remove(ref)
                                }
                            }

                            val frame = JFrame().apply {
                                setBounds(200, 20, 200, 200)
                                add(btn)
                                title = ""
                                isVisible = true
                            }
                            var data = "empty string"

                            override fun update(value: String) {
                                data = value
                                if (!frame.isVisible) {
                                    frame.isVisible = true
                                }
                                frame.title = value
                            }
                        })
                    },
                    content = {
                        Text("Add frame")
                    }
                )
            }
        }
    }
}