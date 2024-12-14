package com.aliza.simiex.data.net

import android.util.Log
import com.aliza.simiex.utils.constants.INSURANCE
import com.aliza.simiex.utils.constants.INSURANCE_TYPE
import com.aliza.simiex.utils.constants.SLIDER_ADS
import com.aliza.simiex.utils.net.ParseRequest
import com.aliza.simiex.utils.net.ParseResponse
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeParse(/*private val sessionManager: SessionManager*/) {


    suspend fun getSliderAds(): ParseRequest<List<ParseObject>> = withContext(Dispatchers.IO) {
        try {
            val query = ParseQuery<ParseObject>(SLIDER_ADS)
            val parseObject = query.find()
            Log.d("TAG", "getSliderAds: ${parseObject[1]}")
            ParseResponse(parseObject, null).generateResponse()
        } catch (e: ParseException) {
            ParseResponse(null, e).generateResponse()
        } catch (e: Exception) {
            ParseRequest.Error("An unknown error occurred: ${e.localizedMessage}")
        }
    }

}