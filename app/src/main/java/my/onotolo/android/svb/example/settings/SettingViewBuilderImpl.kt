package my.onotolo.android.svb.example.settings

import android.view.View
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.setting_line_boolean.view.*
import kotlinx.android.synthetic.main.setting_line_string.view.setting_line_edit_text
import my.onotolo.android.svb.example.R
import my.onotolo.andrset.Setting
import my.onotolo.svb.*
import java.util.*


class SettingViewBuilderImpl<T : Any> constructor(setting: Setting<T>):
    SettingViewBuilder<T>(setting) {

    override val viewResources = WeakHashMap<Class<*>, Int>(mapOf(
        Boolean::class.java to R.layout.setting_line_boolean,
        String::class.java to R.layout.setting_line_string
    ))
    val boolBindFunc: BindFunction<Boolean> = {view, _, setting, callback ->

        view.setting_line_name.text = setting.getName(view.context)

        if (setting.getDescription(view.context) != null) {
            view.settings_line_descr?.text = setting.getDescription(view.context)
        } else
            view.settings_line_descr?.visibility = View.GONE

        val switch = view.settings_line_switch

        switch.isChecked = setting[view.context]

        view.setOnClickListener {

            val value = !setting[view.context]
            setting[view.context] = value

            switch.isChecked = value

            callback(value) {
                view.callOnClick()
            }
        }
        switch.setOnCheckedChangeListener { buttonView, isChecked ->

            val value = setting[view.context]
            if (value == isChecked)
                return@setOnCheckedChangeListener

            setting[buttonView.context] = isChecked
            callback(value) {
                switch.callOnClick()
            }
        }
    }
    private fun bindString(
        view: View,
        currentValue: String,
        setting: Setting<String>,
        callback: OnSettingChangeCallback<String>) {

        view.setting_line_name.text = setting.getName(view.context)

        val editText = view.setting_line_edit_text ?: return
        val context = view.context

        editText.setText(currentValue)

        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId != EditorInfo.IME_ACTION_DONE)
                return@setOnEditorActionListener false
            val value = v.text.toString()
            val previousValue = setting[context]
            setting[context] = value
            callback(value) {
                setting[context] = previousValue
                editText.setText(previousValue)
            }
            true
        }
    }

    override val bindFunctions = mapOf(
        Boolean::class.java toFunc boolBindFunc,
        String::class.java toFunc ::bindString
    )
    companion object {
        infix fun <T: Any>forSetting(setting: Setting<T>): SettingViewBuilderImpl<T> {
            return SettingViewBuilderImpl(setting)
        }
    }
}