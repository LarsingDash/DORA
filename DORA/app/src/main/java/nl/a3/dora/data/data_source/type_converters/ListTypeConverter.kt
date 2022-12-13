package nl.a3.dora.data.data_source.type_converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nl.a3.dora.model.POI

class ListTypeConverter {

    @TypeConverter
    fun fromPoiList(poiList: List<POI>): String {
        return Gson().toJson(poiList)
    }

    @TypeConverter
    fun toPoiList(poiList: String): List<POI>{
        val listType = object: TypeToken<List<POI>>() {}.type
        return Gson().fromJson(poiList, listType)
    }
}