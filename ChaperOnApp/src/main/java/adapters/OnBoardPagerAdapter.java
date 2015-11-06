package adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ceino.chaperonandroid.R;

/**
 * Created by ceino on 27/8/15.
 */
public class OnBoardPagerAdapter extends PagerAdapter {

    public Context mcontect;
    LayoutInflater layoutInflater;
    int[] mResources = {
            R.drawable.screen1,
            R.drawable.screen2,
            R.drawable.screen3,
            R.drawable.screen4,
            R.drawable.screen5,
            R.drawable.screen6
    };
    String [] mtext,mcontent;

    public OnBoardPagerAdapter(Context context) {
        mcontect = context;
        layoutInflater = (LayoutInflater) mcontect.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mtext= context.getResources().getStringArray(R.array.startscreen);
        mcontent = context.getResources().getStringArray(R.array.startcontent);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder holder;
        holder = new ViewHolder();
        View itemView = layoutInflater.inflate(R.layout.adapter_onboard, container, false);

        holder.screenbg = (RelativeLayout) itemView.findViewById(R.id.screen_bg);
        holder.screenbg.setBackgroundResource(mResources[position]);
        holder.screenLogo = (ImageView) itemView.findViewById(R.id.screen_logo);
        if(position == 0){
            holder.screenLogo.setBackgroundResource(R.drawable.logo_login);
            holder.screenLogo.getLayoutParams().width = 650;
            holder.screenLogo.getLayoutParams().height = 150;
        }else {
            holder.screenLogo.setBackgroundResource(R.drawable.screen_logo);
        }
        holder.screenTitle =(TextView) itemView.findViewById(R.id.screen_title);
        holder.screenTitle.setText(Html.fromHtml("<b>" + mtext[position] + "</b>"));
        holder.screenContent =(TextView) itemView.findViewById(R.id.screen_content);
        holder.screenContent.setText(Html.fromHtml(mcontent[position]));
        /*ImageView imageView = (ImageView) itemView.findViewById(R.id.screen_imv);
        imageView.setImageResource(mResources[position]);
        */
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
    private class ViewHolder
    {
        RelativeLayout screenbg;
        ImageView screenLogo;
        TextView screenTitle;
        TextView screenContent;
    }
}
