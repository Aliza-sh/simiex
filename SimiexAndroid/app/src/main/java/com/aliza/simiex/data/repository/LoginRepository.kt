package com.aliza.simiex.data.repository

import com.aliza.simiex.data.net.LoginParse
import com.aliza.simiex.utils.net.ParseRequest
import com.parse.ParseUser

class LoginRepository(private val api: LoginParse) {

    suspend fun login(phoneNumber: String, password: String): ParseRequest<ParseUser> {
        return api.signUp(phoneNumber, password)
    }
}