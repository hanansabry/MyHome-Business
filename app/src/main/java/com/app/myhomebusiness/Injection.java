package com.app.myhomebusiness;

import com.app.myhomebusiness.data.BusinessAuthenticationRepository;
import com.app.myhomebusiness.data.BusinessMenuRepository;
import com.app.myhomebusiness.data.BusinessOrdersRepository;
import com.app.myhomebusiness.domain.BusinessAuthenticationRepositoryImpl;
import com.app.myhomebusiness.domain.BusinessMenuRepositoryImpl;
import com.app.myhomebusiness.domain.BusinessOrdersRepositoryImpl;
import com.app.myhomebusiness.domain.usecases.AddBusinessUseCase;
import com.app.myhomebusiness.domain.usecases.AddMenuItemUseCase;
import com.app.myhomebusiness.domain.usecases.LoginUseCase;
import com.app.myhomebusiness.domain.usecases.PlaceNewOrderUseCase;
import com.app.myhomebusiness.domain.usecases.RegisterUseCase;
import com.app.myhomebusiness.domain.usecases.RetrieveBusinessesUseCase;
import com.app.myhomebusiness.domain.usecases.RetrieveMenuItemsUseCase;

public class Injection {
    public static LoginUseCase getLoginUseCase() {
        return new LoginUseCase(getBusinessAuthenticationRepoistory());
    }

    private static BusinessAuthenticationRepository getBusinessAuthenticationRepoistory() {
        return new BusinessAuthenticationRepositoryImpl();
    }

    public static AddBusinessUseCase getAddBusinessUseCase() {
        return new AddBusinessUseCase(getBusinessAuthenticationRepoistory());
    }


    public static RegisterUseCase getRegisterUseCase() {
        return new RegisterUseCase(getBusinessAuthenticationRepoistory());
    }

    public static AddMenuItemUseCase getAddMenuItemUseCase() {
        return new AddMenuItemUseCase(getBusinessMenuRepository());
    }

    private static BusinessMenuRepository getBusinessMenuRepository() {
        return new BusinessMenuRepositoryImpl();
    }

    public static RetrieveMenuItemsUseCase getRetrieveMenuItemsUseCase() {
        return new RetrieveMenuItemsUseCase(getBusinessMenuRepository());
    }

    public static RetrieveBusinessesUseCase getRetrieveBusinessesUseCase() {
        return new RetrieveBusinessesUseCase(getBusinessAuthenticationRepoistory());
    }

    public static PlaceNewOrderUseCase getPlaceNewOrderUseCase() {
        return new PlaceNewOrderUseCase(getBusinessOrdersRepository());
    }

    private static BusinessOrdersRepository getBusinessOrdersRepository() {
        return new BusinessOrdersRepositoryImpl();
    }
}
