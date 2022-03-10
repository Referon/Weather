package ru.referon.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.referon.weather.model.WeatherModel
import ru.referon.weather.model5day.WeatherModel5Day

private val BASE_URL = "https://api.openweathermap.org/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("data/2.5/weather?&units=metric&lang=ru&APPID=6f7a57c490c07a39fcf3746d3cb2bc68")
    suspend fun getWeatherData(
        @Query("q") cityName: String
    ): WeatherModel

    @GET("data/2.5/forecast?&cnt=16&units=metric&appid=6f7a57c490c07a39fcf3746d3cb2bc68")
    suspend fun getWeather5Day(
        @Query("q") cityName: String
    ): WeatherModel5Day

}
object PostsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}