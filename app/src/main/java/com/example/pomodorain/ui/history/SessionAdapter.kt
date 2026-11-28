package com.example.pomodorain.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pomodorain.R
import com.example.pomodorain.databinding.ItemSessionBinding
import com.example.pomodorain.domain.model.StudySession
import com.example.pomodorain.domain.model.WeatherSnapshot
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class SessionAdapter :
    ListAdapter<StudySession, SessionAdapter.SessionViewHolder>(SessionDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SessionViewHolder {
        val binding = ItemSessionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SessionViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SessionViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    class SessionViewHolder(
        private val binding: ItemSessionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(session: StudySession) {
            with(binding) {
                tvSessionDate.text = formatDate(session.startedAtMillis)
                tvSessionDuration.text = formatDuration(session.durationSeconds)

                tvSessionStatus.setText(
                    if (session.isCompleted) {
                        R.string.session_completed
                    } else {
                        R.string.session_stopped_early
                    }
                )

                tvStartWeather.text = formatWeather(R.string.weather_start, session.startWeather)
                tvEndWeather.text = formatWeather(R.string.weather_end, session.endWeather)
            }
        }

        private fun formatDate(millis: Long): String {
            return Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .format(dateFormatter)
        }

        private fun formatDuration(seconds: Int): String {
            val minutes = seconds / 60
            return binding.root.context
                .getString(R.string.session_duration_format, minutes)
        }

        private fun formatWeather(
            @StringRes resId: Int,
            weather: WeatherSnapshot?,
        ): String {
            val context = binding.root.context

            return if (weather != null) {
                context.getString(resId, weather.temperature.toString())
            } else {
                context.getString(R.string.weather_unavailable)
            }
        }

        companion object {
            private val dateFormatter = DateTimeFormatter.ofPattern(
                "dd.MM.yyyy HH:mm",
                Locale.getDefault()
            )
        }
    }

    private companion object SessionDiffCallback : DiffUtil.ItemCallback<StudySession>() {
        override fun areItemsTheSame(oldItem: StudySession, newItem: StudySession): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: StudySession, newItem: StudySession): Boolean =
            oldItem == newItem
    }
}

