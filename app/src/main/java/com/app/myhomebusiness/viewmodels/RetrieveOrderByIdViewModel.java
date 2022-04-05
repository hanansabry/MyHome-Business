package com.app.myhomebusiness.viewmodels;

import com.app.myhomebusiness.Injection;
import com.app.myhomebusiness.domain.usecases.RetrieveOrderByIdUseCase;
import com.app.myhomebusiness.model.BusinessOrder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RetrieveOrderByIdViewModel extends ViewModel {

    private final RetrieveOrderByIdUseCase retrieveOrderByIdUseCase;
    private MutableLiveData<BusinessOrder> businessOrderMutableLiveData = new MutableLiveData<>();

    public RetrieveOrderByIdViewModel() {
        retrieveOrderByIdUseCase = Injection.getRetrieveOrderByIdUseCase();
    }

    public void retrieveOrderById(String orderId) {
        retrieveOrderByIdUseCase.execute(orderId, businessOrderMutableLiveData);
    }

    public void retrieveOrderByPhone(String phone) {
        retrieveOrderByIdUseCase.executeByPhone(phone, businessOrderMutableLiveData);
    }

    public MutableLiveData<BusinessOrder> getBusinessOrderMutableLiveData() {
        return businessOrderMutableLiveData;
    }
}
