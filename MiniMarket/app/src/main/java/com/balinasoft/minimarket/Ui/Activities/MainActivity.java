package com.balinasoft.minimarket.Ui.Activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.Dialogs.DialogItemFragment;
import com.balinasoft.minimarket.Ui.Dialogs.DialogSearch;
import com.balinasoft.minimarket.Ui.Fragments.BlurbFragment;
import com.balinasoft.minimarket.Ui.Fragments.ItemFragment;
import com.balinasoft.minimarket.Ui.Fragments.ItemsFragment;
import com.balinasoft.minimarket.Ui.Fragments.NavHeaderFragment;
import com.balinasoft.minimarket.Ui.Fragments.PageItemFragment;
import com.balinasoft.minimarket.Ui.Fragments.PagerBlurbFragment;
import com.balinasoft.minimarket.Ui.Fragments.PagerItemFragment;
import com.balinasoft.minimarket.Ui.Fragments.PagerRouteFragment;
import com.balinasoft.minimarket.Ui.Fragments.PagerShopsFragment;
import com.balinasoft.minimarket.Ui.Fragments.RouteFragment;
import com.balinasoft.minimarket.Ui.Fragments.RoutesFragment;
import com.balinasoft.minimarket.Ui.Fragments.ShopsFragment;
import com.balinasoft.minimarket.Utils.AuthManager;
import com.balinasoft.minimarket.Utils.DisableableCoordinatorLayout;
import com.balinasoft.minimarket.Utils.MetricsUtil;
import com.balinasoft.minimarket.adapters.ItemsAdapterRecyclerView;
import com.balinasoft.minimarket.interfaces.ShowFragmentListener;
import com.balinasoft.minimarket.interfaces.ToolbarListener;
import com.balinasoft.minimarket.interfaces.ToolbarSettingsListener;
import com.balinasoft.minimarket.models.ProductItems.SuperProductItem;

public class MainActivity extends AppCompatActivity
        implements ShowFragmentListener, ToolbarListener,ToolbarSettingsListener{
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;

    NavHeaderFragment headerFragment = new NavHeaderFragment();
    NestedScrollView nestedScrollView;
    FrameLayout frameLayout;
    Toolbar toolbar;
DisableableCoordinatorLayout coordinator;

    @Override
    protected void onStart() {
        super.onStart();
        new AuthManager(this).setExtractListener(new AuthManager.StartActivityExtractUser(this) {
            @Override
            public void onEmpty() {

            }
        }).extract();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        nestedScrollView=(NestedScrollView)findViewById(R.id.mainActivity_nestedScroll);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        coordinator=(DisableableCoordinatorLayout) findViewById(R.id.mainActivity_coordianator);
        frameLayout=(FrameLayout)findViewById(R.id.mainActivity_toolBar_frameContainer) ;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        headerFragment.setLoginListener(new NavHeaderFragment.StartActivityLoginUser(this));



        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_navFrameHeader, headerFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_toolBar_frameContainer, new PagerBlurbFragment()).commit();
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
                    dialogItemFragment.setUser(null);
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
    @Override
    public void showCollapseFragment(String fragmentTag, Bundle data, boolean backStack) {
        Fragment fragment = getFragment(fragmentTag, data);
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.mainActivity_frameContainer, fragment);
        if (backStack) {
            fragmentManager.addToBackStack(null);
        }
        fragmentManager.commit();
    }

    @Override
    public void showFragmentToolBar(String fragmentTag, Bundle data) {
        Fragment fragment = getFragment(fragmentTag, data);
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.mainActivity_toolBar_frameContainer, fragment).addToBackStack(null);
        fragmentManager.commit();
    }

    @Override
    public void showFragment(String fragmentTag, Bundle data, boolean backStack) {

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
            case PageItemFragment.TAG:
                fragment=new PageItemFragment();
                break;
            case PagerItemFragment.TAG:
                fragment=new PagerItemFragment();
                break;
            case ItemFragment.TAG:
                fragment=new ItemFragment();
                break;
        }
        if (data != null) {
            fragment.setArguments(data);
        }
        return fragment;
    }

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
        appBarLayout.setExpanded(true, true);
        coordinator.setPassScrolling(true);
    }

    @Override
    public void closeToolbar() {
        appBarLayout.setExpanded(false, true);
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
}
