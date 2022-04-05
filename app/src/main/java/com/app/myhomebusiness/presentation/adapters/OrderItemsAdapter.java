package com.app.myhomebusiness.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.myhomebusiness.R;
import com.app.myhomebusiness.model.OrderItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemViewHolder> {

    private List<OrderItem> orderItemList;

    public OrderItemsAdapter(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_details_layout, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        OrderItem orderItem = orderItemList.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(orderItem.getItem().getImageUrl())
                .placeholder(R.drawable.logo_white_bg)
                .into(holder.itemImageView);

        holder.itemNameTextView.setText(orderItem.getItem().getName());
        holder.itemDescTextView.setText(orderItem.getItem().getDesc());
        holder.itemPriceTextView.setText(String.format("%.2f", orderItem.getTotalPrice()));
        holder.quantityTextView.setText(String.format("%d", orderItem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView itemImageView;
        @BindView(R.id.product_name_textview)
        TextView itemNameTextView;
        @BindView(R.id.product_details_textview)
        TextView itemDescTextView;
        @BindView(R.id.order_price_textview)
        TextView itemPriceTextView;
        @BindView(R.id.quantity_textview)
        TextView quantityTextView;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
