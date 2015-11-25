package sys.constants;

import java.util.HashMap;
import java.util.Map;

public class SystemConstants {

	// 日期范围分隔符（用于查询时的输入框）
	public static final String DATERANGE_SEPARTOR = " 至 ";

	// 账号的角色字符串分隔符
	public static final String USER_ROLEIDS_SEPARTOR = ",";

	// 系统角色（与数据库对应的角色字符串不可以修改！）
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_GUEST = "guest";
	public static final String ROLE_MEMBER = "member";


	public static final String LOG_LOGIN = "mt_log_login";
	public static final String LOG_ADMIN = "mt_log_admin";
	public static final String LOG_OW = "mt_log_ow";
	public static final String LOG_MEMBER_APPLY = "mt_log_member_apply";

	public static final String RESOURCE_TYPE_FUNCTION = "function";
	public static final String RESOURCE_TYPE_URL = "url";
	public static final String RESOURCE_TYPE_MENU = "menu";

	public static final byte AVAILABLE = 1;
	public static final byte UNAVAILABLE = 0;

	// 性别， 1男 2女 3未知
	public static final byte GENDER_MALE = 1;
	public static final byte GENDER_FEMALE = 2;
	public static final byte GENDER_UNKNOWN =3;
	public final static Map<Byte, String> GENDER_MALE_MAP = new HashMap();
	static {
		GENDER_MALE_MAP.put(GENDER_MALE, "男");
		GENDER_MALE_MAP.put(GENDER_FEMALE, "女");
		GENDER_MALE_MAP.put(GENDER_UNKNOWN, "未知");
	}

	//单位状态，1正在运转单位、2历史单位
	public static final byte UNIT_STATUS_RUN = 1;
	public static final byte UNIT_STATUS_HISTORY = 2;

	// 账号类别，1教职工 2本科生 3研究生
	public final static byte USER_TYPE_JZG = 1;
	public final static byte USER_TYPE_BKS = 2;
	public final static byte USER_TYPE_YJS = 3;
	public final static byte USER_TYPE_OTHER= 4;
	public final static Map<Byte, String> USER_TYPE_MAP = new HashMap();
	static {
		USER_TYPE_MAP.put(USER_TYPE_JZG, "教职工");
		USER_TYPE_MAP.put(USER_TYPE_BKS, "本科生");
		USER_TYPE_MAP.put(USER_TYPE_YJS, "研究生");
		USER_TYPE_MAP.put(USER_TYPE_OTHER, "其他");
	}

	// 账号来源 0 后台创建 1人事库、2本科生库 3 研究生库
	public final static byte USER_SOURCE_ADMIN = 0;
	public final static byte USER_SOURCE_JZG = 1;
	public final static byte USER_SOURCE_BKS = 2;
	public final static byte USER_SOURCE_YJS = 3;
	public final static Map<Byte, String> USER_SOURCE_MAP = new HashMap();
	static {
		USER_SOURCE_MAP.put(USER_SOURCE_ADMIN, "后台创建");
		USER_SOURCE_MAP.put(USER_SOURCE_JZG, "人事库");
		USER_SOURCE_MAP.put(USER_SOURCE_BKS, "本科生库");
		USER_SOURCE_MAP.put(USER_SOURCE_YJS, "研究生库");
	}

	// 申请入党类型
	public final static byte APPLY_TYPE_STU= 1; // 学生
	public final static byte APPLY_TYPE_TECHER = 2; // 教职工
	public final static Map<Byte, String> APPLY_TYPE_MAP = new HashMap<>();
	static {
		APPLY_TYPE_MAP.put(APPLY_TYPE_STU, "学生");
		APPLY_TYPE_MAP.put(APPLY_TYPE_TECHER, "教职工");
	}

	// 申请入党阶段
	//0不通过 1申请  2入党积极分子 3发展对象（积极分子满一年）4列入发展计划 5领取志愿书 6预备党员 7正式党员
	public final static byte APPLY_STAGE_DENY = -1; // 未通过
	public final static byte APPLY_STAGE_INIT = 0; // 申请
	public final static byte APPLY_STAGE_PASS = 1; // 通过
	public final static byte APPLY_STAGE_ACTIVE = 2; // 积极分子
	public final static byte APPLY_STAGE_CANDIDATE = 3; // 发展对象
	public final static byte APPLY_STAGE_PLAN = 4; // 列入发展计划
	public final static byte APPLY_STAGE_DRAW = 5; // 领取志愿书
	public final static byte APPLY_STAGE_GROW = 6; // 预备党员
	public final static byte APPLY_STAGE_POSITIVE = 7; // 正式党员
	public final static Map<Byte, String> APPLY_STAGE_MAP = new HashMap<>();
	static {
		APPLY_STAGE_MAP.put(APPLY_STAGE_INIT, "申请");
		APPLY_STAGE_MAP.put(APPLY_STAGE_ACTIVE, "积极分子");
		APPLY_STAGE_MAP.put(APPLY_STAGE_CANDIDATE, "发展对象");
		APPLY_STAGE_MAP.put(APPLY_STAGE_PLAN, "列入发展计划");
		APPLY_STAGE_MAP.put(APPLY_STAGE_DRAW, "领取志愿书");
		APPLY_STAGE_MAP.put(APPLY_STAGE_GROW, "预备党员");
		APPLY_STAGE_MAP.put(APPLY_STAGE_POSITIVE, "正式党员");
	}

	// 申请入党审核状态
	public final static byte APPLY_STATUS_UNCHECKED = 0; // 未审核
	public final static byte APPLY_STATUS_CHECKED = 1; // 已审核
	public final static byte APPLY_STATUS_OD_CHECKED = 2; // 组织部已审核，成为预备党员和正式党员时

	// 党员政治面貌
	public final static byte MEMBER_POLITICAL_STATUS_GROW = 1; //预备党员
	public final static byte MEMBER_POLITICAL_STATUS_POSITIVE = 2; //正式党员
	public final static Map<Byte, String>MEMBER_POLITICAL_STATUS_MAP = new HashMap<>();
	static {
		MEMBER_POLITICAL_STATUS_MAP.put(MEMBER_POLITICAL_STATUS_GROW, "预备党员");
		MEMBER_POLITICAL_STATUS_MAP.put(MEMBER_POLITICAL_STATUS_POSITIVE, "正式党员");
	}

	// 党员类别
	public final static byte MEMBER_TYPE_TEACHER = 1; //在职教职工党员
	public final static byte MEMBER_TYPE_RETIRE = 2; //离退休党员
	public final static byte MEMBER_TYPE_STUDENT= 3; //学生党员

	// 党员状态
	public final static byte MEMBER_STATUS_NORMAL= 1; // 已入党（正常）
	public final static byte MEMBER_STATUS_QUIT= 2; // 已出党

	// 党员来源
	public final static byte MEMBER_SOURCE_GROW = 1; // 本校发展
	public final static byte MEMBER_SOURCE_TRANSFER = 2; // 外校转入
	public final static byte MEMBER_SOURCE_IMPORT = 3; // 建系统时统一导入
	public final static Map<Byte, String>MEMBER_SOURCE_MAP = new HashMap<>();
	static {
		MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_GROW, "本校发展");
		MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_TRANSFER, "外校转入");
		MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_IMPORT, "建系统时统一导入");
	}
}
