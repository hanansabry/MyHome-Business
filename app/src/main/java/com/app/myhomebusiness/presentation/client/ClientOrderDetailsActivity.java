package com.app.myhomebusiness.presentation.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.myhomebusiness.Constants;
import com.app.myhomebusiness.R;
import com.app.myhomebusiness.model.BusinessOrder;
import com.app.myhomebusiness.model.OrderItem;
import com.app.myhomebusiness.presentation.adapters.OrderItemsAdapter;
import com.app.myhomebusiness.viewmodels.PlaceNewOrderViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClientOrderDetailsActivity extends AppCompatActivity {

    @BindView(R.id.itemsRecyclerView)
    RecyclerView orderItemsRecyclerView;
    @BindView(R.id.orderPhoneEditText)
    EditText orderPhoneEditText;
    @BindView(R.id.orderAddressEditText)
    EditText orderAddressEditText;
    @BindView(R.id.orderPriceTextview)
    TextView orderPriceTextView;
    @BindView(R.id.orderStatusTextView)
    TextView orderStatusTextView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btnDone)
    Button btnDone;
    private ArrayList<OrderItem> orderItems;
    private String businessId;
    private PlaceNewOrderViewModel placeNewOrderViewModel;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_order_details);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Order details");

        Intent intent = getIntent();
        if (intent.getStringExtra(Constants.PHONE) != null) {
            orderItems = intent.getParcelableArrayListExtra(Constants.ORDER_ITEMS);

            orderPhoneEditText.setEnabled(false);
            orderPhoneEditText.setText(intent.getStringExtra(Constants.PHONE));

            orderAddressEditText.setEnabled(false);
            orderAddressEditText.setText(intent.getStringExtra(Constants.ADDRESS));

            orderPriceTextView.setText(String.format("%.2f", intent.getDoubleExtra(Constants.PRICE, 0)));
            orderStatusTextView.setText(intent.getStringExtra(Constants.STATUS));

            btnDone.setBackgroundColor(getResources().getColor(R.color.text_gray));
            btnDone.setEnabled(false);
        } else {
            businessId = intent.getStringExtra(Constants.BUSINESS_ID);
            orderItems = intent.getParcelableArrayListExtra(Constants.ORDER_ITEMS);
            orderPriceTextView.setText(String.format("%s", getOrderTotalPrice(orderItems)));
            orderStatusTextView.setText(Constants.STATUS_NEW);
        }
        initiateOrderItemsRecyclerView(orderItems);

        pd = new ProgressDialog(this);
        pd.setTitle("Loading");

        placeNewOrderViewModel = new ViewModelProvider(this).get(PlaceNewOrderViewModel.class);
        placeNewOrderViewModel.isOrderedPlacedSuccessfully().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Order is places successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ClientOrderDetailsActivity.this, ClientHomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Error while placing order, please try again later", Toast.LENGTH_SHORT).show();
            }
            pd.hide();
        });
    }

    private void initiateOrderItemsRecyclerView(List<OrderItem> orderItems) {
        OrderItemsAdapter adapter = new OrderItemsAdapter(orderItems);
        orderItemsRecyclerView.setAdapter(adapter);
        orderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.GONE);
    }

    private double getOrderTotalPrice(List<OrderItem> orderItems) {
        double total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getTotalPrice();
        }
        return total;
    }

    @OnClick(R.id.btnDone)
    public void onDoneClicked() {
        String phone = orderPhoneEditText.getText().toString();
        String address = orderAddressEditText.getText().toString();
        if (phone.isEmpty()) {
            orderPhoneEditText.setError("Required");
        }
        if (address.isEmpty()) {
            orderAddressEditText.setError("Required");
        }
        if (!phone.isEmpty() && !address.isEmpty()) {
            //send order
            BusinessOrder businessOrder = new BusinessOrder();
            businessOrder.setDate(getCurrentDate());
            businessOrder.setPhone(phone);
            businessOrder.setAddress(address);
            businessOrder.setStatus(Constants.STATUS_NEW);
            businessOrder.setTotalPrice(getOrderTotalPrice(orderItems));
            businessOrder.setOrderItems(orderItems);
            //place the order
            placeNewOrderViewModel.placeNewOrder(businessId, businessOrder);
            pd.show();
        }
    }

    private HashMap<String, OrderItem> createHashMapFromOrderItemsList(ArrayList<OrderItem> orderItems) {
        HashMap<String, OrderItem> orderItemHashMap = new HashMap<>();
        for (int i = 0; i < orderItems.size(); i++) {
            orderItemHashMap.put(i+"", orderItems.get(i));
        }
        return orderItemHashMap;
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
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