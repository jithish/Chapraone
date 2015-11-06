package connection;

import android.content.Context;

public class ChaperOnConnectionProvider {

    public static final boolean isMock = false;

    public static ChaperOnConnection getNewConnection(Context context) {

            return new ChaperOnConnection(context);

    }
}
