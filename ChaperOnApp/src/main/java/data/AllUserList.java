package data;

/**
 * Created by ceino on 5/10/15.
 */
public class AllUserList {

    private String image;

    private String type;

    private String username;

    private String _id;

    private String email;

    private String fname;


    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }


    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }


    public String getFname ()
    {
        return fname;
    }

    public void setFname (String fname)
    {
        this.fname = fname;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+", type = "+type+",username = "+username+", _id = "+_id+", email = "+email+",fname = "+fname+"]";
    }
}
