package data;

/**
 * Created by ceino on 30/9/15.
 */
public class ListTotalInfo {
    String title;
    int imgResId;
    String imageUrl;

    String userID;
    boolean IsImageUrl = false;
    boolean IsMenuItems = false;

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUserId(String userId){
        this.userID = userId;
    }

    public String getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

    public int getImgResId() {
        return imgResId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setIsImageUrl(boolean b) {
        this.IsImageUrl = b;
    }

    public boolean isImageUrl() {
        return IsImageUrl;
    }

    public void setIsMenuItems(boolean b) {
        this.IsMenuItems = b;
    }
    public boolean isMenuItems() {
        return IsMenuItems;
    }

}
