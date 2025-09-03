package com.subhajeet.weddingplanner.ui.theme.screen.nav

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object TabScreen

    @Serializable
    object WeddingCheckListRoute

    @Serializable
    data class UpdateChecklistRoute(
        val title: String,
        val id: Int,
        val isCompleted: Boolean
    )

}
