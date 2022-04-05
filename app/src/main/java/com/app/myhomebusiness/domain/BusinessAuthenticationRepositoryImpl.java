package com.app.myhomebusiness.domain;

import android.net.Uri;

import com.app.myhomebusiness.Constants;
import com.app.myhomebusiness.data.BusinessAuthenticationRepository;
import com.app.myhomebusiness.model.Business;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class BusinessAuthenticationRepositoryImpl implements BusinessAuthenticationRepository {

    private final FirebaseAuth auth;
    private final FirebaseDatabase firebaseDatabase;

    public BusinessAuthenticationRepositoryImpl() {
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void loginWithBusinessCredentials(String email, String password, MutableLiveData<Boolean> success) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> success.setValue(task.isSuccessful()));
    }

    @Override
    public void registerNewBusiness(Business business, MutableLiveData<Business> businessMutableLiveData) {
        auth.createUserWithEmailAndPassword(business.getEmail(), business.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        business.setId(task.getResult().getUser().getUid());
                        businessMutableLiveData.setValue(business);
                    } else {
                        businessMutableLiveData.setValue(null);
                    }
                });
    }

    @Override
    public void addNewBusiness(Business business, MutableLiveData<Boolean> success) {
        DatabaseReference businessRef = firebaseDatabase.getReference(Constants.BUSINESS_NODE);
        //storage for the business banner
        Uri file = Uri.fromFile(new File(business.getBannerImageUrl()));
        StorageReference absolutePathStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference storageReference = absolutePathStorageRef.child(Constants.BUSINESS_NODE + "/" + business.getId()
                + "/" + UUID.randomUUID().toString());

        //store the image
        storageReference.putFile(file)
                .addOnSuccessListener(taskSnapshot -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        business.setBannerImageUrl(uri.toString());
                        businessRef.child(business.getId()).setValue(business)
                                .addOnCompleteListener(task -> success.setValue(task.isSuccessful()));
                    })
                            .addOnFailureListener(e -> {
                                success.setValue(false);
                            });
                }).addOnFailureListener(e -> {
            success.setValue(false);
        });
    }

    @Override
    public void retrieveAllBusinesses(MutableLiveData<List<Business>> businessesListMutableLiveData) {
        DatabaseReference reference = firebaseDatabase.getReference(Constants.BUSINESS_NODE);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Business> businessList = new ArrayList<>();
                for (DataSnapshot businessSnapshot : snapshot.getChildren()) {
                    Business business = businessSnapshot.getValue(Business.class);
                    businessList.add(business);
                }
                businessesListMutableLiveData.setValue(businessList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                businessesListMutableLiveData.setValue(null);
            }
        });
    }
}
