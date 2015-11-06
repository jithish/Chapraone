package utils;

import com.squareup.otto.Bus;

/**
 * Created by ceino on 26/8/15.
 */
public class ChaperOnBus {
    private static final Bus BUS = new Bus();
    public static Bus getInstance() {
        return BUS;
    }
}
