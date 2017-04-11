package controller.verify;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.verify.VerifyWorkTime;
import domain.verify.VerifyWorkTimeExample;
import domain.verify.VerifyWorkTimeExample.Criteria;
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
public class VerifyWorkTimeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("verifyWorkTime:list")
    @RequestMapping("/verifyWorkTime")
    public String verifyWorkTime() {

        return "index";
    }

    @RequiresPermissions("verifyWorkTime:list")
    @RequestMapping("/verifyWorkTime_page")
    public String verifyWorkTime_page(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {
        modelMap.put("cls", cls);
        return "verify/verifyWorkTime/verifyWorkTime_page";
    }

    @RequiresPermissions("verifyWorkTime:list")
    @RequestMapping("/verifyWorkTime_data")
    public void verifyWorkTime_data(HttpServletResponse response,
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

        VerifyWorkTimeExample example = new VerifyWorkTimeExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.VERIFY_STATUS_NORMAL);
        example.setOrderByClause("submit_time desc");

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            verifyWorkTime_export(example, response);
            return;
        }

        int count = verifyWorkTimeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<VerifyWorkTime> records = verifyWorkTimeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(verifyWorkTime.class, verifyWorkTimeMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }
    @RequiresPermissions("verifyWorkTime:list")
    @RequestMapping("/verifyWorkTimeLog")
    public String verifyWorkTime_log(int id, ModelMap modelMap) {

        VerifyWorkTime verifyWorkTime = verifyWorkTimeMapper.selectByPrimaryKey(id);
        int cadreId = verifyWorkTime.getCadreId();

        VerifyWorkTimeExample example = new VerifyWorkTimeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("submit_time desc");
        criteria.andCadreIdEqualTo(cadreId);

        List<VerifyWorkTime> records = verifyWorkTimeMapper.selectByExample(example);
        modelMap.put("records", records);

        return "verify/verifyWorkTime/verifyWorkTimeLog";
    }

    @RequiresPermissions("verifyWorkTime:edit")
    @RequestMapping(value = "/verifyWorkTime_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_verifyWorkTime_au(VerifyWorkTime record, HttpServletRequest request) {


        if (verifyWorkTimeService.idDuplicate(record.getCadreId())) {
            return failed("添加重复");
        }
        verifyWorkTimeService.insertSelective(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "添加参加工作时间认定：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("verifyWorkTime:edit")
    @RequestMapping("/verifyWorkTime_au")
    public String verifyWorkTime_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            VerifyWorkTime verifyWorkTime = verifyWorkTimeMapper.selectByPrimaryKey(id);
            modelMap.put("verifyWorkTime", verifyWorkTime);
        }
        return "verify/verifyWorkTime/verifyWorkTime_au";
    }

    @RequiresPermissions("verifyWorkTime:edit")
    @RequestMapping(value = "/verifyWorkTime_verify", method = RequestMethod.POST)
    @ResponseBody
    public Map do_verifyWorkTime_verify(VerifyWorkTime record,
                                   String _materialTime,
                                   String _materialWorkTime,
                                   String _adTime,
                                   String _oldWorkTime,
                                   String _verifyWorkTime,
                                   HttpServletRequest request) {

        record.setMaterialTime(DateUtils.parseDate(_materialTime, DateUtils.YYYY_MM_DD));
        record.setMaterialWorkTime(DateUtils.parseDate(_materialWorkTime, DateUtils.YYYY_MM_DD));
        record.setAdTime(DateUtils.parseDate(_adTime, DateUtils.YYYY_MM_DD));
        record.setOldWorkTime(DateUtils.parseDate(_oldWorkTime, DateUtils.YYYY_MM_DD));
        record.setVerifyWorkTime(DateUtils.parseDate(_verifyWorkTime, DateUtils.YYYY_MM_DD));

        verifyWorkTimeService.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "参加工作时间认定：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("verifyWorkTime:edit")
    @RequestMapping("/verifyWorkTime_verify")
    public String verifyWorkTime_verify(Integer id, ModelMap modelMap) {

        if (id != null) {
            VerifyWorkTime verifyWorkTime = verifyWorkTimeMapper.selectByPrimaryKey(id);
            modelMap.put("verifyWorkTime", verifyWorkTime);
        }
        return "verify/verifyWorkTime/verifyWorkTime_verify";
    }

    @RequiresPermissions("verifyWorkTime:del")
    @RequestMapping(value = "/verifyWorkTime_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            verifyWorkTimeService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除参加工作时间认定：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void verifyWorkTime_export(VerifyWorkTimeExample example, HttpServletResponse response) {

        List<VerifyWorkTime> records = verifyWorkTimeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号", "姓名", "所在单位及职务", "认定类别", "认定前参加工作时间", "认定后参加工作时间", "材料名称",
                "形成时间", "记载的参加工作时间", "形成时间", "备注",};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            VerifyWorkTime record = records.get(i);
            Cadre cadre = record.getCadre();
            String[] values = {
                    cadre.getUser().getCode(),
                    cadre.getUser().getRealname(),
                    cadre.getTitle(),
                    SystemConstants.VERIFY_WORK_TIME_TYPE_MAP.get(record.getType()),
                    DateUtils.formatDate(record.getOldWorkTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getVerifyWorkTime(), DateUtils.YYYY_MM_DD),
                    record.getMaterialName(),
                    DateUtils.formatDate(record.getMaterialTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getMaterialWorkTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getAdTime(), DateUtils.YYYY_MM_DD),
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "参加工作时间认定_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
