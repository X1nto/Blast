package com.materiapps.blast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
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
                    val windowSizeClass = calculateWindowSizeClass(this)
                    MainScreen(
                        modifier = Modifier.fillMaxSize(),
                        windowSizeClass = windowSizeClass
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    viewModel: MainViewModel = viewModel()
) {
    val navigator = rememberNavigator<MiniGame>(initial = MiniGame.TruthOrDare)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    WidgetAdaptiveDrawer(
        windowSizeClass = windowSizeClass,
        drawerState = drawerState,
        drawerContent = {
            WidgetDrawerContent(
                onMinigameClick = {
                    navigator.replace(it)
                },
                currentDestination = navigator.currentDestination
            )
        }
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                WidgetTopAppBar(
                    onMenuClick = {
                        coroutineScope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    },
                    topBarLabel = stringResource(navigator.currentDestination.label),
                    isDrawerOpen = drawerState.isOpen,
                    showMenuIcon = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Expanded
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
                        TruthOrDareScreen(
                            windowSizeClass = windowSizeClass,
                            modifier = Modifier.fillMaxSize()
                        )
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

@Composable
private fun WidgetAdaptiveDrawer(
    windowSizeClass: WindowSizeClass,
    drawerContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    drawerShape: Shape = Shapes.None,
    drawerTonalElevation: Dp = DrawerDefaults.DismissibleDrawerElevation,
    drawerContainerColor: Color = MaterialTheme.colorScheme.surface,
    drawerContentColor: Color = contentColorFor(drawerContainerColor),
    content: @Composable () -> Unit
) {
    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
        PermanentNavigationDrawer(
            drawerContent = drawerContent,
            modifier = modifier,
            drawerShape = drawerShape,
            drawerTonalElevation = drawerTonalElevation,
            drawerContainerColor = drawerContainerColor,
            drawerContentColor = drawerContentColor,
            content = content
        )
    } else {
        DismissibleNavigationDrawer(
            drawerContent = drawerContent,
            modifier = modifier,
            drawerState = drawerState,
            gesturesEnabled = gesturesEnabled,
            drawerShape = drawerShape,
            drawerTonalElevation = drawerTonalElevation,
            drawerContainerColor = drawerContainerColor,
            drawerContentColor = drawerContentColor,
            content = content
        )
    }
}

@Composable
private fun WidgetDrawerContent(
    onMinigameClick: (MiniGame) -> Unit,
    currentDestination: MiniGame,
    modifier: Modifier = Modifier,
) {
    val bottomBarItems = MiniGame.bottomBarItems
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        item {
            ProvideTextStyle(MaterialTheme.typography.titleSmall) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                    text = stringResource(R.string.drawer_headline_minigames),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(bottomBarItems) { minigame ->
            NavigationDrawerItem(
                selected = currentDestination == minigame,
                onClick = { onMinigameClick(minigame) },
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

@Composable
private fun WidgetTopAppBar(
    onMenuClick: () -> Unit,
    topBarLabel: String,
    isDrawerOpen: Boolean,
    showMenuIcon: Boolean,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            AnimatedContent(
                targetState = topBarLabel,
                transitionSpec = {
                    slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.Up
                    ) + fadeIn() with slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.Up
                    ) + fadeOut()
                },
                contentAlignment = Alignment.Center
            ) { label ->
                Text(label)
            }
        },
        navigationIcon = {
            if (showMenuIcon) {
                IconButton(onClick = onMenuClick) {
                    val menuIcon = painterResource(R.drawable.ic_menu)
                    val menuOpenIcon = painterResource(R.drawable.ic_menu_open)
                    Icon(
                        painter = if (isDrawerOpen) menuOpenIcon else menuIcon,
                        contentDescription = null
                    )
                }
            }
        }
    )
}