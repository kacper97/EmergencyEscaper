package org.wit.emergencyescape.activities;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import org.wit.emergencyescape.R;

public class Help extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHelp);
        setSupportActionBar(toolbar);
    }
}