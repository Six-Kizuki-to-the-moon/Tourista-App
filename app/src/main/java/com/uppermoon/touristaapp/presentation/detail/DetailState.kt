package com.uppermoon.touristaapp.presentation.detail

import com.uppermoon.touristaapp.data.network.response.RecommendationsItem

data class DetailState (
    val isLoading : Boolean = false,
    val similiar: List<RecommendationsItem> = emptyList(),
    val error: String = ""
)