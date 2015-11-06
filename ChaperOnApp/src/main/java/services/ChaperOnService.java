package services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data.AllUserList;
import data.CurrUserList;
import events.ForgetPwdEvent;
import events.GotAllUserEvent;
import events.GotCurrentUserEvent;
import events.OnBoardCloseEvent;
import events.SignInEvent;
import events.SignOutEvent;
import events.SignUpEvent;
import events.SplashEvent;
import utils.ChaperOnBus;
import utils.PrefManager;

/**
 * Created by ceino on 26/8/15.
 */
@SuppressWarnings("ALL")
public class ChaperOnService extends BaseIntentService {

    final String LOGIN_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/login";
    final String REGISTER_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/register";
    final String USER_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/user";
    final String FORGOT_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/forgotpassword/";
    final String PHONE_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/phoneverification/generatecode";
    final String PHONE_VER_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/phoneverification/verify";

    private static final String ACTION_SPLASH = "com.ceino.chaperonandroid.services.action.splash";
    private static final String ACTION_CLOSEONBOARD = "com.ceino.chaperonandroid.services.action.closeonboard";

    private static final String ACTION_SIGNIN = "com.ceino.chaperonandroid.services.action.signin";
    private static final String ACTION_REGISTER = "com.ceino.chaperonandroid.services.action.register";
    private static final String ACTION_SIGNOUT = "com.ceino.chaperonandroid.services.action.signout";
    private static final String ACTION_FORGETPWD = "com.ceino.chaperonandroid.services.action.forgetpwd";
    private static final String ACTION_USER = "com.ceino.chaperonandroid.services.action.user";
    private static final String ACTION_PHONE_GENOTP = "com.ceino.chaperonandroid.services.action.phonegencode";
    private static final String ACTION_PHONE_VEROTP = "com.ceino.chaperonandroid.services.action.phoneverifycode";

    private static final String ACTION_USERDETAIL = "com.ceino.chaperonandroid.services.action.userdetail";

    private static final String EXTRA_PARAM1 = "com.ceino.chaperonandroid.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.ceino.chaperonandroid.services.extra.PARAM2";
    private static final String EXTRA_PARAM3 = "com.ceino.chaperonandroid.services.extra.PARAM3";
    private static final String EXTRA_PARAM4 = "com.ceino.chaperonandroid.services.extra.PARAM4";
    private static final String EXTRA_PARAM5 = "com.ceino.chaperonandroid.services.extra.PARAM5";
    private static final String EXTRA_PARAM6 = "com.ceino.chaperonandroid.services.extra.PARAM6";
    private static final String EXTRA_PARAM7 = "com.ceino.chaperonandroid.services.extra.PARAM7";
    private static final String EXTRA_PARAM8 = "com.ceino.chaperonandroid.services.extra.PARAM8";
    private static final String EXTRA_PARAM9 = "com.ceino.chaperonandroid.services.extra.PARAM9";
    private static final String EXTRA_PARAM10 = "com.ceino.chaperonandroid.services.extra.PARAM10";
    private static final String EXTRA_PARAM11 = "com.ceino.chaperonandroid.services.extra.PARAM11";
    private static final String EXTRA_PARAM12 = "com.ceino.chaperonandroid.services.extra.PARAM12";
    private static final String EXTRA_PARAM13 = "com.ceino.chaperonandroid.services.extra.PARAM13";
    private static final String EXTRA_PARAM14 = "com.ceino.chaperonandroid.services.extra.PARAM14";
    private static final String EXTRA_PARAM15 = "com.ceino.chaperonandroid.services.extra.PARAM15";
    private static final String EXTRA_PARAM16 = "com.ceino.chaperonandroid.services.extra.PARAM16";
    private static final String EXTRA_PARAM17 = "com.ceino.chaperonandroid.services.extra.PARAM17";
    private static final String EXTRA_PARAM18 = "com.ceino.chaperonandroid.services.extra.PARAM18";
    private static final String EXTRA_PARAM19 = "com.ceino.chaperonandroid.services.extra.PARAM19";
    private static final String EXTRA_PARAM20 = "com.ceino.chaperonandroid.services.extra.PARAM20";
    private static final String EXTRA_PARAM21 = "com.ceino.chaperonandroid.services.extra.PARAM21";

