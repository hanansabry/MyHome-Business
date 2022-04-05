package com.app.myhomebusiness.presentation.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.app.myhomebusiness.Constants;
import com.app.myhomebusiness.R;
import com.app.myhomebusiness.model.Business;
import com.app.myhomebusiness.presentation.StartActivity;
import com.app.myhomebusiness.presentation.adapters.BusinessesAdapter;
import com.app.myhomebusiness.viewmodels.RetrieveBusinessesViewModel;

import java.util.List;

public class ClientHomeActivity extends AppCompatActivity {

    @BindView(R.id.businessesRecyclerview)
    RecyclerView businessesRecyclerview;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

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