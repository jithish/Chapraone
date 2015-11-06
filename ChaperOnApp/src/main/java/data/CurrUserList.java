package data;

/**
 * Created by ceino on 30/9/15.
 */
public class CurrUserList {
    private String zip;

    private String lname;

    private String street;

    private String isrejected;

    private String image;

    private String type;

    private String city;

    private String country;

    private String appartment;

    private String updatedAt;

    private String username;

    private String _id;

    private String email;

    private String createdAt;

    private String description;

    private String isemailverified;

    private String isapproveduser;

    private String fname;

    public String getZip ()
    {
        return zip;
    }

    public void setZip (String zip)
    {
        this.zip = zip;
    }

    public String getLname ()
    {
        return lname;
    }

    public void setLname (String lname)
    {
        this.lname = lname;
    }

    public String getStreet ()
    {
        return street;
    }

    public void setStreet (String street)
    {
        this.street = street;
    }

    public String getIsrejected ()
    {
        return isrejected;
    }

    public void setIsrejected (String isrejected)
    {
        this.isrejected = isrejected;
    }

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

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getAppartment ()
    {
        return appartment;
    }

    public void setAppartment (String appartment)
    {
        this.appartment = appartment;
    }

    public String getUpdatedAt ()
    {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt)
    {
        this.updatedAt = updatedAt;
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

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getIsemailverified ()
    {
        return isemailverified;
    }

    public void setIsemailverified (String isemailverified)
    {
        this.isemailverified = isemailverified;
    }

    public String getIsapproveduser ()
    {
        return isapproveduser;
    }

    public void setIsapproveduser (String isapproveduser)
    {
        this.isapproveduser = isapproveduser;
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
        return "ClassPojo [zip = "+zip+", lname = "+lname+", street = "+street+", isrejected = "+isrejected+", image = "+image+", type = "+type+", city = "+city+", country = "+country+", appartment = "+appartment+", updatedAt = "+updatedAt+", username = "+username+", _id = "+_id+", email = "+email+", createdAt = "+createdAt+", description = "+description+", isemailverified = "+isemailverified+", isapproveduser = "+isapproveduser+", fname = "+fname+"]";
    }
}
