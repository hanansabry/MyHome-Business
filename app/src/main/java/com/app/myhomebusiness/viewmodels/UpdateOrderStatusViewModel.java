package com.app.myhomebusiness.viewmodels;

import com.app.myhomebusiness.Injection;
import com.app.myhomebusiness.domain.usecases.UpdateOrderStatusUseCase;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UpdateOrderStatusViewModel extends ViewModel {

    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private MutableLiveData<Boolean> success = new MutableLiveData<>();

    public UpdateOrderStatusViewModel() {
        updateOrderStatusUseCase = Injection.getUpdateOrderStatusUseCase();
    }

    public void updateOrderStatus(String orderId, String newStatus) {
        updateOrderStatusUseCase.execute(orderId, newStatus, success);
    }

    public MutableLiveData<Boolean> isOrderStatusUpdated() {
        return success;
    }
}
