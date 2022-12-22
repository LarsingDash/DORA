package nl.a3.dora.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.osmdroid.util.GeoPoint

/**
 * @bier Data class for storing representative data
 * @Entity annotation for Room to make a data table for POI
 */
@Entity(tableName = "poi_table")
data class POI(
    @PrimaryKey(autoGenerate = true) val poiID: Int? = null,
    val poiName: String,
    var isVisited: Boolean,
    val thumbnailName: String,
    val poiDescription: String,
    val poiLocation: GeoPoint,
)