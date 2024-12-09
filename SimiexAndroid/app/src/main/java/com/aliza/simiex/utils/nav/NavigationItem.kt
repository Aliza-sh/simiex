package com.aliza.simiex.utils.nav

import com.aliza.simiex.R
import com.aliza.simiex.utils.constants.HOME_SCREEN
import com.aliza.simiex.utils.constants.PROFILE_SCREEN
import com.aliza.simiex.utils.constants.REPORT_SCREEN

sealed class NavigationItem(
    var id: Int, var route: String, var iconBorder: Int, var iconBold: Int, var title: String
) {
    data object Home :
        NavigationItem(1, HOME_SCREEN, R.drawable.ic_home_outline, R.drawable.ic_home_bold, "   خانه   ")

    data object Request : NavigationItem(
        2, REPORT_SCREEN, R.drawable.ic_report_outline, R.drawable.ic_report_bold, "درخواست"
    )

    data object Profile : NavigationItem(
        5, PROFILE_SCREEN, R.drawable.ic_profile_outline, R.drawable.ic_profile_bold, " پروفایل "
    )
}