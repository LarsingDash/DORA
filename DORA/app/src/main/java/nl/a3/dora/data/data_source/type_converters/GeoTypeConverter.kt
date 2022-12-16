package nl.a3.dora.data.data_source.type_converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.osmdroid.util.GeoPoint

class GeoTypeConverter {
    @TypeConverter
    fun fromGeoPoint(geoPoint: GeoPoint): String? {
        return Gson().toJson(geoPoint)
    }

    @TypeConverter
    fun toGeoPoint(data: String): GeoPoint {
        val tokenType = object: TypeToken<GeoPoint>(){}.type
        return Gson().fromJson(data, tokenType)
    }
}