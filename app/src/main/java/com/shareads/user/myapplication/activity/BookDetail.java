package com.shareads.user.myapplication.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shareads.user.myapplication.AppController;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.adapter.ReviewsAdapter;
import com.shareads.user.myapplication.fragment.home;
import com.shareads.user.myapplication.fragment.wishlist;
import com.shareads.user.myapplication.model.BookReviewModal;
import com.shareads.user.myapplication.model.BookReviewResult;
import com.shareads.user.myapplication.model.BookdetailModel;
import com.shareads.user.myapplication.model.PostReviewModal;
import com.shareads.user.myapplication.model.cartmodel;
import com.shareads.user.myapplication.model.wishModl;
import com.shareads.user.myapplication.preferences.BookPreferences;
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
    ImageView send_btn;
    Button bcart;
    private RecyclerView recyclerView;
    private ArrayAdapter<String> adapter;
    ArrayList<String> mReviews;;
    RatingBar ratingBar,book_rating;
    BookPreferences preferences;
    String bookid,action;
    ImageView book_Image;
    EditText post_review_edit;
    TextView book_name,book_points,book_description;
    public LinearLayout linear_cart;
    public boolean isVisible = false;
    public ReviewsAdapter reviewsAdapter;
    static ArrayList<BookReviewResult> ITEM_REVIEW_LIST;
    TextView book_author;
    ImageView book1,book2,book3,book4;
    public TextView discount_text;
    public RelativeLayout relative_Discount;
    Button order_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
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
        ITEM_REVIEW_LIST = new ArrayList<>();
        preferences = AppController.getInstance().getBookPrefs();
        reviewsAdapter = new ReviewsAdapter(BookDetail.this,ITEM_REVIEW_LIST);
        recyclerView = (RecyclerView) findViewById(R.id.userReviewlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookDetail.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(reviewsAdapter);
        discount_text = (TextView)findViewById(R.id.discount) ;
        relative_Discount = (RelativeLayout)findViewById(R.id.relative_discount);
        order_btn = (Button)findViewById(R.id.buynow);
        book1=(ImageView)findViewById(R.id.book_image1);
        book2=(ImageView)findViewById(R.id.book_image2);
        book3=(ImageView)findViewById(R.id.book_image3);
        book4=(ImageView)findViewById(R.id.book_image4);
        book_author = (TextView)findViewById(R.id.author) ;
        Log.e("PREFRNC===", "" + preferences.getUserId());
        bwishlist = (Button) findViewById(R.id.bwishlist);
        bcart = (Button) findViewById(R.id.borderproduct);
        ratingBar=(RatingBar)findViewById(R.id.ratingReviewBar);
        readmore =(TextView)findViewById(R.id.readmore);
        book_Image = (ImageView)findViewById(R.id.imageView5);
        book_name = (TextView)findViewById(R.id.textView2) ;
        book_points = (TextView)findViewById(R.id.point);
        book_rating = (RatingBar) findViewById(R.id.ratingBar2);
        book_description = (TextView)findViewById(R.id.description) ;
        linear_cart = (LinearLayout)findViewById(R.id.linear_cart);
        send_btn = (ImageView)findViewById(R.id.send_btn);
        post_review_edit = (EditText)findViewById(R.id.post_review_edit);
        //ListView listView1=(ListView)findViewById(R.id.listDescription) ;
        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        initiatePopupWindow();
                    }

        });

        order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BookDetail.this,Shipping_address.class);
                startActivity(i);
            }
        });
        onOrderProduct();
        readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent (BookDetail.this,Readmore.class);
                intent.putExtra(Readmore.BOOK_ID,""+ bookid);
                startActivity(intent);

            }
        });

        bcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getiTemData(preferences.getUserId(),bookid,action);

            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!post_review_edit.getText().toString().equals("")){
                    postMyReview();
                }
            }
        });

        if (getIntent()!=null){
            bookid = getIntent().getStringExtra(home.BOOK_UID);
            isVisible = getIntent().getBooleanExtra(home.ISVISIBLE,true);
            getBookDetail(bookid);
        }

        if (!isVisible){
            linear_cart.setVisibility(View.VISIBLE);
        }else {
            linear_cart.setVisibility(View.INVISIBLE);
        }

        getReviewList();
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
                pDialog.dismiss();
                if (response.code() == 200) {
                    BookdetailModel data = response.body();
                    // String arrayList = data.getResult();

                    if (data.getResults()!=null && data.getResults().size()>0)
                       setBookDetail(data.getResults().get(0));

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
                        .into(book1);
            if (result.getBackimage()!=null && !result.getBackimage().equals(""))
                Picasso.with(BookDetail.this)
                        .load(result.getBackimage())
                        .resize(100,100)
                        .centerInside()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.no_image_available)
                        .into(book2);
            if (result.getSpineimage()!=null && !result.getSpineimage().equals(""))
                Picasso.with(BookDetail.this)
                        .load(result.getSpineimage())
                        .resize(100,100)
                        .centerInside()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.no_image_available)
                        .into(book3);
            if (result.getContentimage()!=null && !result.getContentimage().equals(""))
                Picasso.with(BookDetail.this)
                        .load(result.getContentimage())
                        .resize(100,100)
                        .centerInside()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.no_image_available)
                        .into(book4);
            book_name.setText(result.getTitle());

            book_author.setText(result.getAuthor());
            book_rating.setRating(Float.parseFloat(result.getRating()));
            book_description.setText(result.getDescription());
            if(result.getDiscount().trim().equals("Donate")){
                book_points.setText("" + 0);
                relative_Discount.setVisibility(View.GONE);
            }else{
                relative_Discount.setVisibility(View.VISIBLE);
                int finaPrice = (int)Double.parseDouble(result.getDiscountprice()) + 50;
                book_points.setText("" + finaPrice);
                discount_text.setText("Rs " + result.getPrice());
            }
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

    public void getReviewList() {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(this, "", "Please wait ...", true);

        Call<BookReviewModal> call = apiService.getReview(bookid);
        call.enqueue(new Callback<BookReviewModal>() {
            @Override
            public void onResponse(Call<BookReviewModal> call, Response<BookReviewModal> response) {
                pDialog.dismiss();
                if (response.code() == 200) {

                    BookReviewModal data = response.body();
                    reviewsAdapter.clearItem();
                    for (BookReviewResult result: data.getResult()) {
                        reviewsAdapter.addItem(result);
                    }
                    reviewsAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<BookReviewModal> call, Throwable t) {
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

    public void postMyReview() {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(this, "", "Please wait ...", true);

        Call<PostReviewModal> call = apiService.postReview(AppController.getInstance().getBookPrefs().getUserId(),bookid,Float.toString(ratingBar.getRating()),post_review_edit.getText().toString());
        call.enqueue(new Callback<PostReviewModal>() {
            @Override
            public void onResponse(Call<PostReviewModal> call, Response<PostReviewModal> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    send_btn.setEnabled(false);
                    post_review_edit.setEnabled(false);
                    getReviewList();

                }
            }

            @Override
            public void onFailure(Call<PostReviewModal> call, Throwable t) {
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

