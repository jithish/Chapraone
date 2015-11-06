package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ceino.chaperonandroid.R;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import application.ChaperOnApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import connection.ChaperOnConnection;
import events.CallFragment;
import events.CloseFragment;
import events.SignOutEvent;

/**
 * Created by ceino on 26/8/15.
 */
public class MainFragment extends Fragment{

    public View view;

    @Inject
    Bus bus;
    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;

    @InjectView(R.id.progressbar)
    ProgressBar progressBar;

    public static Fragment create() {
        Fragment f = new MainFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("AllUserList--Oncreate: " + ChaperOnApplication.get(getActivity()).allUserList.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChaperOnApplication.get(getActivity()).inject(this);
        ButterKnife.inject(this, view);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, new HomeGridFrament());
        transaction.addToBackStack("" + new HomeGridFrament());
        transaction.commit();
//        showFragment(new HomeGridFrament());
    }
    @Subscribe
    public void onCallFragment(CallFragment event) {
        if(event.message.equals("Profile")){
            chaperOnConnection = new ChaperOnConnection(getActivity());
            chaperOnConnection.handleActionGetSingleUserDetail(chaperOnApplication.getChaperOnAccessUserID());
            showFragment(new ProfileFragment());
        }else if(event.message.equals("Settings")){
            showFragment(new SettingsFragment());
        }else if(event.message.equals("Change Password")){
            showFragment(new ChangePwdFragment());
        }else if(event.message.equals("Edit Credit card details")){
            showFragment(new CreditCardFragment());
        }else if(event.message.equals("AddChildFrag")){
            showFragment(new AddChildFragment());
        }else if(event.message.equals("EditProfileFrag")){
            showFragment(new EditProfileFragment());
        }else if(event.message.equals("OthersProfile")){

            showFragment(new OthersProfileFragment());
        }

    }
    @Subscribe
    public void onCloseFramgment(CloseFragment event) {
        if(event.message.equals("ChangePwdFrag") || event.message.equals("editccfrag")){
            closeFragment(new SettingsFragment());
        }else if(event.message.equals("AddChildFragJustBack") || event.message.equals("EditProfFrag")){
            chaperOnConnection = new ChaperOnConnection(getActivity());
            chaperOnConnection.handleActionGetSingleUserDetail(chaperOnApplication.getChaperOnAccessUserID());
            closeFragment(new ProfileFragment());
        }else if(event.message.equals("SettingsFrag") || event.message.equals("ProfileFrag") || event.message.equals("AddChildFrag") || event.message.equals("OthersProfile")){
            closeFragment(new HomeGridFrament());
        }

    }
    @Subscribe
    public void onSignOutEvent(SignOutEvent event) {
        closeFragment(new LoginFragment());
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit);
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack("" + fragment);
        transaction.commit();
    }
    public void showFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit).replace(R.id.frame_layout, fragment).addToBackStack("" + fragment).commit();
        // fragmentShowActionBar(fragment);
    }
    public void closeFragment(Fragment fragment) {

        getChildFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left).replace(R.id.frame_layout, fragment).addToBackStack("" + fragment).commit();
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

}
