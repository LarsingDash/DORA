package nl.a3.dora

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import nl.a3.dora.ui.DORA
import nl.a3.dora.ui.theme.DORATheme
import org.osmdroid.config.Configuration


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO find a better way to do this
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //TODO same thing
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        setContent {
            DORATheme() {
                DORA()
            }
        }
    }
}