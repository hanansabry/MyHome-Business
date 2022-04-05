package com.app.myhomebusiness.viewmodels;

import com.app.myhomebusiness.Injection;
import com.app.myhomebusiness.domain.usecases.RetrieveOrdersUseCase;
import com.app.myhomebusiness.model.BusinessOrder;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RetrieveBusinessOrdersViewModel extends ViewModel {

    private final RetrieveOrdersUseCase retrieveOrdersUseCase;
    private MutableLiveData<List<BusinessOrder>> ordersListMutableLiveData = new MutableLiveData<>();

    public RetrieveBusinessOrdersViewModel() {
        retrieveOrdersUseCase = Injection.getRetrieveOrdersUseCase();
    }

    public void retrieveOrders() {
        retrieveOrdersUseCase.execute(ordersListMutableLiveData);
    }

    public MutableLiveData<List<BusinessOrder>> getOrdersListMutableLiveData() {
        return ordersListMutableLiveData;
    }
}
