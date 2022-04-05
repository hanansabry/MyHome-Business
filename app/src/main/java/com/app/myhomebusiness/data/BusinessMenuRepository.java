package com.app.myhomebusiness.data;

import com.app.myhomebusiness.model.MenuItem;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public interface BusinessMenuRepository {

    void retrieveMenuItems(MutableLiveData<List<MenuItem>> menuItemLiveData);

    void retrieveMenuItems(String businessId, MutableLiveData<List<MenuItem>> menuItemLiveData);

    void addNewMenuItem(MenuItem menuItem, MutableLiveData<Boolean> success);
}
