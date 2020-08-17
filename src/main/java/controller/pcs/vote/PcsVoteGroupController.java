package controller.pcs.vote;

import controller.pcs.PcsBaseController;
import domain.pcs.*;
import domain.pcs.PcsVoteGroupExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.pcs.common.IPcsCandidate;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
import sys.gson.GsonUtils;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pcs")
public class PcsVoteGroupController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsVoteGroup:list")
    @RequestMapping("/pcsVoteGroup")
    public String pcsVoteGroup() {

        return "pcs/pcsVoteGroup/pcsVoteGroup_page";
    }

    @RequiresPermissions("pcsVoteGroup:list")
    @RequestMapping("/pcsVoteGroup_data")
    public void pcsVoteGroup_data(HttpServletResponse response,
                                  byte type,
                               String name,
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsVoteGroupExample example = new PcsVoteGroupExample();
        Criteria criteria = example.createCriteria().andTypeEqualTo(type);
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if(!SecurityUtils.getSubject().isPermitted("pcsVoteStat:*")) {
            criteria.andRecordUserIdEqualTo(ShiroHelper.getCurrentUserId());
        }

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        long count = pcsVoteGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsVoteGroup> records = pcsVoteGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsVoteGroup.class, pcsVoteGroupMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsVoteGroup:edit")
    @RequestMapping(value = "/pcsVoteGroup_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsVoteGroup_au(PcsVoteGroup record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
            int configId = currentPcsConfig.getId();
            record.setConfigId(configId);
            pcsVoteGroupService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PCS, "添加小组：%s", record.getName()));
        } else {

            pcsVoteGroupService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PCS, "更新小组：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsVoteGroup:edit")
    @RequestMapping("/pcsVoteGroup_au")
    public String pcsVoteGroup_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PcsVoteGroup pcsVoteGroup = pcsVoteGroupMapper.selectByPrimaryKey(id);
            modelMap.put("pcsVoteGroup", pcsVoteGroup);
        }
        return "pcs/pcsVoteGroup/pcsVoteGroup_au";
    }

    @RequiresPermissions("pcsVoteGroup:del")
    @RequestMapping(value = "/pcsVoteGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pcsVoteGroupService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PCS, "批量删除小组：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsVoteGroup:record")
    @RequestMapping(value = "/pcsVoteGroup_record", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsVoteGroup_record(PcsVoteGroup record,
                                          String items,
                                          HttpServletRequest request) throws UnsupportedEncodingException {

        int groupId = record.getId();

        if(!pcsVoteGroupService.allowModify(groupId)){
            return failed("已报送数据，不可修改。");
        }

        List<PcsVoteCandidateFormBean> records = GsonUtils.toBeans(items, PcsVoteCandidateFormBean.class);
        pcsVoteGroupService.submit(record, records);

        logger.info(addLog(LogConstants.LOG_PCS, "党委委员计票录入：%s", groupId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsVoteGroup:record")
    @RequestMapping("/pcsVoteGroup_record")
    public String pcsVoteGroup_record(int groupId, byte type, ModelMap modelMap) {

        modelMap.put("type", type);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        modelMap.put("committeeCanSelect", BooleanUtils.isTrue(currentPcsConfig.getCommitteeCanSelect()));

        PcsVoteGroup pcsVoteGroup = pcsVoteGroupMapper.selectByPrimaryKey(groupId);
        modelMap.put("pcsVoteGroup", pcsVoteGroup);

        PcsVoteCandidateExample example = new PcsVoteCandidateExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        example.setOrderByClause("id asc");
        List<PcsVoteCandidate> pcsVoteCandidates = pcsVoteCandidateMapper.selectByExample(example);
        modelMap.put("pcsVoteCandidates", pcsVoteCandidates);

        modelMap.put("allowModify", pcsVoteGroupService.allowModify(groupId));

        return "pcs/pcsVoteGroup/pcsVoteGroup_record";
    }

    @RequiresPermissions("pcsVoteGroup:record")
    @RequestMapping(value = "/pcsVoteGroup_selectUser", method = RequestMethod.POST)
    public void do_pcsVoteGroup_selectUser(Integer[] userIds,
                                           byte type,
                                           HttpServletResponse response) throws IOException {

        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        List<PcsVoteCandidate> candidates = new ArrayList<>();
        if(userIds!=null){

            for (Integer userId : userIds) {

                SysUserView uv = sysUserService.findById(userId);
                PcsVoteCandidate candidate = new PcsVoteCandidate();
                candidate.setUserId(uv.getId());
                candidate.setRealname(uv.getRealname());
                candidate.setIsFromStage(iPcsMapper.countPartyCandidateList(userId, true, configId,
                        PcsConstants.PCS_STAGE_THIRD, type)>0);

                candidates.add(candidate);
            }
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("candidates", candidates);
        JSONUtils.write(response, resultMap);
    }

    @RequiresPermissions("pcsVoteGroup:record")
    @RequestMapping("/pcsVoteGroup_candidates")
    public String pcsVoteGroup_candidates(byte type, ModelMap modelMap) {

        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        List<IPcsCandidate> candidates =
                iPcsMapper.selectPartyCandidateList(null, true, configId, PcsConstants.PCS_STAGE_THIRD, type, new RowBounds());

        modelMap.put("candidates", candidates);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        modelMap.put("committeeCanSelect", BooleanUtils.isTrue(currentPcsConfig.getCommitteeCanSelect()));

        return "pcs/pcsVoteGroup/pcsVoteGroup_candidates";
    }

    @RequiresPermissions("pcsVoteGroup:record")
    @RequestMapping("/pcsVoteGroup_otherCandidates")
    public String pcsVoteGroup_otherCandidates(ModelMap modelMap) {

        return "pcs/pcsVoteGroup/pcsVoteGroup_otherCandidates";
    }


    @RequiresPermissions("pcsVoteGroup:record")
    @RequestMapping(value = "/pcsVoteGroup_report", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsVoteGroup_report(int groupId,
                                      HttpServletRequest request) throws UnsupportedEncodingException {

        if(!pcsVoteGroupService.allowModify(groupId)){
            return failed("已报送数据。");
        }

        pcsVoteGroupService.report(groupId);

        logger.info(addLog(LogConstants.LOG_PCS, "党委委员计票录入报送：%s", groupId));

        return success(FormUtils.SUCCESS);
    }

    // 退回报送
    @RequiresPermissions("pcsVoteGroup:back")
    @RequestMapping(value = "/pcsVoteGroup_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsVoteGroup_back(int groupId,
                                      HttpServletRequest request) throws UnsupportedEncodingException {

        pcsVoteGroupService.back(groupId);

        logger.info(addLog(LogConstants.LOG_PCS, "党委委员计票录入退回报送：%s", groupId));

        return success(FormUtils.SUCCESS);
    }
}
