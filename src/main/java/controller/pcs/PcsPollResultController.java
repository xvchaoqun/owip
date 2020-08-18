package controller.pcs;

import domain.pcs.PcsPoll;
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
import persistence.pcs.common.PcsFinalResult;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pcs")
public class PcsPollResultController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPollResult:list")
    @RequestMapping("/pcsPollResult_menu")
    public String pcsPollResult_menu() {

        return "pcs/pcsPoll/pcsPollResult/menu";
    }

    @RequiresPermissions("pcsPollResult:list")
    @RequestMapping("/pcsPollResult")
    public String pcsPollResult(Integer pollId,
                                Integer userId,
                                ModelMap modelMap) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        modelMap.put("pcsPoll", pcsPoll);
        Byte stage = pcsPoll.getStage();
        modelMap.put("stage", stage);

        //页面显示三类人选的人数
        List<Integer> pollIdList = new ArrayList<>();
        pollIdList.add(pollId);
        Map<Byte, Integer> candidateCountMap = new HashMap<>();
        Map<Byte, Integer> hasCountMap = new HashMap<>();
        int candidateCount = 0;//候选人数
        int hasCount = 0;//党支部现有的总推荐人数
        for (Byte key : PcsConstants.PCS_POLL_CANDIDATE_TYPE.keySet()){
            if (stage == PcsConstants.PCS_POLL_FIRST_STAGE) {
                candidateCount =  iPcsMapper.countResult(key, pollIdList, stage, true, null, null, null, null, null);
                hasCount = iPcsMapper.countResult(key, pollIdList, stage, null, null, null, null, null, null);
            }else{
                candidateCount = iPcsMapper.countSecondResult(key, pollIdList, stage, true, null, null, null, null, null);
                hasCount = iPcsMapper.countSecondResult(key, pollIdList, stage, null, null, null, null, null, null);
            }
            candidateCountMap.put(key, candidateCount);
            hasCountMap.put(key, hasCount);
        }

        modelMap.put("candidateCountMap", candidateCountMap);
        modelMap.put("hasCountMap", hasCountMap);


        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        return "pcs/pcsPoll/pcsPollResult/pcsPollResult_page";
    }

    @RequiresPermissions("pcsPollResult:list")
    @RequestMapping("/pcsPollResult_data")
    @ResponseBody
    public void pcsPollResult_data(HttpServletResponse response,
                                   Integer pollId,
                                   Integer partyId,
                                   Integer branchId,
                                   Integer userId,
                                   Byte stage,
                                   Byte type,
                                   Boolean isCandidate,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        List<Integer> pollIdList = new ArrayList<>();
        List<Integer> partyIdList = new ArrayList<>();
        List<Integer> branchIdList = new ArrayList<>();

        if (pollId != null) {
            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
            stage = pcsPoll.getStage();
            pollIdList.add(pollId);
        }else {
            pollIdList = pcsPollService.getCurrentPcsPollId();
        }

        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)){
            partyIdList = loginUserService.adminPartyIdList();
            branchIdList = loginUserService.adminBranchIdList();
        }

        /*if (export == 1) {

            List<PcsFinalResult> pcsFinalResults = iPcsMapper.selectResultList(pollId, new RowBounds());
            drExportService.exportOnlineResult(onlineId, drFinalResults, response);
            return;
        }*/
        int count = 0;
        if (stage != PcsConstants.PCS_POLL_FIRST_STAGE) {
            count = iPcsMapper.countSecondResult(type, pollIdList, stage, isCandidate,  userId, partyId, branchId, partyIdList, branchIdList);
        }else {
            count = iPcsMapper.countResult(type, pollIdList, stage, isCandidate, userId, partyId, branchId, partyIdList, branchIdList);
        }
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsFinalResult> pcsFinalResults = null;
        if (stage != PcsConstants.PCS_POLL_FIRST_STAGE) {
            pcsFinalResults = iPcsMapper.selectSecondResultList(type, pollIdList, stage, isCandidate, userId, partyId, branchId, partyIdList, branchIdList,
                    new RowBounds((pageNo - 1) * pageSize, pageSize));
        }else {
            pcsFinalResults = iPcsMapper.selectResultList(type, pollIdList, stage, isCandidate, userId, partyId, branchId, partyIdList, branchIdList,
                    new RowBounds((pageNo - 1) * pageSize, pageSize));
        }
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", pcsFinalResults);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(drOnlineResult.class, drOnlineResultMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping(value = "/pcsPollResult_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPollResult_cancel(HttpServletRequest request,
                                       Integer[] ids,//对应查出来结果的userId
                                       Boolean isCandidate,
                                       Integer pollId,
                                       Byte type) {

        if (null != ids && ids.length>0){
            pcsPollResultService.batchCancel(ids, isCandidate, pollId, type);
            logger.info(log( LogConstants.LOG_PCS, "批量{1}：{0}", StringUtils.join(ids, ","),
                    isCandidate ? "设置候选人":"取消候选人资格"));
        }

        return success(FormUtils.SUCCESS);
    }
}
