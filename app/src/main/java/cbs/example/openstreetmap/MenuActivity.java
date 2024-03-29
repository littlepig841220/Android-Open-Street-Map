package cbs.example.openstreetmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    //Component declaration
    private Button button, button2, button3, button4, button5, button6;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Component find ID on layout xml.
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        textView = findViewById(R.id.textView3);

        //set listener
        button.setOnLongClickListener(this);
        button2.setOnLongClickListener(this);
        button3.setOnLongClickListener(this);
        button4.setOnLongClickListener(this);
        button5.setOnLongClickListener(this);
        button6.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getTag().toString()){
            case "button" :{
                startActivity(new Intent(getApplicationContext(), BasicActivity.class));
                break;
            }
            case "button2" :{
                startActivity(new Intent(getApplicationContext(), Example1Activity.class));
                break;
            }
            case "button3" :{
                startActivity(new Intent(getApplicationContext(), Example2Activity.class));
                break;
            }
            case "button4" :{
                startActivity(new Intent(getApplicationContext(), Example3Activity.class));
                break;
            }
            case "button5" :{
                startActivity(new Intent(getApplicationContext(), Example4Activity.class));
                break;
            }
            case "button6" :{
                startActivity(new Intent(getApplicationContext(), RecordPathActivity.class));
                break;
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getTag().toString()){
            case "button" :{
                textView.setText("Only default setting and a map view used Open Street Map API.");
                break;
            }
            case "button2" :{
                textView.setText("Add more setting on Map View and Map Controller. let it look like a globe.\n" +
                        "● Use 'RotationGestureOverlay' can make map rotate.\n" +
                        "● 'Geo Point' can create a Geographic coordinate system and set start point.\n" +
                        "● 'Marker' can create a mark on the map. Click on map can set up new position. Long press to drag it.\n" +
                        "● 'Map Listener' can watch user moving and zooming on the map.\n" +
                        "● 'MapEventsReceiver' can pic up Geo Point on the map");
                break;
            }
            case "button3" :{
                textView.setText("Add 4 Overlays from Open Street Map API\n" +
                        "● 'LatLonGridlineOverlay2' can show the grid line on the map. Use 'setFontSizeDp' to control text size, set zero can not display it\n" +
                        "● 'MyLocationNewOverlay' can show the user position on the map. Need some declare Permission in Manifest.xml\n" +
                        "● 'DisplayMetrics' can show the map scale.\n" +
                        "● 'MinimapOverlay' can show the mini map. It can change map layer. API version over 6.0.0 can enable with main map.");
                break;
            }
            case "button4" :{
                textView.setText("Add 1 overlay, Overlay Item and more Marker setting.\n" +
                        "● Custom Marker\n" +
                        "● 'OverlayItem' and 'ItemizedOverlayWithFocus' can mark point on the map like marker. But it can set click and long click listener to use other android function.\n" +
                        "● 'ItemizedOverlayWithFocus' is Deprecated, 'ItemizedIconOverlay' may be can instead.\n" +
                        "● 'simpleFastPointOverlay' can mark a dot on the map. Also cam set on click listener");
                break;
            }
            case "button5" :{
                textView.setText("Add 2 drawing method and their setting method\n" +
                        "● 'Polyline' can draw a line on the map\n" +
                        "● 'Polygon' can draw an area on the map\n" +
                        "● 'setHoles' in the 'Polygon' can make area have a empty area");
                break;
            }
            case "button6" :{
                textView.setText("Include all method to make a user GPS recorder. Contact location on the map.");
                break;
            }
        }
        return true;
    }
}

//https://stackoverflow.com/questions/22250423/osmbonuspack-show-name-of-marker-on-the-map
//https://stackoverflow.com/questions/51689382/set-icon-on-a-fast-overlay-in-osmdroid