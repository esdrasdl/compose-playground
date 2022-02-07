package br.com.esdrasdl.playground.compose.screen1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import br.com.esdrasdl.playground.compose.SimpleScreen
import br.com.esdrasdl.playground.compose.components.SimpleTextField
import br.com.esdrasdl.playground.compose.presentation.Screen1State
import br.com.esdrasdl.playground.compose.presentation.SimpleScreenState
import br.com.esdrasdl.playground.compose.presentation.SimpleUserInteraction
import br.com.esdrasdl.playground.compose.ui.theme.ComposePlaygroundTheme

@Composable
fun Screen1(
    delegate: SimpleScreen.Delegate<SimpleScreenState>,
    viewState: Screen1State
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                delegate.onProgressUpdate(0.1f)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Screen1(
        state = viewState,
        onButtonClick = {
            delegate.handle(SimpleUserInteraction.UpdateName(it))
            delegate.openScreen(br.com.esdrasdl.playground.compose.Destinations.Password)
        },
        onTextChanged = {

        }
    )
}

@Composable
fun Screen1(
    state: Screen1State,
    onButtonClick: (String) -> Unit,
    onTextChanged: (String) -> Unit
) {
    var query by remember { mutableStateOf(TextFieldValue(state.name)) }

    Column(modifier = Modifier.padding(16.dp)) {
        SimpleTextField(
            hint = "Nome",
            value = (query),
            onValueChange = { text ->
                query = text
                onTextChanged.invoke(text.text)
                state.isButtonEnabled = text.text.isNotEmpty()
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Button(
            enabled = state.isButtonEnabled,
            onClick = {
                onButtonClick(query.text)
            }) {
            Text("Próximo")
        }
        Text("State: ${state.name}")
    }
}

@Preview
@Composable
fun PreviewScreen1() {
    ComposePlaygroundTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Screen1(Screen1State(name = "Pedro"),
                onButtonClick = {},
                onTextChanged = {}
            )
        }
    }
}