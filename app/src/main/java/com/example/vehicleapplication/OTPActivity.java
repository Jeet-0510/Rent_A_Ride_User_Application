package com.example.vehicleapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    private EditText edtCode1, edtCode2, edtCode3, edtCode4, edtCode5, edtCode6;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        final ProgressBar progressBar = findViewById(R.id.progressBarOTPVerify);
        final Button btnVerify = findViewById(R.id.btnVerify);

        String mobileNumber = getIntent().getStringExtra("mobile");

        TextView txtMobileNumber = findViewById(R.id.txtMobileNumber);
        txtMobileNumber.setText(String.format("+91-%s", mobileNumber));

        edtCode1 = findViewById(R.id.inputCode1);
        edtCode2 = findViewById(R.id.inputCode2);
        edtCode3 = findViewById(R.id.inputCode3);
        edtCode4 = findViewById(R.id.inputCode4);
        edtCode5 = findViewById(R.id.inputCode5);
        edtCode6 = findViewById(R.id.inputCode6);

        setUpOTPInputs();

        verificationId = getIntent().getStringExtra("verificationId");

        btnVerify.setOnClickListener(v -> {
            if (edtCode1.getText().toString().trim().isEmpty()
                    || edtCode2.getText().toString().trim().isEmpty()
                    || edtCode3.getText().toString().trim().isEmpty()
                    || edtCode4.getText().toString().trim().isEmpty()
                    || edtCode5.getText().toString().trim().isEmpty()
                    || edtCode6.getText().toString().trim().isEmpty()) {
                Toast.makeText(OTPActivity.this, "Please enter valid code!!!", Toast.LENGTH_SHORT).show();
                return;
            }

            String code = edtCode1.getText().toString() +
                    edtCode2.getText().toString() +
                    edtCode3.getText().toString() +
                    edtCode4.getText().toString() +
                    edtCode5.getText().toString() +
                    edtCode6.getText().toString();

            if (verificationId != null) {
                progressBar.setVisibility(View.VISIBLE);
                btnVerify.setVisibility(View.INVISIBLE);

                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationId,
                        code
                );
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    btnVerify.setVisibility(View.VISIBLE);
                    if (task.isSuccessful()) {
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        if (mobileNumber.equals("1234567890") && code.equals("123456")) {
                            editor.putString("check", "trueA");
                            editor.putString("mobileNumber", mobileNumber);
                            editor.apply();

                            Intent intent = new Intent(OTPActivity.this, HomeAdmin.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            editor.putString("check", "trueU");
                            editor.putString("mobileNumber", mobileNumber);
                            editor.apply();

                            Intent intent = new Intent(OTPActivity.this, HomeUser.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(OTPActivity.this, "The verification code entered was invalid!!!", Toast.LENGTH_SHORT).show();
                    }
                });
                /*.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(OTPActivity.this, HomeUser.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });*/
            } else {
                Toast.makeText(OTPActivity.this, "Error in VerificationCode Intent!!!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.textResendOTP).setOnClickListener(v -> PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + getIntent().getStringExtra("mobile"),
                60,
                TimeUnit.SECONDS,
                OTPActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        verificationId = newVerificationId;
                        Toast.makeText(OTPActivity.this, "OTP Sent!!!", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void setUpOTPInputs() {
        edtCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}