package my.onotolo.android.svb.example.settings

import my.onotolo.android.svb.example.R
import my.onotolo.andrset.Setting

const val APP_SETTINGS_FILE_NAME = "AppSettings"

sealed class ApplicationSetting<T>: Setting<T>() {
    override val settingsProvider = AppSettingsProvider
}
sealed class ApplicationEnumSetting<T>: Setting<T>() {
    override val settingsProvider = AppSettingsProvider
}

object IsImageShown: ApplicationSetting<Boolean>() {

    override val settingNameResId = R.string.set_show_image

    override val descriptionResId = R.string.set_show_image_descr

    override val id: String = "Is image shown"
    override val defaultValue = true
}

object ImageDescription: ApplicationSetting<String>() {

    override val settingNameResId = R.string.set_image_description

    override val descriptionResId = null

    override val id: String = "Image description"
    override val defaultValue = "This is image"
}
