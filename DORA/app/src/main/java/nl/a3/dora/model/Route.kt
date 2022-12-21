package nl.a3.dora.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @brief Data class for storing the representation of a Route
 */
@Entity(tableName = "route_table")
data class Route(
    @PrimaryKey(autoGenerate = true) val routeID: Int? = null,
    val routeName: String,
    val routeList: List<POI>,
    val thumbnailUri: String,
    val routeDescription: String,
    val routeLength: Float
)