package com.aliza.simiex.utils.extensions

import com.aliza.simiex.utils.net.NetworkChecker
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject

fun isInternetAvailable(): StateFlow<Boolean> {
    val networkChecker: NetworkChecker by inject(NetworkChecker::class.java)
    return networkChecker.checkNetwork()
}

fun String?.extractError(): String? {
    if (this.isNullOrEmpty()) return null

    val allError = this.split(":")
    return if (allError.size > 1) allError[1].trim() else allError[0].trim()
}

fun String.isValidPhoneNumber(): Boolean {
    return this.length <= 11
}

fun String.validatePhoneNumber(): Boolean {
    return this.matches(Regex("^09[0-9]{9}\$"))
}

fun String.isValidPassword(): Boolean {
    return this.length >= 6
}