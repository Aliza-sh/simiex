package com.aliza.simiex.utils.extensions

import com.aliza.simiex.utils.net.NetworkChecker
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject

fun isInternetAvailable(): StateFlow<Boolean> {
    val networkChecker: NetworkChecker by inject(NetworkChecker::class.java)
    return networkChecker.checkNetwork()
}