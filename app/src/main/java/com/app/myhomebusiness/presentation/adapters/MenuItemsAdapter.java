package com.app.myhomebusiness.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.myhomebusiness.R;
import com.app.myhomebusiness.model.MenuItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.MenuItemViewHolder> {

    private List<MenuItem> menuItemList;

    public MenuItemsAdapter(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_menu_item_layout, parent, false);
        return new MenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        MenuItem menuItem = menuItemList.get(position);
        holder.itemNameTextView.setText(menuItem.getName());
        holder.itemDescTextView.setText(menuItem.getDesc());
        holder.itemPriceTextView.setText(String.format("%s", menuItem.getPrice()));
        Picasso.with(holder.itemView.getContext())
                .load(menuItem.getImageUrl())
                .placeholder(R.drawable.logo_white_bg)
                .into(holder.imageView);
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
        @BindView(R.id.item_description_textview)
        TextView itemDescTextView;
        @BindView(R.id.item_price_textview)
        TextView itemPriceTextView;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
