package com.example.isaksayyed.weatherapp.Retrofit;

import com.example.isaksayyed.weatherapp.Model.ForeCastWeatherResult;
import com.example.isaksayyed.weatherapp.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {

    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLng(@Query("lat") String lat,
                                                  @Query("lon") String lng,
                                                   @Query("appid") String appid,
                                                 @Query("units") String unod
                                                       );

    @GET("forecast")
    Observable<ForeCastWeatherResult> getWeatherForeCastResultByLatLng(@Query("lat") String lat,
                                                                       @Query("lon") String lng,
                                                                       @Query("appid") String appid,
                                                                       @Query("units") String unod
    );



}
