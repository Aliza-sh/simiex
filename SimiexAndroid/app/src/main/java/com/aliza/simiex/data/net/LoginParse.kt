package com.aliza.simiex.data.net

import com.aliza.simiex.utils.net.ParseRequest
import com.aliza.simiex.utils.net.ParseResponse
import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginParse {

    suspend fun signUp(phoneNumber: String, password: String): ParseRequest<ParseUser> {

        return withContext(Dispatchers.IO) {
            try {
                val user = ParseUser().apply {
                    this.username = phoneNumber
                    this.setPassword(password)
                }
                user.signUp()
                ParseResponse(user, null).generateResponse()

            } catch (e: ParseException) {
                if (e.message == "Account already exists for this username.") {
                    login(phoneNumber, password)
                } else
                    ParseResponse<ParseUser>(null, e).generateResponse()

            } catch (e: Exception) {
                ParseRequest.Error("An unknown error occurred: ${e.localizedMessage}")
            }
        }
    }

    private suspend fun login(phoneNumber: String, password: String): ParseRequest<ParseUser> {

        return withContext(Dispatchers.IO) {
            try {
                val user = ParseUser.logIn(phoneNumber, password)
                ParseResponse(user, null).generateResponse()

            } catch (e: ParseException) {
                ParseResponse<ParseUser>(null, e).generateResponse()
            } catch (e: Exception) {
                ParseRequest.Error("An unknown error occurred: ${e.localizedMessage}")
            }
        }
    }

}

