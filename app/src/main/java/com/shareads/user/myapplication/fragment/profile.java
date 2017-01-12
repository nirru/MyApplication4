package com.shareads.user.myapplication.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.activity.EditActivity;
import com.shareads.user.myapplication.preferences.BookPreferences;
import com.shareads.user.myapplication.adapter.profileadapter;

import java.util.ArrayList;



public class profile extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> mProfile;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    TextView password,tv_moblie_num,tv_email,tv_address;
    BookPreferences preferences;
    ImageButton edit;
    TextView contactus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        preferences = new BookPreferences(profile.this);
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

    private void init(){
        listView = (ListView)findViewById(R.id.profile);
        edit=(ImageButton)findViewById(R.id.editdetail);
        contactus = (TextView)findViewById(R.id.contactus);
        tv_email = (TextView)findViewById(R.id.pemail);
        tv_address = (TextView)findViewById(R.id.paddress);
        tv_moblie_num = (TextView)findViewById(R.id.pmobile);
        tv_email = (TextView)findViewById(R.id.pemail);

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","ccare@shareads.co.in", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        RelativeLayout edit_layout = (RelativeLayout)findViewById(R.id.edit_layout);
        edit_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(profile.this, EditActivity.class);
                startActivity(intent);
            }
        });

        setData();
        adapter = new profileadapter(profile.this, R.layout.fragment_profile, mProfile);
        listView.setAdapter(adapter);

        tv_email.setText(preferences.getUser_email());
        tv_moblie_num.setText(preferences.getUser_mob_num());
        tv_address.setText(preferences.getUser_address());


        password=(TextView)findViewById(R.id.pchangepassword);
        password.setClickable(true);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profile.this,changepassword.class);
                startActivity(i);
            }


        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(profile.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setData() {
        mProfile = new ArrayList<String>();
        mProfile.add(preferences.getUser_name());
    }




}
