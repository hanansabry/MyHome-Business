package com.app.myhomebusiness.domain.usecases;

import com.app.myhomebusiness.data.BusinessOrdersRepository;
import com.app.myhomebusiness.model.BusinessOrder;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class RetrieveOrdersUseCase {

    private final BusinessOrdersRepository businessOrdersRepository;

    public RetrieveOrdersUseCase(BusinessOrdersRepository businessOrdersRepository) {
        this.businessOrdersRepository = businessOrdersRepository;
    }

    public void execute(MutableLiveData<List<BusinessOrder>> listMutableLiveData) {
        businessOrdersRepository.retrieveAllOrdersForCurrentBusiness(listMutableLiveData);
    }
}
