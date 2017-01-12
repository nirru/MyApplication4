package com.shareads.user.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.SellingAdapter3;
import com.shareads.user.myapplication.book;

import java.util.ArrayList;

public class HomeActivityItemList extends AppCompatActivity {
    private ArrayList<book> ItemList ;
    private RecyclerView recyclerView;
    private SellingAdapter3 mAdapter;

    private final String book_name[]=
            {
                    "Book1",
                    "Book2",
                    "Book3",
                    "Book4",
                    "Book5",
                    "Book6"
            };
    private final Integer book_image[]=
            {
                    R.drawable.download,
                    R.drawable.book2,
                    R.drawable.book3,
                    R.drawable.book4,
                    R.drawable.book5,
                    R.drawable.book6
            };


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main3);
            recyclerView = (RecyclerView) findViewById(R.id.sellingView);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            ItemList=predata();
            mAdapter = new SellingAdapter3(getApplicationContext(),ItemList);
            recyclerView.setAdapter(mAdapter);
        }

        private ArrayList<book> predata() {
            ArrayList<book>Item_list=new ArrayList<>();
            for(int i=0;i<book_name.length;i++)
            {
                book book1=new book();
                book1.setbook_name(book_name[i]);
                book1.setBook_image(book_image[i]);
                Item_list.add(book1);
            }
            return Item_list;
        }
    }
