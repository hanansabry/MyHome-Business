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

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.myhomebusiness.Constants;
import com.app.myhomebusiness.R;
import com.app.myhomebusiness.model.OrderItem;
import com.app.myhomebusiness.presentation.adapters.OrderItemsAdapter;
import com.app.myhomebusiness.presentation.adapters.OrdersAdapter;
import com.app.myhomebusiness.viewmodels.RetrieveOrderByIdViewModel;
import com.app.myhomebusiness.viewmodels.UpdateOrderStatusViewModel;
import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {

    @BindView(R.id.order_id_textview)
    TextView orderIdTextView;
    @BindView(R.id.order_date_textview)
    TextView orderDateTextView;
    @BindView(R.id.order_price_textview)
    TextView orderPriceTextView;
    @BindView(R.id.order_phone_textview)
    TextView orderPhoneTextView;
    @BindView(R.id.order_status_textview)
    TextView orderStatusTextView;
    @BindView(R.id.itemsRecyclerView)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.btnDelivered)
    Button btnDelivered;
    @BindView(R.id.btnPending)
    Button btnPending;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private UpdateOrderStatusViewModel updateOrderStatusViewModel;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Order details");

        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        RetrieveOrderByIdViewModel retrieveOrderByIdViewModel = new ViewModelProvider(this).get(RetrieveOrderByIdViewModel.class);
        retrieveOrderByIdViewModel.retrieveOrderById(orderId);
        retrieveOrderByIdViewModel.getBusinessOrderMutableLiveData().observe(this, businessOrder -> {
            orderIdTextView.setText(businessOrder.getId());
            orderDateTextView.setText(businessOrder.getDate());
            orderPriceTextView.setText(String.format("%.2f", businessOrder.getTotalPrice()));
            orderPhoneTextView.setText(businessOrder.getPhone());
            orderStatusTextView.setText(businessOrder.getStatus());
            initiateOrderItemsRecyclerView(businessOrder.getOrderItems());
            progressBar.setVisibility(View.GONE);

            if (businessOrder.getStatus().equalsIgnoreCase(Constants.STATUS_NEW)) {
                btnDelivered.setEnabled(false);
                btnDelivered.setBackgroundColor(getResources().getColor(R.color.text_gray));
                btnPending.setEnabled(true);
            } else if (businessOrder.getStatus().equalsIgnoreCase(Constants.STATUS_PENDING)) {
                btnDelivered.setEnabled(true);
                btnPending.setEnabled(false);
                btnPending.setBackgroundColor(getResources().getColor(R.color.text_gray));
            } else if (businessOrder.getStatus().equalsIgnoreCase(Constants.STATUS_DELIVERED)) {
                btnDelivered.setEnabled(false);
                btnPending.setEnabled(false);
                btnDelivered.setBackgroundColor(getResources().getColor(R.color.text_gray));
                btnPending.setBackgroundColor(getResources().getColor(R.color.text_gray));
            }
        });

        updateOrderStatusViewModel = new ViewModelProvider(this).get(UpdateOrderStatusViewModel.class);
        updateOrderStatusViewModel.isOrderStatusUpdated().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Status of the order is updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something wrong happened, please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initiateOrderItemsRecyclerView(ArrayList<OrderItem> orderItems) {
        OrderItemsAdapter orderItemsAdapter = new OrderItemsAdapter(orderItems);
        itemsRecyclerView.setAdapter(orderItemsAdapter);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.btnDelivered)
    public void onDeliveredClicked() {
        updateOrderStatusViewModel.updateOrderStatus(orderId, Constants.STATUS_DELIVERED);
        btnDelivered.setEnabled(false);
        btnDelivered.setBackgroundColor(getResources().getColor(R.color.text_gray));
    }

    @OnClick(R.id.btnPending)
    public void onPendingClicked() {
        updateOrderStatusViewModel.updateOrderStatus(orderId, Constants.STATUS_PENDING);
        btnDelivered.setEnabled(true);
        btnDelivered.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        btnPending.setEnabled(false);
        btnPending.setBackgroundColor(getResources().getColor(R.color.text_gray));
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