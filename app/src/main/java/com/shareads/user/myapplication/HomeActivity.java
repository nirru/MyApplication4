package com.shareads.user.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.shareads.user.myapplication.activity.BookDetail;
import com.shareads.user.myapplication.activity.cartActivity;
import com.shareads.user.myapplication.activity.signin;
import com.shareads.user.myapplication.adapter.SearchAdapter;
import com.shareads.user.myapplication.fragment.home;
import com.shareads.user.myapplication.fragment.myAccount;
import com.shareads.user.myapplication.fragment.mybook;
import com.shareads.user.myapplication.fragment.orderFragment;
import com.shareads.user.myapplication.fragment.profile;
import com.shareads.user.myapplication.fragment.uploadBook;
import com.shareads.user.myapplication.fragment.wishlist;
import com.shareads.user.myapplication.model.SearchModal;
import com.shareads.user.myapplication.model.SearchResult;
import com.shareads.user.myapplication.preferences.BookPreferences;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static com.shareads.user.myapplication.fragment.home.ISVISIBLE;

public class HomeActivity extends AppCompatActivity {

    ListView mDrawerList;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ArrayList<nav_item> mNavItems = new ArrayList<nav_item>();
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ArrayList<String> mProfile;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    BookPreferences preferences;
    RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    SearchAdapter searchAdapter;
    static ArrayList<SearchResult> ITEMS_SEARCH;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = AppController.getInstance().getBookPrefs();
        Log.e("YYYYYY===" ,"" + preferences.getUserId());
        //getSupportActionBar().setLogo(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        ITEMS_SEARCH = new ArrayList<>();
        searchAdapter = new SearchAdapter(HomeActivity.this,ITEMS_SEARCH);
        searchAdapter.setOnItemClickListener(new SearchAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent i = new Intent(HomeActivity.this, BookDetail.class);
                i.putExtra(home.BOOK_UID,ITEMS_SEARCH.get(position).getBookuniqueid());
                i.putExtra(ISVISIBLE,false);
                startActivity(i);
            }
        });
        relativeLayout = (RelativeLayout)findViewById(R.id.mainContent);
        recyclerView = (RecyclerView)findViewById(R.id.recyleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(searchAdapter);
        listView = (ListView) findViewById(R.id.profile);
        setData();
        adapter = new ListViewAdapter(this, R.layout.drawer_profile, mProfile);
        listView.setAdapter(adapter);

        // add the custom view to the action bar


        mNavItems.add(new nav_item("Home", "", R.drawable.home));
        mNavItems.add(new nav_item("Upload Book", "", R.drawable.uploadbooks));
        mNavItems.add(new nav_item("My Profile", "", R.drawable.profile_icon));
        mNavItems.add(new nav_item("My Books", "", R.drawable.mybooks));
        mNavItems.add(new nav_item("My Order", "", R.drawable.order_icon));
        mNavItems.add(new nav_item("My WishList", "", R.drawable.whishlist));
        mNavItems.add(new nav_item("My Account", "", R.drawable.myaccounticon));
        mNavItems.add(new nav_item("Signout", "", R.drawable.logout));
        mNavItems.add(new nav_item("Tell Friends", "", R.drawable.tellafriendicon));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setSelector(android.R.color.background_light);


        Fragment fragment = new home();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContent, fragment);
        transaction.commit();


        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /**
             * Called when a drawer has settled in a completely closed state.
             */
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    private void setData() {
        mProfile = new ArrayList<String>();

        if (preferences.getUser_name() != null && preferences.getUser_name().length() > 0)
            mProfile.add(preferences.getUser_name());
        else
            mProfile.add("Profile");

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItemFromDrawer(int position) {
        Intent fragment = null;
        switch (position) {
            case 0:
                fragment = new Intent(HomeActivity.this,HomeActivity.class);
                break;
            case 1:
                fragment = new Intent(HomeActivity.this,uploadBook.class);
                break;
            case 2:
                fragment = new Intent(HomeActivity.this,profile.class);
                break;
            case 3:
                fragment = new Intent(HomeActivity.this,mybook.class);
                break;
            case 4:
                fragment = new Intent(HomeActivity.this,orderFragment.class);
                break;
            case 5:
                fragment = new Intent(HomeActivity.this,wishlist.class);
                break;
            case 6:
                fragment = new Intent(HomeActivity.this,myAccount.class);
                break;
            case 7:
                SharedPreferences preferences = getSharedPreferences(BookPreferences.USER_PREFS, 0);
                preferences.edit().clear().commit();
                Intent intent1 = new Intent(HomeActivity.this, signin.class);
                startActivity(intent1);
                finish();
                return;
            case 8:
                mDrawerList.setItemChecked(position, true);
                setTitle(mNavItems.get(position).mTitle);

                // Close the drawer
                mDrawerLayout.closeDrawer(mDrawerPane);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "A wonderful application is available for exchanging books.Visit www.shareads.co.in ");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return;
            default:
                break;
        }

        mDrawerLayout.closeDrawer(mDrawerPane);
        startActivity(fragment);

        mDrawerList.setItemChecked(position, true);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();


        setupSearchView(searchView);
        // Configure the search info and add any event listeners...
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent intent = new Intent(HomeActivity.this, cartActivity.class);
                startActivity(intent);
                return true;
//
//            case R.id.logout:
//                SharedPreferences preferences = getSharedPreferences(BookPreferences.USER_PREFS, 0);
//                preferences.edit().clear().commit();
//                Intent intent1 = new Intent(HomeActivity.this, signin.class);
//                startActivity(intent1);
//                finish();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupSearchView(SearchView searchView)
    {
        // search hint
        searchView.setQueryHint("Search by name,author,isbn no");
        RxSearchView.queryTextChanges(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        if(charSequence!=null){
                            // Here you can get the text
                            if (charSequence.length()!=0){
                                recyclerView.setVisibility(View.VISIBLE);
                                relativeLayout.setVisibility(View.GONE);
                                queryDatabase(charSequence);
                            }else{
                                searchAdapter.clearItem();
                                recyclerView.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                });
    }

    private void queryDatabase(CharSequence charss){
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(this, "", "Please wait ...", true);

        Call<SearchModal> call = apiService.searchQuery(charss.toString());
        call.enqueue(new Callback<SearchModal>() {
            @Override
            public void onResponse(Call<SearchModal> call, Response<SearchModal> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    SearchModal data = response.body();

                    // String arrayList = data.getResult();
                     searchAdapter.clearItem();
                    for (SearchResult result: data.getResults()) {
                        searchAdapter.addItem(result);
                    }

                    searchAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<SearchModal> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(HomeActivity.this, "Opps!!!", t.toString());

            }
        });
        /*} else {
            Utils.AlertBox(signin.this, "", "Please check internet connection");
        }*/
    }


}
