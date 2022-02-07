package br.com.esdrasdl.playground.compose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    val progress: MutableStateFlow<Float> = MutableStateFlow(0f)

    fun updateProgress(newProgress: Float) {
        viewModelScope.launch {
            progress.emit(newProgress)
        }
    }
}