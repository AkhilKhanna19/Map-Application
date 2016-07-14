package mapjson.com.mapapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String url ="http://www.mocky.io/v2/5785e6590f0000af23c28a0d";
    private Button filter;
    private double latitude,longitude;
    private static String value = "def";
    private ArrayList<HashMap<String,String>> list= new ArrayList<>();
    private String type, title , coordinate;
    public static final int REQUEST_CODE= 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        filter = (Button) findViewById(R.id.filter_button_id);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MapsActivity.this, MainActivity.class);
                startActivityForResult(i,REQUEST_CODE);
                mMap.clear();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("list", String.valueOf(list));

        if (resultCode== Activity.RESULT_OK){
            value = data.getStringExtra("key");
            onMapReady(mMap);

            Log.d("value",value);



        }



    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i<data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        type = jsonObject.getString("type");
                        coordinate = jsonObject.getString("coordinate");
                        Log.d("coordinate", coordinate);
                        title = jsonObject.getString("title");
                        //String colors[]= {"HUE_AZURE", "HUE_BLUE","HUE_CYAN", "HUE_GREEN", "HUE_MAGENTA", "HUE_ORANGE", "HUE_RED", "HUE_ROSE", "HUE_VIOLET", "HUE_YELLOW"};

                        HashMap<String, String> data_values = new HashMap<>();
                        data_values.put("coordinate", coordinate);
                        data_values.put("type", type);
                        data_values.put("title", title);



                        list.add(data_values);
                    }
                    HashMap<String ,String > mData = new HashMap<>();
                    Log.d("list", String.valueOf(list));
                    for(int c = 0;c<list.size();c++){
                        mData=list.get(c);
                        String coord[] = mData.get("coordinate").split(",");
                        double lati = Double.parseDouble(coord[0]);
                        Log.d("latitude", String.valueOf(lati));
                        double longi = Double.parseDouble(coord[1]);
                        LatLng location = new LatLng(lati, longi);
                        Log.d("value", value);
                        Log.d("type", mData.get("type"));

                        switch (value) {

                            case "exclusive":
                                if (mData.get("type").equals("ex")) {
                                    mMap.addMarker(new MarkerOptions().position(location).title(mData.get("title")).snippet(mData.get("type")).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location));


                                }

                                break;
                            case "people":
                                if (mData.get("type").equals("pe")) {
                                    mMap.addMarker(new MarkerOptions().position(location).title(mData.get("title")).snippet(mData.get("type")).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                                }

                                break;
                            case "normal":
                                if (mData.get("type").equals("ev")) {
                                    mMap.addMarker(new MarkerOptions().position(location).title(mData.get("title")).snippet(mData.get("type")).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                                }

                                break;
                            case "def":
                                mMap.addMarker(new MarkerOptions().position(location).title(mData.get("title")).snippet(mData.get("type")).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));



                        }


                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                Intent intent = new Intent(MapsActivity.this,DisplayActivity.class);
                                intent.putExtra("title", marker.getTitle());
                                intent.putExtra("coordinates",marker.getPosition().toString());
                                intent.putExtra("event", marker.getSnippet());
                                startActivity(intent);

                                return true;
                            }
                        });


                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);




    }
}