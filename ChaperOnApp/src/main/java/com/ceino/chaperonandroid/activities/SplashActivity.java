package com.ceino.chaperonandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ceino.chaperonandroid.MainActivity;
import com.ceino.chaperonandroid.R;
import com.squareup.otto.Subscribe;

import events.SplashEvent;
import services.ChaperOnService;
import utils.ChaperOnBus;

/**
 * Created by ceino on 27/8/15.
 */
public class SplashActivity extends Activity {

    int count=5;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChaperOnBus.getInstance().register(this);
        ChaperOnService.startActionSplash(getApplicationContext());
        // Splash screen view
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChaperOnBus.getInstance().unregister(this);
    }


    @Subscribe
    public void onSplashEvent(SplashEvent event) {
        count--;
        if(count<=5)
        {
            count=5;
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        count=5;
        finish();
    }
}
