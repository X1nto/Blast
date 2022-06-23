package com.materiapps.blast.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

sealed interface NhieCard {
    object Start : NhieCard
    data class Nhie(val question: String) : NhieCard
}

class NhieViewModel : ViewModel() {

    var currentCard by mutableStateOf<NhieCard>(NhieCard.Start)
        private set

    fun next() {
        currentCard = NhieCard.Nhie("This will be replaced by something funny someday")
    }

}