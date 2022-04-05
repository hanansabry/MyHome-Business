package com.app.myhomebusiness.presentation.businessowner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.Toast;

import com.app.myhomebusiness.R;
import com.app.myhomebusiness.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.isLoginSuccessful().observe(this, success -> {
            if (success) {
                startActivity(new Intent(this, BusinessDashboard.class));
            } else {
                Toast.makeText(this, "Invalid credentials.., Please try again", Toast.LENGTH_SHORT).show();
            }
        });
        loginViewModel.getError().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClicked() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        loginViewModel.login(email, password);
    }

    @OnClick(R.id.btnRegister)
    public void onRegisterClicked() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }
}