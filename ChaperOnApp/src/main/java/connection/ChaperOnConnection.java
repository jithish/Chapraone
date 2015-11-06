package connection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.squareup.otto.Bus;

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

import javax.inject.Inject;

import application.ChaperOnApplication;
import data.AllUserList;
import data.OtherUserList;
import data.Scheduleday;
import data.UserChildList;
import events.AddChildSuccessEvent;
import events.ForgetPwdEvent;
import events.GotAllUserEvent;
import events.GotOtherUserEvent;
import events.GotUserChildEvent;
import events.PhoneVerifyEvent;
import events.SignInEvent;
import events.SignUpEvent;

/**
 * Created by ceino on 6/8/15.
 */
@SuppressWarnings("ALL")
public class ChaperOnConnection {

    final String LOGIN_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/login";
    final String REGISTER_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/register";
    final String USER_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/user";
    final String FORGOT_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/forgotpassword/";
    final String PHONE_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/phoneverification/generatecode";
    final String PHONE_VER_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/phoneverification/verify";
    final String CHANGE_PWD_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/user/changepassword/";
    final String ADD_CHILD_URL = "http://dev.ceino.com:8080/chaperoneandroid/api/v1/classes/child";

    Context context;
    @Inject
    Bus bus;
    @Inject
    ChaperOnApplication chaperOnApplication;

    public ChaperOnConnection(Context context) {
        this.context = context;
        ChaperOnApplication.get(context).inject(this);
        bus.register(this);
    }

    //*******************SIGN IN****************************************
    public void handleActionSignIN(final Context activity, final String username, final String password) {
        if (chaperOnApplication.isNetworkAvailableWithMessage(context)) {
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
                                String usrimage = userdata.getString("image");
                                String usrfname = userdata.getString("fname");
                                String usrlname = userdata.getString("lname");
                                String usrstreet = userdata.getString("street");
                                String usrdesc = userdata.getString("description");
                                chaperOnApplication.saveChaperOnAccessToken(usrid,usremail,usrimage,usrfname,usrlname,usrstreet,usrdesc);
                                bus.post(new SignInEvent(true));
//                            prefManager.createLoginSession(usrname, usremail, usrid);
//                            postEventOnMainThread(new SignInEvent(true));
//                            postEventOnMainThread(new DataLoadedNotifyEvent(CurrentUser(json)));
//                            Toast.makeText(getApplicationContext(), "Login Success!!!", Toast.LENGTH_SHORT).show();
                            /*Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();*/
                            }else if(metaData.equals("error")){
                                bus.post(new SignInEvent(false));
                                Toast.makeText(context, "Specified username or password is invalid", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context,"Json Error:"+metaData,Toast.LENGTH_SHORT).show();
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

            AQuery aq = new AQuery(context);

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

    }
    //*******************FORGETPASSWORD****************************************
    public HttpResponse handleActionForgetpwd(final Context context,final String forgetemail) {
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
                bus.post(new ForgetPwdEvent(true));
//                postEventOnMainThread(new ForgetPwdEvent());
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
//*******************GET REGISTERED USER ONCE*******************************
public void handleActionGetAllEmail(FragmentActivity activity) {
    AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

        @Override
        public void callback(String url, JSONObject json, AjaxStatus status) {
            Log.e("respone333", json.toString());
            if (json == null) {

            } else if (json != null)

            {

                bus.post(new GotAllUserEvent(userall(json)));
            }

        }
    };
    AQuery aq = new AQuery(context);

    cb.header("Content-Type", "application/json");
    cb.header("app-id", "appid");
    cb.header("app-key", "appkey");

    aq.ajax(USER_URL, JSONObject.class, cb);
}
//*******************GET REGISTERED USER WITH CALLBACK****************************************

    public void handleActionUser(Context context,final ChaperOnConnectionCallback callback) {
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.e("respone333", json.toString());
                if (json == null) {

                } else if (json != null)

                {
                    bus.post(new GotAllUserEvent(userall(json)));

                    if(callback!=null)
                    {
                        callback.onActionSuccess();
                    }
                    if(callback!=null)
                    {
                        callback.onACtionFailed();
                    }

                    /*ErrorObject errorObject = new ErrorObject(json);

                    if(!errorObject.isError())
                    {
                        bus.post(new GotAllUserEvent(userall(json)));

                        if(callback!=null)
                        {
                            callback.onActionSuccess();
                        }
                    }
                    else
                    {
                        if(callback!=null)
                        {
                            callback.onACtionFailed();
                        }


                    }*/


                }

            }
        };
        AQuery aq = new AQuery(context);

        cb.header("Content-Type", "application/json");
        cb.header("app-id", "appid");
        cb.header("app-key", "appkey");

