package nl.a3.dora.viewmodel.repository

import kotlinx.coroutines.flow.Flow
import nl.a3.dora.model.POI
import nl.a3.dora.data.repository.PoiRepositoryImpl
import nl.a3.dora.viewmodel.PoiViewModel


/**
 * @brief Used for dependency injection
 * @see PoiRepositoryImpl and
 * @see PoiViewModel for injection
 */
interface PoiRepository {
    fun getAllPOIs(): Flow<List<POI>>
    suspend fun getPOIByID(id: Int): POI?
    suspend fun updatePOI(poi: POI)
    suspend fun insertPOI(poi: POI)
    suspend fun deletePOI(poi: POI)
}