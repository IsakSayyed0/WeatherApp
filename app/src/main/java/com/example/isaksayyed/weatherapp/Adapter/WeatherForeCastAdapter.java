package com.example.isaksayyed.weatherapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isaksayyed.weatherapp.Common.Common;
import com.example.isaksayyed.weatherapp.Model.ForeCastWeatherResult;
import com.example.isaksayyed.weatherapp.R;
import com.squareup.picasso.Picasso;

public class WeatherForeCastAdapter extends RecyclerView.Adapter<WeatherForeCastAdapter.MyViewHolder> {


    Context context;
    ForeCastWeatherResult foreCastWeatherResult;

    public WeatherForeCastAdapter(Context context, ForeCastWeatherResult foreCastWeatherResult) {
        this.context = context;
        this.foreCastWeatherResult = foreCastWeatherResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.item_forecast,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //load Image
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                .append(foreCastWeatherResult.list.get(position).weather.get(0).getIcon())
                .append(".png").toString()).into(holder.imageView);

        holder.txtDateTime.setText(new StringBuilder(Common.conertUnixToDate(foreCastWeatherResult
        .list.get(position).dt)));
        holder.txtDis.setText(new StringBuilder(foreCastWeatherResult.list.get(position
        ).weather.get(0).getDescription()));
        holder.txtTemperature.setText(new StringBuilder(String.valueOf(foreCastWeatherResult.list.get(position)
        .main.getTemp())).append("C"));
    }

    @Override
    public int getItemCount() {
        return foreCastWeatherResult.list.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView txtDateTime,txtDis,txtTemperature;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.weatherImage);
            txtDateTime =(TextView)itemView.findViewById(R.id.day);
            txtDis =(TextView)itemView.findViewById(R.id.des);
            txtTemperature =(TextView)itemView.findViewById(R.id.txttemperature);


        }
    }
}
