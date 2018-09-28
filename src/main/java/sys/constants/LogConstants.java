package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/4/14.
 */
public class LogConstants {

    /** 系统日志分类 **/
    public static final int LOG_ADMIN = 10; // 系统管理
    public static final int LOG_PARTY = 20; // 党建
    public static final int LOG_USER = 30; // 用户操作
    public static final int LOG_MEMBER = 40; // 党员信息
    public static final int LOG_CADRE = 50; // 干部信息

    public static final int LOG_CADRERESERVE = 53; // 后备干部信息
    //public static final int LOG_MEMBER = "mt_log_member_apply"; // 申请入党
    public static final int LOG_ABROAD = 60; // 因私出国
    public static final int LOG_CLA = 65; // 干部请假
    public static final int LOG_PCS = 70; // 党代会
    public static final int LOG_OA = 80; // 协同办公
    public static final int LOG_PMD = 90; // 党费收缴
    public static final int LOG_CPC = 100; // 干部职数
    public static final int LOG_CRS = 110; // 干部竞争上岗
    public static final int LOG_SC_MOTION = 115; // 干部选拔任用-动议
    public static final int LOG_SC_MATTER = 120; // 干部选拔任用-个人有关事项
    public static final int LOG_SC_LETTER = 130; // 干部选拔任用-纪委函询管理
    public static final int LOG_SC_GROUP = 140; // 干部选拔任用-干部小组会议题
    public static final int LOG_SC_COMMITTEE = 150; // 干部选拔任用-党委常委会议题
    public static final int LOG_SC_PUBLIC = 160; // 干部选拔任用-干部任前公示
    public static final int LOG_SC_DISPATCH = 170; // 干部选拔任用-文件起草签发
    public static final int LOG_SC_AD = 180; // 干部选拔任用-干部任免审批表
    public static final int LOG_SC_PASSPORT = 190; // 干部选拔任用-新提任干部交证件
    public static final int LOG_SC_SUBSIDY = 195; // 干部选拔任用-干部津贴变动

    public static final int LOG_CET = 500; // 干部教育培训
    public static final int LOG_CET_INSPECTOR = 501; // 干部教育培训评课

    public final static Map<Integer, String> LOG_MAP = new LinkedHashMap<>();
    static {
        LOG_MAP.put(LOG_ADMIN, "系统管理");
        LOG_MAP.put(LOG_PARTY, "党建");
        LOG_MAP.put(LOG_USER, "用户操作");
        LOG_MAP.put(LOG_MEMBER, "党员信息");
        LOG_MAP.put(LOG_CADRE, "干部信息");

        LOG_MAP.put(LOG_CADRERESERVE, "后备干部信息");

        LOG_MAP.put(LOG_ABROAD, "因私出国");
        LOG_MAP.put(LOG_CLA, "干部请假");
        LOG_MAP.put(LOG_PCS, "党代会");
        LOG_MAP.put(LOG_OA, "协同办公");
        LOG_MAP.put(LOG_PMD, "党费收缴");
        LOG_MAP.put(LOG_CPC, "干部职数");

        LOG_MAP.put(LOG_CRS, "干部竞争上岗");

        LOG_MAP.put(LOG_SC_MOTION, "动议");
        LOG_MAP.put(LOG_SC_MATTER, "个人有关事项");
        LOG_MAP.put(LOG_SC_LETTER, "纪委函询管理");
        LOG_MAP.put(LOG_SC_GROUP, "干部小组会议题");
        LOG_MAP.put(LOG_SC_COMMITTEE, "党委常委会议题");

        LOG_MAP.put(LOG_SC_PUBLIC, "干部任前公示");
        LOG_MAP.put(LOG_SC_DISPATCH, "文件起草签发");
        LOG_MAP.put(LOG_SC_AD, "干部任免审批表");
        LOG_MAP.put(LOG_SC_PASSPORT, "新提任干部交证件");
        LOG_MAP.put(LOG_SC_SUBSIDY, "干部津贴变动");

        LOG_MAP.put(LOG_CET, "干部教育培训");
        LOG_MAP.put(LOG_CET_INSPECTOR, "干部教育培训评课");
    }
}
