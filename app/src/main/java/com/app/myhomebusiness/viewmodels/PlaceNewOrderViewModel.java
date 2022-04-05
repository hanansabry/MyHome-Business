package com.app.myhomebusiness.viewmodels;

import com.app.myhomebusiness.Injection;
import com.app.myhomebusiness.domain.usecases.PlaceNewOrderUseCase;
import com.app.myhomebusiness.model.BusinessOrder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlaceNewOrderViewModel extends ViewModel {

    private final PlaceNewOrderUseCase placeNewOrderUseCase;
    private MutableLiveData<Boolean> success = new MutableLiveData<>();

    public PlaceNewOrderViewModel() {
        placeNewOrderUseCase = Injection.getPlaceNewOrderUseCase();
    }

    public void placeNewOrder(String businessId, BusinessOrder businessOrder) {
        placeNewOrderUseCase.execute(businessId, businessOrder, success);
    }

    public MutableLiveData<Boolean> isOrderedPlacedSuccessfully() {
        return success;
    }
}
