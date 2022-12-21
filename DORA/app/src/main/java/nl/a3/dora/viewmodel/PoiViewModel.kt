package nl.a3.dora.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.a3.dora.model.POI
import nl.a3.dora.viewmodel.repository.PoiRepository
import nl.a3.dora.viewmodel.repository.ViewModelAbstract
import javax.inject.Inject

//TODO make this use an interface instead of the Repo
@HiltViewModel
class PoiViewModel @Inject constructor(
    private val poiRepository: PoiRepository
): ViewModel(), ViewModelAbstract<POI> {

    override val typeListFlow: Flow<List<POI>>
        get() = poiRepository.getAllPOIs()

    override fun getTypeByID(id: Int): POI? {
        var tempItem: POI?

        runBlocking { tempItem = poiRepository.getPOIByID(id) }
        return tempItem
    }

    override fun addType(type: POI) {
        viewModelScope.launch(Dispatchers.IO) {
            poiRepository.insertPOI(type)
        }
    }

    override fun updateType(type: POI) {
        viewModelScope.launch(Dispatchers.IO) {
            poiRepository.updatePOI(type)
        }
    }

    override fun deleteType(type: POI) {
        viewModelScope.launch(Dispatchers.IO) {
            poiRepository.deletePOI(type)
        }
    }
}