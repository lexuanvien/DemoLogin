package com.example.demologin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyOtpActivity extends AppCompatActivity {
    TextView txt_sdt;
    EditText edt_OTP;
    Button btnVerify;
    String otp;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        getSupportActionBar().hide();

        txt_sdt = findViewById(R.id.txt_sdt);
        txt_sdt.setText(getIntent().getStringExtra("phone_number"));
        otp = getIntent().getStringExtra("verifytionId");
        edt_OTP = findViewById(R.id.edt_OTP);
        mAuth = FirebaseAuth.getInstance();
        btnVerify = findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_OTP.getText().toString().isEmpty()){
                    Toast.makeText(VerifyOtpActivity.this,"Nhập mã OTP",Toast.LENGTH_SHORT).show();
                }
                else if (edt_OTP.getText().toString().trim().length()!=6){
                    Toast.makeText(VerifyOtpActivity.this,"Mã OTP không đúng",Toast.LENGTH_SHORT).show();
                }
                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otp,edt_OTP.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (mAuth!=null){
                    Intent intent = new Intent(VerifyOtpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Đăng nhập sai",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}