package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/2/8.
 */
public class ModifyConstants {

    // 基本信息修改请求 审核状态，0 待审核 1 部分审核 2 全部审核 3管理员删除（待审核时才可以删除）
    public final static byte MODIFY_BASE_APPLY_STATUS_APPLY = 0;
    public final static byte MODIFY_BASE_APPLY_STATUS_PART_CHECK = 1;
    public final static byte MODIFY_BASE_APPLY_STATUS_ALL_CHECK = 2;
    public final static byte MODIFY_BASE_APPLY_STATUS_DELETE = 3;
    public final static Map<Byte, String> MODIFY_BASE_APPLY_STATUS_MAP = new LinkedHashMap<>();

    static {
        MODIFY_BASE_APPLY_STATUS_MAP.put(MODIFY_BASE_APPLY_STATUS_APPLY, "待审核");
        MODIFY_BASE_APPLY_STATUS_MAP.put(MODIFY_BASE_APPLY_STATUS_PART_CHECK, "部分审核");
        MODIFY_BASE_APPLY_STATUS_MAP.put(MODIFY_BASE_APPLY_STATUS_ALL_CHECK, "全部审核");
        MODIFY_BASE_APPLY_STATUS_MAP.put(MODIFY_BASE_APPLY_STATUS_DELETE, "已删除");
    }

    // 基本信息修改 字段类型
    public final static byte MODIFY_BASE_ITEM_TYPE_STRING = 1;
    public final static byte MODIFY_BASE_ITEM_TYPE_INT = 2;
    public final static byte MODIFY_BASE_ITEM_TYPE_DATE = 3;
    public final static byte MODIFY_BASE_ITEM_TYPE_IMAGE = 4;
    public final static Map<Byte, String> MODIFY_BASE_ITEM_TYPE_MAP = new LinkedHashMap<>();

    static {
        MODIFY_BASE_ITEM_TYPE_MAP.put(MODIFY_BASE_ITEM_TYPE_STRING, "字符串");
        MODIFY_BASE_ITEM_TYPE_MAP.put(MODIFY_BASE_ITEM_TYPE_INT, "数字");
        MODIFY_BASE_ITEM_TYPE_MAP.put(MODIFY_BASE_ITEM_TYPE_DATE, "日期");
        MODIFY_BASE_ITEM_TYPE_MAP.put(MODIFY_BASE_ITEM_TYPE_IMAGE, "图片");
    }


    // 基本信息修改 每个字段的审核状态，0 待审核 1 审核通过 2审核未通过
    public final static byte MODIFY_BASE_ITEM_STATUS_APPLY = 0;
    public final static byte MODIFY_BASE_ITEM_STATUS_PASS = 1;
    public final static byte MODIFY_BASE_ITEM_STATUS_DENY = 2;
    public final static Map<Byte, String> MODIFY_BASE_ITEM_STATUS_MAP = new LinkedHashMap<>();

    static {
        MODIFY_BASE_ITEM_STATUS_MAP.put(MODIFY_BASE_ITEM_STATUS_APPLY, "待审核");
        MODIFY_BASE_ITEM_STATUS_MAP.put(MODIFY_BASE_ITEM_STATUS_PASS, "审核通过");
        MODIFY_BASE_ITEM_STATUS_MAP.put(MODIFY_BASE_ITEM_STATUS_DENY, "审核未通过");
    }

    // 信息修改申请 类别
    public final static byte MODIFY_TABLE_APPLY_TYPE_ADD = 1;
    public final static byte MODIFY_TABLE_APPLY_TYPE_MODIFY = 2;
    public final static byte MODIFY_TABLE_APPLY_TYPE_DELETE = 3;
    public final static Map<Byte, String> MODIFY_TABLE_APPLY_TYPE_MAP = new LinkedHashMap<>();

    static {
        MODIFY_TABLE_APPLY_TYPE_MAP.put(MODIFY_TABLE_APPLY_TYPE_ADD, "添加");
        MODIFY_TABLE_APPLY_TYPE_MAP.put(MODIFY_TABLE_APPLY_TYPE_MODIFY, "修改");
        MODIFY_TABLE_APPLY_TYPE_MAP.put(MODIFY_TABLE_APPLY_TYPE_DELETE, "删除");
    }

    // 信息修改请求 审核状态，0 待审核 1 审核通过 2 审核未通过 3 管理员删除（待审核时才可以删除）
    public final static byte MODIFY_TABLE_APPLY_STATUS_APPLY = 0;
    public final static byte MODIFY_TABLE_APPLY_STATUS_PASS = 1;
    public final static byte MODIFY_TABLE_APPLY_STATUS_DENY = 2;
    public final static byte MODIFY_TABLE_APPLY_STATUS_DELETE = 3;
    public final static Map<Byte, String> MODIFY_TABLE_APPLY_STATUS_MAP = new LinkedHashMap<>();

    static {
        MODIFY_TABLE_APPLY_STATUS_MAP.put(MODIFY_TABLE_APPLY_STATUS_APPLY, "待审核");
        MODIFY_TABLE_APPLY_STATUS_MAP.put(MODIFY_TABLE_APPLY_STATUS_PASS, "审核通过");
        MODIFY_TABLE_APPLY_STATUS_MAP.put(MODIFY_TABLE_APPLY_STATUS_DENY, "审核未通过");
        MODIFY_TABLE_APPLY_STATUS_MAP.put(MODIFY_TABLE_APPLY_STATUS_DELETE, "已删除");
    }

    // 信息修改申请 模块
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_EDU = 1;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_WORK = 2;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK = 3;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY = 4;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE = 5;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER = 6;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME = 7;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH = 9;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH = 11;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER = 12;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN = 10;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT = 8;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN = 13;

    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO = 14;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN = 15;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_POSTWORK = 16;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_FAMLIY = 17;
    public final static byte MODIFY_TABLE_APPLY_MODULE_CADRE_FAMLIYABROAD = 18;

    public final static Map<Byte, String> MODIFY_TABLE_APPLY_MODULE_MAP = new LinkedHashMap<>();

    static {
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_EDU, "学习经历");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_WORK, "工作经历");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK, "出版著作情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY, "企业兼职情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE, "教学课程");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER, "发表论文情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME, "社会或学术兼职");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT, "主持科研项目情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN, "参与科研项目情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH, "教学成果及获奖情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH, "科研成果及获奖情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER, "其他奖励情况");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN, "培训情况");

        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO, "专技岗位过程信息");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN, "管理岗位过程信息");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_POSTWORK, "工勤岗位过程信息");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_FAMLIY, "家庭成员信息");
        MODIFY_TABLE_APPLY_MODULE_MAP.put(MODIFY_TABLE_APPLY_MODULE_CADRE_FAMLIYABROAD, "家庭成员移居国（境）外的情况");

    }

}
