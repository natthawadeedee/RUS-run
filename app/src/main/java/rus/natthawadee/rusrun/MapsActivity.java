package rus.natthawadee.rusrun;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latRusADouble = 13.858962;
    private double lngRusADouble = 100.482094;
    private LocationManager locationManager;
    private Criteria criteria;
    private double latUserADouble, lngUserADouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Setup Location Service
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //ให้ทำการค้นหาอย่างละเอียด
        criteria.setAltitudeRequired(false); //ถ้าตั้งเป็น true สามารถทำการหาค่าระดับน้ำทะเลได้ ถ้าตั้งเป็น false ต้องการแค่ค่า XY เท่านั้น
        criteria.setBearingRequired(false);  //ถ้าตั้งเป็น true สามารถทำการหาค่าระดับน้ำทะเลได้ ถ้าตั้งเป็น false ต้องการแค่ค่า XY เท่านั้น


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }//Main Method

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.removeUpdates(locationListener);

        latUserADouble = latRusADouble;
        lngUserADouble = lngRusADouble;

        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);//การหาพิกัดผ่าน network
        if (networkLocation != null) {

            latUserADouble = networkLocation.getLatitude();
            lngUserADouble = networkLocation.getLongitude();
        }

        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);//หาพิกัดโดยไม่ต้องต่อเน็ต
        if (gpsLocation != null) {
            latUserADouble = gpsLocation.getLatitude();
            lngUserADouble = gpsLocation.getLongitude();
        }

    }//onResume

    @Override
    protected void onStop() {
        super.onStop();

        locationManager.removeUpdates(locationListener);
    }

    public Location myFindLocation(String strProvider) {

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {

            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);//เลข 1000 คือหน่วยเวลาของ android เลข 10 คือถ้าขยับเกิน 10 เมตร
            location = locationManager.getLastKnownLocation(strProvider);


        } else {

            Log.d("RusV2", "Cannot Find Location");

        } //ไม่จริงทำงานที่นี่

        return location;
    }//จะทำงานและคืนค่า location กลับไป

    //Create Class
    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            latUserADouble = location.getLatitude();
            lngUserADouble = location.getLongitude();


        }//onLocation Change

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }//ถ้าสามารถทำงานกับ Internet ได้

        @Override
        public void onProviderDisabled(String provider) {

        }
    };//listener ถ้าพิกัดมีการเปลี่ยนแปลงให้ทำงานอัตโนมัติ



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Setup Center Map
        LatLng latLng = new LatLng(latRusADouble, lngRusADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));


        //Loop
        myLoop();


    }//onMapReady

    private class CreateMarker extends AsyncTask<Void, Void, String> {

        //Explicit
        private Context context;
        private GoogleMap googleMap;
        private String urlJSON = "http://swiftcodingthai.com/rus/get_user_saikaew.php";

        public CreateMarker(Context context, GoogleMap googleMap) {
            this.context = context;
            this.googleMap = googleMap;
        }//Constructor

        @Override
        protected String doInBackground(Void... params) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();

            } catch (Exception e) {
                return null;
            }// try
        }// DoInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("RusV4", "JSON ==>>>" + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    double douLat = Double.parseDouble(jsonObject.getString("Lat"));
                    double douLng = Double.parseDouble(jsonObject.getString("Lng"));
                    String strName = jsonObject.getString("Name");

                    LatLng latLng = new LatLng(douLat, douLng);
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(strName));

                }//for



            } catch (Exception e) {

                e.printStackTrace();
            }


        }//onPostExecute Method
    }//Create Marker Class

    private void myLoop() {

        //To Do
        Log.d("RusV3", "latUser ==> " + latUserADouble);
        Log.d("RusV3", "lngUser ==> " + lngUserADouble);

        //edit Lat,Lng on server
        editlatLngOnServer();

        //Create Marker
        mMap.clear();
        CreateMarker createMarker = new CreateMarker(this, mMap);
        createMarker.execute();


        //Delay
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                myLoop();

            }
        }, 3000);


    }//myLoop

    private void editlatLngOnServer() {

        String urlPHP = "http://swiftcodingthai.com/rus/edit_location_saikaew.php";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", getIntent().getStringExtra("loginID"))
                .add("Lat", Double.toString(latUserADouble))
                .add("Lng", Double.toString(lngUserADouble))
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });

    }//editlatLngOnServer Method
}//Main Class