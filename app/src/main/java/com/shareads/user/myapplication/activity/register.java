package com.shareads.user.myapplication.activity;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


import com.shareads.user.myapplication.AppController;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.ValidationResult;
import com.shareads.user.myapplication.ValidationUtils;
import com.shareads.user.myapplication.dialogUtil.OtpDialog;
import com.shareads.user.myapplication.preferences.BookPreferences;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;

public class register extends AppCompatActivity {


    EditText mEmail;
    EditText mPassword;
    EditText mConfirmPassword;
    EditText mMobileno;
    EditText mPincode;
    EditText mCountry;
    EditText mCity;
    RadioButton mMale, mFemale;
    Button mSignup;
    String gender = "male";
    BookPreferences preferences;
    Subscription _subscription;
    boolean isClickbale = false;
   TextView terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = AppController.getInstance().getBookPrefs();
        mMobileno = (EditText) findViewById(R.id.login_mobile);
        mEmail = (EditText) findViewById(R.id.login_emailid);
        mPassword = (EditText) findViewById(R.id.login_Password);
        mCity = (EditText) findViewById(R.id.edit_city);
        mPincode = (EditText) findViewById(R.id.edit_picode);
        mCountry = (EditText) findViewById(R.id.country);
        mMale = (RadioButton) findViewById(R.id.login_male);
        mFemale = (RadioButton) findViewById(R.id.login_female);
        mSignup = (Button) findViewById(R.id.btnsgnup);
        terms = (TextView)findViewById(R.id.terms);
        terms.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String url = "http://shareads.co.in/terms.html";
                 Intent i = new Intent(Intent.ACTION_VIEW);
                 i.setData(Uri.parse(url));
                 startActivity(i);
             }
         });

        mMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    gender = "male";
                }
            }
        });

        mFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if (checked){
                    gender = "female";
                }

            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClickbale = true;
                setupObservables1();
            }
        });



    }


    public void getRegister(final String mobile, String email, final String password, final String city, final String picode, final String country) {
        //if (Utils.isConnectingToInternet(register.this)) {
            final ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            final ProgressDialog pDialog = ProgressDialog.show(register.this, "", "Please wait ...", true);

            Call<com.shareads.user.myapplication.model.Response> call = apiService.getRegister(mobile,email,password,city,picode,country,gender,"100");
            call.enqueue(new Callback<com.shareads.user.myapplication.model.Response>() {
                @Override
                public void onResponse(Call<com.shareads.user.myapplication.model.Response> call, Response<com.shareads.user.myapplication.model.Response> response) {
                    pDialog.dismiss();
                    if (response.code() == 200) {

                        com.shareads.user.myapplication.model.Response data = response.body();

                        preferences.setUserId("" + data.getUserid());
                        preferences.setUser_name("");
                        preferences.setUser_email(mEmail.getText().toString());
                        preferences.setUser_address("");
                        preferences.setGender(gender);
                        preferences.setUser_mob_num(mMobileno.getText().toString());

                        FragmentManager fm = getFragmentManager();
                        OtpDialog dialogFragment = new OtpDialog(data.getUserid());
                        dialogFragment.show(fm, "OTP Fragment");

//                        Toast.makeText(register.this, data.getMessage(), Toast.LENGTH_SHORT).show();




                    }
                }

                @Override
                public void onFailure(Call<com.shareads.user.myapplication.model.Response> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("", t.toString());
                    if (pDialog != null && pDialog.isShowing())
                        pDialog.hide();
                    Utils.AlertBox(register.this, "Opps!!!", t.toString());

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

        mMobileno.setError(null);
        mEmail.setError(null);
        mPassword.setError(null);
        mCity.setError(null);
        mPincode.setError(null);

        // Store values at the time of the login attempt.
        String mobile_no = mMobileno.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String city = mCity.getText().toString();
        String pincode = mPincode.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(!validatePhone(mobile_no.toString()).isValid()){
            mMobileno.setError(validatePhone(mobile_no.toString()).getReason());
            focusView = mMobileno;
            cancel = true;
        }else if(!validateEmail(email.toString()).isValid()){
            mEmail.setError(validateEmail(email.toString()).getReason());
            focusView = mEmail;
            cancel = true;
        }else if(!validatePassword(password.toString()).isValid()){
            mPassword.setError(validateEmail(password.toString()).getReason());
            focusView = mPassword;
            cancel = true;
        }else if(!validateCity(city.toString()).isValid()){
            mCity.setError(validateCity(city.toString()).getReason());
            focusView = mCity;
            cancel = true;
        }else if(!validatePincode(pincode.toString()).isValid()){
            mPincode.setError(validatePincode(pincode.toString()).getReason());
            focusView = mPincode;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            getRegister(mobile_no,email,password,city,pincode,gender);
        }


//        Observable<Boolean> phoneObservable = RxTextView.textChanges(mMobileno)
//                .debounce(800, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<CharSequence, Boolean>() {
//                    @Override
//                    public Boolean call(CharSequence s) {
//                        ValidationResult result = validatePhone(s.toString());
//                        mMobileno.setError(result.getReason());
//                        return result.isValid();
//
//                    }
//                });
//
//        Observable<Boolean> emailObservable = RxTextView.textChanges(mEmail)
//                .debounce(800, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<CharSequence, Boolean>() {
//                    @Override
//                    public Boolean call(CharSequence s) {
//                        ValidationResult result = validateEmail(s.toString());
//                        mEmail.setError(result.getReason());
//                        return result.isValid();
//                    }
//                });
//        Observable<Boolean> passwordObservable = RxTextView.textChanges(mPassword)
//                .debounce(800, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<CharSequence, Boolean>() {
//                    @Override
//                    public Boolean call(CharSequence s) {
//                        ValidationResult result = validatePassword(s.toString());
//                        mPassword.setError(result.getReason());
//                        return result.isValid();
//                    }
//                });
//
//
//        Observable<Boolean> userNameObservable = RxTextView.textChanges(mCity)
//                .debounce(800, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<CharSequence, Boolean>() {
//                    @Override
//                    public Boolean call(CharSequence s) {
//                        ValidationResult result = validateCity(s.toString());
//                        mCity.setError(result.getReason());
//                        return result.isValid();
//                    }
//                });
//
//
//
//
//
//        Observable<Boolean> confirmPassword = RxTextView.textChanges(mPincode)
//                .debounce(800, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<CharSequence, Boolean>() {
//                    @Override
//                    public Boolean call(CharSequence s) {
//                        ValidationResult result = validatePicode(s.toString());
//                        mPincode.setError(result.getReason());
//                        return result.isValid();
//                    }
//                });
//
//
//
//
//        _subscription = Observable.combineLatest(userNameObservable, emailObservable,passwordObservable,confirmPassword,phoneObservable, new Func5<Boolean, Boolean, Boolean,Boolean, Boolean, Boolean>() {
//            @Override
//            public Boolean call(Boolean username,Boolean validEmail, Boolean validPassword,Boolean validconfirmPassword,Boolean validPhone) {
////               Log.i(TAG, "email: " + validEmail + ", username: " + validUsername + ", phone: " + validPhone);
//                return username && validEmail && validPassword && validconfirmPassword && validPhone;
//            }
//        }).subscribe(new Action1<Boolean>() {
//            @Override
//            public void call(Boolean aBoolean) {
//                if (aBoolean){
//                    if (isClickbale){
//                        String mobileNumber = mMobileno.getText().toString();
//                        String enterEmail = mEmail.getText().toString();
//                        String enterPassword = mPassword.getText().toString();
//                        String city = mCity.getText().toString();
//                        String pincode = mPincode.getText().toString();
//                        getRegister(mobileNumber,enterEmail,enterPassword,city,pincode,gender);
//                        isClickbale = false;
//                    }
//
//                }
//
//            }
//        });
    }

    private ValidationResult<String> validateCity(@NonNull String username) {
        return ValidationUtils.isValidCity(username);
    }

    private ValidationResult<String> validatePicode(@NonNull String username) {
        return ValidationUtils.isValidPicode(username);
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
            return ValidationResult.failure("Phone Number can't be blank", phone);
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

    private ValidationResult<String> validatePincode(@NonNull String picode) {
        return ValidationUtils.isValidPincode(picode);
    }


}


