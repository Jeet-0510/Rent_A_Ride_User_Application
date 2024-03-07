package com.example.vehicleapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import com.google.firebase.FirebaseException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText edtMobileNumber = findViewById(R.id.edtMobileNumber);
        Button btnGetOTP = findViewById(R.id.btnGetOTP);
        ProgressBar progressBar = findViewById(R.id.progressBarGetOTP);

        btnGetOTP.setOnClickListener(v -> {

            if (edtMobileNumber.getText().toString().trim().isEmpty()) {
                Toast.makeText(LoginActivity.this, "Enter Mobile Number!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            btnGetOTP.setVisibility(View.INVISIBLE);

            PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + edtMobileNumber.getText().toString(), 60, TimeUnit.SECONDS, LoginActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    progressBar.setVisibility(View.GONE);
                    btnGetOTP.setVisibility(View.VISIBLE);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    progressBar.setVisibility(View.GONE);
                    btnGetOTP.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    progressBar.setVisibility(View.GONE);
                    btnGetOTP.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                    intent.putExtra("mobile",edtMobileNumber.getText().toString());
                    intent.putExtra("verificationId",verificationId);
                    startActivity(intent);
                }
            });
        });
    }
}