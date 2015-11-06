package com.ceino.chaperonandroid.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.ceino.chaperonandroid.R;

/**
 * Created by ceino on 16/10/15.
 */
public class SplashActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash2);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
    }


    @Override
    public void onBackPressed(){
        finish();
    }



    public static class PlaceholderFragment extends Fragment {


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.splash_layout,
                    container, false);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    if (getActivity() != null) {
                        getActivity().finish();
                    }

                }
            }, 2000);
            return rootView;
        }
    }

}
