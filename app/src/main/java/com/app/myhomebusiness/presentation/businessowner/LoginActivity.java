package com.app.myhomebusiness.presentation.businessowner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.app.myhomebusiness.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClicked() {
        Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, BusinessDashboard.class));
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