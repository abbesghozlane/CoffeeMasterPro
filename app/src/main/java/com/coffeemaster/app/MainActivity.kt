package com.coffeemaster.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.coffeemaster.app.ui.theme.CoffeeMasterTheme
import com.coffeemaster.app.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeMasterTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AppNavGraph()
                }
            }
        }
    }
}

@Composable
fun AppEntry() {
    // Placeholder for navigation host
}
