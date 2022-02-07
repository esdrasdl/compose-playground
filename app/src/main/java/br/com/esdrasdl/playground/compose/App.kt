package br.com.esdrasdl.playground.compose

import android.app.Application
import android.content.Context
import br.com.esdrasdl.playground.compose.presentation.SharedViewModel
import br.com.esdrasdl.playground.compose.presentation.SimpleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        startKoin {
            androidContext(base)
        }
    }

    override fun onCreate() {
        super.onCreate()
        loadKoinModules(module)
    }

    private val module = module {
        viewModel {
            SharedViewModel()
        }
        viewModel {
            SimpleViewModel(savedStateHandle = get())
        }
    }
}