package com.android.selvaraj.displaybus.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.selvaraj.displaybus.R;
import com.android.selvaraj.displaybus.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private TextInputEditText etName, etPassword, etEmail, etRollNo, etPhone;
    private TextInputLayout tilName, tilPassword, tillEmail, tillRollNo, tillPhone;
    private String name, email, rollNo, password, phone;
    private ConstraintLayout clSignUp;
    private Button btnSignUp, btnClear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_sign_up);
        etEmail = findViewById(R.id.et_email_sp);
        etPassword = findViewById(R.id.et_pass_sp);
        etName = findViewById(R.id.et_sign_up_name);
        etRollNo = findViewById(R.id.et_rollno);
        etPhone = findViewById(R.id.et_phone);
        btnSignUp = findViewById(R.id.btn_sign_up);
        btnClear = findViewById(R.id.btn_clear);
        tilName = findViewById(R.id.til_name_sp);
        tillPhone = findViewById(R.id.til_phone);
        tillRollNo = findViewById(R.id.til_roll_sp);
        tillEmail = findViewById(R.id.til_email_sp);
        tilPassword = findViewById(R.id.til_pass_sp);
        clSignUp = findViewById(R.id.cl_sign_up);
        progressBar = findViewById(R.id.progressBar_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        btnClear.setOnClickListener(this);
        progressBar.setVisibility(View.INVISIBLE);
        btnSignUp.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up:
                if (getInputs()) {
                    progressBar.setVisibility(View.VISIBLE);
                    requestOtp();
                }
                break;
            case R.id.btn_clear:
                clearText();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearText() {
        etName.setText("");
        etPassword.setText("");
        etPhone.setText("");
        etRollNo.setText("");
        etEmail.setText("");
    }

    private void requestOtp() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.getDefault());
        String strDate = dateFormat.format(date);
        final Student student = new Student(name, rollNo, email, password, phone, strDate);
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                            databaseReference = firebaseDatabase.getReference("Students" + "/" + userId);
                            databaseReference.setValue(student);
                            phone="+91"+phone;
                            Intent intent = new Intent(SignUpActivity.this, OtpActivity.class);
                            intent.putExtra("Phone", phone);
                            startActivity(intent);
                            progressBar.setVisibility(View.INVISIBLE);
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Snackbar.make(clSignUp, "Something went wrong", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean getInputs() {
        try {
            name = Objects.requireNonNull(etName.getText()).toString();
            password = Objects.requireNonNull(etPassword.getText()).toString();
            email = Objects.requireNonNull(etEmail.getText()).toString();
            rollNo = Objects.requireNonNull(etRollNo.getText()).toString();
            phone = Objects.requireNonNull(etPhone.getText()).toString();
            if (name.isEmpty()) {
                tilName.setEnabled(true);
                tilName.setError("Enter valid name");
                return false;
            }
            if (password.isEmpty() || password.length() < 6) {
                tilPassword.setEnabled(true);
                tilPassword.setError("Enter valid password");
                return false;
            }
            if (email.isEmpty()) {
                tillEmail.setEnabled(true);
                tillEmail.setError("Enter valid email");
                return false;
            }
            if (rollNo.isEmpty()) {
                tillRollNo.setEnabled(true);
                tillRollNo.setError("Enter valid roll number");
                return false;
            }
            if (phone.isEmpty()) {
                tillPhone.setEnabled(true);
                tillPhone.setError("Enter valid phone number");
                return false;
            }
            return true;
        } catch (NullPointerException e) {
            Toast.makeText(this, "Enter valid inputs", Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
