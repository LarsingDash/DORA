package nl.a3.dora.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Route(
    @PrimaryKey(autoGenerate = true) val routeID: Int? = null,
    val routeName: String,
)