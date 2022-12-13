package nl.a3.dora.data.repository.test

import kotlinx.coroutines.flow.Flow
import nl.a3.dora.data.data_source.PoiDao
import nl.a3.dora.model.POI
import nl.a3.dora.viewmodel.repository.PoiRepository

/**
 * @brief Implementation of the Data Access Object used within the application
 * @see PoiDao for documentation
 */
class DummyRepoPoi (private val poiDao: PoiDao): PoiRepository {

    override fun getAllPOIs(): Flow<List<POI>> {
        return poiDao.getAllPOIs()
    }

    override suspend fun getPOIByID(id: Int): POI? {
        return poiDao.getPOIByID(id)
    }

    override suspend fun updatePOI(poi: POI) {
        poiDao.updatePOI(poi)
    }

    override suspend fun insertPOI(poi: POI) {
        poiDao.insertPOI(poi)
    }

    override suspend fun deletePOI(poi: POI) {
        poiDao.deletePOI(poi)
    }
}