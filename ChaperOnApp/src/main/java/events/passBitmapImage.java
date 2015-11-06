package events;

import android.graphics.Bitmap;

/**
 * Created by ceino on 19/10/15.
 */
public class passBitmapImage {

    public Bitmap bitmap_img;
    public boolean isSuccess = false;
    public passBitmapImage(Bitmap bitmap) {
        this.bitmap_img = bitmap;
    }
    public Bitmap getProfileImg() {
        return bitmap_img;
    }
}
