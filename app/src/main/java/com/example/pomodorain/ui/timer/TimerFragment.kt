package com.example.pomodorain.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.pomodorain.R
import com.example.pomodorain.databinding.FragmentTimerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.internal.concurrent.formatDuration
import org.apache.http.params.HttpConnectionParams

@AndroidEntryPoint
class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding: FragmentTimerBinding
        get() = _binding ?: throw IllegalStateException("FragmentTimerBinding for TimerFragment must not be null")

    private val viewModel: TimerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container,  false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDurationToggle()
        setupButtons()
        observeState()
    }

    private fun setupDurationToggle() {
        binding.tgDuration.check(R.id.btnDuration25)

        binding.tgDuration.addOnButtonCheckedListener { group, btnId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener

            val index = when (btnId) {
                R.id.btnDuration25 -> 0
                R.id.btnDuration50 -> 1
                R.id.btnDuration90 -> 2
                else -> return@addOnButtonCheckedListener
            }
            viewModel.onDurationSelected(index)
        }
    }

    private fun setupButtons() {
        binding.btnStart.setOnClickListener {
            viewModel.onStart()
        }

        binding.btnStop.setOnClickListener {
            viewModel.onStop()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    render(it)
                }
            }
        }
    }

    private fun render(state: TimerUiState) {
        with(binding) {
        tvTimer.text = formatTime(state.remainingSeconds)

        btnStart.isEnabled = !state.isRunning
        btnStop.isEnabled = state.isRunning

        updateDurationButtons(state)
        }
    }

    private fun formatTime(secondsAmount: Int): String {
        val minutes = secondsAmount / 60
        val seconds = secondsAmount % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun updateDurationButtons(state: TimerUiState) {
        val isEnabled = !state.isRunning

        with(binding) {
            btnDuration25.isEnabled = isEnabled
            btnDuration50.isEnabled = isEnabled
            btnDuration90.isEnabled = isEnabled
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}