package com.pivnoydevelopment.weatherappcompose

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pivnoydevelopment.weatherappcompose.ui.components.DialogSearch
import com.pivnoydevelopment.weatherappcompose.ui.model.WeatherModel
import com.pivnoydevelopment.weatherappcompose.ui.screen.MainScreen
import com.pivnoydevelopment.weatherappcompose.ui.theme.WeatherAppComposeTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val dialogState = remember {
                    mutableStateOf(false)
                }
                val currentDay = remember {
                    mutableStateOf(
                        WeatherModel("","","0.0","","","0.0","0.0","")
                    )
                }

                getData("Moscow", this, daysList, currentDay)

                if (dialogState.value) {
                    DialogSearch(dialogState, onSubmit = {
                        getData( it, this, daysList, currentDay)
                    })
                }

                MainScreen(
                    currentDay,
                    daysList,
                    onClickRefresh = { getData("Moscow", this, daysList, currentDay) },
                    onClickSearch = { dialogState.value = true }
                )
            }
        }
    }
}

private fun getData(
    city: String,
    context: Context,
    daysList: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>
) {
    val url = "https://api.weatherapi.com/v1/forecast.json?" +
            "key=${BuildConfig.API_KEY}" +
            "&q=$city" +
            "&days=7" +
            "&aqi=no" +
            "&alerts=no" +
            "&lang=ru"

    Log.d("WEATHER_APP", "URL = $url")

    val queue = Volley.newRequestQueue(context)
    val sRequest = object : StringRequest(
        Method.GET,
        url,
        { response ->

            val resp = getWeatherByDays(response)
            currentDay.value = resp[0]
            daysList.value = resp
        },
        {
            Log.d("WEATHER_APP", "Error = $it")
        }
    ) {
        override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
            return try {
                val utf8 = String(response.data, Charsets.UTF_8)
                Response.success(utf8, HttpHeaderParser.parseCacheHeaders(response))
            } catch (e: Exception) {
                Response.error(ParseError(e))
            }
        }
    }
    queue.add(sRequest)
}

private fun getWeatherByDays(response: String): List<WeatherModel> {
    if (response.isEmpty()) return emptyList()

    val items = ArrayList<WeatherModel>()
    val mainObject = JSONObject(response)
    val city = mainObject
        .getJSONObject("location")
        .getString("name")
    val days = mainObject
        .getJSONObject("forecast")
        .getJSONArray("forecastday")
    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        items.add(
            WeatherModel(
                city = city,
                time = item.getString("date"),
                currentTemp = "",
                condition = item
                    .getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("text"),
                icon = item
                    .getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("icon"),
                maxTemp = item
                    .getJSONObject("day")
                    .getString("maxtemp_c").toFloat().toInt().toString(),
                minTemp = item
                    .getJSONObject("day")
                    .getString("mintemp_c").toFloat().toInt().toString(),
                hours = item.getJSONArray("hour").toString()
            )
        )
    }
    items[0] = items[0].copy(
        time = mainObject
            .getJSONObject("current")
            .getString("last_updated"),
        currentTemp = mainObject
            .getJSONObject("current")
            .getString("temp_c").toFloat().toInt().toString() + "°C"
    )

    return items
}