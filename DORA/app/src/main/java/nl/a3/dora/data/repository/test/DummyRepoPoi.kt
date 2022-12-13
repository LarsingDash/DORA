package nl.a3.dora.data.repository.test

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import nl.a3.dora.data.data_source.PoiDao
import nl.a3.dora.model.POI
import nl.a3.dora.viewmodel.repository.PoiRepository

/**
 * @brief Implementation of the Data Access Object used within the application
 * @see PoiDao for documentation
 */
class DummyRepoPoi: PoiRepository {
    private val dummyList: List<List<POI>> = listOf (
        listOf(
            POI(0, "Poi 0", 5.0f, false),
            POI(1, "Poi 1", 6.0f, false),
            POI(2, "Poi 2", 9.0f, true),
            POI(3, "Poi 3", 13.0f, false),
        )
    )
//    POI(0, "Poi 0", 5.0f, false, null),
//    POI(1, "Poi 1", 6.0f, false, null),
//    POI(2, "Poi 2", 9.0f, true, null),
//    POI(3, "Poi 3", 13.0f, false, null),


    override fun getAllPOIs(): Flow<List<POI>> {
        return dummyList.asFlow()
    }

    override suspend fun getPOIByID(id: Int): POI? {
        Log.d("DEBUG TEST", "Gpt ${dummyList.first().toList()[id]} from the list! ${dummyList.first().toList()}")
        return dummyList.first()[id]
    }

    override suspend fun updatePOI(poi: POI) {
        Log.d("DEBUG IMPLEMENTATION", "Function UpdatePOI needs an implementation to test!")
//        if(dummyList.first().contains(poi))
//            return
//
//        val tempList: List<POI> = dummyList.first()
//        tempList.drop(0)
//        tempList + poi
//
//        dummyList.first() = tempList
    }

    override suspend fun insertPOI(poi: POI) {
        dummyList.first() + poi
        Log.d("DEBUG TEST", "Added $poi to the list! ${dummyList.first().toList()}")
    }

    override suspend fun deletePOI(poi: POI) {
        dummyList.first().drop(
            dummyList.first().indexOf(poi)
        )
        Log.d("DEBUG TEST", "Deleted $poi from the list! ${dummyList.first().toList()}")
    }
}