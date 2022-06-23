package com.materiapps.blast.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.materiapps.blast.R


@Composable
fun StartCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ElevatedCard(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}


@Composable
fun TodCard(
    title: @Composable () -> Unit,
    body: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProvideTextStyle(MaterialTheme.typography.headlineSmall) {
                title()
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                    body()
                }
            }
        }
    }
}


@Composable
fun PlaceholderTodCard(
    modifier: Modifier = Modifier
) {
    ElevatedCard(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(96.dp),
                painter = painterResource(R.drawable.ic_question_mark),
                contentDescription = null
            )
        }
    }
}


@Composable
fun NhieCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ElevatedCard(modifier = modifier) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                content()
            }
        }
    }
}


@Composable
fun PlaceholderNhieCard(
    modifier: Modifier = Modifier,
) {
    ElevatedCard(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(96.dp),
                painter = painterResource(R.drawable.ic_question_mark),
                contentDescription = null
            )
        }
    }
}


@Composable
fun WyrCard(
    modifier: Modifier = Modifier,
    optionOne: @Composable () -> Unit,
    optionTwo: @Composable () -> Unit,
) {
    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProvideTextStyle(MaterialTheme.typography.bodyLarge) {
                optionOne()
            }
            Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp))
            ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                optionTwo()
            }
        }
    }
}


@Composable
fun PlaceholderWyrCard(
    modifier: Modifier = Modifier,
) {
    val questionMarkIcon = painterResource(R.drawable.ic_question_mark)
    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(72.dp),
                painter = questionMarkIcon,
                contentDescription = null
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            Icon(
                modifier = Modifier
                    .size(72.dp)
                    .rotate(180f),
                painter = questionMarkIcon,
                contentDescription = null
            )
        }
    }
}


@Composable
fun MltCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ElevatedCard(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                content()
            }
        }
    }
}


@Composable
fun PlaceholderMltCard(
    modifier: Modifier = Modifier,
) {
    ElevatedCard(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(96.dp),
                painter = painterResource(R.drawable.ic_question_mark),
                contentDescription = null
            )
        }
    }
}