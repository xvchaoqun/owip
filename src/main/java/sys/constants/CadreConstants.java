package sys.constants;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lm on 2018/4/14.
 */
public class CadreConstants {

    // 干部审批表样式
    public final static byte CADRE_ADFORMTYPE_BJ = 1; // 北京
    public final static byte CADRE_ADFORMTYPE_ZZB_GB2312 = 2; // 中组部
    public final static byte CADRE_ADFORMTYPE_ZZB_SONG = 3; // 中组部标准
    public final static Map<Byte, String> CADRE_ADFORMTYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_ADFORMTYPE_MAP.put(CADRE_ADFORMTYPE_BJ, "北京");
        CADRE_ADFORMTYPE_MAP.put(CADRE_ADFORMTYPE_ZZB_SONG, "中组部(宋体)");
        CADRE_ADFORMTYPE_MAP.put(CADRE_ADFORMTYPE_ZZB_GB2312, "中组部(仿宋_GB2312)");
    }

    // 干部库类别
    public final static byte CADRE_STATUS_NOT_CADRE = 0; // 非干部（添加到党员干部库时，需要初始化到干部库）
    public final static byte CADRE_STATUS_INSPECT = 2;
    public final static byte CADRE_STATUS_RESERVE = 5;
    public final static byte CADRE_STATUS_MIDDLE_LEAVE = 3;
    public final static byte CADRE_STATUS_MIDDLE = 1;
    public final static byte CADRE_STATUS_LEADER_LEAVE = 4;
    public final static byte CADRE_STATUS_LEADER = 6;
    public final static byte CADRE_STATUS_RECRUIT = 7; // 应聘干部

    public final static Map<Byte, String> CADRE_STATUS_MAP = new LinkedHashMap<>();
    public final static Set<Byte> CADRE_STATUS_SET = new HashSet<>(); // 干部角色对应的所有状态
    public final static Set<Byte> CADRE_STATUS_NOW_SET = new HashSet<>(); // 现任干部角色对应的所有状态
    public final static Set<Byte> CADRE_STATUS_LEAVE_SET = new HashSet<>(); // 离任干部角色对应的所有状态

    static {
        CADRE_STATUS_MAP.put(CADRE_STATUS_LEADER, "现任校领导");
        CADRE_STATUS_MAP.put(CADRE_STATUS_LEADER_LEAVE, "离任校领导");
        CADRE_STATUS_MAP.put(CADRE_STATUS_MIDDLE, "现任干部");
        CADRE_STATUS_MAP.put(CADRE_STATUS_MIDDLE_LEAVE, "离任干部");
        CADRE_STATUS_MAP.put(CADRE_STATUS_INSPECT, "考察对象"); // 非干部角色
        CADRE_STATUS_MAP.put(CADRE_STATUS_RESERVE, "年轻干部库"); // 非干部角色
        CADRE_STATUS_MAP.put(CADRE_STATUS_RECRUIT, "应聘干部");
        CADRE_STATUS_MAP.put(CADRE_STATUS_NOT_CADRE, "临时干部档案"); // 无角色

        CADRE_STATUS_SET.add(CADRE_STATUS_MIDDLE);
        CADRE_STATUS_SET.add(CADRE_STATUS_MIDDLE_LEAVE);
        CADRE_STATUS_SET.add(CADRE_STATUS_LEADER);
        CADRE_STATUS_SET.add(CADRE_STATUS_LEADER_LEAVE);

        CADRE_STATUS_NOW_SET.add(CADRE_STATUS_MIDDLE);
        CADRE_STATUS_NOW_SET.add(CADRE_STATUS_LEADER);

        CADRE_STATUS_LEAVE_SET.add(CADRE_STATUS_MIDDLE_LEAVE);
        CADRE_STATUS_LEAVE_SET.add(CADRE_STATUS_LEADER_LEAVE);
    }

    // 干部类别
    public final static byte CADRE_TYPE_CJ = 1; // 处级干部
    public final static byte CADRE_TYPE_KJ = 2; // 科级干部
    public final static byte CADRE_TYPE_OTHER = 10; // 其他
    public final static Map<Byte, String> CADRE_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_TYPE_MAP.put(CADRE_TYPE_CJ, "处级干部");
        CADRE_TYPE_MAP.put(CADRE_TYPE_KJ, "科级干部");
    }

    // 干部党派类别
   // public final static byte CADRE_PARTY_TYPE_NONE = 0; // 无，此时党派由党员库确认
    public final static byte CADRE_PARTY_TYPE_DP = 1; // 民主党派
    public final static byte CADRE_PARTY_TYPE_OW = 2; // 中共党员
    public final static Map<Byte, String> CADRE_PARTY_TYPE_MAP = new LinkedHashMap<>();

    static {
        //CADRE_PARTY_TYPE_MAP.put(CADRE_PARTY_TYPE_NONE, "无");
        CADRE_PARTY_TYPE_MAP.put(CADRE_PARTY_TYPE_DP, "民主党派");
        CADRE_PARTY_TYPE_MAP.put(CADRE_PARTY_TYPE_OW, "中共党员");

    }

    // 干部历史数据类别
    public final static byte CADRE_STAT_HISTORY_TYPE_CADRE_MIDDLE = 1;
    public final static byte CADRE_STAT_HISTORY_TYPE_STAT_CADRE_CJ = 2;
    public final static byte CADRE_STAT_HISTORY_TYPE_STAT_CPC = 3;
    public final static byte CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT = 4;
    public final static byte CADRE_STAT_HISTORY_TYPE_STAT_CADRE_KJ = 5;
    public final static byte CADRE_STAT_HISTORY_TYPE_STAT_CPC_KJ = 6;
    public final static byte CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT_KJ = 7;
    public final static Map<Byte, String> CADRE_STAT_HISTORY_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_STAT_HISTORY_TYPE_MAP.put(CADRE_STAT_HISTORY_TYPE_CADRE_MIDDLE, "干部信息表");
        CADRE_STAT_HISTORY_TYPE_MAP.put(CADRE_STAT_HISTORY_TYPE_STAT_CADRE_CJ, "处级干部情况统计表");
        CADRE_STAT_HISTORY_TYPE_MAP.put(CADRE_STAT_HISTORY_TYPE_STAT_CPC, "处级干部职数配置情况统计表");
        CADRE_STAT_HISTORY_TYPE_MAP.put(CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT, "内设机构处级干部配备统计表");
        CADRE_STAT_HISTORY_TYPE_MAP.put(CADRE_STAT_HISTORY_TYPE_STAT_CADRE_KJ, "科级干部情况统计表");
        CADRE_STAT_HISTORY_TYPE_MAP.put(CADRE_STAT_HISTORY_TYPE_STAT_CPC_KJ, "科级干部职数配置情况统计表");
        CADRE_STAT_HISTORY_TYPE_MAP.put(CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT_KJ, "内设机构科级干部配备统计表");

    }

    // 优秀年轻干部产生方式 1 学校党委推荐、 2 学校组织部门推荐、 3 所在单位推荐、4 所在基层党组织推荐
    public final static byte CADRE_RESERVE_ORIGIN_WAY_XXDWTJ = 1;
    public final static byte CADRE_RESERVE_ORIGIN_WAY_ZZBTJ = 2;
    public final static byte CADRE_RESERVE_ORIGIN_WAY_SZDWTJ = 3;
    public final static byte CADRE_RESERVE_ORIGIN_WAY_DZZTJ = 4;
    public final static Map<Byte, String> CADRE_RESERVE_ORIGIN_WAY_MAP = new LinkedHashMap<>();

    static {
        CADRE_RESERVE_ORIGIN_WAY_MAP.put(CADRE_RESERVE_ORIGIN_WAY_XXDWTJ, "学校党委推荐");
        CADRE_RESERVE_ORIGIN_WAY_MAP.put(CADRE_RESERVE_ORIGIN_WAY_ZZBTJ, "学校组织部门推荐");
        CADRE_RESERVE_ORIGIN_WAY_MAP.put(CADRE_RESERVE_ORIGIN_WAY_SZDWTJ, "所在单位推荐");
        CADRE_RESERVE_ORIGIN_WAY_MAP.put(CADRE_RESERVE_ORIGIN_WAY_DZZTJ, "所在基层党组织推荐");
    }

    // 优秀年轻干部库状态
    public final static byte CADRE_RESERVE_STATUS_NORMAL = 1;
    public final static byte CADRE_RESERVE_STATUS_TO_INSPECT = 2;
    public final static byte CADRE_RESERVE_STATUS_ASSIGN = 3;
    public final static byte CADRE_RESERVE_STATUS_ABOLISH = 4;
    public final static Map<Byte, String> CADRE_RESERVE_STATUS_MAP = new LinkedHashMap<>();

    static {
        CADRE_RESERVE_STATUS_MAP.put(CADRE_RESERVE_STATUS_NORMAL, "年轻干部");
        CADRE_RESERVE_STATUS_MAP.put(CADRE_RESERVE_STATUS_TO_INSPECT, "已列为考察对象");
        //CADRE_RESERVE_STATUS_MAP.put(CADRE_RESERVE_STATUS_ASSIGN, "年轻干部已使用");
        CADRE_RESERVE_STATUS_MAP.put(CADRE_RESERVE_STATUS_ASSIGN, "已使用");
        CADRE_RESERVE_STATUS_MAP.put(CADRE_RESERVE_STATUS_ABOLISH, "已撤销资格");
    }

    // 干部任免操作类别
    public final static byte CADRE_AD_LOG_MODULE_CADRE = 1;
    public final static byte CADRE_AD_LOG_MODULE_INSPECT = 2;
    public final static byte CADRE_AD_LOG_MODULE_RESERVE = 3;
    public final static Map<Byte, String> CADRE_AD_LOG_MODULE_MAP = new LinkedHashMap<>();

    static {
        CADRE_AD_LOG_MODULE_MAP.put(CADRE_AD_LOG_MODULE_CADRE, "干部库");
        CADRE_AD_LOG_MODULE_MAP.put(CADRE_AD_LOG_MODULE_INSPECT, "考察对象");
        CADRE_AD_LOG_MODULE_MAP.put(CADRE_AD_LOG_MODULE_RESERVE, "优秀年轻干部");
    }

    // 考察对象类别，保留字段
    public final static byte CADRE_INSPECT_TYPE_DEFAULT = 1;

    // 考察对象状态 1考察对象 2 考察对象已任命 3 已撤销资格
    public final static byte CADRE_INSPECT_STATUS_NORMAL = 1;
    public final static byte CADRE_INSPECT_STATUS_ASSIGN = 2;
    public final static byte CADRE_INSPECT_STATUS_ABOLISH = 3;
    public final static Map<Byte, String> CADRE_INSPECT_STATUS_MAP = new LinkedHashMap<>();

    static {
        CADRE_INSPECT_STATUS_MAP.put(CADRE_INSPECT_STATUS_NORMAL, "考察对象");
        CADRE_INSPECT_STATUS_MAP.put(CADRE_INSPECT_STATUS_ASSIGN, "通过常委会任命");
        CADRE_INSPECT_STATUS_MAP.put(CADRE_INSPECT_STATUS_ABOLISH, "已撤销资格");
    }

    // 干部干部信息采集表 类型
    public final static byte CADRE_INFO_TYPE_WORK = 1;
    public final static byte CADRE_INFO_TYPE_PARTTIME = 2;
    public final static byte CADRE_INFO_TYPE_TRAIN = 3;
    public final static byte CADRE_INFO_TYPE_TEACH = 4;
    public final static byte CADRE_INFO_TYPE_RESEARCH = 5;
    public final static byte CADRE_INFO_TYPE_REWARD_OTHER = 6;
    public final static byte CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY = 7;
    public final static byte CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY = 8;
    public final static byte CADRE_INFO_TYPE_BOOK_SUMMARY = 9;
    public final static byte CADRE_INFO_TYPE_EDU = 10;
    public final static byte CADRE_INFO_TYPE_PAPER_SUMMARY = 11;
    public final static byte CADRE_INFO_TYPE_RESEARCH_REWARD = 12;
    public final static byte CADRE_INFO_TYPE_RESUME = 13;
    public final static byte CADRE_INFO_TYPE_REWARD = 14;

    public final static Map<Byte, String> CADRE_INFO_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_WORK, "工作经历");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_PARTTIME, "社会或学术兼职");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_TRAIN, "培训情况");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_TEACH, "教学经历");// 包含教学成果及获奖情况

        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_REWARD_OTHER, "其他奖励情况");

        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY, "参与科研项目");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY, "主持科研项目");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_BOOK_SUMMARY, "出版著作");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_PAPER_SUMMARY, "发表论文");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_RESEARCH_REWARD, "科研成果及获奖");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_RESEARCH, "科研情况");

        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_EDU, "学习经历");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_RESUME, "干部任免审批表简历");
        CADRE_INFO_TYPE_MAP.put(CADRE_INFO_TYPE_REWARD, "干部任免审批表奖惩情况");
    }

    // 干部学习经历 学校类型 1本校 2境内 3境外
    public final static byte CADRE_SCHOOL_TYPE_THIS_SCHOOL = 1;
    public final static byte CADRE_SCHOOL_TYPE_DOMESTIC = 2;
    public final static byte CADRE_SCHOOL_TYPE_ABROAD = 3;
    public final static Map<Byte, String> CADRE_SCHOOL_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_SCHOOL_TYPE_MAP.put(CADRE_SCHOOL_TYPE_THIS_SCHOOL, "本校");
        CADRE_SCHOOL_TYPE_MAP.put(CADRE_SCHOOL_TYPE_DOMESTIC, "境内");
        CADRE_SCHOOL_TYPE_MAP.put(CADRE_SCHOOL_TYPE_ABROAD, "境外");
    }

    // 干部学习经历 导师类型 1硕士研究生导师 2 博士研究生导师
    public final static byte CADRE_TUTOR_TYPE_MASTER = 1;
    public final static byte CADRE_TUTOR_TYPE_DOCTOR = 2;
    public final static Map<Byte, String> CADRE_TUTOR_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_TUTOR_TYPE_MAP.put(CADRE_TUTOR_TYPE_MASTER, "硕士研究生导师");
        CADRE_TUTOR_TYPE_MAP.put(CADRE_TUTOR_TYPE_DOCTOR, "博士研究生导师");
    }

    // 干部教学课程类别
    public final static byte CADRE_COURSE_TYPE_BKS = 1;
    public final static byte CADRE_COURSE_TYPE_SS = 2;
    public final static byte CADRE_COURSE_TYPE_BS = 3;
    public final static Map<Byte, String> CADRE_COURSE_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_COURSE_TYPE_MAP.put(CADRE_COURSE_TYPE_BKS, "本科生课程");
        CADRE_COURSE_TYPE_MAP.put(CADRE_COURSE_TYPE_SS, "硕士生课程");
        CADRE_COURSE_TYPE_MAP.put(CADRE_COURSE_TYPE_BS, "博士生课程");
    }

    // 干部获奖类别 1,教学成果及获奖情况 2科研成果及获奖情况， 3其他奖励情况
    public final static byte CADRE_REWARD_TYPE_TEACH = 1;
    public final static byte CADRE_REWARD_TYPE_RESEARCH = 2;
    public final static byte CADRE_REWARD_TYPE_OTHER = 3;

    // 干部科研项目类别 1,主持 2 参与
    public final static byte CADRE_RESEARCH_TYPE_DIRECT = 1;
    public final static byte CADRE_RESEARCH_TYPE_IN = 2;

    // 干部出版著作类别 独著、译著、合著
    public final static byte CADRE_BOOK_TYPE_ALONE = 1;
    public final static byte CADRE_BOOK_TYPE_TRANSLATE = 2;
    public final static byte CADRE_BOOK_TYPE_COAUTHOR = 3;
    public final static Map<Byte, String> CADRE_BOOK_TYPE_MAP = new LinkedHashMap<>();

    static {
        CADRE_BOOK_TYPE_MAP.put(CADRE_BOOK_TYPE_ALONE, "独著");
        CADRE_BOOK_TYPE_MAP.put(CADRE_BOOK_TYPE_TRANSLATE, "译著");
        CADRE_BOOK_TYPE_MAP.put(CADRE_BOOK_TYPE_COAUTHOR, "合著");
    }

    // 干部信息检查结果
    public final static byte CADRE_INFO_CHECK_RESULT_NOT_EXIST = 0;
    public final static byte CADRE_INFO_CHECK_RESULT_EXIST = 1;
    public final static byte CADRE_INFO_CHECK_RESULT_MODIFY = 2;  // 存在修改申请
    public final static Map<Byte, String> CADRE_INFO_CHECK_RESULT_MAP = new LinkedHashMap<>();

    static {
        CADRE_INFO_CHECK_RESULT_MAP.put(CADRE_INFO_CHECK_RESULT_NOT_EXIST, "否");
        CADRE_INFO_CHECK_RESULT_MAP.put(CADRE_INFO_CHECK_RESULT_EXIST, "是");
        CADRE_INFO_CHECK_RESULT_MAP.put(CADRE_INFO_CHECK_RESULT_MODIFY, "已申请修改，待审核");
    }

    // 干部树key类型
    public final static byte CADRETREE_KEY_CADREID = 1;
    public final static byte CADRETREE_KEY_USERID = 2;
    public final static byte CADRETREE_KEY_CADREID_UNITID = 3;
}
