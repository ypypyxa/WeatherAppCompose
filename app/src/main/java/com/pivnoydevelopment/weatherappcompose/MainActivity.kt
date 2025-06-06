package com.pivnoydevelopment.weatherappcompose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pivnoydevelopment.weatherappcompose.ui.theme.WeatherAppComposeTheme
import org.json.JSONObject

const val API_KEY = "2defcceec5a946db9f0145440250506"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                Greeting(name = "Moscow", this)
            }
        }
    }
}

@Composable
fun Greeting(name: String, context: Context) {
    val state = remember {
        mutableStateOf("Unknown")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Temp in $name! = ${state.value} C",
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),

                onClick = {
                    getResult(name, state, context)
                }
            ) {
                Text(text = "Refresh")
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview(context: Context) {
//    WeatherAppComposeTheme {
//        Greeting("Moscow", context)
//    }
//}

private fun getResult(city: String, state: MutableState<String>, context: Context) {
    val url = "https://api.weatherapi.com/v1/current.json" +
            "?key=$API_KEY&" +
            "q=$city" +
            "&aqi=no"

    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {response ->
            val obj = JSONObject(response)

            state.value = obj.getJSONObject("current").getString("temp_c")
        },
        {error ->
            state.value = error.toString()
        }
    )

    queue.add(stringRequest)
}