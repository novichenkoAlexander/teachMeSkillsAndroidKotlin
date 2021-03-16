package io.techmeskills.an02onl_plannerapp.support

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    val action = currentDestination?.getAction(resId)
    if (action != null) navigate(resId, args, navOptions, navExtras)
}

fun NavController.navigateSafe(
    navDirections: NavDirections,
    navOptions: NavOptions? = null
) {
    val action = currentDestination?.getAction(navDirections.actionId)
    if (action != null) navigate(navDirections, navOptions)
}

fun NavController.navigateSafe(
    navDirections: NavDirections,
    navExtras: Navigator.Extras
) {
    val action = currentDestination?.getAction(navDirections.actionId)
    if (action != null) navigate(navDirections, navExtras)
}