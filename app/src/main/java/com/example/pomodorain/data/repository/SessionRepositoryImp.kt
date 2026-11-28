package com.example.pomodorain.data.repository

import com.example.pomodorain.data.local.dao.StudySessionDao
import com.example.pomodorain.data.repository.mappers.toDomain
import com.example.pomodorain.data.repository.mappers.toEntity
import com.example.pomodorain.domain.model.StudySession
import com.example.pomodorain.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionRepositoryImp(
    private val dao: StudySessionDao
) : SessionRepository {

    override fun observeSessions(): Flow<List<StudySession>> {
        return dao.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveSession(session: StudySession) {
        dao.insert(session.toEntity())
    }

    override suspend fun getSessionId(id: Long): StudySession? {
        return dao.getById(id)?.toDomain()
    }
}