package nl.a3.dora

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import nl.a3.dora.presentation.ui.DORA
import nl.a3.dora.presentation.ui.theme.DORATheme
import nl.a3.dora.viewmodel.PoiViewModel
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        setContent {
            DORATheme {
                DORA()
            }
        }
    }
}