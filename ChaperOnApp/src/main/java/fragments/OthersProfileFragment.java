package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceino.chaperonandroid.R;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import adapters.ProfileAdapter;
import application.ChaperOnApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import connection.ChaperOnConnection;
import data.UserChildList;
import events.CloseFragment;
import events.GotOtherUserEvent;
import events.GotUserChildEvent;
import utils.ImageLoader;

/**
 * Created by ceino on 19/10/15.
 */
public class OthersProfileFragment extends Fragment implements View.OnClickListener {
    public View view;

    @Inject
    Bus bus;

    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;
    public ImageLoader imageLoader;

    @InjectView(R.id.back_arrow)ImageView backarrow;
    @InjectView(R.id.edit_profile)ImageView edit_profile;
    @InjectView(R.id.addchild_layout)RelativeLayout add_childbtn;
    @InjectView(R.id.profile_img)
    ImageView profImage;
    @InjectView(R.id.profile_name)
    TextView profName;
    @InjectView(R.id.prof_street)
    TextView profStreet;
    @InjectView(R.id.prof_desc)
    TextView profDesc;
    @InjectView(R.id.prof_phoneNo)
    TextView profPhone;

    @InjectView(R.id.listview)ListView listView;
    ArrayList<UserChildList> userChildList;
    ProfileAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader=new ImageLoader(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        ChaperOnApplication.get(getActivity()).inject(this);

//        imageLoader.DisplayImage("http://dev.ceino.com:8080/ChaperoneAppFiles/" + chaperOnApplication.getChaperOnAccessUserImage(), profImage);
//        profName.setText(chaperOnApplication.getChaperOnAccessUserFname() + " " + chaperOnApplication.getChaperOnAccessUserLname());
//        profStreet.setText(chaperOnApplication.getChaperOnAccessUserStreet());
//        profDesc.setText(chaperOnApplication.getChaperOnAccessUserDesc());
//        profPhone.setText(chaperOnApplication.getChaperOnAccessUserPhone());
        userChildList = new ArrayList<UserChildList>();
        setUpAdapter(userChildList);

        backarrow.setVisibility(View.VISIBLE);
        edit_profile.setVisibility(View.GONE);
        add_childbtn.setVisibility(View.GONE);
        backarrow.setOnClickListener(this);
    }

    private void setUpAdapter(ArrayList<UserChildList> userChildList1) {
        if(userChildList1.size()>0){
            userChildList = userChildList1;
            adapter = new ProfileAdapter(getActivity(),userChildList);
            listView.setAdapter(adapter);
        }
    }

    @Subscribe
    public void onGotUserChildEvent(GotUserChildEvent event) {
        if(event.userChild != null){
            setUpAdapter(event.userChild);
            Toast.makeText(getActivity(), "Childs count: " + event.userChild.size(), Toast.LENGTH_SHORT).show();
        }


    }
    @Subscribe
    public void onGotOtherUserEvent(GotOtherUserEvent event) {
        if (event.userList != null) {
            imageLoader.DisplayImage("http://dev.ceino.com:8080/ChaperoneAppFiles/" + event.userList.get(0).getImage(), profImage);
        profName.setText(event.userList.get(0).getFname() + " " + event.userList.get(0).getLname());
            profStreet.setText(event.userList.get(0).getStreet());
            profDesc.setText(event.userList.get(0).getDesc());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        bus.unregister(this);
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
                bus.post(new CloseFragment("OthersProfile"));
                break;
        }
    }
}
