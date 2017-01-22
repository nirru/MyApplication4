package com.shareads.user.myapplication.fragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.shareads.user.myapplication.AppController;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.adapter.profileadapter;
import com.shareads.user.myapplication.dialogUtil.AddMorePointsDialog;
import com.shareads.user.myapplication.dialogUtil.TopUpDialog;
import com.shareads.user.myapplication.preferences.BookPreferences;

import java.util.ArrayList;



public class myAccount extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> mProfile;
    private ListView listView;

    profileadapter adapter;
    TextView password;


    BookPreferences preferences;
    TextView topUpNow, addmore,mail;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccount_info);
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
        setData();
    }


 private void init(){
     topUpNow = (TextView)findViewById(R.id.topup);
     addmore = (TextView)findViewById(R.id.addmore) ;
     topUpNow.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             FragmentManager fm = getFragmentManager();
             TopUpDialog dialogFragment = new TopUpDialog();
             dialogFragment.show(fm, "TopUp Fragment");
         }
     });

     addmore.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             FragmentManager fm = getFragmentManager();
             AddMorePointsDialog dialogFragment = new AddMorePointsDialog();
             dialogFragment.show(fm, "AddMore Fragment");
         }
     });

     mail = (TextView)findViewById(R.id.mail);
     mail.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                     "mailto","accounts@shareads.co.in", null));
             emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
             emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
             startActivity(Intent.createChooser(emailIntent, "Send email..."));
         }
     });
 }

    private void setData() {
        mProfile = new ArrayList<String>();
        mProfile.add(preferences.getUser_name());
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
