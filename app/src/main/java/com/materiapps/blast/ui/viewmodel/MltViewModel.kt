package com.materiapps.blast.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

sealed interface MltCard {
    object Start : MltCard
    data class Mlt(val question: String) : MltCard
}

class MltViewModel : ViewModel() {

    var currentCard by mutableStateOf<MltCard>(MltCard.Start)
        private set

    fun next() {
        currentCard = MltCard.Mlt("Something controversial here")
    }

}