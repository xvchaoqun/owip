package controller.pcs.proposal;

import controller.pcs.PcsBaseController;
import domain.base.MetaClass;
import domain.base.MetaType;
import domain.pcs.PcsConfig;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lm on 2017/9/22.
 */
@Controller
@RequestMapping("/pcs")
public class PcsProposalSettingController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsProposalSetting:list")
    @RequestMapping("/pcsProposalSetting")
    public String pcsProposalSetting(ModelMap modelMap) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        modelMap.put("pcsConfig", currentPcsConfig);

        Map<Integer, MetaType> prTypes = CmTag.getMetaTypes("mc_pcs_proposal");
        modelMap.put("prTypes", prTypes.values());

        return "pcs/pcsProposal/pcsProposalSetting";
    }

    @RequiresPermissions("pcsProposalSetting:edit")
    @RequestMapping("/pcsProposalType_au")
    public String pcsProposalType_au(Integer id, ModelMap modelMap) {

        MetaClass proposalTypeClass = CmTag.getMetaClassByCode("mc_pcs_proposal");
        modelMap.put("classId", proposalTypeClass.getId());

        if(id!=null){
            MetaType proposalType =  CmTag.getMetaType(id);
            modelMap.put("proposalType", proposalType);
        }

        return "pcs/pcsProposal/pcsProposalType_au";
    }

    @RequiresPermissions("pcsProposalSetting:edit")
    @RequestMapping(value = "/pcsProposalTime_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsProposalTime_au(PcsConfig record) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        record.setId(currentPcsConfig.getId());

        pcsConfigMapper.updateByPrimaryKeySelective(record);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsProposalSetting:edit")
    @RequestMapping(value = "/pcsProposalType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsProposalType_au(MetaType record) {

        if(record.getId()==null) {
            record.setCode(metaTypeService.genCode());
            metaTypeService.insertSelective(record);
        }else{
            metaTypeService.updateByPrimaryKeySelective(record);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsProposalSetting:del")
    @RequestMapping(value = "/pcsProposalType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsProposalType_del(int id) {

        MetaType metaType = metaTypeMapper.selectByPrimaryKey(id);
        MetaClass proposalTypeClass = metaClassService.codeKeyMap().get("mc_pcs_proposal");
        if(metaType.getClassId().intValue() != proposalTypeClass.getId()){
            throw new UnauthorizedException();
        }

        metaTypeService.del(id);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsProposalSetting:changeOrder")
    @RequestMapping(value = "/pcsProposalType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsProposalType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        metaTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PCS, "提案类型调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
