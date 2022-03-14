package ru.referon.weather.repository

import ru.referon.weather.model.WeatherModel
import ru.referon.weather.model5day.WeatherModel5Day

interface Repository {
    suspend fun getWeather(city: String): WeatherModel
    suspend fun getWeather5Day(city: String): WeatherModel5Day
}