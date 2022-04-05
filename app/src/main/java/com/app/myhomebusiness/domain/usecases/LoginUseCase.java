package com.app.myhomebusiness.domain.usecases;

import com.app.myhomebusiness.data.BusinessAuthenticationRepository;

import androidx.lifecycle.MutableLiveData;

public class LoginUseCase {

    private final BusinessAuthenticationRepository businessAuthenticationRepository;

    public LoginUseCase(BusinessAuthenticationRepository businessAuthenticationRepository) {
        this.businessAuthenticationRepository = businessAuthenticationRepository;
    }

    public void execute(String email, String password, MutableLiveData<Boolean> success) {
        businessAuthenticationRepository.loginWithBusinessCredentials(email, password, success);
    }
}
