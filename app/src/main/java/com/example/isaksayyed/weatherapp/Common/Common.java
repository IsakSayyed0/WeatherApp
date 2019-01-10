package com.example.isaksayyed.weatherapp.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public final  static  String App_Id = "66d4faf2e8af92c227b3d82ab2205d5e";

    public static Location current_location = null;

    public static String conertUnixToDate(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm EEE MM yyyy");

        String sdf = dateFormat.format(date);
        return sdf;
    }

    public static String UnixToHour(long sunrise) {

        Date date = new Date(sunrise*1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String sdf = dateFormat.format(date);
        return sdf;    }
}
