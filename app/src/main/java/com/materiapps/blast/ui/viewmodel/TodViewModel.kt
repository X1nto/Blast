package com.materiapps.blast.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

sealed interface TodCard {
    object Start : TodCard
    data class Truth(val truth: String) : TodCard
    data class Dare(val dare: String) : TodCard
}

class TodViewModel : ViewModel() {

    var currentCard by mutableStateOf<TodCard>(TodCard.Start)
        private set

    fun nextTruth() {
        currentCard = TodCard.Truth("Hello this is a test truth")
    }

    fun nextDare() {
        currentCard = TodCard.Dare("Hello this is a test dare")
    }

}