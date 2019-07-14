package controller.sys;

import bean.ColumnBean;
import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.member.MemberView;
import domain.sys.*;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.SysUserListMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
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

        if (userId != ShiroHelper.getCurrentUserId()) {
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
    public void sysUser_data(HttpServletResponse response,
                             @SortParam(required = false, defaultValue = "id", tableName = "sys_user") String sort,
                             @OrderParam(required = false, defaultValue = "desc") String order,
                             Integer pageSize, Integer pageNo,
                             Integer userId,
                             String username, String realname, String code, String idcard,
                             @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
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

        if (userId != null) {
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

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            sysUser_export(example, response);
            return;
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

    // 抽取工号，根据姓名或身份证号导出带工号的列表
    @RequiresPermissions("sysUser:filterExport")
    @RequestMapping("/sysUser_filterExport")
    public String sysUser_filterExport(ModelMap modelMap) {

        return "sys/sysUser/sysUser_filterExport";
    }

    @RequiresPermissions("sysUser:filterExport")
    @RequestMapping(value = "/sysUser_filterExport", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUser_filterExport(
            byte colType, // 0：身份证 1：姓名
            int col,
            byte roleType, // 1: 干部  0: 混合
            @RequestParam(required = false, defaultValue = "0") byte type, // 类别 教职工、本科生、研究生  0： 混合
            Integer addCol,
            @RequestParam(required = false, defaultValue = "1") int sheetNo,
            HttpServletRequest request, HttpServletResponse response)
            throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(sheetNo-1);

        int firstNotEmptyRowNum = 0;
        XSSFRow firstRow = sheet.getRow(firstNotEmptyRowNum++);
        while (firstRow==null){
            if(firstNotEmptyRowNum>=100) break;
            firstRow = sheet.getRow(firstNotEmptyRowNum++);
        }
        if(firstRow==null){
            return failed("该文件前100行数据为空，无法导出");
        }
        int cellNum = firstRow.getLastCellNum() - firstRow.getFirstCellNum(); // 列数

        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            // 行数据如果为空，不处理
            if (row == null) continue;

            XSSFCell cell = row.getCell(col - 1);
            String key = ExcelUtils.getCellValue(cell);
            if(StringUtils.isBlank(key)) continue;

            // 去掉所有空格
            key = key.replaceAll("\\s*", "");

            String code = null;
            List<String> codeList = new ArrayList<>();
            if (roleType == 1) {

                CadreViewExample example = new CadreViewExample();
                CadreViewExample.Criteria criteria = example.createCriteria();
                if (colType == 0) {
                    // 身份证
                    criteria.andIdcardEqualTo(key);
                } else {
                    // 姓名
                    criteria.andRealnameEqualTo(key);
                }
                List<CadreView> cvs = cadreViewMapper.selectByExample(example);
                if (cvs.size() == 1) {
                    code = cvs.get(0).getCode();
                } else if (cvs.size() > 1) {
                    for (CadreView cv : cvs) {
                        codeList.add(cv.getCode());
                    }
                }
            } else {
                SysUserViewExample example = new SysUserViewExample();
                SysUserViewExample.Criteria criteria = example.createCriteria();
                if (colType == 0) {
                    // 身份证
                    criteria.andIdcardEqualTo(key);
                } else {
                    // 姓名
                    criteria.andRealnameEqualTo(key);
                }
                if (type != 0) {
                    criteria.andTypeEqualTo(type);
                }

                if(colType==0){
                    // 如果是使用身份证查找，则按账号类别 教职工、研究生、本科生的排序
                    example.setOrderByClause("field(type, 1,3,2) asc");
                }

                List<SysUserView> uvs = sysUserViewMapper.selectByExample(example);

                if (uvs.size() == 1) {
                    code = uvs.get(0).getCode();
                } else if (uvs.size() > 1) {

                    SysUserView firstUv = uvs.get(0);
                    byte _type = firstUv.getType();
                    int _typeNum = 0; // 第一个账号类别对应的账号数量
                    for (SysUserView uv : uvs) {
                        if(_type==uv.getType()) _typeNum++;
                        codeList.add(uv.getCode());
                    }

                    if(colType==0 && _typeNum==1){
                        // 按身份证查找时，如果排第一的账号类型对应的账号数量只有一个，则认为是他当前使用的账号
                        code = firstUv.getCode();
                    }
                }
            }
            // 每一行插入的位置
            int rowAddCol = -1;
            if (addCol != null && addCol <= cellNum) {
                rowAddCol = addCol-1;
                cell = row.getCell(rowAddCol);
                if(cell==null){
                    cell = row.createCell(rowAddCol);
                }
                cell.setCellValue(StringUtils.trimToEmpty(code));
            } else {
                rowAddCol = cellNum;
                cell = row.createCell(rowAddCol);
                cell.setCellValue(StringUtils.trimToEmpty(code));
            }

            if (codeList.size() > 0) {

                if (rowAddCol < cellNum) {
                    rowAddCol = cellNum;
                } else {
                    rowAddCol = rowAddCol + 1;
                }

                cell = row.createCell(rowAddCol);
                cell.setCellValue(StringUtils.join(codeList, "、"));
            }
        }

        String savePath = FILE_SEPARATOR + "_filterExport"
                + FILE_SEPARATOR + DateUtils.formatDate(new Date(), DateUtils.YYYYMMDD) + ".xlsx";
        FileUtils.mkdirs(springProps.uploadPath + savePath, true);

        ExportHelper.save(workbook, springProps.uploadPath + savePath);

        Map<String, Object> resultMap = success();
        resultMap.put("file", savePath);

        return resultMap;
    }

    // 批量更新信息
    @RequiresPermissions("sysUser:edit")
    @RequestMapping("/sysUser_batchUpdate")
    public String cadre_batchSort(ModelMap modelMap) {

        return "sys/sysUser/sysUser_batchUpdate";
    }

    @RequiresPermissions("sysUser:edit")
    @RequestMapping(value = "/sysUser_batchUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUser_batchUpdate(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<SysUserInfo> records = new ArrayList<>();
        List<TeacherInfo> teacherInfos = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {
            row++;

            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)) {
                continue; // 学工号为空则忽略行
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }

            SysUserInfo record = new SysUserInfo();
            record.setUserId(uv.getId());
            record.setPhone(StringUtils.trimToNull(xlsRow.get(2)));
            record.setMobile(StringUtils.trimToNull(xlsRow.get(3)));
            record.setEmail(StringUtils.trimToNull(xlsRow.get(4)));

            record.setNativePlace(StringUtils.trimToNull(xlsRow.get(5)));
            record.setHomeplace(StringUtils.trimToNull(xlsRow.get(6)));
            record.setHousehold(StringUtils.trimToNull(xlsRow.get(7)));

            String _health = StringUtils.trimToNull(xlsRow.get(9));
            if(_health!=null) {
                MetaType healthType = CmTag.getMetaTypeByName("mc_health", _health);
                if (healthType == null) throw new OpException("第{0}行健康状态[{1}]不存在", row, _health);
                record.setHealth(healthType.getId());
            }
            record.setSpecialty(StringUtils.trimToNull(xlsRow.get(10)));

            records.add(record);

            if(uv.getType()== SystemConstants.USER_TYPE_JZG) {
                Date workTime = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(8)));
                if(workTime!=null) {
                    TeacherInfo ti = new TeacherInfo();
                    ti.setUserId(uv.getId());
                    ti.setWorkTime(workTime);

                    teacherInfos.add(ti);
                }
            }
        }

        sysUserService.batchUpdate(records, teacherInfos);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("total", records.size());
        return resultMap;
    }

    public void sysUser_export(SysUserViewExample example, HttpServletResponse response) throws IOException {

        List<SysUserView> records = sysUserViewMapper.selectByExample(example);

		int rownum = records.size();
        String[] titles = {"账号|120", "学工号|120", "姓名|100", "性别|50", "账号类别|100", "出生年月|100",
                "所在单位|300", "办公电话|100", "手机号码|100", "邮箱|300|left"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {

            SysUserView record = records.get(i);
            String[] values = {
                    record.getUsername(),
                    record.getCode(),
                    record.getRealname(),
                    record.getGender()==null?"": SystemConstants.GENDER_MAP.get(record.getGender()),
                    SystemConstants.USER_TYPE_MAP.get(record.getType()),
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYYMMDD_DOT),
                    record.getUnit(),
                    record.getPhone(),
                    record.getMobile(),
                    record.getEmail()
            };
            valuesList.add(values);
        }

        String fileName = "系统账号信息";
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
