package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/2/8.
 */
public class OaConstants {

    // 协同办公，任务状态，1 新建 2 召回 3 已发布 4 作废 5 已完成
    public final static byte OA_TASK_STATUS_INIT = 1;
    public final static byte OA_TASK_STATUS_BACK = 2;
    public final static byte OA_TASK_STATUS_PUBLISH = 3;
    public final static byte OA_TASK_STATUS_ABOLISH = 4;
    public final static byte OA_TASK_STATUS_FINISH = 5;
    public final static Map<Byte, String> OA_TASK_STATUS_MAP = new LinkedHashMap<>();
    static {
        OA_TASK_STATUS_MAP.put(OA_TASK_STATUS_INIT, "新建");
        OA_TASK_STATUS_MAP.put(OA_TASK_STATUS_BACK, "召回");
        OA_TASK_STATUS_MAP.put(OA_TASK_STATUS_PUBLISH, "已发布");
        OA_TASK_STATUS_MAP.put(OA_TASK_STATUS_ABOLISH, "作废");
        OA_TASK_STATUS_MAP.put(OA_TASK_STATUS_FINISH, "已完成");
    }
    // 协同办公，任务对象状态， 0 未审核 1 审核通过 2 审核未通过
    public final static byte OA_TASK_USER_STATUS_INIT = 0;
    public final static byte OA_TASK_USER_STATUS_PASS = 1;
    public final static byte OA_TASK_USER_STATUS_DENY = 2;
    public final static Map<Byte, String> OA_TASK_USER_STATUS_MAP = new LinkedHashMap<>();
    static {
        OA_TASK_USER_STATUS_MAP.put(OA_TASK_USER_STATUS_INIT, "未审核");
        OA_TASK_USER_STATUS_MAP.put(OA_TASK_USER_STATUS_PASS, "审核通过");
        OA_TASK_USER_STATUS_MAP.put(OA_TASK_USER_STATUS_DENY, "审核未通过");
    }

    // 协同办公，短信发送类别，1 下发任务短信通知  2 短信催促未报送对象  3 审核未通过短信提醒 4 本人设置的短信提醒
    public final static byte OA_TASK_MSG_TYPE_INFO = 1;
    public final static byte OA_TASK_MSG_TYPE_UNREPORT = 2;
    public final static byte OA_TASK_MSG_TYPE_DENY =3;
    public final static byte OA_TASK_MSG_TYPE_SELF =3;
    public final static Map<Byte, String> OA_TASK_MSG_TYPE_MAP = new LinkedHashMap<>();
    static {
        OA_TASK_MSG_TYPE_MAP.put(OA_TASK_MSG_TYPE_INFO, "下发任务通知");
        OA_TASK_MSG_TYPE_MAP.put(OA_TASK_MSG_TYPE_UNREPORT, "催促未报送对象");
        OA_TASK_MSG_TYPE_MAP.put(OA_TASK_MSG_TYPE_DENY, "审核未通过提醒");
        OA_TASK_MSG_TYPE_MAP.put(OA_TASK_MSG_TYPE_SELF, "本人设置的提醒");
    }

    //数据表格报送模板状态
    public final static byte OA_GRID_USE = 1;
    public final static byte OA_GRID_HASFINISHED = 2;
    public final static byte OA_GRID_HASDELETED = 3;
    public final static Map<Byte, String>  OA_GRID_STATUS_MAP = new LinkedHashMap<>();
    static {
        OA_GRID_STATUS_MAP.put(OA_GRID_USE, "已发布");
        OA_GRID_STATUS_MAP.put(OA_GRID_HASFINISHED, "已完成");
        OA_GRID_STATUS_MAP.put(OA_GRID_HASDELETED, "已删除");
    }

    //党统上传文件状态
    public final static byte OA_GRID_PARTY_INI = 0;
    public final static byte OA_GRID_PARTY_SAVE = 1;
    public final static byte OA_GRID_PARTY_REPORT = 2;
    public final static byte OA_GRID_PARTY_BACK = 3;
    public final static Map<Byte, String>  OA_GRID_PARTY_STATUS_MAP = new LinkedHashMap<>();
    static {
        OA_GRID_PARTY_STATUS_MAP.put(OA_GRID_PARTY_INI, "未填报");
        OA_GRID_PARTY_STATUS_MAP.put(OA_GRID_PARTY_SAVE, "暂存");
        OA_GRID_PARTY_STATUS_MAP.put(OA_GRID_PARTY_REPORT, "已报送");
        OA_GRID_PARTY_STATUS_MAP.put(OA_GRID_PARTY_BACK, "已退回");
    }
}
