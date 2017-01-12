package com.shareads.user.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shareads.user.myapplication.AppController;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.ValidationResult;
import com.shareads.user.myapplication.ValidationUtils;
import com.shareads.user.myapplication.model.ChangePasswordModal;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;


public class changepassword extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match

    EditText old_password, new_password, reset_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_changepassword);
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

    private void init() {
        old_password = (EditText) findViewById(R.id.old_password);
        new_password = (EditText) findViewById(R.id.new_password);
        reset_password = (EditText) findViewById(R.id.reset_password);
        Button submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupObservables1();
            }
        });
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

        old_password.setError(null);
        new_password.setError(null);
        reset_password.setError(null);

        // Store values at the time of the login attempt.
        String _old_password = old_password.getText().toString();
        String _new_password = new_password.getText().toString();
        String _reset_password = reset_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!validatePassword(_old_password.toString()).isValid()) {
            old_password.setError(validatePassword(_old_password.toString()).getReason());
            focusView = old_password;
            cancel = true;
        } else if (!validatePassword(_new_password.toString()).isValid()) {
            new_password.setError(validatePassword(_new_password.toString()).getReason());
            focusView = new_password;
            cancel = true;
        } else if (!validatePassword(_new_password.toString(),_reset_password).isValid()) {
            reset_password.setError(validatePassword(_new_password.toString(),_reset_password).getReason());
            focusView = reset_password;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            changeMyPassword( _old_password, _new_password, _reset_password);
        }
    }

    private void changeMyPassword( String _old_password, String _new_password, String _reset_password){
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(changepassword.this, "", "Please wait ...", true);

        Call<ChangePasswordModal> call = apiService.changePassword(AppController.getInstance().getBookPrefs().getUserId(),
                _old_password,_new_password,_reset_password);
        call.enqueue(new Callback<ChangePasswordModal>() {
            @Override
            public void onResponse(Call<ChangePasswordModal> call, retrofit2.Response<ChangePasswordModal> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    ChangePasswordModal data = response.body();
                    if (data.getStatus().trim().equals("1")){
                        old_password.setText("");
                        new_password.setText("");
                        reset_password.setText("");
                        Toast.makeText(changepassword.this,"Password updated",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(changepassword.this,data.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordModal> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(changepassword.this, "Opps!!!", t.toString());

            }
        });
    }

    private ValidationResult<String> validatePassword(@NonNull String password) {
        return ValidationUtils.isValidPassword(password);
    }

    private ValidationResult<String> validatePassword(@NonNull String password,@NonNull String newPassword) {
        return ValidationUtils.isValidCondirmPassword(password,newPassword);
    }
}
