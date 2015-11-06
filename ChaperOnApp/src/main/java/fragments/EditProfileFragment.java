package fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ceino.chaperonandroid.R;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import application.ChaperOnApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import connection.ChaperOnConnection;
import events.CloseFragment;
import utils.Constants;
import utils.ImageLoader;

/**
 * Created by ceino on 19/10/15.
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener{
    public View view;

    @Inject
    Bus bus;

    @Inject
    ChaperOnApplication chaperOnApplication;

    ChaperOnConnection chaperOnConnection;
    public ImageLoader imageLoader;
    public String image_path = null;

    @InjectView(R.id.back_arrow)ImageView backarrow;
    @InjectView(R.id.saveProf_layout)RelativeLayout saveProfileBtn;
    @InjectView(R.id.eprofile_pic)ImageView profileImg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader=new ImageLoader(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        ChaperOnApplication.get(getActivity()).inject(this);
        imageLoader.DisplayImage("http://dev.ceino.com:8080/ChaperoneAppFiles/" + chaperOnApplication.getChaperOnAccessUserImage(), profileImg);
        backarrow.setOnClickListener(this);
        profileImg.setOnClickListener(this);
        saveProfileBtn.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bus.unregister(this);
    }
    @Override
    public void onResume() {
//        bus.register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
//        bus.unregister(this);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eprofile_pic:
                selectImage();
                break;
            case R.id.back_arrow:
                bus.post(new CloseFragment("EditProfFrag"));
                break;
            case R.id.saveProf_layout:
//                Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Under Construction", Toast.LENGTH_SHORT).show();
                System.out.println("UpdatedDatas"+"\nImagePath: "+image_path);
                bus.post(new CloseFragment("EditProfFrag"));
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    getActivity().startActivityForResult(intent, Constants.CAMERA);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    getActivity().startActivityForResult(intent, Constants.GALLERY);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
