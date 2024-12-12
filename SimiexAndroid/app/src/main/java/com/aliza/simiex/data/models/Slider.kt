package com.aliza.simiex.data.models

import com.aliza.simiex.utils.constants.SLIDER_IMAGE
import com.aliza.simiex.utils.constants.SLIDER_TITLE
import com.parse.ParseFile
import com.parse.ParseObject

data class Slider(
    val title: String?,
    val image: ParseFile,
) {
    companion object {
        fun fromParseObject(parseObject: ParseObject): Slider {
            return Slider(
                title = parseObject.getString(SLIDER_TITLE) ?:"",
                image = parseObject.getParseFile(SLIDER_IMAGE)!!,
            )
        }
    }
}
