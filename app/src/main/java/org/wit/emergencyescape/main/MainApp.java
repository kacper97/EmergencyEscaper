package org.wit.emergencyescape.main;

import java.util.ArrayList;
import java.util.List;
import android.app.Application;
import android.util.Log;
import org.wit.emergencyescape.models.LocationModel;

public class MainApp extends Application
{
    public List <LocationModel>  locations = new ArrayList<>();
    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("Escaper", "Landmark App Started");
    }
}