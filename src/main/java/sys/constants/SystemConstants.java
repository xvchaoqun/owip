package sys.constants;

import java.util.HashMap;
import java.util.Map;

public class SystemConstants {


	public static final String DATERANGE_SEPARTOR = " 至 ";

	public static final String LOG_LOGIN = "mt_log_login";
	public static final String LOG_ADMIN = "mt_log_admin";
	public static final String LOG_OW = "mt_log_ow";
	public static final String LOG_MEMBER_APPLY = "mt_log_member_apply";

	public static final String RESOURCE_TYPE_FUNCTION = "function";
	public static final String RESOURCE_TYPE_URL = "url";
	public static final String RESOURCE_TYPE_MENU = "menu";

	public static final byte AVAILABLE = 1;
	public static final byte UNAVAILABLE = 0;

	public static final byte UNIT_STATUS_RUN = 1; // 正在运转单位
	public static final byte UNIT_STATUS_HISTORY = 2; // 历史单位

	public final static byte USER_UNLOCKED = 0; // 用户状态：未锁定
	public final static byte USER_LOCKED = 1; // 锁定

	public final static byte USER_SOURCE_SYS = 0; // 用户来源：后台创建
	public final static byte USER_SOURCE_EVAOBJ_CADRE = 1; // 后台导入或添加被测评的干部时创建
	public final static byte USER_SOURCE_SYSCONFIG = 2; // 后台创建新的年份时创建

	// 申请入党类型
	public final static byte APPLY_TYPE_STU= 1; // 学生
	public final static byte APPLY_TYPE_TECHER = 2; // 教职工
	public static Map<Byte, String> applyTypeMap = new HashMap<>();
	static {
		applyTypeMap.put(APPLY_TYPE_STU, "学生");
		applyTypeMap.put(APPLY_TYPE_TECHER, "教职工");
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

	public static Map<Byte, String> applyStageTypeMap = new HashMap<>();
	static {
		applyStageTypeMap.put(APPLY_STAGE_INIT, "申请");
		applyStageTypeMap.put(APPLY_STAGE_ACTIVE, "积极分子");
		applyStageTypeMap.put(APPLY_STAGE_CANDIDATE, "发展对象");
		applyStageTypeMap.put(APPLY_STAGE_PLAN, "列入发展计划");
		applyStageTypeMap.put(APPLY_STAGE_DRAW, "领取志愿书");
		applyStageTypeMap.put(APPLY_STAGE_GROW, "预备党员");
		applyStageTypeMap.put(APPLY_STAGE_POSITIVE, "正式党员");
	}

	// 申请入党审核状态
	public final static byte APPLY_STATUS_UNCHECKED = 0; // 未审核
	public final static byte APPLY_STATUS_CHECKED = 1; // 已审核
	public final static byte APPLY_STATUS_OD_CHECKED = 2; // 组织部已审核，成为预备党员和正式党员时
}
