package com.ceino.chaperonandroid.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceino.chaperonandroid.R;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import application.ChaperOnApplication;
import butterknife.ButterKnife;
import connection.ChaperOnConnection;
import events.ForgetPwdEvent;
import utils.ToastHandler;

/**
 * Created by ceino on 7/9/15.
 */
public class ForgotpwdActivity extends Activity implements View.OnClickListener {
    ImageView imgback;
    TextView forgotBtn;
    EditText forgtemail;
    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;

    @Inject
    Bus bus;

    ToastHandler toastHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChaperOnApplication.get(this).inject(this);
        ButterKnife.inject(this);
        setContentView(R.layout.activity_forget);
        toastHandler = new ToastHandler(this);
        overridePendingTransition(R.anim.animation, R.anim.animation2);
        imgback = (ImageView) findViewById(R.id.back_arrow);
        forgotBtn = (TextView) findViewById(R.id.submit_txv);
        forgtemail = (EditText) findViewById(R.id.forgtemail_act);

        imgback.setOnClickListener(this);
        forgotBtn.setOnClickListener(this);
    }
    @Override
    public void onBackPressed() {
        try {
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow:
                finish();
                hideSoftKeyboard(this);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
            case R.id.submit_txv:
                if(isNull(forgtemail))
                {   forgtemail.requestFocus();
                    forgtemail.setError("Enter email");
                }else {
                    hideSoftKeyboard(this);
                    String forgetemail = forgtemail.getText().toString();
                    if(chaperOnApplication.isNetworkAvailableWithMessage(this)){
                        chaperOnConnection = new ChaperOnConnection(this);
                        chaperOnConnection.handleActionForgetpwd(this,forgetemail);
                    }
//                ChaperOnService.startActionForgetPwdIN(this,forgetemail);
                }

                break;
        }
    }
    @Subscribe
    public void onForgetPwdEvent(ForgetPwdEvent event) {
        if(event.isSuccess == true){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setCancelable(false);
            alert.setTitle("Success");
            alert.setMessage("An e-mail instructions for resetting password has been sent.");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                }
            });
            alert.show();
//            toastHandler.mustShowToast("ForgetPassword redirected...", Toast.LENGTH_SHORT);
        }else {
            toastHandler.mustShowToast("Cause Error ", Toast.LENGTH_SHORT);
        }
    }

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
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
