package br.com.esdrasdl.playground.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import br.com.esdrasdl.playground.compose.presentation.SharedViewModel
import br.com.esdrasdl.playground.compose.presentation.SimpleScreenState
import br.com.esdrasdl.playground.compose.presentation.SimpleUserInteraction
import br.com.esdrasdl.playground.compose.presentation.SimpleViewModel
import br.com.esdrasdl.playground.compose.ui.theme.ComposePlaygroundTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    private val progressViewModel: SharedViewModel by viewModel()
    private val simpleViewModel by stateViewModel<SimpleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePlaygroundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberAnimatedNavController()
                    val delegate = object :
                        SimpleScreen.Delegate<SimpleScreenState> {
                        override fun onProgressUpdate(progress: Float) {
                            progressViewModel.updateProgress(progress)
                        }

                        override fun progressState(): StateFlow<Float> {
                            return progressViewModel.progress
                        }

                        override fun openScreen(route: String) {
                            navController.navigate(route)
                        }

                        override fun handle(event: SimpleUserInteraction) {
                            simpleViewModel.handle(event)
                        }
                    }
                    val viewState by simpleViewModel.uiState.collectAsState()

                    NavigationGraph(
                        navController,
                        viewState,
                        delegate
                    )
                }
            }
        }
    }
}

interface SimpleScreen {
    interface Delegate<T> {
        fun onProgressUpdate(progress: Float)
        fun progressState(): StateFlow<Float>
        fun openScreen(route: String)
        fun handle(event: SimpleUserInteraction)
    }
}
