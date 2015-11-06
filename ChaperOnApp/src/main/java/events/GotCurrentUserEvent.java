package events;

import java.util.ArrayList;

import data.CurrUserList;

/**
 * Created by ceino on 30/9/15.
 */
public class GotCurrentUserEvent {
    public ArrayList<CurrUserList> currUserLists = new ArrayList<CurrUserList>();
    public GotCurrentUserEvent(){

    }
    public GotCurrentUserEvent(ArrayList<CurrUserList> currentUser) {
        this.currUserLists = currentUser;
    }
}
