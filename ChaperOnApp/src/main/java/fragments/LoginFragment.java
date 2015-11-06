package fragments;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ceino.chaperonandroid.MainActivity;
import com.ceino.chaperonandroid.R;
import com.ceino.chaperonandroid.activities.ForgotpwdActivity;
import com.ceino.chaperonandroid.activities.RegisterActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import application.ChaperOnApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import connection.ChaperOnConnection;
import events.OnBoardCloseEvent;
import events.SignInEvent;
import utils.ConnectionDetector;
import utils.PrefManager;
import utils.TypefaceClass;

/**
 * Created by ceino on 26/8/15.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    public View view;

    public TextView btnLogin,btnForgot,btnRegister;
    public EditText inputEmail;
    public EditText inputPassword;
    PrefManager prefManager;
    RelativeLayout onBoardingLayout;
//    Activity activity;

    @Inject
    Bus bus;
    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;

    @InjectView(R.id.progressbar)
    ProgressBar progressBar;

    // flag for Internet connection status
//    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;
    LocationManager locationManager;

    public static Fragment create() {
        Fragment f = new LoginFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        isInternetPresent = cd.isConnectingToInternet();
//        activity = ((MainActivity) getActivity());
        cd = new ConnectionDetector(getActivity());
        prefManager = new PrefManager(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login,container,false);
//        setupUI(view.findViewById(R.id.parent));
        onBoardingLayout = (RelativeLayout) view.findViewById(R.id.on_boarding_screen);
        inputEmail = (EditText) view.findViewById(R.id.email_act);
        inputEmail.setTypeface(TypefaceClass.get(getActivity(), "fonts/HelveticaRegular.ttf"));
        inputPassword = (EditText) view.findViewById(R.id.pwd_edt);
        inputPassword.setTypeface(TypefaceClass.get(getActivity(), "fonts/HelveticaRegular.ttf"));
        btnLogin = (TextView) view.findViewById(R.id.sign_in_txv);
        btnLogin.setTypeface(TypefaceClass.get(getActivity(), "fonts/HelveticaRegular.ttf"));
        btnForgot = (TextView) view.findViewById(R.id.forgot_txv);
        btnForgot.setTypeface(TypefaceClass.get(getActivity(), "fonts/HelveticaRegular.ttf"));
        btnRegister = (TextView) view.findViewById(R.id.create_acc_txv);
        btnRegister.setTypeface(TypefaceClass.get(getActivity(), "fonts/HelveticaRegular.ttf"));
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChaperOnApplication.get(getActivity()).inject(this);
        ButterKnife.inject(this, view);
        if(chaperOnApplication.getIsOnBoardDone())
        {
            onBoardingLayout.setVisibility(View.VISIBLE);
        }
        btnLogin.setOnClickListener(this);
        btnForgot.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
//        ChaperOnBus.getInstance().register(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_txv:

                if(isNull(inputEmail))
                {   inputEmail.requestFocus();
                    inputEmail.setError("Enter email");
                }
                else if(isNull(inputPassword))
                { inputPassword.requestFocus();
                    inputPassword.setError("Enter password");
                }/*else if (!((MainActivity) getActivity()).isInternetPresent) {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    ((MainActivity) getActivity()).showAlertDialog(((MainActivity) getActivity()), "No Internet Connection",
                            "Please Check Your internet connection.", false);
                }*/else {
                        String email = inputEmail.getText().toString();
                        String password = inputPassword.getText().toString();
                        if(chaperOnApplication.isNetworkAvailableWithMessage(getActivity())){
                            chaperOnConnection = new ChaperOnConnection(getActivity());
                            chaperOnConnection.handleActionSignIN(getActivity(), email, password);
                            progressBar.setVisibility(View.VISIBLE);
                        }
//                        ChaperOnService.startActionSignIN(getActivity(), email, password);
                    hideKeyboard();

                }


                break;
            case R.id.forgot_txv:
//                ChaperOnService.startActionForgetPwdIN(getActivity());
//                    ChaperOnService.startActionListofUser(getActivity());
                Intent intent1 = new Intent(getActivity(), ForgotpwdActivity.class);
                startActivity(intent1);
                /*Bundle bundlanim = ActivityOptions.makeCustomAnimation((activity), R.anim.animation, R.anim.animation2).toBundle();
                activity.startActivity(intent, bundlanim);*/
                break;
            case R.id.create_acc_txv:
//                ChaperOnService.startActionListofUser(getActivity());
                if(chaperOnApplication.isNetworkAvailableWithMessage(getActivity())){

                    chaperOnConnection = new ChaperOnConnection(getActivity());
                    chaperOnConnection.handleActionGetAllEmail(getActivity());

                    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    if( (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) && (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) ) {
                        ((MainActivity) getActivity()).showGPSDialog(((MainActivity) getActivity()));
                    }else {
                        Intent intent2 = new Intent(getActivity(), RegisterActivity.class);
                        startActivity(intent2);
                    }
                }

                break;
        }
    }
    private void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
    @Subscribe
    public void onSingInEvent(SignInEvent event) {
        if (event.isSuccess) {
            progressBar.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
    @Subscribe
    public void onBoardCloseEvent(OnBoardCloseEvent event) {
        if(event.isSuccess){
            onBoardingLayout.setVisibility(View.GONE);
            /*SharedPreferences.Editor editor = sharedPreferences.edit();
            if(!sharedPreferences.getBoolean("isonboarddone", false)) {
                editor.putBoolean("isonboarddone", true);
                editor.commit();
            }*/
            if(chaperOnApplication.getIsOnBoardDone()){
                chaperOnApplication.setIsOnBoardDone(false);
            }
        }
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
