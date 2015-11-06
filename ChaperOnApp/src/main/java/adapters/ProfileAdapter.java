package adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ceino.chaperonandroid.R;

import java.util.ArrayList;

import data.UserChildList;

/**
 * Created by ceino on 16/10/15.
 */
public class ProfileAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<UserChildList> DataList;
    public LayoutInflater inflater;

    public ProfileAdapter(FragmentActivity activity, ArrayList<UserChildList> userChildList) {
        this.context = activity;
        this.DataList = userChildList;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.profile_item,null);
        }else {
            convertView = convertView;
        }
        TextView mChildName = (TextView) convertView.findViewById(R.id.child_name);
        TextView mGradeName = (TextView) convertView.findViewById(R.id.grade);
        TextView monstart = (TextView) convertView.findViewById(R.id.mon_start);
        TextView monend = (TextView) convertView.findViewById(R.id.mon_end);
        TextView tuestart = (TextView) convertView.findViewById(R.id.tue_start);
        TextView tueend = (TextView) convertView.findViewById(R.id.tue_end);
        TextView wedstart = (TextView) convertView.findViewById(R.id.wed_start);
        TextView wedend = (TextView) convertView.findViewById(R.id.wed_end);
        TextView thustart = (TextView) convertView.findViewById(R.id.thu_start);
        TextView thuend = (TextView) convertView.findViewById(R.id.thu_end);
        TextView fristart = (TextView) convertView.findViewById(R.id.fri_start);
        TextView friend = (TextView) convertView.findViewById(R.id.fri_end);
        TextView satstart = (TextView) convertView.findViewById(R.id.sat_start);
        TextView satend = (TextView) convertView.findViewById(R.id.sat_end);
        TextView sunstart = (TextView) convertView.findViewById(R.id.sun_start);
        TextView sunend = (TextView) convertView.findViewById(R.id.sun_end);
        UserChildList userChildList = DataList.get(position);
        mChildName.setText(userChildList.getNickName());
        mGradeName.setText(userChildList.getGrade());
        for(int i = 0; i<userChildList.getScheduleday().size(); i++){
            if(userChildList.getScheduleday().get(i).getDay().equals("2")){
                monstart.setText((userChildList.getScheduleday().get(i).getStarttime()).substring(0,userChildList.getScheduleday().get(i).getStarttime().length()-3));
                //().substring(0,.length()-3)
                monend.setText((userChildList.getScheduleday().get(i).getEndtime()).substring(0, userChildList.getScheduleday().get(i).getEndtime().length()-3));
            }else if(userChildList.getScheduleday().get(i).getDay().equals("3")){
                tuestart.setText((userChildList.getScheduleday().get(i).getStarttime()).substring(0, userChildList.getScheduleday().get(i).getStarttime().length()-3));
                tueend.setText((userChildList.getScheduleday().get(i).getEndtime()).substring(0, userChildList.getScheduleday().get(i).getEndtime().length() - 3));
            }else if(userChildList.getScheduleday().get(i).getDay().equals("4")){
                wedstart.setText((userChildList.getScheduleday().get(i).getStarttime()).substring(0, userChildList.getScheduleday().get(i).getStarttime().length() - 3));
                wedend.setText((userChildList.getScheduleday().get(i).getEndtime()).substring(0, userChildList.getScheduleday().get(i).getEndtime().length() - 3));
            }else if(userChildList.getScheduleday().get(i).getDay().equals("5")){
                thustart.setText((userChildList.getScheduleday().get(i).getStarttime()).substring(0, userChildList.getScheduleday().get(i).getStarttime().length() - 3));
                thuend.setText((userChildList.getScheduleday().get(i).getEndtime()).substring(0, userChildList.getScheduleday().get(i).getEndtime().length() - 3));
            }else if(userChildList.getScheduleday().get(i).getDay().equals("6")){
                fristart.setText((userChildList.getScheduleday().get(i).getStarttime()).substring(0, userChildList.getScheduleday().get(i).getStarttime().length() - 3));
                friend.setText((userChildList.getScheduleday().get(i).getEndtime()).substring(0, userChildList.getScheduleday().get(i).getEndtime().length() - 3));
            }else if(userChildList.getScheduleday().get(i).getDay().equals("7")){
                satstart.setText((userChildList.getScheduleday().get(i).getStarttime()).substring(0, userChildList.getScheduleday().get(i).getStarttime().length() - 3));
                satend.setText((userChildList.getScheduleday().get(i).getEndtime()).substring(0, userChildList.getScheduleday().get(i).getEndtime().length() - 3));
            }else if(userChildList.getScheduleday().get(i).getDay().equals("1")){
                sunstart.setText((userChildList.getScheduleday().get(i).getStarttime()).substring(0, userChildList.getScheduleday().get(i).getStarttime().length()-3));
                sunend.setText((userChildList.getScheduleday().get(i).getEndtime()).substring(0, userChildList.getScheduleday().get(i).getEndtime().length()-3));
            }
        }

        return convertView;
    }
}
