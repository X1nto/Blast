package com.materiapps.blast.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.materiapps.blast.ui.component.*
import com.materiapps.blast.R
import com.materiapps.blast.ui.component.*
import com.materiapps.blast.ui.viewmodel.MltCard
import com.materiapps.blast.ui.viewmodel.MltViewModel


@Composable
fun MostLikelyToScreen(
    modifier: Modifier = Modifier,
    viewModel: MltViewModel = viewModel()
) {
    val stackState = rememberStackState {
        viewModel.next()
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.screen_mlt_title))
                }
            )
        },
    ) { paddingValues ->
        Stack(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = stackState,
            placeholder = {
                PlaceholderMltCard(
                    modifier = Modifier
                        .width(300.dp)
                        .height(450.dp)
                )
            }
        ) {
            when (val card = viewModel.currentCard) {
                is MltCard.Start -> {
                    WidgetStartMltCard()
                }
                is MltCard.Mlt -> {
                    WidgetMltCard(question = card.question)
                }
            }
        }
    }
}

@Composable
fun WidgetStartMltCard(
    modifier: Modifier = Modifier,
) {
    StartCard(
        modifier = modifier
            .width(300.dp)
            .height(450.dp)
    ) {
        Text(
            text = stringResource(R.string.screen_mlt_start),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun WidgetMltCard(
    modifier: Modifier = Modifier,
    question: String
) {
    MltCard(
        modifier = modifier
            .width(300.dp)
            .height(450.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = question,
            textAlign = TextAlign.Center
        )
    }
}