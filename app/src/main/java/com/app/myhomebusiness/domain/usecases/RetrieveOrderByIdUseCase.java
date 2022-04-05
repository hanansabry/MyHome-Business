package com.app.myhomebusiness.domain.usecases;

import com.app.myhomebusiness.data.BusinessOrdersRepository;
import com.app.myhomebusiness.model.BusinessOrder;

import androidx.lifecycle.MutableLiveData;

public class RetrieveOrderByIdUseCase {

    private final BusinessOrdersRepository businessOrdersRepository;

    public RetrieveOrderByIdUseCase(BusinessOrdersRepository businessOrdersRepository) {
        this.businessOrdersRepository = businessOrdersRepository;
    }

    public void execute(String orderId, MutableLiveData<BusinessOrder> businessOrderMutableLiveData) {
        businessOrdersRepository.retrieveOrderById(orderId, businessOrderMutableLiveData);
    }

    public void executeByPhone(String phone, MutableLiveData<BusinessOrder> businessOrderMutableLiveData) {
        businessOrdersRepository.retrieveOrderByPhone(phone, businessOrderMutableLiveData);
    }
}
