package controller.dr;

import bean.TempInspectorResult;
import bean.TempResult;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import domain.dr.DrOnlineInspector;
import domain.dr.DrOnlineResultView;
import domain.dr.DrOnlineResultViewExample;
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
import sys.constants.DrConstants;
import sys.constants.LogConstants;
import sys.helper.DrHelper;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

@Controller
@RequestMapping("/dr/drOnline")
public class DrOnlineResultController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("drOnlineResult:list")
    @RequestMapping("/drOnlineResult_menu")
    public String drOnlineResult_menu(Integer onlineId, ModelMap modelMap) {

        modelMap.put("onlineId", onlineId);

        return "dr/drOnlineResult/menu";
    }

    @RequiresPermissions("drOnlineResult:list")
    @RequestMapping("/drOnlineResult")
    public String drOnlineResult() {

        return "dr/drOnlineResult/drOnlineResult_page";
    }

    @RequiresPermissions("drOnlineResult:list")
    @RequestMapping("/drOnlineResult_data")
    @ResponseBody
    public void drOnlineResult_data(HttpServletResponse response,
                                    Integer onlineId,
                                    Integer postId,
                                    Integer candidateId,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    @RequestParam(required = false, value = "ids[]") Integer[] candidateIds, // 导出的记录
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DrOnlineResultViewExample example = new DrOnlineResultViewExample();
        DrOnlineResultViewExample.Criteria criteria = example.createCriteria();

        if (onlineId != null){
            criteria.andOnlineIdEqualTo(onlineId);
        }
        if (postId != null) {
            criteria.andPostIdEqualTo(postId);
        }
        if (candidateId != null) {
            criteria.andCandidateIdEqualTo(candidateId);
        }

        if (export == 1) {
            if (candidateIds != null && candidateIds.length > 0)
                criteria.andCandidateIdIn(Arrays.asList(candidateIds));
            drOnlineResult_export(example, response);
            return;
        }

        long count = drOnlineResultViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DrOnlineResultView> records = drOnlineResultViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(drOnlineResult.class, drOnlineResultMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    public void drOnlineResult_export(DrOnlineResultViewExample example, HttpServletResponse response) {

        List<DrOnlineResultView> records = drOnlineResultViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"批次id|100", "推荐职务id|100", "候选人id|100", "参评人id|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DrOnlineResultView record = records.get(i);
            String[] values = {
                    record.getOnlineId() + "",
                    record.getPostId() + "",
                    record.getCandidateId() + "",
                    String.valueOf(record.getOptionSum())
            };
            valuesList.add(values);
        }
        String fileName = String.format("线上民主推荐结果(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping(value = "/agree", method = RequestMethod.POST)
    @ResponseBody
    public Map agree(@RequestParam(defaultValue = "false") Boolean agree,
                     Byte isMobile,
                     HttpServletRequest request){

        DrOnlineInspector inspector = DrHelper.getDrInspector(request);

        TempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());

        tempResult.setAgree(agree);
        tempResult.setMobileAgree((isMobile != null && isMobile == 1) ? (agree ? agree : false) : false);

        DrOnlineInspector record = new DrOnlineInspector();

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", TempResult.class);

        StringWriter sw = new StringWriter();
        xStream.marshal(tempResult, new CompactWriter(sw));
        String tempData = sw.toString();

        record.setTempdata(tempData);
        record.setStatus(DrConstants.INSPECTOR_STATUS_SAVE);
        record.setIsMobile(false);

        drOnlineInspectorService.updateByExampleSelectiveBeforeSubmit(inspector.getId(), record);

        logger.info(addLog(LogConstants.LOG_DR, inspector.getUsername() + "已阅读说明"));
        return success(FormUtils.SUCCESS);
    }

    //保存/提交推荐数据
    @RequestMapping(value = "/doTempSave", method = RequestMethod.POST)
    @ResponseBody
    public Map tempSaveSurvey(@RequestParam(required = false, value = "datas[]") String[] datas,
                              @RequestParam(required = false, value = "others[]") String[] others,
                              Boolean isMoblie,
                              Integer inspectorId,
                              Integer isSubmit,
                              Integer onlineId, HttpServletRequest request) throws Exception {

        DrOnlineInspector inspector = DrHelper.getDrInspector(request);
        inspectorId = inspector.getId();

        if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_FINISH)//已经完成推荐
            return success(FormUtils.SUCCESS);

        //查看是否有临时数据
        TempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());
        TempInspectorResult inspectorResult = new TempInspectorResult();
        inspectorResult.setOnlineId(onlineId);
        inspectorResult.setOptionIdMap(new HashMap<String, Integer>());
        Map<String, TempInspectorResult> tempInspectorResultMap = new HashMap<>();
        tempInspectorResultMap.put(onlineId + "", inspectorResult);
        if (tempResult.getTempInspectorResultMap() == null || !tempResult.getTempInspectorResultMap().containsKey(onlineId + "")) {

            tempResult.setTempInspectorResultMap(tempInspectorResultMap);
        }

        //得到票数
        Integer postViewId = null;
        Integer userId = null;
        Integer option = null;
        Map<String, Integer> options = new HashMap<>();
        if (datas.length > 0) {
            for (String data : datas) {
                String[] results = StringUtils.split(data, "_");
                postViewId = Integer.valueOf(results[0]);
                userId = Integer.valueOf(results[1]);
                option = Integer.valueOf(results[2]);

                options.put(postViewId + "_" + userId, option);
            }
        }

        if (others != null && others.length > 0) {
            Map<Integer, String> otherResultMap = drOnlineResultService.consoleOthers(others);
            tempResult.setOtherResultMap(otherResultMap);
        }
        tempResult.tempInspectorResultMap.get(onlineId + "").setOptionIdMap(options);
        tempResult.setInspectorId(inspectorId);

        //格式转化
        DrOnlineInspector record = new DrOnlineInspector();
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", TempResult.class);

        StringWriter sw = new StringWriter();
        xStream.marshal(tempResult, new CompactWriter(sw));
        String tempData = sw.toString();

        record.setTempdata(tempData);
        record.setStatus(DrConstants.INSPECTOR_STATUS_SAVE);
        record.setIsMobile(false);

        drOnlineInspectorService.updateByExampleSelectiveBeforeSubmit(inspectorId, record);

        logger.info(String.format("%s保存id为%s的测评结果", inspector.getUsername(), onlineId));
        if (isSubmit == 1) {
            //提交推荐结果
            return drOnlineResultService.submitResult(isMoblie, inspectorId, request) ? success(FormUtils.SUCCESS) : failed(FormUtils.FAILED);
        }

        return success(FormUtils.SUCCESS);
    }
}
