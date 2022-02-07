package br.com.esdrasdl.playground.compose.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.esdrasdl.playground.compose.getStateFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SimpleViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val interactions = Channel<SimpleUserInteraction>(Channel.UNLIMITED)

    private val viewModelState: MutableStateFlow<SimpleScreenState> =
        savedStateHandle.getStateFlow(
            viewModelScope,
            "state",
            SimpleScreenState()
        )

    val uiState = viewModelState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        viewModelState.value
    )

    init {
        viewModelScope.launch {
            interactions.consumeAsFlow().collect { event ->
                when (event) {
                    is SimpleUserInteraction.LoadScreen -> {
                        if (viewModelState.value == SimpleScreenState()) {
                            viewModelState.update {
                                it.copy(screen1State = it.screen1State.copy(name = "admin", isButtonEnabled = true))
                            }
                        }
                    }
                    is SimpleUserInteraction.UpdateName -> {
                        viewModelState.update {
                            it.copy(screen1State = it.screen1State.copy(name = event.name))
                        }
                    }
                    is SimpleUserInteraction.UpdatePassword -> {
                        viewModelState.update {
                            it.copy(screen2State = it.screen2State.copy(password = event.password))
                        }
                    }
                }
            }
        }
        handle(SimpleUserInteraction.LoadScreen())
    }

    fun handle(event: SimpleUserInteraction) {
        viewModelScope.launch {
            interactions.send(event)
        }
    }
}



