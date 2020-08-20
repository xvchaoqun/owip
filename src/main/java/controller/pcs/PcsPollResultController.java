package controller.pcs;

import domain.pcs.PcsPoll;
import domain.pcs.PcsPollReport;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.pcs.common.PcsFinalResult;
import sys.constants.PcsConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public String pcsPollResult(Integer cls,
                                @RequestParam(required = false,
                                        defaultValue = PcsConstants.PCS_USER_TYPE_DW+"")Byte _reportType,
                                Integer pollId,
                                Integer userId,
                                ModelMap modelMap) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        Byte stage = pcsPoll.getStage();
        modelMap.put("stage", stage);

        if (stage != PcsConstants.PCS_POLL_THIRD_STAGE) {
            List<PcsPollReport> prReportList = pcsPollReportService.getReport(pcsPoll, PcsConstants.PCS_USER_TYPE_PR);
            modelMap.put("prCount", prReportList.size());
        }

        List<PcsPollReport> dwReportList = pcsPollReportService.getReport(pcsPoll, PcsConstants.PCS_USER_TYPE_DW);
        modelMap.put("dwCount", dwReportList.size());
        List<PcsPollReport> jwReportList = pcsPollReportService.getReport(pcsPoll, PcsConstants.PCS_USER_TYPE_JW);
        modelMap.put("jwCount", jwReportList.size());

        modelMap.put("pcsPoll", pcsPoll);


        //页面显示三类人选的人数
        Map<Byte, Integer> hasCountMap = new HashMap<>();
        int hasCount = 0;//党支部现有的总推荐人数
        for (Byte key : PcsConstants.PCS_USER_TYPE_MAP.keySet()){
            if (stage == PcsConstants.PCS_POLL_FIRST_STAGE) {
                hasCount = iPcsMapper.countResult(key, pollId, stage, null, null, null);
            }else{
                hasCount = iPcsMapper.countSecondResult(key, pollId, stage, null, null, null);
            }
            hasCountMap.put(key, hasCount);
        }

        modelMap.put("hasCountMap", hasCountMap);


        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        if (cls != null){
            modelMap.put("_reportType", _reportType);
            modelMap.put("cls", cls);
            return "pcs/pcsPoll/pcsPollReport/pcsPollReport_page";
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
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        /*if (export == 1) {

            List<PcsFinalResult> pcsFinalResults = iPcsMapper.selectResultList(pollId, new RowBounds());
            drExportService.exportOnlineResult(onlineId, drFinalResults, response);
            return;
        }*/
        int count = 0;
        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        stage = pcsPoll.getStage();
        if (stage != PcsConstants.PCS_POLL_FIRST_STAGE) {
            count = iPcsMapper.countSecondResult(type, pollId, stage,  userId, partyId, branchId);
        }else {
            count = iPcsMapper.countResult(type, pollId, stage, userId, partyId, branchId);
        }
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsFinalResult> pcsFinalResults = null;
        if (stage != PcsConstants.PCS_POLL_FIRST_STAGE) {
            pcsFinalResults = iPcsMapper.selectSecondResultList(type, pollId, stage, userId, partyId, branchId,
                    new RowBounds((pageNo - 1) * pageSize, pageSize));
        }else {
            pcsFinalResults = iPcsMapper.selectResultList(type, pollId, stage, userId, partyId, branchId,
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
}
