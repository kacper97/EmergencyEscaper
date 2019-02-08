package org.wit.emergencyescape.activities

import android.content.Intent
import org.wit.emergencyescape.models.LocationModel
import org.jetbrains.anko.AnkoLogger
import org.wit.emergencyescape.views.BasePresenter
import org.wit.emergencyescape.views.BaseView
import org.wit.emergencyescape.views.VIEW

class Presenter(view: BaseView) : BasePresenter(view), AnkoLogger {

            fun doNavigate(){
                view?.navigateTo(VIEW.MAP)
            }

}