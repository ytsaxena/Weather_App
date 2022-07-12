package android.example.weatherapp;

import android.example.weatherapp.Model.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

//      @GET("v1/current.json?key=a64c93ca56e2431f8f970057222904&{q}&{aqi}")
//     Call<Root>getdata (@Query("q") String city);


      @GET("/v1/current.json?key=a64c93ca56e2431f8f970057222904&q=&aqi=no")
    Call<Root>getData(@Query("q") String city);

}
