package rus.natthawadee.rusrun;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latRusADouble = 13.859132;
    private double lngARusDouble = 100.481989;
    private LocationManager locationManager;
    private Criteria criteria;
    private double latUserADouble, lngUserADouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Setup Location Service
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    } //Main Method

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.removeUpdates(locationListener);

        latUserADouble = latRusADouble;
        lngUserADouble = lngARusDouble;

        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);
        if (networkLocation != null) {
            latUserADouble = networkLocation.getLatitude();
            lngUserADouble = networkLocation.getLongitude();
        }

        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            latUserADouble = gpsLocation.getLatitude();
            lngUserADouble = gpsLocation.getLongitude();

        }


    }   //onResume

    @Override
    protected void onStop() {
        super.onStop();

        locationManager.removeUpdates(locationListener);


    }

    public Location myFindLocation(String strProvider) {

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {

            locationManager.requestLocationUpdates(strProvider,1000,10,locationListener);
            location = locationManager.getLastKnownLocation(strProvider);

        } else {
            Log.d("RusV2", "Cannot Find Location");
        }

        return location;
    }

    //Create Class
    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            latUserADouble = location.getLatitude();
            latUserADouble = location.getLongitude();

        }   // Onlocationchanged

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        //Set Center Map
        LatLng latLng = new LatLng(latRusADouble,lngARusDouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));

        //Loop
        myLoop();


    } // onMapReady

    private void myLoop() {

        // To Do
        Log.d("RusV3", "latUser ==>" + latUserADouble);
        Log.d("RusV3", "lngUser ==>" + lngUserADouble);

        // Delay
        Handler handler = new Handler ();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myLoop();
            }
        },300);

    } // myLoop
}//Main class
