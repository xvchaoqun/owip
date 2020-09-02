package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/2/8.
 */
public class PcsConstants {

    // 党代会管理，推荐人类型，  1 党委委员 2 纪委委员 3 代表
    public final static byte PCS_USER_TYPE_DW = 1;
    public final static byte PCS_USER_TYPE_JW = 2;
    public final static byte PCS_USER_TYPE_PR = 3;
    public final static Map<Byte, String> PCS_USER_TYPE_MAP = new LinkedHashMap<>();

    static {
        PCS_USER_TYPE_MAP.put(PCS_USER_TYPE_DW, "党委委员");
        PCS_USER_TYPE_MAP.put(PCS_USER_TYPE_JW, "纪委委员");
        PCS_USER_TYPE_MAP.put(PCS_USER_TYPE_PR, "代表");
    }

    /*// 党代会管理员类型， 1 书记 2 副书记 3 普通管理员（通常由书记指定一人）
    public final static byte PCS_ADMIN_TYPE_SECRETARY = 1;
    public final static byte PCS_ADMIN_TYPE_VICE_SECRETARY = 2;
    public final static byte PCS_ADMIN_TYPE_NORMAL = 3;
    public final static Map<Byte, String> PCS_ADMIN_TYPE_MAP = new LinkedHashMap<>();

    static {
        PCS_ADMIN_TYPE_MAP.put(PCS_ADMIN_TYPE_SECRETARY, "书记");
        PCS_ADMIN_TYPE_MAP.put(PCS_ADMIN_TYPE_VICE_SECRETARY, "副书记");
        PCS_ADMIN_TYPE_MAP.put(PCS_ADMIN_TYPE_NORMAL, "普通管理员");
    }*/

    // 党代会阶段，1 一下一上 2 二下二上 3 三下三上
    public final static byte PCS_STAGE_FIRST = 1;
    public final static byte PCS_STAGE_SECOND = 2;
    public final static byte PCS_STAGE_THIRD = 3;
    public final static Map<Byte, String> PCS_STAGE_MAP = new LinkedHashMap<>();

    static {
        PCS_STAGE_MAP.put(PCS_STAGE_FIRST, "一下一上");
        PCS_STAGE_MAP.put(PCS_STAGE_SECOND, "二下二上");
        PCS_STAGE_MAP.put(PCS_STAGE_THIRD, "三下三上");
    }

    // 党代表类型，1 专业技术人员和干部 2 学生代表 3 离退休代表
    public final static byte PCS_PR_TYPE_PRO = 1;
    public final static byte PCS_PR_TYPE_STU = 2;
    public final static byte PCS_PR_TYPE_RETIRE = 3;
    public final static Map<Byte, String> PCS_PR_TYPE_MAP = new LinkedHashMap<>();

    static {
        PCS_PR_TYPE_MAP.put(PCS_PR_TYPE_PRO, "专业技术人员和干部");
        PCS_PR_TYPE_MAP.put(PCS_PR_TYPE_STU, "学生代表");
        PCS_PR_TYPE_MAP.put(PCS_PR_TYPE_RETIRE, "离退休代表");
    }
    // 党代表用户类型，1 干部 2 普通教师 3 学生
    public final static byte PCS_PR_USER_TYPE_CADRE = 1;
    public final static byte PCS_PR_USER_TYPE_TEACHER = 2;
    public final static byte PCS_PR_USER_TYPE_STU = 3;
    public final static Map<Byte, String> PCS_PR_USER_TYPE_MAP = new LinkedHashMap<>();

    static {
        PCS_PR_USER_TYPE_MAP.put(PCS_PR_USER_TYPE_CADRE, "干部");
        PCS_PR_USER_TYPE_MAP.put(PCS_PR_USER_TYPE_TEACHER, "普通教师");
        PCS_PR_USER_TYPE_MAP.put(PCS_PR_USER_TYPE_STU, "学生");
    }
    // 党代表名单填报审核情况，0 待审核 1 审核通过 2 审核不通过
    public final static byte PCS_PR_RECOMMEND_STATUS_INIT = 0;
    public final static byte PCS_PR_RECOMMEND_STATUS_PASS = 1;
    public final static byte PCS_PR_RECOMMEND_STATUS_DENY = 2;
    public final static Map<Byte, String> PCS_PR_RECOMMEND_STATUS_MAP = new LinkedHashMap<>();