        aq.ajax(USER_URL, JSONObject.class, cb);
    }
    public static ArrayList<AllUserList> userall(JSONObject json) {

        ArrayList<AllUserList> ChaperOnUserAllList=new ArrayList<AllUserList>();
        JSONArray jo;
        try {
            jo = json.getJSONArray("results");
//            Log.e("responexxx", jo.toString());
            for (int i = 0; i < jo.length(); i++) {
                JSONObject c = jo.getJSONObject(i);
                AllUserList allUserList = new AllUserList();
                /*allUserList.set_id(c.getString("_id")); allUserList.setAppartment(c.getString("appartment")); allUserList.setCity(c.getString("city")); allUserList.setCountry(c.getString("country")); allUserList.setDescription(c.getString("description"));
                allUserList.setEmail(c.getString("email")); allUserList.setFname(c.getString("fname")); allUserList.setImage(c.getString("image")); allUserList.setLname(c.getString("lname")); allUserList.setStreet(c.getString("street"));
                allUserList.setUsername(c.getString("username")); allUserList.setZip(c.getString("zip"));
                ChaperOnUserAllList.add(allUserList);*/
                allUserList.set_id(c.getString("_id"));
                allUserList.setEmail(c.getString("email")); allUserList.setFname(c.getString("fname")); allUserList.setImage(c.getString("image"));
                allUserList.setUsername(c.getString("username"));
                ChaperOnUserAllList.add(allUserList);
                /*ChaperOnUserAllList.add(new UserList(c.getString("email"), c.getString("username"),
                        c.getString("fname")));*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ChaperOnUserAllList;
    }

    //*******************GENERATE OTP for PHONE VERIFICATION****************************************

    public void handleActionPhoneGenCode(final String phone_no) {
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.e("respone555", json.toString());
                if (json == null) {

                } else if (json != null)

                {

                }

            }
        };

        AQuery aq = new AQuery(context);

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

    //*******************VERIFY OTP for PHONE VERIFICATION****************************************

    /*public void handleActionPhoneVerCode(final String phone_no, final String otp_no,
                                         final String email, final String password, final String firstname, final String username, final String imagePath,
                                         final String street, final String apartment, final String city, final String postal, final String country, final String lastname, final String description, final int rideType, final double lattitude, final double longitude)*/
    public void handleActionPhoneVerCode(final String phone_no, final String otp_no) {
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.e("respone666", json.toString());
                if (json == null) {

                } else if (json != null){

                    try {
                        String message = json.getString("message");
                        String mstatus = json.getString("status");
//                        if(message.equals("incorrect code")){
                        if(message.equals("code verified")){
                            bus.post(new PhoneVerifyEvent(true));
                            /*try {
                                startActionRegister(email,password,firstname,username,imagePath,street,apartment,city,postal,country,lastname,description,rideType,lattitude,longitude);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }*/
                        }else {
                            bus.post(new PhoneVerifyEvent(false));
//                            bus.post(new SignUpEvent(false));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        AQuery aq = new AQuery(context);

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
    //*******************NEW USER REGISTRATION****************************************

    public HttpResponse startActionRegister(final String email, final String password, final String firstname, final String username, final String imagePath,
                                              final String street, final String apartment, final String city, final String postal, final String country, final String lastname,
                                            final String description, final int rideType,final double lattitude,final double longitude,final String phone,final int availableseats,final String licenseImage)
            {
        HttpPost request = GetPostHTTP(email, password, firstname, username, imagePath, street, apartment, city, postal, country, lastname, description, rideType, lattitude, longitude,phone,availableseats,licenseImage);
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
                            String usrimage = userdata.getString("image");
                            String usrfname = userdata.getString("fname");
                            String usrlname = userdata.getString("lname");
                            String usrstreet = userdata.getString("street");
                            String usrdesc = userdata.getString("description");
                            chaperOnApplication.saveChaperOnAccessToken(usrid,usremail,usrimage,usrfname,usrlname,usrstreet,usrdesc);
                            bus.post(new SignUpEvent(true));
                        }else {
                            bus.post(new SignUpEvent(false));
//                            Toast.makeText(context,"Json Error:"+metaData,Toast.LENGTH_SHORT).show();
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
    }

    private HttpPost GetPostHTTP(String email, String password, String firstname, String username, String imagePath,
                                 String street, String apartment, String city, String postal, String country, String lastname, String description,
                                 int rideType, double lattitude, double longitude,String phone,int availableseats,String licenseImage) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        Bitmap bitmap2 = BitmapFactory.decodeFile(licenseImage);
        HttpPost request = new HttpPost(REGISTER_URL);
        String param = "{\"phone\":\""+phone+"\",\"email\":\"" + email
                + "\",\"password\": \"" + password
                + "\",\"username\": \"" + username
                + "\",\"availableSeats\":"+availableseats+",\"type\": " + rideType
                + ",\"lattitude\": " + lattitude
                + ",\"longtitude\": " + longitude
                + ",\"street\": \"" + street
                + "\",\"appartment\": \"" + apartment
                + "\",\"city\": \"" + city
                + "\",\"zip\": \"" + postal
                + "\",\"country\": \"" + country
                + "\",\"lname\": \"" + lastname
                + "\",\"description\": \"" + description
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
            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
            bitmap2.compress(Bitmap.CompressFormat.JPEG,70,bos2);

            InputStream in = new ByteArrayInputStream(bos.toByteArray());
            InputStream in2 = new ByteArrayInputStream(bos2.toByteArray());

            ContentBody image = new InputStreamBody(in, "image/jpeg");
            ContentBody image2 = new InputStreamBody(in2, "image/jpeg");
            entity.addPart("image", image);
            entity.addPart("licenceimage", image2);

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.setEntity(entity.build());
        return request;
    }

    public static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }




    //***********************************************CHANGE PASSWORD************************************************
    public void handleActionChangePassword(final String currentUserId,final String currentpwd,final String newpwd) {
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.e("respone777", json.toString());
                if (json == null) {

                } else if (json != null)

                {

                    try {
                        String mstatus = json.getString("status");
                        if(mstatus.equals("success")){
                            Toast.makeText(context, "Password Changed Successfully. Please Login Again", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Old password not matching with the one in db", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                        if(message.equals("incorrect code")){

                }

            }
        };

        AQuery aq = new AQuery(context);

        cb.header("Content-Type", "application/json");
        cb.header("app-id", "appid");
        cb.header("app-key", "appkey");


        Map<String, Object> params = null;
        JSONObject mainJson = new JSONObject();
        try {
            // this is paramters
            mainJson.put("currentpassword", currentpwd);
            mainJson.put("newpassword", newpwd);
            StringEntity sEntity = new StringEntity(mainJson.toString(), HTTP.UTF_8);
            params = new HashMap<String, Object>();
            params.put(AQuery.POST_ENTITY, sEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.params(params);

        aq.ajax(CHANGE_PWD_URL + currentUserId, JSONObject.class, cb);
    }
    //*************************************************ADD CHILD*************************************
    public void handleActionAddCild(String parentId, String childname, String schoolname, String selectedGender, String selectedGrade, String monStime, String monEtime, int i1, String tueStime, String tueEtime, int i2, String wedStime, String wedEtime, int i3, String thuStime, String thuEtime, int i4, String friStime, String friEtime, int i5, String satStime, String satEtime, int i6, String sunStime, String sunEtime, int i7) {
        HttpPost request = GetPostHTTPChild(parentId, childname, schoolname, selectedGender, selectedGrade,
                monStime, monEtime, i1,
                tueStime, tueEtime, i2,
                wedStime, wedEtime, i3,
                thuStime, thuEtime, i4,
                friStime, friEtime, i5,
                satStime, satEtime, i6,
                sunStime, sunEtime, i7);
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
                        bus.post(new AddChildSuccessEvent(true));

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
    }
    private HttpPost GetPostHTTPChild(String parentId, String childname, String schoolname, String selectedGender, String selectedGrade,
                                      String monStime, String monEtime, int i1,
                                      String tueStime, String tueEtime, int i2,
                                      String wedStime, String wedEtime, int i3,
                                      String thuStime, String thuEtime, int i4,
                                      String friStime, String friEtime, int i5,
                                      String satStime, String satEtime, int i6,
                                      String sunStime, String sunEtime, int i7) {
        HttpPost request = new HttpPost(ADD_CHILD_URL);
        String param = "{\"parentId\":\"" + parentId + "\",\"nickName\": \"" + childname + "\",\"schoolname\": \""+schoolname+"\",\"gender\": \"" + selectedGender + "\",\"grade\": \"" + selectedGrade + "\",\"scheduleday\": [{\"starttime\":\""+monStime+"\",\"endtime\":\""+monEtime+"\",\"day\": "+i1+"},{\"starttime\":\""+tueStime+"\",\"endtime\":\""+tueEtime+"\",\"day\": "+i2+"},{\"starttime\":\""+wedStime+"\", \"endtime\": \""+wedEtime+"\",\"day\": "+i3+"},{\"starttime\": \""+thuStime+"\", \"endtime\": \""+thuEtime+"\", \"day\": "+i4+" },{ \"starttime\": \""+friStime+"\", \"endtime\": \""+friEtime+"\", \"day\": "+i5+"},{ \"starttime\": \""+satStime+"\", \"endtime\": \""+satEtime+"\", \"day\": "+i6+" },{ \"starttime\": \""+sunStime+"\", \"endtime\": \""+sunEtime+"\", \"day\": "+i7+" }] }";
        request.setHeader("Content-Type", "application/json");
        request.setHeader("app-id", "appid");
        request.setHeader("app-key", "appkey");

//        MultipartEntityBuilder entity = MultipartEntityBuilder.create();
        try {
            StringEntity entity = new StringEntity(param);
            request.setEntity(entity);
//            entity.addPart("data", new StringBody(param));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        request.setEntity(entity.build());
        return request;
    }
    //**********************************************************GET SINGLE USER DETAILs****************************************
    public void handleActionGetSingleUserDetail(String userId){
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.e("respone888", json.toString());
                if (json == null) {

                } else if (json != null)

                {
                    bus.post(new GotUserChildEvent(userChild(json)));

                }

            }
        };
        AQuery aq = new AQuery(context);

        cb.header("Content-Type", "application/json");
        cb.header("app-id", "appid");
        cb.header("app-key", "appkey");

        aq.ajax(USER_URL+"/"+userId, JSONObject.class, cb);
    }
    public static ArrayList<UserChildList> userChild(JSONObject json) {

        ArrayList<UserChildList> ChaperOnUserChildList=new ArrayList<UserChildList>();


        JSONArray jo,jo1,jo2;
        JSONObject c,d,f;
        try {
            jo = json.getJSONArray("results");
            c = jo.getJSONObject(0);
            jo1 = c.getJSONArray("childs");

            for (int i = 0; i<jo1.length(); i++){
                ArrayList<Scheduleday> scheduledays = new ArrayList<Scheduleday>();
                d = jo1.getJSONObject(i);
                jo2 = d.getJSONArray("scheduleday");
                for(int j = 0; j<jo2.length(); j++){
                    f = jo2.getJSONObject(j);
                    Scheduleday scheduleday = new Scheduleday();
                    scheduleday.setStarttime(f.getString("starttime"));
                    scheduleday.setEndtime(f.getString("endtime"));
                    scheduleday.setDay(f.getString("day"));
                    scheduledays.add(scheduleday);
                }
                UserChildList UserChildList = new UserChildList();
                UserChildList.setParentId(d.getString("parentId"));
                UserChildList.setchildId(d.getString("_id")); UserChildList.setNickName(d.getString("nickName")); UserChildList.setGender(d.getString("gender"));
                UserChildList.setGrade(d.getString("grade"));UserChildList.setScheduleday(scheduledays);
                ChaperOnUserChildList.add(UserChildList);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ChaperOnUserChildList;
    }
    //**********************************OTHER USERS DETAILS******************
    public void handleActionOtherUser(final String userId) {
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.e("respone999", json.toString());
                if (json == null) {

                } else if (json != null)

                {
                    bus.post(new GotOtherUserEvent(OtherUser(json)));
                }

            }
        };
        AQuery aq = new AQuery(context);

        cb.header("Content-Type", "application/json");
        cb.header("app-id", "appid");
        cb.header("app-key", "appkey");

        aq.ajax(USER_URL+"/"+userId, JSONObject.class, cb);
    }
    public static ArrayList<OtherUserList> OtherUser(JSONObject json) {

        ArrayList<OtherUserList> ChaperOnUserAllList=new ArrayList<OtherUserList>();
        JSONArray jo;
        JSONObject c;
        try {
            jo = json.getJSONArray("results");
            c = jo.getJSONObject(0);
            OtherUserList allUserList = new OtherUserList();
                /*allUserList.set_id(c.getString("_id")); allUserList.setAppartment(c.getString("appartment")); allUserList.setCity(c.getString("city")); allUserList.setCountry(c.getString("country")); allUserList.setDescription(c.getString("description"));
                allUserList.setEmail(c.getString("email")); allUserList.setFname(c.getString("fname")); allUserList.setImage(c.getString("image")); allUserList.setLname(c.getString("lname")); allUserList.setStreet(c.getString("street"));
                allUserList.setUsername(c.getString("username")); allUserList.setZip(c.getString("zip"));
                ChaperOnUserAllList.add(allUserList);*/
            allUserList.set_id(c.getString("_id"));allUserList.setFname(c.getString("fname")); allUserList.setImage(c.getString("image"));
            allUserList.setLname(c.getString("lname"));allUserList.setStreet(c.getString("street"));allUserList.setDesc(c.getString("description"));
            ChaperOnUserAllList.add(allUserList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ChaperOnUserAllList;
    }
    //*******************************************************************
    public static interface ChaperOnConnectionCallback
    {
        void onActionSuccess();
        void onACtionFailed();
    }
}