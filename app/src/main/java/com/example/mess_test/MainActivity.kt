package com.example.mess_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val navHost =
                supportFragmentManager
                    .findFragmentById(R.id.nav_host) as NavHostFragment

            val navController = navHost.navController
            navController.setGraph(R.navigation.nav_graph)
        }
    }
}
