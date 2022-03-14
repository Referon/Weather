package ru.referon.weather.model5day

data class WeatherModel5Day(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherList>,
    val message: Int
)