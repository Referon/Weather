package ru.referon.weather

import ru.referon.weather.model.WeatherModel
import ru.referon.weather.model5day.WeatherModel5Day

data class FeedModel(
    val weather: WeatherModel? = null,
    val weather5Day: WeatherModel5Day? = null,
    val error: Boolean = false,
    val loading: Boolean = false
) {
}