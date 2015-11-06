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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceino.chaperonandroid.R;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Calendar;

import javax.inject.Inject;

import application.ChaperOnApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import connection.ChaperOnConnection;
import events.AddChildSuccessEvent;
import events.IntialCloseFragment;

/**
 * Created by ceino on 15/10/15.
 */
public class InitialAddChildFragment extends Fragment implements View.OnClickListener,TimePickerDialog.OnTimeSetListener {

    public View view;
    static final String TIMEPICKER_TAG1 = "tag1";
    static final String TIMEPICKER_TAG2 = "tag2";
    static final String TIMEPICKER_TAG3 = "tag3";
    static final String TIMEPICKER_TAG4 = "tag4";
    static final String TIMEPICKER_TAG5 = "tag5";
    static final String TIMEPICKER_TAG6 = "tag6";
    static final String TIMEPICKER_TAG7 = "tag7";
    static final String TIMEPICKER_TAG8 = "tag8";
    static final String TIMEPICKER_TAG9 = "tag9";
    static final String TIMEPICKER_TAG10 = "tag10";
    static final String TIMEPICKER_TAG11 = "tag11";
    static final String TIMEPICKER_TAG12 = "tag12";
    static final String TIMEPICKER_TAG13 = "tag13";
    static final String TIMEPICKER_TAG14 = "tag14";
    public String cur = "";

    @Inject
    Bus bus;

    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;

    @InjectView(R.id.back_arrow)ImageView backArrow;
    @InjectView(R.id.back_burger)ImageView backMenu;
    @InjectView(R.id.child_name_txt)EditText childName;
    @InjectView(R.id.child_school_txt)EditText schoolName;
    @InjectView(R.id.boy_txv)TextView boy_txv;
    @InjectView(R.id.girl_txv)TextView girl_txv;
    @InjectView(R.id.txv1)TextView txv1;
    @InjectView(R.id.txv2)TextView txv2;
    @InjectView(R.id.txv3)TextView txv3;
    @InjectView(R.id.txv4)TextView txv4;
    @InjectView(R.id.txv5)TextView txv5;
    @InjectView(R.id.txv6)TextView txv6;
    @InjectView(R.id.txv7)TextView txv7;
    @InjectView(R.id.txv8)TextView txv8;
    @InjectView(R.id.txv9)TextView txv9;
    TextView[] gender_view;
    TextView[] grade_view;
    @InjectView(R.id.mon_start)TextView mon_start;
    @InjectView(R.id.mon_end)TextView mon_end;
    @InjectView(R.id.tue_start)TextView tue_start;
    @InjectView(R.id.tue_end)TextView tue_end;
    @InjectView(R.id.wed_start)TextView wed_start;
    @InjectView(R.id.wed_end)TextView wed_end;
    @InjectView(R.id.thu_start)TextView thu_start;
    @InjectView(R.id.thu_end)TextView thu_end;
    @InjectView(R.id.fri_start)TextView fri_start;
    @InjectView(R.id.fri_end)TextView fri_end;
    @InjectView(R.id.sat_start)TextView sat_start;
    @InjectView(R.id.sat_end)TextView sat_end;
    @InjectView(R.id.sun_start)TextView sun_start;
    @InjectView(R.id.sun_end)TextView sun_end;
    TextView[] day_view;
    @InjectView(R.id.mon_lbl)TextView mon_lbl;
    @InjectView(R.id.tue_lbl)TextView tue_lbl;
    @InjectView(R.id.wed_lbl)TextView wed_lbl;
    @InjectView(R.id.thu_lbl)TextView thu_lbl;
    @InjectView(R.id.fri_lbl)TextView fri_lbl;
    @InjectView(R.id.sat_lbl)TextView sat_lbl;
    @InjectView(R.id.sun_lbl)TextView sun_lbl;
    String aTime;
    @InjectView(R.id.progBar)ProgressBar progBar;
    @InjectView(R.id.add_child_submit_layout)RelativeLayout addchild_submit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_initialaddchild, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        ChaperOnApplication.get(getActivity()).inject(this);

        gender_view = new TextView[]{boy_txv,girl_txv};
        setSelectedGender(gender_view, 0);
        grade_view = new TextView[]{txv1,txv2,txv3,txv4,txv5,txv6,txv7,txv8,txv9};
        setSelectedGrade(grade_view, 0);
        day_view = new TextView[]{mon_lbl,tue_lbl,wed_lbl,thu_lbl,fri_lbl,sat_lbl,sun_lbl};

