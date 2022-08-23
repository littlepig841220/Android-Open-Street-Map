package cbs.example.openstreetmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button :{
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            }
            case R.id.button2 :{
                startActivity(new Intent(getApplicationContext(), Example1Activity.class));
                break;
            }
        }
    }
}