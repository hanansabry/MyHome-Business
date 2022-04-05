package com.app.myhomebusiness.domain.usecases;

import com.app.myhomebusiness.data.BusinessAuthenticationRepository;
import com.app.myhomebusiness.model.Business;

import androidx.lifecycle.MutableLiveData;

public class RegisterUseCase {

    private final BusinessAuthenticationRepository businessAuthenticationRepository;

    public RegisterUseCase(BusinessAuthenticationRepository businessAuthenticationRepository) {
        this.businessAuthenticationRepository = businessAuthenticationRepository;
    }

    public void execute(Business business, MutableLiveData<Business> businessMutableLiveData) {
        businessAuthenticationRepository.registerNewBusiness(business, businessMutableLiveData);
    }
}
