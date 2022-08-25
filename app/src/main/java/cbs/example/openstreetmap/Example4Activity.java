package cbs.example.openstreetmap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint;

import java.util.ArrayList;
import java.util.List;

import cbs.example.openstreetmap.tool.APIMethod;

public class Example4Activity extends AppCompatActivity {
    private MapView mapView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        mapView = findViewById(R.id.mapView);
        textView = findViewById(R.id.textView5);

        GeoPoint startPoint = new GeoPoint(25.05397, 121.47309);
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();

        APIMethod apiMethod = new APIMethod(mapView);
        apiMethod.setMapView();
        apiMethod.mapController(startPoint);
        apiMethod.rotationGestureOverlay(getApplicationContext());
        apiMethod.latLonGridlineOverlay2();
        apiMethod.myLocationNewOverlay(getApplicationContext(), textView);
        apiMethod.scaleBarOverlay(displayMetrics);
        apiMethod.minimapOverlay(getApplicationContext(), displayMetrics);

        List<GeoPoint> geoPoints = new ArrayList<>();

        geoPoints.add(new LabelledGeoPoint(25.05400d, 121.47400d, "1"));
        geoPoints.add(new LabelledGeoPoint(25.05200d, 121.47200d, "2"));

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

        Polygon polygon = new Polygon();    //see note below
        polygon.setFillColor(Color.argb(75, 255,0,0));
        points.add(points.get(0));    //forces the loop to close
        polygon.setPoints(points);
        polygon.setTitle("A sample polygon");

        List<List<GeoPoint>> holes = new ArrayList<>();
        holes.add(points);
        polygon.setHoles(holes);

        mapView.getOverlayManager().add(polygon);
    }
}