package com.ceino.chaperonandroid.activities;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ceino.chaperonandroid.R;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import adapters.SettingsAdapter;
import application.ChaperOnApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import data.SettingItems;
import events.SignOutEvent;

/**
 * Created by ceino on 9/10/15.
 */
public class SettingsActivity extends FragmentActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    String[] SettingNames = new String[]{"Change Password", "Edit Credit card details", "Log Out"};
    TypedArray SettingIcons;
    ArrayList<SettingItems> settingItemses;
    SettingsAdapter adapter;

    @InjectView(R.id.listview)
    ListView listView;

    @InjectView(R.id.back_burger)
    ImageView menuBack;

    @Inject
    Bus bus;

    @Inject
    ChaperOnApplication chaperOnApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.animation, R.anim.animation2);
        setContentView(R.layout.fragment_settings);
        ButterKnife.inject(this);
        ChaperOnApplication.get(this).inject(this);
        SettingIcons = getResources().obtainTypedArray(R.array.settingicons);
        menuBack.setOnClickListener(this);
        setAdapter();
    }


    private void setAdapter() {
        settingItemses = new ArrayList<SettingItems>();
        for(int i = 0; i<SettingNames.length; i++){
            settingItemses.add(new SettingItems(SettingNames[i],SettingIcons.getResourceId(i, -1)));
        }
        adapter = new SettingsAdapter(getApplicationContext(), settingItemses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
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
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        pickItem = menu.findItem(R.id.pick);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*if (item.getItemId() == R.id.pick) {
            onBoardingLayout.setVisibility(View.GONE);
        }*/
        return super.onOptionsItemSelected(item);

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SettingItems settingItems = settingItemses.get(position);
        if(settingItems.getStitle().equals("Change Password")){
            Toast.makeText(this, "Under Construction", Toast.LENGTH_SHORT).show();
        }else if(settingItems.getStitle().equals("Edit Credit card details")){
            Toast.makeText(this, "Under Constructuion", Toast.LENGTH_SHORT).show();
        }else if(settingItems.getStitle().equals("Log Out")){
//            Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
            finish();
            bus.post(new SignOutEvent(true));
//            ((MainActivity) getApplicationContext()).setFragment(false, false);
            chaperOnApplication.SignOut();
        }
    }
    @Subscribe
    public void onSignOutEvent(SignOutEvent event) {
        if(event.isSuccess == true){
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Cause Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_burger:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
    }
}
