package com.example.riddhi.m1etropolishtutelage;

/**
 * Created by RIDDHI on 26-02-17.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.facebook.FacebookSdk.getApplicationContext;


public class brif_detail extends Fragment {
    String email;
    String city;
    private Activity context;
    String contact1;
    String address;
    String screen;
    TextView email1;
    TextView city1;
    TextView contact2;
    TextView address1;
    MapView mMapView;
    Button message;
    private GoogleMap googleMap;
    Globalvariable globalVariable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.brif_details, container, false);
        email1 = (TextView) v.findViewById(R.id.email);
        message = (Button) v.findViewById(R.id.message);
        city1 = (TextView) v.findViewById(R.id.city);
        contact2 = (TextView) v.findViewById(R.id.contact);
        address1 = (TextView) v.findViewById(R.id.address);
        mMapView = (MapView) v.findViewById(R.id.mapView_brief);
        mMapView.onCreate(savedInstanceState);


        mMapView.onResume(); // needed to get the map to display immediately
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker

                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");
        Bundle args = getArguments();
        email = args.getString("email");
        city = args.getString("city");
        contact1 = args.getString("contact");
        address = args.getString("address");
        screen = args.getString("screen");
        email1.setText(email);
        city1.setText(city);
        contact2.setText(contact1);
        address1.setText(address);

        if (screen.equals("muncipality")) {
            globalVariable = (Globalvariable) getApplicationContext();
            globalVariable.setName("muncipality");
        }
        else if (screen.equals("school")){
            globalVariable = (Globalvariable) getApplicationContext();
            globalVariable.setName("school");
        }
        else if (screen.equals("ngo")){
            globalVariable = (Globalvariable) getApplicationContext();
            globalVariable.setName("ngo");
        }
        else if (screen.equals("remainstudent")){
            globalVariable = (Globalvariable) getApplicationContext();
            globalVariable.setName("remainstudent");

        }
        SharedPreferences pref1 = getActivity().getSharedPreferences("to", 0); // 0 - for private mode
        SharedPreferences.Editor editor1 = pref1.edit();
        editor1.putString("email", email);
        // editor1.putString("password", password); // for announcements
        editor1.commit();

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = manager.beginTransaction();
//                message fragment = new message();
//                fragmentTransaction.replace(R.id.content_frame, fragment);
//                fragmentTransaction.commit();

                message fragment1 = new message();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment1);
                fragmentTransaction.commit();

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}