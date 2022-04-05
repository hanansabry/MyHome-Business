package com.app.myhomebusiness.domain.usecases;

import com.app.myhomebusiness.data.BusinessMenuRepository;
import com.app.myhomebusiness.model.MenuItem;

import androidx.lifecycle.MutableLiveData;

public class AddMenuItemUseCase {

    private final BusinessMenuRepository businessMenuRepository;

    public AddMenuItemUseCase(BusinessMenuRepository businessMenuRepository) {
        this.businessMenuRepository = businessMenuRepository;
    }

    public void execute(MenuItem menuItem, MutableLiveData<Boolean> success) {
        businessMenuRepository.addNewMenuItem(menuItem, success);
    }
}
