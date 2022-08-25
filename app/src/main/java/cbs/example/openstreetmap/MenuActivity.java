package cbs.example.openstreetmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    private Button button, button2, button3, button4, button5;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        textView = findViewById(R.id.textView3);

        button.setOnLongClickListener(this);
        button2.setOnLongClickListener(this);
        button3.setOnLongClickListener(this);
        button4.setOnLongClickListener(this);
        button5.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button :{
                startActivity(new Intent(getApplicationContext(), BasicActivity.class));
                break;
            }
            case R.id.button2 :{
                startActivity(new Intent(getApplicationContext(), Example1Activity.class));
                break;
            }
            case R.id.button3 :{
                startActivity(new Intent(getApplicationContext(), Example2Activity.class));
                break;
            }
            case R.id.button4 :{
                startActivity(new Intent(getApplicationContext(), Example3Activity.class));
                break;
            }
            case R.id.button5 :{
                startActivity(new Intent(getApplicationContext(), Example4Activity.class));
                break;
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()){
            case R.id.button :{
                textView.setText("Only default setting and a map view used Open Street Map API.");
                break;
            }
            case R.id.button2 :{
                textView.setText("Add more setting on Map View and Map Controller. let it look like a globe.\n" +
                        "● Use 'RotationGestureOverlay' can make map rotate.\n" +
                        "● 'Geo Point' can create a Geographic coordinate system and set start point.\n" +
                        "● 'Marker' can create a mark on the map.\n" +
                        "● 'Map Listener' can watch user moving and zooming on the map.");
                break;
            }
            case R.id.button3 :{
                textView.setText("Add 4 Overlays from Open Street Map API\n" +
                        "● 'LatLonGridlineOverlay2' can show the grid line on the map. Use 'setFontSizeDp' to control text size, set zero can not display it\n" +
                        "● 'MyLocationNewOverlay' can show the user position on the map. Need some declare Permission in Manifest.xml\n" +
                        "● 'DisplayMetrics' can show the map scale.\n" +
                        "● 'MinimapOverlay' can show the mini map. It can change map layer. API version over 6.0.0 can enable with main map.");
                break;
            }
            case R.id.button4 :{
                textView.setText("Add 1 overlay, Overlay Item and more Marker setting.\n" +
                        "● 'OverlayItem' and 'ItemizedOverlayWithFocus' can mark point on the map like marker. But it can set click listener to use other android function.\n" +
                        "● 'simpleFastPointOverlay' can mark a dot on the map. Also cam set on click listener");
            }
        }
        return true;
    }
}