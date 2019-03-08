package org.wit.emergencyescape.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import org.wit.emergencyescape.R;
import org.wit.emergencyescape.main.MainApp;


public class Base extends AppCompatActivity {
    public MainApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MainApp) getApplication();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    public void menuInfo(MenuItem m)
    {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.appAbout))
                .setMessage(getString(R.string.appDesc)
                        + "\n\n"
                        + getString(R.string.appMoreInfo))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // we could put some code here too
                    }
                })
                .show();
    }

    public void menuHelp(MenuItem m)
    {
        startActivity(new Intent(this,Help.class));
    }

    public void menuChangePassword(MenuItem m)
    {
        startActivity(new Intent(this, ChangePassword.class));
    }

    public void menudeactivate(MenuItem m){startActivity(new Intent(this, Deactivate.class));}
}
