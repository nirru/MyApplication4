package com.shareads.user.myapplication.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.model.BookdetailModel;
import com.shareads.user.myapplication.preferences.BookPreferences;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 12/8/2016.
 */

public class Readmore extends AppCompatActivity {

    String item;
    String category="";
    Spinner sp;
    String mediaPath="";
    ImageView bookImage1,bookImage2,bookImage3,bookImage4;
    Button buttonUpload;
    ArrayAdapter<String> aa;
    ArrayList<String> list;
    CharSequence msg;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ImageView ivImage;
    private Button choosefrontimage;
    private String userChoosenTask;
    String edition="";
    String Binding="";
    String imagePath;
    Bitmap bp;
    RadioGroup language,bind,discount,status;
    RadioButton ubooklanguagehindi,ubooklanguageenglish,ubooklanguagetamil,ubooklanguagegujarati,hard,pager,spiral;
    RadioButton book75,book50,donate,now,later;
    TextView ubook_language;
    TextView ubookname, ubookdescription, ubookpublication, ubookcategory,  ubookprice, ubookdiscount,
            ubookstatus, ubookpublishing, ubookrating, ubookuploaddate, upublisingyear, ubookisbnno, ubookauthor, ubookpages,ubookedition;
    TextView ubookbinding;
    BookPreferences preferences;
    public static final String BOOK_ID = "book_id";
    String bookid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_read_book);
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
        if (getIntent()!=null)
         bookid = getIntent().getStringExtra(BOOK_ID);
        init();

        getBookDetail(bookid);
    }

    private void init(){
        bookImage1 = (ImageView) findViewById(R.id.ubookfrontimage);
        bookImage2 = (ImageView) findViewById(R.id.ubookspineimage);
        bookImage3 = (ImageView) findViewById(R.id.ubooktableofcontent);
        bookImage4 = (ImageView) findViewById(R.id.ubookbackimage);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        choosefrontimage=(Button) findViewById(R.id.ubuttonchoose);
        ubookname = (TextView) findViewById(R.id.ubookname);
        ubookdescription = (TextView) findViewById(R.id.ubookdescription);
        ubookcategory = (TextView)findViewById(R.id.ubookcategory);
        ubookpublication = (TextView) findViewById(R.id.ubookpublication);
        ubook_language = (TextView)findViewById(R.id.book_language);
        ubookedition = (TextView)findViewById(R.id.ubookedition);
        // ubooklanguage = (EditText) view.findViewById(R.id.ubooklanguage);
        ubookisbnno = (TextView) findViewById(R.id.ubookIsbno_);
        ubookprice = (TextView) findViewById(R.id.ubookprice);
        //  ubookdiscount = (EditText) view.findViewById(R.id.ubookdiscount);
        //ubookstatus = (EditText) view.findViewById(R.id.ubookstatus);
        upublisingyear = (TextView) findViewById(R.id.ubookpublishingyear);
        //ubookrating = (EditText) view.findViewById(R.id.ubookrating);
        ubookuploaddate = (TextView)findViewById(R.id.ubookuploaddate);
        ubookauthor = (TextView) findViewById(R.id.ubookauthor);
        ubookpages = (TextView) findViewById(R.id.ubookpages);
        ubookbinding = (TextView)findViewById(R.id.book_binding);
        ubookisbnno.setHint("Enter 10/13 digit ISBN no (without -)");
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
                Utils.AlertBox(Readmore.this, "Opps!!!", t.toString());

            }
        });
        /*} else {
            Utils.AlertBox(signin.this, "", "Please check internet connection");
        }*/
    }

    private void setBookDetail(BookdetailModel.Result result) {

        try {
            if (result.getFrontimage()!=null && !result.getFrontimage().equals(""))
                Picasso.with(Readmore.this)
                        .load(result.getFrontimage())
                        .resize(100,100)
                        .centerInside()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.no_image_available)
                        .into(bookImage1);
            if (result.getBackimage()!=null && !result.getBackimage().equals(""))
                Picasso.with(Readmore.this)
                        .load(result.getBackimage())
                        .resize(100,100)
                        .centerInside()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.no_image_available)
                        .into(bookImage2);
            if (result.getSpineimage()!=null && !result.getSpineimage().equals(""))
                Picasso.with(Readmore.this)
                        .load(result.getSpineimage())
                        .resize(100,100)
                        .centerInside()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.no_image_available)
                        .into(bookImage3);
            if (result.getContentimage()!=null && !result.getContentimage().equals(""))
                Picasso.with(Readmore.this)
                        .load(result.getContentimage())
                        .resize(100,100)
                        .centerInside()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.no_image_available)
                        .into(bookImage4);
            ubookname.setText("Book Name: -" + result.getTitle());
            ubookdescription.setText("Description:-" + result.getDescription());
            ubookcategory.setText("Category: -" + result.getCategoryname());
            ubookpublication.setText("Publication:-" + result.getPublication());
            ubookisbnno.setText("ISBN No:-" + result.getIsbnnumber());
            ubookauthor.setText("Author: -" + result.getAuthor());
            ubookedition.setText("Edition: -" + result.getEdition());
            ubook_language.setText("Language : -" + result.getLanguage());
            ubookpages.setText("No of pages: -" + result.getNoofpages());
            upublisingyear.setText("Publishing Year: -" + result.getPublishingyear());
            ubookprice.setText("Price:-" + result.getPrice());
            ubookbinding.setText("Binding: -" + result.getBinding());


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}