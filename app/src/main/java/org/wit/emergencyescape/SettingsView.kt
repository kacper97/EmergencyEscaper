package org.wit.emergencyescape

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_settings.*
import org.wit.emergencyescape.views.BaseView
import org.wit.emergencyescape.views.VIEW


class SettingsView : BaseView() {

    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = initPresenter (SettingsPresenter(this)) as SettingsPresenter

        settings_email.setText(presenter.auth.currentUser?.email)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            R.id.item_settings_save -> {
                presenter.doSaveSettings(settings_email.toString(), settings_password.toString())
            }

        }
        return super.onOptionsItemSelected(item)
    }


}