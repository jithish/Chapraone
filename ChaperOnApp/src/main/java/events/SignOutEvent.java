package events;

/**
 * Created by ceino on 28/8/15.
 */
public class SignOutEvent {
    public Boolean isSuccess=false;
    public SignOutEvent(Boolean isSuccess)
    {
        this.isSuccess=isSuccess;

    }
    public SignOutEvent()
    {

    }
}
