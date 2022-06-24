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
import com.materiapps.blast.ui.viewmodel.NhieCard
import com.materiapps.blast.ui.viewmodel.NhieViewModel


@Composable
fun NeverHaveIEverScreen(
    modifier: Modifier = Modifier,
    viewModel: NhieViewModel = viewModel()
) {
    val stackState = rememberStackState {
        viewModel.next()
    }
    Stack(
        modifier = modifier,
        state = stackState,
        placeholder = {
            PlaceholderNhieCard(
                modifier = Modifier
                    .width(300.dp)
                    .height(450.dp)
            )
        }
    ) {
        when (val card = viewModel.currentCard) {
            is NhieCard.Start -> {
                WidgetStartNhieCard()
            }
            is NhieCard.Nhie -> {
                WidgetNhieCard(question = card.question)
            }
        }
    }
}

@Composable
fun WidgetStartNhieCard(
    modifier: Modifier = Modifier,
) {
    StartCard(
        modifier = modifier
            .width(300.dp)
            .height(450.dp)
    ) {
        Text(
            text = stringResource(R.string.screen_nhie_start),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun WidgetNhieCard(
    modifier: Modifier = Modifier,
    question: String
) {
    NhieCard(
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