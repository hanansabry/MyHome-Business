package com.app.myhomebusiness.domain.usecases;

import com.app.myhomebusiness.data.BusinessMenuRepository;
import com.app.myhomebusiness.model.MenuItem;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class RetrieveMenuItemsUseCase {

    private final BusinessMenuRepository businessMenuRepository;

    public RetrieveMenuItemsUseCase(BusinessMenuRepository businessMenuRepository) {
        this.businessMenuRepository = businessMenuRepository;
    }

    public void execute(MutableLiveData<List<MenuItem>> listMutableLiveData) {
        businessMenuRepository.retrieveMenuItems(listMutableLiveData);
    }

    public void execute(String businessId, MutableLiveData<List<MenuItem>> listMutableLiveData) {
        businessMenuRepository.retrieveMenuItems(businessId, listMutableLiveData);
    }
}
