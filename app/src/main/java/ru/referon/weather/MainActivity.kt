package ru.referon.weather

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.referon.weather.adapter.OnInteractionListener
import ru.referon.weather.adapter.PostsAdapter
import ru.referon.weather.databinding.ActivityMainBinding
import ru.referon.weather.viewmodel.ViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: ViewModel by viewModels()


        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()
        var cName = GET.getString("cityName", "moscow")
        binding.nameCity.setText(cName)
        viewModel.loadWeather(cName!!)

        val adapter = PostsAdapter(object : OnInteractionListener {})

        binding.list.adapter = adapter

        viewModel.data.observe(this) { state ->
            binding.errorButton.isVisible = state.error
            binding.loading.isVisible = state.loading
            if (state.loading == true || state.error == true) {
                binding.group.isVisible = false
            } else binding.group.isVisible = true

            binding.temp.text = state.weather?.main?.temp?.toInt().toString() + " C°"
            binding.nameCity.text = state.weather?.name
            binding.description.text = state.weather?.weather?.get(0)?.description
            adapter.submitList(state.weather5Day?.list)
            binding.humidityInt.text = state.weather?.main?.humidity.toString() + " %"
            binding.windInt.text = state.weather?.wind?.speed?.toInt().toString() + " м/c"
            binding.feelingInt.text = state.weather?.main?.feels_like?.toInt().toString() + " C°"

            Glide.with(this)
                .load("https://openweathermap.org/img/wn/" + state.weather?.weather?.get(0)?.icon + "@2x.png")
                .apply(RequestOptions().override(200, 200))
                .into(binding.image)

            val times = state.weather?.dt?.toLong()?.plus(state.weather?.timezone)?.plus(50800)
            val dateFormat = java.text.SimpleDateFormat("dd MMMM yyyy г.  HH:mm")
            val date = java.util.Date(times?.times(1000) ?: 0)
            binding.time.text = dateFormat.format(date)
        }

        binding.icSearch.setOnClickListener {
            val myCity = binding.editText.text.toString()
            SET.putString("cityName", myCity)
            SET.apply()
            viewModel.loadWeather(myCity)
        }

        binding.errorButton.setOnClickListener {
            val myCity = binding.editText.text.toString()
            viewModel.loadWeather(myCity)
        }

        binding.refresh.setOnRefreshListener {
            viewModel.loadWeather(binding.nameCity.text.toString())
            binding.refresh.setRefreshing(false)
        }
    }
}