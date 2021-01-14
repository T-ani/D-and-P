package com.example.phone_auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class main_page_app extends AppCompatActivity {

    Button log_out_button;
    FirebaseAuth firebaseAuth;
    SharedPrefernceConfiguration sharedPreferenceConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_app);
        log_out_button=findViewById(R.id.log_out_button);
        firebaseAuth=FirebaseAuth.getInstance();
        sharedPreferenceConfig=new SharedPrefernceConfiguration(getApplicationContext());


        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                sharedPreferenceConfig.login_status(false);
                startActivity(new Intent(main_page_app.this,MainActivity.class));
                finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser==null)
        {
            startActivity(new Intent(main_page_app.this,MainActivity.class));
            finish();
        }
    }
}