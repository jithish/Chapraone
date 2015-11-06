package data;

/**
 * Created by ceino on 9/10/15.
 */
public class SettingItems {
    String stitle;
    int simgResId;

    public SettingItems(String settingName, int resourceId) {
        this.stitle = settingName;
        this.simgResId = resourceId;
    }


    public int getSimgResId() {
        return simgResId;
    }

    public String getStitle() {
        return stitle;
    }

}
