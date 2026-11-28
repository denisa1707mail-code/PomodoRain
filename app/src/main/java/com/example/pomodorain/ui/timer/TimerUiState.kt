package com.example.pomodorain.ui.timer

data class TimerUiState(
    val selectedDurationMinutes: Int = 25,
    val remainingSeconds: Int = 25 * 60,
    val isRunning: Boolean = false,
    val isFinished: Boolean = false,
)