package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ceino.chaperonandroid.R;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import application.ChaperOnApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import connection.ChaperOnConnection;
import events.CloseFragment;

/**
 * Created by ceino on 10/10/15.
 */
public class ChangePwdFragment extends Fragment implements View.OnClickListener{
    public View view;

    @Inject
    Bus bus;

    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;

    @InjectView(R.id.back_arrow)
    ImageView backArrow;
    @InjectView(R.id.change_pwd_submit_layout)
    RelativeLayout submit;
    @InjectView(R.id.old_pwd_txt)
    EditText oldpwd;
    @InjectView(R.id.new_pwd_txt)
    EditText newpwd;
    @InjectView(R.id.confirm_pwd_txt)
    EditText confirmpwd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_s_chang_pwd, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        ChaperOnApplication.get(getActivity()).inject(this);
        backArrow.setOnClickListener(this);
        submit.setOnClickListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                bus.post(new CloseFragment("ChangePwdFrag"));
                break;
            case R.id.change_pwd_submit_layout:
                if(isNull(oldpwd))
                {   oldpwd.requestFocus();
                    oldpwd.setError("Enter Old Password");
                }else if(isNull(newpwd)){
                    newpwd.requestFocus();
                    newpwd.setError("Enter New Password");
                }else if(isNull(confirmpwd)){
                    confirmpwd.requestFocus();
                    confirmpwd.setError("Enter Confirm Password");
                }else if(isEqual(newpwd.getText().toString(),confirmpwd.getText().toString())) { //!(newpwd.getText().toString()).equals(confirmpwd.getText().toString())
                    confirmpwd.requestFocus();
                    confirmpwd.setError("MisMatch");
                }else  {
                    hideSoftKeyboard(getActivity());
                    if(chaperOnApplication.isNetworkAvailableWithMessage(getActivity())){
                        if(!isEqual(oldpwd.getText().toString(),newpwd.getText().toString())) {
                            newpwd.requestFocus();
                            newpwd.setError("New Password should not be same as old password");
                        }else {
                            chaperOnConnection = new ChaperOnConnection(getActivity());
                            chaperOnConnection.handleActionChangePassword(chaperOnApplication.getChaperOnAccessUserID(),oldpwd.getText().toString(),newpwd.getText().toString());
                        }
                    }
                }
                break;
        }
    }

    Boolean isEqual(String newPwd,String confirmPwd) {
        if((newPwd != null) && (confirmPwd != null)){
            if(newPwd.equals(confirmPwd)){
                return false;
            }
            return true;
        }
        return true;
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
}
