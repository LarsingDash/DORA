package nl.a3.dora.viewmodel.repository

import kotlinx.coroutines.flow.Flow

interface ViewModelAbstract<T> {
    val typeListFlow: Flow<List<T>>

    fun getTypeByID(id: Int): T?

    fun addType(type: T)

    fun updateType(type: T)

    fun deleteType(type: T)
}