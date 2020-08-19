package controller.pcs.vote;

import controller.pcs.PcsBaseController;
import domain.pcs.PcsConfig;
import domain.pcs.PcsVoteGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.PcsConstants;
import sys.utils.FormUtils;

import java.util.Map;

@Controller
@RequestMapping("/pcs")
public class PcsVoteStatController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsVoteGroup:list")
    @RequestMapping("/pcsVoteStat_group_page")
    public String pcsVoteStat_group_page(byte type, ModelMap modelMap) {

        modelMap.put("type", type);
        return "pcs/pcsVoteStat/pcsVoteStat_group_page";
    }

    @RequiresPermissions("pcsVoteStat:list")
    @RequestMapping("/pcsVoteStat_group_stat_page")
    public String pcsVoteStat_group_stat_page() {

        return "pcs/pcsVoteStat/pcsVoteStat_group_stat_page";
    }

    @RequiresPermissions("pcsVoteStat:*")
    @RequestMapping("/pcsVoteStat")
    public String pcsVoteStat(@RequestParam(required = false, defaultValue = "1") byte cls,
                              ModelMap modelMap) {

        modelMap.put("cls", cls);

        if (cls == 2 || cls == 3) {

            // 党委委员选举统计、 纪委委员选举统计
            return "forward:/pcs/pcsVoteStat_group_page";
        } else if (cls == 4) {

            // 两委当选名单
            return "forward:/pcs/pcsVoteMemberList";
        }

        PcsVoteGroup dwPcsVoteGroup = iPcsMapper.statPcsVoteGroup(PcsConstants.PCS_USER_TYPE_DW);
        modelMap.put("dwPcsVoteGroup", dwPcsVoteGroup);
        PcsVoteGroup jwPcsVoteGroup = iPcsMapper.statPcsVoteGroup(PcsConstants.PCS_USER_TYPE_JW);
        modelMap.put("jwPcsVoteGroup", jwPcsVoteGroup);
        // 分发和回收选票情况
        return "pcs/pcsVoteStat/pcsVoteStat";
    }

    @RequiresPermissions("pcsVoteStat:edit")
    @RequestMapping(value = "/pcsVoteStat_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsVoteStat_au(PcsConfig record) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        record.setId(currentPcsConfig.getId());

        pcsConfigMapper.updateByPrimaryKeySelective(record);
        return success(FormUtils.SUCCESS);
    }

    // 清空所有的两委选举数据
    @RequiresPermissions("pcsVoteStat:clear")
    @RequestMapping(value = "/pcsVoteStat_clear", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsVoteStat_clear(PcsConfig record) {

        try {
            pcsVoteService.clear();
        } catch (InterruptedException e) {
            logger.error("异常", e);
            return failed("操作失败：" + e.getMessage());
        }
        logger.info("清空两委选举数据");
        return success(FormUtils.SUCCESS);
    }
}
