package fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ceino.chaperonandroid.R;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import adapters.GridBaseAdapter;
import application.ChaperOnApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import connection.ChaperOnConnection;
import data.AllUserList;
import data.ListMenuInfo;
import data.ListTotalInfo;
import events.CallFragment;
import events.GotAllUserEvent;
import events.SignOutPass;

/**
 * Created by ceino on 10/10/15.
 */
public class HomeGridFrament extends Fragment implements AdapterView.OnItemClickListener{



    public View view;
    public String user_id = null;

    public static String[] MenuNames = new String[]{"Profile", "Community", "Message",
            "Ride Tokens", "Calendar","Settings", "FAQ","Help"};
    public static TypedArray MenuIcons;
    public static String[] UserNames = new String[]{"Askar Xavi", "Venkat Raman", "Nadesan Saravanan","Karthick", "Poornachari Babu", "Magesh Murugesan",
            "Franklin Roosavelt","HariHaraSudhan" ,"ManiVannan", "Shankar Mahadevan"};
    public static TypedArray UserIcons;

    //    public ArrayList<ListUserInfo> listUserInfos;
    public ArrayList<ListMenuInfo> listMenuInfos;
    public ArrayList<AllUserList> allUserLists;
    public ArrayList<ListTotalInfo> listTotalInfos;

    @Inject
    Bus bus;
    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;

    @InjectView(R.id.progressbar)
    ProgressBar progressBar;

