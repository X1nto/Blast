package com.materiapps.blast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.materiapps.blast.ui.navigation.MiniGame
import com.materiapps.blast.ui.screen.MostLikelyToScreen
import com.materiapps.blast.ui.screen.NeverHaveIEverScreen
import com.materiapps.blast.ui.screen.TruthOrDareScreen
import com.materiapps.blast.ui.screen.WouldYouRatherScreen
import com.materiapps.blast.ui.theme.TruthOrDareTheme
import com.materiapps.blast.ui.viewmodel.MainViewModel
import com.xinto.taxi.Taxi
import com.xinto.taxi.rememberNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TruthOrDareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val navigator = rememberNavigator<MiniGame>(initial = MiniGame.TruthOrDare)
    val bottomBarItems = MiniGame.bottomBarItems
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                for (minigame in bottomBarItems) {
                    NavigationBarItem(
                        selected = navigator.currentDestination == minigame,
                        onClick = { navigator.replace(minigame) },
                        icon = {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(minigame.icon),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(stringResource(minigame.label))
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Taxi(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navigator = navigator,
            transitionSpec = {
                val initialIndex = bottomBarItems.indexOf(initialState)
                val targetIndex = bottomBarItems.indexOf(targetState)

                if (initialIndex < targetIndex) {
                    slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.Start
                    ) with slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.Start
                    )
                } else {
                    slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.End
                    ) with slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.End
                    )
                }
            }
        ) {
            when (it) {
                is MiniGame.TruthOrDare -> {
                    TruthOrDareScreen(modifier = Modifier.fillMaxSize())
                }
                is MiniGame.NeverHaveIEver -> {
                    NeverHaveIEverScreen(modifier = Modifier.fillMaxSize())
                }
                is MiniGame.WouldYouRather -> {
                    WouldYouRatherScreen(modifier = Modifier.fillMaxSize())
                }
                is MiniGame.MostLikelyTo -> {
                    MostLikelyToScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}