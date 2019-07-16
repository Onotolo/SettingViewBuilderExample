package my.onotolo.android.svb.example.settings

import my.onotolo.android.svb.example.settings.APP_SETTINGS_FILE_NAME
import my.onotolo.andrset.SettingsProvider

object AppSettingsProvider: SettingsProvider() {
    override var PREFS_NAME = APP_SETTINGS_FILE_NAME
}