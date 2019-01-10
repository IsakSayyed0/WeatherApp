package com.example.isaksayyed.weatherapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isaksayyed.weatherapp.Adapter.WeatherForeCastAdapter;
import com.example.isaksayyed.weatherapp.Common.Common;
import com.example.isaksayyed.weatherapp.Model.ForeCastWeatherResult;
import com.example.isaksayyed.weatherapp.Retrofit.IOpenWeatherMap;
import com.example.isaksayyed.weatherapp.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {

    TextView txtCityName,txtGeoCoord;
    RecyclerView recyclerViewForeCast;

    // WeatherForeCastAdapter adapter;
    CompositeDisposable compositeDisposable;
    static  ForecastFragment instance;
    IOpenWeatherMap mService;

    public static ForecastFragment getInstance(){
        if (instance == null){
            instance = new ForecastFragment();
        }
        return instance;
    }

    public ForecastFragment() {
        // Required empty public constructor
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofitClient =RetrofitClient.getInstance();
        mService = retrofitClient.create(IOpenWeatherMap.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forecast, container, false);

        txtCityName = (TextView)view.findViewById(R.id.cityName);
        txtGeoCoord =(TextView)view.findViewById(R.id.GeoCoord);

        recyclerViewForeCast = (RecyclerView)view.findViewById(R.id.recycler_ForeCast);
        recyclerViewForeCast.setHasFixedSize(true);
        recyclerViewForeCast.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        getForeCastInformation();

        return  view;

    }

    private void getForeCastInformation() {
        compositeDisposable.add(mService.getWeatherForeCastResultByLatLng(
                String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.App_Id,
                "metric"
        ).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ForeCastWeatherResult>() {
                    @Override
                    public void accept(ForeCastWeatherResult foreCastWeatherResult) throws Exception {
//
//                        txtCityName.setText(new StringBuilder(foreCastWeatherResult.city.name));
//                        txtGeoCoord.setText(new StringBuilder(foreCastWeatherResult.city.coord.toString()));
//                        WeatherForeCastAdapter adapter = new WeatherForeCastAdapter(getContext(),foreCastWeatherResult);
//                        recyclerViewForeCast.setAdapter(adapter);
                    displayForeCastResult(foreCastWeatherResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("httpError","What"+throwable.getMessage());

                    }
                })
        );


    }
//
    private void displayForeCastResult(final ForeCastWeatherResult foreCastWeatherResult) {
        txtCityName.setText(new StringBuilder(foreCastWeatherResult.city.name));
        txtGeoCoord.setText(new StringBuilder(foreCastWeatherResult.city.coord.toString()));


//
////          final ForeCastWeatherResult result = foreCastWeatherResult;
//        getActivity().runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                // Stuff that updates the UI
//                adapter = new WeatherForeCastAdapter(getContext(),foreCastWeatherResult);
//
//                recyclerViewForeCast.setAdapter(adapter);
//            }
//        });

        WeatherForeCastAdapter adapter = new WeatherForeCastAdapter(getContext(),foreCastWeatherResult);
        recyclerViewForeCast.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
