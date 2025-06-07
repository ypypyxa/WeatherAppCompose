package com.pivnoydevelopment.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.pivnoydevelopment.weatherappcompose.ui.layout.CardMain
import com.pivnoydevelopment.weatherappcompose.ui.layout.TabLayout
import com.pivnoydevelopment.weatherappcompose.ui.theme.WeatherAppComposeTheme

const val API_KEY = "2defcceec5a946db9f0145440250506"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                Image(
                    painter = painterResource(id = R.drawable.bg_main),
                    contentDescription = "MainScreen background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column {
                    CardMain()
                    TabLayout()
                }
            }
        }
    }
}