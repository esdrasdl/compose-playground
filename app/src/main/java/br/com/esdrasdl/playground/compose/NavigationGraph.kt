package br.com.esdrasdl.playground.compose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.esdrasdl.playground.compose.presentation.SimpleScreenState
import br.com.esdrasdl.playground.compose.screen1.Screen1
import br.com.esdrasdl.playground.compose.screen2.Screen2
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewState: SimpleScreenState,
    delegate: SimpleScreen.Delegate<SimpleScreenState>,
    startDestination: String = Destinations.Home
) {
    val progressState = delegate.progressState().collectAsState()
    val animatedProgress = animateFloatAsState(
        targetValue = progressState.value,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        ProgressBar(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            percent = animatedProgress.value
        )

        AnimatedNavHost(
            navController = navController,
            startDestination = startDestination,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                )
            }
        ) {
            composable(Destinations.Home) { Screen1(delegate, viewState.screen1State) }
            composable(Destinations.Password) { Screen2(delegate, viewState.screen2State) }
        }
    }
}