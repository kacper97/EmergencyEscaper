package org.wit.emergencyescape

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import org.wit.emergencyescape.views.BasePresenter
import org.wit.emergencyescape.views.BaseView

class SettingsPresenter(view: BaseView) : BasePresenter(view){

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun doSaveSettings(settings_email : String, settings_password : String){

        if (settings_email.isEmpty() || settings_password.isEmpty()){
            view?.toast("Can;'t")
        } else {
            view?.toast("Not implented yet")
        }

        view?.setResult(AppCompatActivity.RESULT_OK)
        view?.finish()
    }

}