package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/2/8.
 */
public class CisConstants {

    // 干部考察，考察组成员类别
    public final static byte CIS_INSPECTOR_STATUS_NOW = 1;
    public final static byte CIS_INSPECTOR_STATUS_HISTORY = 2;
    public final static byte CIS_INSPECTOR_STATUS_DELETE = 3;
    public final static Map<Byte, String> CIS_INSPECTOR_STATUS_MAP = new LinkedHashMap<>();

    static {
        CIS_INSPECTOR_STATUS_MAP.put(CIS_INSPECTOR_STATUS_NOW, "现任考察组成员");
        CIS_INSPECTOR_STATUS_MAP.put(CIS_INSPECTOR_STATUS_HISTORY, "过去考察组成员");
        CIS_INSPECTOR_STATUS_MAP.put(CIS_INSPECTOR_STATUS_DELETE, "已删除");
    }

    // 干部考察 考察主体
    public final static byte CIS_INSPECTOR_TYPE_OW = 1;
    public final static byte CIS_INSPECTOR_TYPE_OTHER = 2;
    public final static Map<Byte, String> CIS_INSPECTOR_TYPE_MAP = new LinkedHashMap<>();

    static {
        CIS_INSPECTOR_TYPE_MAP.put(CIS_INSPECTOR_TYPE_OW, "党委组织部");
        CIS_INSPECTOR_TYPE_MAP.put(CIS_INSPECTOR_TYPE_OTHER, "其他");
    }

    // 干部考察材料 材料类型
    public final static byte CIS_EVALUATE_TYPE_SHOW = 1; // 现实表现材料
    public final static byte CIS_EVALUATE_TYPE_EVA = 2; // 工作评价
    public final static Map<Byte, String> CIS_EVALUATE_TYPE_MAP = new LinkedHashMap<>();

    static {
        CIS_EVALUATE_TYPE_MAP.put(CIS_EVALUATE_TYPE_SHOW, "现实表现材料");
        CIS_EVALUATE_TYPE_MAP.put(CIS_EVALUATE_TYPE_EVA, "工作评价");
    }
}
