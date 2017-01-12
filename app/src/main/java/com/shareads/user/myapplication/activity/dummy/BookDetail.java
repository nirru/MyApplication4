package com.shareads.user.myapplication.activity.dummy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shareads.user.myapplication.AppController;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.activity.Readmore;
import com.shareads.user.myapplication.activity.cartActivity;
import com.shareads.user.myapplication.fragment.home;
import com.shareads.user.myapplication.fragment.wishlist;
import com.shareads.user.myapplication.model.BookdetailModel;
import com.shareads.user.myapplication.model.cartmodel;
import com.shareads.user.myapplication.model.wishModl;
import com.shareads.user.myapplication.preferences.BookPreferences;
import com.shareads.user.myapplication.adapter.reviewadapter;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookDetail extends AppCompatActivity  {

    ImageView bookimage;
    TextView bookname , readmore;
    Button bwishlist;
    Button bcart;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    ArrayList<String> mReviews;;
    RatingBar ratingBar,book_rating;
    BookPreferences preferences;
    String bookid,action;
    ImageView book_Image;
    ImageView book1,book2,book3,book4;
    TextView book_name,book_points,book_description;
    TextView book_author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //book image reference
        book1=(ImageView)findViewById(R.id.book_image1);
        book2=(ImageView)findViewById(R.id.book_image2);
        book3=(ImageView)findViewById(R.id.book_image3);
        book4=(ImageView)findViewById(R.id.book_image4);
        ///
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

        preferences = AppController.getInstance().getBookPrefs();
        Log.e("PREFRNC===", "" + preferences.getUserId());
        book_author = (TextView)findViewById(R.id.author) ;
        bwishlist = (Button) findViewById(R.id.bwishlist);
        bcart = (Button) findViewById(R.id.borderproduct);
        ratingBar=(RatingBar)findViewById(R.id.ratingReviewBar);
        readmore =(TextView)findViewById(R.id.readmore);
      //  listView = (ListView)findViewById(R.id.userReviewlist);
        book_Image = (ImageView)findViewById(R.id.imageView5);
        book_name = (TextView)findViewById(R.id.textView2) ;
        book_points = (TextView)findViewById(R.id.point);
        book_rating = (RatingBar) findViewById(R.id.ratingBar2);
        book_description = (TextView)findViewById(R.id.description) ;
        //ListView listView1=(ListView)findViewById(R.id.listDescription) ;
        setData();
        adapter = new reviewadapter(this, R.layout.activity_main4, mReviews);
//        listView.setAdapter(adapter);
//        ratingBar.setClickable(true);
//        ratingBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                        initiatePopupWindow();
//                    }
//
//        });
//

        onOrderProduct();
        readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent (BookDetail.this,Readmore.class);
                startActivity(intent);

            }
        });

        bcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getiTemData(preferences.getUserId(),bookid,action);

            }
        });

        if (getIntent()!=null){
            bookid = getIntent().getStringExtra(home.BOOK_UID);
            getBookDetail(bookid);
        }
    }

    public void getBookDetail(String mbookid) {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(this, "", "Please wait ...", true);

        Call<BookdetailModel> call = apiService.bookdetail(mbookid);
        call.enqueue(new Callback<BookdetailModel>() {
            @Override
            public void onResponse(Call<BookdetailModel> call, Response<BookdetailModel> response) {
                if (response.code() == 200) {

                    BookdetailModel data = response.body();

                    // String arrayList = data.getResult();

                    setBookDetail(data.getResults().get(0));

                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<BookdetailModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(BookDetail.this, "Opps!!!", t.toString());

            }
        });
        /*} else {
            Utils.AlertBox(signin.this, "", "Please check internet connection");
        }*/
    }

    private void setBookDetail(BookdetailModel.Result result) {

        try {
            if (result.getFrontimage()!=null && !result.getFrontimage().equals(""))
                Picasso.with(BookDetail.this)
                        .load(result.getFrontimage())
                        .resize(100,100)
                        .centerInside()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.no_image_available)
                        .into(book_Image);
            book_name.setText(result.getTitle());
            book_points.setText(result.getPrice());
            book_rating.setRating(Float.parseFloat(result.getRating()));
            book_description.setText(result.getDescription());
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    public void getiTemData(String mUser ,String mbookid, String maction) {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(this, "", "Please wait ...", true);

        Call<cartmodel> call = apiService.myaddtocart(mUser,mbookid,"add");
        call.enqueue(new Callback<cartmodel>() {
            @Override
            public void onResponse(Call<cartmodel> call, Response<cartmodel> response) {
                if (response.code() == 200) {

                    cartmodel data = response.body();

                   // String arrayList = data.getResult();

                    Toast.makeText(BookDetail.this,data.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(BookDetail.this,cartActivity.class);
                    startActivity(intent);

                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<cartmodel> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(BookDetail.this, "Opps!!!", t.toString());

            }
        });
        /*} else {
            Utils.AlertBox(signin.this, "", "Please check internet connection");
        }*/
    }


    // TODO: Rename method, update argument and hook method into UI event

    private void onOrderProduct() {
        bwishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getData(preferences.getUserId(),bookid,action);

            }
        });
    }
    public void getData(String mUser ,String mbookid, String maction) {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(this, "", "Please wait ...", true);

        Call<wishModl> call = apiService.myaddtowish(mUser,mbookid,"1");
        call.enqueue(new Callback<wishModl>() {
            @Override
            public void onResponse(Call<wishModl> call, Response<wishModl> response) {
                if (response.code() == 200) {

                    wishModl data = response.body();

                    // String arrayList = data.getResult();

                    Toast.makeText(BookDetail.this,data.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent (BookDetail.this,wishlist.class);
                    startActivity(intent);

                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<wishModl> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(BookDetail.this, "Opps!!!", t.toString());

            }
        });
        /*} else {
            Utils.AlertBox(signin.this, "", "Please check internet connection");
        }*/
    }

    private void setData() {
        mReviews = new ArrayList<String>();
        mReviews.add("This book is good");
        mReviews.add("Best Book");
    }
        //On ordering of product

    private PopupWindow pwindo;
    EditText title;
    EditText description;
    TextView submit;

    private void initiatePopupWindow() {
                 final Dialog dialog =new Dialog(BookDetail.this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                      dialog.setContentView(R.layout.screenreview_popup);
        /*
            LayoutInflater inflater = (LayoutInflater) BookDetail.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screenreview_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 300, 370, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
,
           Button submit = (Button) layout.findViewById(R.id.submit);
            submit.setOnClickListener(cancel_button_click_listener);*/
        title=(EditText)dialog.findViewById(R.id.txtTitle);
        description=(EditText)dialog.findViewById(R.id.txtDescription);
        submit=(TextView)dialog.findViewById(R.id.btn_close_popup);
        dialog.show();

    }



    }

