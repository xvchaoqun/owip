package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/2/8.
 */
public class ContentTplConstants {

    // 内容模板类别  1 短信
    public final static byte CONTENT_TPL_TYPE_SHORTMSG = 1;
    public final static byte CONTENT_TPL_TYPE_NORMAL = 2;
    public final static Map<Byte, String> CONTENT_TPL_TYPE_MAP = new LinkedHashMap<>();

    static {
        CONTENT_TPL_TYPE_MAP.put(CONTENT_TPL_TYPE_SHORTMSG, "短信");
        CONTENT_TPL_TYPE_MAP.put(CONTENT_TPL_TYPE_NORMAL, "文本");
    }

    // 内容模板内容类型  1 普通文本
    public final static byte CONTENT_TPL_CONTENT_TYPE_STRING = 1;
    public final static byte CONTENT_TPL_CONTENT_TYPE_HTML = 2;
    public final static Map<Byte, String> CONTENT_TPL_CONTENT_TYPE_MAP = new LinkedHashMap<>();

    static {
        CONTENT_TPL_CONTENT_TYPE_MAP.put(CONTENT_TPL_CONTENT_TYPE_STRING, "普通文本");
        CONTENT_TPL_CONTENT_TYPE_MAP.put(CONTENT_TPL_CONTENT_TYPE_HTML, "HTML");
    }

    // 内容模板引擎  1 MessageFormat
    public final static byte CONTENT_TPL_ENGINE_MESSAGEFORMAT = 1;
    public final static Map<Byte, String> CONTENT_TPL_ENGINE_MAP = new LinkedHashMap<>();

    static {
        CONTENT_TPL_ENGINE_MAP.put(CONTENT_TPL_ENGINE_MESSAGEFORMAT, "MessageFormat");
    }

    // 内容模板（与数据库中代码对应）
    public final static String CONTENT_TPL_FIND_PASS = "ct_find_pass"; // 密码找回模板

    /**因私出国境**/
    public final static String CONTENT_TPL_APPLYSELF_SUBMIT_INFO = "ct_applyself_submit_info"; // 干部提交因私申请，通知管理员
    public final static String CONTENT_TPL_APPLYSELF_PASS_INFO = "ct_applyself_pass_info"; // 干部因私申请通过全部领导审批，通知管理员
    public final static String CONTENT_TPL_PASSPORTDRAW_SUBMIT_INFO = "ct_passportDraw_submit_info"; // 干部提交领取证件，通知管理员
    public final static String CONTENT_TPL_PASSPORT_INFO = "ct_passport_info";
    public final static String CONTENT_TPL_PASSPORT_EXPIRE = "ct_passport_expire";
    public final static String CONTENT_TPL_PASSPORT_DISMISS = "ct_passport_dismiss";
    public final static String CONTENT_TPL_PASSPORT_ABOLISH = "ct_passport_abolish";
    public final static String CONTENT_TPL_PASSPORT_KEEP_ADD = "ct_passport_keep_add";
    public final static String CONTENT_TPL_PASSPORT_KEEP_APPLY = "ct_passport_keep_apply";
    public final static String CONTENT_TPL_APPLYSELF_PASS = "ct_applySelf_pass";
    public final static String CONTENT_TPL_APPLYSELF_UNPASS = "ct_applySelf_unpass";
    public final static String CONTENT_TPL_PASSPORTAPPLY_PASS = "ct_passportApply_pass";
    public final static String CONTENT_TPL_PASSPORTAPPLY_UNPASS = "ct_passportApply_unpass";
    public final static String CONTENT_TPL_PASSPORTAPPLY_DRAW = "ct_passportApply_draw";
    public final static String CONTENT_TPL_TAIWANRECORD_HANDLE = "ct_taiwanrecord_handle";
    public final static String CONTENT_TPL_PASSPORTAPPLY_SUBMIT = "ct_passportApply_submit";
    public final static String CONTENT_TPL_PASSPORTAPPLY_SUBMIT_ADMIN = "ct_passportApply_submit_admin";
    public final static String CONTENT_TPL_PASSPORTDRAW = "ct_passportDraw";
    public final static String CONTENT_TPL_PASSPORTDRAW_RETURN = "ct_passportDraw_return";
    public final static String CONTENT_TPL_PASSPORTDRAW_RETURN_SUCCESS = "ct_passportDraw_return_success";
    public final static String CONTENT_TPL_PASSPORTDRAW_PASS = "ct_passportDraw_pass";
    public final static String CONTENT_TPL_PASSPORTDRAW_PASS_NEEDSIGN = "ct_passportDraw_pass_needsign";
    public final static String CONTENT_TPL_PASSPORTDRAW_UNPASS = "ct_passportDraw_unpass";
    public final static String CONTENT_TPL_PASSPORTDRAW_UNPASS_NEEDSIGN = "ct_passportDraw_unpass_needsign";
    // 本单位正职（一人）审批通知
    public final static String CONTENT_TPL_APPLYSELF_APPROVAL_UNIT_1 = "ct_applyself_approval_unit_1";
    // 本单位正职（多人）审批通知
    public final static String CONTENT_TPL_APPLYSELF_APPROVAL_UNIT_2 = "ct_applyself_approval_unit_2";
    // 分管校领导审批通知
    public final static String CONTENT_TPL_APPLYSELF_APPROVAL_LEADER = "ct_applyself_approval_leader";
    // 书记审批通知
    public final static String CONTENT_TPL_APPLYSELF_APPROVAL_SECRETARY = "ct_applyself_approval_secretary";
    // 校长审批通知
    public final static String CONTENT_TPL_APPLYSELF_APPROVAL_MASTER = "ct_applyself_approval_master";

