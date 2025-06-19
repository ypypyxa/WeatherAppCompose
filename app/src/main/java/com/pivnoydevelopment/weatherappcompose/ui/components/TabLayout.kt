package com.pivnoydevelopment.weatherappcompose.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pivnoydevelopment.weatherappcompose.ui.model.WeatherModel
import com.pivnoydevelopment.weatherappcompose.ui.theme.GrayGlass
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun TabLayout(currentDay: MutableState<WeatherModel>, daysList: MutableState<List<WeatherModel>>) {
    val tabList = listOf("Сегодня", "На неделю")
    val pagerState = rememberPagerState(initialPage = 0) { tabList.size }
    val courutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(
                top =  5.dp,
                bottom = 5.dp
            )
    ) {
        TabRow(
            modifier = Modifier
                .padding(
                    start = 5.dp,
                    end = 5.dp
                )
                .clip(
                    RoundedCornerShape(
                        topStart = 15.dp,
                        topEnd = 15.dp,
                        bottomStart = 5.dp,
                        bottomEnd = 5.dp
                    )
            ),
            selectedTabIndex = pagerState.currentPage,
            containerColor = GrayGlass,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier =  Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            }
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        courutineScope.launch {
                            pagerState.animateScrollToPage(
                                page =  index,
                                animationSpec = tween(durationMillis = 1000)
                            )
                        }
                    },
                    text = {
                        Text(
                            text = text,
                            color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                        )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->
            val list = when(index) {
                0 -> getWeatherByHours(currentDay.value.hours)
                1 -> daysList.value
                else -> daysList.value
            }
            MainList(currentDay, list)
        }
    }
}

private fun getWeatherByHours(hours: String): List<WeatherModel> {
    if (hours.isEmpty()) return emptyList()

    val hoursArray = JSONArray(hours)
    val hoursList = ArrayList<WeatherModel>()

    for (i in 0 until hoursArray.length()) {
        val item = hoursArray[i] as JSONObject
        hoursList.add(
            WeatherModel(
                city = "",
                time = item.getString("time"),
                currentTemp = item.getString("temp_c").toFloat().toInt().toString() + "°C",
                condition = item.getJSONObject("condition").getString("text"),
                icon = item.getJSONObject("condition").getString("icon"),
                minTemp = "",
                maxTemp = "",
                hours = ""
            )
        )
    }
    return hoursList
}