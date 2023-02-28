package com.ribsky.common.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController

interface Navigation {

    var activity: AppCompatActivity?
    var navController: NavController?

    fun setup(activity: AppCompatActivity?, navController: NavController?) {
        this.activity = activity
        this.navController = navController
    }

    fun navigateHome(bundle: Bundle? = null)
}
