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

    public static final int LOG_CADRERESERVE = 53; // 优秀年轻干部信息
    //public static final int LOG_MEMBER = "mt_log_member_apply"; // 申请入党
    public static final int LOG_ABROAD = 60; // 因私出国
    public static final int LOG_CLA = 65; // 干部请假
    public static final int LOG_PCS = 70; // 党代会
    public static final int LOG_OA = 80; // 协同办公
    public static final int LOG_PMD = 90; // 党费收缴
    public static final int LOG_QY = 94; // 七一表彰
    public static final int LOG_PM = 95; // 三会一课
    public static final int LOG_CPC = 100; // 干部职数
    public static final int LOG_CRS = 110; // 干部竞争上岗
    public static final int LOG_CR = 111; // 干部竞争上岗2
    public static final int LOG_DR = 112; // 民主推荐
    public static final int LOG_SC_MOTION = 115; // 干部选拔任用-动议
    public static final int LOG_SC_RECORD = 116; // 干部选拔任用-纪实
    public static final int LOG_SC_MATTER = 120; // 干部选拔任用-个人有关事项
    public static final int LOG_SC_LETTER = 130; // 干部选拔任用-纪委函询管理
    public static final int LOG_SC_GROUP = 140; // 干部选拔任用-干部小组会议题
    public static final int LOG_SC_COMMITTEE = 150; // 干部选拔任用-党委常委会议题
    public static final int LOG_SC_PUBLIC = 160; // 干部选拔任用-干部任前公示
    public static final int LOG_SC_DISPATCH = 170; // 干部选拔任用-文件起草签发
    public static final int LOG_SC_AD = 180; // 干部选拔任用-干部任免审批表
    public static final int LOG_SC_PASSPORT = 190; // 干部选拔任用-新提任干部交证件
    public static final int LOG_SC_SUBSIDY = 195; // 干部选拔任用-干部津贴变动
    public static final int LOG_SC_BORDER = 198; // 干部选拔任用-出入境备案
    public static final int LOG_SC_SHIFT = 200; // 干部选拔任用-交流轮岗

    public static final int LOG_CET = 500; // 干部教育培训
    public static final int LOG_CET_INSPECTOR = 501; // 干部教育培训评课
    public static final int LOG_PS = 510; // 二级党校
    public static final int LOG_GROW = 520;//党员发展
    public static final int LOG_DPPARTY= 521; //民主党派党建
    public static final int LOG_DPMEMBER = 525;//民主党派成员信息
    public static final int LOG_OP = 530;//组织处理管理
    public static final int LOG_CG = 550; // 委员会和领导小组
    public static final int LOG_SP = 560;//八类代表
    public static final int LOG_PARTTIME_APPLY = 570;//兼职申报

    public final static Map<Integer, String> LOG_MAP = new LinkedHashMap<>();
    static {
        LOG_MAP.put(LOG_ADMIN, "系统管理");
        LOG_MAP.put(LOG_PARTY, "党建");
        LOG_MAP.put(LOG_USER, "用户操作");
        LOG_MAP.put(LOG_MEMBER, "党员信息");
        LOG_MAP.put(LOG_CADRE, "干部信息");

        LOG_MAP.put(LOG_CADRERESERVE, "优秀年轻干部信息");

        LOG_MAP.put(LOG_ABROAD, "因私出国");
        LOG_MAP.put(LOG_CLA, "干部请假");
        LOG_MAP.put(LOG_PCS, "党代会");
        LOG_MAP.put(LOG_OA, "协同办公");
        LOG_MAP.put(LOG_PMD, "党费收缴");
        LOG_MAP.put(LOG_QY, "七一表彰");
        LOG_MAP.put(LOG_PM, "三会一课");
        LOG_MAP.put(LOG_CPC, "干部职数");

        LOG_MAP.put(LOG_CRS, "干部竞争上岗");
        LOG_MAP.put(LOG_CR, "干部竞争上岗2");
        LOG_MAP.put(LOG_DR, "民主推荐");

        LOG_MAP.put(LOG_SC_MOTION, "动议");
        LOG_MAP.put(LOG_SC_RECORD, "纪实");
        LOG_MAP.put(LOG_SC_MATTER, "个人有关事项");
        LOG_MAP.put(LOG_SC_LETTER, "纪委函询管理");
        LOG_MAP.put(LOG_SC_GROUP, "干部小组会议题");
        LOG_MAP.put(LOG_SC_COMMITTEE, "党委常委会议题");

        LOG_MAP.put(LOG_SC_PUBLIC, "干部任前公示");
        LOG_MAP.put(LOG_SC_DISPATCH, "文件起草签发");
        LOG_MAP.put(LOG_SC_AD, "干部任免审批表");
        LOG_MAP.put(LOG_SC_PASSPORT, "新提任干部交证件");
        LOG_MAP.put(LOG_SC_SUBSIDY, "干部津贴变动");
        LOG_MAP.put(LOG_SC_SHIFT, "交流轮岗");

        LOG_MAP.put(LOG_CET, "干部教育培训");
        LOG_MAP.put(LOG_CET_INSPECTOR, "干部教育培训评课");

        LOG_MAP.put(LOG_PS, "二级党校");
        LOG_MAP.put(LOG_GROW,"党员发展");

        LOG_MAP.put(LOG_DPPARTY, "民主党派党建");
        LOG_MAP.put(LOG_DPMEMBER, "民主党派成员");
        LOG_MAP.put(LOG_OP, "组织处理管理");
        LOG_MAP.put(LOG_CG, "委员会和领导小组");

        LOG_MAP.put(LOG_SP,"八类代表");
        LOG_MAP.put(LOG_PARTTIME_APPLY, "兼职申报");
    }
}