    public static ArrayList<AllUserList> ChaperOnUserAllList=new ArrayList<AllUserList>();
    public static ArrayList<CurrUserList> ChaperOnUserList= new ArrayList<CurrUserList>();

    PrefManager prefManager;


    public ChaperOnService() {
        super("ChaperOnService");
    }
    public static void startActionSplash(Context context){
        Intent intent = new Intent(context,ChaperOnService.class);
        intent.setAction(ACTION_SPLASH);
        context.startService(intent);
    }
    public static void startActionCloseOnboard(Context context){
        Intent intent = new Intent(context,ChaperOnService.class);
        intent.setAction(ACTION_CLOSEONBOARD);
        context.startService(intent);
    }
    public static void startActionSignIN(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ChaperOnService.class);
        intent.setAction(ACTION_SIGNIN);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startActionRegister(Context context, String email, String pwd, String firstname, String uname, String image_path,
                                           String mstreet, String mapt_no, String mcity, String mpostal, String mcountry, String mlname, String mDescription, int mrideType, double lattitude, double longitude) {
        Intent intent = new Intent(context, ChaperOnService.class);
        intent.setAction(ACTION_REGISTER);
        intent.putExtra(EXTRA_PARAM3, email);
        intent.putExtra(EXTRA_PARAM4, pwd);
        intent.putExtra(EXTRA_PARAM5, firstname);
        intent.putExtra(EXTRA_PARAM6, uname);
        intent.putExtra(EXTRA_PARAM7, image_path);
        intent.putExtra(EXTRA_PARAM8, mstreet);
        intent.putExtra(EXTRA_PARAM9, mapt_no);
        intent.putExtra(EXTRA_PARAM10, mcity);
        intent.putExtra(EXTRA_PARAM11, mpostal);
        intent.putExtra(EXTRA_PARAM12, mcountry);
        intent.putExtra(EXTRA_PARAM13, mlname);
        intent.putExtra(EXTRA_PARAM14, mDescription);
        intent.putExtra(EXTRA_PARAM15, mrideType);
        intent.putExtra(EXTRA_PARAM19, lattitude);
        intent.putExtra(EXTRA_PARAM20, longitude);
        context.startService(intent);
    }

    public static void startActionSignOUT(Context context) {
        Intent intent = new Intent(context, ChaperOnService.class);
        intent.setAction(ACTION_SIGNOUT);
        context.startService(intent);
    }
    public static void startActionForgetPwdIN(Context context, String forgetemail) {
        Intent intent = new Intent(context, ChaperOnService.class);
        intent.setAction(ACTION_FORGETPWD);
        intent.putExtra(EXTRA_PARAM16, forgetemail);
        context.startService(intent);
    }
    public static void startActionPVGenerateCode(Context context, String phone_no) {
        Intent intent = new Intent(context, ChaperOnService.class);
        intent.setAction(ACTION_PHONE_GENOTP);
        intent.putExtra(EXTRA_PARAM17, phone_no);
        context.startService(intent);
    }
    public static void startActionPVerifyCode(Context context, String phone_no, String otp_no,
                                              String email, String pwd, String firstname, String uname, String image_path,
                                              String mstreet, String mapt_no, String mcity, String mpostal, String mcountry, String mlname, String mDescription, int mrideType, double lattitude, double longtitude) {
        Intent intent = new Intent(context, ChaperOnService.class);
        intent.setAction(ACTION_PHONE_VEROTP);
        intent.putExtra(EXTRA_PARAM17, phone_no);
        intent.putExtra(EXTRA_PARAM18, otp_no);

        intent.putExtra(EXTRA_PARAM3, email);
        intent.putExtra(EXTRA_PARAM4, pwd);
        intent.putExtra(EXTRA_PARAM5, firstname);
        intent.putExtra(EXTRA_PARAM6, uname);
        intent.putExtra(EXTRA_PARAM7, image_path);
        intent.putExtra(EXTRA_PARAM8, mstreet);
        intent.putExtra(EXTRA_PARAM9, mapt_no);
        intent.putExtra(EXTRA_PARAM10, mcity);
        intent.putExtra(EXTRA_PARAM11, mpostal);
        intent.putExtra(EXTRA_PARAM12, mcountry);
        intent.putExtra(EXTRA_PARAM13, mlname);
        intent.putExtra(EXTRA_PARAM14, mDescription);
        intent.putExtra(EXTRA_PARAM15, mrideType);
        intent.putExtra(EXTRA_PARAM19, lattitude);
        intent.putExtra(EXTRA_PARAM20, longtitude);

        context.startService(intent);
    }
    public static void startActionListofUser(Context context) {
        Intent intent = new Intent(context, ChaperOnService.class);
        intent.setAction(ACTION_USER);
        context.startService(intent);
    }

