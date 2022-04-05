package com.app.myhomebusiness.domain.usecases;

import com.app.myhomebusiness.data.BusinessAuthenticationRepository;
import com.app.myhomebusiness.model.Business;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class RetrieveBusinessesUseCase {

    private final BusinessAuthenticationRepository businessAuthenticationRepository;

    public RetrieveBusinessesUseCase(BusinessAuthenticationRepository businessAuthenticationRepository) {
        this.businessAuthenticationRepository = businessAuthenticationRepository;
    }

    public void execute(MutableLiveData<List<Business>> businessListMutableLiveData) {
        businessAuthenticationRepository.retrieveAllBusinesses(businessListMutableLiveData);
    }
}
