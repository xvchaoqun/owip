package sys.constants;

public class SystemConstants {


	public static final String DATERANGE_SEPARTOR = " 至 ";

	public static final String LOG_LOGIN = "mt_log_login";
	public static final String LOG_ADMIN = "mt_log_admin";
	public static final String LOG_OW = "mt_log_ow";
	public static final String LOG_UNIT = "mt_log_unit";
	public static final String LOG_CADRE = "mt_log_cadre";

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
	// 申请入党当前状态
	public final static byte APPLY_STATUS_DENY = 0; // 未通过
	public final static byte APPLY_STATUS_INIT = 1; // 申请
	public final static byte APPLY_STATUS_ACTIVE = 2; // 积极分子
}
