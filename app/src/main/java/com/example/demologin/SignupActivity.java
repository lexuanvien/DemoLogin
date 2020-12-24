package com.example.demologin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignupActivity extends AppCompatActivity {
    Button btnContinue;
    EditText edtphoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        edtphoneNumber = findViewById(R.id.edtPhoneNumber);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtphoneNumber.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignupActivity.this, "Nhập số điện thoại",Toast.LENGTH_SHORT).show();
                    return;
                }
                sendOTP();
            }
        });
    }
    public void sendOTP(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+84"+edtphoneNumber.getText().toString().trim(),
                60, TimeUnit.SECONDS,
                SignupActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String vertificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Intent intent = new Intent(SignupActivity.this, VerifyOtpActivity.class);
                        intent.putExtra("phone_number",edtphoneNumber.getText().toString());
                        intent.putExtra("verifytionId",vertificationId);
                        startActivity(intent);
                    }
                }
        );
    }
}