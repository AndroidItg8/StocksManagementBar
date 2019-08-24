package itg8.com.stockmanagement.common;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import itg8.com.stockmanagement.R;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        EasyPermissions.PermissionCallbacks {


    private String fragment=null;
    private FragmentManager fm;
    private FragmentTransaction ft;

    public  NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            return initNavigationMenu(menuItem);
        }
    };

    public BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@androidx.annotation.NonNull MenuItem menuItem) {
            return initBottomMenu(menuItem);
        }
    };

    private boolean initBottomMenu(MenuItem menuItem) {


        return false;
    }

    private boolean initNavigationMenu(MenuItem menuItem) {

        return onNavigationItemSelected(menuItem);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm= getSupportFragmentManager();
    }

    /**
     * Called for Every  backPress Fragment
     *
     * @param fragment
     */
    public void startFragment(Fragment fragment) {
        this.fragment = fragment.getClass().getSimpleName();
        if(fm==null)
            fm= getSupportFragmentManager();

        ft = fm.beginTransaction();

        ft.replace(R.id.frame_container, fragment, this.fragment)
                .addToBackStack(this.fragment)
                .commitAllowingStateLoss();
    }

    /**
     * Called for Initiate Fragment
     *
     * @param fragment
     */
    public void initMenus(Fragment fragment) {
        this.fragment = fragment.getClass().getSimpleName();
        if(fm==null)
            fm= getSupportFragmentManager();

        ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment).commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    public abstract FloatingActionButton getFab();


    private void showFab(){
        getFab().show();
    }

    private void hideFab(){
        getFab().hide();
    }

}
