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

	/*public static String ADMIN_LOGIN_SESSION_NAME="SESSION:ACCOUNT";
	
	public static String INSPECTOR_LOGIN_SESSION_NAME="SESSION:INSPECTOR";
	public static String INSPECTOR_AGREE_SESSION_NAME="SESSION:INSPECTOR:AGREE";*/
	
	// 用户类型(ces_user_type表)
	public final static String ROLE_ADMIN = "admin";
	public final static String ROLE_UNIT_ADMIN = "unitAdmin";
	public final static String ROLE_CADRE = "cadre";

	public final static int ROLE_ID_ADMIN = 1;
	public final static int ROLE_ID_UNIT_ADMIN = 2;
	public final static int ROLE_ID_CADRE = 3;

	public final static String ROLE_INSPECTOR = "inspector"; // 参评人
	public final static String ROLE_SYS = "sys"; // 系统用户，包括管理员、单位管理员、干部等
	/*public static Map<String, String> USER_TYPE_MAP = new LinkedHashMap<String, String>();
	static{
		USER_TYPE_MAP.put(ROLE_ADMIN, "系统管理员");
		USER_TYPE_MAP.put(ROLE_UNIT_ADMIN, "单位管理员");
		USER_TYPE_MAP.put(ROLE_CADRE, "干部");
	}*/
	
	
}
