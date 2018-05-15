package controller.sys;

import controller.BaseController;
import domain.sys.SysUser;
import domain.sys.SysUserExample;
import domain.sys.SysUserExample.Criteria;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.SysUserListMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.shiro.SaltPassword;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class SysUserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sysUser:list")
    @RequestMapping("/sysUser_view")
    public String sysUser_view(int userId, ModelMap modelMap) {

        SysUserView sysUser = sysUserService.findById(userId);
        modelMap.put("sysUser", sysUser);

        String unit = sysUserService.getUnit(userId);
        modelMap.put("unit", unit); // 学校人事库或学生库中的单位名称


        modelMap.put("adminPartyIdList", partyMemberAdminService.adminPartyIdList(userId));
        modelMap.put("adminBranchIdList", branchMemberAdminService.adminBranchIdList(userId));

        return "sys/sysUser/sysUser_view";
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
        if (StringUtils.isNotBlank(username)) {
            criteria.andUsernameEqualTo(username.trim());
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeEqualTo(code.trim());
        }

        if (StringUtils.isNotBlank(idcard)) {
            criteria.andIdcardLike("%" + idcard.trim() + "%");
        }

        if (StringUtils.isNotBlank(realname)) {
            criteria.andRealnameLike("%" + realname.trim() + "%");
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

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping("/sysUser_roles")
    public String sysUser_types(String _sysUsername, ModelMap modelMap) {

        Set<String> roles = sysUserService.findRoles(_sysUsername);
        modelMap.put("roles", roles);

        return "sys/sysUser/sysUser_roles";
    }

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping(value = "/sysUser_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUser_au(@Validated SysUser sysUser, BindingResult result, HttpServletRequest request) {

        if(result.hasErrors()){
            FieldError fieldError = result.getFieldError();
            return formValidError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        sysUser.setUsername(StringUtils.lowerCase(StringUtils.trimToNull(sysUser.getUsername())));
        sysUser.setCode(StringUtils.lowerCase(StringUtils.trimToNull(sysUser.getCode())));
        Integer id = sysUser.getId();

        if (sysUser.getUsername() != null) {
            if (!FormUtils.usernameFormatRight(sysUser.getUsername())) {
                return formValidError("username", "用户名由5-20位的字母、下划线和数字组成，且不能以数字或下划线开头。");
            }
            if (sysUserService.idDuplicate(id, sysUser.getUsername(), sysUser.getCode())) {

                return formValidError("code", "用户名或学工号重复");
            }
        }

        if (id == null) {
            if (StringUtils.isBlank(sysUser.getUsername())) {
                return formValidError("username", "用户名不能为空");
            }
            if (StringUtils.isBlank(sysUser.getPasswd())) {
                return formValidError("passwd", "密码不能为空");
            }
            sysUser.setLocked(false);
            SaltPassword encrypt = passwordHelper.encryptByRandomSalt(sysUser.getPasswd());
            sysUser.setSalt(encrypt.getSalt());
            sysUser.setPasswd(encrypt.getPassword());
            sysUser.setCreateTime(new Date());
            sysUser.setSource(SystemConstants.USER_SOURCE_ADMIN);
            sysUserService.insertSelective(sysUser);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加用户：%s", sysUser.getId()));
        } else {
            if (StringUtils.isBlank(sysUser.getPasswd())) {
                // 密码不变
                sysUser.setPasswd(null);
            } else {
                SaltPassword encrypt = passwordHelper.encryptByRandomSalt(sysUser.getPasswd());
                sysUser.setSalt(encrypt.getSalt());
                sysUser.setPasswd(encrypt.getPassword());
            }
            //SysUser oldSysUser = sysUserMapper.selectByPrimaryKey(id);
            sysUserService.updateByPrimaryKeySelective(sysUser);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新用户：%s", sysUser.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping("/sysUser_au")
    public String sysUser_au(Integer id, ModelMap modelMap) {

        if (id != null) {

            SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
            modelMap.put("sysUser", sysUser);
        }

        return "sys/sysUser/sysUser_au";
    }

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping(value = "/sysUserInfo_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUserInfo_au(int userId, SysUserInfo record, MultipartFile _avatar) throws IOException {

        record.setUserId(userId);
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        String avatar = avatarService.uploadAvatar(_avatar);
        record.setAvatar(avatar);

        sysUserService.insertOrUpdateUserInfoSelective(record);
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
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

    // 预览用户的菜单
    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping("/sysUser_menu")
    public String sysUser_menu(int userId, ModelMap modelMap) {

        SysUserView uv = sysUserService.findById(userId);
        modelMap.put("uv", uv);
        return "sys/sysUser/sysUser_menu";
    }

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
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

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping(value = "/sysUserRole", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUserRole(@CurrentUser SysUserView loginSysUser,
                              SysUser sysUser,
                              @RequestParam(value = "rIds[]", required = false) Integer[] rIds,
                              HttpServletRequest request) {

        if (rIds == null || rIds.length == 0) {
            rIds = new Integer[1];
            rIds[0] = -1;
        }

        sysUserService.updateUserRoles(sysUser.getId(),  "," + StringUtils.join(rIds, ",") + ",");

        logger.info(addLog(LogConstants.LOG_ADMIN, "更新用户%s 角色：%s", sysUser.getUsername(), StringUtils.join(rIds, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
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
        int rownum = sysUserMapper.countByExample(example);

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
