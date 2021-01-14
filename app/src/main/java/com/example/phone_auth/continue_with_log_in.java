package com.example.phone_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class continue_with_log_in extends AppCompatActivity {
    Button button_continue;
    String email,password;
    FirebaseAuth firebaseAuth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_with_log_in);
        button_continue=findViewById(R.id.button_continue);
       // Intent startingIntent = getIntent();
        //String email = startingIntent.getStringExtra("email", );
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("address");
        firebaseAuth2=FirebaseAuth.getInstance();

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            firebaseAuth2.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        if(firebaseAuth2.getCurrentUser().isEmailVerified())
                        {
                            startActivity(new Intent(continue_with_log_in.this,main_page_app.class));
                        }
                        else
                        {
                            Toast.makeText(continue_with_log_in.this,"First verify your email",Toast.LENGTH_SHORT);
                        }
                    }
                    else
                    {
                        Toast.makeText(continue_with_log_in.this,"Mail verification is not sent",Toast.LENGTH_SHORT);
                    }
                }
            });
            }
        });

    }
}