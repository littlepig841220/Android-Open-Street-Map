package cbs.example.openstreetmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

import cbs.example.openstreetmap.tool.APIMethod;

public class Example4Activity extends AppCompatActivity {
    private MapView mapView;
    private TextView textView;
    private APIMethod apiMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue("github-glenn1wang-myapp");
        setContentView(R.layout.activity_basic);

        mapView = findViewById(R.id.mapView);
        textView = findViewById(R.id.textView5);

        GeoPoint startPoint = new GeoPoint(25.05397, 121.47309);
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();

        apiMethod = new APIMethod(mapView);
        apiMethod.setMapView();
        apiMethod.mapController(startPoint);
        apiMethod.rotationGestureOverlay(getApplicationContext());
        apiMethod.latLonGridlineOverlay2();
        apiMethod.myLocationNewOverlay(getApplicationContext(), textView);
        apiMethod.scaleBarOverlay(displayMetrics);
        apiMethod.minimapOverlay(getApplicationContext(), displayMetrics);

        List<GeoPoint> geoPoints = new ArrayList<>();

        geoPoints.add(new GeoPoint(25.05400d, 121.47300d));
        geoPoints.add(new GeoPoint(25.05200d, 121.47100d));

        Polyline polyline = new Polyline();
        polyline.setPoints(geoPoints);
        polyline.setOnClickListener(new Polyline.OnClickListener() {
            @Override
            public boolean onClick(Polyline polyline, MapView mapView, GeoPoint eventPos) {
                Toast.makeText(mapView.getContext(), "polyline with " + polyline.getPoints().size() + "pts was tapped", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        mapView.getOverlayManager().add(polyline);

        List<GeoPoint> points = new ArrayList<>();

        points.add(new GeoPoint(25.05300d, 121.47300d));
        points.add(new GeoPoint(25.05100d, 121.47100d));
        points.add(new GeoPoint(25.05100d, 121.47300d));

        Polygon polygon = new Polygon(mapView);    //see note below
        polygon.getFillPaint().setColor(Color.argb(75, 255,0,0));
        points.add(points.get(0));    //forces the loop to close
        polygon.setPoints(points);
        polygon.setTitle("A sample polygon");
        polygon.setSubDescription(Polygon.class.getCanonicalName());

        List<GeoPoint> holesData = new ArrayList<>();
        GeoPoint hole1 = new GeoPoint(25.05100d,121.47200d);
        holesData.add(hole1);
        GeoPoint hole2 = hole1.destinationPoint(50, 0);//North
        holesData.add(hole2);
        GeoPoint hole3 = hole1.destinationPoint(50, 90);//East
        holesData.add(hole3);
        List<List<GeoPoint>> holes = new ArrayList<>();
        holes.add(holesData);
        polygon.setHoles(holes);

        mapView.getOverlayManager().add(polygon);
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
}