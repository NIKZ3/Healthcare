package com.t1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;
import com.t1.R;
import com.t1.ambmodel;
import com.t1.docadapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class amb_adapter extends RecyclerView.Adapter<amb_adapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<ambmodel> mlist;
    private amb_adapter.OnItemClickListener mlistener;
    private String dial;
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userlocation;
    String latitude,longitude;




    public interface OnItemClickListener{
        void onItemClick(int position);

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        final int REQUEST_CALL=1;
       // final int REQUEST_LOC=2;
        if(requestCode == REQUEST_CALL)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mcontext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            {
                mcontext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
            else
            {
               // Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }


       /* if(requestCode==REQUEST_LOC) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(mcontext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                }
            }
        }*/

    }


    public void setOnItemClickListener(amb_adapter.OnItemClickListener listener)
    {
        mlistener = listener;
    }

    amb_adapter(Context context, ArrayList<ambmodel> list)
    {
        mcontext = context;
        mlist = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);

        View view = layoutInflater.inflate(R.layout.activity_emergency,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull amb_adapter.ViewHolder holder, int position) {

        final int REQUEST_CALL=1;
        //final int REQUEST_LOC=2;
        final ambmodel docitem = mlist.get(position);

        TextView name1 = holder.name;
        ImageView ambcall1 = holder.ambcall;
        //ImageView book2 = holder.book1;
        final Context context = docitem.getcontext();
        mcontext = context;
       //final Activity a1 = (Activity) context;
        final String number=docitem.gettel().toString();




        name1.setText(docitem.getHospital_name());
        ambcall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dial = "tel:"+ number;
                if(ContextCompat.checkSelfPermission(mcontext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions((Activity) mcontext,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
                }
                else
                {
                    dial = "tel:"+ number;
                    mcontext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }
            }
        });

       /* book2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
               final  CollectionReference mref =  db.collection("doctors");
                locationManager = (LocationManager) mcontext.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                locationListener =new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
android-studio
                        locationManager.removeUpdates(locationListener);

                        userlocation = new LatLng(location.getLatitude(),location.getLongitude());
                        latitude = String.valueOf(location.getLatitude());
                        longitude = String.valueOf(location.getLongitude());

                        Log.i("latitude",latitude);
                        Log.i("longitude",longitude);

                        mref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        final String doc_log=document.get("Longitude").toString();
                                        final String doc_lat=document.get("Latitude").toString();
                                        String clinic=document.get("clinicname").toString();

                                        LatLng origin=new LatLng(Double.parseDouble(doc_lat),Double.parseDouble(doc_log));
                                        LatLng dest=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));

                                        final String org=origin.toString();
                                        final String dst=dest.toString();


                                        Map<String,Object> data_user = new HashMap<>();
                                       data_user.put("Patient Longitude",longitude);
                                        data_user.put("Patient Latitude",latitude);

                                        data_user.put("Doctor Longitude",doc_log);
                                        data_user.put("Doctor Latitude",doc_lat);
                                        data_user.put("Clinic name",clinic);
                                        data_user.put("booked","true");

                                        db.collection("ambulance")
                                                .add(data_user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                       //String time=getDurationForRoute(org,dst);
                                                       //Log.i("time",time);
                                                        Toast.makeText(mcontext,"Ambulance arriving in",Toast.LENGTH_LONG).show();

                                                        Log.i("message","success");

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.i("message","failure");
                                                    }
                                                });


                                    }
                                }
                                if(ContextCompat.checkSelfPermission(mcontext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                                {
                                    ActivityCompat.requestPermissions((Activity) mcontext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOC);

                                }
                                else
                                {

                                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                                }
                            }
                        });

                    }

                    public String getDurationForRoute(String origin, String destination)
                    {
                        // - We need a context to access the API
                        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                                .apiKey(API_KEY)
                                .build();

                        // - Perform the actual request
                        DirectionsResult directionsResult = null;
                        try {
                            directionsResult = DirectionsApi.newRequest(geoApiContext)
                                    .mode(TravelMode.DRIVING)
                                    .origin(origin)
                                    .destination(destination)
                                    .await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (com.google.maps.errors.ApiException e) {
                            e.printStackTrace();
                        }

                        // - Parse the result
                        DirectionsRoute route = directionsResult.routes[0];
                        DirectionsLeg leg = route.legs[0];
                        Duration duration = leg.duration;
                        return duration.humanReadable;
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };



                    }
        })*/




    }

    @Override
    public int getItemCount() {
        return mlist.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView ambcall;
       // ImageView book1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.hname);
            ambcall = itemView.findViewById(R.id.phone_call);
           // book1 = itemView.findViewById(R.id.bookamb);

        }

    }
}


