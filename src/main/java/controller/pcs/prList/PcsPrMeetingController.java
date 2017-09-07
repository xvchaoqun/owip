package controller.pcs.prList;

import controller.BaseController;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPrCandidateView;
import domain.pcs.PcsPrCandidateViewExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.common.bean.PcsPrPartyBean;
import shiro.ShiroHelper;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PcsPrMeetingController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 各分党委党员大会
    @RequiresPermissions("pcsPrMeeting:list")
    @RequestMapping("/pcsPrMeeting_party")
    public String pcsPrMeeting_party_page(byte stage, ModelMap modelMap) {

        return "pcs/pcsPrMeeting/pcsPrMeeting_party_page";
    }

    @RequiresPermissions("pcsPrOw:list")
    @RequestMapping("/pcsPrMeeting_party_data")
    public void pcsPrMeeting_party_data(HttpServletResponse response,
                                   byte stage,
                                   Integer partyId,
                                   Boolean hasReport,
                                   Byte recommendStatus,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        int count = iPcsMapper.countPcsPrPartyBeans(configId, stage, partyId, hasReport, recommendStatus);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsPrPartyBean> records = iPcsMapper.selectPcsPrPartyBeans(configId, stage, partyId, hasReport, recommendStatus,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsPrMeeting:list")
    @RequestMapping("/pcsPrMeeting")
    public String pcsPrMeeting(@RequestParam(required = false, defaultValue = "1") byte cls,
                             Integer userId,
                             ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            // 全校党代表数据统计
            return "forward:/pcsPrMeeting_table_page";
        } else if (cls == 3) {
            // 全校党员参与情况
            return "forward:/pcsPrMeeting_party_table_page";
        }
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        // 全校党代表名单
        return "pcs/pcsPrMeeting/pcsPrMeeting_candidate_page";
    }

    // 全校党代表数据统计
    @RequiresPermissions("pcsPrMeeting:list")
    @RequestMapping("/pcsPrMeeting_table_page")
    public String pcsPrMeeting_candidate_table_page(byte stage, ModelMap modelMap) {

        return "pcs/pcsPrMeeting/pcsPrMeeting_table_page";
    }

    // 全校党员参与情况
    @RequiresPermissions("pcsPrMeeting:list")
    @RequestMapping("/pcsPrMeeting_party_table_page")
    public String pcsPrMeeting_party_table_page(byte stage, ModelMap modelMap) {

        return "pcs/pcsPrMeeting/pcsPrMeeting_party_table_page";
    }

    // 全校党代表名单
    @RequiresPermissions("pcsPrMeeting:list")
    @RequestMapping("/pcsPrMeeting_candidate_page")
    public String pcsPrMeeting_candidate_page(byte stage, ModelMap modelMap) {


        return "pcs/pcsPrMeeting/pcsPrMeeting_candidate_page";
    }

    @RequiresPermissions("pcsPrMeeting:list")
    @RequestMapping("/pcsPrMeeting_candidate_data")
    public void pcsPrMeeting_candidate_data(HttpServletResponse response,
                                              byte stage,
                                              Integer userId,
                                              Integer pageSize, Integer pageNo) throws IOException {


        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        PcsPrCandidateViewExample example = pcsPrCandidateService.createExample(configId, stage, partyId, userId);

        long count = pcsPrCandidateViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsPrCandidateView> records = pcsPrCandidateViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
