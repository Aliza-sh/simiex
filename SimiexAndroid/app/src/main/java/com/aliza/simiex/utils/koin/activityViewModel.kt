package com.aliza.simiex.utils.koin

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@Composable
inline fun <reified VM : ViewModel> activityViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): VM {
    val activity = LocalContext.current as ComponentActivity
    return koinViewModel(
        qualifier = qualifier,
        parameters = parameters,
        viewModelStoreOwner = activity
    )
}
