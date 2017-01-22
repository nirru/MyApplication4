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
import android.widget.TextView;

import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.adapter.DetailCategoryAdapter;
import com.shareads.user.myapplication.book;
import com.shareads.user.myapplication.model.BookCategoryModal;
import com.shareads.user.myapplication.model.BookCategoryResult;
import com.shareads.user.myapplication.preferences.BookPreferences;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shareads.user.myapplication.fragment.home.BOOK_UID;
import static com.shareads.user.myapplication.fragment.home.ISVISIBLE;

public class DetailCategory extends AppCompatActivity {
    private ArrayList<book> ItemList;
    private RecyclerView recyclerView;
    TextView more;
    TextView seecategory;
    String bookid = "1", categoryid = "";
    private BookPreferences preferences;
    private DetailCategoryAdapter mAdapter;
    public  static  List<BookCategoryResult> ITEMS ;
    String categoryname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category);
        if (getIntent()!=null){
            bookid = getIntent().getStringExtra(Category.MY_BOOK_ID);
            categoryname = getIntent().getStringExtra(Category.MY_BOOK_NAME);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(categoryname);
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
        preferences = new BookPreferences(this);
        mAdapter = new DetailCategoryAdapter(DetailCategory.this,ITEMS);
        mAdapter.setOnItemClickListener(new DetailCategoryAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent i = new Intent(DetailCategory.this,BookDetail.class);
                i.putExtra(ISVISIBLE,false);
                i.putExtra(BOOK_UID,ITEMS.get(position).getBookuniqueid());
                startActivity(i);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.categorydetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailCategory.this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(mAdapter);
        getiTemData(bookid, categoryid);


    }

public void getiTemData( String mbookid, String categoryid) {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
    final ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);

    final ProgressDialog pDialog = ProgressDialog.show(this, "", "Please wait ...", true);

    Call<BookCategoryModal> call = apiService.getbooklistcategory(mbookid);
    call.enqueue(new Callback<BookCategoryModal>() {
        @Override
        public void onResponse(Call<BookCategoryModal> call, Response<BookCategoryModal> response) {
            pDialog.dismiss();
            if (response.code() == 200) {

                BookCategoryModal data = response.body();

                for (BookCategoryResult result: data.getResult()) {
                    mAdapter.addItem(result);
                }
            }
        }

        @Override
        public void onFailure(Call<BookCategoryModal> call, Throwable t) {
            // Log error here since request failed
            Log.e("", t.toString());
            if (pDialog != null && pDialog.isShowing())
                pDialog.hide();
            Utils.AlertBox(DetailCategory.this, "Opps!!!", t.toString());

        }
    });
}

}







