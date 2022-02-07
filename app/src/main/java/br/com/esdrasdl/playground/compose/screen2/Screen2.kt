package br.com.esdrasdl.playground.compose.screen2

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
import br.com.esdrasdl.playground.compose.components.PasswordTextField
import br.com.esdrasdl.playground.compose.presentation.Screen2State
import br.com.esdrasdl.playground.compose.presentation.SimpleScreenState
import br.com.esdrasdl.playground.compose.presentation.SimpleUserInteraction
import br.com.esdrasdl.playground.compose.ui.theme.ComposePlaygroundTheme

@Composable
fun Screen2(
    delegate: SimpleScreen.Delegate<SimpleScreenState>,
    viewState: Screen2State
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                delegate.onProgressUpdate(0.6f)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Screen2(
        state = viewState,
        onButtonClick = {
            delegate.handle(SimpleUserInteraction.UpdatePassword(it))
        },
        onTextChanged = {

        }
    )
}

@Composable
fun Screen2(
    state: Screen2State,
    onButtonClick: (String) -> Unit,
    onTextChanged: (String) -> Unit
) {
    var password by remember { mutableStateOf(TextFieldValue(state.password)) }

    Column(modifier = Modifier.padding(16.dp)) {
        PasswordTextField(
            hint = "Senha",
            value = password,
            onValueChange = { text ->
                password = text
                onTextChanged.invoke(text.text)
                state.isButtonEnabled = text.text.isNotEmpty()
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Button(
            enabled = state.isButtonEnabled,
            onClick = {
                onButtonClick(password.text)
            }) {
            Text("Autenticar")
        }
        Text("State: ${state.password}")
    }
}

@Preview
@Composable
fun PreviewScreen2() {
    ComposePlaygroundTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Screen2(Screen2State(),
                onButtonClick = {},
                onTextChanged = {})
        }
    }
}