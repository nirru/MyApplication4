package com.shareads.user.myapplication.activity;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shareads.user.myapplication.AppController;
import com.shareads.user.myapplication.BusProvider;
import com.shareads.user.myapplication.HomeActivity;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.ValidationResult;
import com.shareads.user.myapplication.ValidationUtils;
import com.shareads.user.myapplication.dialogUtil.ForgetPasswordDialog;
import com.shareads.user.myapplication.model.LoginData;
import com.shareads.user.myapplication.preferences.BookPreferences;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.otto.Bus;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;

/**
 * A login screen that offers login via email/password.
 */
public class signin extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

//    TextView loginMessage;

    private TextView information, extraInformation;
    private final static String TAG = "signin";
    public static Bus bus;



    //Values for email and password at the time of login form
    private String mUser;
    private String mPassword;


    // UI references.
    TextView forgot_password;
    EditText mEmailView;
    EditText mPasswordView;
    Button mSignInButton;
    Button mregisterButton;
    ImageView mFacebook_btn,mGoogle_btn;
    TextView mSkip;
    BookPreferences preferences;
    CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;
    static final int RC_SIGN_IN = 9001;
    GoogleSignInOptions gso;
    Subscription _subscription;
    boolean isClickbale = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences  = AppController.getInstance().getBookPrefs();
        if (!preferences.getUserId().equals("")){
            Intent i = new Intent(signin.this,HomeActivity.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_login);
        // Set up the login form.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
//        preferences = new BookPreferences(this);
        mEmailView = (EditText) findViewById(R.id.txtemail);
        mPasswordView = (EditText) findViewById(R.id.txtpassword);
        mSignInButton = (Button) findViewById(R.id.btnsignup);
        mregisterButton = (Button) findViewById(R.id.btnregister);
        mFacebook_btn = (ImageView)findViewById(R.id.imageButton6) ;
        mGoogle_btn = (ImageView)findViewById(R.id.button2 );
        mSkip = (TextView) findViewById(R.id.btnskip);
        forgot_password = (TextView)findViewById(R.id.textView3);
//        mSignInButton.setEnabled(false);

        forgot_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                ForgetPasswordDialog dialogFragment = new ForgetPasswordDialog();
                dialogFragment.show(fm, "Forgot Fragment");
            }
        });



        mFacebook_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startFacebookLoginUi();
            }
        });
        mGoogle_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        mPasswordView.setTransformationMethod(new PasswordTransformationMethod());


        mregisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signin.this, register.class);
                startActivity(intent);
            }
        });

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser = mEmailView.getText().toString();
                mPassword = mPasswordView.getText().toString();
                isClickbale = true;
                setupObservables1();
                //usePost(mUser, mPassword);

            }

        });



        mSkip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(signin.this, HomeActivity.class);
                startActivity(intent1);

               // register.userName = "Guest";

                preferences.setUser_name("Guest");

            }
        });

    }


