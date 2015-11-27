package controller;

import domain.SysUser;
import domain.SysUserExample;
import domain.SysUserExample.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import shiro.SaltPassword;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class SysUserController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/sysUser")
	public String sysUser() {

		return "index";
	}

	@RequestMapping("/sysUser_page")
	public String sysUser_page(HttpServletRequest request,
							   @RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "asc") String order,
			Integer pageSize, Integer pageNo, String username,
			Byte type, Integer roleId, Boolean locked, ModelMap modelMap) {
		
		if (null == pageSize) {
			pageSize = springProps.pageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);
		
		SysUserExample example = new SysUserExample();
		Criteria criteria = example.createCriteria();
		example.setOrderByClause(String.format("%s %s", sort, order));

		if(StringUtils.isNotBlank(username)){
			criteria.andUsernameLike("%" + username + "%");
		}
		if(roleId!=null){
			criteria.andRoleIdsLike("%," + roleId + "%,");
		}
		if(type!=null){
			criteria.andTypeEqualTo(type);
		}
		if(locked!=null){
			criteria.andLockedEqualTo(locked);
		}
		int count = sysUserMapper.countByExample(example);
		if((pageNo-1)*pageSize >= count){
			pageNo = Math.max(1, pageNo-1);
		}
		List<SysUser> SysUsers = sysUserMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));
		modelMap.put("sysUsers", SysUsers);
		
		CommonList commonList = new CommonList(count, pageNo, pageSize);
		
		String searchStr = "&pageSize="+pageSize;
		if(StringUtils.isNotBlank(username)){
			searchStr += "&username="+username;
		}
		if(StringUtils.isNotBlank(sort)){
			searchStr += "&sort="+sort;
		}
		if(StringUtils.isNotBlank(order)){
			searchStr += "&order="+order;
		}
		if(roleId!=null){
			searchStr += "&roleId="+roleId;
		}
		if(type!=null){
			searchStr += "&type="+type;
		}
		if(locked!=null){
			searchStr += "&locked="+locked;
		}
		
		commonList.setSearchStr(searchStr);
		modelMap.put("commonList", commonList);

		modelMap.put("userTypeMap", SystemConstants.USER_TYPE_MAP);
		modelMap.put("userSourceMap", SystemConstants.USER_SOURCE_MAP);
		modelMap.put("roleMap", sysRoleService.findAll());

		return "sysUser/sysUser_page";
	}
	@RequiresRoles("admin")
	@RequestMapping("/sysUser_roles")
	public String sysUser_types(String _sysUsername, ModelMap modelMap){

		Set<String> roles = sysUserService.findRoles(_sysUsername);
		modelMap.put("roles", roles);
		
		return "sysUser/sysUser_roles";
	}
	@RequiresRoles("admin")
	@RequestMapping(value="/sysUser_au", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysUser_au(SysUser sysUser, HttpServletRequest request) {

		sysUser.setUsername(StringUtils.lowerCase(StringUtils.trimToNull(sysUser.getUsername())));
		sysUser.setCode(StringUtils.lowerCase(StringUtils.trimToNull(sysUser.getCode())));

		if(!FormUtils.usernameFormatRight(sysUser.getUsername())) {
			return failed(FormUtils.ILLEGAL);
		}
		Integer id = sysUser.getId();
		if(sysUserService.idDuplicate(id, sysUser.getUsername(), sysUser.getCode())){

			return failed("用户名或学工号重复");
		}


		if(id == null){
			sysUser.setLocked(false);
			SaltPassword encrypt = passwordHelper.encryptByRandomSalt(sysUser.getUsername()); // 初始化密码与账号相同
			sysUser.setSalt(encrypt.getSalt());
			sysUser.setPasswd(encrypt.getPassword());
			sysUser.setCreateTime(new Date());
			sysUser.setSource(SystemConstants.USER_SOURCE_ADMIN);
			sysUserService.insertSelective(sysUser);
			logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加用户：%s", sysUser.getId()));
		}else{
			
			if(StringUtils.isBlank(sysUser.getPasswd())){
				// 密码不变
				sysUser.setPasswd(null);
			} else {
				SaltPassword encrypt = passwordHelper.encryptByRandomSalt(sysUser.getPasswd());
				sysUser.setSalt(encrypt.getSalt());
				sysUser.setPasswd(encrypt.getPassword());
			}
			SysUser oldSysUser = sysUserMapper.selectByPrimaryKey(id);
			sysUserService.updateByPrimaryKeySelective(sysUser, oldSysUser.getUsername());
			logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新用户：%s", sysUser.getId()));
		}

		/*sysUserTypeService.delAllSysUserTypes(sysUser.getId(), year);
		if(StringUtils.isNoneBlank(sysUserTypes)){
			for(String type:sysUserTypes.split(",")){

				sysUserTypeService.addSysUserType(sysUser.getId(), type, year);
			}
		}*/
		
		return success(FormUtils.SUCCESS);
	}
	@RequiresRoles("admin")
	@RequestMapping("/sysUser_au")
	public String sysUser_au(Integer id, ModelMap modelMap) {

		if(id != null){
			
			SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
			modelMap.put("sysUser", sysUser);
		}

		modelMap.put("userTypeMap", SystemConstants.USER_TYPE_MAP);

		return "sysUser/sysUser_au";
	}

	@RequiresRoles("admin")
	@RequestMapping(value="/sysUser_del", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysUser_del(String username, boolean locked, HttpServletRequest request) {
		
		if(StringUtils.isNotBlank(username)){
			SysUser sysUser = sysUserService.findByUsername(username);
			sysUserService.lockUser(sysUser.getId(), username, locked);
			logger.info(addLog(request, SystemConstants.LOG_ADMIN, (locked?"禁用":"解禁")+"用户：%s", username));
		}
		
		return success(FormUtils.SUCCESS);
	}

	@RequiresRoles("admin")
	@RequestMapping(value="/sysUserRole", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysUserRole(@CurrentUser SysUser loginSysUser,
								 SysUser sysUser,
								 @RequestParam(value="rIds[]",required=false) Integer[] rIds,
								 HttpServletRequest request) {

		if(rIds==null || rIds.length ==0){
			rIds = new Integer[1];
			rIds[0] = -1;
		}

		SysUser sysSysUser2 = sysUserMapper.selectByPrimaryKey(sysUser.getId());
		sysUserService.updateUserRoles(sysUser.getId(), sysSysUser2.getUsername(), "," + StringUtils.join(rIds, ",") + ",");

		logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新用户%s 角色：%s", sysSysUser2.getUsername(), StringUtils.join(rIds, ",")));
		return success(FormUtils.SUCCESS);
	}
	@RequiresRoles("admin")
	@RequestMapping("/sysUserRole")
	public String sysUserRole(Integer id, ModelMap modelMap) throws IOException {

		Set<Integer> selectIdSet = new HashSet<Integer>();
		if(id != null){

			SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
			selectIdSet = sysUserService.getUserRoleIdSet(sysUser.getRoleIds());

			modelMap.put("sysUser", sysUser);
		}

		TreeNode tree = sysRoleService.getTree(selectIdSet);
		modelMap.put("tree", JSONUtils.toString(tree));

		return "sysUser/sysUserRole";
	}

	@RequestMapping("/sysUser_export")
	public String sysUser_export(Integer roleId, boolean locked, HttpServletResponse response,
								 HttpServletRequest request, ModelMap modelMap) throws IOException {

		SysUserExample example = new SysUserExample();
		Criteria criteria = example .createCriteria().andLockedEqualTo(locked);
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
		String[] titles ={"账号","工作证号","所属单位","姓名","工作电话", "手机号码", "单位及职务", "干部身份"};
		for(int i=0; i<titles.length; i++){
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
		ServletOutputStream outputStream = response.getOutputStream();
		fileName = new String(fileName.getBytes(), "ISO8859_1");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
		wb.write(outputStream);
		outputStream.flush();

		return null;
	}
}
