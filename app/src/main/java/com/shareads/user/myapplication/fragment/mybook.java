package com.shareads.user.myapplication.fragment;

import android.app.ProgressDialog;
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
import com.shareads.user.myapplication.adapter.BookAdapter;
import com.shareads.user.myapplication.model.MyBookListModel;
import com.shareads.user.myapplication.preferences.BookPreferences;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class mybook extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<MyBookListModel.Result> ItemList;
    private RecyclerView recyclerView;
    private BookAdapter mAdapter;
    private BookPreferences preferences;
    static ArrayList<MyBookListModel.Result> ITEMS_BOOKS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mybook);
        preferences = AppController.getInstance().getBookPrefs();
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
        getiTemData(preferences.getUserId());
    }

    private void init(){
        ITEMS_BOOKS = new ArrayList<>();
        mAdapter = new BookAdapter(mybook.this,ITEMS_BOOKS);
        recyclerView = (RecyclerView) findViewById(R.id.book_recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(mybook.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
    }



    public void getiTemData(String mUser) {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(mybook.this, "", "Please wait ...", true);
        Call<MyBookListModel> call = apiService.myBookList(mUser);
        call.enqueue(new Callback<MyBookListModel>() {
            @Override
            public void onResponse(Call<MyBookListModel> call, Response<MyBookListModel> response) {
                if (response.code() == 200) {

                    MyBookListModel data = response.body();

                    List<MyBookListModel.Result> arrayList = data.getResult();

                    // Please run the code
                    for (MyBookListModel.Result result: arrayList) {
                        mAdapter.addItem(result);
                    }
                    mAdapter.notifyDataSetChanged();

                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MyBookListModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(mybook.this, "Opps!!!", t.toString());

            }
        });
    }

}




