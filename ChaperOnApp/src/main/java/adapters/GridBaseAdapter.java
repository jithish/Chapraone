package adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceino.chaperonandroid.R;

import java.util.ArrayList;

import javax.inject.Inject;

import application.ChaperOnApplication;
import data.CurrUserList;
import data.ListTotalInfo;
import utils.ImageLoader;

/**
 * Created by ceino on 30/9/15.
 */
public class GridBaseAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<ListTotalInfo> DataList;
    public LayoutInflater inflater;
    public ArrayList<CurrUserList> cUserList = new ArrayList<CurrUserList>();
    public ImageLoader imageLoader;
    @Inject
    ChaperOnApplication chaperOnApplication;

    public GridBaseAdapter(FragmentActivity activity, ArrayList<ListTotalInfo> listTotalInfos) {
        this.context = activity;
        ChaperOnApplication.get(context).inject(this);
        this.DataList = listTotalInfos;
        inflater = LayoutInflater.from(this.context);
        imageLoader=new ImageLoader(context);
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
            convertView = inflater.inflate(R.layout.grid_item,null);
        }else {
            convertView = convertView;
        }
        ListTotalInfo listTotalInfo = DataList.get(position);
//        System.out.println("currentUserImageFname : " + chaperOnApplication.getChaperOnAccessUserImage()+"&&"+chaperOnApplication.getChaperOnAccessUserFname());
        ImageView mitemImg = (ImageView) convertView.findViewById(R.id.item_img);
        utils.CircleImageView mitemImgcircle = (utils.CircleImageView) convertView.findViewById(R.id.item_imgCircle);

        TextView mitemName = (TextView) convertView.findViewById(R.id.item_name);
        mitemImgcircle.setVisibility(View.INVISIBLE);

        if(position == 0){
            mitemImgcircle.setVisibility(View.VISIBLE);
            mitemImg.setImageResource(DataList.get(position).getImgResId());
            imageLoader.DisplayImage("http://dev.ceino.com:8080/ChaperoneAppFiles/" + chaperOnApplication.getChaperOnAccessUserImage(), mitemImgcircle);
            mitemName.setText(chaperOnApplication.getChaperOnAccessUserFname());
        }
        if((listTotalInfo.isImageUrl()) && (listTotalInfo.getImageUrl() != null)){
            imageLoader.DisplayImage("http://dev.ceino.com:8080/ChaperoneAppFiles/" + DataList.get(position).getImageUrl(), mitemImg);
            mitemName.setText(DataList.get(position).getTitle());
        }
        if((!listTotalInfo.isImageUrl()) && (listTotalInfo.getImageUrl() == null)){
            mitemImg.setImageResource(DataList.get(position).getImgResId());
            mitemName.setText(DataList.get(position).getTitle());
        }

        return convertView;
    }

}
