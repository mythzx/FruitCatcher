package com.mygdx.game.android;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mystic.game.GlobalController;

public class MainActivity extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.lblValue);
        textView.setText(Integer.toString(AndroidLauncher.getScore()));
        Log.d("test",Integer.toString(AndroidLauncher.c.score));
    }
}
