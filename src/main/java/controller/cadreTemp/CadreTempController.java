package controller.cadreTemp;

import bean.XlsCadreTemp;
import bean.XlsUpload;
import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadreTemp.CadreTemp;
import domain.cadreTemp.CadreTempView;
import domain.cadreTemp.CadreTempViewExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
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
import service.helper.ExportHelper;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CadreTempController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreTemp:list")
    @RequestMapping("/cadreTemp")
    public String cadreTemp() {

        return "index";
    }

    @RequiresPermissions("cadreTemp:list")
    @RequestMapping("/cadreTemp_page")
    public String cadreTemp_page(
            @RequestParam(required = false, defaultValue =
                    SystemConstants.CADRE_TEMP_STATUS_NORMAL + "")Byte status,
            Integer userId, ModelMap modelMap) {

        modelMap.put("status", status);
        if (userId!=null) {
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
        }

        return "cadreTemp/cadreTemp_page";
    }
    @RequiresPermissions("cadreTemp:list")
    @RequestMapping("/cadreTemp_data")
    public void cadreTemp_data(HttpServletResponse response,
                               @RequestParam(required = false, defaultValue =
                                       SystemConstants.CADRE_TEMP_STATUS_NORMAL + "")Byte status,
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

        CadreTempViewExample example = new CadreTempViewExample();
        CadreTempViewExample.Criteria criteria = example.createCriteria()
                .andTempStatusEqualTo(status);
        example.setOrderByClause("temp_sort_order desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
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
            cadreTemp_export(example, response);
            return;
        }

        int count = cadreTempViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreTempView> Cadres = cadreTempViewMapper.
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
    @RequiresPermissions("cadreTemp:edit")
    @RequestMapping(value = "/cadreTemp_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTemp_au(int userId, Integer tempId, String tempRemark,
                                  Cadre cadreRecord, HttpServletRequest request) {

        CadreTemp record = new CadreTemp();
        record.setId(tempId);
        record.setRemark(tempRemark);
        if (tempId == null) {
            cadreTempService.insertSelective(userId, record, cadreRecord);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加考察对象：%s", record.getId()));
        } else {
            cadreTempService.updateByPrimaryKeySelective(record, cadreRecord);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新考察对象：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTemp:edit")
    @RequestMapping("/cadreTemp_au")
    public String cadreTemp_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreTemp cadreTemp = cadreTempMapper.selectByPrimaryKey(id);
            modelMap.put("cadreTemp", cadreTemp);
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreTemp.getCadreId());
            modelMap.put("cadre", cadre);
        }

        return "cadreTemp/cadreTemp_au";
    }

    @RequiresPermissions("cadreTemp:edit")
    @RequestMapping("/cadreTemp_pass")
    public String cadreTemp_pass(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreTemp cadreTemp = cadreTempMapper.selectByPrimaryKey(id);
            Cadre cadre = cadreService.findAll().get(cadreTemp.getCadreId());
            modelMap.put("cadreTemp", cadreTemp);
            modelMap.put("cadre", cadre);
        }
        return "cadreTemp/cadreTemp_pass";
    }

    // 通过常委会任命
    @RequiresPermissions("cadreTemp:edit")
    @RequestMapping(value = "/cadreTemp_pass", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTemp_pass(Integer tempId, String tempRemark,
                                 Cadre cadreRecord, HttpServletRequest request) {

        CadreTemp record = new CadreTemp();
        record.setId(tempId);
        record.setRemark(tempRemark);

        Cadre cadre = cadreTempService.pass(record, cadreRecord);

        SysUserView user = cadre.getUser();
        logger.info(addLog(SystemConstants.LOG_ADMIN, "考察对象通过常委会任命：%s-%s", user.getRealname(), user.getCode()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTemp:abolish")
    @RequestMapping(value = "/cadreTemp_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreTemp_abolish(HttpServletRequest request, Integer id, ModelMap modelMap) {

        cadreTempService.abolish(id);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "撤销考察对象：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTemp:changeOrder")
    @RequestMapping(value = "/cadreTemp_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTemp_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreTempService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "考察对象调序：%s, %s", id ,addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadreTemp_export(CadreTempViewExample example, HttpServletResponse response) {

        List<CadreTempView> records = cadreTempViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工号","姓名","行政级别","职务属性","职务","所在单位及职务","手机号","办公电话","家庭电话","电子邮箱","备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CadreTempView record = records.get(i);
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
    }

    @RequiresPermissions("cadreTemp:import")
    @RequestMapping("/cadreTemp_import")
    public String cadreTemp_import() {

        return "cadreTemp/cadreTemp_import";
    }

    @RequiresPermissions("cadreTemp:import")
    @RequestMapping(value="/cadreTemp_import", method=RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTemp_import( HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        List<XlsCadreTemp> cadreTemps = new ArrayList<XlsCadreTemp>();

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        cadreTemps.addAll(XlsUpload.fetchCadreTemps(sheet));

        int successCount = cadreTempService.importCadreTemps(cadreTemps);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", cadreTemps.size());

        return resultMap;
    }
}
