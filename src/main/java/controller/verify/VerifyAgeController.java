package controller.verify;

import controller.BaseController;
import domain.cadre.CadreView;
import domain.verify.VerifyAge;
import domain.verify.VerifyAgeExample;
import domain.verify.VerifyAgeExample.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class VerifyAgeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("verifyAge:list")
    @RequestMapping("/verifyAge")
    public String verifyAge() {

        return "index";
    }

    @RequiresPermissions("verifyAge:list")
    @RequestMapping("/verifyAge_page")
    public String verifyAge_page(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {
        modelMap.put("cls", cls);
        return "verify/verifyAge/verifyAge_page";
    }

    @RequiresPermissions("verifyAge:list")
    @RequestMapping("/verifyAge_data")
    public void verifyAge_data(HttpServletResponse response,
                               Byte type,
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

        VerifyAgeExample example = new VerifyAgeExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.VERIFY_STATUS_NORMAL);
        example.setOrderByClause("submit_time desc");

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            verifyAge_export(example, response);
            return;
        }

        int count = verifyAgeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<VerifyAge> records = verifyAgeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(verifyAge.class, verifyAgeMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("verifyAge:list")
    @RequestMapping("/verifyAgeLog")
    public String verifyAge_log(int id, ModelMap modelMap) {

        VerifyAge verifyAge = verifyAgeMapper.selectByPrimaryKey(id);
        int cadreId = verifyAge.getCadreId();

        VerifyAgeExample example = new VerifyAgeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("submit_time desc");
        criteria.andCadreIdEqualTo(cadreId);

        List<VerifyAge> records = verifyAgeMapper.selectByExample(example);
        modelMap.put("records", records);

        return "verify/verifyAge/verifyAgeLog";
    }

    @RequiresPermissions("verifyAge:edit")
    @RequestMapping(value = "/verifyAge_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_verifyAge_au(VerifyAge record, HttpServletRequest request) {

        if (verifyAgeService.idDuplicate(record.getCadreId())) {
            return failed("添加重复");
        }

        verifyAgeService.insertSelective(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "添加出生时间认定：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("verifyAge:edit")
    @RequestMapping("/verifyAge_au")
    public String verifyAge_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            VerifyAge verifyAge = verifyAgeMapper.selectByPrimaryKey(id);
            modelMap.put("verifyAge", verifyAge);
        }
        return "verify/verifyAge/verifyAge_au";
    }

    @RequiresPermissions("verifyAge:edit")
    @RequestMapping(value = "/verifyAge_verify", method = RequestMethod.POST)
    @ResponseBody
    public Map do_verifyAge_verify(VerifyAge record,
                                   String _materialTime,
                                   String _materialBirth,
                                   String _adTime,
                                   String _adBirth,
                                   String _oldBirth,
                                   String _verifyBirth,
                                   HttpServletRequest request) {

        record.setMaterialTime(DateUtils.parseDate(_materialTime, DateUtils.YYYY_MM_DD));
        record.setMaterialBirth(DateUtils.parseDate(_materialBirth, DateUtils.YYYY_MM_DD));
        record.setAdTime(DateUtils.parseDate(_adTime, DateUtils.YYYY_MM_DD));
        record.setAdBirth(DateUtils.parseDate(_adBirth, DateUtils.YYYY_MM_DD));
        record.setOldBirth(DateUtils.parseDate(_oldBirth, DateUtils.YYYY_MM_DD));
        record.setVerifyBirth(DateUtils.parseDate(_verifyBirth, DateUtils.YYYY_MM_DD));

        verifyAgeService.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "出生时间认定：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("verifyAge:edit")
    @RequestMapping("/verifyAge_verify")
    public String verifyAge_verify(Integer id, ModelMap modelMap) {

        if (id != null) {
            VerifyAge verifyAge = verifyAgeMapper.selectByPrimaryKey(id);
            modelMap.put("verifyAge", verifyAge);
        }
        return "verify/verifyAge/verifyAge_verify";
    }

    // 假删除
    @RequiresPermissions("verifyAge:del")
    @RequestMapping(value = "/verifyAge_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            verifyAgeService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除出生时间认定：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void verifyAge_export(VerifyAgeExample example, HttpServletResponse response) {

        List<VerifyAge> records = verifyAgeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号", "姓名", "所在单位及职务", "认定类别", "认定前日期", "认定后日期", "材料名称",
                "形成时间", "记载的出生时间", "形成时间", "记载的出生时间", "备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            VerifyAge record = records.get(i);
            CadreView cadre = record.getCadre();
            String[] values = {
                    cadre.getUser().getCode(),
                    cadre.getUser().getRealname(),
                    cadre.getTitle(),
                    SystemConstants.VERIFY_AGE_TYPE_MAP.get(record.getType()),
                    DateUtils.formatDate(record.getOldBirth(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getVerifyBirth(), DateUtils.YYYY_MM_DD),
                    record.getMaterialName(),
                    DateUtils.formatDate(record.getMaterialTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getMaterialBirth(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getAdTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getAdBirth(), DateUtils.YYYY_MM_DD),
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "出生时间认定_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
