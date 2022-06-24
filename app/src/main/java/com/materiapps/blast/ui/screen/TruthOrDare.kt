package com.materiapps.blast.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Stack(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
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
        WidgetStackControls(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 12.dp
            ),
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
        )
    }
}

@Composable
private fun WidgetStackControls(
    onTruthClick: () -> Unit,
    onDareClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
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