package org.wit.emergencyescape

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_settings.*
import org.wit.emergencyescape.views.BaseView


class SettingsView : BaseView() {

    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = initPresenter (SettingsPresenter(this)) as SettingsPresenter


        settings_email.setBackground(null)
        settings_email.setKeyListener(null)

        settings_email.setText(presenter.auth.currentUser?.email)
    }

}