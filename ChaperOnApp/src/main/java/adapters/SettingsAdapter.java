package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceino.chaperonandroid.R;

import java.util.ArrayList;

import data.SettingItems;

/**
 * Created by ceino on 9/10/15.
 */
public class SettingsAdapter extends BaseAdapter {

    public Context mcontext;
    private LayoutInflater mInflater;
    ArrayList<SettingItems> Datalist = new ArrayList<SettingItems>();

    public SettingsAdapter(Context context, ArrayList<SettingItems> settingItemses) {
        this.mcontext = context;
        this.Datalist = settingItemses;
        mInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
        return Datalist.size();
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
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.lv_setting_item, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.settingName);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.settingIcon);
        SettingItems row_pos = Datalist.get(position);
        // setting the image resource and title
        txtTitle.setText(row_pos.getStitle());
        imageView.setImageResource(row_pos.getSimgResId());

//        txtTitle.setTypeface(new FontManager(context).getMyriadTitle());
        return convertView;
    }

}
