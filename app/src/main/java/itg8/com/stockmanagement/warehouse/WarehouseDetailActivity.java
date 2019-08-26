package itg8.com.stockmanagement.warehouse;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;

import android.view.View;

import itg8.com.stockmanagement.R;
import itg8.com.stockmanagement.databinding.ActivityWareHouseDetailActivityBinding;
import itg8.com.stockmanagement.home.mvvm.HomeViewModel;
import itg8.com.stockmanagement.warehouse.mvvm.WarehouseDetailViewModel;

public class WarehouseDetailActivity extends AppCompatActivity  {

    public ActivityWareHouseDetailActivityBinding binding;
    public WarehouseDetailViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ware_house_detail_activity);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ware_house_detail_activity);
    setUpActionBar();
        setViewModel();


    }
    private void  setUpActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setSupportActionBar(toolbar);
//        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    private void setViewModel() {
         model = new WarehouseDetailViewModel(this);
        binding.setModel(model);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
