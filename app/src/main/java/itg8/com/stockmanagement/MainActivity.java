package itg8.com.stockmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.material.navigation.NavigationView;

import androidx.databinding.DataBindingUtil;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.FragmentManager;

import itg8.com.stockmanagement.common.BaseActivity;

import itg8.com.stockmanagement.databinding.ActivityMainBinding;
import itg8.com.stockmanagement.home.HomeFragment;
import itg8.com.stockmanagement.home.mvvm.HomeViewModel;
import itg8.com.stockmanagement.warehouse.WarehouseDetailActivity;

public class MainActivity extends BaseActivity
        implements FragmentManager.OnBackStackChangedListener {

    private FloatingActionButton fab;
    private ActivityMainBinding binding;


    public HomeViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = setUpActionBar();
        setUpNavigation(toolbar);
        setViewModel();
        initMenus(HomeFragment.newInstance("", ""));


    }

    private void setViewModel() {
        model = new HomeViewModel(this);
        binding.setModel(model);
    }

    private Toolbar setUpActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        return toolbar;
    }

    private void setUpNavigation(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        BottomNavigationView bottomNavigationItemView = findViewById(R.id.bottom_navigation);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(bottomNavigationListener);
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
