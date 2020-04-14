package controller.dr;

import bean.DrTempResult;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import controller.global.OpException;
import domain.dr.DrOnline;
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
import sys.helper.DrHelper;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dr/drOnline")
public class DrOnlineResultController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("drOnlineResult:list")
    @RequestMapping("/drOnlineResult")
    public String drOnlineResult(Integer onlineId,
                                 @RequestParam(required = false, value = "typeIds[]") String[] typeIds,
                                 ModelMap modelMap) {

        modelMap.put("onlineId", onlineId);
        DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(onlineId);
        modelMap.put("drOnline", drOnline);
        modelMap.put("typeIds", typeIds);

        return "dr/drOnlineResult/drOnlineResult_page";
    }

    @RequiresPermissions("drOnlineResult:list")
    @RequestMapping("/drOnlineResult_data")
    @ResponseBody
    public void drOnlineResult_data(HttpServletResponse response,
                                    Integer onlineId,
                                    String a,
                                    @RequestParam(required = false, value = "typeIds[]") String[] typeIds,
                                    Integer postId,
                                    Integer candidateId,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    @RequestParam(required = false, value = "ids[]") Integer[] candidateIds, // 导出的记录
                                    Integer pageSize, Integer pageNo, ModelMap modelMap) throws IOException {

        if (null != typeIds && typeIds.length > 0) {
            List<Integer> list = new ArrayList<>();
            for (String str : typeIds) {
                list.add(Integer.valueOf(str));
            }
            modelMap.put("typeIds", list);
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        if (null != typeIds && typeIds.length > 0) {
            List<Integer> _typesIds = new ArrayList<>();
            for (String typeId : typeIds) {
                _typesIds.add(Integer.valueOf(typeId));
            }
            List<DrOnlineResultView> resultViews = iDrMapper.selectInspectorFilter(_typesIds, onlineId);
            int count = resultViews.size();
            CommonList commonList = new CommonList(count, pageNo, pageSize);

            Map resultMap = new HashMap();
            resultMap.put("rows", resultViews);
            resultMap.put("records", count);
            resultMap.put("page", pageNo);
            resultMap.put("total", commonList.pageNum);

            Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
            //baseMixins.put(drOnlineResult.class, drOnlineResultMixin.class);
            JSONUtils.jsonp(resultMap, baseMixins);
            return;
            //System.out.println(resultViews);
        }

        DrOnlineResultViewExample example = new DrOnlineResultViewExample();
        DrOnlineResultViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(" post_id desc");

        if (onlineId != null){
            criteria.andOnlineIdEqualTo(onlineId);
        }
        if (postId != null) {
            criteria.andPostIdEqualTo(postId);
        }
        if (candidateId != null) {
            criteria.andCandidateIdEqualTo(candidateId);
        }
/*
        if (export == 1) {
            if (candidateIds != null && candidateIds.length > 0)
                criteria.andCandidateIdIn(Arrays.asList(candidateIds));
            drOnlineResult_export(example, response);
            return;
        }*/

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

    @RequiresPermissions("drOnlineResult:list")
    @RequestMapping("/drOnlineResult_export")
    public void drOnlineResult_export(Integer onlineId,
                                      @RequestParam(required = false, value = "ids[]") Integer[] candidateIds,
                                        HttpServletResponse response) throws IOException {

        DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(onlineId);
        Byte status = drOnline.getStatus();
        if (status == DrConstants.DR_ONLINE_FINISH ) {
                drCommonService.exportOnlineResult(onlineId, response);
        }else
            throw new OpException("该批次线上民主推荐还未完成，不能导出结果！");
    }

    @RequiresPermissions("drOnlineResult:list")
    @RequestMapping("/drOnlineResult_filter")
    public String drOnlineResult_filter(Integer onlineId,
                                      ModelMap modelMap,
                                      HttpServletResponse response) throws IOException {

        modelMap.put("onlineId", onlineId);

        return "/dr/drOnlineResult/drOnlineResult_filter";
    }

    @RequestMapping(value = "/agree", method = RequestMethod.POST)
    @ResponseBody
    public Map agree(@RequestParam(defaultValue = "false") Boolean agree,
                     Byte isMobile,
                     HttpServletRequest request){

        DrOnlineInspector _inspector = DrHelper.getDrInspector(request);

        DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(_inspector.getId());

        DrTempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());

        tempResult.setInspectorId(inspector.getId());
        tempResult.setAgree(agree);
        tempResult.setMobileAgree((isMobile != null && isMobile == 1) ? agree : false);

        DrOnlineInspector record = new DrOnlineInspector();

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", DrTempResult.class);

        StringWriter sw = new StringWriter();
        xStream.marshal(tempResult, new CompactWriter(sw));
        String tempData = sw.toString();

        record.setTempdata(tempData);
        record.setStatus(DrConstants.INSPECTOR_STATUS_SAVE);
        record.setIsMobile(false);

        drOnlineInspectorService.updateByExampleSelectiveBeforeSubmit(inspector.getId(), record);

        logger.info(String.format("%s已阅读说明", inspector.getUsername()));
        return success(FormUtils.SUCCESS);
    }

    //处理-保存/提交推荐数据
    @RequestMapping(value = "/doTempSave", method = RequestMethod.POST)
    @ResponseBody
    public Map tempSaveSurvey(@RequestParam(required = false, value = "datas[]") String[] datas,
                              @RequestParam(required = false, value = "others[]") String[] others,
                              Boolean isMoblie,
                              Integer inspectorId,
                              Integer isSubmit,
                              Integer onlineId, HttpServletRequest request) throws Exception {

        DrOnlineInspector _inspector = DrHelper.getDrInspector(request);
        DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(_inspector.getId());
        inspectorId = inspector.getId();

        if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_FINISH)//已经完成推荐
            return success(FormUtils.SUCCESS);

        //查看是否有临时数据
        DrTempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());

        //得到票数
        Integer postId = null;
        Integer userId = null;
        Integer option = null;
        Map<String, Integer> optionMap = new HashMap<>();
        if (datas != null && datas.length > 0) {
            for (String data : datas) {
                String[] results = StringUtils.split(data, "_");
                postId = Integer.valueOf(results[0]);
                userId = Integer.valueOf(results[1]);
                option = Integer.valueOf(results[2]);

                optionMap.put(postId + "_" + userId, option);
            }
            tempResult.setRawOptionMap(optionMap);
        }

        if (others != null && others.length > 0) {
            Map<Integer, String> otherResultMap = drOnlineResultService.consoleOthers(others, datas);
            tempResult.setOtherResultMap(otherResultMap);
        }else{
            if(null != tempResult.getOtherResultMap() && tempResult.getOtherResultMap().size() > 0)
                tempResult.getOtherResultMap().clear();
        }

        //格式转化
        DrOnlineInspector record = new DrOnlineInspector();
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", DrTempResult.class);

        StringWriter sw = new StringWriter();
        xStream.marshal(tempResult, new CompactWriter(sw));
        String tempData = sw.toString();

        record.setTempdata(tempData);
        record.setStatus(DrConstants.INSPECTOR_STATUS_SAVE);
        record.setIsMobile(false);

        drOnlineInspectorService.updateByExampleSelectiveBeforeSubmit(inspectorId, record);

        logger.info(String.format("%s保存批次为%s的测评结果", inspector.getUsername(), inspector.getDrOnline().getCode()));
        if (isSubmit == 1) {
            //提交推荐结果
            return drOnlineResultService.submitResult(isMoblie, inspectorId, request) ? success(FormUtils.SUCCESS) : failed(FormUtils.FAILED);
        }

        return success(FormUtils.SUCCESS);
    }
}
