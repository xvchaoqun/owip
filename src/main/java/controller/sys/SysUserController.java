package controller.sys;

import bean.ColumnBean;
import controller.BaseController;
import domain.member.MemberView;
import domain.sys.*;
import domain.sys.SysUserExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.SysUserListMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.DBServcie;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.freemarker.TableNameMethod;
import sys.shiro.SaltPassword;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class SysUserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected DBServcie dbServcie;

    // 门户账号信息
    @RequiresPermissions("sysUser:view")
    @RequestMapping("/sysUser_ext")
    public String sysUser_ext(int userId, ModelMap modelMap) {

        SysUserView sysUser = sysUserService.findById(userId);
        modelMap.put("sysUser", sysUser);
        String code = sysUser.getCode();

        Map<String, ColumnBean> columnBeanMap = null;
        Object bean = null;
        byte type = sysUser.getType();
        if (type == SystemConstants.USER_TYPE_JZG) {

            bean = extJzgMapper.selectByPrimaryKey(code);
            columnBeanMap = dbServcie.getColumnBeanMap("ext_jzg");
        } else if (type == SystemConstants.USER_TYPE_YJS) {

            bean = extYjsMapper.selectByPrimaryKey(code);
            columnBeanMap = dbServcie.getColumnBeanMap("ext_yjs");
        } else if (type == SystemConstants.USER_TYPE_BKS) {

            bean = extBksMapper.selectByPrimaryKey(code);
            columnBeanMap = dbServcie.getColumnBeanMap("ext_bks");
        }

        if (columnBeanMap != null && columnBeanMap.size() > 0) {
            Map<String, Object> valuesMap = new LinkedHashMap<>();
            for (ColumnBean columnBean : columnBeanMap.values()) {
                // POJO 字段名
                String name = TableNameMethod.formatStr(columnBean.getName(), "tableName");
                Object value = JavaBeanUtils.getFieldValueByFieldName(name, bean);
                if (value instanceof Date) {
                    value = DateUtils.formatDate((Date) value, DateUtils.YYYYMMDD);
                }
                valuesMap.put(name, value);
            }
            modelMap.put("valuesMap", valuesMap);
        }

        modelMap.put("columnBeanMap", columnBeanMap);

        return "sys/sysUser/sysUser_ext";
    }

    @RequiresPermissions("sysUser:view")
    @RequestMapping("/sysUser_view")
    public String sysUser_view(int userId, String to, ModelMap modelMap) {

        SysUserView sysUser = sysUserService.findById(userId);
        modelMap.put("uv", sysUser);

        modelMap.put("to", StringUtils.defaultString(to, "sysUser_base"));

        return "sys/sysUser/sysUser_view";
    }

    // 账号信息
    //@RequiresPermissions("sysUser:view")
    @RequestMapping("/sysUser_base")
    public String sysUser_base(int userId, ModelMap modelMap) {

        if(userId!=ShiroHelper.getCurrentUserId()){
            SecurityUtils.getSubject().checkPermission("sysUser:view");
        }

        SysUserView sysUser = sysUserService.findById(userId);
        modelMap.put("uv", sysUser);

        MemberView member = iMemberMapper.getMemberView(userId);
        modelMap.put("member", member);

        if (sysUser.getType() == SystemConstants.USER_TYPE_JZG) {

            // 系统教职工账号（注册或后台添加）基础信息维护
            TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(userId);
            modelMap.put("teacherInfo", teacherInfo);

            return "sys/sysUser/teacher_base";
        }

        // 系统学生账号（注册或后台添加）基础信息维护
        StudentInfo studentInfo = studentInfoMapper.selectByPrimaryKey(userId);
        modelMap.put("studentInfo", studentInfo);

        return "sys/sysUser/student_base";
    }

    // 账号信息
    @RequiresPermissions("sysUser:view")
    @RequestMapping("/sysUser_info")
    public String sysUser_info(int userId, ModelMap modelMap) {

        SysUserView sysUser = sysUserService.findById(userId);
        modelMap.put("sysUser", sysUser);

        String unit = extService.getUnit(userId);
        modelMap.put("unit", unit); // 学校人事库或学生库中的单位名称


        modelMap.put("adminPartyIdList", partyMemberAdminService.adminPartyIdList(userId));
        modelMap.put("adminBranchIdList", branchMemberAdminService.adminBranchIdList(userId));

        return "sys/sysUser/sysUser_info";
    }

    @RequiresPermissions("sysUser:list")
    @RequestMapping("/sysUser")
    public String sysUser(ModelMap modelMap) {

        return "sys/sysUser/sysUser_page";
    }

    @RequiresPermissions("sysUser:list")
    @RequestMapping("/sysUser_data")
    @ResponseBody
    public void sysUser_data(HttpServletRequest request,
                             @SortParam(required = false, defaultValue = "id", tableName = "sys_user") String sort,
                             @OrderParam(required = false, defaultValue = "desc") String order,
                             Integer pageSize, Integer pageNo,
                             Integer userId,
                             String username, String realname, String code, String idcard,
                             Byte type, Byte source, Integer roleId, Boolean locked) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SysUserViewExample example = new SysUserViewExample();
        SysUserViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }

        if (StringUtils.isNotBlank(username)) {
            criteria.andUsernameEqualTo(username.trim());
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeEqualTo(code.trim());
        }

        if (StringUtils.isNotBlank(realname)) {
            criteria.andRealnameLike(SqlUtils.like(realname));
        }

        if (StringUtils.isNotBlank(idcard)) {
            criteria.andIdcardLike(SqlUtils.like(idcard));
        }

        if (roleId != null) {
            criteria.andRoleIdsLike("%," + roleId + ",%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (source != null) {
            criteria.andSourceEqualTo(source);
        }
        if (locked != null) {
            criteria.andLockedEqualTo(locked);
        }
        long count = sysUserViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserView> uvs = sysUserViewMapper.selectByExampleWithRowbounds
                (example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", uvs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = new HashMap<>();
        baseMixins.put(SysUserView.class, SysUserListMixin.class);
        JSONUtils.jsonp(resultMap);
        return;
    }

    @RequiresPermissions("sysUser:list")
    @RequestMapping("/sysUser_roles")
    public String sysUser_types(String _sysUsername, ModelMap modelMap) {

        Set<String> roles = sysUserService.findRoles(_sysUsername);
        modelMap.put("roles", roles);

        return "sys/sysUser/sysUser_roles";
    }

    @RequiresPermissions("sysUser:edit")
    @RequestMapping(value = "/sysUser_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUser_au(@Validated SysUser record, BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            return formValidError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        record.setUsername(StringUtils.lowerCase(StringUtils.trimToNull(record.getUsername())));
        record.setCode(StringUtils.lowerCase(StringUtils.trimToNull(record.getCode())));
        Integer id = record.getId();

        if (record.getUsername() != null) {
            if (!CmTag.validUsername(record.getUsername())) {
                return formValidError("username", CmTag.getStringProperty("usernameMsg"));
            }
            if (sysUserService.idDuplicate(id, record.getUsername(), record.getCode())) {

                return formValidError("code", "用户名或学工号重复");
            }
        }

        if (id == null) {
            if (StringUtils.isBlank(record.getUsername())) {
                return formValidError("username", "用户名不能为空");
            }
            if (StringUtils.isBlank(record.getPasswd())) {
                return formValidError("passwd", "密码不能为空");
            }
            record.setLocked(false);
            SaltPassword encrypt = passwordHelper.encryptByRandomSalt(record.getPasswd());
            record.setSalt(encrypt.getSalt());
            record.setPasswd(encrypt.getPassword());
            record.setCreateTime(new Date());
            record.setSource(SystemConstants.USER_SOURCE_ADMIN);
            sysUserService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加用户：%s", record.getId()));
        } else {
            if (StringUtils.isBlank(record.getPasswd())) {
                // 密码不变
                record.setPasswd(null);
            } else {
                SaltPassword encrypt = passwordHelper.encryptByRandomSalt(record.getPasswd());
                record.setSalt(encrypt.getSalt());
                record.setPasswd(encrypt.getPassword());
            }

            SysUserView uv = sysUserService.findById(id);
            if (ShiroHelper.lackRole(RoleConstants.ROLE_SUPER)
                    && CmTag.hasRole(uv.getUsername(), RoleConstants.ROLE_SUPER)) {
                return failed("该账号不允许更新。");
            }

            if (record.getTimeout() == null) {
                commonMapper.excuteSql("update sys_user set timeout=null where id=" + id);
            }
            sysUserService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新用户：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysUser:edit")
    @RequestMapping("/sysUser_au")
    public String sysUser_au(Integer id, ModelMap modelMap) {

        if (id != null) {

            SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
            modelMap.put("sysUser", sysUser);
        }

        return "sys/sysUser/sysUser_au";
    }

    @RequiresPermissions("sysUser:edit")
    @RequestMapping(value = "/sysUserInfo_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUserInfo_au(int userId, SysUserInfo record, MultipartFile _avatar) throws IOException {

        record.setUserId(userId);
        //SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        String avatar = avatarService.uploadAvatar(_avatar);
        record.setAvatar(avatar);

        sysUserService.insertOrUpdateUserInfoSelective(record);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysUser:edit")
    @RequestMapping("/sysUserInfo_au")
    public String sysUserInfo_au(Integer userId, ModelMap modelMap) {

        if (userId != null) {
            SysUserInfo ui = sysUserInfoMapper.selectByPrimaryKey(userId);
            modelMap.put("ui", ui);

            SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
            modelMap.put("sysUser", sysUser);
        }

        return "sys/sysUser/sysUserInfo_au";
    }

    // 查看中组部干部任免审批表简历
    @RequiresPermissions("sysUser:resume")
    @RequestMapping("/sysUserInfo_resume")
    public String sysUserInfo_resume(Integer userId, ModelMap modelMap) {

        if (userId != null) {
            SysUserInfo ui = sysUserInfoMapper.selectByPrimaryKey(userId);
            modelMap.put("ui", ui);

            SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
            modelMap.put("sysUser", sysUser);
        }

        return "sys/sysUser/sysUserInfo_resume";
    }

    // 预览用户的菜单
    @RequiresPermissions("sysUser:list")
    @RequestMapping("/sysUser_menu")
    public String sysUser_menu(int userId, ModelMap modelMap) {

        SysUserView uv = sysUserService.findById(userId);
        modelMap.put("uv", uv);
        return "sys/sysUser/sysUser_menu";
    }

    @RequiresPermissions("sysUser:del")
    @RequestMapping(value = "/sysUser_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUser_del(@RequestParam(value = "ids[]") Integer[] ids, boolean locked, HttpServletRequest request) {

        for (Integer id : ids) {
            SysUserView sysUser = sysUserService.findById(id);
            String username = sysUser.getUsername();
            sysUserService.lockUser(sysUser.getId(), locked);
            logger.info(addLog(LogConstants.LOG_ADMIN, (locked ? "禁用" : "解禁") + "用户：%s", username));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysUser:edit")
    @RequestMapping(value = "/sysUserRole", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUserRole(SysUser record,
                              @RequestParam(value = "rIds[]", required = false) Integer[] rIds,
                              HttpServletRequest request) {

        if (rIds == null || rIds.length == 0) {
            rIds = new Integer[1];
            rIds[0] = -1;
        }
        boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());
        int userId = record.getId();
        SysUserView uv = sysUserService.findById(userId);
        if (!superAccount && ShiroHelper.lackRole(RoleConstants.ROLE_SUPER)
                && CmTag.hasRole(uv.getUsername(), RoleConstants.ROLE_SUPER)) {
            return failed("该账号不允许更新。");
        }

        sysUserService.updateUserRoles(userId, "," + StringUtils.join(rIds, ",") + ",");

        logger.info(addLog(LogConstants.LOG_ADMIN, "更新用户%s 角色：%s", record.getUsername(), StringUtils.join(rIds, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysUser:list")
    @RequestMapping("/sysUserRole")
    public String sysUserRole(Integer id, ModelMap modelMap) throws IOException {

        Set<Integer> selectIdSet = new HashSet<Integer>();
        if (id != null) {

            SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
            selectIdSet = sysUserService.getUserRoleIdSet(sysUser.getRoleIds());

            modelMap.put("sysUser", sysUser);
        }

        TreeNode tree = sysRoleService.getTree(selectIdSet, true);
        modelMap.put("tree", JSONUtils.toString(tree));

        return "sys/sysUser/sysUserRole";
    }

    @RequiresPermissions("sysUser:list")
    @RequestMapping("/sysUser_export")
    public String sysUser_export(Integer roleId, boolean locked, HttpServletResponse response,
                                 HttpServletRequest request, ModelMap modelMap) throws IOException {

        SysUserExample example = new SysUserExample();
        Criteria criteria = example.createCriteria().andLockedEqualTo(locked);
        /*if(StringUtils.isNotBlank(role)){

			RoleExample _example = new SysUserTypeExample();
			_example.createCriteria().andTypeEqualTo(role);
			List<SysUserType> sysUserTypes = sysUserTypeMapper.selectByExample(_example );
			List<Integer> sysUserIds = new ArrayList<Integer>();
			for(SysUserType ut:sysUserTypes){
				sysUserIds.add(ut.getSysUserId());
			}
			if(sysUserIds.size()>0)
				criteria.andIdIn(sysUserIds);
		}*/

        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        long rownum = sysUserMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);
        String[] titles = {"账号", "工作证号", "所属单位", "姓名", "工作电话", "手机号码", "单位及职务", "干部身份"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

		/*Map<Integer, Post> postMap = postService.getPostsMap(getYear());
        Map<Integer, Unit> unitMap = unitService.findAll(getYear());
		for(int i=0; i<rownum; i++){

			SysUser sysUser = sysUsers.get(i);
			String[] values ={
					sysUser.getSysUsername(),
					sysUser.getCode(),
					sysUser.getUnitId()==null?"":
					unitMap.get(sysUser.getUnitId()).getUnitName(),
					sysUser.getRealname(),
					sysUser.getPhone(),
					sysUser.getMobile(),
					sysUser.getTitle(),
					sysUser.getPostId()==null?"":
					postMap.get(sysUser.getPostId()).getPostName()};

			Row row = sheet.createRow(i+1);
			for(int j=0; j<titles.length; j++){

				XSSFCell cell = (XSSFCell) row.createCell(j);
				cell.setCellValue(values[j]);
				cell.setCellStyle(getBodyStyle(wb));
			}
		}*/
        String fileName = "账号";
        ExportHelper.output(wb, fileName + ".xlsx", response);

        return null;
    }
}
