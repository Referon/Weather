package ru.referon.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.referon.weather.databinding.CardLayaoutBinding
import ru.referon.weather.model5day.WeatherList


interface OnInteractionListener {

}

class PostsAdapter(
    private val callback: OnInteractionListener,
    ) : androidx.recyclerview.widget.ListAdapter<WeatherList, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardLayaoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardLayaoutBinding,
    private val callback: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: WeatherList) {

        val dateFormat = java.text.SimpleDateFormat("HH:mm")
        val date = task.dt + task.timezone + 54000
        val data2 = java.util.Date(date.toLong() * 1000)

        binding.apply {
            tempCard.text = task.main.temp.toInt().toString() + "C°"
            timeCard.text = dateFormat.format(data2)
            Glide.with(itemView)
                .load("https://openweathermap.org/img/wn/" + task.weather[0].icon + "@2x.png")
                .apply(RequestOptions().override(150, 150))
                .into(imageCard)
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<WeatherList>() {
    override fun areItemsTheSame(oldItem: WeatherList, newItem: WeatherList): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WeatherList, newItem: WeatherList): Boolean {
        return oldItem == newItem
    }

}