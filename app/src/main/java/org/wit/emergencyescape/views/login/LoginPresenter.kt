package org.wit.emergencyescape.views.login

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.emergencyescape.models.LocationFireStore
import org.wit.emergencyescape.views.BasePresenter
import org.wit.emergencyescape.views.BaseView
import org.wit.emergencyescape.views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view), AnkoLogger {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun doLogin(email: String, password: String) {
        val showProgress: Any = view?.showProgress()!!
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                view?.toast("login as " + FirebaseAuth.getInstance().currentUser?.email.toString())
                view?.navigateTo(VIEW.LOCATION)
            } else {
                view?.toast("Try again-login Failed: ${task.exception?.message}")
            }
            view?.hideProgress()
        }
    }

    fun doLogIn(email: String, password: String) {
        view?.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (app.firestore != null) {
                    app.firestore!!.fetchLocation {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LOCATION)
                    }
                } else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LOCATION)
                }
            } else {
                view?.hideProgress()
                view?.toast("Logging Up Failed: ${task.exception?.message}")
            }
        }
    }


}