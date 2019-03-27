package org.wit.emergencyescape.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import org.wit.emergencyescape.R;


public class BuildingPlanActivity extends FragmentActivity {
    BuildingPlanView grid;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_buildingplan);
           // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBuildingPlan);
            Button start = (Button) findViewById(R.id.start);
            Button stop = (Button) findViewById(R.id.stop);
            Button dijkstra = (Button) findViewById(R.id.dijkstra);
            final TextView results = (TextView) findViewById(R.id.results);
            grid = (BuildingPlanView) findViewById(R.id.grid);


            start.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    grid.stopClicked=0;
                    grid.startClicked=1;
                }
            });
            stop.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    grid.startClicked=0;
                    grid.stopClicked=1;
                }
            });
            dijkstra.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String text=grid.Dijkstra();
                    results.setText(text);
                }
            });
        }

        @Override
        protected void onStop() {
            if(grid.dijkstrathread.getStatus()== AsyncTask.Status.RUNNING)
                grid.dijkstrathread.cancel(true);
            super.onStop();
        }
}



