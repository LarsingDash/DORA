package nl.a3.dora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.a3.dora.model.POI
import nl.a3.dora.model.Route
import nl.a3.dora.viewmodel.repository.RouteRepository
import nl.a3.dora.viewmodel.repository.ViewModelAbstract
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(
    private val routeRepository: RouteRepository
): ViewModel(), ViewModelAbstract<Route> {
    override val typeListFlow: Flow<List<Route>>
        get() = routeRepository.getAllRoutes()

    override fun getTypeByID(id: Int): Route? {
        var tempRoute: Route?
        runBlocking { tempRoute = routeRepository.getRouteByID(id) }

        return tempRoute
    }

    override fun addType(type: Route) {
        viewModelScope.launch(Dispatchers.IO) {
            routeRepository.insertRoute(type)
        }
    }

    override fun updateType(type: Route) {
        viewModelScope.launch(Dispatchers.IO) {
            routeRepository.updateRoute(type)
        }
    }

    override fun deleteType(type: Route) {
        viewModelScope.launch(Dispatchers.IO) {
            routeRepository.deleteRoute(type)
        }
    }
}