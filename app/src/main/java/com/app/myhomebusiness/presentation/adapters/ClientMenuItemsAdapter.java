package com.app.myhomebusiness.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.myhomebusiness.R;
import com.app.myhomebusiness.model.MenuItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientMenuItemsAdapter extends RecyclerView.Adapter<ClientMenuItemsAdapter.MenuItemViewHolder> {

    private List<MenuItem> menuItemList;
    private OnAddToCartClickedListener onAddToCartClickedListener;

    public ClientMenuItemsAdapter(List<MenuItem> menuItemList, OnAddToCartClickedListener onAddToCartClickedListener) {
        this.menuItemList = menuItemList;
        this.onAddToCartClickedListener = onAddToCartClickedListener;
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_menu_item_layout, parent, false);
        return new MenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        MenuItem menuItem = menuItemList.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(menuItem.getImageUrl())
                .placeholder(R.drawable.logo_white_bg)
                .into(holder.imageView);
        holder.itemNameTextView.setText(menuItem.getName());
        holder.itemDescTextView.setText(menuItem.getDesc());
        holder.itemPriceTextView.setText(String.format("%s", menuItem.getPrice()));

        holder.increaseItemButton.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.itemCountTextView.getText().toString());
            holder.itemCountTextView.setText(++count + "");
        });
        holder.decreaseItemButton.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.itemCountTextView.getText().toString());
            if (count > 1) {
                holder.itemCountTextView.setText(--count + "");
            }
        });
        holder.addToCartButton.setOnClickListener(v -> {
            Toast.makeText(holder.itemView.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
        });

        holder.addToCartButton.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.itemCountTextView.getText().toString());
            onAddToCartClickedListener.onAddToCartClicked(menuItem, count);
        });
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_img)
        ImageView imageView;
        @BindView(R.id.item_name_textview)
        TextView itemNameTextView;
        @BindView(R.id.item_desc_textview)
        TextView itemDescTextView;
        @BindView(R.id.item_price_textview)
        TextView itemPriceTextView;
        @BindView(R.id.increase_item_button)
        ImageButton increaseItemButton;
        @BindView(R.id.decrease_item_button)
        ImageButton decreaseItemButton;
        @BindView(R.id.item_count_textView)
        TextView itemCountTextView;
        @BindView(R.id.add_to_cart_button)
        Button addToCartButton;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnAddToCartClickedListener {
        void onAddToCartClicked(MenuItem menuItem, int quantity);
    }
}
