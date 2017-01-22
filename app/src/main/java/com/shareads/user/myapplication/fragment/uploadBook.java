package com.shareads.user.myapplication.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.shareads.user.myapplication.AppConstant;
import com.shareads.user.myapplication.AppController;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.ValidationResult;
import com.shareads.user.myapplication.ValidationUtils;
import com.shareads.user.myapplication.model.BooKUploadModal;
import com.shareads.user.myapplication.model.categoryListModel;
import com.shareads.user.myapplication.preferences.BookPreferences;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.PermissionUtils;
import com.shareads.user.myapplication.util.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;


public class uploadBook extends AppCompatActivity {

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


    EditText ubookname, ubookdescription, ubookpublication, ubooklanguage,  ubookprice, ubookdiscount,
            ubookstatus, ubookpublishing, ubookrating, ubookuploaddate, upublisingyear, ubookisbnno, ubookauthor, ubookpages,ubookedition;
    CheckBox terms_conditions,ubookbinding;
    BookPreferences preferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String TAG_CAMERA = "Camera";
    public static final String TAG_CHOOSE_FROM_LIBRARY = "Gallery";
    public static final String TAG_CANCEL = "Cancel";
    public static final String TAG_ADD_PHOTO = "Choose Photo From";

    static  final List<String> CATEGORY_LIST=new ArrayList<String>();

    static int i = 0;
    File file;
    Uri logo_uri = null;
    Uri pic_uri =  null;
    Uri pic_uri_2 =  null;
    Uri pic_uri_3 =  null;
    Uri pic_uri_4 =  null;

    String logo_url = "";
    String picture_url ;
    String picture_url_2 ;
    String picture_url_3 ;
    String picture_url_4 ;
    categoryListModel data;

