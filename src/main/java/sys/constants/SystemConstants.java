package sys.constants;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SystemConstants {

	public final static Map<String, String> appKeyMap = new HashMap<>();
	static {

		appKeyMap.put("LXXT", "7507a3c61bf38d9f06d00c3f2fa2de58");
	}

	public static Map loginFailedResultMap(String message){

		Map<String, Object> resultMap = new HashMap();
		resultMap.put("success", false);
		if ("SystemClosedException".equals(message)) {

			resultMap.put("msg", "参评人员测评未开启");
		} else if ("IncorrectCredentialsException".equals(message)) {
			resultMap.put("msg", "账号或密码错误");
		} else if ("UnknownAccountException".equals(message)) {
			resultMap.put("msg", "账号或密码错误");
		} else if ("IncorrectCaptchaException".equals(message)) {
			resultMap.put("msg", "验证码错误");
		} else if ("LockedAccountException".equals(message)) {
			resultMap.put("msg", "账号被锁定");
		} else if ("ExcessiveAttemptsException".equals(message)) {
			resultMap.put("msg", "登录过于频繁，请稍后再试");
		}else if ("InspectorFinishException".equals(message)) {
			resultMap.put("msg", "该账号已经测评完成");
		}else if("SSOException".equals(message)){
			resultMap.put("msg", "单点登录服务器错误，请稍后重试");
		}else {
			resultMap.put("msg", "系统错误");
		}
		return resultMap;
	}

	// 日期范围分隔符（用于查询时的输入框）
	public static final String DATERANGE_SEPARTOR = " 至 ";

	// 账号的角色字符串分隔符
	public static final String USER_ROLEIDS_SEPARTOR = ",";

	// 系统角色（与数据库对应的角色字符串不可以修改！）
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_GUEST = "guest";
	public static final String ROLE_REG = "reg"; // 注册用户，未审核通过
	public static final String ROLE_CADRE= "cadre";
	public static final String ROLE_MEMBER = "member";
	public static final String ROLE_INFLOWMEMBER = "inflowMember";
	public static final String ROLE_PARTYADMIN = "partyAdmin";
	public static final String ROLE_BRANCHADMIN = "branchAdmin";
	public static final String ROLE_ODADMIN = "odAdmin";
	public static final String ROLE_CADREADMIN = "cadreAdmin";
	public final static Map<String, String> ROLE_MAP = new LinkedHashMap<>();
	static {
		ROLE_MAP.put(ROLE_ADMIN, "系统管理员");
		ROLE_MAP.put(ROLE_GUEST, "非党员");
		ROLE_MAP.put(ROLE_REG, "注册用户");
		ROLE_MAP.put(ROLE_CADRE, "干部");
		ROLE_MAP.put(ROLE_MEMBER, "党员");
		ROLE_MAP.put(ROLE_INFLOWMEMBER, "流入党员");
		ROLE_MAP.put(ROLE_PARTYADMIN, "分党委管理员");
		ROLE_MAP.put(ROLE_BRANCHADMIN, "党支部管理员");
		ROLE_MAP.put(ROLE_ODADMIN, "组织部管理员");
		ROLE_MAP.put(ROLE_CADREADMIN, "干部管理员");
	}

	// 系统配置字段：
	// 因私出国（境）申请说明
	public static final String SYS_CONFIG_APPLY_SELF_NOTE = "sc_apply_self_note";
	// 因私出国（境）审批说明
	public static final String SYS_CONFIG_APPLY_SELF_APPROVAL_NOTE = "sc_apply_self_approval_note ";
	// 申请使用因私出国（境）证件说明
	public static final String SYS_CONFIG_PASSPORT_DRAW_NOTE = "sc_passport_draw_note ";
/*	public final static Map<String, String> SYS_CONFIG_MAP = new LinkedHashMap<>();
	static {
		SYS_CONFIG_MAP.put(SYS_CONFIG_APPLY_SELF_NOTE, "因私出国境申请说明");
		SYS_CONFIG_MAP.put(SYS_CONFIG_APPLY_SELF_APPROVAL_NOTE, "因私出国境审批说明");
	}*/

	// 登录类型：1 网站 2 下次自动登录 2 CAS 3 移动设备
	public static final byte LOGIN_TYPE_NET = 1;
	public static final byte LOGIN_TYPE_NET_REMEBERME = 2;
	public static final byte LOGIN_TYPE_CAS = 3;
	public static final byte LOGIN_TYPE_MOBILE =4;
	public final static Map<Byte, String> LOGIN_TYPE_MAP = new LinkedHashMap<>();
	static {
		LOGIN_TYPE_MAP.put(LOGIN_TYPE_NET, "网站");
		LOGIN_TYPE_MAP.put(LOGIN_TYPE_NET_REMEBERME, "下次自动登录");
		LOGIN_TYPE_MAP.put(LOGIN_TYPE_CAS, "单点登录");
		LOGIN_TYPE_MAP.put(LOGIN_TYPE_MOBILE, "移动设备");
	}

	//public static final String LOG_LOGIN = "mt_log_login"; 登录单独记录
	public static final String LOG_ADMIN = "mt_log_admin";
	public static final String LOG_OW = "mt_log_ow";
	public static final String LOG_USER = "mt_log_user";
	public static final String LOG_MEMBER = "mt_log_member";
	public static final String LOG_MEMBER_APPLY = "mt_log_member_apply";
	public static final String LOG_ABROAD = "mt_log_abroad";

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

	// 账号来源 0 后台创建 1人事库、2本科生库 3 研究生库
	public final static byte USER_REG_STATUS_APPLY = 0;
	public final static byte USER_REG_STATUS_DENY = -1;
	public final static byte USER_REG_STATUS_PASS = 1;
	public final static Map<Byte, String> USER_REG_STATUS_MAP = new LinkedHashMap();
	static {
		USER_REG_STATUS_MAP.put(USER_REG_STATUS_APPLY, "申请");
		USER_REG_STATUS_MAP.put(USER_REG_STATUS_DENY, "申请不通过");
		USER_REG_STATUS_MAP.put(USER_REG_STATUS_PASS, "申请通过");
	}

	// 账号类别，1教职工 2本科生 3研究生
	public final static byte USER_TYPE_JZG = 1;
	public final static byte USER_TYPE_BKS = 2;
	public final static byte USER_TYPE_YJS = 3;
	public final static Map<Byte, String> USER_TYPE_MAP = new LinkedHashMap();
	static {
		USER_TYPE_MAP.put(USER_TYPE_JZG, "教职工");
		USER_TYPE_MAP.put(USER_TYPE_BKS, "本科生");
		USER_TYPE_MAP.put(USER_TYPE_YJS, "研究生");
	}

	// 同步类型，1人事库 2研究库 3本科生库 4教职工党员出国信息库
	public final static byte SYNC_TYPE_JZG = 1;
	public final static byte SYNC_TYPE_BKS = 2;
	public final static byte SYNC_TYPE_YJS = 3;
	public final static byte SYNC_TYPE_ABROAD = 4;
	public final static Map<Byte, String> SYNC_TYPE_MAP = new LinkedHashMap();
	static {
		SYNC_TYPE_MAP.put(SYNC_TYPE_JZG, "人事库");
		SYNC_TYPE_MAP.put(SYNC_TYPE_BKS, "本科生库");
		SYNC_TYPE_MAP.put(SYNC_TYPE_YJS, "研究生库");
		SYNC_TYPE_MAP.put(SYNC_TYPE_ABROAD, "教职工党员出国境信息库");
	}

	// 账号来源 0 后台创建 1人事库、2本科生库 3 研究生库
	public final static byte USER_SOURCE_ADMIN = 0;
	public final static byte USER_SOURCE_JZG = 1;
	public final static byte USER_SOURCE_BKS = 2;
	public final static byte USER_SOURCE_YJS = 3;
	public final static byte USER_SOURCE_REG = 4;
	public final static Map<Byte, String> USER_SOURCE_MAP = new LinkedHashMap();
	static {
		USER_SOURCE_MAP.put(USER_SOURCE_ADMIN, "后台创建");
		USER_SOURCE_MAP.put(USER_SOURCE_JZG, "人事库");
		USER_SOURCE_MAP.put(USER_SOURCE_BKS, "本科生库");
		USER_SOURCE_MAP.put(USER_SOURCE_YJS, "研究生库");
		USER_SOURCE_MAP.put(USER_SOURCE_REG, "用户注册");
	}

	// 权限开通申请状态，0申请 1本人撤销 2 管理员撤回 3通过；
	public final static byte ENTER_APPLY_STATUS_APPLY= 0;
	public final static byte ENTER_APPLY_STATUS_SELF_ABORT = 1;
	public final static byte ENTER_APPLY_STATUS_ADMIN_ABORT = 2;
	public final static byte ENTER_APPLY_STATUS_PASS = 3;
	public final static Map<Byte, String> ENTER_APPLY_STATUS_MAP = new LinkedHashMap<>();
	static {
		ENTER_APPLY_STATUS_MAP.put(ENTER_APPLY_STATUS_APPLY, "申请");
		ENTER_APPLY_STATUS_MAP.put(ENTER_APPLY_STATUS_SELF_ABORT, "本人撤销");
		ENTER_APPLY_STATUS_MAP.put(ENTER_APPLY_STATUS_ADMIN_ABORT, "管理员撤回");
		ENTER_APPLY_STATUS_MAP.put(ENTER_APPLY_STATUS_PASS, "通过");
	}

	// 权限开通申请类别，1申请入党 2 留学归国申请 3转入申请  4 流入申请
	public final static byte ENTER_APPLY_TYPE_MEMBERAPPLY= 1;
	public final static byte ENTER_APPLY_TYPE_RETURN = 2;
	public final static byte ENTER_APPLY_TYPE_MEMBERIN = 3;
	public final static byte ENTER_APPLY_TYPE_MEMBERINFLOW = 4;
	public final static Map<Byte, String> ENTER_APPLY_TYPE_MAP = new LinkedHashMap<>();
	static {
		ENTER_APPLY_TYPE_MAP.put(ENTER_APPLY_TYPE_MEMBERAPPLY, "申请入党");
		ENTER_APPLY_TYPE_MAP.put(ENTER_APPLY_TYPE_RETURN, "留学归国党员申请");
		ENTER_APPLY_TYPE_MAP.put(ENTER_APPLY_TYPE_MEMBERIN, "组织关系转入");
		ENTER_APPLY_TYPE_MAP.put(ENTER_APPLY_TYPE_MEMBERINFLOW, "流入党员申请");
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
		APPLY_STAGE_MAP.put(APPLY_STAGE_DENY, "未通过");
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

	// 党员状态, 1正常，2已退休(弃用) 3已出党 4已转出 5暂时转出（外出挂职、休学等）
	public final static byte MEMBER_STATUS_NORMAL= 1; // 正常
	//public final static byte MEMBER_STATUS_RETIRE= 2; // 已退休
	public final static byte MEMBER_STATUS_QUIT= 3; // 已出党
	public final static byte MEMBER_STATUS_TRANSFER= 4; // 已转出
	public final static byte MEMBER_STATUS_TRANSFER_TEMP= 5; // 外出挂职、休学等
	public final static Map<Byte, String> MEMBER_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_STATUS_MAP.put(MEMBER_STATUS_NORMAL, "正常");
		//MEMBER_STATUS_MAP.put(MEMBER_STATUS_RETIRE, "已退休");
		MEMBER_STATUS_MAP.put(MEMBER_STATUS_QUIT, "已出党");
		MEMBER_STATUS_MAP.put(MEMBER_STATUS_TRANSFER, "已转出");
		MEMBER_STATUS_MAP.put(MEMBER_STATUS_TRANSFER_TEMP, "外出挂职、休学等");
	}

	// 党员来源
	public final static byte MEMBER_SOURCE_IMPORT = 1; // 2015年底统一导入
	public final static byte MEMBER_SOURCE_GROW = 2; // 本校发展
	public final static byte MEMBER_SOURCE_TRANSFER = 3; // 外校转入
	public final static byte MEMBER_SOURCE_RETURNED = 4; // 归国人员恢复入党
	public final static byte MEMBER_SOURCE_ADMIN = 5; // 后台添加
	public final static Map<Byte, String>MEMBER_SOURCE_MAP = new LinkedHashMap<>();
	static {
		MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_IMPORT, "2015年底统一导入");
		MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_GROW, "本校发展");
		MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_TRANSFER, "外校转入");
		MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_RETURNED, "归国人员恢复入党");
		MEMBER_SOURCE_MAP.put(MEMBER_SOURCE_ADMIN, "后台添加");
	}

	// 党员退休审核状态
	public final static byte RETIRE_APPLY_STATUS_UNCHECKED = 0; // 未审核
	public final static byte RETIRE_APPLY_STATUS_CHECKED = 1; // 已审核

	// 出党类别，1自动退党 2开除党籍 3党员去世
	public final static byte MEMBER_QUIT_TYPE_SELF = 1;
	public final static byte MEMBER_QUIT_TYPE_DISMISS = 2;
	public final static byte MEMBER_QUIT_TYPE_WITHGOD = 3;
	public final static Map<Byte, String> MEMBER_QUIT_TYPE_MAP = new LinkedHashMap<>();
	static {
		MEMBER_QUIT_TYPE_MAP.put(MEMBER_QUIT_TYPE_SELF, "自动退党");
		MEMBER_QUIT_TYPE_MAP.put(MEMBER_QUIT_TYPE_DISMISS, "开除党籍");
		MEMBER_QUIT_TYPE_MAP.put(MEMBER_QUIT_TYPE_WITHGOD, "党员去世");
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
	public final static byte MEMBER_RETURN_STATUS_DENY = -1;
	public final static byte MEMBER_RETURN_STATUS_APPLY = 0;
	public final static byte MEMBER_RETURN_STATUS_BRANCH_VERIFY= 1;
	public final static byte MEMBER_RETURN_STATUS_PARTY_VERIFY = 2;
	public final static Map<Byte, String> MEMBER_RETURN_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_RETURN_STATUS_MAP.put(MEMBER_RETURN_STATUS_DENY, "不通过");
		MEMBER_RETURN_STATUS_MAP.put(MEMBER_RETURN_STATUS_APPLY, "申请");
		MEMBER_RETURN_STATUS_MAP.put(MEMBER_RETURN_STATUS_BRANCH_VERIFY, "支部审核通过");
		MEMBER_RETURN_STATUS_MAP.put(MEMBER_RETURN_STATUS_PARTY_VERIFY, "分党委审核通过");
	}

	// 党员转入转出类别
	public final static byte MEMBER_INOUT_TYPE_INSIDE= 1;
	public final static byte MEMBER_INOUT_TYPE_OUTSIDE = 2;
	public final static Map<Byte, String> MEMBER_INOUT_TYPE_MAP = new LinkedHashMap<>();
	static {
		MEMBER_INOUT_TYPE_MAP.put(MEMBER_INOUT_TYPE_INSIDE, "京内");
		MEMBER_INOUT_TYPE_MAP.put(MEMBER_INOUT_TYPE_OUTSIDE, "京外");
	}

	// 党员出党状态
	public final static byte MEMBER_QUIT_STATUS_SELF_BACK = -2;
	public final static byte MEMBER_QUIT_STATUS_BACK = -1;
	public final static byte MEMBER_QUIT_STATUS_APPLY = 0;
	public final static byte MEMBER_QUIT_STATUS_BRANCH_VERIFY = 1;
	public final static byte MEMBER_QUIT_STATUS_PARTY_VERIFY= 2;
	public final static byte MEMBER_QUIT_STATUS_OW_VERIFY= 3;
	public final static Map<Byte, String> MEMBER_QUIT_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_SELF_BACK, "本人撤回");
		MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_BACK, "返回修改");
		MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_APPLY, "申请");
		MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_BRANCH_VERIFY, "支部审核通过");
		MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_PARTY_VERIFY, "分党委审核通过");
		MEMBER_QUIT_STATUS_MAP.put(MEMBER_QUIT_STATUS_OW_VERIFY, "组织部审核通过");
	}

	// 党员转出状态
	public final static byte MEMBER_OUT_STATUS_SELF_BACK = -2;
	public final static byte MEMBER_OUT_STATUS_BACK = -1;
	public final static byte MEMBER_OUT_STATUS_APPLY = 0;
	public final static byte MEMBER_OUT_STATUS_PARTY_VERIFY= 1;
	public final static byte MEMBER_OUT_STATUS_OW_VERIFY= 2;
	public final static Map<Byte, String> MEMBER_OUT_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_SELF_BACK, "本人撤回");
		MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_BACK, "返回修改");
		MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_APPLY, "申请");
		MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_PARTY_VERIFY, "分党委审核通过");
		MEMBER_OUT_STATUS_MAP.put(MEMBER_OUT_STATUS_OW_VERIFY, "组织部审核通过");
	}

	// 党员转入状态
	public final static byte MEMBER_IN_STATUS_SELF_BACK = -2;
	public final static byte MEMBER_IN_STATUS_BACK = -1;
	public final static byte MEMBER_IN_STATUS_APPLY = 0;
	public final static byte MEMBER_IN_STATUS_PARTY_VERIFY= 1;
	public final static byte MEMBER_IN_STATUS_OW_VERIFY= 2;
	public final static Map<Byte, String> MEMBER_IN_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_SELF_BACK, "本人撤回");
		MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_BACK, "返回修改");
		MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_APPLY, "申请");
		MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_PARTY_VERIFY, "分党委审核通过");
		MEMBER_IN_STATUS_MAP.put(MEMBER_IN_STATUS_OW_VERIFY, "组织部审核通过");
	}

	// 校内组织关系互转状态，-1返回修改 0申请 1转出分党委审批 2转入分党委审批
	public final static byte MEMBER_TRANSFER_STATUS_SELF_BACK = -2;
	public final static byte MEMBER_TRANSFER_STATUS_BACK = -1;
	public final static byte MEMBER_TRANSFER_STATUS_APPLY = 0;
	public final static byte MEMBER_TRANSFER_STATUS_FROM_VERIFY= 1;
	public final static byte MEMBER_TRANSFER_STATUS_TO_VERIFY= 2;
	public final static Map<Byte, String> MEMBER_TRANSFER_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_SELF_BACK, "本人撤回");
		MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_BACK, "返回修改");
		MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_APPLY, "申请");
		MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_FROM_VERIFY, "转出分党委审核通过");
		MEMBER_TRANSFER_STATUS_MAP.put(MEMBER_TRANSFER_STATUS_TO_VERIFY, "转入分党委审核通过");
	}

	// 公派留学生党员申请组织关系暂留状态，状态，-2本人撤回 -1返回修改 0申请 1分党委审批 2组织部审批
	public final static byte MEMBER_STAY_STATUS_SELF_BACK = -2;
	public final static byte MEMBER_STAY_STATUS_BACK = -1;
	public final static byte MEMBER_STAY_STATUS_APPLY = 0;
	public final static byte MEMBER_STAY_STATUS_PARTY_VERIFY= 1;
	public final static byte MEMBER_STAY_STATUS_OW_VERIFY= 2;
	public final static Map<Byte, String> MEMBER_STAY_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_SELF_BACK, "本人撤回");
		MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_BACK, "返回修改");
		MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_APPLY, "申请");
		MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_PARTY_VERIFY, "分党委审核通过");
		MEMBER_STAY_STATUS_MAP.put(MEMBER_STAY_STATUS_OW_VERIFY, "组织部审核通过");
	}

	// 党员流入状态
	public final static byte MEMBER_INFLOW_STATUS_BACK = -1; // 本人撤回、审核不通过
	public final static byte MEMBER_INFLOW_STATUS_APPLY = 0;
	public final static byte MEMBER_INFLOW_STATUS_BRANCH_VERIFY= 1;
	public final static byte MEMBER_INFLOW_STATUS_PARTY_VERIFY= 2;
	public final static Map<Byte, String> MEMBER_INFLOW_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_INFLOW_STATUS_MAP.put(MEMBER_INFLOW_STATUS_BACK, "不通过");
		MEMBER_INFLOW_STATUS_MAP.put(MEMBER_INFLOW_STATUS_APPLY, "申请");
		MEMBER_INFLOW_STATUS_MAP.put(MEMBER_INFLOW_STATUS_BRANCH_VERIFY, "党支部审核通过");
		MEMBER_INFLOW_STATUS_MAP.put(MEMBER_INFLOW_STATUS_PARTY_VERIFY, "分党委审核通过");
	}
	// 党员流入转出状态
	public final static byte MEMBER_INFLOW_OUT_STATUS_SELF_BACK = -2; // 本人撤回
	public final static byte MEMBER_INFLOW_OUT_STATUS_BACK = -1; // 审核不通过
	public final static byte MEMBER_INFLOW_OUT_STATUS_APPLY = 0;
	public final static byte MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY= 1;
	public final static byte MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY= 2;
	public final static Map<Byte, String> MEMBER_INFLOW_OUT_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_INFLOW_OUT_STATUS_MAP.put(MEMBER_INFLOW_OUT_STATUS_SELF_BACK, "本人撤回");
		MEMBER_INFLOW_OUT_STATUS_MAP.put(MEMBER_INFLOW_OUT_STATUS_BACK, "不通过");
		MEMBER_INFLOW_OUT_STATUS_MAP.put(MEMBER_INFLOW_OUT_STATUS_APPLY, "申请");
		MEMBER_INFLOW_OUT_STATUS_MAP.put(MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY, "党支部审核通过");
		MEMBER_INFLOW_OUT_STATUS_MAP.put(MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY, "分党委审核通过");
	}


	// 党员流出状态
	public final static byte MEMBER_OUTFLOW_STATUS_SELF_BACK = -2; // 本人撤回
	public final static byte MEMBER_OUTFLOW_STATUS_BACK = -1; // 审核不通过
	public final static byte MEMBER_OUTFLOW_STATUS_APPLY = 0;
	public final static byte MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY= 1;
	public final static byte MEMBER_OUTFLOW_STATUS_PARTY_VERIFY= 2;
	public final static Map<Byte, String> MEMBER_OUTFLOW_STATUS_MAP = new LinkedHashMap<>();
	static {
		MEMBER_OUTFLOW_STATUS_MAP.put(MEMBER_OUTFLOW_STATUS_SELF_BACK, "本人撤回");
		MEMBER_OUTFLOW_STATUS_MAP.put(MEMBER_OUTFLOW_STATUS_BACK, "不通过");
		MEMBER_OUTFLOW_STATUS_MAP.put(MEMBER_OUTFLOW_STATUS_APPLY, "申请");
		MEMBER_OUTFLOW_STATUS_MAP.put(MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY, "党支部审核通过");
		MEMBER_OUTFLOW_STATUS_MAP.put(MEMBER_OUTFLOW_STATUS_PARTY_VERIFY, "分党委审核通过");
	}

	// 党员出国（境）保留组织关系申请    留学方式
	public final static byte GRADUATE_ABROAD_TYPE_PUB = 1; // 公派
	public final static byte GRADUATE_ABROAD_TYPE_SELF = 2; // 自费
	public final static Map<Byte, String> GRADUATE_ABROAD_TYPE_MAP = new LinkedHashMap<>();
	static {
		GRADUATE_ABROAD_TYPE_MAP.put(GRADUATE_ABROAD_TYPE_PUB, "公派");
		GRADUATE_ABROAD_TYPE_MAP.put(GRADUATE_ABROAD_TYPE_SELF, "自费");
	}
	// 党员出国（境）保留组织关系申请状态
	public final static byte GRADUATE_ABROAD_STATUS_SELF_BACK = -2; // 本人撤回
	public final static byte GRADUATE_ABROAD_STATUS_BACK = -1; // 审核不通过
	public final static byte GRADUATE_ABROAD_STATUS_APPLY = 0;
	public final static byte GRADUATE_ABROAD_STATUS_BRANCH_VERIFY= 1;
	public final static byte GRADUATE_ABROAD_STATUS_PARTY_VERIFY= 2;
	public final static byte GRADUATE_ABROAD_STATUS_OW_VERIFY= 3;
	public final static Map<Byte, String> GRADUATE_ABROAD_STATUS_MAP = new LinkedHashMap<>();
	static {
		GRADUATE_ABROAD_STATUS_MAP.put(GRADUATE_ABROAD_STATUS_SELF_BACK, "本人撤回");
		GRADUATE_ABROAD_STATUS_MAP.put(GRADUATE_ABROAD_STATUS_BACK, "不通过");
		GRADUATE_ABROAD_STATUS_MAP.put(GRADUATE_ABROAD_STATUS_APPLY, "申请");
		GRADUATE_ABROAD_STATUS_MAP.put(GRADUATE_ABROAD_STATUS_BRANCH_VERIFY, "党支部审核通过");
		GRADUATE_ABROAD_STATUS_MAP.put(GRADUATE_ABROAD_STATUS_PARTY_VERIFY, "分党委审核通过");
		GRADUATE_ABROAD_STATUS_MAP.put(GRADUATE_ABROAD_STATUS_OW_VERIFY, "组织部审核通过");
	}

	// 党员各类申请的审批记录类型 1入党申请 2 留学归国申请 3 组织关系转入 4 流入党员申请 5 流出党员申请 6 组织关系转出 7 留学归国党员
	public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY = 1;
	public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_ABROAD = 2;
	public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_IN = 3;
	public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW = 4;
	public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW = 5;
	public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT = 6;
	public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN = 7;
	public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER = 8;
	public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY = 9;
	public final static byte APPLY_APPROVAL_LOG_TYPE_USER_REG = 10;
	public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT = 11;
	public final static byte APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT = 12;
	public final static byte APPLY_APPROVAL_LOG_TYPE_GRADUATE_ABROAD = 13;
	public final static Map<Byte, String> APPLY_APPROVAL_LOG_TYPE_MAP = new LinkedHashMap<>();
	static {
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, "申请入党");
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_ABROAD, "留学归国申请");
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_IN, "组织关系转入");
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW, "流入党员申请");
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW, "流出党员申请");
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT, "组织关系转出");
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN, "留学归国党员申请");
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER, "校内组织关系转接");
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY, "公派留学生党员申请组织关系暂留");
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_USER_REG, "用户注册");
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT, "党员出党");
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT, "流入党员转出");
		APPLY_APPROVAL_LOG_TYPE_MAP.put(APPLY_APPROVAL_LOG_TYPE_GRADUATE_ABROAD, "党员出国（境）申请组织关系暂留");
	}

	// 党员各类申请的操作人类别
	public final static byte APPLY_APPROVAL_LOG_USER_TYPE_SELF = 0; // 本人
	public final static byte APPLY_APPROVAL_LOG_USER_TYPE_BRANCH = 1; // 党支部
	public final static byte APPLY_APPROVAL_LOG_USER_TYPE_PARTY = 2; // 分党委
	public final static byte APPLY_APPROVAL_LOG_USER_TYPE_OW = 3; // 组织部
	public final static byte APPLY_APPROVAL_LOG_USER_TYPE_OUT_PARTY = 4; // 转出分党委
	public final static byte APPLY_APPROVAL_LOG_USER_TYPE_IN_PARTY = 5; // 转入分党委
	public final static byte APPLY_APPROVAL_LOG_USER_TYPE_ADMIN = 10;// 后台操作
	public final static Map<Byte, String> APPLY_APPROVAL_LOG_USER_TYPE_MAP = new LinkedHashMap<>();
	static {
		APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_SELF, "本人");
		APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_BRANCH, "党支部");
		APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_PARTY, "分党委");
		APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_OW, "组织部");
		APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_OUT_PARTY, "转出分党委");
		APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_IN_PARTY, "转入分党委");
		APPLY_APPROVAL_LOG_USER_TYPE_MAP.put(APPLY_APPROVAL_LOG_USER_TYPE_ADMIN, "后台操作");
	}

	// 党员各类申请的审批结果
	public final static byte APPLY_APPROVAL_LOG_STATUS_DENY = 0;
	public final static byte APPLY_APPROVAL_LOG_STATUS_PASS = 1;
	public final static byte APPLY_APPROVAL_LOG_STATUS_BACK = 2;
	public final static byte APPLY_APPROVAL_LOG_STATUS_NONEED = 3; // 直接通过，不需要审核
	public final static Map<Byte, String> APPLY_APPROVAL_LOG_STATUS_MAP = new LinkedHashMap<>();
	static {
		APPLY_APPROVAL_LOG_STATUS_MAP.put(APPLY_APPROVAL_LOG_STATUS_DENY, "不通过");
		APPLY_APPROVAL_LOG_STATUS_MAP.put(APPLY_APPROVAL_LOG_STATUS_PASS, "通过");
		APPLY_APPROVAL_LOG_STATUS_MAP.put(APPLY_APPROVAL_LOG_STATUS_BACK, "打回");
		APPLY_APPROVAL_LOG_STATUS_MAP.put(APPLY_APPROVAL_LOG_STATUS_NONEED, "-");
	}


	// 干部库类别 1现任干部库  2 考察对象 3离任干部库
	public final static byte CADRE_STATUS_NOW = 1;
	public final static byte CADRE_STATUS_TEMP = 2;
	public final static byte CADRE_STATUS_LEAVE = 3;
	public final static byte CADRE_STATUS_LEADER_LEAVE = 4;
	public final static Map<Byte, String>CADRE_STATUS_MAP = new LinkedHashMap<>();
	static {
		CADRE_STATUS_MAP.put(CADRE_STATUS_NOW, "现任干部库");
		CADRE_STATUS_MAP.put(CADRE_STATUS_TEMP, "考察对象");
		CADRE_STATUS_MAP.put(CADRE_STATUS_LEAVE, "离任处级干部库");
		CADRE_STATUS_MAP.put(CADRE_STATUS_LEADER_LEAVE, "离任校领导干部库");
	}

	// 干部任免类别
	public static final byte DISPATCH_CADRE_TYPE_APPOINT = 1;
	public static final byte DISPATCH_CADRE_TYPE_DISMISS = 2;
	public final static Map<Byte, String> DISPATCH_CADRE_TYPE_MAP = new HashMap();
	static {
		DISPATCH_CADRE_TYPE_MAP.put(DISPATCH_CADRE_TYPE_APPOINT, "任命");
		DISPATCH_CADRE_TYPE_MAP.put(DISPATCH_CADRE_TYPE_DISMISS, "免职");
	}

	// 干部任免关联模块
	public static final byte DISPATCH_CADRE_RELATE_TYPE_WORK = 1;
	public static final byte DISPATCH_CADRE_RELATE_TYPE_POST = 2;
	public final static Map<Byte, String> DISPATCH_CADRE_RELATE_TYPE_MAP = new HashMap();
	static {
		DISPATCH_CADRE_RELATE_TYPE_MAP.put(DISPATCH_CADRE_RELATE_TYPE_WORK, "工作经历");
		DISPATCH_CADRE_RELATE_TYPE_MAP.put(DISPATCH_CADRE_RELATE_TYPE_POST, "任职情况(主职/兼职)");
	}

	// 干部学习经历 学校类型 1本校 2境内 3境外
	public final static byte CADRE_SCHOOL_TYPE_THIS_SCHOOL = 1;
	public final static byte CADRE_SCHOOL_TYPE_DOMESTIC = 2;
	public final static byte CADRE_SCHOOL_TYPE_ABROAD = 3;
	public final static Map<Byte, String>CADRE_SCHOOL_TYPE_MAP = new LinkedHashMap<>();
	static {
		CADRE_SCHOOL_TYPE_MAP.put(CADRE_SCHOOL_TYPE_THIS_SCHOOL, "本校");
		CADRE_SCHOOL_TYPE_MAP.put(CADRE_SCHOOL_TYPE_DOMESTIC, "境内");
		CADRE_SCHOOL_TYPE_MAP.put(CADRE_SCHOOL_TYPE_ABROAD, "境外");
	}
	// 干部学习经历 导师类型 1硕士研究生导师 2 博士研究生导师
	public final static byte CADRE_TUTOR_TYPE_MASTER = 1;
	public final static byte CADRE_TUTOR_TYPE_DOCTOR = 2;
	public final static Map<Byte, String>CADRE_TUTOR_TYPE_MAP = new LinkedHashMap<>();
	static {
		CADRE_TUTOR_TYPE_MAP.put(CADRE_TUTOR_TYPE_MASTER, "硕士研究生导师");
		CADRE_TUTOR_TYPE_MAP.put(CADRE_TUTOR_TYPE_DOCTOR, "博士研究生导师");
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

	//出行时间范围
	public final static byte APPLY_SELF_DATE_TYPE_HOLIDAY = 1;
	public final static byte APPLY_SELF_DATE_TYPE_SCHOOL = 2;
	public final static byte APPLY_SELF_DATE_TYPE_OTHER = 3;
	public final static Map<Byte, String>APPLY_SELF_DATE_TYPE_MAP = new LinkedHashMap<>();
	static {
		APPLY_SELF_DATE_TYPE_MAP.put(APPLY_SELF_DATE_TYPE_HOLIDAY, "公众假期");
		APPLY_SELF_DATE_TYPE_MAP.put(APPLY_SELF_DATE_TYPE_SCHOOL, "寒/暑假");
		APPLY_SELF_DATE_TYPE_MAP.put(APPLY_SELF_DATE_TYPE_OTHER, "其他");
	}

	//行程修改类别
	public final static byte APPLYSELF_MODIFY_TYPE_ORIGINAL = 0;
	public final static byte APPLYSELF_MODIFY_TYPE_MODIFY = 1;
	public final static Map<Byte, String> APPLYSELF_MODIFY_TYPE_MAP = new LinkedHashMap<>();
	static {
		APPLYSELF_MODIFY_TYPE_MAP.put(APPLYSELF_MODIFY_TYPE_ORIGINAL, "首次提交申请");
		APPLYSELF_MODIFY_TYPE_MAP.put(APPLYSELF_MODIFY_TYPE_MODIFY, "行程修改");
	}

	//证件类别 1:集中管理证件 2:取消集中保管证件 3:丢失证件
	public final static byte PASSPORT_TYPE_KEEP = 1;
	public final static byte PASSPORT_TYPE_CANCEL = 2;
	public final static byte PASSPORT_TYPE_LOST = 3;
	public final static Map<Byte, String>PASSPORT_TYPE_MAP = new LinkedHashMap<>();
	static {
		PASSPORT_TYPE_MAP.put(PASSPORT_TYPE_KEEP, "集中管理证件");
		PASSPORT_TYPE_MAP.put(PASSPORT_TYPE_CANCEL, "取消集中保管证件");
		PASSPORT_TYPE_MAP.put(PASSPORT_TYPE_LOST, "丢失证件");
	}
	// 取消集中保管原因 1 证件过期 2 不再担任行政职务 3证件作废
	public final static byte PASSPORT_CANCEL_TYPE_EXPIRE= 1;
	public final static byte PASSPORT_CANCEL_TYPE_DISMISS = 2;
	public final static byte PASSPORT_CANCEL_TYPE_ABOLISH = 3;
	public final static Map<Byte, String>PASSPORT_CANCEL_TYPE_MAP = new LinkedHashMap<>();
	static {
		PASSPORT_CANCEL_TYPE_MAP.put(PASSPORT_CANCEL_TYPE_EXPIRE, "证件过期");
		PASSPORT_CANCEL_TYPE_MAP.put(PASSPORT_CANCEL_TYPE_DISMISS, "不再担任行政职务");
		PASSPORT_CANCEL_TYPE_MAP.put(PASSPORT_CANCEL_TYPE_ABOLISH, "证件作废");
	}
	// 丢失来源 1 从集中管理库中转移 2 后台添加
	public final static byte PASSPORT_LOST_TYPE_TRANSFER= 1;
	public final static byte PASSPORT_LOST_TYPE_ADD = 2;
	public final static Map<Byte, String> PASSPORT_LOST_TYPE_MAP = new LinkedHashMap<>();
	static {
		PASSPORT_LOST_TYPE_MAP.put(PASSPORT_LOST_TYPE_TRANSFER, "集中管理证件库");
		PASSPORT_LOST_TYPE_MAP.put(PASSPORT_LOST_TYPE_ADD, "后台添加");
	}

	//领取证件类别
	public final static byte PASSPORT_DRAW_TYPE_SELF = 1;
	public final static byte PASSPORT_DRAW_TYPE_TW = 2;
	public final static byte PASSPORT_DRAW_TYPE_OTHER = 3;
	public final static byte PASSPORT_DRAW_TYPE_LONG_SELF = 4;
	public final static Map<Byte, String>PASSPORT_DRAW_TYPE_MAP = new LinkedHashMap<>();
	static {
		PASSPORT_DRAW_TYPE_MAP.put(PASSPORT_DRAW_TYPE_SELF, "因私出国（境）");
		PASSPORT_DRAW_TYPE_MAP.put(PASSPORT_DRAW_TYPE_TW, "因公赴台");
		PASSPORT_DRAW_TYPE_MAP.put(PASSPORT_DRAW_TYPE_OTHER, "其他事务");
		PASSPORT_DRAW_TYPE_MAP.put(PASSPORT_DRAW_TYPE_LONG_SELF, "长期因公出国");
	}

	//领取证件用途 1 仅签证 2 已签证，本次出境 3 同时签证和出境
	public final static byte PASSPORT_DRAW_USE_TYPE_SIGN = 1;
	public final static byte PASSPORT_DRAW_USE_TYPE_ABROAD = 2;
	public final static byte PASSPORT_DRAW_USE_TYPE_BOTH = 3;
	public final static Map<Byte, String>PASSPORT_DRAW_USE_TYPE_MAP = new LinkedHashMap<>();
	static {
		PASSPORT_DRAW_USE_TYPE_MAP.put(PASSPORT_DRAW_USE_TYPE_SIGN, "仅签证");
		PASSPORT_DRAW_USE_TYPE_MAP.put(PASSPORT_DRAW_USE_TYPE_ABROAD, "已签证，本次出境");
		PASSPORT_DRAW_USE_TYPE_MAP.put(PASSPORT_DRAW_USE_TYPE_BOTH, "同时签证和出境");
	}

	//申办证件审批状态
	public final static byte PASSPORT_APPLY_STATUS_INIT = 0;
	public final static byte PASSPORT_APPLY_STATUS_PASS = 1;
	public final static byte PASSPORT_APPLY_STATUS_NOT_PASS= 2;
	public final static Map<Byte, String> PASSPORT_APPLY_STATUS_MAP = new LinkedHashMap<>();
	static {
		PASSPORT_APPLY_STATUS_MAP.put(PASSPORT_APPLY_STATUS_INIT, "待审批");
		PASSPORT_APPLY_STATUS_MAP.put(PASSPORT_APPLY_STATUS_PASS, "批准");
		PASSPORT_APPLY_STATUS_MAP.put(PASSPORT_APPLY_STATUS_NOT_PASS, "未批准");
	}

	//使用证件审批状态
	public final static byte PASSPORT_DRAW_STATUS_INIT = 0;
	public final static byte PASSPORT_DRAW_STATUS_PASS= 1;
	public final static byte PASSPORT_DRAW_STATUS_NOT_PASS= 2;
	public final static Map<Byte, String> PASSPORT_DRAW_STATUS_MAP = new LinkedHashMap<>();
	static {
		PASSPORT_DRAW_STATUS_MAP.put(PASSPORT_DRAW_STATUS_INIT, "待审批");
		PASSPORT_DRAW_STATUS_MAP.put(PASSPORT_DRAW_STATUS_PASS, "审批通过");
		PASSPORT_DRAW_STATUS_MAP.put(PASSPORT_DRAW_STATUS_NOT_PASS, "未通过审批");
	}

	//使用证件领取状态
	public final static byte PASSPORT_DRAW_DRAW_STATUS_UNDRAW = 0;
	public final static byte PASSPORT_DRAW_DRAW_STATUS_DRAW= 1;
	public final static byte PASSPORT_DRAW_DRAW_STATUS_RETURN= 2;
	public final static byte PASSPORT_DRAW_DRAW_STATUS_ABOLISH= 3;
	public final static Map<Byte, String> PASSPORT_DRAW_DRAW_STATUS_MAP = new LinkedHashMap<>();
	static {
		PASSPORT_DRAW_DRAW_STATUS_MAP.put(PASSPORT_DRAW_DRAW_STATUS_UNDRAW, "未领取");
		PASSPORT_DRAW_DRAW_STATUS_MAP.put(PASSPORT_DRAW_DRAW_STATUS_DRAW, "已领取");
		PASSPORT_DRAW_DRAW_STATUS_MAP.put(PASSPORT_DRAW_DRAW_STATUS_RETURN, "已归还");
		PASSPORT_DRAW_DRAW_STATUS_MAP.put(PASSPORT_DRAW_DRAW_STATUS_ABOLISH, "已作废");
	}

	// 因私出国审批人类别  1本单位正职 2分管校领导 3 其他
	public final static byte APPROVER_TYPE_UNIT = 1;
	public final static byte APPROVER_TYPE_LEADER= 2;
	public final static byte APPROVER_TYPE_OTHER= 3;
	public final static Map<Byte, String> APPROVER_TYPE_MAP = new LinkedHashMap<>();
	static {
		APPROVER_TYPE_MAP.put(APPROVER_TYPE_UNIT, "本单位正职");
		APPROVER_TYPE_MAP.put(APPROVER_TYPE_LEADER, "分管校领导");
		APPROVER_TYPE_MAP.put(APPROVER_TYPE_OTHER, "其他");
	}

	public final static int APPROVER_TYPE_ID_OD_FIRST = -1; // 初审管理员，伪ID
	public final static int APPROVER_TYPE_ID_OD_LAST = 0; // 终审管理员，伪ID

	// 管理员审批类型，0初审，1终审（type_id为null时）
	public final static byte APPROVER_LOG_OD_TYPE_FIRST = 0;
	public final static byte APPROVER_LOG_OD_TYPE_LAST = 1;

	// 内容模板类别  1 短信
	public final static byte CONTENT_TPL_TYPE_SHORTMSG = 1;
	public final static Map<Byte, String> CONTENT_TPL_TYPE_MAP = new LinkedHashMap<>();
	static {
		CONTENT_TPL_TYPE_MAP.put(CONTENT_TPL_TYPE_SHORTMSG, "短信");
	}
	// 内容模板内容类型  1 普通文本
	public final static byte CONTENT_TPL_CONTENT_TYPE_STRING = 1;
	public final static Map<Byte, String> CONTENT_TPL_CONTENT_TYPE_MAP = new LinkedHashMap<>();
	static {
		CONTENT_TPL_CONTENT_TYPE_MAP.put(CONTENT_TPL_CONTENT_TYPE_STRING, "普通文本");
	}
	// 内容模板引擎  1 MessageFormat
	public final static byte CONTENT_TPL_ENGINE_MESSAGEFORMAT = 1;
	public final static Map<Byte, String> CONTENT_TPL_ENGINE_MAP = new LinkedHashMap<>();
	static {
		CONTENT_TPL_ENGINE_MAP.put(CONTENT_TPL_ENGINE_MESSAGEFORMAT, "MessageFormat");
	}

	// 内容模板（与数据库中代码对应）
	public final static String CONTENT_TPL_APPLYSELF_SUBMIT_INFO = "ct_applyself_submit_info"; // 干部提交因私申请，通知管理员
	public final static String CONTENT_TPL_APPLYSELF_PASS_INFO = "ct_applyself_pass_info"; // 干部因私申请通过全部领导审批，通知管理员
	public final static String CONTENT_TPL_PASSPORT_EXPIRE = "ct_passport_expire";
	public final static String CONTENT_TPL_PASSPORT_DISMISS = "ct_passport_dismiss";
	public final static String CONTENT_TPL_PASSPORT_ABOLISH = "ct_passport_abolish";
	public final static String CONTENT_TPL_APPLYSELF_PASS = "ct_applySelf_pass";
	public final static String CONTENT_TPL_APPLYSELF_UNPASS = "ct_applySelf_unpass";
	public final static String CONTENT_TPL_PASSPORTAPPLY_PASS = "ct_passportApply_pass";
	public final static String CONTENT_TPL_PASSPORTAPPLY_UNPASS = "ct_passportApply_unpass";
	public final static String CONTENT_TPL_PASSPORTAPPLY_DRAW = "ct_passportApply_draw";
	public final static String CONTENT_TPL_PASSPORTAPPLY_SUBMIT = "ct_passportApply_submit";
	public final static String CONTENT_TPL_PASSPORTDRAW = "ct_passportDraw";
	public final static String CONTENT_TPL_PASSPORTDRAW_RETURN = "ct_passportDraw_return";
	public final static String CONTENT_TPL_PASSPORTDRAW_PASS = "ct_passportDraw_pass";
	public final static String CONTENT_TPL_PASSPORTDRAW_PASS_NEEDSIGN = "ct_passportDraw_pass_needsign";
	public final static String CONTENT_TPL_PASSPORTDRAW_UNPASS = "ct_passportDraw_unpass";
	public final static String CONTENT_TPL_PASSPORTDRAW_UNPASS_NEEDSIGN = "ct_passportDraw_unpass_needsign";


	/*public final static Map<String, String> SHORT_MSG_KEY_MAP = new LinkedHashMap<>();
	static {
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORT_EXPIRE, "取消集中管理-证件到期");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORT_DISMISS, "取消集中管理-不再担任职务");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_APPLYSELF_PASS, "因私出国申请-通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_APPLYSELF_UNPASS, "因私出国申请-不通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTAPPLY_PASS, "申办证件-通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTAPPLY_UNPASS, "申办证件-不通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTAPPLY_DRAW, "申办证件-催交证件");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTAPPLY_SUBMIT, "申办证件-提交申请");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTDRAW, "因私出国申请-领取证件");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTDRAW_RETURN, "申请使用证件-催交证件");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTDRAW_PASS, "申请使用证件-通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTDRAW_PASS_NEEDSIGN, "申请使用证件（需要签注）-通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTDRAW_UNPASS, "申请使用证件-不通过");
		SHORT_MSG_KEY_MAP.put(SHORT_MSG_KEY_PASSPORTDRAW_UNPASS_NEEDSIGN, "申请使用证件（需要签注）-不通过");
	}*/
}
