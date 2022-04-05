package com.app.myhomebusiness.domain;

import com.app.myhomebusiness.Constants;
import com.app.myhomebusiness.data.BusinessOrdersRepository;
import com.app.myhomebusiness.model.BusinessOrder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class BusinessOrdersRepositoryImpl implements BusinessOrdersRepository {

    private final DatabaseReference ordersReference;

    public BusinessOrdersRepositoryImpl() {
        ordersReference = FirebaseDatabase.getInstance().getReference(Constants.BUSINESS_NODE);
    }

    @Override
    public void retrieveAllOrders(MutableLiveData<List<BusinessOrder>> businessOrdersLiveData) {

    }

    @Override
    public void placeNewOrder(String businessId, BusinessOrder businessOrder, MutableLiveData<Boolean> success) {
        ordersReference.child(businessId).child(Constants.ORDERS_NODE).push().setValue(businessOrder)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        success.setValue(task.isSuccessful());
                    }
                });
    }

    @Override
    public void retrieveOrderById(String orderId, MutableLiveData<BusinessOrder> businesOrderMutableLiveData) {

    }
}
