package com.shareads.user.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.shareads.user.myapplication.R;

/**
 * Created by user on 12/8/2016.
 */

public class ScreenReview extends AppCompatActivity {

    EditText title;
    EditText description;
    TextView submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screenreview_popup);

         title=(EditText)findViewById(R.id.txtTitle);
        description=(EditText)findViewById(R.id.txtDescription);
        submit=(TextView)findViewById(R.id.btn_close_popup);

    }


}
