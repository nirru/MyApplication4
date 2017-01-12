package com.shareads.user.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shareads.user.myapplication.AppController;
import com.shareads.user.myapplication.HomeActivity;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.ValidationResult;
import com.shareads.user.myapplication.ValidationUtils;
import com.shareads.user.myapplication.model.OTPModal;
import com.shareads.user.myapplication.model.updateprofileModel;
import com.shareads.user.myapplication.preferences.BookPreferences;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;


public class EditActivity extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText mEmail;
    EditText mPassword;
    EditText mConfirmPassword;
    EditText mMobileno;
    EditText mAddress;
    EditText mName;
    RadioButton mMale,mFemale;
    Button mSignup;
    String gender = "";
    EditText  tv_email, tv_address, tv_name,tv_state,tv_city,tv_pincode,address2;
    TextView tv_moblie_num;
    CheckBox tv_gender;
    BookPreferences preferences;
    Subscription _subscription;
    boolean isClickbale = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edti_detail);
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
        mSignup=(Button) findViewById(R.id.btnsgnup);
        tv_email = (EditText) findViewById(R.id.login_emailid);
        tv_address = (EditText) findViewById(R.id.login_address);
        tv_moblie_num = (TextView) findViewById(R.id.login_mobile);
        tv_name = (EditText) findViewById(R.id.login_name);
        tv_state = (EditText) findViewById(R.id.edit_state);
        tv_city=(EditText) findViewById(R.id.edit_city);
        tv_pincode=(EditText) findViewById(R.id.edit_pincode);
        //tv_gender = (CheckBox)rootview.findViewById(R.id.login_female);
        address2 = (EditText)findViewById(R.id.login_address_2) ;


        getUserProfile();

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClickbale = true;
                setupObservables1();

            }
        });
    }



    private void setDetails(updateprofileModel data){
        try {
            tv_name.setText(data.getName());
            tv_email.setText(data.getEmail());
            tv_moblie_num.setText(data.getMobileno());
            tv_city.setText(data.getCity());
            tv_pincode.setText(data.getPincode());
            tv_address.setText(data.getAddress1());
            address2.setText(data.getAddress2());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void getUserProfile(){
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Log.e("UPDATE PROFILE","" + AppController.getInstance().getBookPrefs().getUserId());
        final ProgressDialog pDialog = ProgressDialog.show(EditActivity.this, "", "Please wait ...", true);
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
                Utils.AlertBox(EditActivity.this, "Opps!!!", t.toString());

            }
        });
    }
    public void updateprofile(final String mUser ,final String name, final String email,final String moblie_no, final String state,final String city ,final String pincode,final String address ,final String address2) {
        //if (Utils.isConnectingToInternet(register.this)) {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(EditActivity.this, "", "Please wait ...", true);
        Call<OTPModal> call = apiService.updateProfile("update",mUser,name,email,moblie_no,state,city,pincode,address,address2);
        call.enqueue(new Callback<OTPModal>() {
            @Override
            public void onResponse(Call<OTPModal> call, Response<OTPModal> response) {
                pDialog.dismiss();
                if (response.code() == 200) {

                    OTPModal data = response.body();

                   // preferences.setUserId(data.getResult());
                    preferences.setUser_name(name);
                    preferences.setUser_email(email);
                    preferences.setUser_address(address);
                    preferences.setGender(gender);
                    preferences.setUser_mob_num(moblie_no);

                    Intent i = new Intent(EditActivity.this,HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<OTPModal> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(EditActivity.this, "Opps!!!", t.toString());

            }
        });
       /* } else {
            Utils.AlertBox(register.this, "", "Please check internet connection");
        }*/
    }



    private void setupObservables1() {

        // Debounce is coming in very handy here.
        // What I had understood before is that if I use debounce, it will emit event after the give
        // time period regardless of other events.
        // But now I am realizing that this is not the case.
        // Let's say debounce interval is 200 milliseconds. Once an event is emitted, RxJava clock starts
        // ticking. Once 200 ms is up, debounce operator will emit that event.
        // One more event comes to debounce and it will start the clock for 200 ms. If another event comes
        // in 100 ms, debounce operator will reset the clock and start to count 200 ms again.
        // So let's say if you continue emitting events at 199 ms intervals, this debounce operator
        // will never emit any event.

        // Also, debounce by default goes on Scheduler thread, so it is important to add observeOn
        // and observe it on main thread.

        tv_name.setError(null);
        tv_email.setError(null);
        tv_moblie_num.setError(null);
        tv_city.setError(null);
        tv_pincode.setError(null);
        tv_address.setError(null);

        // Store values at the time of the login attempt.
        String name = tv_name.getText().toString();
        String email = tv_email.getText().toString();
        String mobile = tv_moblie_num.getText().toString();
        String city = tv_city.getText().toString();
        String pincode = tv_pincode.getText().toString();
        String address = tv_address.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!validateUsername(name.toString()).isValid()){
            tv_name.setError(validateUsername(name.toString()).getReason());
            focusView = tv_name;
            cancel = true;
        }else if(!validateEmail(email.toString()).isValid()){
            tv_email.setError(validateEmail(email.toString()).getReason());
            focusView = tv_email;
            cancel = true;
        }else if(!validatePhone(mobile.toString()).isValid()){
            tv_moblie_num.setError(validatePhone(mobile.toString()).getReason());
            focusView = tv_moblie_num;
            cancel = true;
        }else if(!validateCity(city.toString()).isValid()){
            tv_city.setError(validateCity(city.toString()).getReason());
            focusView = tv_city;
            cancel = true;
        }else if(!validatePincode(pincode.toString()).isValid()){
            tv_pincode.setError(validatePincode(pincode.toString()).getReason());
            focusView = tv_pincode;
            cancel = true;
        }else if(!validateAddress(address.toString()).isValid()){
            tv_address.setError(validateAddress(address.toString()).getReason());
            focusView = tv_address;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            updateprofile(preferences.getUserId(),name, email,mobile, "Rajasthna",city,pincode,address,address2.getText().toString());
        }
    }

    private ValidationResult<String> validateUsername(@NonNull String username) {
        return ValidationUtils.isValidUsername(username);
    }

    private ValidationResult<String> validateEmail(@NonNull String email) {
        return ValidationUtils.isValidEmailAddress(email);
    }

    private ValidationResult<String> validatePassword(@NonNull String password) {
        return ValidationUtils.isValidPassword(password);
    }

    private ValidationResult<String> validatePassword(@NonNull String password,@NonNull String newPassword) {
        return ValidationUtils.isValidCondirmPassword(password,newPassword);
    }

    private ValidationResult validatePhone(@NonNull String phone) {
        if (phone.isEmpty()) {
            return ValidationResult.failure("Phone number can't be blank", phone);
        }

        boolean isValid = ValidationUtils.isValidMobileNumber(phone);
        if (isValid) {
            return ValidationResult.success(phone);
        }

        return ValidationResult.failure("Phone Number is Invalid", phone);
    }

    private ValidationResult<String> validateAddress(@NonNull String username) {
        return ValidationUtils.isValidAddress(username);
    }

    private ValidationResult<String> validateState(@NonNull String username) {
        return ValidationUtils.isValidState(username);
    }

    private ValidationResult<String> validateCity(@NonNull String city) {
        return ValidationUtils.isValidCity(city);
    }

    private ValidationResult<String> validatePincode(@NonNull String picode) {
        return ValidationUtils.isValidPincode(picode);
    }
}