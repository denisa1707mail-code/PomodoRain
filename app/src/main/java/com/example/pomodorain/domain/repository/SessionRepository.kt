package com.example.pomodorain.domain.repository

import com.example.pomodorain.domain.model.StudySession
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun observeSessions(): Flow<List<StudySession>>
    suspend fun saveSession(session: StudySession)
    suspend fun getSessionId(id: Long): StudySession?
}