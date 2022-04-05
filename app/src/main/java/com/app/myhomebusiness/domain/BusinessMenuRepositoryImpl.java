package com.app.myhomebusiness.domain;

import android.net.Uri;

import com.app.myhomebusiness.Constants;
import com.app.myhomebusiness.data.BusinessMenuRepository;
import com.app.myhomebusiness.model.MenuItem;
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

public class BusinessMenuRepositoryImpl implements BusinessMenuRepository {

    private final FirebaseDatabase firebaseDatabase;
    private final String currentBusinessId;

    public BusinessMenuRepositoryImpl() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        currentBusinessId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void retrieveMenuItems(MutableLiveData<List<MenuItem>> menuItemLiveData) {
        DatabaseReference reference = firebaseDatabase.getReference(Constants.BUSINESS_NODE).child(currentBusinessId).child(Constants.MENU_NODE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MenuItem> menuItemList = new ArrayList<>();
                for (DataSnapshot menuItemSnapshot : snapshot.getChildren()) {
                    MenuItem menuItem = menuItemSnapshot.getValue(MenuItem.class);
                    menuItem.setId(menuItemSnapshot.getKey());
                    menuItemList.add(menuItem);
                }
                menuItemLiveData.setValue(menuItemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                menuItemLiveData.setValue(null);
            }
        });
    }

    @Override
    public void retrieveMenuItems(String businessId, MutableLiveData<List<MenuItem>> menuItemLiveData) {
        DatabaseReference reference = firebaseDatabase.getReference(Constants.BUSINESS_NODE).child(businessId).child(Constants.MENU_NODE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MenuItem> menuItemList = new ArrayList<>();
                for (DataSnapshot menuItemSnapshot : snapshot.getChildren()) {
                    MenuItem menuItem = menuItemSnapshot.getValue(MenuItem.class);
                    menuItem.setId(menuItemSnapshot.getKey());
                    menuItemList.add(menuItem);
                }
                menuItemLiveData.setValue(menuItemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                menuItemLiveData.setValue(null);
            }
        });
    }

    @Override
    public void addNewMenuItem(MenuItem menuItem, MutableLiveData<Boolean> success) {
        DatabaseReference reference = firebaseDatabase.getReference(Constants.BUSINESS_NODE);

        Uri file = Uri.fromFile(new File(menuItem.getImageUrl()));
        StorageReference absolutePathStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference storageReference = absolutePathStorageRef.child(Constants.BUSINESS_NODE + "/" + currentBusinessId +"/menu/"
                + menuItem.getName() + "/" + UUID.randomUUID().toString());

        //store the image
        storageReference.putFile(file)
                .addOnSuccessListener(taskSnapshot -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        menuItem.setImageUrl(uri.toString());
                        reference.child(currentBusinessId).child(Constants.MENU_NODE).push().setValue(menuItem)
                                .addOnCompleteListener(task -> success.setValue(task.isSuccessful()));
                    })
                            .addOnFailureListener(e -> {
                                success.setValue(false);
                            });
                }).addOnFailureListener(e -> {
            success.setValue(false);
        });
    }
}
