package com.shareads.user.myapplication.retrofit;



import com.shareads.user.myapplication.model.BooKUploadModal;
import com.shareads.user.myapplication.model.BookCategoryModal;
import com.shareads.user.myapplication.model.BookReviewModal;
import com.shareads.user.myapplication.model.BookdetailModel;
import com.shareads.user.myapplication.model.CartResponse;
import com.shareads.user.myapplication.model.ChangePasswordModal;
import com.shareads.user.myapplication.model.ForgotPasswordModal;
import com.shareads.user.myapplication.model.LoginData;
import com.shareads.user.myapplication.model.MyBookListModel;
import com.shareads.user.myapplication.model.OTPModal;
import com.shareads.user.myapplication.model.PostReviewModal;
import com.shareads.user.myapplication.model.Recomended;
import com.shareads.user.myapplication.model.SearchModal;
import com.shareads.user.myapplication.model.TopSelling;
import com.shareads.user.myapplication.model.UserWishList;
import com.shareads.user.myapplication.model.cartmodel;
import com.shareads.user.myapplication.model.categoryListModel;
import com.shareads.user.myapplication.model.updateprofileModel;
import com.shareads.user.myapplication.model.wishModl;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginData> getLogin(@Field("mobileno") String mobileNo, @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginData> changeAddress(@Field("userid") String userid, @Field("address1") String address1,@Field("address2") String address2);

    @FormUrlEncoded
    @POST("r2.php")
    Call<com.shareads.user.myapplication.model.Response> getRegister(@Field("mobileno") String mobile, @Field("email") String email,
                                                                     @Field("password") String password, @Field("city") String city,
                                                                     @Field("pincode") String pincode, @Field("country") String india, @Field("gender") String gender, @Field("points") String points);


    @FormUrlEncoded
    @POST("update-profile.php")
    Call<OTPModal> varifyOTP(@Field("action") String action, @Field("userid") String userid, @Field("otp") String otp);

//    @FormUrlEncoded
//    @POST("uploadbook.php")
//    Call<LoginData> uploadBook(@Field("userid") String userid, @Field("title") String title,
//                               @Field("categoryid") String categoryid, @Field("isbnnumber") String isbnnumber,
//                               @Field("author") String author, @Field("publication") String publication,
//                               @Field("edition") String edition, @Field("language") String language,
//                               @Field("binding") String binding, @Field("price") String price,
//                               @Field("description") String description, @Field("discount") String discount,
//                               @Field("noofpages") String noofpages, @Field("bookstatus") String bookstatus,
//                               @Field("rating") String rating, @Field("publishingyear") String publishingyear,
//                               @Field("termsandconditions") String termsandconditions, @Field("status") String status,@Field("frontimage") String frontimage,
//                               @Field("backimage") String backimage,@Field("contentimage") String contentimage,@Field("spineimage") String spineimage,
//                               @Field("reload_new") String reloadnew,@Field("dateposted") String dateposted);

//    @Headers("Accept: multipart/form-data")
    @Multipart
    @POST("uploadbook.php")
    Call<BooKUploadModal> uploadBook(@Part("userid") RequestBody userid, @Part("categoryid") RequestBody categoryid,
                                     @Part("title") RequestBody title, @Part("isbnnumber") RequestBody isbnnumber,
                                     @Part("author") RequestBody author, @Part("publication") RequestBody publication,
                                     @Part("noofpages") RequestBody noofpages, @Part("edition") RequestBody edition,
                                     @Part("language") RequestBody language, @Part("binding") RequestBody binding,
                                     @Part("price") RequestBody price, @Part("discount") RequestBody discount,
                                     @Part("bookstatus") RequestBody bookstatus, @Part("description") RequestBody description,
                                     @Part("reload_new") RequestBody reload_new, @Part("status") RequestBody status,
                                     @Part("publishingyear") RequestBody publishingyear, @Part("termsandconditions") RequestBody termsandconditions,
                                     @Part("rating") RequestBody rating,
                                     @Part("dateposted") RequestBody dateposted,
                                     @Part("discountprice") RequestBody discountprice,
                                     @Part MultipartBody.Part frontimage,
                                     @Part MultipartBody.Part backimage,
                                     @Part MultipartBody.Part contentimage,
                                     @Part MultipartBody.Part spineimage);



    //Call<BloodBankData> getBankData(@Query("current_page") String current_page);

    @FormUrlEncoded
    @POST("getuserbooklist.php")
    Call<MyBookListModel>myBookList(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("getreviews.php")
    Call<BookReviewModal>getReview(@Field("bookid") String bookid);

    @FormUrlEncoded
    @POST("ratebook.php")
    Call<PostReviewModal> postReview(@Field("userid") String userid, @Field("bookid") String bookid, @Field("rating") String rating, @Field("comment") String comment);

    @FormUrlEncoded
    @POST("getuserwishlist.php")
    Call<UserWishList> myBookWishList(@Field("userid") String userid);


    @POST("categorylist.php")
    Call<categoryListModel> categoryList();

    @FormUrlEncoded
    @POST("update-profile.php")
    Call<OTPModal> updateProfile(@Field("action") String update,@Field("userid") String userid,@Field("name") String name,
                                           @Field("email") String email,@Field("mobileno") String mobile_no,
                                           @Field("state") String state, @Field("city") String city,
                                           @Field("pincode") String pincode,@Field("address1") String address,
                                           @Field("address2") String address1);

    @FormUrlEncoded
    @POST("update-profile.php")
    Call<updateprofileModel> getProfile(@Field("action") String update,@Field("userid") String userid);


    @FormUrlEncoded
    @POST("addtocart.php")
    Call<cartmodel> myaddtocart(@Field("userid") String userid,@Field("bookid") String bookid,@Field("action") String action);

    @FormUrlEncoded
    @POST("wishlist.php")
    Call<wishModl> myaddtowish(@Field("userid") String userid, @Field("bookid") String bookid, @Field("action") String action);

    @FormUrlEncoded
    @POST("deletefromwishlist.php")
    Call<wishModl> deleteFromWishList(@Field("id") String wishlistid);

    @FormUrlEncoded
    @POST("getbookdetails.php")
    Call<BookdetailModel> bookdetail(@Field("bookid") String bookuniaueid);

    @FormUrlEncoded
    @POST("recommand.php")
    Call<Recomended> recommand(@Field("userid") String userid);

    @GET("topselling.php")
    Call<TopSelling> topselling();

    @FormUrlEncoded
    @POST("getusercart.php")
    Call<CartResponse> getUserCart(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("getbookbysearch.php")
    Call<SearchModal> searchQuery(@Field("bookname") String bookname);

    @FormUrlEncoded
    @POST("getbooklistcategory.php")
    Call<BookCategoryModal> getbooklistcategory(@Field("categoryid") String categoryid);

    @FormUrlEncoded
    @POST("forgetpassword.php")
    Call<ForgotPasswordModal> getPassword(@Field("mobileno") String mobileno);

    @FormUrlEncoded
    @POST("chnagepassword.php")
    Call<ChangePasswordModal> changePassword(@Field("userid") String userid,@Field("oldpassword") String oldpassword,
                                             @Field("password") String password,@Field("cpassword") String cpassword);

}

