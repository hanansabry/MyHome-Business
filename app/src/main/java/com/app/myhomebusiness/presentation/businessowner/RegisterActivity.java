package com.app.myhomebusiness.presentation.businessowner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.app.myhomebusiness.R;
import com.app.myhomebusiness.viewmodels.RegisterViewModel;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterActivity extends AppCompatActivity implements IPickResult {

    @BindView(R.id.editTextBusiness)
    EditText editTextBusiness;
    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.editTextPhone)
    EditText editTextPhone;
    @BindView(R.id.editTextCity)
    EditText editTextCity;
    @BindView(R.id.editTextAddress)
    EditText editTextAddress;
    @BindView(R.id.editTextBannerUri)
    EditText editTextBannerUri;
    private RegisterViewModel registerViewModel;
    private ProgressDialog pd;
    private String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        pd = new ProgressDialog(this);
        pd.setTitle("Loading");

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        //registration
        registerViewModel.getRegisterBusinessLiveData().observe(this, business -> {
            if (business == null) {
                Toast.makeText(this, "Error while registration , please try again later!", Toast.LENGTH_SHORT).show();
                pd.hide();
            } else {
                registerViewModel.addNewBusiness(business);
            }
        });
        //add business
        registerViewModel.getAddBusinessSuccessLiveData().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this, BusinessDashboard.class));
            } else {
                Toast.makeText(this, "Error while registration , please try again later!", Toast.LENGTH_SHORT).show();
            }
            pd.hide();
        });

        //errors
        registerViewModel.getBusinessNameError().observe(this, error -> {
            editTextBusiness.setError(error);
            pd.hide();
        });
        registerViewModel.getEmailError().observe(this, error -> {
            editTextEmail.setError(error);
            pd.hide();
        });
        registerViewModel.getPasswordError().observe(this, error -> {
            editTextPassword.setError(error);
            pd.hide();
        });
        registerViewModel.getPhoneError().observe(this, error -> {
            editTextPhone.setError(error);
            pd.hide();
        });
        registerViewModel.getCityError().observe(this, error -> {
            editTextCity.setError(error);
            pd.hide();
        });
        registerViewModel.getAddressError().observe(this, error -> {
            editTextAddress.setError(error);
            pd.hide();
        });
        registerViewModel.getBannerUriError().observe(this, error -> {
            editTextBannerUri.setError(error);
            pd.hide();
        });
    }

    @OnClick(R.id.btnRegister)
    public void onRegisterClicked() {
        String name = editTextBusiness.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String phone = editTextPhone.getText().toString();
        String city = editTextCity.getText().toString();
        String address = editTextAddress.getText().toString();
        imageUri = editTextBannerUri.getText().toString();

        registerViewModel.register(name, email, password, phone, city, address, imageUri);
        pd.show();
    }

    @OnClick(R.id.editTextBannerUri)
    public void onSelectImageClicked() {
        PickImageDialog.build(new PickSetup()).show(this);
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClicked() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnTextChanged(R.id.editTextBusiness)
    public void removeBusinessNameError() {
        editTextBusiness.setError(null);
    }

    @OnTextChanged(R.id.editTextEmail)
    public void removeEmailError() {
        editTextEmail.setError(null);
    }

    @OnTextChanged(R.id.editTextPassword)
    public void removePasswordError() {
        editTextPassword.setError(null);
    }

    @OnTextChanged(R.id.editTextPhone)
    public void removePhoneError() {
        editTextPhone.setError(null);
    }

    @OnTextChanged(R.id.editTextCity)
    public void removeCityError() {
        editTextCity.setError(null);
    }

    @OnTextChanged(R.id.editTextAddress)
    public void removeAddressError() {
        editTextAddress.setError(null);
    }

    @OnTextChanged(R.id.editTextBannerUri)
    public void removeBannerUriError() {
        editTextBannerUri.setError(null);
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            editTextBannerUri.setText(r.getPath());
            imageUri = r.getUri().toString();
        } else {
            //Handle possible errors
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}