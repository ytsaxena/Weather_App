package android.example.weatherapp.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api{


    private static Retrofit retrofit;


public static final String BASE_URL ="http://api.weatherapi.com/";

public static Retrofit getretrofit()
{
    if (retrofit == null)
    {
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
return retrofit;
}


}
