package com.ceino.chaperonandroid.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceino.chaperonandroid.R;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import application.ChaperOnApplication;
import connection.ChaperOnConnection;
import data.AllUserList;
import events.GotAllUserEvent;
import utils.ChaperOnBus;
import utils.ConnectionDetector;
import utils.ToastHandler;

/**
 * Created by ceino on 8/9/15.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    ImageView imgback;
    public EditText email,pwd,fname,lname,bbiograph;
    public EditText street,apt_no,city,postal,country;
    public CheckBox checkbox1,checkbox2;
    public ImageView pofilePic;
    public TextView btnRegister;
    public Bitmap bitmap;
    public String image_path = null;
    public String emailmain = null;
    public static ArrayList<AllUserList> userLists = new ArrayList<AllUserList>();
    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;

    @Inject
    Bus bus;

    ToastHandler toastHandler;
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChaperOnApplication.get(this).inject(this);
        setContentView(R.layout.activity_register);
//        ChaperOnBus.getInstance().register(this);
        fa = this;
        overridePendingTransition(R.anim.animation, R.anim.animation2);
        turnGPSOn(); // method to turn on the GPS if its in off state.
        getMyCurrentLocation();
        toastHandler = new ToastHandler(this);
        imgback = (ImageView) findViewById(R.id.back_arrow);
        email = (EditText) findViewById(R.id.email_txt);
        pwd = (EditText) findViewById(R.id.pwd_txt);
        fname = (EditText) findViewById(R.id.fname_txt);
        lname = (EditText) findViewById(R.id.lname_txt);
        bbiograph = (EditText) findViewById(R.id.bbio_txt);
        pofilePic = (ImageView) findViewById(R.id.profile_pic);
        street = (EditText) findViewById(R.id.street_txt);
        apt_no = (EditText) findViewById(R.id.apt_txt);
        city = (EditText) findViewById(R.id.city_txt);
        postal = (EditText) findViewById(R.id.zip_txt);
        country = (EditText) findViewById(R.id.state_txt);
        checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkbox2 = (CheckBox) findViewById(R.id.checkbox2);

        btnRegister = (TextView) findViewById(R.id.next_txv);

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {


                if (hasFocus) {
                    // If view having focus.
                } else {
                    isEmailAlrdy();
                }
            }
        });


        imgback.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        pofilePic.setOnClickListener(this);

        /*if (!isInternetPresent) {
            showAlertDialog(this, "No Internet Connection",
                    "Please Check Your internet connection.", false);
        }else {*/
        street.setText(Street);
        apt_no.setText(streetno);
        city.setText(CityName);
        postal.setText(zip);
        country.setText(CountryName);
