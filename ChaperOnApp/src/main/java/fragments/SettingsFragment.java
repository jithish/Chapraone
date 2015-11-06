package fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ceino.chaperonandroid.R;
import com.squareup.otto.Bus;

import java.util.ArrayList;

import javax.inject.Inject;

import adapters.SettingsAdapter;
import application.ChaperOnApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import connection.ChaperOnConnection;
import data.SettingItems;
import events.CallFragment;
import events.CloseFragment;
import events.SignOutEvent;

/**
 * Created by ceino on 10/10/15.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {
    public View view;

    String[] SettingNames = new String[]{"Change Password", "Edit Credit card details", "Log Out"};
    TypedArray SettingIcons;
    ArrayList<SettingItems> settingItemses;
    SettingsAdapter adapter;

    @InjectView(R.id.listview)
    ListView listView;

    @Inject
    Bus bus;

    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;

    @InjectView(R.id.back_burger)
    ImageView menuBack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this,view);
        ChaperOnApplication.get(getActivity()).inject(this);
        SettingIcons = getResources().obtainTypedArray(R.array.settingicons);
        menuBack.setOnClickListener(this);
        setAdapter();
    }


    private void setAdapter() {
        settingItemses = new ArrayList<SettingItems>();
        for(int i = 0; i<SettingNames.length; i++){
            settingItemses.add(new SettingItems(SettingNames[i],SettingIcons.getResourceId(i, -1)));
        }
        adapter = new SettingsAdapter(getActivity(), settingItemses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SettingItems settingItems = settingItemses.get(position);
        if(settingItems.getStitle().equals("Change Password")){
            bus.post(new CallFragment("Change Password"));
        }else if(settingItems.getStitle().equals("Edit Credit card details")){
            bus.post(new CallFragment("Edit Credit card details"));
        }else if(settingItems.getStitle().equals("Log Out")){

            showAlertDialogLogOut();

        }
    }
    private void showAlertDialogLogOut(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setCancelable(false);
//        alertDialog.setTitle("LOGOUT");
        alertDialog.setMessage("Are you sure you want logout");
        alertDialog.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Toast.makeText(getActivity(), "LogOut", Toast.LENGTH_SHORT).show();
                bus.post(new SignOutEvent());
                chaperOnApplication.SignOut();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
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
            case R.id.back_burger:
                bus.post(new CloseFragment("SettingsFrag"));
                break;
        }
    }
}
