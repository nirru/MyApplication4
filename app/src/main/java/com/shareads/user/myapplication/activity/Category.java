package com.shareads.user.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.adapter.categoryadapter;
import com.shareads.user.myapplication.model.categoryListModel;
import com.shareads.user.myapplication.preferences.BookPreferences;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category extends AppCompatActivity {

    static ArrayList<categoryListModel.Result> ITEMS;
    private RecyclerView recyclerView;
    private categoryadapter mAdapter;
    private BookPreferences preferences;
    public static final String MY_BOOK_ID = "book_id";
    public static final String MY_BOOK_NAME = "book_name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
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
        ITEMS = new ArrayList<>();
        mAdapter = new categoryadapter(Category.this,ITEMS);
        preferences = new BookPreferences(this);
        recyclerView = (RecyclerView) findViewById(R.id.List);
        recyclerView.setLayoutManager(new LinearLayoutManager(Category.this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(mAdapter);
        getiTemData();
        mAdapter.setOnItemClickListener(new categoryadapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String id  = ITEMS.get(position).getId();
                Intent i = new Intent(Category.this,DetailCategory.class);
                i.putExtra(MY_BOOK_ID,ITEMS.get(position).getId());
                i.putExtra(MY_BOOK_NAME,ITEMS.get(position).getName());
                startActivity(i);

            }
        });

    }
    public void getiTemData() {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        Call<categoryListModel> call = apiService.categoryList();
        call.enqueue(new Callback<categoryListModel>() {
            @Override
            public void onResponse(Call<categoryListModel> call, Response<categoryListModel> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    categoryListModel data = response.body();
                    Log.e("dede","" + response.body().toString());
                    // Please run the code
                    for (categoryListModel.Result result: data.getResult()) {
                        Log.e("NAME",result.getName());
                        mAdapter.addItem(result);
                    }
                    mAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<categoryListModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(Category.this, "Opps!!!", t.toString());

            }
        });
        /*} else {
            Utils.AlertBox(signin.this, "", "Please check internet connection");
        }*/
    }


}
