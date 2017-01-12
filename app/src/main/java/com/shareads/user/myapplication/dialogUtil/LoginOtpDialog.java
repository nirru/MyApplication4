package com.shareads.user.myapplication.dialogUtil;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shareads.user.myapplication.HomeActivity;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.model.OTPModal;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by ericbasendra on 28/12/16.
 */

public class LoginOtpDialog extends DialogFragment {

    EditText mOtpVarifaction;
    Button   submitButton;
    String mUserID;

    public LoginOtpDialog(String userID){
       this.mUserID = userID;
    }
    public LoginOtpDialog(Integer userID){
        this.mUserID = "" + userID;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otp_dialog, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View view){
        mOtpVarifaction = (EditText)view.findViewById(R.id.text_otp);
        submitButton = (Button)view.findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRegister();
            }
        });
    }

    public void getRegister() {
        //if (Utils.isConnectingToInternet(register.this)) {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);

        Call<OTPModal> call = apiService.varifyOTP("verify",mUserID,mOtpVarifaction.getText().toString());
        call.enqueue(new Callback<OTPModal>() {
            @Override
            public void onResponse(Call<OTPModal> call, retrofit2.Response<OTPModal> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    OTPModal data = response.body();
                    if (data.getResult().toString().equals("1")){
                        dismiss();
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }else{
                        mOtpVarifaction.setError("Wrong OTP");
                        Toast.makeText(getActivity(), "Wrong OTP", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OTPModal> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(getActivity(), "Opps!!!", t.toString());

            }
        });
       /* } else {
            Utils.AlertBox(register.this, "", "Please check internet connection");
        }*/
    }


}
