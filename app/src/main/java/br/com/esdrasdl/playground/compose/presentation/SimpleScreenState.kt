package br.com.esdrasdl.playground.compose.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleScreenState(
    var screen1State: Screen1State = Screen1State(),
    var screen2State: Screen2State = Screen2State(),
) : Parcelable

@Parcelize
data class Screen1State(
    var name: String = "",
    var isButtonEnabled: Boolean = name.isNotEmpty()
) : Parcelable

@Parcelize
data class Screen2State(
    var password: String = "",
    var isButtonEnabled: Boolean = false
) : Parcelable
