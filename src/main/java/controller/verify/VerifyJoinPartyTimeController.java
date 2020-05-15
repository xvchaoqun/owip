package controller.verify;

import domain.cadre.CadreView;
import domain.verify.VerifyJoinPartyTime;
import domain.verify.VerifyJoinPartyTimeExample;
import domain.verify.VerifyJoinPartyTimeExample.Criteria;
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
public class VerifyJoinPartyTimeController extends VerifyBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("verifyJoinPartyTime:list")
    @RequestMapping("/verifyJoinPartyTime")
    public String verifyJoinPartyTime(Integer cadreId,
                                      ModelMap modelMap) {

        if (null != cadreId) {
            CadreView cadre = CmTag.getCadreById(cadreId);
            modelMap.put("cadre", cadre);
        }

        return "verify/verifyJoinPartyTime/verifyJoinPartyTime_page";
    }

    @RequiresPermissions("verifyJoinPartyTime:list")
    @RequestMapping("/verifyJoinPartyTime_data")
    @ResponseBody
    public void verifyJoinPartyTime_data(HttpServletResponse response,
                                    Integer cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        VerifyJoinPartyTimeExample example = new VerifyJoinPartyTimeExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(VerifyConstants.VERIFY_STATUS_NORMAL);
        example.setOrderByClause("submit_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            verifyJoinPartyTime_export(example, response);
            return;
        }

        long count = verifyJoinPartyTimeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<VerifyJoinPartyTime> records= verifyJoinPartyTimeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(verifyJoinPartyTime.class, verifyJoinPartyTimeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("verifyJoinPartyTime:edit")
    @RequestMapping(value = "/verifyJoinPartyTime_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_verifyJoinPartyTime_au(VerifyJoinPartyTime record, HttpServletRequest request) {


        Integer cadreId = record.getCadreId();
        if (null != cadreId && verifyJoinPartyTimeService.idDuplicate(cadreId)){
            return failed("添加重复");
        }

        Integer id = record.getId();

        if (id == null) {
            verifyJoinPartyTimeService.insertSelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "添加入党时间认定：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("verifyJoinPartyTime:edit")
    @RequestMapping("/verifyJoinPartyTime_au")
    public String verifyJoinPartyTime_au() {

        return "verify/verifyJoinPartyTime/verifyJoinPartyTime_au";
    }

    @RequiresPermissions("verifyJoinPartyTime:edit")
    @RequestMapping(value = "/verifyJoinPartyTime_verify", method = RequestMethod.POST)
    @ResponseBody
    public Map do_verifyJoinPartyTime_verify(VerifyJoinPartyTime record,
                                   String _materialTime,
                                   String _materialJoinTime,
                                   String _adTime,
                                   String _adJoinTime,
                                   String _oldJoinTime,
                                   String _verifyJoinTime,
                                   HttpServletRequest request) {

        record.setMaterialTime(DateUtils.parseDate(_materialTime, DateUtils.YYYYMMDD_DOT));
        record.setMaterialJoinTime(DateUtils.parseDate(_materialJoinTime, DateUtils.YYYYMM));
        record.setAdTime(DateUtils.parseDate(_adTime, DateUtils.YYYYMMDD_DOT));
        record.setAdJoinTime(DateUtils.parseDate(_adJoinTime, DateUtils.YYYYMMDD_DOT));
        record.setOldJoinTime(DateUtils.parseDate(_oldJoinTime, DateUtils.YYYYMMDD_DOT));
        record.setVerifyJoinTime(DateUtils.parseDate(_verifyJoinTime, DateUtils.YYYYMM));

        verifyJoinPartyTimeService.update(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "入党时间认定：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("verifyJoinPartyTime:edit")
    @RequestMapping("/verifyJoinPartyTime_verify")
    public String verifyJoinPartyTime_verify(Integer id, ModelMap modelMap) {

        if (id != null) {
            VerifyJoinPartyTime verifyTime = verifyJoinPartyTimeMapper.selectByPrimaryKey(id);
            modelMap.put("verifyTime", verifyTime);
        }
        return "verify/verifyJoinPartyTime/verifyJoinPartyTime_verify";
    }

    @RequiresPermissions("verifyJoinPartyTime:del")
    @RequestMapping(value = "/verifyJoinPartyTime_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_verifyJoinPartyTime_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            verifyJoinPartyTimeService.del(id);
            logger.info(log( LogConstants.LOG_ADMIN, "删除入党时间认定：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("verifyJoinPartyTime:del")
    @RequestMapping(value = "/verifyJoinPartyTime_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map verifyJoinPartyTime_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            verifyJoinPartyTimeService.batchDel(ids);
            logger.info(log( LogConstants.LOG_ADMIN, "批量删除入党时间认定：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("verifyJoinPartyTime:list")
    @RequestMapping("/verifyJoinPartyTimeLog")
    public String verifyJoinPartyTime_log(int id, ModelMap modelMap) {

        VerifyJoinPartyTime verifyJoinPartyTime = verifyJoinPartyTimeMapper.selectByPrimaryKey(id);
        int cadreId = verifyJoinPartyTime.getCadreId();

        VerifyJoinPartyTimeExample example = new VerifyJoinPartyTimeExample();
        VerifyJoinPartyTimeExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("submit_time desc");
        criteria.andCadreIdEqualTo(cadreId);

        List<VerifyJoinPartyTime> records = verifyJoinPartyTimeMapper.selectByExample(example);
        modelMap.put("records", records);

        return "verify/verifyJoinPartyTime/verifyJoinPartyTimeLog";
    }

    public void verifyJoinPartyTime_export(VerifyJoinPartyTimeExample example, HttpServletResponse response) {

        List<VerifyJoinPartyTime> records = verifyJoinPartyTimeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号|100","姓名|80","所在单位及职务|255","认定前入党时间|100","认定后入党时间|100",
                "入党志愿书形成时间|110","入党志愿书记载的入党时间|110","任免审批表形成时间|110","任免审批表记载的入党时间|110","备注|200"};

        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            VerifyJoinPartyTime record = records.get(i);
            String[] values = {
                    record.getCadre().getCode(),
                    record.getCadre().getRealname(),
                    record.getCadre().getTitle(),
                    DateUtils.formatDate(record.getOldJoinTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getVerifyJoinTime(), DateUtils.YYYYMM),
                    DateUtils.formatDate(record.getMaterialTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getMaterialJoinTime(), DateUtils.YYYYMM),
                    DateUtils.formatDate(record.getAdTime(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getAdJoinTime(), DateUtils.YYYYMMDD_DOT),
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("入党时间认定(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/verifyJoinPartyTime_selects")
    @ResponseBody
    public Map verifyJoinPartyTime_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        VerifyJoinPartyTimeExample example = new VerifyJoinPartyTimeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            //criteria.and(SqlUtils.like(searchStr));
        }

        long count = verifyJoinPartyTimeMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<VerifyJoinPartyTime> records = verifyJoinPartyTimeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(VerifyJoinPartyTime record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getCadreId());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
