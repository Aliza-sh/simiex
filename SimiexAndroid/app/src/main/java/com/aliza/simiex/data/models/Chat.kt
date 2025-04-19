package com.aliza.simiex.data.models

data class Chat(
    var id: Int,
    var from: From,
    var message: String,
    var title: String? = null,
    var conditions: List<String?>? = null,
    var actions: List<String?>? = null
) {

    enum class From {
        USER,
        BOT,
        COMMON
    }

}

