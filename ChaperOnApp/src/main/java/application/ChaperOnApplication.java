package application;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.ceino.chaperonandroid.R;
import com.orm.SugarApp;
import com.squareup.otto.Bus;

import org.acra.ACRA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import daggerModule.BasicModule;
import data.AllUserList;
import utils.Utilities;

/**
 * Created by ceino on 6/8/15.
 */
public class ChaperOnApplication extends SugarApp {

    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    SharedPreferences.Editor preferencEeditor;
    @Inject
    ConnectivityManager connectivityManager;
    @Inject
    Bus bus;
    private ObjectGraph objectGraph;

    public static boolean isProduction=false;
    public static ArrayList<AllUserList> allUserList = new ArrayList<AllUserList>();

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        buildObjectGraphandInject();
        inject(this);
        bus.register(this);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    }
    public static ChaperOnApplication get(Context context) {
        return (ChaperOnApplication) context.getApplicationContext();
    }
    private void buildObjectGraphandInject() {

        objectGraph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList(new BasicModule(this));
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    @Override
    public void onTerminate() {
        bus.unregister(this);
        super.onTerminate();
    }
    public boolean isNetworkAvailableWithMessage(Context context) {
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        boolean b = activeNetworkInfo != null
                && activeNetworkInfo.isConnected();
        if (!b) {
            Toast.makeText(context, R.string.internet_not_connected,
                    Toast.LENGTH_LONG).show();

        }
        return b;
    }

    public void saveChaperOnAccessToken(String chaperOnaccessid, String chaperOnaccessemail, String chaperOnaccessimage, String chaperOnaccessfname,
                                        String usrlname, String usrstreet, String usrdesc)

    {
        preferencEeditor.putString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_USERID,
                chaperOnaccessid).commit();
        preferencEeditor.putString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_EMAIL,
            chaperOnaccessemail).commit();
        preferencEeditor.putString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_IMAGE,
                chaperOnaccessimage).commit();
        preferencEeditor.putString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_FNAME,
                chaperOnaccessfname).commit();
        preferencEeditor.putString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_LNAME,
                usrlname).commit();
        preferencEeditor.putString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_STREET,
                usrstreet).commit();
        preferencEeditor.putString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_DESC,
                usrdesc).commit();
        /*preferencEeditor.putString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_PHONE,
                usrphoneno).commit();*/

    }
    public void editChaperOnAccessUserImage(String chaperOnaccessimage){
        preferencEeditor.putString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_IMAGE,
                chaperOnaccessimage).commit();
    }
    public String getChaperOnAccessUserImage()
    {
        return sharedPreferences.getString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_IMAGE,
                null);
    }
    public String getChaperOnAccessUserFname()
    {
        return sharedPreferences.getString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_FNAME,
                null);
    }
    public String getChaperOnAccessUserLname()
    {
        return sharedPreferences.getString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_LNAME,
                null);
    }
    public String getChaperOnAccessUserStreet()
    {
        return sharedPreferences.getString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_STREET,
                null);
    }
    public String getChaperOnAccessUserDesc()
    {
        return sharedPreferences.getString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_DESC,
                null);
    }
    public String getChaperOnAccessUserPhone()
    {
        return sharedPreferences.getString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_PHONE,
                null);
    }

    public String getChaperOnAccessUserID()
    {
        return sharedPreferences.getString(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_USERID,
                null);
    }

    public boolean getIsOnBoardDone(){
        return sharedPreferences.getBoolean(Utilities.Constants.PreferenceKeys.IS_ON_BOARD_DONE, true);
    }

    public void setIsOnBoardDone(boolean b) {
        preferencEeditor.putBoolean(Utilities.Constants.PreferenceKeys.IS_ON_BOARD_DONE,b).commit();
    }

    public void SignOut() {
        preferencEeditor.remove(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_USERID);
        preferencEeditor.remove(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_EMAIL);
        preferencEeditor.remove(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_IMAGE);
        preferencEeditor.remove(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_FNAME);
        preferencEeditor.remove(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_DESC);
        preferencEeditor.remove(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_STREET);
        preferencEeditor.remove(Utilities.Constants.PreferenceKeys.CHAPERON_ACCESS_LNAME);
        preferencEeditor.commit();
    }
}
