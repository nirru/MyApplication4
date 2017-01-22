package com.shareads.user.myapplication.dialogUtil;

import android.app.DialogFragment;
import android.app.ProgressDialog;
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

import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.model.ForgotPasswordModal;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by ericbasendra on 28/12/16.
 */

public class ForgetPasswordDialog extends DialogFragment {

    EditText mOtpVarifaction;
    Button   submitButton;

    public ForgetPasswordDialog(){
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forget_dialog, container);
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
                if (mOtpVarifaction.getText().toString().equals("")){
                    mOtpVarifaction.setError("Mobile number can't be blank");
                    return;
                }else{
                    getRegister();
                }

            }
        });
    }

    public void getRegister() {
        //if (Utils.isConnectingToInternet(register.this)) {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);

        Call<ForgotPasswordModal> call = apiService.getPassword(mOtpVarifaction.getText().toString());
        call.enqueue(new Callback<ForgotPasswordModal>() {
            @Override
            public void onResponse(Call<ForgotPasswordModal> call, retrofit2.Response<ForgotPasswordModal> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    ForgotPasswordModal data = response.body();
                    if (data.getStatus().toString().equals("1")){
                        dismiss();
                        Toast.makeText(getActivity(), "Password is sent to your registered mobile number", Toast.LENGTH_SHORT).show();
                    }else{
                        mOtpVarifaction.setError("Mobile No is wrong");
                        Toast.makeText(getActivity(), "Mobile No is wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordModal> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(getActivity(), "Opps!!!", t.toString());

            }
        });
    }


}
