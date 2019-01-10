package com.example.isaksayyed.weatherapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isaksayyed.weatherapp.Common.Common;
import com.example.isaksayyed.weatherapp.Model.WeatherResult;
import com.example.isaksayyed.weatherapp.Retrofit.IOpenWeatherMap;
import com.example.isaksayyed.weatherapp.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {

    ImageView weatherImage;
    TextView txtWind,txtHumdity,txtSunset,txtSunrise,txtPressure,txttemperature,txtDate_Time,txtCity,txtDiscription,txtGeoCoord;
    LinearLayout weatherPanel;
    ProgressBar loaging;
    CompositeDisposable compositeDisposable;

    static  TodayFragment instance;
    IOpenWeatherMap mService;

    public static TodayFragment getInstance(){
        if (instance == null){
            instance = new TodayFragment();
        }
        return instance;
    }


    public TodayFragment() {
        // Required empty public constructor
     compositeDisposable = new CompositeDisposable();
        Retrofit retrofitClient =RetrofitClient.getInstance();
        mService = retrofitClient.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view = inflater.inflate(R.layout.fragment_today, container, false);

        weatherImage = view.findViewById(R.id.weatherImage);
        txtCity = view.findViewById(R.id.cityName);
        txtHumdity = view.findViewById(R.id.txtHumdity);
        txtSunrise = view.findViewById(R.id.txtSunrise);
        txtSunset = view.findViewById(R.id.txtSunset);
        txtPressure = view.findViewById(R.id.txtPressure);
        txttemperature = view.findViewById(R.id.temperature);
        txtDiscription = view.findViewById(R.id.decription);
        txtDate_Time = view.findViewById(R.id.dateTime);
        txtWind = view.findViewById(R.id.txtWind);
        txtGeoCoord = view.findViewById(R.id.txtGeoCoord);

        loaging = view.findViewById(R.id.loading);
        weatherPanel = view.findViewById(R.id.weather_panel);

        getWeatherInformation();

    return view;
    }

    private void getWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.App_Id,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {

                        //load Image
                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                        .append(weatherResult.getWeather().get(0).getIcon())
                        .append(".png").toString()).into(weatherImage);

                        txtCity.setText(weatherResult.getName());
                        txtDiscription.setText(new StringBuilder("Weather in")
                        .append(weatherResult.getName()));
                        txttemperature.setText(new StringBuilder(
                                String.valueOf(weatherResult.getMain().getTemp())).append("C").toString());
                        txtDate_Time.setText(Common.conertUnixToDate(weatherResult.getDt()));
                        txtPressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append(" hpa").toString());
                        txtHumdity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append(" %").toString());
                        txtSunrise.setText(Common.UnixToHour(weatherResult.getSys().getSunrise()));
                        txtSunset.setText(Common.UnixToHour(weatherResult.getSys().getSunset()));
                        txtGeoCoord.setText(new StringBuilder("[").append(weatherResult.getCoord().toString()).append("]").toString());

                        weatherPanel.setVisibility(View.VISIBLE);
                        loaging.setVisibility(View.GONE);


                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Error",throwable.getMessage());
                    }
                })
        );

    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
