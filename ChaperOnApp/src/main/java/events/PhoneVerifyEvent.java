package events;

/**
 * Created by ceino on 15/10/15.
 */
public class PhoneVerifyEvent {
    public Boolean isSuccess=false;
    public PhoneVerifyEvent(Boolean isSuccess)
    {
        this.isSuccess=isSuccess;

    }
}
