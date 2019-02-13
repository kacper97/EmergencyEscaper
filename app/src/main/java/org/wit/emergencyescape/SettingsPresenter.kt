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
        if (password.isEmpty()){
            view?.toast(R.string.currentPassword)
        }
        if (settings_passwordConfirm.isEmpty() || settings_passwordConfirm != settings_password){
            view?.toast(R.string.confirmPassword)
        }
        if (settings_email.isEmpty() || settings_password.isEmpty()) {
            view?.toast(R.string.hintEmailPassword)
        } else user!!.updatePassword(settings_password).addOnCompleteListener {
            user.updateEmail(settings_email)
            view?.toast(R.string.updatedPassword)
            view?.setResult(AppCompatActivity.RESULT_OK)
            view?.finish()
        }
    }

}
