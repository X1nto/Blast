package com.materiapps.blast.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

sealed interface WyrCard {
    object Start : WyrCard
    data class Wyr(
        val optionOne: String,
        val optionTwo: String
    ) : WyrCard
}

class WyrViewModel : ViewModel() {

    var currentCard by mutableStateOf<WyrCard>(WyrCard.Start)
        private set

    fun next() {
        currentCard = WyrCard.Wyr(
            optionOne = "Hi this is a bad thing",
            optionTwo = "This is an even worse thing"
        )
    }

}