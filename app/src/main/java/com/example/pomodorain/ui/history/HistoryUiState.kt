package com.example.pomodorain.ui.history

import com.example.pomodorain.domain.model.StudySession

sealed class HistoryUiState {
    object Loading : HistoryUiState()
    object Empty : HistoryUiState()
    object Error : HistoryUiState()
    data class Success(val sessions: List<StudySession>) : HistoryUiState()
}