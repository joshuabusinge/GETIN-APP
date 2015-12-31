package ug.getin.app.service;

import retrofit.RestAdapter;


/**
 * Created by abdussekalala on 12/31/15.
 */
public class ApiClient {
    public RestAdapter getRestAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://10.0.3.2:8000/api/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter;
    }


}