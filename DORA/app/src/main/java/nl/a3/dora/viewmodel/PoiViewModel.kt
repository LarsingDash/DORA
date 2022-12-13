package nl.a3.dora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import nl.a3.dora.data.repository.test.DummyRepoPoi
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
        var tempItem: POI? = null
        viewModelScope.launch(Dispatchers.IO) {
            tempItem = poiRepository.getPOIByID(id)!!.copy()
        }
        return tempItem
    }

    override fun addType(poi: POI) {
        viewModelScope.launch(Dispatchers.IO) {
            poiRepository.insertPOI(poi)
        }
    }

    override fun updateType(poi: POI) {
        viewModelScope.launch(Dispatchers.IO) {
            poiRepository.updatePOI(poi)
        }
    }

    override fun deleteType(poi: POI) {
        viewModelScope.launch(Dispatchers.IO) {
            poiRepository.deletePOI(poi)
        }
    }
}