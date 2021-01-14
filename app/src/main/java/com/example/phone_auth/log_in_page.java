package com.example.phone_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    EditText editText_email_log_in,editText_password_log_in,editText_phone_number_log_in;
    Button button_sign_in_phone_number_log_in,button_sign_in_email_log_in;
    TextView go_to_sign_up_page_from_phone_number_log_in,go_to_sign_up_page_from_email_log_in;
    RadioButton radio_phone_number_log_in,radio_email_log_in;
    LinearLayout linearLayout_email_log_in,linearLayout_phone_number_log_in;
    FirebaseAuth firebaseAuth,firebaseAuth_1;
    SharedPrefernceConfiguration sharedPreferenceConfiguration;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        editText_email_log_in=findViewById(R.id.mail_edit_text_log_in);
        editText_password_log_in=findViewById(R.id.password_edit_text_log_in);
        editText_phone_number_log_in=findViewById(R.id.phone_number_edit_text_log_in);

        button_sign_in_phone_number_log_in=findViewById(R.id.button_sign_in_from_phone_number_log_in);
        button_sign_in_email_log_in=findViewById(R.id.button_sign_in_from_email_log_in);

        go_to_sign_up_page_from_phone_number_log_in=findViewById(R.id.go_to_sign_up_page_from_phone_number_log_in);
        go_to_sign_up_page_from_email_log_in=findViewById(R.id.go_to_sign_up_page_from_email_log_in);

        radio_email_log_in=findViewById(R.id.radio_email_log_in);
        radio_phone_number_log_in=findViewById(R.id.radio_phone_number_log_in);

        linearLayout_email_log_in=findViewById(R.id.linear_layout_email_log_in);
        linearLayout_phone_number_log_in=findViewById(R.id.linear_layout_phone_number_log_in);

        firebaseAuth=FirebaseAuth.getInstance();

        sharedPreferenceConfiguration=new SharedPrefernceConfiguration(getApplicationContext());

        if(sharedPreferenceConfiguration.read_login_status())
        {
            startActivity(new Intent(getApplicationContext(),main_page_app.class));
            finish();
        }


        go_to_sign_up_page_from_phone_number_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent_1_log_in=new Intent(log_in_page.this,MainActivity.class);
                startActivity(mainIntent_1_log_in);
                finish();
            }
        });

        go_to_sign_up_page_from_email_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent_2_log_in=new Intent(log_in_page.this,MainActivity.class);
                startActivity(mainIntent_2_log_in);
                finish();
            }
        });
        radio_phone_number_log_in.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                linearLayout_phone_number_log_in.setVisibility(View.GONE);
                linearLayout_email_log_in.setVisibility(View.VISIBLE);

            }
        });
        button_sign_in_email_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String editText_email_string_log_in=editText_email_log_in.getText().toString().trim();
                final String editText_password_string_log_in=editText_password_log_in.getText().toString().trim();
                if(editText_email_string_log_in.isEmpty())
                {
                    editText_email_log_in.setError("Please enter email");
                    return;
                }
                if(editText_password_string_log_in.isEmpty())
                {
                    editText_password_log_in.setError("Please enter password");
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(editText_email_string_log_in,editText_password_string_log_in).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(log_in_page.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            editText_email_log_in.setText("");
                            editText_password_log_in.setText("");
                            sharedPreferenceConfiguration.login_status(true);
                            startActivity(new Intent(getApplicationContext(),main_page_app.class));
                            finish();


                        }
                        else
                        {
                            Toast.makeText(log_in_page.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            editText_email_log_in.setText("");
                            editText_password_log_in.setText("");
                        }
                    }
                });

            }
        });
        radio_email_log_in.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                linearLayout_phone_number_log_in.setVisibility(View.VISIBLE);
                linearLayout_email_log_in.setVisibility(View.GONE);

            }
        });

        button_sign_in_phone_number_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number_string=editText_phone_number_log_in.getText().toString();
                String phone_number="+88"+""+phone_number_string;

                if(phone_number_string.isEmpty())
                {
                    editText_phone_number_log_in.setError("Please enter a phone number");
                    return;
                }
                else
                {

                    if(PhoneNumberUtils.isGlobalPhoneNumber(phone_number))
                    {

                        PhoneAuthOptions option= PhoneAuthOptions.newBuilder(firebaseAuth_1)
                                .setPhoneNumber(phone_number)
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setActivity(log_in_page.this)
                                .setCallbacks(mCallBacks)
                                .build();
                        PhoneAuthProvider.verifyPhoneNumber(option);}
                    else
                    {
                        editText_phone_number_log_in.setError("Invalid phone number");
                    }
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
        };
    }
    private void sendToMain()
    {
        Intent mainIntent=new Intent(log_in_page.this,main_page_app.class);
        startActivity(mainIntent);
        finish();
    }
    private void signIn(PhoneAuthCredential credential)
    {
        firebaseAuth_1.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    sendToMain();
                    sharedPreferenceConfiguration.login_status(true);
                }
                else
                {
                    Toast.makeText(log_in_page.this,"Verification Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}