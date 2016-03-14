package controller.cadre;

import bean.XlsCadre;
import bean.XlsUpload;
import controller.BaseController;
import domain.*;
import domain.CadreExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CadreController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadre:list")
    @RequestMapping("/cadre")
    public String cadre() {

        return "index";
    }
    @RequiresPermissions("cadre:list")
    @RequestMapping("/cadre_page")
    public String cadre_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order",tableName = "base_cadre") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 @RequestParam(required = false, defaultValue = "1")Byte status,
                                    Integer cadreId,
                                    Integer typeId,
                                    Integer postId,
                                    String title,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        modelMap.put("status", status);

        CadreExample example = new CadreExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            if(cadre!=null) {
                SysUser sysUser = sysUserService.findById(cadre.getUserId());
                modelMap.put("sysUser", sysUser);
            }
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
            cadre_export(example, response);
            return null;
        }

        int count = cadreMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Cadre> Cadres = cadreMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cadres", Cadres);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId!=null) {
            searchStr += "&cadreId=" + cadreId;
        }
        if (typeId!=null) {
            searchStr += "&typeId=" + typeId;
        }
        if (postId!=null) {
            searchStr += "&postId=" + postId;
        }
        if (StringUtils.isNotBlank(title)) {
            searchStr += "&title=" + title;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "cadre/cadre_page";
    }


    @RequiresPermissions("cadre:info")
    @RequestMapping("/cadre_view")
    public String cadre_show_page(HttpServletResponse response,  ModelMap modelMap) {

        return "cadre/cadre_view";
    }

    // 基本信息
    @RequiresPermissions("cadre:info")
    @RequestMapping("/cadre_base")
    public String cadre_base(Integer id, ModelMap modelMap) {

        Cadre cadre = cadreService.findAll().get(id);
        modelMap.put("cadre", cadre);

        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);
        modelMap.put("member", memberService.get(sysUser.getId()));

        // 人事信息
        ExtJzg extJzg = extJzgService.getByCode(sysUser.getCode());
        modelMap.put("extJzg", extJzg);

        modelMap.put("unitMap", unitService.findAll());
        modelMap.put("unitTypeMap", metaTypeService.metaTypes("mc_unit_type"));
        // 主职
        modelMap.put("cadreMainWork", cadreMainWorkService.getByCadreId(id));

        // 现任职务
        modelMap.put("cadrePost", cadrePostService.getPresentByCadreId(id));

        // 兼职单位
        List<CadreSubWork> cadreSubWorks = cadreSubWorkService.findByCadreId(id);
        if(cadreSubWorks.size()>=1){
            modelMap.put("cadreSubWork1", cadreSubWorks.get(0));
        }
        if(cadreSubWorks.size()>=2){
            modelMap.put("cadreSubWork2", cadreSubWorks.get(1));
        }

        modelMap.put("eduTypeMap", metaTypeService.metaTypes("mc_edu"));
        modelMap.put("learnStyleMap", metaTypeService.metaTypes("mc_learn_style"));
        modelMap.put("schoolTypeMap", metaTypeService.metaTypes("mc_school"));
        // 最高学历
        modelMap.put("highEdu", cadreEduService.getHighEdu(id));
        //最高学位
        modelMap.put("highDegree", cadreEduService.getHighDegree(id));

        return "cadre/cadre_base";
    }

    // 人事信息
    @RequiresPermissions("cadre:info")
    @RequestMapping("/cadre_personnel")
    public String cadre_personnel(Integer id, ModelMap modelMap) {

        Cadre cadre = cadreService.findAll().get(id);
        //modelMap.put("cadre", cadre);
        return "cadre/cadre_personnel";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_pass", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_pass(int id, HttpServletRequest request) {

        Cadre record = new Cadre();
        record.setStatus(SystemConstants.CADRE_STATUS_NOW);
        CadreExample example = new CadreExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(SystemConstants.CADRE_STATUS_TEMP);

        cadreService.updateByExampleSelective(record, example);

        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "干部通过常委会任命：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_leave")
    public String cadre_leave(int id, HttpServletResponse response,  ModelMap modelMap) {

        Cadre cadre = cadreService.findAll().get(id);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadre_leave";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_leave", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_leave(int id, Byte status, HttpServletRequest request) {

        if(status==null) status=SystemConstants.CADRE_STATUS_LEAVE;

        cadreService.leave(id, status);

        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "干部离任：%s，%s", id, SystemConstants.CADRE_STATUS_MAP.get(status)));
        return success(FormUtils.SUCCESS);
    }

    // for test 给所有的干部加上干部身份
    @RequiresRoles("admin")
    @RequestMapping(value = "/cadre_addAllCadreRole")
    @ResponseBody
    public Map do_cadre_addAllCadreRole() {

        Map<Integer, Cadre> cadreMap = cadreService.findAll();
        for (Cadre cadre : cadreMap.values()) {
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            // 添加干部身份
            sysUserService.addRole(sysUser.getId(), SystemConstants.ROLE_CADRE, sysUser.getUsername());
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_au(Cadre record, HttpServletRequest request) {

        Integer id = record.getId();
        if(cadreService.idDuplicate(id, record.getUserId())){
            return failed("添加重复");
        }

        if (id == null) {
            cadreService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加干部：%s", record.getId()));
        } else {

            cadreService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新干部：%s", record.getId()));
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
        return "cadre/cadre_au";
    }

    @RequiresPermissions("cadre:del")
    @RequestMapping(value = "/cadre_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_del(Integer id, HttpServletRequest request) {

        if (id != null) {

            cadreService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除干部：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:del")
    @RequestMapping(value = "/cadre_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            cadreService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除干部：%s", new Object[]{ids}));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:changeOrder")
    @RequestMapping(value = "/cadre_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_changeOrder(Integer id, byte status, Integer addNum, HttpServletRequest request) {

        cadreService.changeOrder(id, status, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "干部调序：%s, %s", id ,addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadre_export(CadreExample example, HttpServletResponse response) {

        List<Cadre> cadres = cadreMapper.selectByExample(example);
        int rownum = cadreMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"工号","姓名","行政级别","职务属性","单位及职务","备注"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            Cadre cadre = cadres.get(i);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                                            cadre.getTypeId()+"",
                                            cadre.getPostId()+"",
                                            cadre.getTitle(),
                                            cadre.getRemark()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "干部库_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
