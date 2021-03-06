package org.wit.emergencyescape.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import org.wit.emergencyescape.R;
import org.wit.emergencyescape.activities.LogIn;

public class SplashScreen extends Activity {

    //Duration of wait
    private final int SPLASH_DISPLAY_LENGTH = 3000; // 3 sec

    //Called when the activity is first created
    @Override
    public void onCreate(Bundle i) {
        super.onCreate(i);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create an Intent that will start the Menu-Activity.
                Intent mainIntent = new Intent(SplashScreen.this,LogIn.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}