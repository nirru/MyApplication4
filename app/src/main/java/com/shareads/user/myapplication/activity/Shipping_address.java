package com.shareads.user.myapplication.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shareads.user.myapplication.AppController;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.model.updateprofileModel;
import com.shareads.user.myapplication.preferences.BookPreferences;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shipping_address extends AppCompatActivity {
    BookPreferences preferences;
    TextView password,tv_state,tv_city,tv_address,tv_pincode,tv_homeDelivery;
    int count;
    Button change_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
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

        if (getIntent()!=null){
            count = getIntent().getIntExtra("COUNT",0);
        }
        change_address = (Button)findViewById(R.id.change_address);
        tv_homeDelivery = (TextView)findViewById(R.id.home_delivery);
        tv_state = (TextView)findViewById(R.id.s_state);
        tv_address = (TextView)findViewById(R.id.s_address);
        tv_city = (TextView)findViewById(R.id.s_city);
        tv_pincode=(TextView)findViewById(R.id.s_pincode) ;
        int finalDeliveryCharges = 50 * count;
        tv_homeDelivery.setText("Home Delivery charges Rs " + finalDeliveryCharges);

        change_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getUserProfile();

    }



    private void getUserProfile(){
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Log.e("UPDATE PROFILE","" + AppController.getInstance().getBookPrefs().getUserId());
        final ProgressDialog pDialog = ProgressDialog.show(Shipping_address.this, "", "Please wait ...", true);
        Call<updateprofileModel> call = apiService.getProfile("edit",AppController.getInstance().getBookPrefs().getUserId());
        call.enqueue(new Callback<updateprofileModel>() {
            @Override
            public void onResponse(Call<updateprofileModel> call, Response<updateprofileModel> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    updateprofileModel data = response.body();
                    Log.e("RESPONSE==" ,""+ data.toString());
                    setDetails(data);
                }
            }

            @Override
            public void onFailure(Call<updateprofileModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(Shipping_address.this, "Opps!!!", t.toString());

            }
        });
    }

    private void setDetails(updateprofileModel data) {
        try
        {
            tv_address.setText("Address:-"+ data.getAddress1());
            tv_city.setText("City:-" + data.getCity());
            tv_pincode.setText("Pincode:-" + data.getPincode());
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
