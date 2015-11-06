package data;

import java.util.ArrayList;

/**
 * Created by ceino on 16/10/15.
 */
public class UserChildList {

    private String parentId;

    private String childid;

    private String nickName;

    private String grade;

    private String gender;

    private ArrayList<Scheduleday> scheduleday;


    public String getParentId ()
    {
        return parentId;
    }

    public void setParentId (String parentId)
    {
        this.parentId = parentId;
    }

    public String getChildId ()
    {
        return childid;
    }

    public void setchildId (String _id)
    {
        this.childid = _id;
    }

    public String getNickName ()
    {
        return nickName;
    }

    public void setNickName (String nickName)
    {
        this.nickName = nickName;
    }

    public String getGrade ()
    {
        return grade;
    }

    public void setGrade (String grade)
    {
        this.grade = grade;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public ArrayList<Scheduleday> getScheduleday ()
    {
        return scheduleday;
    }

    public void setScheduleday (ArrayList<Scheduleday> scheduleday)
    {
        this.scheduleday = scheduleday;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [parentId = "+parentId+", childid = "+childid+", nickName = "+nickName+", grade = "+grade+", gender = "+gender+"]";
    }
}
