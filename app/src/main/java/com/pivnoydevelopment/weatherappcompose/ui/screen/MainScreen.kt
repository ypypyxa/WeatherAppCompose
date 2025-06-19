package com.pivnoydevelopment.weatherappcompose.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.pivnoydevelopment.weatherappcompose.R
import com.pivnoydevelopment.weatherappcompose.ui.components.CardMain
import com.pivnoydevelopment.weatherappcompose.ui.components.TabLayout
import com.pivnoydevelopment.weatherappcompose.ui.model.WeatherModel

@Composable
fun MainScreen(
    currentDay: MutableState<WeatherModel>,
    daysList: MutableState<List<WeatherModel>>
) {
    Image(
        painter = painterResource(id = R.drawable.bg_main),
        contentDescription = "MainScreen background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues())
    ) {
        Column {
            CardMain(currentDay)
            TabLayout(currentDay, daysList)
        }
    }
}