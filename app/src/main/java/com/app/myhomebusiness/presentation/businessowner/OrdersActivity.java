package com.app.myhomebusiness.presentation.businessowner;

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
import android.widget.ProgressBar;

import com.app.myhomebusiness.Constants;
import com.app.myhomebusiness.R;
import com.app.myhomebusiness.model.BusinessOrder;
import com.app.myhomebusiness.presentation.adapters.OrdersAdapter;
import com.app.myhomebusiness.viewmodels.RetrieveBusinessOrdersViewModel;

import java.util.List;

public class OrdersActivity extends AppCompatActivity implements OrdersAdapter.OnOrderDetailsClickedListener {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.ordersRecyclerView)
    RecyclerView ordersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Orders");

        RetrieveBusinessOrdersViewModel retrieveBusinessOrdersViewModel = new ViewModelProvider(this).get(RetrieveBusinessOrdersViewModel.class);
        retrieveBusinessOrdersViewModel.retrieveOrders();
        retrieveBusinessOrdersViewModel.getOrdersListMutableLiveData().observe(this, this::initiateOrdersRecyclerView);
    }

    private void initiateOrdersRecyclerView(List<BusinessOrder> businessOrders) {
        OrdersAdapter ordersAdapter = new OrdersAdapter(businessOrders, this);
        ordersRecyclerView.setAdapter(ordersAdapter);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.ordersRecyclerView)
    public void tempOrdersClicked() {
        startActivity(new Intent(this, OrderDetailsActivity.class));
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

    @Override
    public void onOrderDetailsClicked(BusinessOrder businessOrder) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra(Constants.ORDER_ID, businessOrder.getId());
        startActivity(intent);
    }
}