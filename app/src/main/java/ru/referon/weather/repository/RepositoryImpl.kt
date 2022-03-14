package ru.referon.weather.repository

import ru.referon.weather.PostsApi
import ru.referon.weather.model.WeatherModel
import ru.referon.weather.model5day.WeatherModel5Day

class RepositoryImpl : Repository {

    override suspend fun getWeather(city: String): WeatherModel =
        PostsApi.retrofitService.getWeatherData(city)

    override suspend fun getWeather5Day(city: String): WeatherModel5Day =
        PostsApi.retrofitService.getWeather5Day(city)
}