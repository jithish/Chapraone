package events;

/**
 * Created by ceino on 27/8/15.
 */
public class OnBoardCloseEvent {
    public Boolean isSuccess=false;
    public OnBoardCloseEvent(Boolean isSuccess)
    {
        this.isSuccess=isSuccess;

    }
    public OnBoardCloseEvent()
    {
        this.isSuccess=true;

    }
}
