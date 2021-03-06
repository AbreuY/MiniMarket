package com.balinasoft.mallione.Ui.Activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.view.View;

import com.balinasoft.mallione.Implementations.SimpleDrawerListener;
import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Dialogs.DialogItemFragment;
import com.balinasoft.mallione.Ui.Dialogs.DialogSearch;
import com.balinasoft.mallione.Ui.Fragments.BasketFragment;
import com.balinasoft.mallione.Ui.Fragments.BlurbFragment;
import com.balinasoft.mallione.Ui.Fragments.DisputsFragment;
import com.balinasoft.mallione.Ui.Fragments.ItemFragment;
import com.balinasoft.mallione.Ui.Fragments.ItemsBasketFragment;
import com.balinasoft.mallione.Ui.Fragments.ItemsFragment;
import com.balinasoft.mallione.Ui.Fragments.MenuFragment;
import com.balinasoft.mallione.Ui.Fragments.MyFavoritesFragment;
import com.balinasoft.mallione.Ui.Fragments.MyOrderFragment;
import com.balinasoft.mallione.Ui.Fragments.MyServicesFragment;
import com.balinasoft.mallione.Ui.Fragments.NavHeaderFragment;
import com.balinasoft.mallione.Ui.Fragments.NavHeaderManagerFragment;
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
import com.balinasoft.mallione.Utils.DisableableCoordinatorLayout;
import com.balinasoft.mallione.Utils.MetricsUtil;
import com.balinasoft.mallione.adapters.ItemsAdapterRecyclerView;
import com.balinasoft.mallione.adapters.MenuAdapterRecyclerView;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.Swipeable;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.ToolbarSettingsListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.Menu.ItemMenu;
import com.balinasoft.mallione.models.ProductItems.SuperProductItem;
import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.models.modelUsers.Courier;
import com.balinasoft.mallione.models.modelUsers.Dispatcher;
import com.balinasoft.mallione.models.modelUsers.Manager;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.ApiFactory;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestCountNotification;
import com.balinasoft.mallione.networking.Response.ResponseCountNotification;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;

public class ManagerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Swipeable,ToolbarSettingsListener,ToolbarListener, MenuAdapterRecyclerView.OnMenuItemClickListener, ShowFragmentListener, UserListener<User> {
    NavHeaderManagerFragment headerFragment = new NavHeaderManagerFragment();
    Manager manager;
    private AppBarLayout appBarLayout;
    private SwipyRefreshLayout swipyRefreshLayout;

