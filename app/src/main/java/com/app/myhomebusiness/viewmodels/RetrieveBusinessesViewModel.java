package com.app.myhomebusiness.viewmodels;

import com.app.myhomebusiness.Injection;
import com.app.myhomebusiness.domain.usecases.RetrieveBusinessesUseCase;
import com.app.myhomebusiness.model.Business;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RetrieveBusinessesViewModel extends ViewModel {

    private final RetrieveBusinessesUseCase retrieveBusinessesUseCase;
    private MutableLiveData<List<Business>> businessListMutableLiveData = new MutableLiveData<>();

    public RetrieveBusinessesViewModel() {
        retrieveBusinessesUseCase = Injection.getRetrieveBusinessesUseCase();
    }

    public void retrieveAllBusinesses() {
        retrieveBusinessesUseCase.execute(businessListMutableLiveData);
    }

    public MutableLiveData<List<Business>> getBusinessListMutableLiveData() {
        return businessListMutableLiveData;
    }
}
