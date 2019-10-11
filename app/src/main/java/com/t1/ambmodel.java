package com.t1;

import android.content.Context;

public class ambmodel {

    private String Hospital_name,lon,lat,tel;


    private Context context;


    public ambmodel(String Hospital_name,String tel1,Context context1)
    {
        this.Hospital_name=Hospital_name;
        this.tel = tel1;
        this.context = context1;
    }

    public String getHospital_name(){return Hospital_name;}

    public String gettel(){return tel;};

    public Context getcontext(){return context;};


    public String getlon(){return lon;}

    public String getlat(){return lat;}

}
