package data;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ceino on 23/9/15.
 */
public class UserList implements Parcelable {

    String uemail;
    String uname;
    String ufname;
    String ulname;

    public UserList(String email, String username, String fname){
        this.uemail = email;
        this.uname = username;
        this.ufname = fname;
//        this.ulname = lname;
    }

    public String getUemail() {
        return uemail;
    }

    public String getUname() {
        return uname;
    }

    public String getUfname() {
        return ufname;
    }

    public String getUlname() {
        return ulname;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public UserList(Parcel in)
    {
        this.uemail = in.readString();
        this.uname = in.readString();
        this.ufname = in.readString();
        this.ulname = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uemail);
        dest.writeString(uname);
        dest.writeString(ufname);
        dest.writeString(ulname);
    }

    public static final Creator<UserList> CREATOR= new Creator<UserList>() {

        @Override
        public UserList[] newArray(int size) {

            return new UserList[size];
        }

        @Override
        public UserList createFromParcel(Parcel source) {

            return new UserList(source);
        }
    };
    public static ArrayList<String> getAlluseremail(ArrayList<UserList> userLists) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < userLists.size(); i++) {
            list.add(userLists.get(i).getUemail());
        }
        return list;
    }
}
