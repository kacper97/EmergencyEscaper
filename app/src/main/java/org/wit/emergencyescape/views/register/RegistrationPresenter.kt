package org.wit.emergencyescape.views.register

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import org.wit.emergencyescape.views.BasePresenter
import org.wit.emergencyescape.views.BaseView

class RegistrationPresenter(view: BaseView) : BasePresenter(view){

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun doRegister(email: String, password: String) {
        view?.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                view?.finish()
                view?.hideProgress()
                view?.toast("Registration Successful")
            } else {
                view?.toast("Registration Failed: ${task.exception?.message}")
            }
        }
    }
}