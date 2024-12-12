package com.aliza.simiex.data.repository

import com.aliza.simiex.data.models.Insurance
import com.aliza.simiex.data.models.Slider
import com.aliza.simiex.data.net.HomeParse
import com.aliza.simiex.utils.constants.ERROR_NO_FOND_USER
import com.aliza.simiex.utils.net.ParseRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(private val api: HomeParse) {

    suspend fun getSliderAds(): ParseRequest<List<Slider>> = withContext(Dispatchers.IO) {
        try {
            val parseSliderAds = api.getSliderAds().data
                ?: return@withContext ParseRequest.Error(ERROR_NO_FOND_USER)
            val slider = parseSliderAds.map { Slider.fromParseObject(it) }
            ParseRequest.Success(slider)
        } catch (e: Exception) {
            ParseRequest.Error("An unknown error occurred: ${e.localizedMessage}")
        }
    }
}