package com.elishi.android.Api;

import com.elishi.android.Modal.AddProduct.AddProductBody;
import com.elishi.android.Modal.AddProduct.VerifyCode;
import com.elishi.android.Modal.Category.AllCategory;
import com.elishi.android.Modal.Constant.Constant;
import com.elishi.android.Modal.EditProduct.EditProductBody;
import com.elishi.android.Modal.EditProfile.EditProfileBody;
import com.elishi.android.Modal.Favorite.FavBody;
import com.elishi.android.Modal.Filter.FilterList;
import com.elishi.android.Modal.Home.Ads;
import com.elishi.android.Modal.Home.GetHome;
import com.elishi.android.Modal.Home.NotifBody;
import com.elishi.android.Modal.Product.GetSingleProduct;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Product.ProductBody;
import com.elishi.android.Modal.Profile.GetUserById;
import com.elishi.android.Modal.Profile.User;
import com.elishi.android.Modal.Request.Login.PhoneCode;
import com.elishi.android.Modal.Request.User.SignupBody;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.Modal.Response.Login.UserBody;
import com.elishi.android.Modal.Response.PublicAPI.Locations;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("mobile/user/phone-verification")
    Call<GBody<UserBody>> phoneVerification(@Body PhoneCode body);

    @POST("mobile/user/code-verification")
    Call<GBody<UserBody>> codeVerification(@Body PhoneCode body);

    @POST("mobile/user/sign-up")
    Call<GBody<UserBody>> signUp(@Body SignupBody body);

    @GET("mobile/public/get-locations")
    Call<GBody<ArrayList<Locations>>> getLocations();

    @GET("mobile/public/get-home?")
    Call<GBody<GetHome>> getHome(@Query("device") String device, @Header("Authorization") String token);

    @GET("mobile/public/get-categories")
    Call<GBody<ArrayList<AllCategory>>> getCategories();

    @GET("mobile/user/get-user-by-id")
    Call<GBody<GetUserById>> getUserById(@Header("Authorization") String token);


    @Multipart
    @PUT("mobile/user/update-profile-image")
    Call<GBody<User>> addProductImage(@Part MultipartBody.Part oldImage, @Part MultipartBody.Part image, @Header("Authorization") String token);

    @POST("mobile/public/add-product")
    Call<GBody<Product>> addProduct(@Body AddProductBody body,@Header("Authorization") String token);

    @GET("mobile/public/get-constant-by-type?")
    Call<GBody<Constant>> getConstantPage(@Query("type") String type);

    @GET("mobile/public/get-constant-by-id?")
    Call<GBody<Constant>> getConstantById(@Query("type") String type);

    @Multipart
    @POST("mobile/public/add-product-image")
    Call<GBody<Product.Images>> addProductSmallImage(@Part MultipartBody.Part image, @Header("Authorization") String token);


    @Multipart
    @POST("mobile/public/add-main-product-image")
    Call<GBody<Product.Images>> addProductMainImage(@Part MultipartBody.Part image, @Header("Authorization") String token);


    @POST("mobile/public/get-products")
    Call<GBody<ArrayList<Product>>> getProducts(@Header("Authorization") String token, @Body ProductBody body);

    @GET("mobile/public/get-filter")
    Call<GBody<FilterList>> getFilter();

    @GET("mobile/public/get-products-ads")
    Call<GBody<ArrayList<Ads>>> getAdsProducts();

    @GET("mobile/public/get-product-by-id?")
    Call<GBody<GetSingleProduct>> getSingleProduct(@Query("p_id") String p_id,@Header("Authorization") String token);

    @POST("mobile/user/add-favorite")
    Call<ResponseBody> addFav(@Body FavBody body, @Header("Authorization") String token);

    @HTTP(method = "DELETE", path = "mobile/user/delete-favorite/{id}", hasBody = true)
    Call<ResponseBody> deleteFav(@Header("Authorization") String token, @Path("id") String p_id);

    @GET("mobile/public/search?")
    Call<GBody<ArrayList<Product>>> search(@Query("page") Integer page,@Query("query") String query,@Query("limit") Integer limit,@Header("Authorization") String token);

    @POST("mobile/public/get-verify-code")
    Call<GBody<VerifyCode>> getVerifyCode(@Header("Authorization") String token,@Body PhoneCode body);

    @PUT("mobile/public/update-product")
    Call<GBody<Product>> updateProduct(@Body EditProductBody body, @Header("Authorization") String token);

    @POST("mobile/public/delete-single-image")
    Call<GBody<String>> deleteSingleImage(@Body Product.Images body,@Header("Authorization") String token);

    @HTTP(method = "DELETE", path = "mobile/public/delete-product/{id}", hasBody = true)
    Call<ResponseBody> deleteProduct(@Header("Authorization") String token, @Path("id") String p_id);

    @POST("mobile/public/bring-to-front/{id}")
    Call<ResponseBody> bringToFront(@Header("Authorization") String token,@Path("id") String p_id);

    @GET("mobile/user/get-favorite?")
    Call<GBody<ArrayList<Product>>> getFavorite(@Header("Authorization") String token,@Query("page") Integer page,@Query("limit") Integer limit);

    @PUT("mobile/user/update-notification-token")
    Call<GBody<ResponseBody>> updateNotificationToken(@Header("Authorization") String token,@Body NotifBody body);

    @PUT("mobile/user/edit-profile")
    Call<ResponseBody> editProfile(@Header("Authorization") String token,
                                  @Body EditProfileBody body);

}
