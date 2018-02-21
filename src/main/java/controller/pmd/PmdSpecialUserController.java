package controller.pmd;

import bean.XlsPmdSpecialUser;
import bean.XlsUpload;
import domain.pmd.PmdSpecialUser;
import domain.pmd.PmdSpecialUserExample;
import domain.pmd.PmdSpecialUserExample.Criteria;
import mixin.MixinUtils;
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
import sys.constants.SystemConstants;
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
@RequestMapping("/pmd")
public class PmdSpecialUserController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdSpecialUser:list")
    @RequestMapping("/pmdSpecialUser")
    public String pmdSpecialUser() {

        return "pmd/pmdSpecialUser/pmdSpecialUser_page";
    }

    @RequiresPermissions("pmdSpecialUser:list")
    @RequestMapping("/pmdSpecialUser_data")
    public void pmdSpecialUser_data(HttpServletResponse response,
                                    String type,
                                    String code,
                                    String realname,
                                    String unit,
                                    @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdSpecialUserExample example = new PmdSpecialUserExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (StringUtils.isNotBlank(type)) {
            criteria.andTypeLike("%" + type + "%");
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }
        if (StringUtils.isNotBlank(realname)) {
            criteria.andRealnameLike("%" + realname + "%");
        }
        if (StringUtils.isNotBlank(unit)) {
            criteria.andUnitLike("%" + unit + "%");
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            pmdSpecialUser_export(example, response);
            return;
        }

        long count = pmdSpecialUserMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdSpecialUser> records = pmdSpecialUserMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdSpecialUser.class, pmdSpecialUserMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmdSpecialUser:edit")
    @RequestMapping(value = "/pmdSpecialUser_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSpecialUser_au(PmdSpecialUser record, HttpServletRequest request) {

        Integer id = record.getId();

        if (pmdSpecialUserService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }
        if (id == null) {
            pmdSpecialUserService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_PMD, "添加特殊人员：%s", record.getId()));
        } else {

            pmdSpecialUserService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_PMD, "更新特殊人员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdSpecialUser:edit")
    @RequestMapping("/pmdSpecialUser_au")
    public String pmdSpecialUser_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PmdSpecialUser pmdSpecialUser = pmdSpecialUserMapper.selectByPrimaryKey(id);
            modelMap.put("pmdSpecialUser", pmdSpecialUser);
        }
        return "pmd/pmdSpecialUser/pmdSpecialUser_au";
    }

    @RequiresPermissions("pmdSpecialUser:import")
    @RequestMapping("/pmdSpecialUser_import")
    public String pmdSpecialUser_import() {

        return "pmd/pmdSpecialUser/pmdSpecialUser_import";
    }

    @RequiresPermissions("pmdSpecialUser:import")
    @RequestMapping(value="/pmdSpecialUser_import", method=RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSpecialUser_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        List<XlsPmdSpecialUser> records = new ArrayList<XlsPmdSpecialUser>();

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        records.addAll(XlsUpload.fetchPmdSpecialUsers(sheet));

        int successCount = pmdSpecialUserService.imports(records);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    @RequiresPermissions("pmdSpecialUser:del")
    @RequestMapping(value = "/pmdSpecialUser_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSpecialUser_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pmdSpecialUserService.del(id);
            logger.info(addLog(SystemConstants.LOG_PMD, "删除特殊人员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdSpecialUser:del")
    @RequestMapping(value = "/pmdSpecialUser_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pmdSpecialUserService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_PMD, "批量删除特殊人员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void pmdSpecialUser_export(PmdSpecialUserExample example, HttpServletResponse response) {

        List<PmdSpecialUser> records = pmdSpecialUserMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"类型|100", "code|100", "realname|100", "unit|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PmdSpecialUser record = records.get(i);
            String[] values = {
                    record.getType(),
                    record.getCode(),
                    record.getRealname(),
                    record.getUnit()
            };
            valuesList.add(values);
        }
        String fileName = "特殊人员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
