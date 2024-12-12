package com.aliza.simiex.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliza.simiex.R
import com.aliza.simiex.data.models.Insurance
import com.aliza.simiex.data.models.Slider
import com.aliza.simiex.data.repository.HomeRepository
import com.aliza.simiex.utils.net.ParseRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            delay(300)
            getSliders()
        }
    }

    val services = listOf(
        Insurance(
            title = "شخص ثالث",
            icon = R.drawable.img_car
        ),
        Insurance(
            title = "بیمه عمر",
            icon = R.drawable.img_meditation
        ),
        Insurance(
            title = "بیمه درمانی",
            icon = R.drawable.img_health
        ),
        Insurance(
            title = "بیمه نامه",
            icon = R.drawable.img_latter
        ),
    )

    val damages = listOf(
        Insurance(
            title = "ثبت خسارت",
            icon = R.drawable.img_damage_reg
        ),
        Insurance(
            title = "درخواست ها",
            icon = R.drawable.img_requests
        ),
        Insurance(
            title = "خسارت تاهل",
            icon = R.drawable.img_marriage
        ),
        Insurance(
            title = "پرداخت شده",
            icon = R.drawable.img_paid
        ),
    )


    private val _sliderState =
        MutableStateFlow<ParseRequest<List<Slider>>>(ParseRequest.Idle())
    val sliderState: StateFlow<ParseRequest<List<Slider>>> = _sliderState
    fun getSliders() {
        viewModelScope.launch {
            _sliderState.value = ParseRequest.Loading()
            try {
                val result = repository.getSliderAds()
                _sliderState.value = result
            } catch (e: Exception) {
                _sliderState.value =
                    ParseRequest.Error("Failed to load service : ${e.localizedMessage}")
            }
        }
    }

}