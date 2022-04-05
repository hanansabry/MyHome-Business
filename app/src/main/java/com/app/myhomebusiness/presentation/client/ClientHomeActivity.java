package com.app.myhomebusiness.presentation.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.myhomebusiness.Constants;
import com.app.myhomebusiness.R;
import com.app.myhomebusiness.model.Business;
import com.app.myhomebusiness.presentation.StartActivity;
import com.app.myhomebusiness.presentation.adapters.BusinessesAdapter;
import com.app.myhomebusiness.viewmodels.RetrieveBusinessesViewModel;
import com.app.myhomebusiness.viewmodels.RetrieveOrderByIdViewModel;

import java.util.List;

public class ClientHomeActivity extends AppCompatActivity {

    @BindView(R.id.businessesRecyclerview)
    RecyclerView businessesRecyclerview;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.searchEdiText)
    EditText searchEdiText;
    private RetrieveOrderByIdViewModel retrieveOrderByIdViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Available Businesses");

        RetrieveBusinessesViewModel retrieveBusinessesViewModel = new ViewModelProvider(this).get(RetrieveBusinessesViewModel.class);
        retrieveBusinessesViewModel.retrieveAllBusinesses();
        retrieveBusinessesViewModel.getBusinessListMutableLiveData().observe(this, this::initiateBusinessesRecyclerView);

        retrieveOrderByIdViewModel = new ViewModelProvider(this).get(RetrieveOrderByIdViewModel.class);
        retrieveOrderByIdViewModel.getBusinessOrderMutableLiveData().observe(this, businessOrder -> {
            if (businessOrder != null) {
                Intent intent = new Intent(this, ClientOrderDetailsActivity.class);
                intent.putExtra(Constants.PHONE, businessOrder.getPhone());
                intent.putExtra(Constants.ADDRESS, businessOrder.getAddress());
                intent.putExtra(Constants.PRICE, businessOrder.getTotalPrice());
                intent.putExtra(Constants.STATUS, businessOrder.getStatus());
                intent.putParcelableArrayListExtra(Constants.ORDER_ITEMS, businessOrder.getOrderItems());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Can't find orders with this phone number", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initiateBusinessesRecyclerView(List<Business> businesses) {
        BusinessesAdapter businessesAdapter = new BusinessesAdapter(businesses, business -> {
            Intent intent = new Intent(this, ClientMenuActivity.class);
            intent.putExtra(Constants.BUSINESS_ID, business.getId());
            intent.putExtra(Constants.BUSINESS_NAME, business.getName());
            startActivity(intent);
        });
        businessesRecyclerview.setAdapter(businessesAdapter);
        businessesRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.searchButton)
    public void onSearchClicked() {
        String phone = searchEdiText.getText().toString();
        if (phone.isEmpty()) {
            Toast.makeText(this, "You must enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        retrieveOrderByIdViewModel.retrieveOrderByPhone(phone);
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
}