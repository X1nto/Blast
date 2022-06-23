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
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.screen_tod_title)) }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                    .height(56.dp)
                    .clip(MaterialTheme.shapes.large),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                ElevatedButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    onClick = {
                        coroutineScope.launch {
                            stackState.swipeToLeft()
                        }
                    },
                    shape = MaterialTheme.shapes.extraSmall,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_left),
                        contentDescription = stringResource(R.string.screen_tod_dare)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.screen_tod_dare))
                }
                ElevatedButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    onClick = {
                        coroutineScope.launch {
                            stackState.swipeToRight()
                        }
                    },
                    shape = MaterialTheme.shapes.extraSmall,
                ) {
                    Text(stringResource(R.string.screen_tod_truth))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = stringResource(R.string.screen_tod_truth)
                    )
                }
            }
        }
    ) { paddingValues ->
        Stack(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
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