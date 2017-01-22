package com.shareads.user.myapplication.dialogUtil;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.activity.EditActivity;

/**
 * Created by ericbasendra on 28/12/16.
 */

public class CongratulationDialog extends DialogFragment {

    Button   submitButton;
    String mUserID;

    public CongratulationDialog(){

    }

    public CongratulationDialog(String mUserID) {
        this.mUserID = mUserID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dilaog_congratulation, container);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view){
        submitButton = (Button)view.findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), EditActivity.class);
                startActivity(intent1);
                getActivity().finish();
            }
        });
    }



}