    /*public static void startActionUserDetail(Context context){
        Intent intent = new Intent(context,ChaperOnService.class);
        intent.setAction(ACTION_USERDETAIL);
        context.startService(intent);
    }*/

    public static void startActionCurrUser(Context context, String user_id){
        Intent intent = new Intent(context,ChaperOnService.class);
        intent.setAction(ACTION_USERDETAIL);
        intent.putExtra(EXTRA_PARAM21, user_id);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ChaperOnBus.getInstance().register(this);
        prefManager = new PrefManager(getApplicationContext());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent != null) {
            final String action = intent.getAction();
            if(ACTION_SPLASH.equals(action)) {
                handleActionSplash();
            }else if(ACTION_CLOSEONBOARD.equals(action)){
                handleActionCloseOnboard();
            }else if(ACTION_SIGNIN.equals(action)) {
                String param1 = intent.getStringExtra(EXTRA_PARAM1);
                String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionSignIN(param1, param2);
            }else if(ACTION_SIGNOUT.equals(action)){
                handleActionSignOUT();
            }else if(ACTION_FORGETPWD.equals(action)){
                String forgetemail = intent.getStringExtra(EXTRA_PARAM16);
                handleActionForgetpwd(forgetemail);
            }else if(ACTION_USER.equals(action)){
                handleActionUser();
            }else if(ACTION_REGISTER.equals(action)){
                String email = intent.getStringExtra(EXTRA_PARAM3);
                String password = intent.getStringExtra(EXTRA_PARAM4);
                String firstname = intent.getStringExtra(EXTRA_PARAM5);
                String username = intent.getStringExtra(EXTRA_PARAM6);
                String imagePath = intent.getStringExtra(EXTRA_PARAM7);
                String street = intent.getStringExtra(EXTRA_PARAM8);
                String apartment = intent.getStringExtra(EXTRA_PARAM9);
                String city = intent.getStringExtra(EXTRA_PARAM10);
                String postal = intent.getStringExtra(EXTRA_PARAM11);
                String country = intent.getStringExtra(EXTRA_PARAM12);
                String lastname = intent.getStringExtra(EXTRA_PARAM13);
                String description = intent.getStringExtra(EXTRA_PARAM14);
                int rideType = intent.getIntExtra(EXTRA_PARAM15, 0);
                double lattitude = intent.getDoubleExtra(EXTRA_PARAM19, 0);
                double longitude = intent.getDoubleExtra(EXTRA_PARAM20,0);
                try {
                    handleActionRegister(email,password,firstname,username,imagePath,
                            street,apartment,city,postal,country,lastname,description,rideType,lattitude,longitude);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else if(ACTION_PHONE_GENOTP.equals(action)){
                String phone_no = intent.getStringExtra(EXTRA_PARAM17);
                handleActionPhoneGenCode(phone_no);
            }else if(ACTION_PHONE_VEROTP.equals(action)){
                String phone_no = intent.getStringExtra(EXTRA_PARAM17);
                String otp_no = intent.getStringExtra(EXTRA_PARAM18);

                String email = intent.getStringExtra(EXTRA_PARAM3);
                String password = intent.getStringExtra(EXTRA_PARAM4);
                String firstname = intent.getStringExtra(EXTRA_PARAM5);
                String username = intent.getStringExtra(EXTRA_PARAM6);
                String imagePath = intent.getStringExtra(EXTRA_PARAM7);
                String street = intent.getStringExtra(EXTRA_PARAM8);
                String apartment = intent.getStringExtra(EXTRA_PARAM9);
                String city = intent.getStringExtra(EXTRA_PARAM10);
                String postal = intent.getStringExtra(EXTRA_PARAM11);
                String country = intent.getStringExtra(EXTRA_PARAM12);
                String lastname = intent.getStringExtra(EXTRA_PARAM13);
                String description = intent.getStringExtra(EXTRA_PARAM14);
                int rideType = intent.getIntExtra(EXTRA_PARAM15, 0);
                double lattitude = intent.getDoubleExtra(EXTRA_PARAM19, 0);
                double longitude = intent.getDoubleExtra(EXTRA_PARAM20,0);

                handleActionPhoneVerCode(phone_no, otp_no,
                        email,password,firstname,username,imagePath,
                        street,apartment,city,postal,country,lastname,description,rideType,lattitude,longitude);
            }else if(ACTION_USERDETAIL.equals(action)){
                String usrId = intent.getStringExtra(EXTRA_PARAM21);
                handleActionUserDetail(usrId);
            }
        }
    }

    private void handleActionUserDetail(String usrId) {
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.e("responeSingleUser", json.toString());
                postEventOnMainThread(new GotCurrentUserEvent(CurrentUser(json)));
            }
        };
        AQuery aq = new AQuery(this);

