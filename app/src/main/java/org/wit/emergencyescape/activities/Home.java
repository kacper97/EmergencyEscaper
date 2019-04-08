package org.wit.emergencyescape.activities;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import org.wit.emergencyescape.R;

public class Home extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMainScreen);
        setSupportActionBar(toolbar);
        isMapPermissionGranted();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void map(View v) {
        startActivity(new Intent(this, MapsActivity.class));
    }

    public void buildingplan(View v) {
        startActivity(new Intent(this,BuildingPlanActivity.class));
    }

    public void bluetooth(View v) {
        startActivity(new Intent(this,BluetoothConnector.class));
    }


    public boolean isMapPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return false;
            }
        } else {
            return true;
        }
    }


}