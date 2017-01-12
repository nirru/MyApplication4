package com.shareads.user.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.book;
import com.shareads.user.myapplication.adapter.orderAdapter;

import java.util.ArrayList;


public class orderFragment extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<book> ItemList;
    private RecyclerView recyclerView;
    private orderAdapter mAdapter;


    private final String book_name[] =
            {       "Book1",
                    "Harry Potter",
                    "Duck Rabit",
            };
    private final Integer book_image[] =
            {
                    R.drawable.download,
                    R.drawable.book2,
                    R.drawable.book3,
            };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order);
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
        recyclerView = (RecyclerView) findViewById(R.id.orderView);
        recyclerView.setLayoutManager(new LinearLayoutManager(orderFragment.this, LinearLayoutManager.VERTICAL, false));
        ItemList = predata();
        mAdapter = new orderAdapter(orderFragment.this, ItemList);
        recyclerView.setAdapter(mAdapter);
    }


    private ArrayList<book> predata() {
        ArrayList<book>Item_list=new ArrayList<>();
        for(int i=0;i<book_name.length;i++)
        {
            book book1=new book();
            book1.setbook_name(book_name[i]);
            book1.setBook_image(book_image[i]);
           // book1.setbook_sale(book_sale[i]);
            //book1.setBook_points(book_points[i]);
            Item_list.add(book1);
        }
        return Item_list;
    }

    // TODO: Rename method, update argument and hook method into UI event


}
