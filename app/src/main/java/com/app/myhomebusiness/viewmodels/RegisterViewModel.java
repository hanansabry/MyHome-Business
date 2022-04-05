package com.app.myhomebusiness.viewmodels;

import com.app.myhomebusiness.Injection;
import com.app.myhomebusiness.domain.usecases.AddBusinessUseCase;
import com.app.myhomebusiness.domain.usecases.RegisterUseCase;
import com.app.myhomebusiness.model.Business;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private final RegisterUseCase registerUseCase;
    private final AddBusinessUseCase addBusinessUseCase;
    private MutableLiveData<Boolean> addBusinessSuccessLiveData = new MutableLiveData<>();
    private MutableLiveData<Business> registerBusinessLiveData = new MutableLiveData<>();
    private MutableLiveData<String> businessNameError = new MutableLiveData<>();
    private MutableLiveData<String> bannerUriError = new MutableLiveData<>();
    private MutableLiveData<String> emailError = new MutableLiveData<>();
    private MutableLiveData<String> passwordError = new MutableLiveData<>();
    private MutableLiveData<String> phoneError = new MutableLiveData<>();
    private MutableLiveData<String> cityError = new MutableLiveData<>();
    private MutableLiveData<String> addressError = new MutableLiveData<>();

    public RegisterViewModel() {
        addBusinessUseCase = Injection.getAddBusinessUseCase();
        registerUseCase = Injection.getRegisterUseCase();
    }

    public void addNewBusiness(Business business) {
        addBusinessUseCase.execute(business, addBusinessSuccessLiveData);
    }

    public void register(String businessName, String email, String password,
                         String phone, String city, String address, String bannerUri) {
        if (validate(businessName, email, password, phone, city,
                address, bannerUri)) {
            Business business = new Business();
            business.setName(businessName);
            business.setEmail(email);
            business.setPassword(password);
            business.setPhone(phone);
            business.setCity(city);
            business.setAddress(address);
            business.setBannerImageUrl(bannerUri);
            registerUseCase.execute(business, registerBusinessLiveData);
        }
    }

    private boolean validate(String businessName, String email, String password,
                             String phone, String city, String address, String bannerUri) {
        boolean isValid = true;

        if (businessName == null || businessName.isEmpty()) {
            businessNameError.setValue("Required");
            isValid = false;
        }
        if (email == null || email.isEmpty()) {
            emailError.setValue("Required");
            isValid = false;
        } else if (!isEmailValid(email)) {
            emailError.setValue("Incorrect email format");
            isValid = false;
        }
        if (password == null || password.isEmpty()) {
            passwordError.setValue("Required");
            isValid = false;
        } else if (password.length() < 8) {
            passwordError.setValue("Password must be 8 characters or more");
            isValid = false;
        }
        if (phone == null || phone.isEmpty()) {
            phoneError.setValue("Required");
            isValid = false;
        }
        if (city == null || city.isEmpty()) {
            cityError.setValue("Required");
            isValid = false;
        }
        if (address == null || address.isEmpty()) {
            addressError.setValue("Required");
            isValid = false;
        }
        if (bannerUri == null || bannerUri.isEmpty()) {
            bannerUriError.setValue("Required");
            isValid = false;
        }
        return isValid;
    }

    private boolean isEmailValid(String email) {
        boolean isValid = false;

        // String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        String expression = "(?!^[.+&'_-]*@.*$)(^[_\\w\\d+&'-]+(\\.[_\\w\\d+&'-]*)*@[\\w\\d-]+(\\.[\\w\\d-]+)*\\.(([\\d]{1,3})|([\\w]{2,}))$)";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public MutableLiveData<Boolean> getAddBusinessSuccessLiveData() {
        return addBusinessSuccessLiveData;
    }

    public MutableLiveData<Business> getRegisterBusinessLiveData() {
        return registerBusinessLiveData;
    }

    public MutableLiveData<String> getBusinessNameError() {
        return businessNameError;
    }

    public MutableLiveData<String> getBannerUriError() {
        return bannerUriError;
    }

    public MutableLiveData<String> getEmailError() {
        return emailError;
    }

    public MutableLiveData<String> getPasswordError() {
        return passwordError;
    }

    public MutableLiveData<String> getPhoneError() {
        return phoneError;
    }

    public MutableLiveData<String> getCityError() {
        return cityError;
    }

    public MutableLiveData<String> getAddressError() {
        return addressError;
    }
}
