package com.materiapps.blast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.materiapps.blast.ui.navigation.MiniGame
import com.materiapps.blast.ui.screen.MostLikelyToScreen
import com.materiapps.blast.ui.screen.NeverHaveIEverScreen
import com.materiapps.blast.ui.screen.TruthOrDareScreen
import com.materiapps.blast.ui.screen.WouldYouRatherScreen
import com.materiapps.blast.ui.theme.BlastTheme
import com.materiapps.blast.ui.viewmodel.MainViewModel
import com.xinto.taxi.Taxi
import com.xinto.taxi.rememberNavigator
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlastTheme {
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val bottomBarItems = MiniGame.bottomBarItems
    DismissibleNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                item {
                    ProvideTextStyle(MaterialTheme.typography.titleMedium) {
                        Text(
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 12.dp
                            ),
                            text = "MiniGames"
                        )
                    }
                }

                items(bottomBarItems) { minigame ->
                    NavigationDrawerItem(
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
                            Text(
                                text = stringResource(minigame.label),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        AnimatedContent(
                            targetState = navigator.currentDestination,
                            transitionSpec = {
                                slideIntoContainer(
                                    towards = AnimatedContentScope.SlideDirection.Up
                                ) + fadeIn() with slideOutOfContainer(
                                    towards = AnimatedContentScope.SlideDirection.Up
                                ) + fadeOut()
                            },
                            contentAlignment = Alignment.Center
                        ) { destination ->
                            Text(stringResource(destination.label))
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            val menuIcon = painterResource(R.drawable.ic_menu)
                            val menuOpenIcon = painterResource(R.drawable.ic_menu_open)
                            Icon(
                                painter = if (drawerState.isOpen) menuOpenIcon else menuIcon,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
        ) { paddingValues ->
            Taxi(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                navigator = navigator,
                transitionSpec = { fadeIn() with fadeOut() }
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
}