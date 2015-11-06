package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ceino.chaperonandroid.R;
import com.gc.materialdesign.views.ButtonFlat;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import adapters.OnBoardPagerAdapter;
import application.ChaperOnApplication;
import butterknife.ButterKnife;
import connection.ChaperOnConnection;
import events.OnBoardCloseEvent;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by ceino on 27/8/15.
 */
public class OnBoardFragment extends Fragment {
    public View view;

    OnBoardPagerAdapter onBoardPagerAdapter;
    ViewPager viewpager;
    CircleIndicator vpager_indicator;
    ButtonFlat gone;

    @Inject
    Bus bus;

    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_onboard, container, false);
        onBoardPagerAdapter = new OnBoardPagerAdapter(getActivity());
        gone = (ButtonFlat) view.findViewById(R.id.gone);

        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        viewpager.setAdapter(onBoardPagerAdapter);
        vpager_indicator = (CircleIndicator) view.findViewById(R.id.vpager_indicator);
        vpager_indicator.setViewPager(viewpager);
        /*txttest = (TextView) view.findViewById(R.id.txt_test);
        txttest.setTypeface(TypefaceClass.get(getActivity(), "fonts/HelveticaRegular.ttf"));*/
        vpager_indicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 5) {
                    gone.setVisibility(View.VISIBLE);
                    gone.setText("Done");
                } else {
                    gone.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChaperOnApplication.get(getActivity()).inject(this);
        ButterKnife.inject(this, view);
        gone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bus.post(new OnBoardCloseEvent(true));
//                ChaperOnService.startActionCloseOnboard(getActivity().getApplicationContext());
            }
        });
//        ChaperOnBus.getInstance().register(this);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ChaperOnBus.getInstance().unregister(this);
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
