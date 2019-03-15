package org.wit.emergencyescape.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.wit.emergencyescape.R;

public class ChangePassword extends AppCompatActivity {
    TextView email;
    EditText password;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        dialog = new ProgressDialog(this);
        password = (EditText) findViewById(R.id.settings_password);
        email = (TextView)findViewById(R.id.settings_email);
        auth = FirebaseAuth.getInstance();
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    public void change(View V){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            dialog.setMessage("Changing password, please wait");
            dialog.show();
            user.updatePassword(password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Your password has been changed",Toast.LENGTH_SHORT).show();
                                auth.signOut();
                                finish();
                                Intent l = new Intent(ChangePassword.this, LogIn.class);
                                startActivity(l);
                            }

                            else{
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Password inputed incorrectly",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        }
    }
