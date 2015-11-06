package data;

/**
 * Created by ceino on 25/9/15.
 */
public class ListUserInfo {
    String title;
    int imgResId;

    public ListUserInfo(String menutitle, int resourceId) {
        this.title = menutitle;
        this.imgResId = resourceId;
    }
    public String getTitle() {
        return title;
    }

    public int getImgResId() {
        return imgResId;
    }
}
