package com.roblesdotdev.ohmylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.roblesdotdev.ohmylist.ui.theme.OhMyListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OhMyListTheme {
                Text(text = "Hello, World")
            }
        }
    }
}
