package controller.pcs;

import domain.party.Branch;
import domain.party.Party;
import domain.pcs.PcsBranch;
import domain.pcs.PcsBranchExample;
import domain.pcs.PcsBranchExample.Criteria;
import domain.pcs.PcsConfig;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.helper.PartyHelper;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pcs")
public class PcsBranchController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPartyList:list")
    @RequestMapping("/pcsBranch")
    public String pcsBranch(@RequestParam(required = false, defaultValue = "2") byte cls,Integer partyId,
                            Integer branchId,ModelMap modelMap) {
        modelMap.put("cls",cls);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null)
            modelMap.put("party", partyMap.get(partyId));
        if (branchId != null)
            modelMap.put("branch", branchMap.get(branchId));

        return "pcs/pcsBranch/pcsBranch_page";
    }

    @RequiresPermissions("pcsPartyList:list")
    @RequestMapping("/pcsBranch_data")
    @ResponseBody
    public void pcsBranch_data(HttpServletResponse response,
                                    Integer partyId,
                                    Integer branchId,
                                 Boolean isDeleted,
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

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        PcsBranchExample example = new PcsBranchExample();
        Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId);
        example.setOrderByClause("party_sort_order desc, sort_order desc");
        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (isDeleted!=null) {
            criteria.andIsDeletedEqualTo(isDeleted);
        }

        long count = pcsBranchMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsBranch> records= pcsBranchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsBranch.class, pcsBranchMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsPartyList:edit")
    @RequestMapping(value = "/pcsBranch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsBranch_au(PcsBranch record, HttpServletRequest request) {
        PcsBranch pcsBranch  =pcsBranchMapper.selectByPrimaryKey(record.getId());
        Integer partyId = pcsBranch.getPartyId();
        Integer branchId = pcsBranch.getBranchId();
        if (!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(), partyId, branchId)) {
            throw new UnauthorizedException();
        }

        pcsBranchService.updateByPrimaryKeySelective(record);
        logger.info(log( LogConstants.LOG_PCS, "更新召开党代会的支部：{0}", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPartyList:edit")
    @RequestMapping("/pcsBranch_au")
    public String pcsBranch_au(int id, ModelMap modelMap) {


        PcsBranch pcsBranch = pcsBranchMapper.selectByPrimaryKey(id);
        modelMap.put("pcsBranch", pcsBranch);

        return "pcs/pcsBranch/pcsBranch_au";
    }

    @RequiresPermissions("pcsPartyList:edit")
    @RequestMapping(value = "/pcsBranch_exclude", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsBranch_exclude(HttpServletRequest request, Integer[] ids, Boolean isDeleted, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            pcsBranchService.exclude(ids, isDeleted);
            logger.info(log( LogConstants.LOG_PCS, "批量设置不召开党代会的支部：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPartyList:edit")
    @RequestMapping(value = "/pcsBranch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsBranch_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            pcsBranchService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PCS, "批量删除召开党代会的支部：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
