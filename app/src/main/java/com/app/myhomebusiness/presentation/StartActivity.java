package com.app.myhomebusiness.presentation;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;

import com.app.myhomebusiness.R;
import com.app.myhomebusiness.presentation.businessowner.LoginActivity;
import com.app.myhomebusiness.presentation.businessowner.RegisterActivity;
import com.app.myhomebusiness.presentation.client.ClientHomeActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnGuest)
    public void onContinueAsGuestClicked() {
        startActivity(new Intent(this, ClientHomeActivity.class));
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClicked() {
        startActivity(new Intent(this, LoginActivity.class));
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