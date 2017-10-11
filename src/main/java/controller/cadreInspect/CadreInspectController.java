package controller.cadreInspect;

import bean.XlsCadreInspect;
import bean.XlsUpload;
import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.cadreInspect.CadreInspect;
import domain.cadreInspect.CadreInspectView;
import domain.cadreInspect.CadreInspectViewExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadreInspectController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreInspect:list")
    @RequestMapping("/cadreInspect")
    public String cadreInspect(
            @RequestParam(required = false, defaultValue =
                    SystemConstants.CADRE_INSPECT_STATUS_NORMAL + "") Byte status,
            Integer userId, ModelMap modelMap) {

        modelMap.put("status", status);
        if (userId != null) {
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
        }

        return "cadreInspect/cadreInspect_page";
    }

    @RequiresPermissions("cadreInspect:list")
    @RequestMapping("/cadreInspect_data")
    public void cadreInspect_data(HttpServletResponse response,
                                  @RequestParam(required = false, defaultValue =
                                          SystemConstants.CADRE_INSPECT_STATUS_NORMAL + "") Byte status,
                                  Integer userId,
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

        CadreInspectViewExample example = new CadreInspectViewExample();
        CadreInspectViewExample.Criteria criteria = example.createCriteria()
                .andInspectStatusEqualTo(status);
        example.setOrderByClause("inspect_sort_order asc");

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (typeId != null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (postId != null) {
            criteria.andPostIdEqualTo(postId);
        }

        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike("%" + title + "%");
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andInspectIdIn(Arrays.asList(ids));
            cadreInspect_export(example, response);
            return;
        }

        long count = cadreInspectViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreInspectView> Cadres = cadreInspectViewMapper.
                selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Cadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }


    // 只有干部库中类型为考察对象时，才可以修改干部库的信息
    @RequiresPermissions("cadreInspect:edit")
    @RequestMapping(value = "/cadreInspect_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInspect_au(int userId, Integer inspectId, String inspectRemark,
                                  Cadre cadreRecord, HttpServletRequest request) {

        CadreInspect record = new CadreInspect();
        record.setId(inspectId);
        record.setRemark(inspectRemark);
        if (inspectId == null) {
            cadreInspectService.insertSelective(userId, record, cadreRecord);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加考察对象：%s", record.getId()));
        } else {
            cadreInspectService.updateByPrimaryKeySelective(record, cadreRecord);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新考察对象：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreInspect:edit")
    @RequestMapping("/cadreInspect_au")
    public String cadreInspect_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreInspect cadreInspect = cadreInspectMapper.selectByPrimaryKey(id);
            modelMap.put("cadreInspect", cadreInspect);
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreInspect.getCadreId());
            modelMap.put("cadre", cadre);
        }

        return "cadreInspect/cadreInspect_au";
    }

    @RequiresPermissions("cadreInspect:edit")
    @RequestMapping("/cadreInspect_pass")
    public String cadreInspect_pass(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreInspect cadreInspect = cadreInspectMapper.selectByPrimaryKey(id);
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreInspect.getCadreId());
            modelMap.put("cadreInspect", cadreInspect);
            modelMap.put("cadre", cadre);
        }
        return "cadreInspect/cadreInspect_pass";
    }

    // 通过常委会任命
    @RequiresPermissions("cadreInspect:edit")
    @RequestMapping(value = "/cadreInspect_pass", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInspect_pass(Integer inspectId, String inspectRemark,
                                    Cadre cadreRecord, HttpServletRequest request) {

        CadreInspect record = new CadreInspect();
        record.setId(inspectId);
        record.setRemark(inspectRemark);

        Cadre cadre = cadreInspectService.pass(record, cadreRecord);

        SysUserView user = cadre.getUser();
        logger.info(addLog(SystemConstants.LOG_ADMIN, "考察对象通过常委会任命：%s-%s", user.getRealname(), user.getCode()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreInspect:abolish")
    @RequestMapping(value = "/cadreInspect_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreInspect_abolish(HttpServletRequest request, Integer id, ModelMap modelMap) {

        cadreInspectService.abolish(id);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "撤销考察对象：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreInspect:changeOrder")
    @RequestMapping(value = "/cadreInspect_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInspect_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreInspectService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "考察对象调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    private void cadreInspect_export(CadreInspectViewExample example, HttpServletResponse response) {

        SXSSFWorkbook wb = cadreInspectExportService.export(example);

        String fileName = CmTag.getSysConfig().getSchoolName() + "考察对象_" + DateUtils.formatDate(new Date(), "yyyyMMdd");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    /*public void cadreInspect_export(CadreInspectViewExample example, HttpServletResponse response) {

        List<CadreInspectView> records = cadreInspectViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工号","姓名","行政级别","职务属性","职务","所在单位及职务","手机号","办公电话","家庭电话","电子邮箱","备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CadreInspectView record = records.get(i);
            SysUserView sysUser =  record.getUser();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    metaTypeService.getName(record.getTypeId()),
                    metaTypeService.getName(record.getPostId()),
                    record.getPost(),
                    record.getTitle(),
                    record.getMobile(),
                    record.getPhone(),
                    record.getHomePhone(),
                    record.getEmail(),
                    record.getRemark()
            };
            valuesList.add(values);
        }

        String fileName = "考察对象_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }*/

    @RequiresPermissions("cadreInspect:import")
    @RequestMapping("/cadreInspect_import")
    public String cadreInspect_import() {

        return "cadreInspect/cadreInspect_import";
    }

    @RequiresPermissions("cadreInspect:import")
    @RequestMapping(value = "/cadreInspect_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInspect_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        List<XlsCadreInspect> cadreInspects = new ArrayList<XlsCadreInspect>();

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        cadreInspects.addAll(XlsUpload.fetchCadreInspects(sheet));

        int successCount = cadreInspectService.importCadreInspects(cadreInspects);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", cadreInspects.size());

        return resultMap;
    }
}
