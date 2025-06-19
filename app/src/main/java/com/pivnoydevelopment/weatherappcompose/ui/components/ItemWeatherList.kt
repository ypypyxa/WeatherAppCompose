package com.pivnoydevelopment.weatherappcompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pivnoydevelopment.weatherappcompose.ui.model.WeatherModel
import com.pivnoydevelopment.weatherappcompose.ui.theme.GrayGlass

@Composable
fun MainList(currentDay: MutableState<WeatherModel>, daysList: List<WeatherModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            daysList
        ) {_, item ->
            ItemWeatherList(item, currentDay)
        }
    }

}

@Composable
fun ItemWeatherList(
    item: WeatherModel = WeatherModel(
        city = "Moscow",
        time = "10:00",
        currentTemp = "30°C",
        condition = "Умеренный дождь",
        icon = "//cdn.weatherapi.com/weather/64x64/day/302.png",
        maxTemp = "",
        minTemp = "",
        hours = ""
    ),
    currentDay: MutableState<WeatherModel>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 3.dp,
                start = 5.dp,
                end = 5.dp
            )
            .clickable {
                if (item.hours.isEmpty()) return@clickable
                currentDay.value = item
            },
        colors = cardColors(containerColor = GrayGlass),
        elevation = cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().size(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 5.dp,
                        bottom = 5.dp
                    )
                    .weight(1f)
            ) {
                Text(
                    text = item.time,
                    color = Color.White,
                    style = TextStyle(fontSize = 13.sp)
                )
                Text(
                    text = item.condition,
                    color = Color.White,
                    style = TextStyle(fontSize = 13.sp)
                )
            }
            Box(
                modifier = Modifier.weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.currentTemp.ifEmpty { "${item.maxTemp}°C / ${item.minTemp}°C" },
                    color = Color.White,
                    style = TextStyle(fontSize = 20.sp)
                )
            }
            Box(contentAlignment = Alignment.CenterEnd) {
                AsyncImage(
                    model = "https:${item.icon}",
                    contentDescription = "WeatherImage",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(top = 1.dp, end = 8.dp)
                )
            }
        }
    }
}