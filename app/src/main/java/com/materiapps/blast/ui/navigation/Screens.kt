package com.materiapps.blast.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.materiapps.blast.R
import com.xinto.taxi.Destination
import kotlinx.parcelize.Parcelize

sealed class MiniGame(
    @StringRes
    val label: Int,
    
    @DrawableRes
    val icon: Int
) : Destination {

    @Parcelize
    object TruthOrDare : MiniGame(
        label = R.string.screen_tod_title,
        icon = R.drawable.ic_description
    )

    @Parcelize
    object NeverHaveIEver : MiniGame(
        label = R.string.screen_nhie_title,
        icon = R.drawable.ic_close
    )

    @Parcelize
    object WouldYouRather : MiniGame(
        label = R.string.screen_wyr_title,
        icon = R.drawable.ic_compare
    )

    @Parcelize
    object MostLikelyTo : MiniGame(
        label = R.string.screen_mlt_title,
        icon = R.drawable.ic_pan
    )

    companion object {
        val bottomBarItems by lazy {
            listOf(TruthOrDare, NeverHaveIEver, WouldYouRather, MostLikelyTo)
        }
    }
}