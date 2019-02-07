package org.wit.emergencyescape.views.location

import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import org.wit.emergencyescape.R
import org.wit.emergencyescape.views.BaseView

class ocationView : BaseView(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
    }
}