package com.shareads.user.myapplication.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.shareads.user.myapplication.AppController;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.activity.BookDetail;
import com.shareads.user.myapplication.adapter.WishAdapter;
import com.shareads.user.myapplication.model.UserWishList;
import com.shareads.user.myapplication.model.UserWishListResult;
import com.shareads.user.myapplication.model.wishModl;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shareads.user.myapplication.fragment.home.BOOK_UID;

public class wishlist extends AppCompatActivity{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<UserWishListResult> ITEM_WISHLIST;
    private RecyclerView recyclerView;
    private WishAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wishlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open navigation drawer when click navigation back button
                finish();
            }
        });
        init();
    }

    private void init(){
        ITEM_WISHLIST = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.horizontal_recycler_view1);
        recyclerView.setLayoutManager( new LinearLayoutManager(wishlist.this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new WishAdapter(wishlist.this,ITEM_WISHLIST);
        mAdapter.setOnItemClickListener(new WishAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                if (v.getId()==R.id.cdelete){
                    Log.e("DELETE id===",ITEM_WISHLIST.get(position).getWishlistid());
                    removeItemFromWishList(ITEM_WISHLIST.get(position).getWishlistid());
                }else{
                    Intent i = new Intent(wishlist.this,BookDetail.class);
                    i.putExtra(home.ISVISIBLE,true);
                    i.putExtra(BOOK_UID,ITEM_WISHLIST.get(position).getBookid());
                    startActivity(i);

                }

            }
        });

        recyclerView.setAdapter(mAdapter);

        getiTemData(AppController.getInstance().getBookPrefs().getUserId());
    }


    public void getiTemData(String mUser) {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        Log.e("MY USER ID" , mUser);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(wishlist.this, "", "Please wait ...", true);

        Call<UserWishList> call = apiService.myBookWishList(mUser);
        call.enqueue(new Callback<UserWishList>() {
            @Override
            public void onResponse(Call<UserWishList> call, Response<UserWishList> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    UserWishList data = response.body();
                      mAdapter.clearItem();
                    for (UserWishListResult result: data.getResult()) {
                        mAdapter.addItem(result);
                    }
                    // Please run the code
                    mAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<UserWishList> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(wishlist.this, "Opps!!!", t.toString());

            }
        });
        /*} else {
            Utils.AlertBox(signin.this, "", "Please check internet connection");
        }*/
    }

    private void removeItemFromWishList(String wishlistid){
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Log.e("REMOVE ITEM F==",  "" + wishlistid);
//        final ProgressDialog pDialog = ProgressDialog.show(this, "", "Please wait ...", true);

//        Log.e("HEHUIII====" ,  " " + mUser);
        Call<wishModl> call = apiService.deleteFromWishList(wishlistid);
        call.enqueue(new Callback<wishModl>() {
            @Override
            public void onResponse(Call<wishModl> call, Response<wishModl> response) {
                if (response.code() == 200) {

                    Log.e("DELETED ==" ,AppController.getInstance().getBookPrefs().getUserId());
                    getiTemData(AppController.getInstance().getBookPrefs().getUserId());

                    // String arrayList = data.getResult();
                }

            }

            @Override
            public void onFailure(Call<wishModl> call, Throwable t) {
                // Log error here since request failed
                Log.e("gv", t.toString());
                Utils.AlertBox(wishlist.this, "Opps!!!", t.toString());

            }
        });
    }

}
