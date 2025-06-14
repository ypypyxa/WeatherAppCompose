package com.pivnoydevelopment.weatherappcompose.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pivnoydevelopment.weatherappcompose.ui.model.WeatherModel
import com.pivnoydevelopment.weatherappcompose.ui.theme.GrayGlass

@Preview (showBackground = true)
@Composable
fun ItemWeatherList(item: WeatherModel = WeatherModel(
        city = "Moscow",
        time = "10:00",
        currentTemp = "30°C",
        condition = "Дождь",
        icon = "//cdn.weatherapi.com/weather/64x64/day/302.png",
        maxTemp = "",
        minTemp = "",
        hours = ""
    )) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp),
        colors = cardColors(containerColor = GrayGlass),
        elevation = cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                    color = Color.White
                )
                Text(
                    text = "Солнечно",
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.currentTemp.ifEmpty { "${item.minTemp}/${item.maxTemp}" },
                    color = Color.White,
                    style = TextStyle(fontSize = 25.sp)
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
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