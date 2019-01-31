package org.wit.emergencyescape.views.register

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_registration.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

import org.wit.emergencyescape.R
import org.wit.emergencyescape.views.BaseView
import org.wit.emergencyescape.views.VIEW

class RegistrationView : BaseView(), AnkoLogger {

    lateinit var presenter: RegistrationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        toolbarRegister.title = getString(R.string.reg_title)
        setSupportActionBar(toolbarRegister)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = initPresenter (RegistrationPresenter(this)) as RegistrationPresenter

        button_register.setOnClickListener() {

            val email = regEmail.text.toString()
            val password = regPassword.text.toString()
            presenter.doRegister(email, password)

        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            R.id.reg_cancel -> {
                navigateTo(VIEW.LOGIN)
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
}