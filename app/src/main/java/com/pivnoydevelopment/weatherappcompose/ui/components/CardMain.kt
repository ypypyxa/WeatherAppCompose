package com.pivnoydevelopment.weatherappcompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pivnoydevelopment.weatherappcompose.R
import com.pivnoydevelopment.weatherappcompose.ui.model.WeatherModel
import com.pivnoydevelopment.weatherappcompose.ui.theme.GrayGlass

@Composable
fun CardMain(
    currentDay: MutableState<WeatherModel>,
    onClickRefresh: () -> Unit,
    onClickSearch: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = cardColors(containerColor = GrayGlass),
            elevation = cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = currentDay.value.time,
                        style = TextStyle(fontSize = 15.sp),
                        color = Color.White
                    )
                    AsyncImage(
                        model = "https:${currentDay.value.icon}",
                        contentDescription = "WeatherImage",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(top = 1.dp, end = 8.dp)
                    )
                }
                Text(
                    text = currentDay.value.city,
                    style = TextStyle(fontSize = 24.sp),
                    color = Color.White
                )
                Text(
                    text = if(currentDay.value.currentTemp.isNotEmpty())
                        currentDay.value.currentTemp
                    else
                        "${currentDay.value.maxTemp}°C / ${currentDay.value.minTemp}°C",
                    style = TextStyle(fontSize = 65.sp),
                    color = Color.White
                )
                Text(
                    text = currentDay.value.condition,
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.White
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            onClickSearch.invoke()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "SearchImage",
                            tint = Color.White
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 15.dp, start = 8.dp),
                        text = "${currentDay.value.maxTemp}°С / ${currentDay.value.minTemp}°С",
                        style = TextStyle(fontSize = 15.sp),
                        color = Color.White
                    )
                    IconButton(
                        onClick = {
                            onClickRefresh.invoke()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = "RefreshImage",
                            tint = Color.White
                            )
                    }
                }
            }
        }
    }
}