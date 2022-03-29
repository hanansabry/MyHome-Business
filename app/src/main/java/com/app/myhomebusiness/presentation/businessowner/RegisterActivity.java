package com.app.myhomebusiness.presentation.businessowner;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.app.myhomebusiness.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegister)
    public void onRegisterClicked() {
        Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClicked() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }
}