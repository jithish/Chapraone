package events;

import java.util.ArrayList;

import data.CurrUserList;

public class DataLoadedNotifyEvent {
    public Boolean isSuccess=false;
    public ArrayList<CurrUserList> currUserLists = new ArrayList<CurrUserList>();
    public DataLoadedNotifyEvent()
    {
        this.isSuccess=true;

    }
    public DataLoadedNotifyEvent(ArrayList<CurrUserList> currentUser) {
        this.currUserLists = currentUser;
    }
}
