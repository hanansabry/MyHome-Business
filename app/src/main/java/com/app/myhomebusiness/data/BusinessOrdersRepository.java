package com.app.myhomebusiness.data;

import com.app.myhomebusiness.model.BusinessOrder;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public interface BusinessOrdersRepository {

    void retrieveAllOrdersForCurrentBusiness(MutableLiveData<List<BusinessOrder>> businessOrdersLiveData);

    void placeNewOrder(String businessId, BusinessOrder businessOrder, MutableLiveData<Boolean> success);

    void retrieveOrderById(String orderId, MutableLiveData<BusinessOrder> businessOrderMutableLiveData);

    void retrieveOrderByPhone(String phone, MutableLiveData<BusinessOrder> businessOrderMutableLiveData);

    void updateOrderStatus(String orderId, String newStatus, MutableLiveData<Boolean> success);
}
