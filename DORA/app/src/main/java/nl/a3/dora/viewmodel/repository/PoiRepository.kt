package nl.a3.dora.viewmodel.repository

import kotlinx.coroutines.flow.Flow
import nl.a3.dora.model.POI

interface PoiRepository {
    fun getAllPOIs(): Flow<List<POI>>
    suspend fun getPOIByID(id: Int): POI?
    suspend fun updatePOI(poi: POI)
    suspend fun insertPOI(poi: POI)
    suspend fun deletePOI(poi: POI)
}