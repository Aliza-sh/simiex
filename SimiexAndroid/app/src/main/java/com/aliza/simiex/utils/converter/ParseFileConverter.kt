package com.aliza.simiex.utils.converter

import androidx.room.TypeConverter
import com.parse.ParseFile

class ParseFileConverter {

    @TypeConverter
    fun fromParseFile(parseFile: ParseFile?): String? {
        return parseFile?.url
    }

    @TypeConverter
    fun toParseFile(url: String?): ParseFile? {
        return url?.let { ParseFile("fileName", byteArrayOf()) }
    }
}
