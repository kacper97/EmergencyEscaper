 package org.wit.emergencyescape.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main_screen.*
import org.jetbrains.anko.AnkoLogger
import org.wit.emergencyescape.R
import org.wit.emergencyescape.R.string.getMap
import org.wit.emergencyescape.views.BasePresenter
import org.wit.emergencyescape.views.BaseView
import org.wit.emergencyescape.views.VIEW


 class MainScreen :BaseView(), AnkoLogger {

     lateinit var presenter : Presenter

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main_screen)
         presenter = initPresenter (Presenter(this)) as Presenter

         toolbarMainScreen.title = getString(R.string.mainApp)
         setSupportActionBar(toolbarMainScreen)


         buttonGetMap.setOnClickListener() {
             presenter.doNavigate()
         }

         buttonGetBuildingPlan.setOnClickListener(){
             presenter.doNavigatePlan()
         }

     }

     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.menu_location, menu)
         return super.onCreateOptionsMenu(menu)
     }


     override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         when (item?.itemId) {

             R.id.navigation_settings -> {
                 navigateTo(VIEW.SETTINGS)
             }

         }
         return super.onOptionsItemSelected(item)
     }
 }