    static {
        PCS_PR_RECOMMEND_STATUS_MAP.put(PCS_PR_RECOMMEND_STATUS_INIT, "待审核");
        PCS_PR_RECOMMEND_STATUS_MAP.put(PCS_PR_RECOMMEND_STATUS_PASS, "审核通过");
        PCS_PR_RECOMMEND_STATUS_MAP.put(PCS_PR_RECOMMEND_STATUS_DENY, "审核不通过");
    }

    // 党代表提案状态，暂存、未审核、审核未通过、征集附议人、立案处理、并案处理、不予立案、处理完成
    public final static byte PCS_PROPOSAL_STATUS_SAVE = 0;
    public final static byte PCS_PROPOSAL_STATUS_INIT = 1;
    public final static byte PCS_PROPOSAL_STATUS_DENY = 2;
    public final static byte PCS_PROPOSAL_STATUS_PASS = 3;
    public final static byte PCS_PROPOSAL_STATUS_CASE = 4;
    public final static byte PCS_PROPOSAL_STATUS_TOGETHER = 5;
    public final static byte PCS_PROPOSAL_STATUS_NOT_CASE = 6;
    public final static byte PCS_PROPOSAL_STATUS_FINISH = 7;
    public final static Map<Byte, String> PCS_PROPOSAL_STATUS_MAP = new LinkedHashMap<>();

    static {
        PCS_PROPOSAL_STATUS_MAP.put(PCS_PROPOSAL_STATUS_SAVE, "暂存");
        PCS_PROPOSAL_STATUS_MAP.put(PCS_PROPOSAL_STATUS_INIT, "未审核");
        PCS_PROPOSAL_STATUS_MAP.put(PCS_PROPOSAL_STATUS_DENY, "审核未通过");
        PCS_PROPOSAL_STATUS_MAP.put(PCS_PROPOSAL_STATUS_PASS, "征集附议人");
        PCS_PROPOSAL_STATUS_MAP.put(PCS_PROPOSAL_STATUS_CASE, "立案处理");
        PCS_PROPOSAL_STATUS_MAP.put(PCS_PROPOSAL_STATUS_TOGETHER, "并案处理");
        PCS_PROPOSAL_STATUS_MAP.put(PCS_PROPOSAL_STATUS_NOT_CASE, "不予立案");
        PCS_PROPOSAL_STATUS_MAP.put(PCS_PROPOSAL_STATUS_FINISH, "处理完成");
    }

    public final static byte PCS_POLL_FIRST_STAGE = 1;
    public final static byte PCS_POLL_SECOND_STAGE = 2;
    public final static byte PCS_POLL_THIRD_STAGE = 3;
    public final static Map<Byte, String> PCS_POLL_STAGE_MAP = new LinkedHashMap<>();

    static {
        PCS_POLL_STAGE_MAP.put(PCS_POLL_FIRST_STAGE, "一下阶段");
        PCS_POLL_STAGE_MAP.put(PCS_POLL_SECOND_STAGE, "二下阶段");
        PCS_POLL_STAGE_MAP.put(PCS_POLL_THIRD_STAGE, "三下阶段");
    }

    // 推荐结果状态
    public final static byte RESULT_STATUS_AGREE = 1;
    public final static byte RESULT_STATUS_DISAGREE = 2;
    public final static byte RESULT_STATUS_ABSTAIN = 3;
    public final static byte RESULT_STATUS_OTHER = 4;
    public final static Map<Byte, String> RESULT_STATUS_MAP = new LinkedHashMap<Byte, String>();

    static {

        RESULT_STATUS_MAP.put(RESULT_STATUS_AGREE, "同意");
        RESULT_STATUS_MAP.put(RESULT_STATUS_DISAGREE, "不同意");
        RESULT_STATUS_MAP.put(RESULT_STATUS_ABSTAIN, "弃权");
        RESULT_STATUS_MAP.put(RESULT_STATUS_OTHER, "另选他人");
    }
}
