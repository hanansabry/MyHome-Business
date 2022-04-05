package com.app.myhomebusiness.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.myhomebusiness.R;
import com.app.myhomebusiness.model.Business;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessesAdapter extends RecyclerView.Adapter<BusinessesAdapter.BusinessViewHolder> {

    private List<Business> businessList;
    private OnBusinessClickedListener onBusinessClickedListener;

    public BusinessesAdapter(List<Business> businessList, OnBusinessClickedListener onBusinessClickedListener) {
        this.businessList = businessList;
        this.onBusinessClickedListener = onBusinessClickedListener;
    }

    @NonNull
    @Override
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_item_layout, parent, false);
        return new BusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessViewHolder holder, int position) {
        Business business = businessList.get(position);
        holder.businessNameTextView.setText(business.getName());
        holder.businessAddressTextView.setText(business.getAddress());
        Picasso.with(holder.itemView.getContext())
                .load(business.getBannerImageUrl())
                .placeholder(R.drawable.logo_white_bg)
                .into(holder.businessBannerImageView);
        holder.allView.setOnClickListener(v -> onBusinessClickedListener.onBusinessClicked(business));
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    class BusinessViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.business_banner_imgview)
        ImageView businessBannerImageView;
        @BindView(R.id.business_name_textview)
        TextView businessNameTextView;
        @BindView(R.id.business_address_textview)
        TextView businessAddressTextView;
        @BindView(R.id.view)
        View allView;

        public BusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnBusinessClickedListener {
        void onBusinessClicked(Business business);
    }
}