    String _language = "Hindi",_bind = "Hard Back",_discount = "75%",_status = "Available Now";
    String categoryID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upload_book);
        preferences = new BookPreferences(uploadBook.this);
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
        bookImage1 = (ImageView) findViewById(R.id.ubookfrontimage);
        bookImage2 = (ImageView) findViewById(R.id.ubookspineimage);
        bookImage3 = (ImageView) findViewById(R.id.ubooktableofcontent);
        bookImage4 = (ImageView) findViewById(R.id.ubookbackimage);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        choosefrontimage=(Button) findViewById(R.id.ubuttonchoose);
        sp = (Spinner) findViewById(R.id.ucategory);

        ubookname = (EditText) findViewById(R.id.ubookname);
        ubookdescription = (EditText) findViewById(R.id.ubookdescription);
        ubookpublication = (EditText) findViewById(R.id.ubookpublication);
        ubookedition = (EditText)findViewById(R.id.ubookedition);
        // ubooklanguage = (EditText) view.findViewById(R.id.ubooklanguage);
        ubookisbnno = (EditText) findViewById(R.id.ubookIsbno_);
        ubookprice = (EditText) findViewById(R.id.ubookprice1);
        //  ubookdiscount = (EditText) view.findViewById(R.id.ubookdiscount);
        //ubookstatus = (EditText) view.findViewById(R.id.ubookstatus);
        upublisingyear = (EditText) findViewById(R.id.ubookpublishingyear);
        //ubookrating = (EditText) view.findViewById(R.id.ubookrating);
        ubookuploaddate = (EditText)findViewById(R.id.ubookuploaddate);
        ubookauthor = (EditText) findViewById(R.id.ubookauthor);
        ubookpages = (EditText) findViewById(R.id.ubookpages);
        language=(RadioGroup) findViewById(R.id.ubooklanguage);
        ubooklanguageenglish=(RadioButton) findViewById(R.id.ubooklanguageenglish);
        ubooklanguagehindi=(RadioButton)findViewById(R.id.ubooklanguagehindi);
        ubooklanguagetamil=(RadioButton) findViewById(R.id.ubooklanguagetamil);
        ubooklanguagegujarati=(RadioButton) findViewById(R.id.ubooklanguagegujarati);
        bind=(RadioGroup) findViewById(R.id.ubookbinding);
        hard=(RadioButton)findViewById(R.id.ubookbindinghard);
        pager=(RadioButton) findViewById(R.id.ubookbindingpager);
        spiral=(RadioButton) findViewById(R.id.ubookbindingspireal);
        discount=(RadioGroup) findViewById(R.id.ubookdiscount);
        book50=(RadioButton) findViewById(R.id.ubookdiscount50);
        book75=(RadioButton) findViewById(R.id.ubookdiscount75);
        donate=(RadioButton) findViewById(R.id.ubookdiscountdonate);
        status=(RadioGroup) findViewById(R.id.ubookstatus);
        now=(RadioButton) findViewById(R.id.ubookstatusnow);
        later=(RadioButton) findViewById(R.id.ubookstatusshortly);

        ubookisbnno.setHint("Enter 10/13 digit ISBN no (without -)");


        sp.setPrompt("Choose your book category");
        sp.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0)
                   categoryID = data.getResult().get(position -1).getId();
                else{
                   categoryID = data.getResult().get(position).getId();
                }
                Log.e("ITETETE=","" + categoryID);

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb=(RadioButton)group.findViewById(checkedId);
                _language = rb.getText().toString();
            }
        });

        bind.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb=(RadioButton)group.findViewById(checkedId);
                _bind = rb.getText().toString();
            }
        });

        discount.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb=(RadioButton)group.findViewById(checkedId);
                _discount = rb.getText().toString();
            }
        });

        status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb=(RadioButton)group.findViewById(checkedId);
                _status = rb.getText().toString();
            }
        });

        getiTemData();
        choosefrontimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    setupObservables1();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        bookImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=1;
                Log.e("1===","" + "" + i);
                selectImage();
            }
        });

        bookImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=2;
                Log.e("2===","" + "" + i);
                selectImage();
            }
        });

        bookImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=3;
                Log.e("3===","" + "" + i);
                selectImage();
            }
        });

        bookImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=4;
                Log.e("4===","" + "" + i);
                selectImage();
            }
        });

    }


    public void getiTemData() {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(uploadBook.this, "", "Please wait ...", true);
        Call<categoryListModel> call = apiService.categoryList();
        call.enqueue(new Callback<categoryListModel>() {
            @Override
            public void onResponse(Call<categoryListModel> call, Response<categoryListModel> response) {
                pDialog.dismiss();
                if (response.code() == 200) {

                     data = response.body();


//                    CATEGORY_LIST.add("Choose Your Category");
                    /*ArrayList to Array Conversion */
                     CATEGORY_LIST.add("Choose Your Book Category");
                    for (categoryListModel.Result result:data.getResult()) {
                        CATEGORY_LIST.add(result.getName());
                    }

                    ArrayAdapter<String> adp1=new ArrayAdapter<String>(uploadBook.this,
                            R.layout.spiner_row,CATEGORY_LIST);
                    adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp.setAdapter(adp1);
                }

            }

            @Override
            public void onFailure(Call<categoryListModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                //Utils.AlertBox(Category.this, "Opps!!!", t.toString());

            }
        });
        /*} else {
            Utils.AlertBox(signin.this, "", "Please check internet connection");
        }*/
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == AppConstant.REQUEST_TAKE_PHOTO) {
                try {
                    if(data!=null){
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        file =  Utils.saveImageToExternalStorage(photo);
                        if (i==1){
                            bookImage1.setTag(2);
                            setPic(file.getAbsolutePath(),bookImage1);
                            logo_uri = Uri.fromFile(file);
                        }
                        else if(i==2){
                            bookImage2.setTag(2);
                            setPic(file.getAbsolutePath(),bookImage2);
                            pic_uri = Uri.fromFile(file);
                        }else if(i==3){
                            bookImage3.setTag(2);
                            setPic(file.getAbsolutePath(),bookImage3);
                            pic_uri_2 = Uri.fromFile(file);
                        }else if(i==4){
                            bookImage4.setTag(2);
                            setPic(file.getAbsolutePath(),bookImage4);
                            pic_uri_3 = Uri.fromFile(file);
                        }

                        galleryAddPic(file.getAbsolutePath());

                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (requestCode == AppConstant.PICK_IMAGE_REQUEST) {
                // SDK >= 11 && SDK < 19
                String realPath = "";
//                 if (Build.VERSION.SDK_INT < 19)
//                    realPath = Utils.getRealPathFromURI_API11to18(this, data.getData());
//                else
//                    realPath = Utils.getRealPathFromURI_API19(this, data.getData());

//                Log.e("REAL PATH", "" + realPath);
                if (i==1){
                    bookImage1.setTag(2);
                    logo_uri = data.getData();
//                    logo_camera_image.setImageURI(logo_uri);
                    setPic(logo_uri,bookImage1);
                }
                else if(i==2){
                    bookImage2.setTag(2);
                    pic_uri = data.getData();
//                    pic_camera_image.setImageURI(pic_uri);
                    setPic(pic_uri,bookImage2);
                }else if(i==3){
                    bookImage3.setTag(2);
                    pic_uri_2 = data.getData();
//                    pic_camera_image.setImageURI(pic_uri);
                    setPic(pic_uri_2,bookImage3);
                }
                else if(i==4){
                    bookImage4.setTag(2);
                    pic_uri_3 = data.getData();
//                    pic_camera_image.setImageURI(pic_uri);
                    setPic(pic_uri_3,bookImage4);
                }
            }
        }
    }


    public void captureImageFromCamera() throws IOException {
        if(!PermissionUtils.hasPermissions(uploadBook.this, AppConstant.PERMISSIONS)){
            ActivityCompat.requestPermissions(uploadBook.this,AppConstant.PERMISSIONS, AppConstant.PERMISSION_ALL);
        }else{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's aon camera activity to handle the intent
            if (takePictureIntent.resolveActivity(uploadBook.this.getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, AppConstant.REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void captureImageFromGallery() throws IOException {
        if(!PermissionUtils.hasPermissions(uploadBook.this, AppConstant.PERMISSIONS)){
            ActivityCompat.requestPermissions(uploadBook.this,AppConstant.PERMISSIONS, AppConstant.PERMISSION_ALL);
        }else{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), AppConstant.PICK_IMAGE_REQUEST);
        }
    }

    private void selectImage()  {
        final CharSequence[] items = {TAG_CAMERA, TAG_CHOOSE_FROM_LIBRARY,
                TAG_CANCEL};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(uploadBook.this);
        builder.setTitle(TAG_ADD_PHOTO);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                try {
                    if (items[item].equals(TAG_CAMERA)) {
                        captureImageFromCamera();
                    } else if (items[item].equals(TAG_CHOOSE_FROM_LIBRARY)) {
                        captureImageFromGallery();
                    } else if (items[item].equals(TAG_CANCEL)) {
                        dialog.dismiss();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        // display dialog
        dialog.show();
    }

    private void setPic(String mCurrentPhotoPath ,ImageView imageView) {
        // Get the dimensions of the View
        if (i==1){
            picture_url = mCurrentPhotoPath;
        }
        if (i == 2){
            picture_url_2 = mCurrentPhotoPath;
        }
        if (i == 3){
            picture_url_3 = mCurrentPhotoPath;
        }
        if (i == 4){
            picture_url_4 = mCurrentPhotoPath;
        }
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
//
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    private void setPic(Uri uri ,ImageView imageView) {
        // Get the dimensions of the View
        try {
            if (i==1){
                picture_url = Utils.getFilePath(uploadBook.this,uri);
            }
            if (i == 2){
                picture_url_2 = Utils.getFilePath(uploadBook.this,uri);;
            }
            if (i == 3){
                picture_url_3 = Utils.getFilePath(uploadBook.this,uri);;
            }
            if (i == 4){
                picture_url_4 = Utils.getFilePath(uploadBook.this,uri);;
            }
            int targetW = imageView.getWidth();
            int targetH = imageView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            InputStream inputStream = uploadBook.this.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,bmOptions);
            imageView.setImageBitmap(bitmap);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void galleryAddPic(String mCurrentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }



    private double percantageCalculator(){
        double value = 0;
        double discoutprice = 0;
        try {
            if (_discount.trim().equals("75%")){
                value = 75;
            }
            if (_discount.trim().equals("50%")){
                value = 50;
            }if (_discount.trim().equals("Donate")){
                value = 0;
            }
            double amount = Double.parseDouble(ubookprice.getText().toString());
            double res = (amount / 100.0f) * value;

            discoutprice = amount -res;
        }catch (Exception ex){

        }


        return discoutprice;
    }
    private void uploadMyBook() throws Exception{
        File imageFile1 = null;
        File imageFile2 = null;
        File imageFile3 = null;
        File imageFile4 = null;
        MultipartBody.Part imageBody1 = null,imageBody2 = null,imageBody3 = null,imageBody4 = null;
        RequestBody requestImageFile1 = null,requestImageFile2 = null,requestImageFile3 = null,requestImageFile4 = null;

        if (picture_url != null){
            imageFile1 = new File(picture_url);
            Log.e("GILLLE=" , "" + imageFile1.getName());
            Log.e("Bduu=" , "" + imageFile1.getAbsolutePath());
            requestImageFile1 =
                    RequestBody.create(MediaType.parse("image/*"), imageFile1);

            imageBody1 = MultipartBody.Part.createFormData("frontimage", imageFile1.getName(), requestImageFile1);
        }
        if(picture_url_2 != null){
            imageFile2 = new File(picture_url_2);
            requestImageFile2 =
                    RequestBody.create(MediaType.parse("image/*"), imageFile2);
            imageBody2 = MultipartBody.Part.createFormData("backimage", imageFile2.getName(), requestImageFile2);
        }
        if (picture_url_3 !=null){
            imageFile3 = new File(picture_url_3);
            requestImageFile3 =
                    RequestBody.create(MediaType.parse("image/*"), imageFile3);
            imageBody3 = MultipartBody.Part.createFormData("contentimage", imageFile3.getName(), requestImageFile3);
        }
        if(picture_url_4 != null){
            imageFile4 =  new File(picture_url_4);
            requestImageFile4 =
                    RequestBody.create(MediaType.parse("image/*"), imageFile4);
            imageBody4 = MultipartBody.Part.createFormData("spineimage", imageFile4.getName(), requestImageFile4);
        }

        String number_of_pages = "50";
        String reload_new = "true";
        String book_desc = "";
        String publication1 = "";
        String edition1 = "";
        String publishing_year1;
        String userid = AppController.getInstance().getBookPrefs().getUserId();
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Observable<BooKUploadModal> uploadBook = null;
        final ProgressDialog pDialog = ProgressDialog.show(uploadBook.this, "", "Please wait ...", true);

        if (!ubookdescription.getText().toString().equals("") && ubookdescription.getText().toString()!=null){
            book_desc = ubookdescription.getText().toString();
        }else{
            book_desc = "";
        }
        if (!ubookpublication.getText().toString().equals("") && ubookpublication.getText().toString()!=null){
            publication1 = ubookpublication.getText().toString();
        }else{
            publication1 = "";
        }
        if (!ubookedition.getText().toString().equals("") && ubookedition.getText().toString()!=null){
            edition1 = ubookedition.getText().toString();
        }else{
            edition1 = "";
        }

        String discountprice = percantageCalculator() + "";
        RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), userid);
        RequestBody categoryId = RequestBody.create(MediaType.parse("multipart/form-data"), categoryID);
        RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"), ubookname.getText().toString());
        RequestBody isbnnumber = RequestBody.create(MediaType.parse("multipart/form-data"), ubookisbnno.getText().toString());
        RequestBody author =  RequestBody.create(MediaType.parse("multipart/form-data"), ubookauthor.getText().toString());

        RequestBody publication =  RequestBody.create(MediaType.parse("multipart/form-data"), publication1);
        RequestBody no_pages =  RequestBody.create(MediaType.parse("multipart/form-data"), ubookpages.getText().toString());
        RequestBody edition =  RequestBody.create(MediaType.parse("multipart/form-data"), edition1);

        RequestBody language =  RequestBody.create(MediaType.parse("multipart/form-data"), _language);
        RequestBody binding =  RequestBody.create(MediaType.parse("multipart/form-data"), _bind);
        RequestBody price =  RequestBody.create(MediaType.parse("multipart/form-data"), "" + ubookprice.getText().toString());
        RequestBody discount =  RequestBody.create(MediaType.parse("multipart/form-data"), _discount);

        RequestBody bookstatus =  RequestBody.create(MediaType.parse("multipart/form-data"), _status);
        RequestBody description =  RequestBody.create(MediaType.parse("multipart/form-data"), book_desc);
        RequestBody reload_new1 =  RequestBody.create(MediaType.parse("multipart/form-data"), "reload_new");
        RequestBody status =  RequestBody.create(MediaType.parse("multipart/form-data"), "published");

        RequestBody publishingyear =  RequestBody.create(MediaType.parse("multipart/form-data"),  "" + Calendar.getInstance().get(Calendar.YEAR));
        RequestBody termsandconditions =  RequestBody.create(MediaType.parse("multipart/form-data"), "termsandconditions");
        RequestBody rating =  RequestBody.create(MediaType.parse("multipart/form-data"), "0");
        RequestBody dateposted =  RequestBody.create(MediaType.parse("multipart/form-data"), getCurrentDate());
        RequestBody discountPrice =  RequestBody.create(MediaType.parse("multipart/form-data"), discountprice);
        Call<BooKUploadModal> call = apiService.uploadBook(userId,
                categoryId,
                title,
                isbnnumber,
                author,
                publication,
                no_pages,
                edition,
                language,
                binding,
                price,
                discount,
                bookstatus,
                description,
                reload_new1,
                status,
                publishingyear,
                termsandconditions,
                rating,
                dateposted,
                discountPrice,
                imageBody1,
                imageBody2,
                imageBody3,
                imageBody4);
        call.enqueue(new Callback<BooKUploadModal>() {
            @Override
            public void onResponse(Call<BooKUploadModal> call, Response<BooKUploadModal> response) {
                if (response.code() == 200) {
                    clearForm();
                    BooKUploadModal data = response.body();
                    Toast.makeText(uploadBook.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<BooKUploadModal> call, Throwable t) {
                // Log error here since request failed
                Log.e("dfd", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(uploadBook.this, "Opps!!!", t.toString());

            }
        });

    }

    private String getCurrentDate() throws Exception{
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;

    }

    private void setupObservables1() throws Exception{

        // Debounce is coming in very handy here.
        // What I had understood before is that if I use debounce, it will emit event after the give
        // time period regardless of other events.
        // But now I am realizing that this is not the case.
        // Let's say debounce interval is 200 milliseconds. Once an event is emitted, RxJava clock starts
        // ticking. Once 200 ms is up, debounce operator will emit that event.
        // One more event comes to debounce and it will start the clock for 200 ms. If another event comes
        // in 100 ms, debounce operator will reset the clock and start to count 200 ms again.
        // So let's say if you continue emitting events at 199 ms intervals, this debounce operator
        // will never emit any event.

        // Also, debounce by default goes on Scheduler thread, so it is important to add observeOn
        // and observe it on main thread.

        ubookname.setError(null);
        ubookdescription.setError(null);
        ubookpublication.setError(null);
        ubookisbnno.setError(null);
        ubookauthor.setError(null);
        ubookedition.setError(null);
        ubookpages.setError(null);
        upublisingyear.setError(null);
        ubookprice.setError(null);


        // Store values at the time of the login attempt.
        String name = ubookname.getText().toString();
        String subscription = ubookdescription.getText().toString();
        String publication = ubookpublication.getText().toString();
        String isbn_No = ubookisbnno.getText().toString();
        String authorname = ubookauthor.getText().toString();
        String bookEdition = ubookedition.getText().toString();
        String book_pages = ubookpages.getText().toString();
        String publishing_year = upublisingyear.getText().toString();
        String book_price = ubookprice.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!validateUsername(name.toString()).isValid()){
            ubookname.setError(validateUsername(name.toString()).getReason());
            focusView = ubookname;
            cancel = true;
        }else if(!validateISBNNo(isbn_No.toString()).isValid()){
            ubookisbnno.setError(validateISBNNo(isbn_No.toString()).getReason());
            focusView = ubookisbnno;
            cancel = true;
        }else if(!validateUsername(authorname.toString()).isValid()){
            ubookauthor.setError(validateUsername(authorname.toString()).getReason());
            focusView = ubookauthor;
            cancel = true;
        }else if(!validateBookPages(book_pages.toString()).isValid()){
            ubookpages.setError(validateBookPages(book_pages.toString()).getReason());
            focusView = ubookpages;
            cancel = true;
        }else if(!validatePrice(book_price.toString()).isValid()){
            ubookprice.setError(validatePrice(book_price.toString()).getReason());
            focusView = ubookprice;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            uploadMyBook();
        }
    }

    private void clearForm(){
        ubookname.setText("");
        ubookdescription.setText("");
        ubookpublication.setText("");
        ubookisbnno.setText("");
        ubookauthor.setText("");
        ubookedition.setText("");
        ubookpages.setText("");
        upublisingyear.setText("");
        ubookprice.setText("");
        bookImage1.setImageResource(0);
        bookImage2.setImageResource(0);
        bookImage3.setImageResource(0);
        bookImage4.setImageResource(0);
    }


    private ValidationResult<String> validateUsername(@NonNull String username) {
        return ValidationUtils.isValidUsername(username);
    }

    private ValidationResult<String> validateDescription(@NonNull String desc) {
        return ValidationUtils.isValidDescription(desc);
    }

    private ValidationResult<String> validPublicationName(@NonNull String password) {
        return ValidationUtils.isValidPublicationName(password);
    }

    private ValidationResult<String> validateISBNNo(@NonNull String password) {
        return ValidationUtils.isValidISBNNumber(password);
    }


    private ValidationResult<String> validateBookEdition(@NonNull String username) {
        return ValidationUtils.isValidEdition(username);
    }

    private ValidationResult<String> validateBookPages(@NonNull String username) {
        return ValidationUtils.isValidPageNumber(username);
    }

    private ValidationResult<String> validateYear(@NonNull String city) {
        return ValidationUtils.isValidPublishingYear(city);
    }

    private ValidationResult<String> validatePrice(@NonNull String picode) {
        return ValidationUtils.isValidBookPrice(picode);
    }
}
