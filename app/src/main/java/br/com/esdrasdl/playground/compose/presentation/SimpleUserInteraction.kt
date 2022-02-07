package br.com.esdrasdl.playground.compose.presentation

sealed class SimpleUserInteraction {
    class UpdateName(val name: String) : SimpleUserInteraction()
    class UpdatePassword(val password: String) : SimpleUserInteraction()
    class LoadScreen() : SimpleUserInteraction()
}