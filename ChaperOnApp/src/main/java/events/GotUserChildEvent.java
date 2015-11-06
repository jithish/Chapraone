package events;

import java.util.ArrayList;

import data.UserChildList;

/**
 * Created by ceino on 16/10/15.
 */
public class GotUserChildEvent {
    public ArrayList<UserChildList> userChild = new ArrayList<UserChildList>();
    public GotUserChildEvent(ArrayList<UserChildList> userall) {
        this.userChild = userall;
    }
}