//    public void getRegister(final String name, String mPassword, final String email, final String address, final String moblie_no, final String gender) {
//        //if (Utils.isConnectingToInternet(register.this)) {
//        Log.e("NAME==" ,"" + name + "" + mPassword+ "" + email+ "" + address+ "" + moblie_no+ "" + gender);
//        final ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
//        final ProgressDialog pDialog = ProgressDialog.show(signin.this, "", "Please wait ...", true);
//
//        Call<com.example.user.myapplication.model.Response> call = apiService.getRegister(name,mPassword,email,address,moblie_no,gender);
//        call.enqueue(new Callback<com.example.user.myapplication.model.Response>() {
//            @Override
//            public void onResponse(Call<com.example.user.myapplication.model.Response> call, Response<com.example.user.myapplication.model.Response> response) {
//                pDialog.dismiss();
//                String sds = response.toString();
//                if (response.code() == 200) {
//                    com.example.user.myapplication.model.Response data = response.body();
//                    Log.e("JSJJJ==" , "" + data.getUserid());
//                    preferences.setUserId("" + data.getUserid());
//                    preferences.setUser_name(name);
//                    preferences.setUser_email(email);
//                    preferences.setUser_address(address);
//                    preferences.setGender(gender);
//                    preferences.setUser_mob_num(moblie_no);
//
//                     Intent intent1 = new Intent(signin.this, HomeActivity.class);
//                     startActivity(intent1);
//                     finish();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<com.example.user.myapplication.model.Response> call, Throwable t) {
//                // Log error here since request failed
//                if (pDialog != null && pDialog.isShowing())
//                    pDialog.hide();
//                Utils.AlertBox(signin.this, "Opps!!!", t.toString());
//
//            }
//        });
//       /* } else {
//            Utils.AlertBox(register.this, "", "Please check internet connection");
//        }*/
//    }


    @Override
    public void onResume(){
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }


    private void startFacebookLoginUi(){
//        loginButton.setReadPermissions("email");
        // Callback registration
        callbackManager = CallbackManager.Factory.create();
        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(signin.this, Arrays.asList("email","user_photos","public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            getFacebookProfiledata(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                exception.printStackTrace();
                Log.e("EXCE","" + exception.toString());
            }
        });
    }



    public void getiTemData(String mUser, String mPassword) {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
            final ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            final ProgressDialog pDialog = ProgressDialog.show(signin.this, "", "Please wait ...", true);

            Call<LoginData> call = apiService.getLogin(mUser, mPassword);
            call.enqueue(new Callback<LoginData>() {
                @Override
                public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                    pDialog.dismiss();
                    if (response.code() == 200) {
                        LoginData data = response.body();

                        if (data.getMessage().toString().trim().equals("Login Failure")){
                            Toast.makeText(signin.this,"Login failure",Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e("USER ID","" + data.getUserid());
                            preferences.setUserId(data.getUserid());
                            preferences.setUser_name(data.getName());
                            preferences.setUser_email(data.getEmail());
                            preferences.setUser_address(data.getAddress());
                            preferences.setGender(data.getGender());
                            preferences.setUser_mob_num(data.getMobileno());

//                        FragmentManager fm = getFragmentManager();
//                        LoginOtpDialog dialogFragment = new LoginOtpDialog(data.getUserid());
//                        dialogFragment.show(fm, "OTP Fragment");
                            Intent intent1 = new Intent(signin.this, HomeActivity.class);
                            startActivity(intent1);
                            finish();
                        }


                    }
                }

                @Override
                public void onFailure(Call<LoginData> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("", t.toString());
                    if (pDialog != null && pDialog.isShowing())
                        pDialog.hide();
                    Utils.AlertBox(signin.this, "Opps!!!", t.toString());
                }
            });
        /*} else {
            Utils.AlertBox(signin.this, "", "Please check internet connection");
        }*/
    }

    private void getFacebookProfiledata(AccessToken accessToken){

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject account,GraphResponse response) {
                        // Application code
//                        pDialog.dismiss();
                        Log.e("NLSD==", "" + response.toString());
                        JSONObject object = account;
                        String asd = response.toString();
                        try {
                            Log.e("EMIL", " " + account.getString("email"));
//                            preferences.setUserId(account.getString("id"));
//                            preferences.setUser_name(account.getString("name"));
//                            preferences.setUser_email(account.getString("email"));
//                            preferences.setUser_address("Not Found");
//                            preferences.setGender(account.getString("gender"));
//                            preferences.setUser_mob_num("Not Found");

//                            getRegister(account.getString("name"),"12",account.getString("email"),"jaipur","7737508585",account.getString("gender"));

//                            Intent intent1 = new Intent(signin.this, HomeActivity.class);
//                            startActivity(intent1);
//                            finish();

                        } catch (JSONException e) {
                            System.out.print(e.getMessage().toString());
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,link,name,birthday,about,gender,website,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager !=null)
            callbackManager.onActivityResult(requestCode, resultCode, data);

//         Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                Log.e(TAG, "firebaseAuthWithGoogle:" + account.getDisplayName());
//                preferences.setUserId(account.getId());
//                preferences.setUser_name(account.getDisplayName());
//                preferences.setUser_email(account.getEmail());
//                preferences.setUser_address("Not Found");
//                preferences.setGender("Not Found");
//                preferences.setUser_mob_num("Not Found");
//                final ProgressDialog pDialog = ProgressDialog.show(signin.this, "", "Please wait ...", true);
//                getRegister(account.getDisplayName(),"12",account.getEmail(),"jaipur","5687996625","Male");
//                Intent intent1 = new Intent(signin.this, HomeActivity.class);
//                startActivity(intent1);
//                finish();
            }
            else {
                // Google Sign In failed, update UI appropriately
                // ...
                Log.e(TAG, "ERROR:" + "error");
            }
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String mobile = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!validatePhone(mobile.toString()).isValid()){
            mEmailView.setError(validatePhone(mobile.toString()).getReason());
            focusView = mEmailView;
            cancel = true;
        }else if(!validatePassword(password.toString()).isValid()){
            mPasswordView.setError(validatePassword(password.toString()).getReason());
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            getiTemData(mobile, password);
        }


//
    }

    private ValidationResult validatePhone(@NonNull String phone) {
        if (phone.isEmpty()) {
            return ValidationResult.failure("phone number can't be blank", phone);
        }

        boolean isValid = ValidationUtils.isValidMobileNumber(phone);
        if (isValid) {
            return ValidationResult.success(phone);
        }

        return ValidationResult.failure("Phone Number is Invalid", phone);
    }

    private ValidationResult<String> validatePassword(@NonNull String password) {
        return ValidationUtils.isValidPassword(password);
    }


}





