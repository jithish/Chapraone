package com.ceino.chaperonandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ceino.chaperonandroid.R;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import application.ChaperOnApplication;
import connection.ChaperOnConnection;
import events.PhoneVerifyEvent;
import utils.ToastHandler;

/**
 * Created by ceino on 21/9/15.
 */
public class PhoneActivity extends Activity implements View.OnClickListener {

    public ImageView imgback;
    public EditText phoneNo,otpNo;
    public TextView submitBtn,submitotpBtn;
    public LinearLayout phoneFrame,verifyFrame;
    ProgressBar progressBar;
    String email,password,firstname,username,imagePath,street,apartment,city,postal,country,lastname,description;
    double lattitude,longtitude;
    int rideType;
    ToastHandler toastHandler;

    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;

    @Inject
    Bus bus;
    public static Activity pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChaperOnApplication.get(this).inject(this);
        chaperOnConnection = new ChaperOnConnection(this);
        overridePendingTransition(R.anim.animation, R.anim.animation2);
        setContentView(R.layout.activity_phoneverify);
        pa = this;
//        ChaperOnBus.getInstance().register(this);
        toastHandler = new ToastHandler(this);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("pwd");
        firstname = intent.getStringExtra("fname");
        username = intent.getStringExtra("uname");
        imagePath = intent.getStringExtra("imgpath");
        street = intent.getStringExtra("street");
        apartment = intent.getStringExtra("aptno");
        city = intent.getStringExtra("city");
        postal = intent.getStringExtra("postal");
        country = intent.getStringExtra("country");
        lastname = intent.getStringExtra("lname");
        description = intent.getStringExtra("desc");
        rideType = intent.getIntExtra("ridetype", 0);
        lattitude = intent.getDoubleExtra("lat", 0);
        longtitude = intent.getDoubleExtra("long",0);
        imgback = (ImageView) findViewById(R.id.back_arrow);
        phoneNo = (EditText) findViewById(R.id.phone_txt);
        otpNo = (EditText) findViewById(R.id.otp_txt);
        submitBtn = (TextView) findViewById(R.id.submit_txv);
        submitotpBtn = (TextView) findViewById(R.id.submitotp_txv);
        phoneFrame = (LinearLayout) findViewById(R.id.phone_frame);
        verifyFrame = (LinearLayout) findViewById(R.id.verify_frame);
        progressBar = (ProgressBar) findViewById(R.id.progbar);

        Drawable drawable = getResources().getDrawable(R.drawable.ccode);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.4),
                (int) (drawable.getIntrinsicHeight() * 0.4));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, 1f, 1f);

        phoneNo.setCompoundDrawablePadding(15);
        phoneNo.setCompoundDrawables(sd.getDrawable(), null, null, null);

        imgback.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        submitotpBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                if (phoneFrame.getVisibility() == View.GONE){
                    phoneFrame.setVisibility(View.VISIBLE);
                    verifyFrame.setVisibility(View.GONE);
                }else {
                    finish();
                    hideSoftKeyboard(this);
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                }


                break;
            case R.id.submit_txv:
                if(isNull(phoneNo))
                {
                    phoneNo.requestFocus();
                    phoneNo.setError("Enter Phone #");
                }else {
                    hideSoftKeyboard(this);
                    phoneFrame.setVisibility(View.GONE);
                    verifyFrame.setVisibility(View.VISIBLE);
                    String phoneno = phoneNo.getText().toString();

                    chaperOnConnection.handleActionPhoneGenCode(phoneno);
                }
                break;
            case R.id.submitotp_txv:
                if(isNull(otpNo))
                {
                    otpNo.requestFocus();
                    otpNo.setError("OTP is Must");
                }else {
                    hideSoftKeyboard(this);
                    String phoneno = phoneNo.getText().toString();
                    String otpno = otpNo.getText().toString();
                    /*chaperOnConnection.handleActionPhoneVerCode(phoneno, otpno,email, password,firstname, username,imagePath,
                            street,apartment,city,postal,country,lastname,description,rideType,lattitude,longtitude);*/
                    chaperOnConnection.handleActionPhoneVerCode(phoneno, otpno);
                    progressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
    @Subscribe
    public void onPhoneVerifyEvent(PhoneVerifyEvent event){
        if(event.isSuccess){
            progressBar.setVisibility(View.GONE);
            Intent intent3 = new Intent(this, LicenseActivity.class);
            intent3.putExtra("phone",phoneNo.getText().toString());
            intent3.putExtra("email", email);
            intent3.putExtra("pwd", password);
            intent3.putExtra("fname", firstname);
            intent3.putExtra("uname", username);
            intent3.putExtra("imgpath", imagePath);
            intent3.putExtra("street", street);
            intent3.putExtra("aptno", apartment);
            intent3.putExtra("city", city);
            intent3.putExtra("postal", postal);
            intent3.putExtra("country", country);
            intent3.putExtra("lname", lastname);
            intent3.putExtra("desc", description);
            intent3.putExtra("ridetype", rideType);
            intent3.putExtra("lat",lattitude);
            intent3.putExtra("long",longtitude);
            startActivity(intent3);
        }else {
            progressBar.setVisibility(View.GONE);
            toastHandler.mustShowToast("Incorrect OTP", Toast.LENGTH_SHORT);
        }
    }
    /*@Subscribe
    public void onSignUpEvent(SignUpEvent event) {
        if(event.isSuccess){
            toastHandler.mustShowToast("Registration Success ", Toast.LENGTH_SHORT);
            this.finish();
            RegisterActivity.fa.finish();
            progressBar.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            toastHandler.mustShowToast("Incorrect OTP", Toast.LENGTH_SHORT);
        }
    }*/

    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    Boolean isNull(EditText editText)
    {
        if(editText!=null)
        {
            if(editText.getText()==null ||editText.getText().toString().trim().equalsIgnoreCase(""))
            {
                return true;
            }
            return false;
        }
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ChaperOnBus.getInstance().unregister(this);
    }
    @Override
    public void onResume() {
        bus.register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        bus.unregister(this);
        super.onPause();
    }

}
