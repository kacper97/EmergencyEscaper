package org.wit.emergencyescape.activities;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import org.wit.emergencyescape.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class BuildingPlanActivity extends FragmentActivity {
    BuildingPlanView grid;
    View view;
    Bitmap bitmap;
    File file;
    // Creating Separate Directory for saving Generated Images
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/BuildingPlans/";
    String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    String StoredPath = DIRECTORY + pic_name + ".png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildingplan);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBuildingPlan);
        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);
        Button dijkstra = (Button) findViewById(R.id.dijkstra);
        final Button save = (Button) findViewById(R.id.save);
        final TextView results = (TextView) findViewById(R.id.results);
        grid = (BuildingPlanView) findViewById(R.id.grid);
        view = grid;

        // Method to create Directory, if the Directory doesn't exists
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }


        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                grid.stopClicked = 0;
                grid.startClicked = 1;
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                grid.startClicked = 0;
                grid.stopClicked = 1;
            }
        });
        dijkstra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String text = grid.Dijkstra();
                results.setText(text);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isStoragePermissionGranted();
                view.setDrawingCacheEnabled(true);
                save(view, StoredPath);
                Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
            }
        });

        }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }



    @Override
    protected void onStop() {
        if (grid.dijkstrathread.getStatus() == AsyncTask.Status.RUNNING)
            grid.dijkstrathread.cancel(true);
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            grid.setDrawingCacheEnabled(true);
            save(view, StoredPath);
            Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
            // Calling the same class
            recreate();
        }
        else
        {
            Toast.makeText(this, "The app was not allowed to write to your storage- grant it this permission", Toast.LENGTH_LONG).show();
        }
    }

    public void save(View v, String StoredPath) {
        Log.v("log_tag", "Width: " + v.getWidth());
        Log.v("log_tag", "Height: " + v.getHeight());

        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(grid.getWidth(), grid.getHeight(), Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        try {
            // Output the file
            FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
            v.draw(canvas);

            // Convert the output file to Image such as .png
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
            mFileOutStream.flush();
            mFileOutStream.close();

        } catch (Exception e) {
            Log.v("log_tag", e.toString());
        }

    }

}