//        }
    }

    public void isEmailAlrdy() {
        emailmain = email.getText().toString();

        for(int i=0;i<userLists.size();i++){
            if(emailmain != null){
                String regExpn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

                CharSequence inputStr = emailmain;

                Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(inputStr);

                if(matcher.matches()){

                }
                else
                    email.setError("Enter valid email");

            }
            if(emailmain.equals(userLists.get(i).getEmail().toString())){
                email.setError("Email already exist");
            }
        }

    }

    @Subscribe
    public void onGotAllUserEvent(GotAllUserEvent event) {
        userLists = event.userList;

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
            case R.id.next_txv:

                if(isNull(email))
                {
                    email.requestFocus();
                    email.setError("Enter email");
                }else if(isNull(pwd))
                {
                    pwd.requestFocus();
                    pwd.setError("Enter password");
                }else if(isNull(fname))
                {
                    fname.requestFocus();
                    fname.setError("Enter First Name");
                }else if(isNull(lname))
                {
                    lname.requestFocus();
                    lname.setError("Enter Last Name");
                }else if(image_path == null)
                {
                    showAlertDialog(this, "Image Not Found","Everyone likes picture", false);
                }else {

                    String password = pwd.getText().toString();
                    String firstname = fname.getText().toString();
                    String uname = firstname;
                    String mlname = lname.getText().toString();
                    String mDescription = bbiograph.getText().toString();
                    String mstreet = street.getText().toString();
                    String mapt_no = apt_no.getText().toString();
                    String mcity = city.getText().toString();
                    String mpostal = postal.getText().toString();
                    String mcountry = country.getText().toString();
                    int ride_type=0;
                    if(checkbox1.isChecked()){
                        ride_type = 1;
                    }
                    if(checkbox2.isChecked()){
                        ride_type = 0;
                    }
                    if(checkbox1.isChecked() && checkbox2.isChecked()){
                        ride_type = 2;
                    }

//                        String mrideType = String.valueOf(ride_type);
                    Intent intent3 = new Intent(this, PhoneActivity.class);
                    intent3.putExtra("email", emailmain);
                    intent3.putExtra("pwd", password);
                    intent3.putExtra("fname", firstname);
                    intent3.putExtra("uname", uname);
                    intent3.putExtra("imgpath", image_path);
                    intent3.putExtra("street", mstreet);
                    intent3.putExtra("aptno", mapt_no);
                    intent3.putExtra("city", mcity);
                    intent3.putExtra("postal", mpostal);
                    intent3.putExtra("country", mcountry);
                    intent3.putExtra("lname", mlname);
                    intent3.putExtra("desc", mDescription);
                    intent3.putExtra("ridetype", ride_type);
                    intent3.putExtra("lat",MyLat);
                    intent3.putExtra("long",MyLong);
                    startActivity(intent3);
                    /*ChaperOnService.startActionRegister(this, email1, password, firstname, uname, image_path,
                            mstreet, mapt_no, mcity, mpostal, mcountry, mlname, mDescription, ride_type);*/

                }

                break;
            case R.id.profile_pic:
                selectImage();
                break;
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
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
    public void onResume() {
        bus.register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        bus.unregister(this);
        super.onPause();
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

    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
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
                    pofilePic.setImageBitmap(bitmap);

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
                pofilePic.setImageBitmap(bitmap);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ChaperOnBus.getInstance().unregister(this);
    }

    /** Method to turn on GPS **/
    public void turnGPSOn(){
        try
        {

            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


            if(!provider.contains("gps")){ //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
        }
        catch (Exception e) {

        }
    }
    void getMyCurrentLocation() {


        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locListener = new MyLocationListener();


        try{gps_enabled=locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}
        try{network_enabled=locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}

        //don't start listeners if no provider is enabled
        //if(!gps_enabled && !network_enabled)
        //return false;

        if(gps_enabled){
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);

        }


        if(gps_enabled){
            location=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        }


        if(network_enabled && location==null){
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);

        }


        if(network_enabled && location==null)    {
            location=locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }

        if (location != null) {

            MyLat = location.getLatitude();
            MyLong = location.getLongitude();


        } else {
            Location loc= getLastKnownLocation(this);
            if (loc != null) {

                MyLat = loc.getLatitude();
                MyLong = loc.getLongitude();


            }
        }
        locManager.removeUpdates(locListener); // removes the periodic updates from location listener to //avoid battery drainage. If you want to get location at the periodic intervals call this method using //pending intent.

        try
        {
// Getting address from found locations.
            Geocoder geocoder;

            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(MyLat, MyLong, 1);

            StateName= addresses.get(0).getAdminArea();
            CityName = addresses.get(0).getLocality();
            CountryName = addresses.get(0).getCountryName();
            streetno = addresses.get(0).getAddressLine(0);
            Street =  addresses.get(0).getAddressLine(1);
            PostalCode = addresses.get(0).getAddressLine(2);

            // you can get more details other than this . like country code, state code, etc.
            tokens = PostalCode.split(" ");
            zip = tokens[3];
            System.out.println(tokens[3]);
            System.out.println("StateName: " + StateName);
            System.out.println("CityName: " + CityName);
            System.out.println("CountryName: " + CountryName);
            System.out.println("StreetAddress:"+Street);
            System.out.println("Postal:"+zip);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        /*textView2.setText(""+MyLat);
        textView3.setText(""+MyLong);
        textView1.setText("Apt#\t:" + streetno +
                "\nStreetName\t:" + Street +
                "\nStateName\t:" + StateName +
                "\nCityName\t:" + CityName +
                "\nCountryName\t:" + CountryName +
                "\nPostalCode\t:"+tokens[3]);*/
    }

    // Location listener class. to get location.
    public class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            if (location != null) {
            }
        }

        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }

    private boolean gps_enabled=false;
    private boolean network_enabled=false;
    Location location;

    Double MyLat, MyLong;
    String CityName="";
    String StateName="";
    String CountryName="";
    String Street="";
    String PostalCode="";
    String streetno="";
    String zip="";
    String[] tokens= null;

// below method to get the last remembered location. because we don't get locations all the times .At some instances we are unable to get the location from GPS. so at that moment it will show us the last stored location.

    public static Location getLastKnownLocation(Context context)
    {
        Location location = null;
        LocationManager locationmanager = (LocationManager)context.getSystemService("location");
        List list = locationmanager.getAllProviders();
        boolean i = false;
        Iterator iterator = list.iterator();
        do
        {
            //System.out.println("---------------------------------------------------------------------");
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            //if(i != 0 && !locationmanager.isProviderEnabled(s))
            if(i != false && !locationmanager.isProviderEnabled(s))
                continue;
            // System.out.println("provider ===> "+s);
            Location location1 = locationmanager.getLastKnownLocation(s);
            if(location1 == null)
                continue;
            if(location != null)
            {
                //System.out.println("location ===> "+location);
                //System.out.println("location1 ===> "+location);
                float f = location.getAccuracy();
                float f1 = location1.getAccuracy();
                if(f >= f1)
                {
                    long l = location1.getTime();
                    long l1 = location.getTime();
                    if(l - l1 <= 600000L)
                        continue;
                }
            }
            location = location1;
            // System.out.println("location  out ===> "+location);
            //System.out.println("location1 out===> "+location);
            i = locationmanager.isProviderEnabled(s);
            // System.out.println("---------------------------------------------------------------------");
        } while(true);
        return location;
    }
}
