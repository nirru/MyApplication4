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
import android.widget.Button;
import android.widget.TextView;

import com.shareads.user.myapplication.AppController;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.adapter.cartadapter;
import com.shareads.user.myapplication.book;
import com.shareads.user.myapplication.model.CartResponse;
import com.shareads.user.myapplication.model.CartResult;
import com.shareads.user.myapplication.model.cartmodel;
import com.shareads.user.myapplication.model.wishModl;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cartActivity extends AppCompatActivity {

    private ArrayList<book> ItemList;
    private RecyclerView recyclerView;
    private cartadapter mAdapter;
    Button checkout;
    TextView payment;
    static ArrayList<CartResult> ITEMS_CARTS;
    private int minValue = Integer.MIN_VALUE;
    private int maxValue = Integer.MAX_VALUE;
    ProgressDialog myDialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);
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
        myDialog = new ProgressDialog(cartActivity.this);
        myDialog.setMessage("Loading...");
        myDialog.setCanceledOnTouchOutside(false);
        ITEMS_CARTS = new ArrayList<>();
        payment = (TextView)findViewById(R.id.payment);
        checkout=(Button) findViewById(R.id.checkout_button);
       // preferences = new BookPreferences(this);

        mAdapter = new cartadapter(cartActivity.this,ITEMS_CARTS);
        mAdapter.setOnItemClickListener(new cartadapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                if (v.getId()==R.id.cdelete){
                    Log.e("DELETE FROM CART===","" + position);
                    myDialog.show();
                    removeItemFromCart(AppController.getInstance().getBookPrefs().getUserId(),ITEMS_CARTS.get(position).getBookid(),"remove");
                }
                else if (v.getId()==R.id.minusButton){
                    Log.e("MINUS CART===","" + "MINUS CLICC");

                        TextView textView = (TextView)recyclerView.getChildAt(position).findViewById(R.id.valueTextView);
                        TextView priceView = (TextView)recyclerView.getChildAt(position).findViewById(R.id.price);
                        decrementValue(textView,priceView,position);
                }
                else if (v.getId()==R.id.plusButton){
                    Log.e("PLUS CART===","" + "PLIS CLICC");
                    TextView textView = (TextView)recyclerView.getChildAt(position).findViewById(R.id.valueTextView);
                    TextView priceView = (TextView)recyclerView.getChildAt(position).findViewById(R.id.price);
                    incrementValue(textView,priceView,position);

                }
                else{
                    Log.e("CLCIK CART===","" + position);
                }
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(cartActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        getUserCart();
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(cartActivity.this,Shipping_address.class);
                startActivity(intent);
            }
        });
    }

    public void getUserCart() {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

//        final ProgressDialog pDialog = ProgressDialog.show(this, "", "Please wait ...", true);

        if (!myDialog.isShowing())
            myDialog.show();
        Log.e("GET USER IS ==" ,"" + AppController.getInstance().getBookPrefs().getUserId());
        Call<CartResponse> call = apiService.getUserCart(AppController.getInstance().getBookPrefs().getUserId());
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                myDialog.dismiss();
                if (response.code() == 200) {

                    CartResponse data = response.body();
                    int sum = 0;
                    // String arrayList = data.getResult();
                     mAdapter.clearItem();
                    for (CartResult result: data.getResult()) {
                        mAdapter.addItem(result);
                        sum = sum + Integer.parseInt(result.getPrice());
                    }

                   mAdapter.notifyDataSetChanged();
                   payment.setText(" " + sum);
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (myDialog != null && myDialog.isShowing())
                    myDialog.hide();
                Utils.AlertBox(cartActivity.this, "Opps!!!", t.toString());

            }
        });
        /*} else {
            Utils.AlertBox(signin.this, "", "Please check internet connection");
        }*/
    }

    public void removeItemFromCart(final String mUser , final String mbookid, String maction) {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

//        final ProgressDialog pDialog = ProgressDialog.show(this, "", "Please wait ...", true);

//        Log.e("HEHUIII====" ,  " " + mUser);
        Call<cartmodel> call = apiService.myaddtocart(mUser,mbookid,"remove");
        call.enqueue(new Callback<cartmodel>() {
            @Override
            public void onResponse(Call<cartmodel> call, Response<cartmodel> response) {
                if (response.code() == 200) {

                    Log.e("DELETED ==" ,"" + "DELETE");
//                    getUserCart();
                    movetoWistList(mUser,mbookid);

                    // String arrayList = data.getResult();
                }
            }

            @Override
            public void onFailure(Call<cartmodel> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                Utils.AlertBox(cartActivity.this, "Opps!!!", t.toString());

            }
        });
    }

    private void incrementValue(TextView valueTextView,TextView priceView,int position) {
        int currentVal = Integer.valueOf(valueTextView.getText().toString());
        if(currentVal < maxValue) {
            int price = Integer.parseInt(ITEMS_CARTS.get(position).getPrice()) * (currentVal + 1);
            priceView.setText("" + price);
            payment.setText(" " + sum(ITEMS_CARTS));
            valueTextView.setText(String.valueOf(currentVal + 1));
        }
    }

    private void decrementValue(TextView valueTextView,TextView priceView,int position) {
        int currentVal = Integer.valueOf(valueTextView.getText().toString());
        if(currentVal > 1) {
            int price = Integer.parseInt(ITEMS_CARTS.get(position).getPrice()) * (currentVal - 1);
            priceView.setText("" + price);
            payment.setText(" " + sum(ITEMS_CARTS));
            valueTextView.setText(String.valueOf(currentVal - 1));

        }
    }

    public  int sum(ArrayList<CartResult> list){
        if(list==null || list.size()<1)
            return 0;

        int sum = 0;
        for (int i = 0;i<list.size();i++){
            TextView priceView = (TextView)recyclerView.getChildAt(i).findViewById(R.id.price);
            int price = Integer.parseInt(priceView.getText().toString());
            sum=sum+ price;
        }

        return sum;
    }

    public void movetoWistList(String mUser ,String mbookid) {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<wishModl> call = apiService.myaddtowish(mUser,mbookid,"1");
        call.enqueue(new Callback<wishModl>() {
            @Override
            public void onResponse(Call<wishModl> call, Response<wishModl> response) {
                if (response.code() == 200) {

                    wishModl data = response.body();

                    // String arrayList = data.getResult();
                    getUserCart();

//                    Toast.makeText(cartActivity.this,data.getMessage(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent (cartActivity.this,FrameActivity.class);
//                    startActivity(intent);

                    myDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<wishModl> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (myDialog != null && myDialog.isShowing())
                    myDialog.hide();
                Utils.AlertBox(cartActivity.this, "Opps!!!", t.toString());

            }
        });
        /*} else {
            Utils.AlertBox(signin.this, "", "Please check internet connection");
        }*/
    }

}


