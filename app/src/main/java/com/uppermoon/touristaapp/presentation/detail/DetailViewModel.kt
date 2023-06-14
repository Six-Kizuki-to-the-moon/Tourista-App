package com.uppermoon.touristaapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.DestinationResult
import kotlinx.coroutines.flow.onEach

class DetailViewModel(private val destinationRepository: DestinationRepository,): ViewModel() {

    private val _listSimiliar = MutableLiveData<DetailState>()
    val listSimiliar : LiveData<DetailState> = _listSimiliar

    fun postSimiliarItem(destinationName: String) {
        destinationRepository.postSimiliarItem(destinationName).onEach {
            when(it) {
                is DestinationResult.Success -> {
                    _listSimiliar.value = DetailState(similiar = it.data)
                }
                is DestinationResult.Loading -> {
                    _listSimiliar.value = DetailState(isLoading = true)
                }
                is DestinationResult.Error -> {
                    _listSimiliar.value = DetailState(error = it.error)
                }
            }
        }
    }
}