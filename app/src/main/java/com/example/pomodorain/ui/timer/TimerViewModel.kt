package com.example.pomodorain.ui.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private val durations = listOf(25, 50, 90)

    fun onDurationSelected(index: Int) {
        if (index !in durations.indices) return

        val newDuration = durations[index]
        _uiState.update {
            it.copy(
                selectedDurationMinutes = newDuration,
                isRunning = false,
                isFinished = false,
                remainingSeconds = newDuration * 60,
            )
        }

        timerJob?.cancel()
        timerJob = null
    }


    fun onStart() {
        if (_uiState.value.isRunning) return

        _uiState.update {
            it.copy(isRunning = true, isFinished = false)
        }

        timerJob = viewModelScope.launch {
            while (_uiState.value.remainingSeconds > 0) {
                delay(1000L)

                _uiState.update {
                    it.copy(remainingSeconds = it.remainingSeconds - 1)
                }
            }

            _uiState.update {
                it.copy(isRunning = false, isFinished = true)
            }
        }
    }

    fun onStop() {
        timerJob?.cancel()
        timerJob = null
        _uiState.update {
            it.copy(isRunning = false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}