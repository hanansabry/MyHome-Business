package com.app.myhomebusiness.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.myhomebusiness.R;
import com.app.myhomebusiness.model.BusinessOrder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<BusinessOrder> businessOrderList;
    private OnOrderDetailsClickedListener onOrderDetailsClickedListener;

    public OrdersAdapter(List<BusinessOrder> businessOrderList, OnOrderDetailsClickedListener onOrderDetailsClickedListener) {
        this.businessOrderList = businessOrderList;
        this.onOrderDetailsClickedListener = onOrderDetailsClickedListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        BusinessOrder businessOrder = businessOrderList.get(position);
        holder.orderIdTextView.setText(String.format("Order Id: %s", businessOrder.getId()));
        holder.orderDateTextView.setText(String.format("Date: %s", businessOrder.getDate()));
        holder.orderPhoneTextView.setText(businessOrder.getPhone());
        holder.orderStatusTextView.setText(businessOrder.getStatus());
        holder.orderDetailsButton.setOnClickListener(v -> {
            onOrderDetailsClickedListener.onOrderDetailsClicked(businessOrder);
        });
    }

    @Override
    public int getItemCount() {
        return businessOrderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.order_id_textview)
        TextView orderIdTextView;
        @BindView(R.id.order_phone_textview)
        TextView orderPhoneTextView;
        @BindView(R.id.order_status_textview)
        TextView orderStatusTextView;
        @BindView(R.id.order_date_textview)
        TextView orderDateTextView;
        @BindView(R.id.orderDetailsButton)
        Button orderDetailsButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnOrderDetailsClickedListener {
        void onOrderDetailsClicked(BusinessOrder businessOrder);
    }
}
