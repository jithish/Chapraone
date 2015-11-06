package com.ceino.chaperonandroid.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceino.chaperonandroid.MainActivity;
import com.ceino.chaperonandroid.R;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.File;

import javax.inject.Inject;

import application.ChaperOnApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import connection.ChaperOnConnection;
import events.SignUpEvent;
import utils.ToastHandler;

/**
 * Created by ceino on 15/10/15.
 */
public class LicenseActivity extends FragmentActivity implements View.OnClickListener {

    String phone,email,password,firstname,username,imagePath,street,apartment,city,postal,country,lastname,description;
    double lattitude,longtitude;
    int rideType;
    ToastHandler toastHandler;
    public Bitmap bitmap;
    public String image_path = null;

    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;

    @Inject
    Bus bus;

    @InjectView(R.id.back_arrow)ImageView imgBack;
    @InjectView(R.id.empty_seats_ed)EditText emptySeats;
    @InjectView(R.id.driver_license)ImageView imgLicense;
    @InjectView(R.id.checkbox)CheckBox checkTerms;
    @InjectView(R.id.save_txv)TextView saveAllbtn;
    @InjectView(R.id.progbar)ProgressBar progressBar;
    @InjectView(R.id.license_actionbar)RelativeLayout customActionbar;
    @InjectView(R.id.parent)LinearLayout parentLayout;
    @InjectView(R.id.frame_layout)FrameLayout framelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChaperOnApplication.get(this).inject(this);
        chaperOnConnection = new ChaperOnConnection(this);
        overridePendingTransition(R.anim.animation, R.anim.animation2);
        setContentView(R.layout.activity_licenseverify);
        ButterKnife.inject(this);
        toastHandler = new ToastHandler(this);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
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


        Drawable drawable = getResources().getDrawable(R.drawable.hash);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.4),
                (int) (drawable.getIntrinsicHeight() * 0.4));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, 1f, 1f);

        emptySeats.setCompoundDrawablePadding(15);
        emptySeats.setCompoundDrawables(sd.getDrawable(), null, null, null);

        imgBack.setOnClickListener(this);
        imgLicense.setOnClickListener(this);
        saveAllbtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                finish();
                hideSoftKeyboard(this);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
            case R.id.driver_license:
                selectImage();
                break;
            case R.id.save_txv:
                hideSoftKeyboard(this);
                if(isNull(emptySeats))
                {
                    emptySeats.requestFocus();
                    emptySeats.setError("Enter Available Seats");
                }else if(image_path == null)
                {
                    imgLicense.requestFocus();
                    showAlertDialog(this, "Image Not Found", "License Image Required", false);
                }else if(!checkTerms.isChecked()){
                    checkTerms.requestFocus();
                    showAlertDialog(this, "","Please Accept the terms", false);
                }else {
                    /*Toast.makeText(LicenseActivity.this, "Getting datas OKKKK", Toast.LENGTH_SHORT).show();
                    System.out.println("DatasInOrder:" + phone + "-" + email + "-" + password + "-" + firstname + "-" + username + "-" + imagePath + "-" + street + "-" + apartment + "-"
                            + city + "-" + postal + "-" + country + "-" + lastname + "-" + description + "-" + rideType + "-" + lattitude + "-" + longtitude + "-"
                            + emptySeats.getText().toString() + "-" + image_path);*/
                    progressBar.setVisibility(View.VISIBLE);
                    chaperOnConnection = new ChaperOnConnection(this);
                    chaperOnConnection.startActionRegister(email, password, firstname, username, imagePath, street, apartment, city, postal, country, lastname, description, rideType,
                            lattitude, longtitude, phone, availableSeatsCount(emptySeats.getText().toString()), image_path);

                }
                break;
        }
    }
    @Subscribe
    public void onSignUpEvent(SignUpEvent event) {
        if(event.isSuccess){
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("addchild", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            toastHandler.mustShowToast("Registration Success ", Toast.LENGTH_LONG);
        }else {
            progressBar.setVisibility(View.GONE);
            toastHandler.mustShowToast("Something Went Wrong...", Toast.LENGTH_SHORT);
        }
    }

    private int availableSeatsCount(String s) {
        int count = Integer.parseInt(s);
        return count;
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
    public void showAlertDialog(Activity context, String title, String message, Boolean status) {
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
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;

                        break;
                    }
                }
                image_path = String.valueOf(f);
                try {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    // bitmap = Bitmap.createScaledBitmap(bitmap, 70, 70, true);
                    imgLicense.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                image_path = picturePath;
                c.close();
                bitmap = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath + "");
                imgLicense.setImageBitmap(bitmap);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
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
