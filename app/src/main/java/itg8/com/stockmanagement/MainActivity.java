package itg8.com.stockmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.material.navigation.NavigationView;

import androidx.databinding.DataBindingUtil;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.FragmentManager;


import itg8.com.stockmanagement.common.BaseActivity;

import itg8.com.stockmanagement.common.GenericSpinnerAdapter;
import itg8.com.stockmanagement.common.SpinnerGenericModel;
import itg8.com.stockmanagement.common.SpinnerItemSelect;
import itg8.com.stockmanagement.databinding.ActivityMainBinding;
import itg8.com.stockmanagement.home.HomeFragment;
import itg8.com.stockmanagement.home.mvvm.HomeViewModel;
import itg8.com.stockmanagement.home.mvvm.UserViewModel;
import itg8.com.stockmanagement.warehouse.WarehouseDetailActivity;

public class MainActivity extends BaseActivity
        implements FragmentManager.OnBackStackChangedListener {

    private FloatingActionButton fab;
    private ActivityMainBinding binding;


    public HomeViewModel model;
    public UserViewModel navigation;
    public NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private static final String TAG = "MainActivity";
    public ObservableList<SpinnerGenericModel> users= new ObservableArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        findViewById();
        setUpActionBar();
        setUpNavigation();
        setViewModel();
        initMenus(HomeFragment.newInstance("", ""));


    }

    private void findViewById() {
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
    }

    private void setViewModel() {
        model = new HomeViewModel(this);
        binding.setModel(model);
        getHeaderFromNavigationView(navigationView);

    }

    private Toolbar setUpActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fab = findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        return toolbar;
    }

    private void setUpNavigation() {
        BottomNavigationView bottomNavigationItemView = findViewById(R.id.bottom_navigation);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(bottomNavigationListener);
    }

    private void getHeaderFromNavigationView(NavigationView navigationView) {
        createData();
       Spinner spinner = navigationView.getHeaderView(0).findViewById(R.id.nav_users);
        spinner.setAdapter(new GenericSpinnerAdapter(spinner.getContext(), users));
//        if (model != null) {
        SpinnerItemSelect itemDate = new SpinnerItemSelect("AppType");
        spinner.setOnItemSelectedListener(itemDate);
        itemDate.setOnItemAvailListener(userItemListener);
    }


    public SpinnerItemSelect.OnItemSelectListener userItemListener = new SpinnerItemSelect.OnItemSelectListener() {
        @Override
        public void onItemSelect(SpinnerGenericModel id) {
            Log.d(TAG, "onItemSelect: "+id.getId()+"value "+id.getValue());

        }
    };


    private void createData() {
        for (int i=0; i<9; i++){
            SpinnerGenericModel model = new SpinnerGenericModel(String.valueOf(i), "user "+String.valueOf(i));
            users.add(model);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /**
     * Handle by BackPress
     */
    @Override
    public void onBackStackChanged() {


    }


    @Override
    public void showFab() {
        model.isfab.set(true);
    }

    @Override
    public void showNaviagtion() {
        model.isBottomView.set(true);

    }

    public void startCustomActivity(Object data){
        Intent intent = new Intent(this, WarehouseDetailActivity.class);
        startActivity(intent);
    }


}
