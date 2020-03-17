package com.android.selvaraj.displaybus.activity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.selvaraj.displaybus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = LoginActivity.class.getSimpleName();
    private int count = 0;
    private Button btnLogin;
    private TextInputEditText etEmail, etPassword;
    private TextInputLayout tilEmail, tilPassword;
    private String email, password;
    private ProgressBar loginProgress;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    private void initViews() {
        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);
        loginProgress = findViewById(R.id.progress_bar_login);
        loginProgress.setVisibility(View.INVISIBLE);
        tilEmail = findViewById(R.id.til_email);
        tilPassword = findViewById(R.id.til_pass);
        tvSignUp = findViewById(R.id.tv_sign_up);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (getValidNames()) {
                    loginProgress.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                            email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startIntent();
                            } else {
                                loginProgress.setVisibility(View.INVISIBLE);
                                count = count + 1;
                                Log.d(TAG, "firebase auth failed");
                                if (count == 2) {
                                    showInvalidAlert();
                                }
                            }
                        }
                    });

                }
                break;
            case R.id.tv_sign_up:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
        }
    }

    private void showInvalidAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Forgot Password?")
                .setMessage(" Firebase auth failed..Seems like you forgot your password..\n Please contact Your Admin for Details!!")
                .setNeutralButton(R.string.ok, null);
        builder.show();
        count = 0;
    }

    private void startIntent() {
        Intent intent = new Intent(this, BusListActivity.class);
        intent.putExtra("Name", email);
        startActivity(intent);
        finish();
    }

    private boolean getValidNames() {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";
        if (TextUtils.isEmpty(email)) {
            tilEmail.setEnabled(true);
            tilEmail.setError("Invalid Email");
            return false;
        }
        if (password.length() < 6) {
            tilPassword.setEnabled(true);
            tilPassword.setError("Enter Valid password");
            etPassword.setError("Enter Valid password");
            return false;
        }
        return true;
    }
}
