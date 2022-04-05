package com.app.myhomebusiness.viewmodels;

import com.app.myhomebusiness.Injection;
import com.app.myhomebusiness.domain.usecases.AddMenuItemUseCase;
import com.app.myhomebusiness.model.MenuItem;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddMenuItemViewModel extends ViewModel {

    private final AddMenuItemUseCase addMenuItemUseCase;
    private MutableLiveData<Boolean> success = new MutableLiveData<>();
    private MutableLiveData<String> nameError = new MutableLiveData<>();
    private MutableLiveData<String> priceError = new MutableLiveData<>();
    private MutableLiveData<String> categoryError = new MutableLiveData<>();
    private MutableLiveData<String> descError = new MutableLiveData<>();
    private MutableLiveData<String> imageUriError = new MutableLiveData<>();

    public AddMenuItemViewModel() {
        this.addMenuItemUseCase = Injection.getAddMenuItemUseCase();
    }

    public void addMenuItem(String itemName, double price, String category, String desc, String imageUri) {
        if (validate(itemName, price, category, desc, imageUri)) {
            MenuItem menuItem = new MenuItem();
            menuItem.setName(itemName);
            menuItem.setPrice(price);
            menuItem.setCategory(category);
            menuItem.setDesc(desc);
            menuItem.setImageUrl(imageUri);
            addMenuItemUseCase.execute(menuItem, success);
        }
    }

    private boolean validate(String itemName, double price, String category, String desc, String imageUri) {
        boolean isValid = true;
        if (itemName == null || itemName.isEmpty()) {
            nameError.setValue("Required");
            isValid = false;
        }
        if (price == 0) {
            priceError.setValue("Price value must be greater than 0.0");
            isValid = false;
        }
        if (category == null || category.isEmpty()) {
            categoryError.setValue("Required");
            isValid = false;
        }
        if (desc == null || desc.isEmpty()) {
            descError.setValue("Required");
            isValid = false;
        }
        if (imageUri == null || imageUri.isEmpty()) {
            imageUriError.setValue("Required");
            isValid = false;
        }
        return isValid;
    }

    public MutableLiveData<Boolean> isItemAddedSuccessfully() {
        return success;
    }

    public MutableLiveData<String> getNameError() {
        return nameError;
    }

    public MutableLiveData<String> getPriceError() {
        return priceError;
    }

    public MutableLiveData<String> getCategoryError() {
        return categoryError;
    }

    public MutableLiveData<String> getDescError() {
        return descError;
    }

    public MutableLiveData<String> getImageUriError() {
        return imageUriError;
    }
}
