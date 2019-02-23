package org.wit.emergencyescape

import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration.*
import org.jetbrains.anko.toast
import org.wit.emergencyescape.views.BasePresenter
import org.wit.emergencyescape.views.BaseView

class SettingsPresenter(view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val user =  FirebaseAuth.getInstance().currentUser

    fun doSaveSettings(settings_email: String,password: String, settings_password: String, settings_passwordConfirm: String) {
        if (settings_email.isEmpty() || password.isEmpty() || settings_password.isEmpty() || settings_passwordConfirm.isEmpty()) {
            view?.toast(R.string.emptyBoxes)
            view?.finish()


        }
            else if (settings_password != settings_passwordConfirm) {
                view?.toast(R.string.passwords_not_same)
                view?.finish()
            }

        else{
            user!!.updatePassword(settings_password).addOnCompleteListener {
                user.updatePassword(settings_password)
                auth.updateCurrentUser(user)
                view?.toast(R.string.updatedPassword)
                view?.setResult(AppCompatActivity.RESULT_OK)
                view?.finish()
            }
        }
    }

}
