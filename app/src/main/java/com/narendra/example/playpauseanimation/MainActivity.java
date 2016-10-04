package com.narendra.example.playpauseanimation;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity implements MusicPlayerLayout.GettingClickListner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void shuffelButtonclick() {

    }

    @Override
    public void rotationButtonclick() {

    }

    @Override
    public void previousButtonclick() {

    }

    @Override
    public void nextButtonclick() {

    }

    @Override
    public void playpauseButtonclick() {

    }
}
