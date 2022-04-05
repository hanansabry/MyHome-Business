package com.app.myhomebusiness.domain.usecases;

import com.app.myhomebusiness.data.BusinessAuthenticationRepository;
import com.app.myhomebusiness.model.Business;

import androidx.lifecycle.MutableLiveData;

public class AddBusinessUseCase {
    private final BusinessAuthenticationRepository businessAuthenticationRepository;

    public AddBusinessUseCase(BusinessAuthenticationRepository businessAuthenticationRepository) {
        this.businessAuthenticationRepository = businessAuthenticationRepository;
    }

    public void execute(Business business, MutableLiveData<Boolean> success) {
        businessAuthenticationRepository.addNewBusiness(business, success);
    }
}
