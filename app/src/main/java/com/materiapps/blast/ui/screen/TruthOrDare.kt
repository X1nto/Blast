package com.materiapps.blast.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.materiapps.blast.ui.component.*
import com.materiapps.blast.R
import com.materiapps.blast.ui.viewmodel.TodCard
import com.materiapps.blast.ui.viewmodel.TodViewModel
import kotlinx.coroutines.launch

@Composable
fun TruthOrDareScreen(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: TodViewModel = viewModel()
) {
    val stackState = rememberStackState {
        when (it) {
            StackSwipeValue.None -> {}
            StackSwipeValue.Right -> {
                viewModel.nextTruth()
            }
            StackSwipeValue.Left -> {
                viewModel.nextDare()
            }
        }
    }
    val coroutineScope = rememberCoroutineScope()
    WidgetAdaptiveStackLayout(
        modifier = modifier,
        windowSizeClass = windowSizeClass,
        onTruthClick = {
            coroutineScope.launch {
                stackState.swipeToRight()
            }
        },
        onDareClick = {
            coroutineScope.launch {
                stackState.swipeToLeft()
            }
        }
    ) {
        Stack(
            modifier = Modifier.fillMaxSize(),
            state = stackState,
            placeholder = {
                PlaceholderTodCard(
                    modifier = Modifier
                        .width(300.dp)
                        .height(450.dp)
                )
            }
        ) {
            when (val card = viewModel.currentCard) {
                is TodCard.Start -> {
                    WidgetStartTodCard()
                }
                is TodCard.Truth -> {
                    WidgetTodCard(
                        title = stringResource(R.string.screen_tod_truth),
                        body = card.truth
                    )
                }
                is TodCard.Dare -> {
                    WidgetTodCard(
                        title = stringResource(R.string.screen_tod_dare),
                        body = card.dare
                    )
                }
            }
        }
    }
}

@Composable
fun WidgetAdaptiveStackLayout(
    onTruthClick: () -> Unit,
    onDareClick: () -> Unit,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    content()
                }
                WidgetHorizontalStackControls(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 12.dp
                        ),
                    onTruthClick = onTruthClick,
                    onDareClick = onDareClick
                )
            }
        }
        WindowWidthSizeClass.Medium -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedButton(
                    modifier = Modifier.padding(start = 16.dp),
                    onClick = onDareClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_left),
                        contentDescription = stringResource(R.string.screen_tod_dare)
                    )
                    Text(stringResource(R.string.screen_tod_dare))
                }
                Box(modifier = Modifier.weight(1f)) {
                    content()
                }
                ElevatedButton(
                    modifier = Modifier.padding(end = 16.dp),
                    onClick = onTruthClick
                ) {
                    Text(stringResource(R.string.screen_tod_truth))
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = stringResource(R.string.screen_tod_truth)
                    )
                }
            }
        }
        WindowWidthSizeClass.Expanded -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    content()
                }
                WidgetVerticalStackControls(
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(
                            start = 4.dp,
                            end = 12.dp,
                        ),
                    onTruthClick = onTruthClick,
                    onDareClick = onDareClick
                )
            }
        }
    }
}

@Composable
private fun WidgetHorizontalStackControls(
    onTruthClick: () -> Unit,
    onDareClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .clip(MaterialTheme.shapes.large),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ControlButton(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            onClick = onDareClick,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = stringResource(R.string.screen_tod_dare)
            )
            Text(stringResource(R.string.screen_tod_dare))
        }
        ControlButton(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            onClick = onTruthClick,
        ) {
            Text(stringResource(R.string.screen_tod_truth))
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = stringResource(R.string.screen_tod_truth)
            )
        }
    }
}

@Composable
private fun WidgetVerticalStackControls(
    onTruthClick: () -> Unit,
    onDareClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .height(108.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ControlButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            onClick = onDareClick,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = stringResource(R.string.screen_tod_dare)
            )
            Text(stringResource(R.string.screen_tod_dare))
        }
        ControlButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            onClick = onTruthClick,
        ) {
            Text(stringResource(R.string.screen_tod_truth))
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = stringResource(R.string.screen_tod_truth)
            )
        }
    }
}

@Composable
private fun ControlButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    ElevatedButton(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.extraSmall,
    ) {
        ProvideTextStyle(MaterialTheme.typography.titleMedium) {
            content()
        }
    }
}

@Composable
private fun WidgetStartTodCard(
    modifier: Modifier = Modifier
) {
    StartCard(
        modifier = modifier
            .width(300.dp)
            .height(450.dp)
    ) {
        Text(
            text = stringResource(R.string.screen_tod_start),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun WidgetTodCard(
    modifier: Modifier = Modifier,
    title: String,
    body: String
) {
    TodCard(
        modifier = modifier
            .width(300.dp)
            .height(450.dp),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = TextAlign.Center
            )
        },
        body = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = body,
                textAlign = TextAlign.Center
            )
        }
    )
}