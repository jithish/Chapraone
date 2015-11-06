package events;

/**
 * Created by ceino on 14/10/15.
 */
public class AddChildSuccessEvent {
    public Boolean isSuccess=false;
    public AddChildSuccessEvent(Boolean isSuccess)
    {
        this.isSuccess=isSuccess;

    }
}