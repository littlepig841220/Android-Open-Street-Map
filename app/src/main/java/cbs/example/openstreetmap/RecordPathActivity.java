package cbs.example.openstreetmap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.TilesOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

import cbs.example.openstreetmap.tool.APIMethod;

public class RecordPathActivity extends AppCompatActivity {
    private MapView mapView;
    private TextView textView;
    private MyLocationNewOverlay myLocationNewOverlay;
    private IMapController mapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue("github-glenn1wang-myapp");
        setContentView(R.layout.activity_record_path);

        mapView = findViewById(R.id.mapView2);
        textView = findViewById(R.id.textView6);

        GeoPoint startPoint = new GeoPoint(25.05397, 121.47309);
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();

        APIMethod apiMethod = new APIMethod(mapView);
        apiMethod.setMapView();
        apiMethod.minimapOverlay(getApplicationContext(), displayMetrics);
        apiMethod.latLonGridlineOverlay2();
        apiMethod.rotationGestureOverlay(getApplicationContext());
        apiMethod.scaleBarOverlay(displayMetrics);

        mapController = mapView.getController();
        mapController.setZoom(19.0d);//數字越小地圖越小19
        mapController.setCenter(startPoint);

        myLocationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), mapView);
        myLocationNewOverlay.enableMyLocation();
        myLocationNewOverlay.runOnFirstFix(new getCurrentLocation(myLocationNewOverlay, textView, mapView));
        mapView.getOverlays().add(myLocationNewOverlay);
    }

    public void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    class getCurrentLocation implements Runnable {
        private TextView textView;
        private MyLocationNewOverlay myLocationNewOverlay;
        private Handler handler = new Handler();
        private List<GeoPoint> geoPoints = new ArrayList<>();
        private GeoPoint geoPoint;
        private MapView mapView;

        public getCurrentLocation(MyLocationNewOverlay myLocationNewOverlay, TextView textView, MapView mapView) {
            this.myLocationNewOverlay = myLocationNewOverlay;
            this.textView = textView;
            this.mapView = mapView;

            /*geoPoints.add(new GeoPoint(25.0539158, 121.4708258, 52.400001525878906));
            geoPoints.add(new GeoPoint(25.0539044, 121.470823, 52.400001525878906));
            geoPoints.add(new GeoPoint(25.0539133, 121.4708332, 52.400001525878906));
            geoPoints.add(new GeoPoint(25.05391478, 121.47081717, 18.94683837890625));

            Polyline polyline = new Polyline(mapView);
            polyline.setPoints(geoPoints);
            mapView.getOverlayManager().add(polyline);*/

        }

        @Override
        public void run() {
            synchronized (this) {
                if (myLocationNewOverlay.getMyLocation() == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Searching...");
                        }
                    });
                } else {
                    Log.d("MyTag", String.format("First location fix: %s", myLocationNewOverlay.getMyLocation().toString()));
                    geoPoint = myLocationNewOverlay.getMyLocation();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.format("My Location\nLongitude: %s\nLatitude: %s\nAltitude: %s",
                                    geoPoint.getLongitude(),
                                    geoPoint.getLatitude(),
                                    geoPoint.getAltitude()));

                            mapController.setCenter(geoPoint);
                        }
                    });

                    if (geoPoints.isEmpty()){
                        geoPoints.add(geoPoint);
                    }else {
                        if (geoPoints.get(geoPoints.size() - 1).equals(geoPoint)){
                            geoPoints.add(geoPoint);
                            Toast.makeText(getApplicationContext(), "update" + geoPoints.size(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    if (geoPoints.size() >= 2){
                        Polyline polyline = new Polyline(mapView);
                        polyline.setPoints(geoPoints);
                        mapView.getOverlayManager().add(polyline);
                        System.out.println("test");
                    }

                    handler.postDelayed(this, 2000);
                }
            }
        }
    }
}