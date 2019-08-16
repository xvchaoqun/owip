package controller.pm;


import domain.pm.*;

import org.apache.commons.lang3.StringUtils;

import org.apache.shiro.authz.UnauthorizedException;
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
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.FormUtils;
import sys.utils.IpUtils;


import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller

public class PmExcludeBranchController extends PmBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    @RequiresPermissions("partyBranchMeeting:edit")
    @RequestMapping(value = "/pmExcludeBranch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map pmExcludeBranch_au(Integer partyId,@RequestParam(value = "ids[]") Integer[] ids,ModelMap modelMap) {

        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
            throw new UnauthorizedException();
        }
        if (null != ids && ids.length>0) {
            PmQuarterBranchExample example = new PmQuarterBranchExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<PmQuarterBranch> pmQuarterBranchs=pmQuarterBranchMapper.selectByExample(example);
            PmExcludeBranch record = new PmExcludeBranch();
            for(PmQuarterBranch  pmQuarterBranch:pmQuarterBranchs){
                pmQuarterBranch.setIsExclude(true);
                pmQuarterBranchMapper.updateByPrimaryKeySelective(pmQuarterBranch);



                record.setPartyId(partyId);
                record.setBranchId(pmQuarterBranch.getBranchId());
                record.setUserId(loginUserId);
                record.setCreateTime(new Date());
                record.setIp(IpUtils.getRealIp(ContextHelper.getRequest()));

                pmExcludeBranchMapper.insertSelective(record);
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/pmExcludeBranch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyBranchMeeting_del(Integer partyId,@RequestParam(value = "ids[]") Integer[] ids,ModelMap modelMap) {
        Integer loginUserId = ShiroHelper.getCurrentUserId();

        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
            throw new UnauthorizedException();
        }
        if (null != ids && ids.length>0) {
            PmQuarterBranchExample example = new PmQuarterBranchExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<PmQuarterBranch> pmQuarterBranchs=pmQuarterBranchMapper.selectByExample(example);
            for(PmQuarterBranch  pmQuarterBranch:pmQuarterBranchs){
                pmQuarterBranch.setIsExclude(false);
                pmQuarterBranchMapper.updateByPrimaryKeySelective(pmQuarterBranch);

                PmExcludeBranchExample excludeExample = new PmExcludeBranchExample();
                excludeExample.createCriteria().andBranchIdEqualTo(pmQuarterBranch.getBranchId()).andPartyIdEqualTo(partyId);
                pmExcludeBranchMapper.deleteByExample(excludeExample);
            }

        }
        return success(FormUtils.SUCCESS);
    }

}
