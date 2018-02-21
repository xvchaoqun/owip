package controller.sc.scDispatch;

import domain.sc.scCommittee.ScCommitteeVoteView;
import domain.sc.scCommittee.ScCommitteeVoteViewExample;
import domain.sc.scDispatch.ScDispatch;
import domain.sc.scDispatch.ScDispatchView;
import domain.sc.scDispatch.ScDispatchViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import service.sc.scCommittee.ScCommitteeService;
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
@RequestMapping("/sc")
public class ScDispatchController extends ScDispatchBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scDispatch:list")
    @RequestMapping("/scDispatch")
    public String scDispatch(@RequestParam(defaultValue = "1") Integer cls,
                             Integer dispatchTypeId,
                             ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("dispatchTypeId", dispatchTypeId);

        return "sc/scDispatch/scDispatch/scDispatch_page";
    }
    @RequiresPermissions("scDispatch:list")
    @RequestMapping("/scDispatch_popup")
    public String scDispatch_popup(Integer dispatchTypeId,
                             ModelMap modelMap) {

        modelMap.put("dispatchTypeId", dispatchTypeId);

        return "sc/scDispatch/scDispatch/scDispatch_popup";
    }

    @RequiresPermissions("scDispatch:list")
    @RequestMapping("/scDispatch_data")
    public void scDispatch_data(HttpServletResponse response,
                                    Integer year,
                                    Integer dispatchTypeId,
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

        ScDispatchViewExample example = new ScDispatchViewExample();
        ScDispatchViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (dispatchTypeId!=null) {
            criteria.andDispatchTypeIdEqualTo(dispatchTypeId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scDispatch_export(example, response);
            return;
        }

        long count = scDispatchViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScDispatchView> records= scDispatchViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scDispatch.class, scDispatchMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scDispatch:edit")
    @RequestMapping(value = "/scDispatch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scDispatch_au(ScDispatch record,
                                MultipartFile _wordFilePath,
                                MultipartFile _pdfFilePath,
                                @RequestParam(required=false, value = "committeeIds[]") Integer[] committeeIds,
                                @RequestParam(required=false, value = "voteIds[]") Integer[] voteIds,
                                HttpServletRequest request) throws IOException, InterruptedException {

        record.setFilePath(upload(_wordFilePath, "scDispatch-word"));
        record.setSignFilePath(uploadPdf(_pdfFilePath, "scDispatch-sign"));

        Integer id = record.getId();

        if (record.getYear()!=null && record.getCode()!=null &&
                scDispatchService.idDuplicate(id, record.getYear(), record.getCode())) {
            return failed("添加重复");
        }
        if (id == null) {
            scDispatchService.insertSelective(record, committeeIds, voteIds);
            logger.info(addLog( SystemConstants.LOG_SC_DISPATCH, "添加文件起草签发：%s", record.getId()));
        } else {

            scDispatchService.updateByPrimaryKeySelective(record, committeeIds, voteIds);
            logger.info(addLog( SystemConstants.LOG_SC_DISPATCH, "更新文件起草签发：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scDispatch:edit")
    @RequestMapping("/scDispatch_users")
    public String scDispatch_users(@RequestParam(required=false, value = "committeeIds[]") Integer[] committeeIds, ModelMap modelMap) {

        if(committeeIds!=null && committeeIds.length>0) {
            /*ScCommitteeVoteViewExample example = new ScCommitteeVoteViewExample();
            example.createCriteria().andCommitteeIdIn(Arrays.asList(committeeIds));
            example.setOrderByClause("type asc, sort_order desc");
            List<ScCommitteeVoteView> scCommitteeVoteViews = scCommitteeVoteViewMapper.selectByExample(example);*/
            modelMap.put("scCommitteeVotes", iScMapper.getScDispatchUsers(StringUtils.join(committeeIds, ",")));
        }

        return "sc/scDispatch/scDispatch/scDispatch_users";
    }

    @RequiresPermissions("scDispatch:edit")
    @RequestMapping(value = "/scDispatch_selectUser", method = RequestMethod.POST)
    public void do_scDispatch_selectUser(@RequestParam(required=false, value = "voteIds[]") Integer[] voteIds,
                                       HttpServletResponse response) throws IOException {

        List<ScCommitteeVoteView> votes = new ArrayList<>();
        if(voteIds!=null && voteIds.length>0){

            ScCommitteeVoteViewExample example = new ScCommitteeVoteViewExample();
            example.createCriteria().andIdIn(Arrays.asList(voteIds));
            example.setOrderByClause("type asc, sort_order desc");
            votes = scCommitteeVoteViewMapper.selectByExample(example);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("votes", votes);
        JSONUtils.write(response, resultMap);
    }
    // 添加发文-从文件签发稿中选择
    @RequiresPermissions("scDispatch:edit")
    @RequestMapping(value = "/scDispatch_select", method = RequestMethod.POST)
    public void do_scDispatch_select(int id, HttpServletResponse response) throws IOException {

        ScDispatchView scDispatchView = scDispatchService.get(id);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("scDispatch", scDispatchView);
        JSONUtils.write(response, resultMap);
    }

    // 同步干部任免信息-从文件签发稿中同步
    @RequiresPermissions("scDispatch:edit")
    @RequestMapping(value = "/scDispatch_snyc", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scDispatch_snyc(int dispatchId, HttpServletResponse response) throws IOException {

        try {

            scDispatchService.sync(dispatchId);

        } catch (Exception e) {
          return failed("同步失败");
        }

        return success();
    }

    @RequiresPermissions("scDispatch:edit")
    @RequestMapping("/scDispatch_au")
    public String scDispatch_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScDispatch scDispatch = scDispatchMapper.selectByPrimaryKey(id);
            modelMap.put("scDispatch", scDispatch);

            modelMap.put("votes", iScMapper.getScDispatchVotes(id));

            modelMap.put("itemList", iScMapper.getScDispatchCommittees(id));
        }

        ScCommitteeService scCommitteeService = CmTag.getBean(ScCommitteeService.class);
        modelMap.put("scCommittees", scCommitteeService.findAll());

        return "sc/scDispatch/scDispatch/scDispatch_au";
    }

    @RequiresPermissions("scDispatch:del")
    @RequestMapping(value = "/scDispatch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scDispatch_batchDel(HttpServletRequest request, @RequestParam(required=false, value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scDispatchService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC_DISPATCH, "批量删除文件起草签发：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scDispatch_export(ScDispatchViewExample example, HttpServletResponse response) {

        List<ScDispatchView> records = scDispatchViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年份|100","发文类型|100","发文号|100","党委常委会日期|100","起草日期|100","文件签发稿|100","签发单|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScDispatchView record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getDispatchTypeId()+"",
                            record.getCode()+"",
                            DateUtils.formatDate(record.getMeetingTime(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getPubTime(), DateUtils.YYYY_MM_DD),
                            record.getFilePath(),
                            record.getSignFilePath(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "文件起草签发_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("scDispatch:export")
    @RequestMapping("/scDispatch_exportSign")
    public String scDispatch_exportSign(int dispatchId, HttpServletResponse response) throws IOException {

        ScDispatch scDispatch = scDispatchMapper.selectByPrimaryKey(dispatchId);
        String dispatchCode = CmTag.getDispatchCode(scDispatch.getCode(), scDispatch.getDispatchTypeId(), scDispatch.getYear());
        String fileName = dispatchCode + "签发单";

        XSSFWorkbook wb = scDispatchService.exportSign(dispatchId);
        ExportHelper.output(wb, fileName + ".xlsx", response);
        return null;
    }
}
