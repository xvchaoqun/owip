package controller.verify;

import controller.global.OpException;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.verify.VerifyGrowTime;
import domain.verify.VerifyGrowTimeExample;
import domain.verify.VerifyGrowTimeExample.Criteria;
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
import sys.tags.CmTag;
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
@RequestMapping("/verify")
public class VerifyGrowTimeController extends VerifyBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("verifyGrowTime:list")
    @RequestMapping("/verifyGrowTime")
    public String verifyGrowTime(Integer cadreId,
                                      ModelMap modelMap) {

        if (null != cadreId) {
            CadreView cadre = CmTag.getCadreById(cadreId);
            modelMap.put("cadre", cadre);
        }

        return "verify/verifyGrowTime/verifyGrowTime_page";
    }

    @RequiresPermissions("verifyGrowTime:list")
    @RequestMapping("/verifyGrowTime_data")
    @ResponseBody
    public void verifyGrowTime_data(HttpServletResponse response,
                                    Integer cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        VerifyGrowTimeExample example = new VerifyGrowTimeExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(VerifyConstants.VERIFY_STATUS_NORMAL);
        example.setOrderByClause("submit_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            verifyGrowTime_export(example, response);
            return;
        }

        long count = verifyGrowTimeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<VerifyGrowTime> records= verifyGrowTimeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(verifyGrowTime.class, verifyGrowTimeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("verifyGrowTime:edit")
    @RequestMapping(value = "/verifyGrowTime_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_verifyGrowTime_au(VerifyGrowTime record, int userId, HttpServletRequest request) {

        Cadre cadre = cadreService.getByUserId(userId);
        if(cadre==null){
            cadre = cadreService.addTempCadre(userId);
        }
        record.setCadreId(cadre.getId());

        if (verifyGrowTimeService.idDuplicate(record.getCadreId())){
            throw new OpException("添加重复！");
        }
        verifyGrowTimeService.insertSelective(record);
        logger.info(log( LogConstants.LOG_ADMIN, "添加入党时间认定：{0}", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("verifyGrowTime:edit")
    @RequestMapping("/verifyGrowTime_au")
    public String verifyGrowTime_au() {

        return "verify/verifyGrowTime/verifyGrowTime_au";
    }

    @RequiresPermissions("verifyGrowTime:edit")
    @RequestMapping(value = "/verifyGrowTime_verify", method = RequestMethod.POST)
    @ResponseBody
    public Map do_verifyGrowTime_verify(VerifyGrowTime record,
                                   HttpServletRequest request) {

        verifyGrowTimeService.update(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "入党时间认定：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("verifyGrowTime:edit")
    @RequestMapping("/verifyGrowTime_verify")
    public String verifyGrowTime_verify(Integer id, ModelMap modelMap) {

        if (id != null) {
            VerifyGrowTime verifyTime = verifyGrowTimeMapper.selectByPrimaryKey(id);
            modelMap.put("verifyTime", verifyTime);
        }
        return "verify/verifyGrowTime/verifyGrowTime_verify";
    }

    @RequiresPermissions("verifyGrowTime:del")
    @RequestMapping(value = "/verifyGrowTime_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_verifyGrowTime_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            verifyGrowTimeService.del(id);
            logger.info(log( LogConstants.LOG_ADMIN, "删除入党时间认定：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("verifyGrowTime:del")
    @RequestMapping(value = "/verifyGrowTime_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map verifyGrowTime_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            verifyGrowTimeService.batchDel(ids);
            logger.info(log( LogConstants.LOG_ADMIN, "批量删除入党时间认定：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("verifyGrowTime:list")
    @RequestMapping("/verifyGrowTimeLog")
    public String verifyGrowTime_log(int id, ModelMap modelMap) {

        VerifyGrowTime verifyGrowTime = verifyGrowTimeMapper.selectByPrimaryKey(id);
        int cadreId = verifyGrowTime.getCadreId();

        VerifyGrowTimeExample example = new VerifyGrowTimeExample();
        VerifyGrowTimeExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("submit_time desc");
        criteria.andCadreIdEqualTo(cadreId);

        List<VerifyGrowTime> records = verifyGrowTimeMapper.selectByExample(example);
        modelMap.put("records", records);

        return "verify/verifyGrowTime/verifyGrowTimeLog";
    }

    public void verifyGrowTime_export(VerifyGrowTimeExample example, HttpServletResponse response) {

        List<VerifyGrowTime> records = verifyGrowTimeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号|100","姓名|80","所在单位及职务|255","认定前入党时间|100","认定后入党时间|100",
                "入党志愿书形成时间|110","入党志愿书记载的入党时间|110","任免审批表形成时间|110","任免审批表记载的入党时间|110","备注|200"};

        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            VerifyGrowTime record = records.get(i);
            String[] values = {
                    record.getCadre().getCode(),
                    record.getCadre().getRealname(),
                    record.getCadre().getTitle(),
                    DateUtils.formatDate(record.getOldGrowTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getVerifyGrowTime(), DateUtils.YYYYMM),
                    DateUtils.formatDate(record.getMaterialTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getMaterialGrowTime(), DateUtils.YYYYMM),
                    DateUtils.formatDate(record.getAdTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getAdGrowTime(), DateUtils.YYYYMM),
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("入党时间认定(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
