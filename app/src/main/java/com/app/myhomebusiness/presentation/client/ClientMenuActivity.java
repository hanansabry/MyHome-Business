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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.myhomebusiness.Constants;
import com.app.myhomebusiness.R;
import com.app.myhomebusiness.model.OrderItem;
import com.app.myhomebusiness.presentation.adapters.ClientMenuItemsAdapter;
import com.app.myhomebusiness.viewmodels.RetrieveMenuItemsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ClientMenuActivity extends AppCompatActivity implements ClientMenuItemsAdapter.OnAddToCartClickedListener {

    @BindView(R.id.clientMenuRecyclerView)
    RecyclerView clientMenuRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.cartItemsNumberTextView)
    TextView cartItemsNumberTextView;
    private ArrayList<OrderItem> orderItems = new ArrayList<>();
    private String businessId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //get business id and name
        businessId = getIntent().getStringExtra(Constants.BUSINESS_ID);
        String businessName = getIntent().getStringExtra(Constants.BUSINESS_NAME);
        actionBar.setTitle(businessName + "'s Menu");

        RetrieveMenuItemsViewModel retrieveMenuItemsViewModel = new ViewModelProvider(this).get(RetrieveMenuItemsViewModel.class);
        retrieveMenuItemsViewModel.retrieveMenuItems(businessId);
        retrieveMenuItemsViewModel.getItemsListMutableLiveData().observe(this, this::initiateMenuItemsRecyclerView);
    }

    private void initiateMenuItemsRecyclerView(List<com.app.myhomebusiness.model.MenuItem> menuItems) {
        ClientMenuItemsAdapter menuItemsAdapter = new ClientMenuItemsAdapter(menuItems, this);
        clientMenuRecyclerView.setAdapter(menuItemsAdapter);
        clientMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    @Override
    public void onAddToCartClicked(com.app.myhomebusiness.model.MenuItem menuItem, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(menuItem);
        orderItem.setQuantity(quantity);
        orderItem.setTotalPrice(menuItem.getPrice() * quantity);

        orderItems.add(orderItem);
        cartItemsNumberTextView.setText(String.format("Cart Items: %s", getTotalCartItemsCount(orderItems)));
        Toast.makeText(this, "Item is added successfully", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnCheckout)
    public void onCheckoutClicked() {
        if (orderItems.isEmpty()) {
            Toast.makeText(this, "You must add items to the cart", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ClientOrderDetailsActivity.class);
            intent.putParcelableArrayListExtra(Constants.ORDER_ITEMS, orderItems);
            intent.putExtra(Constants.BUSINESS_ID, businessId);
            startActivity(intent);
        }
    }

    private String getTotalCartItemsCount(List<OrderItem> orderItems) {
        int count = 0;
        for (OrderItem orderItem : orderItems) {
            count += orderItem.getQuantity();
        }
        return count + "";
    }
}