        cb.header("Content-Type", "application/json");
        cb.header("app-id", "appid");
        cb.header("app-key", "appkey");

        aq.ajax(USER_URL + "/" + usrId, JSONObject.class, cb);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ChaperOnBus.getInstance().unregister(this);
    }

    private void handleActionPhoneVerCode(String phone_no, String otp_no,
                                          final String email, final String password, final String firstname, final String username, final String imagePath,
                                          final String street, final String apartment, final String city, final String postal, final String country, final String lastname, final String description, final int rideType, final double lattitude, final double longitude) {
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.e("respone666", json.toString());
                if (json == null) {

                } else if (json != null){
                    startActionRegister(getApplicationContext(),email,password,firstname,username,imagePath,street,apartment,city,postal,country,lastname,description,rideType,lattitude,longitude);
                }
            }
        };

        AQuery aq = new AQuery(this);

        cb.header("Content-Type", "application/json");
        cb.header("app-id", "appid");
        cb.header("app-key", "appkey");


        Map<String, Object> params = null;
        JSONObject mainJson = new JSONObject();
        try {
            // this is paramters
            mainJson.put("phone", "+91"+phone_no);
            mainJson.put("code", otp_no);
            StringEntity sEntity = new StringEntity(mainJson.toString(), HTTP.UTF_8);
            params = new HashMap<String, Object>();
            params.put(AQuery.POST_ENTITY, sEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.params(params);

        aq.ajax(PHONE_VER_URL, JSONObject.class, cb);
    }

    private void handleActionPhoneGenCode(final String phone_no) {
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.e("respone555", json.toString());
                if (json == null) {

                } else if (json != null)

                {
                    /*try {
                        String metaData = json.getString("status");
                        if(metaData.equals("success")){
                            String userData = json.getString("userdata");
                            JSONObject userdata = new JSONObject(userData);
                            String usrname = userdata.getString("username");
                            String usremail = userdata.getString("email");
                            prefManager.createLoginSession(usrname, usremail);
                            postEventOnMainThread(new SignInEvent(true));
                        }else if(metaData.equals("error")){
                            Toast.makeText(getApplicationContext(),"Specified username or password is invalid",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"Json Error:"+metaData,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }*/

                }

            }
        };

        AQuery aq = new AQuery(this);

        cb.header("Content-Type", "application/json");
        cb.header("app-id", "appid");
        cb.header("app-key", "appkey");


        Map<String, Object> params = null;
        JSONObject mainJson = new JSONObject();
        try {
            // this is paramters
            mainJson.put("phone", "+91"+phone_no);
            StringEntity sEntity = new StringEntity(mainJson.toString(), HTTP.UTF_8);
            params = new HashMap<String, Object>();
            params.put(AQuery.POST_ENTITY, sEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.params(params);

        aq.ajax(PHONE_URL, JSONObject.class, cb);
    }


    private HttpResponse handleActionRegister(final String email, final String password, final String firstname, final String username, final String imagePath,
                                              final String street, final String apartment, final String city, final String postal, final String country, final String lastname, final String description, final int rideType,final double lattitude,final double longitude)
            throws UnsupportedEncodingException{
        HttpPost request = GetPostHTTP(email, password, firstname, username, imagePath,street,apartment,city,postal,country,lastname,description,rideType,lattitude,longitude);
        HttpResponse response = null;
        HttpClient client = new DefaultHttpClient();
        String xmlString = null;
        String result = null;
        InputStream inputStream = null;
        JSONObject json = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            response = client.execute(request);
            if(response != null){
                inputStream = response.getEntity().getContent();
                if(inputStream != null) {
                    result = convertInputStreamToString(inputStream);// call this method declar the result in to global

                    json = new JSONObject(result);
                    if (json == null) {

                    } else if (json != null){
                        String metaData = json.getString("status");
                        if(metaData.equals("success")){
                            String userData = json.getString("userdata");
                            JSONObject userdata = new JSONObject(userData);
                            String usrname = userdata.getString("username");
                            String usremail = userdata.getString("email");
                            String usrid = userdata.getString("_id");
                            prefManager.createLoginSession(usrname, usremail,usrid);
                            postEventOnMainThread(new SignUpEvent(true));
                        }else {
                            Toast.makeText(getApplicationContext(),"Json Error:"+metaData,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
//        Log.e("respone444", response.toString());
    }

    private HttpPost GetPostHTTP(String email, String password, String firstname, String username, String imagePath,
                                 String street, String apartment, String city, String postal, String country, String lastname, String description,
                                 int rideType, double lattitude, double longitude) {
//        int ride = Integer.parseInt(rideType);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        HttpPost request = new HttpPost(REGISTER_URL);
        String param = "{\"email\":\"" + email
                + "\",\"password\": \"" + password
                + "\",\"username\": \"" + username
                + "\",\"type\": " + rideType
                + ",\"lattitude\": " + lattitude
                + ",\"longtitude\": " + longitude
                + ",\"street\": \"" + street
                + "\",\"appartment\": \"" + apartment
                + "\",\"city\": \"" + city
                + "\",\"zip\": \"" + postal
                + "\",\"country\": \"" + country
                + "\",\"lname\": \"" + lastname
                + "\",\"description\": \"" + description

                /*
                + "\",\"country\": \"" + country
                + "\",\"country\": \"" + country
                + "\",\"country\": \"" + country
                + "\",\"country\": \"" + country
                + "\",\"country\": \"" + country
                + "\",\"country\": \"" + country
                + "\",\"country\": \"" + country
                + "\",\"country\": \"" + country
                + "\",\"country\": \"" + country*/
                + "\",\"fname\": \"" + firstname + "\"}";
        String BOUNDARY = "---------------------------41184676334";
        request.setHeader("enctype", "multipart/form-data; boundary="
                + BOUNDARY);
        request.setHeader("app-id", "appid");
        request.setHeader("app-key", "appkey");

        MultipartEntityBuilder entity = MultipartEntityBuilder.create();

        try {
            entity.addPart("data", new StringBody(param));

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);

            InputStream in = new ByteArrayInputStream(bos.toByteArray());

            ContentBody image = new InputStreamBody(in, "image/jpeg");
            entity.addPart("image", image );

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.setEntity(entity.build());
        return request;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
    @Override
    public void revokeUriPermission(Uri uri, int modeFlags) {
        super.revokeUriPermission(uri, modeFlags);
    }

    private void handleActionUser() {
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.e("respone333", json.toString());
                /*if(ChaperOnUserList==null)
                    ChaperOnUserList=new ArrayList<UserList>();

                ChaperOnUserList.clear();
                JSONArray jo;
                try {
                    jo = json.getJSONArray("results");
//            Log.e("responexxx", jo.toString());
                    for (int i = 0; i < jo.length(); i++) {
                        JSONObject c = jo.getJSONObject(i);
                        ChaperOnUserList.add(new UserList(c.getString("email"), c.getString("username"),
                                c.getString("fname")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                postEventOnMainThread(new GotAllUserEvent(userall(json)));
//                postEventOnMainThread(new GotAllUserEvent(true));
            }
        };
        AQuery aq = new AQuery(this);

        cb.header("Content-Type", "application/json");
        cb.header("app-id", "appid");
        cb.header("app-key", "appkey");

        aq.ajax(USER_URL, JSONObject.class, cb);
    }

    private ArrayList<AllUserList> userall(JSONObject json) {
        if(ChaperOnUserAllList==null)
            ChaperOnUserAllList=new ArrayList<AllUserList>();

        ChaperOnUserAllList.clear();
        JSONArray jo;
        try {
            jo = json.getJSONArray("results");
//            Log.e("responexxx", jo.toString());
            for (int i = 0; i < jo.length(); i++) {
                JSONObject c = jo.getJSONObject(i);
                AllUserList allUserList = new AllUserList();
                /*allUserList.set_id(c.getString("_id")); allUserList.setAppartment(c.getString("appartment")); allUserList.setCity(c.getString("city")); allUserList.setCountry(c.getString("country")); allUserList.setDescription(c.getString("description"));
                allUserList.setEmail(c.getString("email")); allUserList.setFname(c.getString("fname")); allUserList.setImage(c.getString("image")); allUserList.setLname(c.getString("lname")); allUserList.setStreet(c.getString("street"));
                allUserList.setUsername(c.getString("username")); allUserList.setZip(c.getString("zip"));*/
                allUserList.set_id(c.getString("_id"));
                allUserList.setEmail(c.getString("email")); allUserList.setFname(c.getString("fname")); allUserList.setImage(c.getString("image"));
                allUserList.setUsername(c.getString("username"));
                ChaperOnUserAllList.add(allUserList);
                ChaperOnUserAllList.add(allUserList);
                /*ChaperOnUserAllList.add(new UserList(c.getString("email"), c.getString("username"),
                        c.getString("fname")));*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ChaperOnUserAllList;
    }

    private HttpResponse handleActionForgetpwd(String forgetemail) {
        HttpDelete request = DoDeleteHTTP(forgetemail);
        HttpResponse response = null;
        HttpClient client = new DefaultHttpClient();
        String xmlString = null;
        String result = null;
        InputStream content = null;
        JSONObject json = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            response = client.execute(request);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                postEventOnMainThread(new ForgetPwdEvent());
                HttpEntity entity = response.getEntity();
                content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } else {
                Log.e("Response444", "Failed to get response");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private HttpDelete DoDeleteHTTP(String email) {
        HttpDelete request = new HttpDelete(FORGOT_URL+email);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("app-id", "appid");
        request.setHeader("app-key", "appkey");
        return request;
    }

    private void handleActionCloseOnboard() {
        postEventOnMainThread(new OnBoardCloseEvent());
    }

    private void handleActionSplash() {
        postEventOnMainThread(new SplashEvent());
    }

    private void handleActionSignOUT() {
        prefManager.signOut();
        postEventOnMainThread(new SignOutEvent(true));
    }

    private void handleActionSignIN(final String username,final String password) {
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.e("respone222", json.toString());
                if (json == null) {

                } else if (json != null)

                {
                    try {
                        String metaData = json.getString("status");
                        if(metaData.equals("success")){
                            String userData = json.getString("userdata");
                            JSONObject userdata = new JSONObject(userData);
                            String usrname = userdata.getString("username");
                            String usremail = userdata.getString("email");
                            String usrid = userdata.getString("_id");
                            prefManager.createLoginSession(usrname, usremail, usrid);
                            postEventOnMainThread(new SignInEvent(true));
//                            postEventOnMainThread(new DataLoadedNotifyEvent(CurrentUser(json)));
//                            Toast.makeText(getApplicationContext(), "Login Success!!!", Toast.LENGTH_SHORT).show();
                            /*Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();*/
                        }else if(metaData.equals("error")){
                            Toast.makeText(getApplicationContext(),"Specified username or password is invalid",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"Json Error:"+metaData,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        };

        AQuery aq = new AQuery(this);

        cb.header("Content-Type", "application/json");
        cb.header("app-id", "appid");
        cb.header("app-key", "appkey");


        Map<String, Object> params = null;
        JSONObject mainJson = new JSONObject();
        try {
            // this is paramters
            mainJson.put("username", username);
            mainJson.put("password", password);
            StringEntity sEntity = new StringEntity(mainJson.toString(), HTTP.UTF_8);
            params = new HashMap<String, Object>();
            params.put(AQuery.POST_ENTITY, sEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*Map<String, String> params = new HashMap<String, String>();
//        params.put(AQuery.POST_ENTITY, createStringEntity(json));
        params.put("username", username);
        params.put("password", password);*/

        cb.params(params);

        aq.ajax(LOGIN_URL, JSONObject.class, cb);
    }
    private ArrayList<CurrUserList> CurrentUser(JSONObject json) {
        if(ChaperOnUserList==null)
            ChaperOnUserList=new ArrayList<CurrUserList>();
        ChaperOnUserList.clear();
        JSONArray jo;
        try {
            jo = json.getJSONArray("results");
            JSONObject c = jo.getJSONObject(0);

            CurrUserList currUserList = new CurrUserList();
            currUserList.set_id(c.getString("_id")); currUserList.setAppartment(c.getString("appartment")); currUserList.setCity(c.getString("city")); currUserList.setCountry(c.getString("country")); currUserList.setDescription(c.getString("description"));
            currUserList.setEmail(c.getString("email")); currUserList.setFname(c.getString("fname")); currUserList.setImage(c.getString("image")); currUserList.setLname(c.getString("lname")); currUserList.setStreet(c.getString("street"));
            currUserList.setUsername(c.getString("username")); currUserList.setZip(c.getString("zip"));
            ChaperOnUserList.add(currUserList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ChaperOnUserList;
    }

    /*private ArrayList<CurrUserList> CurrentUser(JSONObject json) {
        if(ChaperOnUserList==null)
            ChaperOnUserList=new ArrayList<CurrUserList>();
        ChaperOnUserList.clear();
        JSONArray jo;
        try {
            String metaData = json.getString("status");
            if(metaData.equals("success")) {
                String userData = json.getString("userdata");
                JSONObject c = new JSONObject(userData);
                CurrUserList currUserList = new CurrUserList();
                currUserList.set_id(c.getString("_id")); currUserList.setAppartment(c.getString("appartment")); currUserList.setCity(c.getString("city")); currUserList.setCountry(c.getString("country")); currUserList.setDescription(c.getString("description"));
                currUserList.setEmail(c.getString("email")); currUserList.setFname(c.getString("fname")); currUserList.setImage(c.getString("image")); currUserList.setLname(c.getString("lname")); currUserList.setStreet(c.getString("street"));
                currUserList.setUsername(c.getString("username")); currUserList.setZip(c.getString("zip"));
                ChaperOnUserList.add(currUserList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ChaperOnUserList;
    }*/

}
