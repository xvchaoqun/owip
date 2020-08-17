package controller.pcs;

import domain.pcs.PcsExcludeBranch;
import domain.pcs.PcsExcludeBranchExample;
import domain.pcs.PcsExcludeBranchExample.Criteria;
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
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.ContextHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/pcs")
public class PcsExcludeBranchController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsExcludeBranch:list")
    @RequestMapping("/pcsExcludeBranch")
    public String pcsExcludeBranch(Integer partyId,
                                   Integer branchId, ModelMap modelMap) {

        if (partyId != null)
            modelMap.put("party", partyService.findAll().get(partyId));
        if (branchId != null)
            modelMap.put("branch", branchService.findAll().get(branchId));

        return "pcs/pcsExcludeBranch/pcsExcludeBranch_page";
    }

    @RequiresPermissions("pcsExcludeBranch:list")
    @RequestMapping("/pcsExcludeBranch_data")
    public void pcsExcludeBranch_data(HttpServletResponse response,
                                      Integer partyId,
                                      Integer branchId,
                                      Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsExcludeBranchExample example = new PcsExcludeBranchExample();
        Criteria criteria = example.createCriteria();
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        long count = pcsExcludeBranchMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsExcludeBranch> records = pcsExcludeBranchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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

    @RequiresPermissions("pcsExcludeBranch:edit")
    @RequestMapping(value = "/pcsExcludeBranch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsExcludeBranch_au(PcsExcludeBranch record, HttpServletRequest request) {

        Integer id = record.getId();
        PcsExcludeBranchExample example = new PcsExcludeBranchExample();
        Criteria criteria = example.createCriteria().andPartyIdEqualTo(record.getPartyId()).andBranchIdEqualTo(record.getBranchId());
        if (id != null) {
            criteria.andIdNotEqualTo(id);
        }
        if (pcsExcludeBranchMapper.countByExample(example) > 0) {
            return failed("添加重复");
        }
        record.setUserId(ShiroHelper.getCurrentUserId());
        record.setCreateTime(new Date());
        record.setIp(ContextHelper.getRealIp());
        if (id == null) {
            pcsExcludeBranchMapper.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PCS, "添加不参与党代会的支部：%s", record.getId()));
        } else {

            pcsExcludeBranchMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PCS, "更新不参与党代会的支部：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsExcludeBranch:edit")
    @RequestMapping("/pcsExcludeBranch_au")
    public String pcsExcludeBranch_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PcsExcludeBranch pcsExcludeBranch = pcsExcludeBranchMapper.selectByPrimaryKey(id);
            modelMap.put("pcsExcludeBranch", pcsExcludeBranch);
            if (pcsExcludeBranch != null) {
                Integer partyId = pcsExcludeBranch.getPartyId();
                Integer branchId = pcsExcludeBranch.getBranchId();

                modelMap.put("party", partyService.findAll().get(partyId));
                modelMap.put("branch", branchService.findAll().get(branchId));
            }
        }
        return "pcs/pcsExcludeBranch/pcsExcludeBranch_au";
    }

    @RequiresPermissions("pcsExcludeBranch:del")
    @RequestMapping(value = "/pcsExcludeBranch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            PcsExcludeBranchExample example = new PcsExcludeBranchExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            pcsExcludeBranchMapper.deleteByExample(example);
            logger.info(addLog(LogConstants.LOG_PCS, "批量不参与党代会的支部：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
