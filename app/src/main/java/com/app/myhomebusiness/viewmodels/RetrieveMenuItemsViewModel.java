package com.app.myhomebusiness.viewmodels;

import com.app.myhomebusiness.Injection;
import com.app.myhomebusiness.domain.usecases.RetrieveMenuItemsUseCase;
import com.app.myhomebusiness.model.MenuItem;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RetrieveMenuItemsViewModel extends ViewModel {

    private final RetrieveMenuItemsUseCase retrieveMenuItemsUseCase;
    private MutableLiveData<List<MenuItem>> itemsListMutableLiveData = new MutableLiveData<>();

    public RetrieveMenuItemsViewModel() {
        retrieveMenuItemsUseCase = Injection.getRetrieveMenuItemsUseCase();
    }

    public void retrieveMenuItems() {
        retrieveMenuItemsUseCase.execute(itemsListMutableLiveData);
    }

    public void retrieveMenuItems(String businessId) {
        retrieveMenuItemsUseCase.execute(businessId, itemsListMutableLiveData);
    }

    public MutableLiveData<List<MenuItem>> getItemsListMutableLiveData() {
        return itemsListMutableLiveData;
    }
}
