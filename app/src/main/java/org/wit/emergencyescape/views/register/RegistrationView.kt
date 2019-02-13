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
import org.wit.emergencyescape.views.login.LoginView

class RegistrationView : BaseView(), AnkoLogger {

    lateinit var presenter: RegistrationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        toolbarRegister.title = getString(R.string.reg_title)
        setSupportActionBar(toolbarRegister)

        presenter = initPresenter(RegistrationPresenter(this)) as RegistrationPresenter

        button_register.setOnClickListener {
            val email = regEmail.text.toString()
            val password = regPassword.text.toString()
            if (email == "" || password == "") {
                toast(R.string.hintEmailPassword)
            } else {
                presenter.doRegister(email, password)
            }
        }

    }
}