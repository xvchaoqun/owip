package sys.constants;

import sys.utils.DateUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class DpConstants {

    //人大代表、政协委员信息
    public final static byte DP_PR_COUNTRY = 1;
    public final static byte DP_CM_COUNTRY = 2;
    public final static byte DP_PR_BEIJIGN = 3;
    public final static byte DP_CM_BEIJIGN = 4;
    public final static byte DP_PR_HAIDIAN = 5;
    public final static byte DP_CM_HAIDIAN = 6;
    public final static Map<Byte, String> DP_PR_CM_MAP = new LinkedHashMap<>();

    static {
        DP_PR_CM_MAP.put(DP_PR_COUNTRY, "全国人大代表");
        DP_PR_CM_MAP.put(DP_CM_COUNTRY, "全国政协委员");
        DP_PR_CM_MAP.put(DP_PR_BEIJIGN, "北京市人大代表");
        DP_PR_CM_MAP.put(DP_CM_BEIJIGN, "北京市政协委员");
        DP_PR_CM_MAP.put(DP_PR_HAIDIAN, "海淀区人大代表");
        DP_PR_CM_MAP.put(DP_CM_HAIDIAN, "海淀区政协委员");
    }

    //党员年龄段
    public final static byte DP_MEMBER_AGE_20 = 1;//20及以下
    public final static byte DP_MEMBER_AGE_21_30= 2;
    public final static byte DP_MEMBER_AGE_31_40 = 3;
    public final static byte DP_MEMBER_AGE_41_50 = 4;
    public final static byte DP_MEMBER_AGE_51 = 5;
    public final static byte DP_MEMBER_AGE_0 = 0;//未知
    public final static Map<Byte, String> DP_MEMBER_AGE_MAP = new LinkedHashMap<>();

    static {
        DP_MEMBER_AGE_MAP.put(DP_MEMBER_AGE_20, "20岁及以下");
        DP_MEMBER_AGE_MAP.put(DP_MEMBER_AGE_21_30, "21-30");
        DP_MEMBER_AGE_MAP.put(DP_MEMBER_AGE_31_40, "31-40");
        DP_MEMBER_AGE_MAP.put(DP_MEMBER_AGE_41_50, "41-50");
        DP_MEMBER_AGE_MAP.put(DP_MEMBER_AGE_51, "51及以上");
        DP_MEMBER_AGE_MAP.put(DP_MEMBER_AGE_0, "未知");
    }

    //党派成员类别，用于成员信息、流动成员、校内组织关系互转
    public final static byte DP_MEMBER_TYPE_TEACHER = 1;//教职工
    public final static byte DP_MEMBER_TYPE_STUDENT = 2;//学生
    public final static Map<Byte, String> DP_MEMBER_TYPE_MAP = new LinkedHashMap<>();

    static {
        DP_MEMBER_TYPE_MAP.put(DP_MEMBER_TYPE_TEACHER, "教工");
        DP_MEMBER_TYPE_MAP.put(DP_MEMBER_TYPE_STUDENT, "学生");
    }

    //成员状态，1正常，2已退休（弃用），3已出党，4已转出，5暂时转出（外出挂职、休学等）
    public final static byte DP_MEMBER_STATUS_NORMAL = 1; // 正常
    public final static byte DP_MEMBER_STATUS_RETIRE= 2; // 已退休
    public final static byte DP_MEMBER_STATUS_QUIT = 3; // 已出党
    public final static byte DP_MEMBER_STATUS_TRANSFER = 4; // 已转出
    //public final static byte DP_MEMBER_STATUS_TRANSFER_TEMP = 5; // 外出挂职、休学等
    public final static Map<Byte, String> DP_MEMBER_STATUS_MAP = new LinkedHashMap<>();

    static {
        DP_MEMBER_STATUS_MAP.put(DP_MEMBER_STATUS_NORMAL, "正常");
        DP_MEMBER_STATUS_MAP.put(DP_MEMBER_STATUS_RETIRE, "已退休");
        DP_MEMBER_STATUS_MAP.put(DP_MEMBER_STATUS_QUIT, "已出党");
        DP_MEMBER_STATUS_MAP.put(DP_MEMBER_STATUS_TRANSFER, "已转出");
        //DP_MEMBER_STATUS_MAP.put(DP_MEMBER_STATUS_TRANSFER_TEMP, "外出挂职、休学等");
    }

    //无党派人士状态，1无党派人士，2退出人士，3转出人士
    public final static byte DP_NPM_NORMAL = 1;
    public final static byte DP_NPM_OUT = 2;
    public final static byte DP_NPM_TRANSFER = 3;
    public final  static Map<Byte, String> DP_NPM_STATUS_MAP = new LinkedHashMap<>();

    static{
        DP_NPM_STATUS_MAP.put(DP_NPM_NORMAL, "无党派人士");
        DP_NPM_STATUS_MAP.put(DP_NPM_OUT, "退出人士");
        DP_NPM_STATUS_MAP.put(DP_NPM_TRANSFER, "转出人士");
    }

    public static byte getDpMemberAgeRange(Date birth){

        Date now = new Date();
        byte key = DP_MEMBER_AGE_0;//年龄未知
        if (birth != null){
            if(birth.after(DateUtils.getDateBeforeOrAfterYears(now, -21))){ // 20及以下
                key = DP_MEMBER_AGE_20;
            } else if (birth.after(DateUtils.getDateBeforeOrAfterYears(now, -31))) { // 21~30
                key = DP_MEMBER_AGE_21_30;
            } else if (birth.after(DateUtils.getDateBeforeOrAfterYears(now, -41))) { // 31~40
                key = DP_MEMBER_AGE_31_40;
            } else if (birth.after(DateUtils.getDateBeforeOrAfterYears(now, -51))) { // 41~50
                key = DP_MEMBER_AGE_41_50;
            } else { // 51及以上
                key =DP_MEMBER_AGE_51;
            }
        }
        return key;
    }

    // 党派成员政治面貌
    public final static byte DP_MEMBER_POLITICAL_STATUS_GROW = 1; //预备成员
    public final static byte DP_MEMBER_POLITICAL_STATUS_POSITIVE = 2; //正式成员
    public final static Map<Byte, String> DP_MEMBER_POLITICAL_STATUS_MAP = new LinkedHashMap<>();

    static {
        DP_MEMBER_POLITICAL_STATUS_MAP.put(DP_MEMBER_POLITICAL_STATUS_GROW, "预备成员");
        DP_MEMBER_POLITICAL_STATUS_MAP.put(DP_MEMBER_POLITICAL_STATUS_POSITIVE, "正式成员");
    }

    // 党派成员来源
    public final static byte DP_MEMBER_SOURCE_IMPORT = 1; // 后台导入
    public final static byte DP_MEMBER_SOURCE_GROW = 2; // 本校发展
    public final static byte DP_MEMBER_SOURCE_TRANSFER = 3; // 外校转入
    public final static byte DP_MEMBER_SOURCE_RETURNED = 4; // 归国人员恢复入党
    public final static byte DP_MEMBER_SOURCE_ADMIN = 5; // 后台添加
    public final static Map<Byte, String> DP_MEMBER_SOURCE_MAP = new LinkedHashMap<>();

    static {
        DP_MEMBER_SOURCE_MAP.put(DP_MEMBER_SOURCE_IMPORT, "后台导入");
        DP_MEMBER_SOURCE_MAP.put(DP_MEMBER_SOURCE_GROW, "本校发展");
        DP_MEMBER_SOURCE_MAP.put(DP_MEMBER_SOURCE_TRANSFER, "外校转入");
        DP_MEMBER_SOURCE_MAP.put(DP_MEMBER_SOURCE_RETURNED, "归国人员恢复党派成员身份");
        DP_MEMBER_SOURCE_MAP.put(DP_MEMBER_SOURCE_ADMIN, "后台添加");
    }

}
