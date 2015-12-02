package sys.constants;

import java.util.LinkedHashMap;
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

	// 性别， 1男 2女 0未知
	public static final byte GENDER_MALE = 1;
	public static final byte GENDER_FEMALE = 2;
	public static final byte GENDER_UNKNOWN =0;
	public final static Map<Byte, String> GENDER_MAP = new LinkedHashMap<>();
	static {
		GENDER_MAP.put(GENDER_MALE, "男");
		GENDER_MAP.put(GENDER_FEMALE, "女");
		GENDER_MAP.put(GENDER_UNKNOWN, "未知");
	}

	//单位状态，1正在运转单位、2历史单位
	public static final byte UNIT_STATUS_RUN = 1;
	public static final byte UNIT_STATUS_HISTORY = 2;

	// 账号类别，1教职工 2本科生 3研究生
	public final static byte USER_TYPE_JZG = 1;
	public final static byte USER_TYPE_BKS = 2;
	public final static byte USER_TYPE_YJS = 3;
	public final static byte USER_TYPE_OTHER= 4;
	public final static Map<Byte, String> USER_TYPE_MAP = new LinkedHashMap();
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
	public final static Map<Byte, String> USER_SOURCE_MAP = new LinkedHashMap();
	static {
		USER_SOURCE_MAP.put(USER_SOURCE_ADMIN, "后台创建");
		USER_SOURCE_MAP.put(USER_SOURCE_JZG, "人事库");
		USER_SOURCE_MAP.put(USER_SOURCE_BKS, "本科生库");
		USER_SOURCE_MAP.put(USER_SOURCE_YJS, "研究生库");
	}

	// 申请入党类型
	public final static byte APPLY_TYPE_STU= 1; // 学生
	public final static byte APPLY_TYPE_TECHER = 2; // 教职工
	public final static Map<Byte, String> APPLY_TYPE_MAP = new LinkedHashMap<>();
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
	public final static Map<Byte, String> APPLY_STAGE_MAP = new LinkedHashMap<>();
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
	public final static Map<Byte, String>MEMBER_POLITICAL_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_POLITICAL_STATUS_MAP.put(MEMBER_POLITICAL_STATUS_GROW, "预备党员");
		MEMBER_POLITICAL_STATUS_MAP.put(MEMBER_POLITICAL_STATUS_POSITIVE, "正式党员");
	}

	// 党员类别，用于党员信息、流动党员、校内组织关系互转
	public final static byte MEMBER_TYPE_TEACHER = 1; //教工
	public final static byte MEMBER_TYPE_STUDENT = 2; //学生
	public final static Map<Byte, String> MEMBER_TYPE_MAP = new LinkedHashMap<>();
	static {
		MEMBER_TYPE_MAP.put(MEMBER_TYPE_TEACHER, "教工");
		MEMBER_TYPE_MAP.put(MEMBER_TYPE_STUDENT, "学生");
	}

	// 党员状态, 1正常，2已退休 3已出党 4已转出 5暂时转出（外出挂职、休学等）
	public final static byte MEMBER_STATUS_NORMAL= 1; // 已入党（正常）
	public final static byte MEMBER_STATUS_RETIRE= 2; // 已退休
	public final static byte MEMBER_STATUS_QUIT= 3; // 已出党
	public final static byte MEMBER_STATUS_TRANSFER= 4; // 已转出
	public final static byte MEMBER_STATUS_TRANSFER_TEMP= 5; // 暂时转出

	// 党员来源
	public final static byte MEMBER_SOURCE_IMPORT = 1; // 建系统时统一导入
	public final static byte MEMBER_SOURCE_GROW = 2; // 本校发展
	public final static byte MEMBER_SOURCE_TRANSFER = 3; // 外校转入
	public final static byte MEMBER_SOURCE_RETURNED = 4; // 归国人员恢复入党
	public final static Map<Byte, String>MEMBER_SOURCE_MAP = new LinkedHashMap<>();
	static {
		MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_IMPORT, "建系统时统一导入");
		MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_GROW, "本校发展");
		MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_TRANSFER, "外校转入");
		MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_RETURNED, "归国人员恢复入党");
	}

	// 党员退休审核状态
	public final static byte RETIRE_APPLY_STATUS_UNCHECKED = 0; // 未审核
	public final static byte RETIRE_APPLY_STATUS_CHECKED = 1; // 已审核

	// 党员出党审核状态
	public final static byte RETIRE_QUIT_STATUS_UNCHECKED = 0; // 未审核
	public final static byte RETIRE_QUIT_STATUS_CHECKED = 1; // 已审核

	// 出党类别，1自动退党 2开除党籍 3党员去世
	public final static byte RETIRE_QUIT_TYPE_SELF = 1;
	public final static byte RETIRE_QUIT_TYPE_DISMISS = 2;
	public final static byte RETIRE_QUIT_TYPE_WITHGOD = 3;
	public final static Map<Byte, String> RETIRE_QUIT_TYPE_MAP = new LinkedHashMap<>();
	static {
		RETIRE_QUIT_TYPE_MAP.put(RETIRE_QUIT_TYPE_SELF, "自动退党");
		RETIRE_QUIT_TYPE_MAP.put(RETIRE_QUIT_TYPE_DISMISS, "开除党籍");
		RETIRE_QUIT_TYPE_MAP.put(RETIRE_QUIT_TYPE_WITHGOD, "党员去世");
	}


	// 组织关系状态
	public final static byte OR_STATUS_OUT = 1;
	public final static byte OR_STATUS_NOT_OUT = 2;
	public final static Map<Byte, String> OR_STATUS_MAP = new LinkedHashMap<>();
	static {
		OR_STATUS_MAP.put(OR_STATUS_OUT, "已转出");
		OR_STATUS_MAP.put(OR_STATUS_NOT_OUT, "未转出");
	}
	// 留学归国人员申请恢复组织生活状态
	public final static byte MEMBER_RETURN_STATUS_APPLY = 0;
	public final static byte MEMBER_RETURN_STATUS_BRANCH_VERIFY= 1;
	public final static byte MEMBER_RETURN_STATUS_PARTY_VERIFY = 2;
	public final static Map<Byte, String> MEMBER_RETURN_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_RETURN_STATUS_MAP.put(MEMBER_RETURN_STATUS_APPLY, "申请");
		MEMBER_RETURN_STATUS_MAP.put(MEMBER_RETURN_STATUS_BRANCH_VERIFY, "支部审核");
		MEMBER_RETURN_STATUS_MAP.put(MEMBER_RETURN_STATUS_PARTY_VERIFY, "分党委审核");
	}

	// 党员转入转出类别
	public final static byte MEMBER_INOUT_TYPE_INSIDE= 1;
	public final static byte MEMBER_INOUT_TYPE_OUTSIDE = 2;
	public final static Map<Byte, String> MEMBER_INOUT_TYPE_MAP = new LinkedHashMap<>();
	static {
		MEMBER_INOUT_TYPE_MAP.put(MEMBER_INOUT_TYPE_INSIDE, "京内");
		MEMBER_INOUT_TYPE_MAP.put(MEMBER_INOUT_TYPE_OUTSIDE, "京外");
	}

	// 党员转出状态
	public final static byte MEMBER_OUT_STATUS_BACK = -1;
	public final static byte MEMBER_OUT_STATUS_APPLY = 0;
	public final static byte MEMBER_OUT_STATUS_PARTY_VERIFY= 1;
	public final static byte MEMBER_OUT_STATUS_OW_VERIFY= 2;
	public final static Map<Byte, String> MEMBER_OUT_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_BACK, "返回修改");
		MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_APPLY, "申请");
		MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_PARTY_VERIFY, "分党委审核");
		MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_OW_VERIFY, "组织部审核");
	}

	// 党员转入状态
	public final static byte MEMBER_IN_STATUS_BACK = -1;
	public final static byte MEMBER_IN_STATUS_APPLY = 0;
	public final static byte MEMBER_IN_STATUS_PARTY_VERIFY= 1;
	public final static byte MEMBER_IN_STATUS_OW_VERIFY= 2;
	public final static Map<Byte, String> MEMBER_IN_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_BACK, "返回修改");
		MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_APPLY, "申请");
		MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_PARTY_VERIFY, "分党委审核");
		MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_OW_VERIFY, "组织部审核");
	}

	// 校内组织关系互转状态，-1返回修改 0申请 1转出分党委审批 2转入分党委审批
	public final static byte MEMBER_TRANSFER_STATUS_BACK = -1;
	public final static byte MEMBER_TRANSFER_STATUS_APPLY = 0;
	public final static byte MEMBER_TRANSFER_STATUS_FROM_VERIFY= 1;
	public final static byte MEMBER_TRANSFER_STATUS_TO_VERIFY= 2;
	public final static Map<Byte, String> MEMBER_TRANSFER_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_BACK, "返回修改");
		MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_APPLY, "申请");
		MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_FROM_VERIFY, "转出分党委审核");
		MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_TO_VERIFY, "转入分党委审批");
	}

	// 公派留学生党员申请组织关系暂留状态，状态，-1返回修改 0申请 1分党委审批 2组织部审批
	public final static byte MEMBER_STAY_STATUS_BACK = -1;
	public final static byte MEMBER_STAY_STATUS_APPLY = 0;
	public final static byte MEMBER_STAY_STATUS_PARTY_VERIFY= 1;
	public final static byte MEMBER_STAY_STATUS_OW_VERIFY= 2;
	public final static Map<Byte, String> MEMBER_STAY_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_BACK, "返回修改");
		MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_APPLY, "申请");
		MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_PARTY_VERIFY, "分党委审批");
		MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_OW_VERIFY, "组织部审批");
	}


	// 干部库类别 1现任干部库  2 临时干部库 3离任干部库
	public final static byte CADRE_STATUS_NOW = 1;
	public final static byte CADRE_STATUS_TEMP = 2;
	public final static byte CADRE_STATUS_LEAVE = 3;
	public final static Map<Byte, String>CADRE_STATUS_MAP = new LinkedHashMap<>();
	static {
		CADRE_STATUS_MAP.put(CADRE_STATUS_NOW, "现任干部库");
		CADRE_STATUS_MAP.put(CADRE_STATUS_TEMP, "临时干部库");
		CADRE_STATUS_MAP.put(CADRE_STATUS_LEAVE, "离任干部库");
	}

	// 干部教学课程类别
	public final static byte CADRE_COURSE_TYPE_BKS = 1;
	public final static byte CADRE_COURSE_TYPE_SS = 2;
	public final static byte CADRE_COURSE_TYPE_BS = 3;
	public final static Map<Byte, String>CADRE_COURSE_TYPE_MAP = new LinkedHashMap<>();
	static {
		CADRE_COURSE_TYPE_MAP.put(CADRE_COURSE_TYPE_BKS, "本科生课程");
		CADRE_COURSE_TYPE_MAP.put(CADRE_COURSE_TYPE_SS, "硕士生课程");
		CADRE_COURSE_TYPE_MAP.put(CADRE_COURSE_TYPE_BS, "研究生课程");
	}

	// 干部获奖类别 1,教学成果及获奖情况 2科研成果及获奖情况， 3其他奖励情况
	public final static byte CADRE_REWARD_TYPE_TEACH = 1;
	public final static byte CADRE_REWARD_TYPE_RESEARCH = 2;
	public final static byte CADRE_REWARD_TYPE_OTHER = 3;

	// 称谓，1父亲，2母亲， 3配偶， 4儿子， 5女儿
	public final static byte CADRE_FAMLIY_TITLE_FATHER = 1;
	public final static byte CADRE_FAMLIY_TITLE_MOTHER = 2;
	public final static byte CADRE_FAMLIY_TITLE_MATE = 3;
	public final static byte CADRE_FAMLIY_TITLE_SON = 4;
	public final static byte CADRE_FAMLIY_TITLE_DAUGHTER = 5;
	public final static Map<Byte, String>CADRE_FAMLIY_TITLE_MAP = new LinkedHashMap<>();
	static {
		CADRE_FAMLIY_TITLE_MAP.put(CADRE_FAMLIY_TITLE_FATHER, "父亲");
		CADRE_FAMLIY_TITLE_MAP.put(CADRE_FAMLIY_TITLE_MOTHER, "母亲");
		CADRE_FAMLIY_TITLE_MAP.put(CADRE_FAMLIY_TITLE_MATE, "配偶");
		CADRE_FAMLIY_TITLE_MAP.put(CADRE_FAMLIY_TITLE_SON, "儿子");
		CADRE_FAMLIY_TITLE_MAP.put(CADRE_FAMLIY_TITLE_DAUGHTER, "女儿");
	}
}
