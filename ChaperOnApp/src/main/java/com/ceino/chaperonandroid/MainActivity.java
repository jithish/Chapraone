package com.ceino.chaperonandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ceino.chaperonandroid.activities.SplashActivity2;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Date;

import javax.inject.Inject;

import application.ChaperOnApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import events.IntialCloseFragment;
import events.SignInEvent;
import fragments.InitialAddChildFragment;
import fragments.LoginFragment;
import fragments.MainFragment;
import utils.ConnectionDetector;
import utils.ToastHandler;

public class MainActivity extends FragmentActivity {

    Date pausedTime;
    @Inject
    Bus bus;
    @Inject
    ChaperOnApplication chaperOnApplication;

    @InjectView(R.id.progressbar)
    ProgressBar progressBar;

    boolean firstTime = true;
    boolean isLogout = false;
    boolean onPaused = false;

    @InjectView(R.id.frame_layout)
    FrameLayout frameLayout;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    SharedPreferences.Editor editor;

    ToastHandler toastHandler;

    boolean isAddChild = false;

    // flag for Internet connection status
    public Boolean isInternetPresent = false;
    // Connection detector class
    public ConnectionDetector cd;
    int SDK_INT = android.os.Build.VERSION.SDK_INT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        setContentView(R.layout.activity_main);
//        CheckInternetConnection();
        ButterKnife.inject(this);
        ButterKnife.inject(this);
        ButterKnife.inject(this);
        ChaperOnApplication.get(this).inject(this);
        toastHandler = new ToastHandler(this);
        Intent intent = getIntent();
        isAddChild = intent.getBooleanExtra("addchild",false);
        if (savedInstanceState == null) {
            setFragment(false, true);
            /*if (chaperOnApplication.getChaperOnAccessUserID() == null) {
                startActivity(new Intent(this, SplashActivity2.class));
            }*/
            if(!isAddChild){
                startActivity(new Intent(this, SplashActivity2.class));
            }

        }

    }

    public void setFragment(boolean isRefresh, boolean splash) {
        try {
            if (chaperOnApplication.getChaperOnAccessUserID() == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, LoginFragment.create()).commit();
                isLogout = true;

            } else {
                if(isAddChild){
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit).replace(R.id.frame_layout, new InitialAddChildFragment()).commit();
                }else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, MainFragment.create()).commit();
                }
                isLogout = false;
            }

        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onInitialCloseFramgment(IntialCloseFragment event) {
        if(event.message.equals("AddChildFrag")){
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left).replace(R.id.frame_layout, MainFragment.create()).commit();
        }
    }
    @Subscribe
    public void onSingInEvent(SignInEvent event) {
        if (event.isSuccess) {
            toastHandler.mustShowToast("Signin Success ", Toast.LENGTH_LONG);
            setFragment(false, false);
            isLogout = false;
        } else {
            toastHandler.mustShowToast("Signin failed ", Toast.LENGTH_LONG);
        }
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            if (doubleBackToExitPressedOnce) {
                fm.popBackStack();
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press one more time to exit app",
                    Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    doubleBackToExitPressedOnce = false;
                }
            }, 3000);

        }
    }

    @Override
    protected void onPause() {

        super.onPause();
//        pausedTime = Calendar.getInstance().getTime();
        onPaused = true;
        bus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Main activity", "onResume");
        bus.register(this);

//		if(onPaused)
        /*if (pausedTime != null)

        {
            long diff = Calendar.getInstance().getTime().getTime() - pausedTime.getTime();
            long diffSeconds = diff / (1000);
            long diffMinutes = diff / (60 * 1000);
            long diffHours = diffMinutes / 60;
            if (diffSeconds > (300))//5 minutes
            {
                setFragment(false, false);
            }
        }*/


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void CheckInternetConnection() {
        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        // check for Internet status
        if (!isInternetPresent) {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
    }
    public void showAlertDialog(MainActivity context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.ic_launcher : R.drawable.ic_launcher);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void showGPSDialog(MainActivity context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("GPS not found");  // GPS not found
        builder.setMessage("You Need to Enable"); // Want to enable?
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();
        return;

    }
}