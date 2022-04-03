package com.elishi.android.Api;

import com.elishi.android.Modal.Home.GetHome;
import com.elishi.android.Modal.Request.Login.PhoneCode;
import com.elishi.android.Modal.Response.GBody;
import com.elishi.android.Modal.Response.Login.UserBody;
import com.elishi.android.Modal.Response.PublicAPI.Locations;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("mobile/user/phone-verification")
    Call<GBody<UserBody>> phoneVerification(@Body PhoneCode body);

    @POST("mobile/user/code-verification")
    Call<GBody<UserBody>> codeVerification(@Body PhoneCode body);

    @POST("mobile/user/sign-up")
    Call<GBody<UserBody>> signUp();

    @GET("mobile/public/get-locations")
    Call<GBody<ArrayList<Locations>>> getLocations();

    @GET("mobile/public/get-home?")
    Call<GBody<GetHome>> getHome(@Query("device") String device, @Header("Authorization") String token);
}
