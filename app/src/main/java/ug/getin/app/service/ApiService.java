package ug.getin.app.service;

import java.util.List;


import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import ug.getin.app.models.Girl;

/**
 * Created by abdussekalala on 12/31/15.
 */
public interface ApiService {

    @GET("/girls/list/")
    void listGirls(Callback<List<Girl>> cb);



    @POST("/girls/register/")
    void registerGirl(@Body Girl pregnantGirl, Callback<Girl> cb);
}

