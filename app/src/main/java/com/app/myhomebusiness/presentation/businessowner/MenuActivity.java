package com.app.myhomebusiness.presentation.businessowner;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.myhomebusiness.R;
import com.app.myhomebusiness.presentation.adapters.MenuItemsAdapter;
import com.app.myhomebusiness.viewmodels.RetrieveMenuItemsViewModel;

import java.util.List;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.businessMenuRecyclerView)
    RecyclerView businessMenuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Menu");

        RetrieveMenuItemsViewModel retrieveMenuItemsViewModel = new ViewModelProvider(this).get(RetrieveMenuItemsViewModel.class);
        retrieveMenuItemsViewModel.retrieveMenuItems();
        retrieveMenuItemsViewModel.getItemsListMutableLiveData().observe(this, this::initiateMenuItemsRecyclerView);
    }

    private void initiateMenuItemsRecyclerView(List<com.app.myhomebusiness.model.MenuItem> menuItems) {
        MenuItemsAdapter adapter = new MenuItemsAdapter(menuItems);
        businessMenuRecyclerView.setAdapter(adapter);
        businessMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.addItemAction:
                startActivity(new Intent(this, AddItemActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}