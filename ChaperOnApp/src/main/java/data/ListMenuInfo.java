package data;

/**
 * Created by ceino on 30/9/15.
 */
public class ListMenuInfo {
    String title;
    int imgResId;

    public ListMenuInfo(String menutitle, int resourceId) {
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