    @Override
    protected void onStart() {
        headerFragment.setLoginListener(new NavHeaderFragment.StartActivityLoginUser(this));
        new AuthManager(this).setExtractListener(new AuthManager.ExtractListener() {
            @Override
            public void onEmpty() {

            }

            @Override
            public void onManager(Manager manager) {
                ManagerActivity.this.manager = manager;
            }

            @Override
            public void onCourier(Courier courier) {

            }

            @Override
            public void onBuer(Buer buer) {

            }

            @Override
            public void onDispatcher(Dispatcher dispatcher) {

            }
        }).extract();
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager = getIntent().getParcelableExtra(Manager.class.getCanonicalName());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        drawer.openDrawer(GravityCompat.START);
        drawer.addDrawerListener(new SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ApiFactory.getService().countNotification(new RequestCountNotification(manager.getSession_id(), manager.getId())).enqueue(new MyCallbackWithMessageError<ResponseCountNotification>() {
                    @Override
                    public void onData(ResponseCountNotification data) {
                        if (data.getResult().getCount() == 0) {

                        }
                        menuFragment.addNotification(getString(R.string.notifications), data.getResult().getCount());
                    }

                    @Override
                    public void onRequestEnd() {

                    }
                });
            }
        });
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.mangerActivity_collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.managerActivity_app_bar_layout);
        coordinator = (DisableableCoordinatorLayout) findViewById(R.id.managerActivity_coordinator);
        swipyRefreshLayout=(SwipyRefreshLayout)findViewById(R.id.managerActivity_swipe);
        getSupportFragmentManager().beginTransaction().add(R.id.managerActivity_navFrame, headerFragment).commit();
        menuFragment = MenuFragment.newInstance(getResources().getStringArray(R.array.menuManager));
        menuFragment.setOnMenuItemClickListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.managerActivity_frameMenu, menuFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.managerActivity_toolBarFrameContainer,new PagerBlurbFragment()).commit();

    }

    MenuFragment menuFragment;

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
        getMenuInflater().inflate(R.menu.manager, menu);
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
            dialogSearch=new DialogSearch();
            dialogSearch.setClickItemListener(new ItemsAdapterRecyclerView.ClickItemListener() {
                @Override
                public void onClick(final SuperProductItem productItem) {
                    dialogItemFragment.setProductListener(new DialogItemFragment.ProductListener() {
                        @Override
                        public SuperProductItem getProduct() {
                            return productItem;
                        }
                    });
                    dialogItemFragment.setUser(manager);
                    dialogItemFragment.show(getSupportFragmentManager(),"");
                }
            });
            dialogSearch.show(getSupportFragmentManager(),"");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    DialogSearch dialogSearch;
    DialogItemFragment dialogItemFragment=new DialogItemFragment();
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMenuItemClick(ItemMenu item) {
        clearFragmetManager();

        String nameMenu = item.getName();
        if (nameMenu.equals(getString(R.string.shops))) {
            showFragmentToolBar(PagerBlurbFragment.TAG, null);
        }
        if (nameMenu.equals(getString(R.string.profile))) {
            showCollapseFragment(ProfileFragment.TAG, null, false);
        }

        if (nameMenu.equals(getString(R.string.orders))) {

            showFragment(MyOrderFragment.TAG, null, false);
        }
        if (nameMenu.equals(getString(R.string.record))) {
            showCollapseFragment(MyServicesFragment.TAG, null, false);
        }
        if (nameMenu.equals(getString(R.string.disputs))) {
            showFragment(DisputsFragment.TAG,null,false);
        }
        if (nameMenu.equals(getString(R.string.notifications))) {
            showFragment(NotificationFragment.TAG, null, false);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void clearFragmetManager() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().executePendingTransactions();

        }
    }

    @Override
    public void showCollapseFragment(String fragmentTag, Bundle data, boolean backStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

       transaction.replace(R.id.managerActivity_frameContainer, getFragment(fragmentTag, data));
        if(backStack){
            transaction.addToBackStack(null);
        }
        transaction.commit();
        findViewById(R.id.managerActivity_frameContainerNonCollapse).setVisibility(View.GONE);
        findViewById(R.id.managerActivity_frameContainer).setVisibility(View.VISIBLE);

    }

    @Override
    public void showFragmentToolBar(String fragmentTag, Bundle data) {
       getSupportFragmentManager().beginTransaction().replace(R.id.managerActivity_toolBarFrameContainer, getFragment(fragmentTag,data)).addToBackStack(null).commit();

    }

    @Override
    public void showFragment(String fragmentTag, Bundle data, boolean backStack) {
        getSupportFragmentManager().beginTransaction().replace(R.id.managerActivity_frameContainerNonCollapse,getFragment(fragmentTag,data)).commit();
        findViewById(R.id.managerActivity_frameContainerNonCollapse).setVisibility(View.VISIBLE);
        findViewById(R.id.managerActivity_frameContainer).setVisibility(View.GONE);
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
            case DisputsFragment.TAG:
                fragment=new DisputsFragment();
                break;
        }
        if (data != null) {
            fragment.setArguments(data);
        }
        return fragment;
    }

    @Override
    public User getUser() {
        return manager;
    }

    private DisableableCoordinatorLayout coordinator;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    public void setTittle(String tittle) {
        collapsingToolbarLayout.setTitle(tittle);
        collapsingToolbarLayout.setExpandedTitleMarginStart(MetricsUtil.convertDpToPixel(8, this));
        //  collapsingToolbarLayout.setExpandedTitleTypeface(R.style.CollapsedAppBar);
        //  collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

    }

    @Override
    public void setSubTittle(String subTittle) {

    }

    @Override
    public void openToolbar() {
        ((AppBarLayout) findViewById(R.id.managerActivity_app_bar_layout)).setExpanded(true, true);
        coordinator.setPassScrolling(true);
    }

    @Override
    public void closeToolbar() {
        ((AppBarLayout) findViewById(R.id.managerActivity_app_bar_layout)).setExpanded(false, true);
        coordinator.setPassScrolling(false);
    }

    @Override
    public void hideTitle() {
        collapsingToolbarLayout.setTitleEnabled(false);
        getSupportActionBar().setTitle(null);
    }

    @Override
    public void showitle() {
        collapsingToolbarLayout.setTitleEnabled(true);
    }

    @Override
    public SwipyRefreshLayout getSwipyRefreshLayout() {
        return swipyRefreshLayout;
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

}
