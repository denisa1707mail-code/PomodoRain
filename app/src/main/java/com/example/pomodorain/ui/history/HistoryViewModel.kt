package com.example.pomodorain.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodorain.domain.model.StudySession
import com.example.pomodorain.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: SessionRepository
) : ViewModel() {

    val uiState: StateFlow<HistoryUiState> = repository
        .observeSessions()
        .map {
            if (it.isEmpty()) {
                HistoryUiState.Empty
            } else {
                HistoryUiState.Success(it)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HistoryUiState.Loading
        )

    // TODO: Заменить после добвления функционала сохранения сессий
    fun addTestSession() {
        viewModelScope.launch {
            val testSession = StudySession(
                startedAtMillis = System.currentTimeMillis(),
                durationSeconds =  25 * 60,
                isCompleted = true,
                startWeather = null,
                endWeather = null
            )
            repository.saveSession(testSession)
        }
    }

}