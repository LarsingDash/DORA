package nl.a3.dora

import android.app.Application
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DoraApp: Application() {
    companion object{
        private val resources: Resources = getAppResources()

        fun getAppResources(): Resources {return resources}
    }
}