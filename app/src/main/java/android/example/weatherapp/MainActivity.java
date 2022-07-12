package android.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.example.weatherapp.Api.Api;
import android.example.weatherapp.Model.Condition;
import android.example.weatherapp.Model.Root;
import android.example.weatherapp.databinding.ActivityMainBinding;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    Root root;
    ActivityMainBinding activityMainBinding;
  //  String cityn = "Jhansi";
  String cityName;

  ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_main);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();

        setContentView(view);

        activityMainBinding.searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName = activityMainBinding.enteryourcity.getText().toString();
                getCityWeather(cityName);
            }
        });


        getCityWeather("Noida");
    }


    private void getCityWeather(String cityName){
        ApiInterface service = Api.getretrofit().create(ApiInterface.class);
        Call<Root> call = service.getData(cityName);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {

                    activityMainBinding.shimmerLayout.shimmerViewContainer.stopShimmerAnimation();
                    activityMainBinding.shimmerLayout.shimmerViewContainer.setVisibility(View.GONE);


                    Root root = response.body();
                    Log.v("data", "data sucess");
                    if (root != null)
                    {
                        getalldata(root);
                    }else{
                        Toast.makeText(MainActivity.this,response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something error happened", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getalldata(Root root) {
        activityMainBinding.name.setText(root.getLocation().getName());
        activityMainBinding.region.setText(root.getLocation().getRegion());
        activityMainBinding.time.setText(root.getLocation().getLocaltime());


        Double tempv = root.getCurrent().getTemp_c();
        Double windspeed = root.getCurrent().getWind_kph();

        int humidity = root.getCurrent().getHumidity();
        activityMainBinding.temp.setText(tempv.toString());
        activityMainBinding.humidity.setText(Integer.toString(humidity));
        activityMainBinding.wind.setText(root.getCurrent().getWind_dir());
        activityMainBinding.windspeed.setText(windspeed.toString());
        Log.d("HHHH", root.getCurrent().getCondition().getIcon());

        String url = "http:"+root.getCurrent().getCondition().getIcon();
        Picasso.get()
                .load(url)
                .into(activityMainBinding.icon);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityMainBinding.shimmerLayout.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityMainBinding.shimmerLayout.shimmerViewContainer.stopShimmerAnimation();

    }
}