package com.app.myhomebusiness.domain.usecases;

import com.app.myhomebusiness.data.BusinessOrdersRepository;

import androidx.lifecycle.MutableLiveData;

public class UpdateOrderStatusUseCase {

    private final BusinessOrdersRepository businessOrdersRepository;

    public UpdateOrderStatusUseCase(BusinessOrdersRepository businessOrdersRepository) {
        this.businessOrdersRepository = businessOrdersRepository;
    }

    public void execute(String orderId, String newStatus, MutableLiveData<Boolean> success) {
        businessOrdersRepository.updateOrderStatus(orderId, newStatus, success);
    }
}
