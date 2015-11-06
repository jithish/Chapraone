package events;

/**
 * Created by ceino on 27/8/15.
 */
public class SplashEvent {
    public Boolean isSuccess=false;
    public SplashEvent(Boolean isSuccess)
    {
        this.isSuccess=isSuccess;

    }
    public SplashEvent()
    {
        this.isSuccess=true;

    }
}
