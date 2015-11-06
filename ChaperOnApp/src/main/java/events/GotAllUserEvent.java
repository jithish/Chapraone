package events;

import java.util.ArrayList;

import data.AllUserList;

/**
 * Created by ceino on 23/9/15.
 */
public class GotAllUserEvent {
    public Boolean isSuccess=false;
    public ArrayList<AllUserList> userList = new ArrayList<AllUserList>();
    public GotAllUserEvent(ArrayList<AllUserList> userLists) {
        this.userList = userLists;
    }

    public GotAllUserEvent(Boolean isSuccess)
    {
        this.isSuccess=isSuccess;

    }
    public GotAllUserEvent()
    {
        this.isSuccess=true;

    }
}
