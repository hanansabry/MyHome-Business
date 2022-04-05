package com.app.myhomebusiness.data;

import com.app.myhomebusiness.model.Business;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public interface BusinessAuthenticationRepository {

    void loginWithBusinessCredentials(String email, String password, MutableLiveData<Boolean> success);

    void registerNewBusiness(Business business, MutableLiveData<Business> businessMutableLiveData);

    void addNewBusiness(Business business, MutableLiveData<Boolean> success);

    void retrieveAllBusinesses(MutableLiveData<List<Business>> businessesListMutableLiveData);

}
