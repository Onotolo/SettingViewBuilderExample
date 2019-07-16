package my.onotolo.android.svb.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import my.onotolo.android.svb.example.settings.ImageDescription
import my.onotolo.android.svb.example.settings.IsImageShown
import my.onotolo.android.svb.example.settings.SettingViewBuilderImpl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleImageVisibility(IsImageShown[this])
        handleImageDescription(ImageDescription[this])

        SettingViewBuilderImpl.forSetting(IsImageShown)
            .withOnSettingChangeCallback { value, cancel ->
                handleImageVisibility(value)
            }
            .build(settings_container)

        SettingViewBuilderImpl.forSetting(ImageDescription)
            .withOnSettingChangeCallback { value, cancel ->
                if (value.length < 3 || value.length > 30) {
                    Toast.makeText(this, "Invalid description length", Toast.LENGTH_SHORT).show()
                    cancel()
                    return@withOnSettingChangeCallback
                }
                handleImageDescription(value)
            }
            .build(settings_container)
    }

    private fun handleImageVisibility(isVisible: Boolean) {
        android_image_view?.visibility = when(isVisible) {
            true -> View.VISIBLE
            else -> View.INVISIBLE
        }
    }

    private fun handleImageDescription(description: String) {
        image_descr?.text = description
    }
}
