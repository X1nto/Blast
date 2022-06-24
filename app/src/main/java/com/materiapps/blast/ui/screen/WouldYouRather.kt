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
import com.materiapps.blast.ui.viewmodel.WyrCard
import com.materiapps.blast.ui.viewmodel.WyrViewModel


@Composable
fun WouldYouRatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WyrViewModel = viewModel()
) {
    val stackState = rememberStackState {
        viewModel.next()
    }
    Stack(
        modifier = modifier,
        state = stackState,
        placeholder = {
            PlaceholderWyrCard(
                modifier = Modifier
                    .width(300.dp)
                    .height(450.dp)
            )
        }
    ) {
        when (val card = viewModel.currentCard) {
            is WyrCard.Start -> {
                WidgetStartWyrCard()
            }
            is WyrCard.Wyr -> {
                WidgetWyrCard(
                    optionOne = card.optionOne,
                    optionTwo = card.optionTwo
                )
            }
        }
    }
}

@Composable
fun WidgetStartWyrCard(
    modifier: Modifier = Modifier,
) {
    StartCard(
        modifier = modifier
            .width(300.dp)
            .height(450.dp)
    ) {
        Text(
            text = stringResource(R.string.screen_wyr_start),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun WidgetWyrCard(
    modifier: Modifier = Modifier,
    optionOne: String,
    optionTwo: String,
) {
    WyrCard(
        modifier = modifier
            .width(300.dp)
            .height(450.dp),
        optionOne = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = optionOne,
                textAlign = TextAlign.Center
            )
        },
        optionTwo = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = optionTwo,
                textAlign = TextAlign.Center
            )
        },
    )
}