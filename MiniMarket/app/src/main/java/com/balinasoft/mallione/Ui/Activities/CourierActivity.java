package com.balinasoft.mallione.Ui.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Fragments.BasketFragment;
import com.balinasoft.mallione.Ui.Fragments.BlurbFragment;
import com.balinasoft.mallione.Ui.Fragments.ItemFragment;
import com.balinasoft.mallione.Ui.Fragments.ItemsBasketFragment;
import com.balinasoft.mallione.Ui.Fragments.ItemsFragment;
import com.balinasoft.mallione.Ui.Fragments.MenuFragment;
import com.balinasoft.mallione.Ui.Fragments.MyFavoritesFragment;
import com.balinasoft.mallione.Ui.Fragments.MyOrderFragment;
import com.balinasoft.mallione.Ui.Fragments.MyServicesFragment;
import com.balinasoft.mallione.Ui.Fragments.NavHeaderCourierFragment;
import com.balinasoft.mallione.Ui.Fragments.NavHeaderFragment;
import com.balinasoft.mallione.Ui.Fragments.NotificationFragment;
import com.balinasoft.mallione.Ui.Fragments.PageItemFragment;
import com.balinasoft.mallione.Ui.Fragments.PagerBlurbFragment;
import com.balinasoft.mallione.Ui.Fragments.PagerItemFragment;
import com.balinasoft.mallione.Ui.Fragments.PagerRouteFragment;
import com.balinasoft.mallione.Ui.Fragments.PagerShopsFragment;
import com.balinasoft.mallione.Ui.Fragments.ProfileFragment;
import com.balinasoft.mallione.Ui.Fragments.RouteFragment;
import com.balinasoft.mallione.Ui.Fragments.RoutesFragment;
import com.balinasoft.mallione.Ui.Fragments.ShopsFragment;
import com.balinasoft.mallione.Utils.AuthManager;
import com.balinasoft.mallione.adapters.MenuAdapterRecyclerView;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.Menu.ItemMenu;
import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.models.modelUsers.Courier;
import com.balinasoft.mallione.models.modelUsers.Dispatcher;
import com.balinasoft.mallione.models.modelUsers.Manager;

public class CourierActivity extends AppCompatActivity
        implements MenuAdapterRecyclerView.OnMenuItemClickListener, ShowFragmentListener,UserListener<Courier>,ToolbarListener {
    MenuFragment menuFragment;
    private NavHeaderCourierFragment headerFragment = new NavHeaderCourierFragment();
    private Courier courier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        new AuthManager(this).setExtractListener(new AuthManager.ExtractListener() {
            @Override
            public void onEmpty() {

            }

            @Override
            public void onManager(Manager manager) {

            }

            @Override
            public void onCourier(Courier courier) {
                CourierActivity.this.courier = courier;
            }

            @Override
            public void onBuer(Buer buer) {

            }

            @Override
            public void onDispatcher(Dispatcher dispatcher) {

            }
        }).extract();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        menuFragment = MenuFragment.newInstance(getResources().getStringArray(R.array.menuCourier));
        menuFragment.setOnMenuItemClickListener(this);
        headerFragment.setLoginListener(new NavHeaderFragment.StartActivityLoginUser(this));
        getSupportFragmentManager().beginTransaction().replace(R.id.courierActivity_menuFrame, menuFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.courierActivity_navFrame, headerFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.courierActivity_frame,new MyOrderFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.courier, menu);
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


    @Override
    public boolean onMenuItemClick(ItemMenu item) {
        clearFragmetManager();
        String nameMenu = item.getName();
        if (nameMenu.equals(getString(R.string.profile))) {
            showFragment(ProfileFragment.TAG, null, false);
        }
        if (nameMenu.equals(getString(R.string.orders))) {
            showFragment(MyOrderFragment.TAG, null, false);
        }

        if (nameMenu.equals(getString(R.string.notifications))) {

            showFragment(NotificationFragment.TAG, null, false);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showCollapseFragment(String fragmentTag, Bundle data, boolean backStack) {

    }

    @Override
    public void showFragmentToolBar(String fragmentTag, Bundle data) {

    }

    @Override
    public void showFragment(String fragmentTag, Bundle data, boolean backStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.courierActivity_frame,getFragment(fragmentTag,data));
        if(backStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    private void clearFragmetManager() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().executePendingTransactions();

        }
    }

    private Fragment getFragment(String fragmentTag, Bundle data) {
        Fragment fragment = null;
        switch (fragmentTag) {
            case BlurbFragment.TAG:
                fragment = new BlurbFragment();
                break;
            case PagerBlurbFragment.TAG:
                fragment = new PagerBlurbFragment();
                break;
            case RoutesFragment.TAG:
                fragment = new RoutesFragment();
                break;
            case PagerRouteFragment.TAG:
                fragment = new PagerRouteFragment();
                break;
            case RouteFragment.TAG:
                fragment = new RouteFragment();
                break;
            case ShopsFragment.TAG:
                fragment = new ShopsFragment();
                break;
            case ItemsFragment.TAG:
                fragment = new ItemsFragment();
                break;
            case PagerShopsFragment.TAG:
                fragment = new PagerShopsFragment();
                break;
            case ProfileFragment.TAG:
                fragment = new ProfileFragment();
                break;
            case PageItemFragment.TAG:
                fragment = new PageItemFragment();
                break;
            case PagerItemFragment.TAG:
                fragment = new PagerItemFragment();
                break;
            case ItemFragment.TAG:
                fragment = new ItemFragment();
                break;
            case MyFavoritesFragment.TAG:
                fragment = new MyFavoritesFragment();
                break;
            case BasketFragment.TAG:
                fragment = new BasketFragment();
                break;
            case ItemsBasketFragment.TAG:
                fragment = new ItemsBasketFragment();
                break;
            case NotificationFragment.TAG:
                fragment = new NotificationFragment();
                break;
            case MyServicesFragment.TAG:
                fragment = new MyServicesFragment();
                break;
            case MyOrderFragment.TAG:
                fragment = new MyOrderFragment();
                break;
        }
        if (data != null) {
            fragment.setArguments(data);
        }
        return fragment;
    }

    @Override
    public Courier getUser() {
        return courier;
    }

    @Override
    public void setTittle(String tittle) {
        getSupportActionBar().setTitle(tittle);
    }

    @Override
    public void setSubTittle(String subTittle) {

    }

    @Override
    public void openToolbar() {

    }

    @Override
    protected void onResume() {
        try {
            Bundle extras = getIntent().getExtras();
            if (extras.getString("extra").equals("sendNotification")) {
                getIntent().removeExtra("extra");
                clearFragmentManager();
                showFragment(NotificationFragment.TAG, null, false);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        } catch (Exception e) {
        }
        super.onResume();
    }

    public void clearFragmentManager() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().executePendingTransactions();
        }
    }

    @Override
    public void closeToolbar() {

    }
}
