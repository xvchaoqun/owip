package controller.cadre;

import bean.DispatchCadreRelateBean;
import bean.XlsCadre;
import bean.XlsUpload;
import controller.BaseController;
import domain.cadre.*;
import domain.dispatch.Dispatch;
import domain.ext.ExtJzg;
import domain.party.Branch;
import domain.party.Party;
import domain.base.MetaType;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import domain.unit.Unit;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sys.tool.xlsx.ExcelTool;
import sys.utils.*;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Controller
public class CadreController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadre:list")
    @RequestMapping("/cadre/search")
    public String search(){

        return "cadre/cadre_search";
    }
    @RequiresPermissions("cadre:list")
    @RequestMapping(value = "/cadre/search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_search(int cadreId) {

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        String msg = "";
        Cadre cadre = cadreService.findAll().get(cadreId);
        SysUserView sysUser = cadre.getUser();
        if(sysUser==null){
            msg = "该用户不存在";
        }else {
            resultMap.put("realname", sysUser.getRealname());

            if(cadre==null){
                msg = "该用户不是干部";
            }else{
                resultMap.put("cadreId", cadre.getId());
                resultMap.put("status", cadre.getStatus());
            }
        }
        resultMap.put("msg", msg);

        return resultMap;
    }

    @RequiresPermissions("cadre:list")
    @RequestMapping("/cadre")
    public String cadre() {

        return "index";
    }
    @RequiresPermissions("cadre:list")
    @RequestMapping("/cadre_page")
    public String cadre_page(@RequestParam(required = false, defaultValue = "1")Byte status,
                             Integer cadreId,ModelMap modelMap) {

        modelMap.put("status", status);

        if (cadreId!=null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            if(cadre!=null) {
                SysUserView sysUser = sysUserService.findById(cadre.getUserId());
                modelMap.put("sysUser", sysUser);
            }
        }

        return "cadre/cadre_page";
    }
    @RequiresPermissions("cadre:list")
    @RequestMapping("/cadre_data")
    public void cadre_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order",tableName = "cadre") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 @RequestParam(required = false, defaultValue = "1")Byte status,
                                    Integer cadreId,
                                    Integer typeId,
                                    Integer postId,
                                    String title,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreViewExample example = new CadreViewExample();
        CadreViewExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andIdEqualTo(cadreId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (postId!=null) {
            criteria.andPostIdEqualTo(postId);
        }
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike("%" + title + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cadre_export(status, example, response);
            return;
        }

        int count = cadreViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreView> Cadres = cadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Cadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(CadreView.class, CadreMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }


    @RequiresPermissions("cadre:view")
    @RequestMapping("/cadre_view")
    public String cadre_view(HttpServletResponse response,
                                  @RequestParam(defaultValue = "cadre_base")String to, // 默认跳转到基本信息
                                  ModelMap modelMap) {
        modelMap.put("to", to);

        return "cadre/cadre_view";
    }

    // 基本信息
    @RequiresPermissions("cadre:view")
    @RequestMapping("/cadre_base")
    public String cadre_base(Integer cadreId, ModelMap modelMap) {

        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);

        SysUserView uv = sysUserService.findById(cadre.getUserId());
        modelMap.put("uv", uv);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);
        modelMap.put("member", memberService.get(uv.getId()));

        TeacherInfo teacherInfo = teacherService.get(uv.getUserId());
        modelMap.put("teacherInfo", teacherInfo);

        // 人事信息
        ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
        modelMap.put("extJzg", extJzg);

        CadrePost mainCadrePost = cadrePostService.getCadreMainCadrePost(cadreId);
        // 主职,现任职务
        modelMap.put("mainCadrePost", mainCadrePost);

        // 任现职级
        modelMap.put("cadreAdminLevel",cadreAdminLevelService.getPresentByCadreId(cadreId,
                mainCadrePost!=null?mainCadrePost.getAdminLevelId():null));

        // 兼职单位
        List<CadrePost> subCadrePosts = cadrePostService.getSubCadrePosts(cadreId);
        if(subCadrePosts.size()>=1){
            modelMap.put("subCadrePost1", subCadrePosts.get(0));
        }
        if(subCadrePosts.size()>=2){
            modelMap.put("subCadrePost2", subCadrePosts.get(1));
        }

        // 最高学历
        modelMap.put("highEdu", cadreEduService.getHighEdu(cadreId));
        //最高学位
        modelMap.put("highDegree", cadreEduService.getHighDegree(cadreId));

        return "cadre/cadre_base";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_leave")
    public String cadre_leave(int id, HttpServletResponse response,  ModelMap modelMap) {

        Cadre cadre = cadreService.findAll().get(id);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        modelMap.put("cadre", cadre);

        TreeNode dispatchCadreTree = cadreCommonService.getDispatchCadreTree(id, SystemConstants.DISPATCH_CADRE_TYPE_DISMISS);
        modelMap.put("tree", JSONUtils.toString(dispatchCadreTree));

        return "cadre/cadre_leave";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_leave", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_leave(int id, Byte status, String title, Integer dispatchCadreId, HttpServletRequest request) {

        if(status==null) status=SystemConstants.CADRE_STATUS_LEAVE;

        cadreService.leave(id, status, StringUtils.trimToNull(title), dispatchCadreId);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部离任：%s，%s", id, SystemConstants.CADRE_STATUS_MAP.get(status)));
        return success(FormUtils.SUCCESS);
    }

    // 在“离任中层干部库”和“离任校领导干部库”中加一个按钮“重新任用”，点击这个按钮，可以转移到“考察对象”中去。
    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_re_assign", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_re_assign(@RequestParam(value = "ids[]") Integer[] ids) {

        cadreService.re_assign(ids);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部重新任用：%s", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    // for test 给所有的干部加上干部身份
    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping(value = "/cadre_addAllCadreRole")
    @ResponseBody
    public Map do_cadre_addAllCadreRole() {

        Map<Integer, Cadre> cadreMap = cadreService.findAll();
        for (Cadre cadre : cadreMap.values()) {
            SysUserView sysUser = cadre.getUser();
            // 添加干部身份
            sysUserService.addRole(sysUser.getId(), SystemConstants.ROLE_CADRE, sysUser.getUsername(), sysUser.getCode());
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_au(Cadre record, HttpServletRequest request) {

        Integer id = record.getId();
        if (id == null) {
            cadreService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部：%s", record.getId()));
        } else {
            record.setUserId(null); // 不能修改账号、干部类别
            record.setStatus(null);
            cadreService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_au")
    public String cadre_au(Integer id, Byte status, ModelMap modelMap) {

        modelMap.put("status", status);
        if (id != null) {
            Cadre cadre = cadreMapper.selectByPrimaryKey(id);
            modelMap.put("cadre", cadre);
            modelMap.put("status", cadre.getStatus());

            modelMap.put("sysUser", sysUserService.findById(cadre.getUserId()));
        }
        if(id!=null && (status==SystemConstants.CADRE_STATUS_LEAVE || status==SystemConstants.CADRE_STATUS_LEADER_LEAVE)){
            TreeNode dispatchCadreTree = cadreCommonService.getDispatchCadreTree(id, SystemConstants.DISPATCH_CADRE_TYPE_DISMISS);
            modelMap.put("tree", JSONUtils.toString(dispatchCadreTree));
        }

        if(status==SystemConstants.CADRE_STATUS_TEMP)
            return "cadre/cadre_temp_au";
        return "cadre/cadre_au";
    }

    /*@RequiresPermissions("cadre:del")
    @RequestMapping(value = "/cadre_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            cadreService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadre:changeOrder")
    @RequestMapping(value = "/cadre_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_changeOrder(Integer id, byte status, Integer addNum, HttpServletRequest request) {

        cadreService.changeOrder(id, status, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部调序：%s, %s", id ,addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadre_export(Byte status, CadreViewExample example, HttpServletResponse response) {

        String cadreType = SystemConstants.CADRE_STATUS_MAP.get(status);

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<CadreView> records = cadreViewMapper.selectByExample(example);

        int rowNum = 0;
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        //sheet.setDefaultColumnWidth(12);
        //sheet.setDefaultRowHeight((short)(20*60));
        {
            Row titleRow = sheet.createRow(rowNum);
            titleRow.setHeight((short) (35.7 * 30));
            Cell headerCell = titleRow.createCell(0);
            XSSFCellStyle cellStyle = wb.createCellStyle();
            // 设置单元格居中对齐
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // 设置单元格垂直居中对齐
            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
            XSSFFont font = wb.createFont();
            // 设置字体加粗
            font.setFontName("宋体");
            font.setFontHeight((short) 350);
            cellStyle.setFont(font);
            headerCell.setCellStyle(cellStyle);
            headerCell.setCellValue(PropertiesUtils.getString("site.school") + cadreType +"一览表");
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 9));
            rowNum++;
        }

        int count = records.size();
        String[] titles = {"工作证号","姓名","部门属性","所在单位","现任职务",
                "所在单位及职务","行政级别","职务属性", "是否正职","性别",
                "民族","籍贯","出生地","身份证号","出生时间",
                "年龄","党派","党派加入时间","参加工作时间","到校时间",
                "最高学历","最高学位","毕业时间","学习方式","毕业学校",
                "学校类型","所学专业","岗位类别", "主岗等级","专业技术职务",
                "专技职务评定时间","专技职务等级","专技岗位分级时间","管理岗位等级", "管理岗位分级时间",
                "现职务任命文件","任现职时间","现职务始任时间","现职务始任年限","现职级始任时间",
                "任现职级年限","兼任单位及职务", "兼任职务现任时间", "兼任职务始任时间", "是否双肩挑",
                "双肩挑单位","联系方式","党委委员", "纪委委员","电子信箱",
                "所属党组织","备注"};

        int columnCount = titles.length;
        XSSFRow firstRow = (XSSFRow) sheet.createRow(rowNum++);
        firstRow.setHeight((short) (35.7 * 12));
        for (int i = 0; i < columnCount; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(getHeadStyle(wb));
        }

        int columnIndex = 0;
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 工作证号
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 300));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 160));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 300)); // 所在单位及职务
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 50));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 民族
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 50));// 年龄
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120)); // 最高学历
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 学校类型
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 160));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200)); // 专技职务评定时间
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150)); // 现职务任命文件
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));//任现职级年限
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 250));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 180));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 500));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 500));

        for (int i = 0; i < count; i++) {
            CadreView record = records.get(i);
            SysUserView sysUser =  record.getUser();

            String isPositive = ""; // 是否正职
            CadrePost mainCadrePost = record.getMainCadrePost();
            if(mainCadrePost!=null && mainCadrePost.getPostId()!=null){
                MetaType metaType = metaTypeMap.get(mainCadrePost.getPostId());
                if(metaType!=null){
                    isPositive = (BooleanUtils.isTrue(metaType.getBoolAttr()))?"是":"否";
                }
            }

            String partyName = "";// 党派
            String partyAddTime = "";
            if(!record.getIsDp() && record.getGrowTime()!=null){
                partyName = "中共党员";
                partyAddTime = DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD);
            }else if(record.getIsDp()){
                partyName = metaTypeMap.get(record.getDpTypeId()).getName();
                partyAddTime = DateUtils.formatDate(record.getDpAddTime(), DateUtils.YYYY_MM_DD);
            }

            String postDispatchCode = ""; // 现职务任命文件
            String postTime = ""; // 任现职时间
            String postStartTime = ""; // 现职务始任时间
            String postYear = ""; // 现职务始任年限
            String adminLevelStartTime = ""; // 现职级始任时间
            String adminLevelYear = ""; // 任现职级年限
            String isDouble = ""; // 是否双肩挑
            String doubleUnit = ""; // 双肩挑单位
            if(mainCadrePost!=null){
                DispatchCadreRelateBean dispatchCadreRelateBean = mainCadrePost.getDispatchCadreRelateBean();
                if(dispatchCadreRelateBean!=null){
                    Dispatch first = dispatchCadreRelateBean.getFirst();
                    Dispatch last = dispatchCadreRelateBean.getLast();
                    if(first!=null){
                        postDispatchCode = first.getDispatchCode();
                        postStartTime = DateUtils.formatDate(first.getWorkTime(), DateUtils.YYYY_MM_DD);
                        Integer year = DateUtils.intervalYearsUntilNow(first.getWorkTime());
                        if(year==0) postYear= "未满一年";
                        else postYear = year + "";
                    }

                    if(last!=null){
                        postTime = DateUtils.formatDate(last.getWorkTime(), DateUtils.YYYY_MM_DD);
                    }
                }
                isDouble = BooleanUtils.isTrue(mainCadrePost.getIsDouble())?"是":"否";
                Integer doubleUnitId = mainCadrePost.getDoubleUnitId();
                if(doubleUnitId!=null){
                    Unit unit = unitMap.get(doubleUnitId);
                    doubleUnit = (unit!=null)?unit.getName():"";
                }
            }

            CadreAdminLevel presentAdminLevel = record.getPresentAdminLevel();
            if(presentAdminLevel!=null){
                Dispatch startDispatch = presentAdminLevel.getStartDispatch();
                Dispatch endDispatch = presentAdminLevel.getEndDispatch();

                Date endDate = new Date();
                if(endDispatch!=null) endDate =endDispatch.getWorkTime();
                if(startDispatch!=null){
                    adminLevelStartTime = DateUtils.formatDate(startDispatch.getWorkTime(), DateUtils.YYYY_MM_DD);

                    Integer monthDiff = DateUtils.monthDiff(startDispatch.getWorkTime(), endDate);
                    int year = monthDiff/12;
                    if(year==0) adminLevelYear= "未满一年";
                    else adminLevelYear = year + "";
                }
            }

            String partyFullName = ""; // 所属党组织
            if(record.getPartyId()!=null){
                Party party = partyMap.get(record.getPartyId());
                if(party!=null){
                    partyFullName = party.getName();
                    if(record.getBranchId()!=null){
                        Branch branch = branchMap.get(record.getBranchId());
                        if(branch!=null){
                            partyFullName += "-" + branch.getName();
                        }
                    }
                }
            }

            String subPost = ""; // 兼任单位及职务
            String subPostTime = ""; // 兼任职务现任时间
            String subPostStartTime = ""; // 兼任职务始任时间
            List<CadrePost> subCadrePosts = cadrePostService.getSubCadrePosts(record.getId());
            if(subCadrePosts.size()>0){
                CadrePost cadrePost = subCadrePosts.get(0);
                Unit unit = unitMap.get(cadrePost.getUnitId());
                MetaType metaType = metaTypeMap.get(cadrePost.getPostId());
                subPost = unit.getName() + ((metaType==null)?"":metaType.getName());

                DispatchCadreRelateBean dispatchCadreRelateBean = cadrePost.getDispatchCadreRelateBean();
                if(dispatchCadreRelateBean!=null){
                    Dispatch first = dispatchCadreRelateBean.getFirst();
                    Dispatch last = dispatchCadreRelateBean.getLast();

                    if(last!=null) subPostTime = DateUtils.formatDate(last.getWorkTime(), DateUtils.YYYY_MM_DD);
                    if(first!=null) subPostStartTime = DateUtils.formatDate(first.getWorkTime(), DateUtils.YYYY_MM_DD);
                }
            }

            Unit unit = record.getUnit();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    unit==null?"":unit.getUnitType().getName(),
                    unit==null?"":unit.getName(),
                    record.getPost(),

                    record.getTitle(),
                    metaTypeService.getName(record.getTypeId()),
                    metaTypeService.getName(record.getPostId()),
                    isPositive,
                    record.getGender()==null?"":SystemConstants.GENDER_MAP.get(record.getGender()),

                    record.getNation(),
                    record.getNativePlace(),
                    record.getUser().getHomeplace(),
                    record.getIdcard(),
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYY_MM_DD),

                    DateUtils.calAge(record.getBirth()),
                    partyName,
                    partyAddTime,
                    "", //参加工作时间
                    DateUtils.formatDate(record.getArriveTime(), DateUtils.YYYY_MM_DD),

                    metaTypeService.getName(record.getEduId()),
                    record.getDegree(),
                    DateUtils.formatDate(record.getFinishTime(), "yyyy.MM"),
                    metaTypeService.getName(record.getLearnStyle()),
                    record.getSchool(),

                    record.getSchoolType()==null?"":SystemConstants.CADRE_SCHOOL_TYPE_MAP.get(record.getSchoolType()),
                    record.getMajor(),
                    record.getPostClass(),
                    record.getMainPostLevel(),
                    record.getProPost(),

                    DateUtils.formatDate(record.getProPostTime(), DateUtils.YYYY_MM_DD),
                    record.getProPostLevel(),
                    DateUtils.formatDate(record.getProPostLevelTime(), DateUtils.YYYY_MM_DD),
                    record.getManageLevel(),
                    DateUtils.formatDate(record.getManageLevelTime(), DateUtils.YYYY_MM_DD),

                    postDispatchCode, // 现职务任命文件
                    postTime,
                    postStartTime,
                    postYear,
                    adminLevelStartTime,

                    adminLevelYear,
                    subPost,
                    subPostTime,
                    subPostStartTime,
                    isDouble,

                    doubleUnit,
                    record.getMobile(),
                    "", // 党委委员
                    "",
                    record.getEmail(),

                    partyFullName,
                    record.getRemark()
            };

            Row row = sheet.createRow(rowNum++);
            row.setHeight((short) (35.7 * 18));
            for (int j = 0; j < columnCount; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                String value = values[j];
                if(StringUtils.isBlank(value)) value="-";
                cell.setCellValue(value);
                cell.setCellStyle(getBodyStyle(wb));
            }
        }

        String fileName = PropertiesUtils.getString("site.school")  + cadreType+"(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static XSSFCellStyle getBodyStyle(XSSFWorkbook wb) {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        //font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 220);
        cellStyle.setFont(font);
        return cellStyle;
    }

    public static XSSFCellStyle getHeadStyle(XSSFWorkbook wb) {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 250);
        cellStyle.setFont(font);
        // 设置单元格边框为细线条
       /* cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);*/
        return cellStyle;
    }

    @RequiresPermissions("cadre:import")
    @RequestMapping("/cadre_import")
    public String cadre_import(byte status, ModelMap modelMap) {

        modelMap.put("status", status);
        return "cadre/cadre_import";
    }

    @RequiresPermissions("cadre:import")
    @RequestMapping(value="/cadre_import", method=RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_import( HttpServletRequest request, Byte status) throws InvalidFormatException, IOException {

        //User sessionUser = getAdminSessionUser(request);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        List<XlsCadre> cadres = new ArrayList<XlsCadre>();

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        for (int k = 0; k < workbook.getNumberOfSheets(); k++) {
            XSSFSheet sheet = workbook.getSheetAt(k);

            String sheetName = sheet.getSheetName();
            if(StringUtils.equals(sheetName, "干部")){

                cadres.addAll(XlsUpload.fetchCadres(sheet));
            }
        }

        int successCount = cadreService.importCadres(cadres, status);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", cadres.size());

        return resultMap;
    }
}
