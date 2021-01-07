package com.example.phone_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class verify_otp extends AppCompatActivity {

    EditText edit_text_verification_code;
    Button verirfy_otp_button;
    String OTP;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        edit_text_verification_code=findViewById(R.id.edit_text_verification_code);
        verirfy_otp_button=findViewById(R.id.verirfy_otp_button);
        firebaseAuth=FirebaseAuth.getInstance();

        OTP=getIntent().getStringExtra("auth");
        verirfy_otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verification_code=edit_text_verification_code.getText().toString();
                if(!verification_code.isEmpty())
                {
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(OTP,verification_code);
                    signIn(credential);
                }
                else
                {
                    Toast.makeText(verify_otp.this,"Please Enter OTP",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void signIn(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    sendToMain();
                }
                else
                {
                    Toast.makeText(verify_otp.this,"Verification Failed",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null)
        {
            sendToMain();
        }
    }

    private void sendToMain()
    {
        startActivity(new Intent(verify_otp.this,main_page_app.class));
        finish();
    }
}