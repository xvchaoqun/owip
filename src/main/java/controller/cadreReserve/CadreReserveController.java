package controller.cadreReserve;

import bean.XlsCadre;
import bean.XlsCadreReserve;
import bean.XlsUpload;
import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.cadreReserve.CadreReserve;
import domain.cadreReserve.CadreReserveView;
import domain.cadreReserve.CadreReserveViewExample;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import sys.utils.ExportHelper;
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
public class CadreReserveController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreReserve:list")
    @RequestMapping("/cadreReserve")
    public String cadreReserve() {

        return "index";
    }

    @RequiresPermissions("cadreReserve:list")
    @RequestMapping("/cadreReserve_page")
    public String cadreReserve_page(Byte status, Byte reserveType,
                                    Integer userId, ModelMap modelMap) {

        if (status == null && reserveType == null) {
            // 默认页面
            reserveType = SystemConstants.CADRE_RESERVE_TYPE_SCHOOL;
        }
        if (reserveType != null) {
            // 正常状态的后备干部库，读取指定的类别
            status = SystemConstants.CADRE_RESERVE_STATUS_NORMAL;
        }
        if (status != SystemConstants.CADRE_RESERVE_STATUS_NORMAL) {
            // 非正常状态的后备干部库，读取全部的类别
            reserveType = null;
        }

        modelMap.put("status", status);
        modelMap.put("reserveType", reserveType);

        if (userId != null) {
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
        }

        return "cadreReserve/cadreReserve_page";
    }

    @RequiresPermissions("cadreReserve:list")
    @RequestMapping("/cadreReserve_data")
    public void cadreReserve_data(HttpServletResponse response, Byte status, Byte reserveType,
                                  Integer userId,
                                  Integer typeId,
                                  Integer postId,
                                  String title,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {

        if (status == null && reserveType == null) {
            // 默认页面
            reserveType = SystemConstants.CADRE_RESERVE_TYPE_SCHOOL;
        }
        if (reserveType != null) {
            // 正常状态的后备干部库，读取指定的类别
            status = SystemConstants.CADRE_RESERVE_STATUS_NORMAL;
        }
        if (status != SystemConstants.CADRE_RESERVE_STATUS_NORMAL) {
            // 非正常状态的后备干部库，读取全部的类别
            reserveType = null;
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreReserveViewExample example = new CadreReserveViewExample();
        CadreReserveViewExample.Criteria criteria = example.createCriteria();

        if (status != null)
            criteria.andReserveStatusEqualTo(status);
        if (status == null || status == SystemConstants.CADRE_RESERVE_STATUS_NORMAL)
            criteria.andReserveTypeEqualTo(reserveType);

        example.setOrderByClause("reserve_sort_order desc");

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
                criteria.andIdIn(Arrays.asList(ids));
            cadreReserve_export(example, response);
            return;
        }

        int count = cadreReserveViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreReserveView> Cadres = cadreReserveViewMapper.
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


    // 只有干部库中类型为后备干部时，才可以修改干部库的信息
    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping(value = "/cadreReserve_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_au(int userId, Integer reserveId, Byte reserveType, String reserveRemark,
                                  Cadre cadreRecord, HttpServletRequest request) {

        CadreReserve record = new CadreReserve();
        record.setId(reserveId);
        record.setType(reserveType);
        record.setRemark(reserveRemark);
        if (reserveId == null) {
            cadreReserveService.insertSelective(userId, record, cadreRecord);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加后备干部：%s", record.getId()));
        } else {
            cadreReserveService.updateByPrimaryKeySelective(record, cadreRecord);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新后备干部：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping("/cadreReserve_au")
    public String cadreReserve_au(Integer id, byte reserveType, ModelMap modelMap) {

        if (id != null) {
            CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(id);
            reserveType = cadreReserve.getType();
            modelMap.put("cadreReserve", cadreReserve);
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreReserve.getCadreId());
            modelMap.put("cadre", cadre);
        }
        modelMap.put("reserveType", reserveType);

        return "cadreReserve/cadreReserve_au";
    }

    /*@RequiresPermissions("cadreReserve:del")
    @RequestMapping(value = "/cadreReserve_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserve_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            cadreReserveService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除后备干部：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping("/cadreReserve_pass")
    public String cadreReserve_pass(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(id);
            Cadre cadre = cadreService.findAll().get(cadreReserve.getCadreId());
            modelMap.put("cadreReserve", cadreReserve);
            modelMap.put("cadre", cadre);
        }
        return "cadreReserve/cadreReserve_pass";
    }

    // 列为考察对象
    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping(value = "/cadreReserve_pass", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_pass(Integer reserveId, String reserveRemark,
                                 Cadre cadreRecord, HttpServletRequest request) {

        CadreReserve record = new CadreReserve();
        record.setId(reserveId);
        record.setRemark(reserveRemark);

        Cadre cadre = cadreReserveService.pass(record, cadreRecord);

        SysUserView user = cadre.getUser();
        logger.info(addLog(SystemConstants.LOG_ADMIN, "后备干部列为考察对象：%s-%s", user.getRealname(), user.getCode()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:abolish")
    @RequestMapping(value = "/cadreReserve_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserve_abolish(HttpServletRequest request, Integer id, ModelMap modelMap) {

        cadreReserveService.abolish(id);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "撤销后备干部：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:changeOrder")
    @RequestMapping(value = "/cadreReserve_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreReserveService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "后备干部调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadreReserve_export(CadreReserveViewExample example, HttpServletResponse response) {

        List<CadreReserveView> records = cadreReserveViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工号", "姓名", "行政级别", "职务属性", "职务", "所在单位及职务", "手机号", "办公电话", "家庭电话", "电子邮箱", "备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CadreReserveView record = records.get(i);
            SysUserView sysUser = record.getUser();
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

    @RequiresPermissions("cadreReserve:import")
    @RequestMapping("/cadreReserve_import")
    public String cadreReserve_import(byte reserveType, ModelMap modelMap) {

        modelMap.put("reserveType", reserveType);
        return "cadreReserve/cadreReserve_import";
    }

    @RequiresPermissions("cadreReserve:import")
    @RequestMapping(value = "/cadreReserve_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_import(HttpServletRequest request, byte reserveType) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        List<XlsCadreReserve> cadreReserves = new ArrayList<XlsCadreReserve>();

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        cadreReserves.addAll(XlsUpload.fetchCadreReserves(sheet));

        int successCount = cadreReserveService.importCadreReserves(cadreReserves, reserveType);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", cadreReserves.size());

        return resultMap;
    }
}
