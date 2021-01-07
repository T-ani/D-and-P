package com.example.phone_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class log_in_page extends AppCompatActivity {

    TextView go_to_sign_up_page;
    Button button_sign_in;
    EditText edit_text_password_sign_in,edit_text_phone_number_sign_in;
    FirebaseAuth m_Auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        m_Auth = FirebaseAuth.getInstance();

        go_to_sign_up_page=findViewById(R.id.go_to_sign_up_page);
        button_sign_in=findViewById(R.id.button_sign_in);

        edit_text_password_sign_in=findViewById(R.id.edit_text_password_sign_in);
        edit_text_phone_number_sign_in=findViewById(R.id.edit_text_phone_number_sign_in);

        go_to_sign_up_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_main1=new Intent(log_in_page.this,MainActivity.class);
                startActivity(intent_main1);
                finish();
            }
        });

        /*button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number_email_sign_in_string=edit_text_phone_number_sign_in.getText().toString().trim();
                String password_sign_in_string=edit_text_password_sign_in.getText().toString().trim();

                if(phone_number_email_sign_in_string.isEmpty())
                {
                    edit_text_phone_number_sign_in.setError("Please enter phone number or email");
                    return;
                }
                if(password_sign_in_string.isEmpty())
                {
                    edit_text_password_sign_in.setError("Please enter the password");
                    return;
                }
                if(PhoneNumberUtils.isGlobalPhoneNumber(phone_number_email_sign_in_string))
                {
                String phone_number_sign_in="+88"+""+phone_number_email_sign_in_string;
                PhoneAuthOptions option= PhoneAuthOptions.newBuilder(m_Auth)
                        .setPhoneNumber(phone_number_sign_in)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(log_in_page.this)
                        .setCallbacks(mCallBacks)
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(option);
                }

            }
        });
        mCallBacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull final String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(log_in_page.this,"OTP SEND",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent otpIntent=new Intent(log_in_page.this,verify_otp.class);
                        otpIntent.putExtra("auth",s);
                        startActivity(otpIntent);
                    }
                },10000);

            }
        };*/
    }
    /*@Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=m_Auth.getCurrentUser();
        if(user!=null)
        {
            sendToMain();
        }
    }

    private void sendToMain()
    {
        Intent mainIntent=new Intent(log_in_page.this,main_page_app.class);
        startActivity(mainIntent);
        finish();
    }
    private void signIn(PhoneAuthCredential credential)
    {
        m_Auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    sendToMain();
                }
                else
                {
                    Toast.makeText(log_in_page.this,"Verification Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/
}