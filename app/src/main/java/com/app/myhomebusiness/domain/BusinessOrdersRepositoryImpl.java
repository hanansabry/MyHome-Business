package com.app.myhomebusiness.domain;

import com.app.myhomebusiness.Constants;
import com.app.myhomebusiness.data.BusinessOrdersRepository;
import com.app.myhomebusiness.model.BusinessOrder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class BusinessOrdersRepositoryImpl implements BusinessOrdersRepository {

    private final DatabaseReference businessesReference;

    public BusinessOrdersRepositoryImpl() {
        businessesReference = FirebaseDatabase.getInstance().getReference(Constants.BUSINESS_NODE);
    }

    @Override
    public void retrieveAllOrdersForCurrentBusiness(MutableLiveData<List<BusinessOrder>> businessOrdersLiveData) {
        String businessId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        businessesReference.child(businessId).child(Constants.ORDERS_NODE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<BusinessOrder> businessOrderList = new ArrayList<>();
                        for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                            BusinessOrder businessOrder = orderSnapshot.getValue(BusinessOrder.class);
                            businessOrder.setId(orderSnapshot.getKey());
                            businessOrderList.add(businessOrder);
                        }
                        businessOrdersLiveData.setValue(businessOrderList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        businessOrdersLiveData.setValue(null);
                    }
                });
    }

    @Override
    public void placeNewOrder(String businessId, BusinessOrder businessOrder, MutableLiveData<Boolean> success) {
        businessesReference.child(businessId).child(Constants.ORDERS_NODE).push().setValue(businessOrder)
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
