package controller.pcs;

import domain.pcs.PcsPoll;
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
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

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
    @RequestMapping("/pcsPollResult")
    public String pcsPollResult(Integer pollId,
                                Integer userId,
                                ModelMap modelMap) {

        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        modelMap.put("isSecond", pcsPoll.getIsSecond());
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
                                 Boolean isSecond,
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
            isSecond = pcsPoll.getIsSecond();
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
        if (isSecond) {
            count = iPcsMapper.countSecondResult(userId, partyId, branchId, pollIdList, partyIdList, branchIdList);
        }else {
            count = iPcsMapper.countResult(userId, partyId, branchId, pollIdList, partyIdList, branchIdList);
        }
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsFinalResult> pcsFinalResults = null;
        if (isSecond) {
            pcsFinalResults = iPcsMapper.selectSecondResultList(userId, partyId, branchId, pollIdList, partyIdList, branchIdList,
                    new RowBounds((pageNo - 1) * pageSize, pageSize));
        }else {
            pcsFinalResults = iPcsMapper.selectResultList(userId, partyId, branchId, pollIdList, partyIdList, branchIdList,
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
