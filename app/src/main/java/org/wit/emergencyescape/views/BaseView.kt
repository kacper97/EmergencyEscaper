package org.wit.emergencyescape.views

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.AnkoLogger
import org.wit.emergencyescape.views.login.LoginView
import org.wit.emergencyescape.views.register.RegistrationView

import org.wit.emergencyescape.models.LocationModel
import org.wit.emergencyescape.views.location.MapsActivity

enum class VIEW {
    LOCATION, LOGIN, REGISTER
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {

    var basePresenter: BasePresenter? = null

    // Navigate to a new activity
    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
       var intent = Intent(this, MapsActivity::class.java)
        when (view) {
            VIEW.LOCATION -> intent = Intent(this, MapsActivity::class.java)
            VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
            VIEW.REGISTER -> intent = Intent(this, RegistrationView::class.java)
        }
        if (key != "") {
            intent.putExtra(key, value)
        }
        startActivityForResult(intent, code)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun init(toolbar: Toolbar) {
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showLocation(location: LocationModel) {}
    open fun showLocations(locations: List<LocationModel>) {}

    open fun showProgress() {}
    open fun hideProgress() {}

}