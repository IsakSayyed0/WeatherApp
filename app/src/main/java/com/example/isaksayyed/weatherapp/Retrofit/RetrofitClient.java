package com.example.isaksayyed.weatherapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

//    https://samples.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=66d4faf2e8af92c227b3d82ab2205d5e

    private static Retrofit retrofit = null;
    public static Retrofit getInstance(){
        if (retrofit == null){
//http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=66d4faf2e8af92c227b3d82ab2205d5e
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
