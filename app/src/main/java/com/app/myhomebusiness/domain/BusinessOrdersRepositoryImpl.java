package com.app.myhomebusiness.domain;

import com.app.myhomebusiness.Constants;
import com.app.myhomebusiness.data.BusinessOrdersRepository;
import com.app.myhomebusiness.model.BusinessOrder;
import com.app.myhomebusiness.model.MenuItem;
import com.app.myhomebusiness.model.OrderItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
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
        String currentBusinessId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        businessesReference.child(currentBusinessId).child(Constants.ORDERS_NODE)
                .addValueEventListener(new ValueEventListener() {
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
    public void retrieveOrderById(String orderId, MutableLiveData<BusinessOrder> businessOrderMutableLiveData) {
        String currentBusinessId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        businessesReference.child(currentBusinessId).child(Constants.ORDERS_NODE).child(orderId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        BusinessOrder businessOrder = snapshot.getValue(BusinessOrder.class);
                        businessOrder.setId(snapshot.getKey());
                        businessOrderMutableLiveData.setValue(businessOrder);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        businessOrderMutableLiveData.setValue(null);
                    }
                });
    }

    @Override
    public void retrieveOrderByPhone(String phone, MutableLiveData<BusinessOrder> businessOrderMutableLiveData) {
        businessesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isFound = false;
                BusinessOrder businessOrder = null;
                HashMap<String, HashMap<String, Object>> businessList = (HashMap<String, HashMap<String, Object>>) snapshot.getValue();
                for (String businessKey : businessList.keySet()) {
                    HashMap<String, Object> businessHashMap = businessList.get(businessKey);
                    HashMap<String, Object> orderHashMap = (HashMap<String, Object>) businessHashMap.get("orders");
                    if (orderHashMap != null) {
                        for (String orderKey : orderHashMap.keySet()) {
                            HashMap<String, Object> order = (HashMap<String, Object>) orderHashMap.get(orderKey);
                            String orderPhone = (String) order.get("phone");
                            if (orderPhone.equals(phone)) {
                                isFound = true;
                                businessOrder = new BusinessOrder();
                                businessOrder.setId(orderKey);
                                businessOrder.setPhone(phone);
                                businessOrder.setDate((String) order.get("date"));
                                businessOrder.setTotalPrice((double) order.get("totalPrice"));
                                businessOrder.setStatus((String) order.get("status"));
                                businessOrder.setAddress((String) order.get("address"));
                                ArrayList<HashMap<String, Object>> orderItems = (ArrayList<HashMap<String, Object>>) order.get("orderItems");

                                ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();

                                for (HashMap<String, Object>  orderObject: orderItems) {
//                                    HashMap<String, Object> orderObject = (HashMap<String, Object>) orderItems.get(key);

                                    OrderItem orderItem = new OrderItem();
                                    orderItem.setTotalPrice(Double.parseDouble(orderObject.get("totalPrice").toString()));
                                    orderItem.setQuantity(Integer.parseInt(orderObject.get("quantity").toString()));
                                    HashMap<String, Object> menuItemHashMap = (HashMap<String, Object>) orderObject.get("item");
                                    MenuItem menuItem = new MenuItem();
                                    menuItem.setId((String) menuItemHashMap.get("id"));
                                    menuItem.setImageUrl((String) menuItemHashMap.get("imageUrl"));
                                    menuItem.setDesc((String) menuItemHashMap.get("desc"));
                                    menuItem.setCategory((String) menuItemHashMap.get("category"));
                                    menuItem.setPrice(Double.parseDouble(menuItemHashMap.get("price").toString()));
                                    menuItem.setName((String) menuItemHashMap.get("name"));

                                    orderItem.setItem(menuItem);
                                    orderItemArrayList.add(orderItem);
                                }

                                businessOrder.setOrderItems(orderItemArrayList);
                                break;
                            }
                        }
                    }
                }
                businessOrderMutableLiveData.setValue(businessOrder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                businessOrderMutableLiveData.setValue(null);
            }
        });
    }

    @Override
    public void updateOrderStatus(String orderId, String newStatus, MutableLiveData<Boolean> success) {
        String currentBusinessId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        businessesReference.child(currentBusinessId).child(Constants.ORDERS_NODE).child(orderId)
                .child("status").setValue(newStatus)
                .addOnCompleteListener(task -> success.setValue(task.isSuccessful()));
    }
}
