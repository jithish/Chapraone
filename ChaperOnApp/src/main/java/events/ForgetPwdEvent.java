package events;

/**
 * Created by ceino on 7/9/15.
 */
public class ForgetPwdEvent {
    public Boolean isSuccess=false;
    public ForgetPwdEvent(Boolean isSuccess)
    {
        this.isSuccess=isSuccess;

    }
    public ForgetPwdEvent()
    {
        this.isSuccess=true;

    }
}
