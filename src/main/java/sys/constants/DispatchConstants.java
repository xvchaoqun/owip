package sys.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fafa on 2015/11/23.
 */
public class DispatchConstants {

    // 干部任免类别
    public static final byte DISPATCH_CADRE_TYPE_ASSIGN = 1;
    public static final byte DISPATCH_CADRE_TYPE_DEPOSE = 2;
    public final static Map<Byte, String> DISPATCH_CADRE_TYPE_MAP = new HashMap();
    static {
        DISPATCH_CADRE_TYPE_MAP.put(DISPATCH_CADRE_TYPE_ASSIGN, "任命");
        DISPATCH_CADRE_TYPE_MAP.put(DISPATCH_CADRE_TYPE_DEPOSE, "免职");
    }
}
