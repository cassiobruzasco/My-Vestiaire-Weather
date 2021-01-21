package com.cassiobruzasco.parisweather.fragment.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.cassiobruzasco.data.api.*
import com.cassiobruzasco.parisweather.R
import com.cassiobruzasco.util.DateUtil
import kotlinx.android.synthetic.main.item_weather_recycler.view.*

class WeatherRecyclerAdapter(
    private val dateUtil: DateUtil,
    private var item: WeatherResponseItem
) : RecyclerView.Adapter<WeatherRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherRecyclerAdapter.ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_recycler, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount() = item.count

    override fun onBindViewHolder(holder: WeatherRecyclerAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    fun updateItem(newItem: WeatherResponseItem) {
        item = newItem
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() = with(itemView) {
            val pos = adapterPosition

            Log.d("CASSIO", "date: ${item.list[pos].date}")
            date.text = dateUtil.to_dd_MMM(item.list[pos].date)
            var tempInt = 0
            var feelsLikeString = ""
            when (dateUtil.getTimeOfDay()) {
                in 6..11 -> {
                    tempInt = item.list[pos].temperature.morning.toInt()
                    feelsLikeString = item.list[pos].feelsLike.morning.toInt().toString()
                }
                in 11..16 -> {
                    tempInt = item.list[pos].temperature.day.toInt()
                    feelsLikeString = item.list[pos].feelsLike.day.toInt().toString()
                }
                in 17..20 -> {
                    tempInt = item.list[pos].temperature.evening.toInt()
                    feelsLikeString = item.list[pos].feelsLike.evening.toInt().toString()
                }
                in 0..5,
                in 21..23 -> {
                    tempInt = item.list[pos].temperature.night.toInt()
                    feelsLikeString = item.list[pos].feelsLike.night.toInt().toString()
                }
            }

            when {
                tempInt > 25 -> {
                    temp_emoji.text = context.getString(R.string.recycler_temperature_emoji_hot)
                }
                tempInt < 10 -> {
                    temp_emoji.text = context.getString(R.string.recycler_temperature_emoji_cold)
                }
                else -> {
                    temp_emoji.visibility = View.GONE
                }
            }

            temp.text = context.getString(
                R.string.recycler_temperature_label,
                tempInt.toString()
            )
            weather.text = HtmlCompat.fromHtml(
                context.getString(
                    R.string.recycler_weather_label,
                    item.list[pos].weather[0].main
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            weather_description.text = HtmlCompat.fromHtml(
                context.getString(
                    R.string.recycler_weather_label,
                    item.list[pos].weather[0].description.toLowerCase().capitalize()
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            temp_min.text = HtmlCompat.fromHtml(
                context.getString(
                    R.string.recycler_min_temperature_label,
                    item.list[pos].temperature.min.toInt().toString()
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            temp_max.text = HtmlCompat.fromHtml(
                context.getString(
                    R.string.recycler_max_temperature_label,
                    item.list[pos].temperature.max.toInt().toString()
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            feels_like.text = HtmlCompat.fromHtml(
                context.getString(
                    R.string.recycler_feels_like_label,
                    feelsLikeString,
                    item.list[pos].rain.toInt().toString()
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            humidity.text = HtmlCompat.fromHtml(
                context.getString(
                    R.string.recycler_humidity_label,
                    item.list[pos].humidity.toString()
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            Glide.with(context)
                .asBitmap()
                .load("https://openweathermap.org/img/wn/${item.list[pos].weather[0].icon}@2x.png")
                .into(icon)
        }
    }
}