    GridView gridView;
    GridBaseAdapter adapter;

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
        view = inflater.inflate(R.layout.fragment_hm_grid, container, false);
        MenuIcons = getResources().obtainTypedArray(R.array.icons);
        UserIcons = getResources().obtainTypedArray(R.array.users);
        gridView = (GridView) view.findViewById(R.id.grid_view);
//        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChaperOnApplication.get(getActivity()).inject(this);
        ButterKnife.inject(this, view);
//        progressBar.setVisibility(View.VISIBLE);
        user_id = chaperOnApplication.getChaperOnAccessUserID();
        setAdapterAndDownload();
//        setProgressBarDelay();
    }

    ArrayList<ListTotalInfo> menu_userToTotalList(ArrayList<ListMenuInfo> listMenuInfos, ArrayList<AllUserList> listUserInfos) {
        int m = listMenuInfos.size() + listUserInfos.size();
        int nMenuCount = 0;
        int nUserCount = 0;
        for(int i=1;i<=m;i++){
            if(i%2 == 0 && listUserInfos.size()!=0 && nUserCount < listUserInfos.size()){
                listTotalInfos.add(convertToUser(listUserInfos.get(nUserCount)));
                nUserCount ++;
            }else {
                if(nMenuCount < listMenuInfos.size()) {
                    listTotalInfos.add(convertToMenu(listMenuInfos.get(nMenuCount)));
                    nMenuCount ++;
                }else if(nUserCount < listUserInfos.size()){
                    listTotalInfos.add(convertToUser(listUserInfos.get(nUserCount)));
                    nUserCount ++;
                }
            }
        }
        return listTotalInfos;
    }



    ListTotalInfo convertToMenu(ListMenuInfo listMenuInfo) {
        ListTotalInfo listTotalInfo = new ListTotalInfo();
        listTotalInfo.setTitle(listMenuInfo.getTitle());
        listTotalInfo.setImgResId(listMenuInfo.getImgResId());
        listTotalInfo.setIsImageUrl(false);
        listTotalInfo.setIsMenuItems(true);
        return listTotalInfo;
    }

    ListTotalInfo convertToUser(AllUserList listUserInfo) {
        ListTotalInfo listTotalInfo = new ListTotalInfo();
        listTotalInfo.setUserId(listUserInfo.get_id());
        listTotalInfo.setTitle(listUserInfo.getFname());
        listTotalInfo.setImageUrl(listUserInfo.getImage());
        listTotalInfo.setIsImageUrl(true);
        listTotalInfo.setIsMenuItems(false);
        return listTotalInfo;
    }




    private void setProgressBarDelay() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                progressBar.setVisibility(View.GONE);
            }
        }, 5000);
    }

    private void setAdapterAndDownload() {
//        progressBar.setVisibility(View.VISIBLE);//GGK
        allUserLists = new ArrayList<AllUserList>();
        setUpAdapter(allUserLists);
        downloadFeed();

    }

    private void setUpAdapter(ArrayList<AllUserList> allUserList) {

//        listUserInfos = new ArrayList<ListUserInfo>();
        listMenuInfos = new ArrayList<ListMenuInfo>();
        listTotalInfos = new ArrayList<ListTotalInfo>();
        allUserLists = new ArrayList<AllUserList>();

        for (int i = 0; i < MenuNames.length; i++) {
//            MenuListData items = new MenuListData(menutitles[i], menuIcons.getResourceId(i, -1));
            listMenuInfos.add(new ListMenuInfo(MenuNames[i], MenuIcons.getResourceId(i, -1)));
        }
        for (int i = 0; i < allUserList.size(); i++) {
//            MenuListData items = new MenuListData(menutitles[i], menuIcons.getResourceId(i, -1));
            AllUserList allUserList1 = new AllUserList();
            allUserList1.setFname(allUserList.get(i).getFname());
            allUserList1.setImage(allUserList.get(i).getImage());
            allUserList1.set_id(allUserList.get(i).get_id());
            allUserLists.add(allUserList1);
        }
        System.out.println("AllUserList--insideAda: "+ChaperOnApplication.get(getActivity()).allUserList.size());
        listTotalInfos = menu_userToTotalList(listMenuInfos, allUserLists);

        adapter = new GridBaseAdapter(getActivity(),listTotalInfos);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    private void downloadFeed() {


        chaperOnConnection = new ChaperOnConnection(getActivity());
        chaperOnConnection.handleActionUser(getActivity(), new ChaperOnConnection.ChaperOnConnectionCallback() {

            @Override
            public void onActionSuccess() {
                setUpAdapter(removeCurrentUser(ChaperOnApplication.get(getActivity()).allUserList));
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onACtionFailed() {

            }
        });

    }

    ArrayList<AllUserList> removeCurrentUser(ArrayList<AllUserList> allUserList) {
        ArrayList<AllUserList> filterUserLists = new ArrayList<AllUserList>();
        for(int i=0;i<allUserList.size();i++)
        {
            if(!allUserList.get(i).get_id().equals(user_id))
            {
                filterUserLists.add(allUserList.get(i));
            }
        }
        return filterUserLists;
    }

    @Subscribe
    public void onGotAllUserEvent(GotAllUserEvent event) {
        ChaperOnApplication.get(getActivity()).allUserList = event.userList;
        System.out.println("AllUserList--OnGot: " + ChaperOnApplication.get(getActivity()).allUserList.size());
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);

    }*/
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
            {
                chaperOnApplication.SignOut();
                bus.post(new SignOutEvent(true));
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String selectedItem = MenuNames[position];

        ListTotalInfo listTotalInform = listTotalInfos.get(position);
        if(listTotalInform.isMenuItems()){
            if(listTotalInform.getTitle().equals("Profile")){
//                Toast.makeText(getActivity(), "Profile", Toast.LENGTH_SHORT).show();
                bus.post(new CallFragment("Profile"));
            }else if(listTotalInform.getTitle().equals("Community")){
                Toast.makeText(getActivity(), "Community", Toast.LENGTH_SHORT).show();
            }else if(listTotalInform.getTitle().equals("Message")){
                Toast.makeText(getActivity(), "Message", Toast.LENGTH_SHORT).show();
            }else if(listTotalInform.getTitle().equals("Ride Tokens")){
                Toast.makeText(getActivity(), "Ride Tokens", Toast.LENGTH_SHORT).show();
            }else if(listTotalInform.getTitle().equals("Calendar")){
                Toast.makeText(getActivity(), "Calendar", Toast.LENGTH_SHORT).show();
//                bus.post(new CallFragment("Calendar"));
            }else if(listTotalInform.getTitle().equals("Settings")){
//                Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(getActivity(), SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("showsnew_id", showsNew.getObjectId());
                getActivity().startActivity(intent);*/
                bus.post(new CallFragment("Settings"));
            }else if(listTotalInform.getTitle().equals("FAQ")){
                Toast.makeText(getActivity(), "FAQ", Toast.LENGTH_SHORT).show();
            }else if(listTotalInform.getTitle().equals("Help")){
                Toast.makeText(getActivity(), "Help", Toast.LENGTH_SHORT).show();
            }
        }else {
            bus.post(new CallFragment("OthersProfile"));
            chaperOnConnection = new ChaperOnConnection(getActivity());
            chaperOnConnection.handleActionOtherUser(listTotalInform.getUserID());
            chaperOnConnection.handleActionGetSingleUserDetail(listTotalInform.getUserID());
//            Toast.makeText(getActivity(), listTotalInform.getTitle()+"----------->UserId:"+listTotalInform.getUserID(), Toast.LENGTH_SHORT).show();
        }
    }
    @Subscribe
    public void onSignOutPass(SignOutPass event) {
        Toast.makeText(getActivity(), "Pass called", Toast.LENGTH_SHORT).show();
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
