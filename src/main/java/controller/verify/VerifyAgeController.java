package controller.verify;

import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.verify.VerifyAge;
import domain.verify.VerifyAgeExample;
import domain.verify.VerifyAgeExample.Criteria;
import mixin.MixinUtils;
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
import sys.constants.LogConstants;
import sys.constants.VerifyConstants;
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
public class VerifyAgeController extends VerifyBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("verifyAge:list")
    @RequestMapping("/verifyAge")
    public String verifyAge(@RequestParam(defaultValue = "1") Integer cls,
                                 Integer cadreId,
                                 ModelMap modelMap) {
        modelMap.put("cls", cls);
        if(cadreId!=null){
            modelMap.put("cadre", iCadreMapper.getCadre(cadreId));
        }
        return "verify/verifyAge/verifyAge_page";
    }

    @RequiresPermissions("verifyAge:list")
    @RequestMapping("/verifyAge_data")
    public void verifyAge_data(HttpServletResponse response,
                               Byte type,
                               Integer cadreId,
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
        Criteria criteria = example.createCriteria().andStatusEqualTo(VerifyConstants.VERIFY_STATUS_NORMAL);
        example.setOrderByClause("submit_time desc");

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if(cadreId!=null){
            criteria.andCadreIdEqualTo(cadreId);
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(verifyAge.class, verifyAgeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
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
    public Map do_verifyAge_au(VerifyAge record, int userId, HttpServletRequest request) {

        Cadre cadre = cadreService.getByUserId(userId);
        if(cadre==null){
            cadre = cadreService.addTempCadre(userId);
        }
        record.setCadreId(cadre.getId());

        if (verifyAgeService.idDuplicate(record.getCadreId())) {
            return failed("添加重复");
        }

        verifyAgeService.insertSelective(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "添加出生时间认定：%s", record.getId()));

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
                                   HttpServletRequest request) {

        verifyAgeService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "出生时间认定：%s", record.getId()));

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
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除出生时间认定：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void verifyAge_export(VerifyAgeExample example, HttpServletResponse response) {

        List<VerifyAge> records = verifyAgeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号|100", "姓名|80", "所在单位及职务|255", "认定类别|150", "认定前日期|100", "认定后日期|100", "材料名称|100",
                "形成时间|110", "记载的出生时间|110", "形成时间|110", "记载的出生时间|110", "备注|200"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            VerifyAge record = records.get(i);
            CadreView cadre = record.getCadre();
            String[] values = {
                    cadre.getCode(),
                    cadre.getRealname(),
                    cadre.getTitle(),
                    VerifyConstants.VERIFY_AGE_TYPE_MAP.get(record.getType()),
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
