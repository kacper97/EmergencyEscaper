package org.wit.emergencyescape.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import org.wit.emergencyescape.R;

public class Home extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMainScreen);
        setSupportActionBar(toolbar);
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


}