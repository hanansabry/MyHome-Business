package com.app.myhomebusiness.presentation.businessowner;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.app.myhomebusiness.R;
import com.app.myhomebusiness.viewmodels.AddMenuItemViewModel;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AddItemActivity extends AppCompatActivity implements IPickResult {

    @BindView(R.id.editTextItemName)
    EditText editTextItemName;
    @BindView(R.id.editTextPrice)
    EditText editTextPrice;
    @BindView(R.id.editTextCategory)
    EditText editTextCategory;
    @BindView(R.id.editTextDesc)
    EditText editTextDesc;
    @BindView(R.id.editTextItemImageUri)
    EditText editTextItemImageUri;
    private AddMenuItemViewModel addMenuItemViewModel;
    private String imageUri;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add new item");

        pd = new ProgressDialog(this);
        pd.setTitle("Loading");

        addMenuItemViewModel = new ViewModelProvider(this).get(AddMenuItemViewModel.class);
        addMenuItemViewModel.isItemAddedSuccessfully().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Item is added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error while adding item, please try again later", Toast.LENGTH_SHORT).show();
            }
            pd.hide();
        });
        addMenuItemViewModel.getNameError().observe(this, error -> editTextItemName.setError(error));
        addMenuItemViewModel.getPriceError().observe(this, error -> editTextPrice.setError(error));
        addMenuItemViewModel.getCategoryError().observe(this, error -> editTextCategory.setError(error));
        addMenuItemViewModel.getDescError().observe(this, error -> editTextDesc.setError(error));
        addMenuItemViewModel.getImageUriError().observe(this, error -> editTextItemImageUri.setError(error));
    }

    @OnClick(R.id.btnAddItem)
    public void onAddItemClicked() {
        String name = editTextItemName.getText().toString();
        double price = editTextPrice.getText().toString().isEmpty() ? 0 : Double.parseDouble(editTextPrice.getText().toString());
        String category = editTextCategory.getText().toString();
        String desc = editTextDesc.getText().toString();
        imageUri = editTextItemImageUri.getText().toString();
        addMenuItemViewModel.addMenuItem(name, price, category, desc, imageUri);
        pd.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnTextChanged(R.id.editTextItemName)
    public void removeNameError() {
        editTextItemName.setError(null);
    }

    @OnTextChanged(R.id.editTextPrice)
    public void removePriceError() {
        editTextPrice.setError(null);
    }

    @OnTextChanged(R.id.editTextCategory)
    public void removeCategoryError() {
        editTextCategory.setError(null);
    }

    @OnTextChanged(R.id.editTextDesc)
    public void removeDescError() {
        editTextDesc.setError(null);
    }

    @OnTextChanged(R.id.editTextItemImageUri)
    public void removeImageError() {
        editTextItemImageUri.setError(null);
    }

    @OnClick(R.id.editTextItemImageUri)
    public void onUploadImageClicked() {
        PickImageDialog.build(new PickSetup()).show(this);
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            editTextItemImageUri.setText(r.getPath());
            imageUri = r.getUri().toString();
        } else {
            //Handle possible errors
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}