package ru.referon.weather.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.referon.weather.FeedModel
import ru.referon.weather.repository.RepositoryImpl
import java.lang.Exception

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data

    fun loadWeather(city: String) {

        _data.value = FeedModel(loading = true)
        viewModelScope.launch {
            try {
                val weather = repository.getWeather(city)
                val weather5Day = repository.getWeather5Day(city)

                for (i in 0 until weather5Day.list.size) {
                    weather5Day.list[i].timezone = weather.timezone
                }

                _data.value = FeedModel(weather = weather, weather5Day = weather5Day)
            } catch (e: Exception) {
                _data.value = FeedModel(error = true)
              }

        }
    }
}