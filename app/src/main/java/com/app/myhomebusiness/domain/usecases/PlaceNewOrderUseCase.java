package com.app.myhomebusiness.domain.usecases;

import com.app.myhomebusiness.data.BusinessOrdersRepository;
import com.app.myhomebusiness.model.BusinessOrder;

import androidx.lifecycle.MutableLiveData;

public class PlaceNewOrderUseCase {

    private final BusinessOrdersRepository businessOrdersRepository;

    public PlaceNewOrderUseCase(BusinessOrdersRepository businessOrdersRepository) {
        this.businessOrdersRepository = businessOrdersRepository;
    }

    public void execute(String businessId, BusinessOrder businessOrder, MutableLiveData<Boolean> success) {
        businessOrdersRepository.placeNewOrder(businessId, businessOrder, success);
    }
}
