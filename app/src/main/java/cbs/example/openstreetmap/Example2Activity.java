package cbs.example.openstreetmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import cbs.example.openstreetmap.tool.APIMethod;

public class Example2Activity extends AppCompatActivity implements MapListener{
    //Component declaration
    private MapView mapView;
    private TextView textView;
    //Element declaration
    private MyLocationNewOverlay myLocationNewOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue("github-glenn1wang-myapp");
        setContentView(R.layout.activity_basic);

        mapView = findViewById(R.id.mapView);
        textView = findViewById(R.id.textView5);

        GeoPoint startPoint = new GeoPoint(25.05397, 121.47309);

        //Method already used
        APIMethod apiMethod = new APIMethod(mapView);
        apiMethod.setMapView();
        apiMethod.mapController(startPoint);
        apiMethod.maker(startPoint, "Start point", "Start point");
        apiMethod.rotationGestureOverlay(getApplicationContext());

        //LatLonGridlineOverlay2 method
        LatLonGridlineOverlay2 latLonGridlineOverlay2 = new LatLonGridlineOverlay2();
        latLonGridlineOverlay2.setFontSizeDp(Short.parseShort("0"));
        mapView.getOverlays().add(latLonGridlineOverlay2);

        //MyLocationNewOverlay method
        myLocationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), mapView);
        myLocationNewOverlay.enableMyLocation();
        myLocationNewOverlay.runOnFirstFix(new getCurrentLocation(myLocationNewOverlay));
        mapView.getOverlays().add(myLocationNewOverlay);

        //DisplayMetrics method
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(displayMetrics.widthPixels/2, 10);
        mapView.getOverlays().add(scaleBarOverlay);

        //MinimapOverlay method
        MinimapOverlay minimapOverlay = new MinimapOverlay(getApplicationContext(), mapView.getTileRequestCompleteHandler());
        minimapOverlay.setWidth(displayMetrics.widthPixels/5);
        minimapOverlay.setHeight(displayMetrics.heightPixels/5);
        minimapOverlay.setTileSource(TileSourceFactory.WIKIMEDIA);
        mapView.getOverlays().add(minimapOverlay);

        //Listener
        mapView.addMapListener(this);

        //Failed
        /*InternalCompassOrientationProvider internalCompassOrientationProvider = new InternalCompassOrientationProvider(getApplicationContext());
        CompassOverlay compassOverlay = new CompassOverlay(getApplicationContext(), internalCompassOrientationProvider, mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);*/

        //Failed
        /*CompassOverlay compassOverlay = new CompassOverlay(getApplicationContext(), mapView);
        compassOverlay.setPointerMode(false);
        compassOverlay.enableCompass();
        mapView.getOverlayManager().add(compassOverlay);
        mapView.invalidate();*/
    }

    public void onResume() {
        super.onResume();
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        if (mapView != null)
            mapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause() {
        super.onPause();
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        if (mapView != null)
            mapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public boolean onScroll(ScrollEvent event) {
        int x = event.getX();
        int y = event.getY();
        Log.i(APIMethod.TAG, "x:" + x + "y:" + y);
        return false;
    }

    @Override
    public boolean onZoom(ZoomEvent event) {
        double zoomLevel = event.getSource().getZoomLevelDouble();
        Log.i(APIMethod.TAG, "zoom level:" + zoomLevel);

        if (zoomLevel == mapView.getMinZoomLevel()){
            Toast.makeText(getApplicationContext(), "世界很大，但地圖很小了", Toast.LENGTH_LONG).show();
        } else if (zoomLevel == mapView.getMaxZoomLevel()){
            Toast.makeText(getApplicationContext(), "已經很大囉，這樣你會知道的太多", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    class getCurrentLocation implements Runnable{
        private MyLocationNewOverlay myLocationNewOverlay;
        private Handler handler = new Handler();

        public getCurrentLocation(MyLocationNewOverlay myLocationNewOverlay){
            this.myLocationNewOverlay = myLocationNewOverlay;
        }

        @Override
        public void run() {
            if (myLocationNewOverlay.getMyLocation() == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Searching...");
                    }
                });
            }else {
                Log.d(APIMethod.TAG, String.format("First location fix: %s", myLocationNewOverlay.getMyLocation().toString()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(String.format("My Location\nLongitude: %s\nLatitude: %s\nAltitude: %s",
                                myLocationNewOverlay.getMyLocation().getLongitude(),
                                myLocationNewOverlay.getMyLocation().getLatitude(),
                                myLocationNewOverlay.getMyLocation().getAltitude()));
                    }
                });
            }
            handler.postDelayed(this, 1000);
        }
    }
}