        backArrow.setOnClickListener(this);
        backMenu.setOnClickListener(this);
        boy_txv.setOnClickListener(this);
        girl_txv.setOnClickListener(this);
        txv1.setOnClickListener(this);txv2.setOnClickListener(this);txv3.setOnClickListener(this);txv4.setOnClickListener(this);txv5.setOnClickListener(this);txv6.setOnClickListener(this);txv7.setOnClickListener(this);txv8.setOnClickListener(this);txv9.setOnClickListener(this);
        mon_start.setOnClickListener(this);mon_end.setOnClickListener(this);tue_start.setOnClickListener(this);tue_end.setOnClickListener(this);wed_start.setOnClickListener(this);wed_end.setOnClickListener(this);thu_start.setOnClickListener(this);thu_end.setOnClickListener(this);fri_start.setOnClickListener(this);fri_end.setOnClickListener(this);sat_start.setOnClickListener(this);sat_end.setOnClickListener(this);sun_start.setOnClickListener(this);sun_end.setOnClickListener(this);
        addchild_submit.setOnClickListener(this);
    }
    void setSelectedGender(TextView []textViews,int position)
    {
        for(int i=0;i<2;i++)
        {
            if(i==position)
                textViews[i].setSelected(true);
            else
                textViews[i].setSelected(false);
        }
    }
    void setSelectedGrade(TextView []textViews,int position)
    {
        for(int i=0;i<9;i++)
        {
            if(i==position)
                textViews[i].setSelected(true);
            else
                textViews[i].setSelected(false);
        }
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
            case R.id.boy_txv:setSelectedGender(gender_view, 0);break;
            case R.id.girl_txv:setSelectedGender(gender_view, 1);break;
            case R.id.txv1:setSelectedGrade(grade_view, 0);break;
            case R.id.txv2:setSelectedGrade(grade_view, 1);break;
            case R.id.txv3:setSelectedGrade(grade_view, 2);break;
            case R.id.txv4:setSelectedGrade(grade_view, 3);break;
            case R.id.txv5:setSelectedGrade(grade_view, 4);break;
            case R.id.txv6:setSelectedGrade(grade_view, 5);break;
            case R.id.txv7:setSelectedGrade(grade_view, 6);break;
            case R.id.txv8:setSelectedGrade(grade_view, 7);break;
            case R.id.txv9:setSelectedGrade(grade_view, 8);break;
            case R.id.mon_start:TimePickerInitialize(TIMEPICKER_TAG1);break;
            case R.id.mon_end:TimePickerInitialize(TIMEPICKER_TAG2);break;
            case R.id.tue_start:TimePickerInitialize(TIMEPICKER_TAG3);break;
            case R.id.tue_end:TimePickerInitialize(TIMEPICKER_TAG4);break;
            case R.id.wed_start:TimePickerInitialize(TIMEPICKER_TAG5);break;
            case R.id.wed_end:TimePickerInitialize(TIMEPICKER_TAG6);break;
            case R.id.thu_start:TimePickerInitialize(TIMEPICKER_TAG7);break;
            case R.id.thu_end:TimePickerInitialize(TIMEPICKER_TAG8);break;
            case R.id.fri_start:TimePickerInitialize(TIMEPICKER_TAG9);break;
            case R.id.fri_end:TimePickerInitialize(TIMEPICKER_TAG10);break;
            case R.id.sat_start:TimePickerInitialize(TIMEPICKER_TAG11);break;
            case R.id.sat_end:TimePickerInitialize(TIMEPICKER_TAG12);break;
            case R.id.sun_start:TimePickerInitialize(TIMEPICKER_TAG13);break;
            case R.id.sun_end:TimePickerInitialize(TIMEPICKER_TAG14);break;
            case R.id.add_child_submit_layout:
                if(isNull(childName))
                {   childName.requestFocus();
                    childName.setError("Enter Child Name");
                }else if(isNull(schoolName)){
                    schoolName.requestFocus();
                    schoolName.setError("Enter School Name");
                }else {
                    hideSoftKeyboard(getActivity());
                    if(chaperOnApplication.isNetworkAvailableWithMessage(getActivity())) {
                        progBar.setVisibility(View.VISIBLE);
                        chaperOnConnection = new ChaperOnConnection(getActivity());
                        chaperOnConnection.handleActionAddCild(chaperOnApplication.getChaperOnAccessUserID(), childName.getText().toString(), schoolName.getText().toString(),
                                selectedGender(gender_view), selectedGrade(grade_view),mon_start.getText().toString(),mon_end.getText().toString(),selectedDay(mon_lbl.getText().toString()),
                                tue_start.getText().toString(),tue_end.getText().toString(),selectedDay(tue_lbl.getText().toString()),
                                wed_start.getText().toString(),wed_end.getText().toString(),selectedDay(wed_lbl.getText().toString()),
                                thu_start.getText().toString(),thu_end.getText().toString(),selectedDay(thu_lbl.getText().toString()),
                                fri_start.getText().toString(),fri_end.getText().toString(),selectedDay(fri_lbl.getText().toString()),
                                sat_start.getText().toString(),sat_end.getText().toString(),selectedDay(sat_lbl.getText().toString()),
                                sun_start.getText().toString(),sun_end.getText().toString(),selectedDay(sun_lbl.getText().toString()));
                    }

                        /*Toast.makeText(getActivity(), "Submit Button", Toast.LENGTH_SHORT).show();
                        System.out.println("child" + "\nnickName : " + childName.getText().toString() + "\nschoolname : " + schoolName.getText().toString()
                                + "\ngender : " + selectedGender(gender_view) + "\ngrade : " + selectedGrade(grade_view)
                                + "\nscheduleday " + "\n\tMonday" + "\n\tstarttime:" + mon_start.getText() + "\tendtime:" + mon_end.getText()+ "\tday:" + selectedDay(mon_lbl.getText().toString())
                                + "\n\tTuesday" + "\n\tstarttime:" + tue_start.getText() + "\tendtime:" + tue_end.getText()+ "\tday:" + selectedDay(tue_lbl.getText().toString())
                                + "\n\tWednesday" + "\n\tstarttime:" + wed_start.getText() + "\tendtime:" + wed_end.getText()+ "\tday:" + selectedDay(wed_lbl.getText().toString())
                                + "\n\tThursday" + "\n\tstarttime:" + thu_start.getText() + "\tendtime:" + thu_end.getText()+ "\tday:" + selectedDay(thu_lbl.getText().toString())
                                + "\n\tFriday" + "\n\tstarttime:" + fri_start.getText() + "\tendtime:" + fri_end.getText()+ "\tday:" + selectedDay(fri_lbl.getText().toString())
                                + "\n\tSaturday" + "\n\tstarttime:" + sat_start.getText() + "\tendtime:" + sat_end.getText()+ "\tday:" + selectedDay(sat_lbl.getText().toString())
                                + "\n\tSunday" + "\n\tstarttime:" + sun_start.getText() + "\tendtime:" + sun_end.getText()+ "\tday:" + selectedDay(sun_lbl.getText().toString()));*/
                }

                break;
            case R.id.back_arrow:
                bus.post(new IntialCloseFragment("AddChildFrag"));
                break;
            case R.id.back_burger:
                bus.post(new IntialCloseFragment("AddChildFrag"));
                break;

            //Final onclick
//            selectedPriority(gender_view);
        }
    }

    @Subscribe
    public void onAddChilEvent(AddChildSuccessEvent event) {
        if(event.isSuccess){
            progBar.setVisibility(View.GONE);
            bus.post(new IntialCloseFragment("AddChildFrag"));
            Toast.makeText(getActivity(), "Child Added successfully", Toast.LENGTH_SHORT).show();
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
    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void TimePickerInitialize(String tag) {
        final Calendar calendar = Calendar.getInstance();
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance((TimePickerDialog.OnTimeSetListener) this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
        switch (tag) {
            case TIMEPICKER_TAG1:
                cur = TIMEPICKER_TAG1;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG2:
                cur = TIMEPICKER_TAG2;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG3:
                cur = TIMEPICKER_TAG3;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG4:
                cur = TIMEPICKER_TAG4;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG5:
                cur = TIMEPICKER_TAG5;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG6:
                cur = TIMEPICKER_TAG6;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG7:
                cur = TIMEPICKER_TAG7;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG8:
                cur = TIMEPICKER_TAG8;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG9:
                cur = TIMEPICKER_TAG9;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG10:
                cur = TIMEPICKER_TAG10;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG11:
                cur = TIMEPICKER_TAG11;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG12:
                cur = TIMEPICKER_TAG12;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG13:
                cur = TIMEPICKER_TAG13;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
            case TIMEPICKER_TAG14:
                cur = TIMEPICKER_TAG14;
                timePickerDialog.show(getChildFragmentManager(), tag);
                break;
        }

    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
//        Toast.makeText(getActivity(), "new time:" + hourOfDay + "-" + minute, Toast.LENGTH_LONG).show();
        if(cur == TIMEPICKER_TAG1){
            updateTime(hourOfDay, minute, 1);
        }else if(cur == TIMEPICKER_TAG2){
            updateTime(hourOfDay, minute, 2);
        }else if(cur == TIMEPICKER_TAG3){
            updateTime(hourOfDay, minute, 3);
        }else if(cur == TIMEPICKER_TAG4){
            updateTime(hourOfDay, minute, 4);
        }else if(cur == TIMEPICKER_TAG5){
            updateTime(hourOfDay, minute, 5);
        }else if(cur == TIMEPICKER_TAG6){
            updateTime(hourOfDay, minute, 6);
        }else if(cur == TIMEPICKER_TAG7){
            updateTime(hourOfDay, minute, 7);
        }else if(cur == TIMEPICKER_TAG8){
            updateTime(hourOfDay, minute, 8);
        }else if(cur == TIMEPICKER_TAG9){
            updateTime(hourOfDay, minute, 9);
        }else if(cur == TIMEPICKER_TAG10){
            updateTime(hourOfDay, minute, 10);
        }else if(cur == TIMEPICKER_TAG11){
            updateTime(hourOfDay, minute, 11);
        }else if(cur == TIMEPICKER_TAG12){
            updateTime(hourOfDay, minute, 12);
        }else if(cur == TIMEPICKER_TAG13){
            updateTime(hourOfDay, minute, 13);
        }else if(cur == TIMEPICKER_TAG14){
            updateTime(hourOfDay, minute, 14);
        }

    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins, int id) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";

        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        String hour = "";
        if (hours < 10)
            hour = "0" + hours;
        else
            hour = String.valueOf(hours);

        // Append in a StringBuilder
        aTime = new StringBuilder().append(hour).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        if(id == 1){
            mon_start.setText(aTime);
        }else if(id == 2){
            mon_end.setText(aTime);
        }else if(id == 3){
            tue_start.setText(aTime);
        }else if(id == 4){
            tue_end.setText(aTime);
        }else if(id == 5){
            wed_start.setText(aTime);
        }else if(id == 6){
            wed_end.setText(aTime);
        }else if(id == 7){
            thu_start.setText(aTime);
        }else if(id == 8){
            thu_end.setText(aTime);
        }else if(id == 9){
            fri_start.setText(aTime);
        }else if(id == 10){
            fri_end.setText(aTime);
        }else if(id == 11){
            sat_start.setText(aTime);
        }else if(id == 12){
            sat_end.setText(aTime);
        }else if(id == 13){
            sun_start.setText(aTime);
        }else if(id == 14){
            sun_end.setText(aTime);
        }


    }

    int selectedDay(String days) {
        int day = 0;
        if(days.equals("MON")) {
            day = 2;
        }else if(days.equals("TUE")) {
            day = 3;
        }else if(days.equals("WED")) {
            day = 4;
        }else if(days.equals("THU")) {
            day = 5;
        }else if(days.equals("FRI")) {
            day = 6;
        }else if(days.equals("SAT")) {
            day = 7;
        }else if(days.equals("SUN")) {
            day = 1;
        }
        return day;
    }

    String selectedGender(TextView []textViews) {
        String gender = "";
        for (int i = 0; i < 2; i++) {
            if(textViews[i].isSelected()) {
                switch (i) {
                    case 0:
                        gender = getResources().getString(R.string.boy);
                        break;
                    case 1:
                        gender = getResources().getString(R.string.girl);
                        break;
                }
                break;
            }

        }
        return  gender;
    }
    String selectedGrade(TextView[] grade_view) {
        String grade = "";
        for(int i = 0; i < 9; i++){
            if(grade_view[i].isSelected()){
                switch (i) {
                    case 0:
                        grade = getResources().getString(R.string.first_child);
                        break;
                    case 1:
                        grade = getResources().getString(R.string.second_child);
                        break;
                    case 2:
                        grade = getResources().getString(R.string.third_child);
                        break;
                    case 3:
                        grade = getResources().getString(R.string.fourth_child);
                        break;
                    case 4:
                        grade = getResources().getString(R.string.fifth_child);
                        break;
                    case 5:
                        grade = getResources().getString(R.string.sixth_child);
                        break;
                    case 6:
                        grade = getResources().getString(R.string.seventh_child);
                        break;
                    case 7:
                        grade = getResources().getString(R.string.eighth_child);
                        break;
                    case 8:
                        grade = getResources().getString(R.string.ninth_child);
                        break;
                }
                break;
            }
        }
        return grade;
    }
}
