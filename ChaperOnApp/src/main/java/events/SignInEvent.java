package events;

/**
 * Created by ceino on 28/8/15.
 */
public class SignInEvent {
    public Boolean isSuccess=false;
    public SignInEvent(Boolean isSuccess)
    {
        this.isSuccess=isSuccess;

    }
    public SignInEvent()
    {
        this.isSuccess=true;

    }
}
