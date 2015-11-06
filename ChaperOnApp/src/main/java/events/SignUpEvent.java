package events;


/**
 * Created by ceino on 22/9/15.
 */
public class SignUpEvent {
    public Boolean isSuccess=false;
    public SignUpEvent(Boolean isSuccess)
    {
        this.isSuccess=isSuccess;

    }
    public SignUpEvent()
    {
        this.isSuccess=true;

    }
}