package com.uppermoon.touristaapp.presentation.home

import com.uppermoon.touristaapp.data.network.response.DestinationResponseItem

data class HomeState(
    val isLoading : Boolean = false,
    val destination: List<DestinationResponseItem> = emptyList(),
    val error: String = ""
)
