package com.pivnoydevelopment.weatherappcompose.ui.layout

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pivnoydevelopment.weatherappcompose.ui.model.WeatherModel
import com.pivnoydevelopment.weatherappcompose.ui.theme.GrayGlass
import kotlinx.coroutines.launch

@Preview (showBackground = true)
@Composable
fun TabLayout() {
    val tabList = listOf("Сегодня", "На неделю")
    val pagerState = rememberPagerState(initialPage = 0) { tabList.size }
    val courutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(
                top =  5.dp,
                start = 5.dp,
                end = 5.dp,
                bottom = 5.dp
            )
    ) {
        TabRow(
            modifier = Modifier.clip(
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
                                animationSpec = tween(durationMillis = 700)
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    listOf(
                        WeatherModel(
                            city = "Moscow",
                            time = "10:00",
                            currentTemp = "30°C",
                            condition = "Дождь",
                            icon = "//cdn.weatherapi.com/weather/64x64/day/302.png",
                            maxTemp = "",
                            minTemp = "",
                            hours = ""
                        ),
                        WeatherModel(
                            city = "Moscow",
                            time = "10:00",
                            currentTemp = "30°C",
                            condition = "Дождь",
                            icon = "//cdn.weatherapi.com/weather/64x64/day/302.png",
                            maxTemp = "",
                            minTemp = "",
                            hours = ""
                        ),
                        WeatherModel(
                            city = "Moscow",
                            time = "09/06/2025",
                            currentTemp = "",
                            condition = "Дождь",
                            icon = "//cdn.weatherapi.com/weather/64x64/day/302.png",
                            maxTemp = "30°C",
                            minTemp = "20°C",
                            hours = "12:00"
                        ),
                    )
                ) {
                    _, item -> ItemWeatherList(item)
                }
            }
        }
    }
}