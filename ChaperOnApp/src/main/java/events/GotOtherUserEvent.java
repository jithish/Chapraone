package events;

import java.util.ArrayList;

import data.OtherUserList;

/**
 * Created by ceino on 19/10/15.
 */
public class GotOtherUserEvent {
    public Boolean isSuccess=false;
    public ArrayList<OtherUserList> userList = new ArrayList<OtherUserList>();
    public GotOtherUserEvent(ArrayList<OtherUserList> userLists) {
        this.userList = userLists;
    }

    public GotOtherUserEvent(Boolean isSuccess)
    {
        this.isSuccess=isSuccess;

    }
    public GotOtherUserEvent()
    {
        this.isSuccess=true;

    }
}