    // 党代表-邀请为提案附议人
    public final static String CONTENT_TPL_PCS_INVITE_SECONDER = "ct_pcs_invite_seconder";
    public final static String CONTENT_TPL_PCS_PROPOSAL_SUMIT_INFO = "ct_pcs_proposal_sumit_info";

    // 协同办公，通知指定负责人
    public final static String CONTENT_TPL_OA_INFO_USER = "ct_oa_info_user";

    /**党费收缴**/
    // 短信通知分党委
    public final static String CONTENT_TPL_PMD_NOTIFY_PARTY = "ct_pmd_notify_party";
    // 短信通知支部
    public final static String CONTENT_TPL_PMD_NOTIFY_BRANCH = "ct_pmd_notify_branch";
    // 短信通知全部支部党员
    public final static String CONTENT_TPL_PMD_NOTIFY_MEMBERS = "ct_pmd_notify_members";
    // 短信催促支部党员
    public final static String CONTENT_TPL_PMD_URGE_MEMBERS = "ct_pmd_urge_members";

    /*******
     * 干部招聘
     *******/
    // 通知1：预通知（没有确定时间和地点）
    public final static String CONTENT_TPL_CRS_MSG_1 = "ct_crs_msg_1";
    // 通知2：预通知（初步确定了时间，未确定地点）
    public final static String CONTENT_TPL_CRS_MSG_2 = "ct_crs_msg_2";
    // 通知3：正式通知（明确了时间和地点）
    public final static String CONTENT_TPL_CRS_MSG_3 = "ct_crs_msg_3";
    // 通知4：招聘会前一天提醒
    public final static String CONTENT_TPL_CRS_MSG_4 = "ct_crs_msg_4";
    // 通知5：招聘会前一小时提醒
    public final static String CONTENT_TPL_CRS_MSG_5 = "ct_crs_msg_5";

    public final static Map<String, String> CONTENT_TPL_CRS_MSG_MAP = new LinkedHashMap<>();
    static {
        CONTENT_TPL_CRS_MSG_MAP.put(CONTENT_TPL_CRS_MSG_1, "通知1：预通知（没有确定时间和地点）");
        CONTENT_TPL_CRS_MSG_MAP.put(CONTENT_TPL_CRS_MSG_2, "通知2：预通知（初步确定了时间，未确定地点）");
        CONTENT_TPL_CRS_MSG_MAP.put(CONTENT_TPL_CRS_MSG_3, "通知3：正式通知（明确了时间和地点）");
        CONTENT_TPL_CRS_MSG_MAP.put(CONTENT_TPL_CRS_MSG_4, "通知4：招聘会前一天提醒");
        CONTENT_TPL_CRS_MSG_MAP.put(CONTENT_TPL_CRS_MSG_5, "通知5：招聘会前一小时提醒");
    }

    /** 干部选拨 **/
    // 干部任前公示结束确认提醒
    public final static String CONTENT_TPL_SC_PUBLIC_FINISH_CONFIRM = "ct_sc_public_finish_confirm";
}
