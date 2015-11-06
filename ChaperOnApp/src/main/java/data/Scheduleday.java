package data;

/**
 * Created by ceino on 16/10/15.
 */
public class Scheduleday {

    private String endtime;

    private String starttime;

    private String day;

    public String getEndtime ()
    {
        return endtime;
    }

    public void setEndtime (String endtime)
    {
        this.endtime = endtime;
    }

    public String getStarttime ()
    {
        return starttime;
    }

    public void setStarttime (String starttime)
    {
        this.starttime = starttime;
    }

    public String getDay ()
    {
        return day;
    }

    public void setDay (String day)
    {
        this.day = day;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [endtime = "+endtime+", starttime = "+starttime+", day = "+day+"]";
    }
}
