package com.example.phone_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout_phone_number,linearLayout_email;
    EditText editText_phone_number,editText_email,editText_password,editText_confirm_password;

    Button sign_up_phone_number_btn,sign_up_email_btn;
    TextView go_to_log_in_page_from_phone_number,go_to_log_in_page_from_email;
    RadioGroup radioGroup;
    FirebaseAuth auth,firebaseAuth;
    String user_uid;

    RadioButton radiobutton,radioButton1,radioButton2;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout_email=findViewById(R.id.linear_layout_email);
        linearLayout_phone_number=findViewById(R.id.linear_layout_phone_number);

        editText_phone_number=findViewById(R.id.phone_number_edit_text);
        editText_email=findViewById(R.id.mail_edit_text);
        editText_password=findViewById(R.id.password_edit_text);
        editText_confirm_password=findViewById(R.id.confirm_password_edit_text);

        go_to_log_in_page_from_phone_number=findViewById(R.id.go_to_sign_in_page_from_phone_number);
        go_to_log_in_page_from_email=findViewById(R.id.go_to_sign_in_page_from_email);

        sign_up_email_btn=findViewById(R.id.sign_up_email_btn);
        sign_up_phone_number_btn=findViewById(R.id.sign_up_phone_number_btn);

        radioGroup=findViewById(R.id.radio_grp);

        radioButton1=findViewById(R.id.radio_phone_number);
        radioButton2=findViewById(R.id.radio_email);

        auth=FirebaseAuth.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        go_to_log_in_page_from_phone_number.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(MainActivity.this,log_in_page.class);
                startActivity(i1);
                finish();

            }
        });
        go_to_log_in_page_from_email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(MainActivity.this,log_in_page.class);
                startActivity(i2);
                finish();

            }
        });

        if(radioButton1.isChecked())
        {
            sign_up_phone_number_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone_number_string=editText_phone_number.getText().toString();
                    String phone_number="+88"+""+phone_number_string;

                    if(phone_number_string.isEmpty())
                    {
                        editText_phone_number.setError("Please enter a phone number");
                        return;
                    }
                    else
                    {
                        if(PhoneNumberUtils.isGlobalPhoneNumber(phone_number))
                        {

                        PhoneAuthOptions option= PhoneAuthOptions.newBuilder(auth)
                                .setPhoneNumber(phone_number)
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setActivity(MainActivity.this)
                                .setCallbacks(mCallBacks)
                                .build();
                        PhoneAuthProvider.verifyPhoneNumber(option);}
                        else
                        {
                            editText_phone_number.setError("Invalid phone number");
                        }
                    }

                }
            });
        }
        //Phone Number Visibility
        radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                linearLayout_phone_number.setVisibility(View.GONE);
                linearLayout_email.setVisibility(View.VISIBLE);
                sign_up_email_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String editText_email_string=editText_email.getText().toString().trim();
                        final String editText_password_string=editText_password.getText().toString().trim();
                        String editText_confirm_password_string=editText_confirm_password.getText().toString().trim();

                        if(editText_email_string.isEmpty())
                        {
                            editText_email.setError("Please enter email");
                            return;
                        }
                        if(editText_password_string.isEmpty())
                        {
                            editText_password.setError("Please enter password");
                            return;
                        }
                        if(editText_confirm_password_string.isEmpty())
                        {
                            editText_confirm_password.setError("Please enter confirm password");
                            return;
                        }
                        if(!editText_confirm_password_string.equals(editText_password_string))
                        {
                            editText_confirm_password.setError("Passwords do not match");
                            return;
                        }
                        firebaseAuth.createUserWithEmailAndPassword(editText_email_string,editText_password_string).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {   //Send Verification link
                                    FirebaseUser fuser=auth.getCurrentUser();
                                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(MainActivity.this,"Verification email is sent",Toast.LENGTH_SHORT);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainActivity.this,"Email is not sent",Toast.LENGTH_SHORT);

                                        }
                                    });
                                    //On Back press


                                    Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user=firebaseAuth.getCurrentUser();
                                    user_uid=user.getUid();
                                    System.out.println(user_uid);
                                    HashMap<String,Object>map=new HashMap<>();
                                    map.put("Name",editText_email.getText().toString());
                                    map.put("Email",editText_password.getText().toString());
                                    map.put("Password",editText_confirm_password.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(user_uid).
                                            setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.i("dcn", "onComplete: ");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("dcn", "OnFailure "+e.toString());

                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.i("dcn", "onSuccess: ");
                                        }
                                    });

                                    editText_email.setText("");
                                    editText_password.setText("");
                                    editText_confirm_password.setText("");
                                    sendTo_Continue(editText_email_string,editText_password_string);

                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Error in registration"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
                });
            }
        });




        //Email Visibility
        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                linearLayout_phone_number.setVisibility(View.VISIBLE);
                linearLayout_email.setVisibility(View.GONE);
                sign_up_phone_number_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phone_number_string=editText_phone_number.getText().toString();
                        String phone_number="+88"+""+phone_number_string;

                        if(phone_number_string.isEmpty())
                        {
                            editText_phone_number.setError("Please enter a phone number");
                            return;
                        }
                        else
                        {
                            if(PhoneNumberUtils.isGlobalPhoneNumber(phone_number))
                            {

                                PhoneAuthOptions option= PhoneAuthOptions.newBuilder(auth)
                                        .setPhoneNumber(phone_number)
                                        .setTimeout(60L, TimeUnit.SECONDS)
                                        .setActivity(MainActivity.this)
                                        .setCallbacks(mCallBacks)
                                        .build();
                                PhoneAuthProvider.verifyPhoneNumber(option);}
                            else
                            {
                                editText_phone_number.setError("Invalid phone number");
                            }
                        }

                    }
                });

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
                Toast.makeText(MainActivity.this,"OTP SEND",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent otpIntent=new Intent(MainActivity.this,verify_otp.class);
                        otpIntent.putExtra("auth",s);
                        startActivity(otpIntent);
                    }
                },10000);

            }
        };


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null)
        {
            sendToMain();
        }
    }

    private void sendToMain()
    {
        Intent mainIntent=new Intent(MainActivity.this,main_page_app.class);
        startActivity(mainIntent);
        finish();
    }
    private void sendTo_Continue(String nameString,String addressString)
    {
        Intent mainIntent=new Intent(MainActivity.this,continue_with_log_in.class);
        mainIntent.putExtra("email",nameString);
        mainIntent.putExtra("address", addressString);
        startActivity(mainIntent);
        finish();
    }
    private void signIn(PhoneAuthCredential credential)
    {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    sendToMain();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Verification Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}