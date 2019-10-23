package controller.party;

import controller.BaseController;
import domain.party.*;
import domain.sys.SysUserView;
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
import org.springframework.web.multipart.MultipartFile;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller

public class PartyRewardController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("RePu:function")
    @RequestMapping("/party/partyRePu_page")
    public String partyReward_menu(Integer type,
                                   Integer cls,
                                   @RequestParam(defaultValue = "1")Integer clss,
                                   @RequestParam(defaultValue = "1")Integer list,
                                   Integer partyId,
                                   Integer userPartyId,
                                   Integer branchId,
                                   Integer userId,
                                   ModelMap modelMap) {

        Party party = new Party();
        if (partyId != null) {

            party = partyMapper.selectByPrimaryKey(partyId);
        }
        if (userPartyId != null) {

            party = partyMapper.selectByPrimaryKey(userPartyId);
        }

        Branch branch = branchMapper.selectByPrimaryKey(branchId);
        SysUserView user = new SysUserView();
        if (userId != null) {
            user = sysUserService.findById(userId);
        }
        modelMap.put("user", user);
        modelMap.put("branch", branch);
        modelMap.put("party", party);
        cls = type;
        modelMap.put("cls", cls);
        modelMap.put("list", list);
        modelMap.put("clss", clss);

        return "party/partyReward/partyRewardList_page";
    }

    @RequiresPermissions("partyReward:list")
    @RequestMapping("/party/partyReward")
    public String partyReward(@RequestParam(defaultValue = "1") Integer cls,
                              Integer type,
                              Integer partyId,
                              Integer branchId,
                              Integer userId,
                              HttpServletResponse response,
                              ModelMap modelMap) {

        Party party = partyMapper.selectByPrimaryKey(partyId);
        Branch branch = branchMapper.selectByPrimaryKey(branchId);
        SysUserView user = new SysUserView();
        if (userId != null) {
            user = sysUserService.findById(userId);
        }
        modelMap.put("user", user);
        modelMap.put("branch", branch);
        modelMap.put("party", party);
        cls = type;
        modelMap.put("cls", cls);

        return "party/partyReward/partyReward_page";
    }

    @RequiresPermissions("partyReward:list")
    @RequestMapping("/party/partyReward_data")
    @ResponseBody
    public void partyReward_data(HttpServletResponse response,
                                    Integer id,
                                    Integer partyId,
                                    Integer branchId,
                                    Integer userId,
                                    Date rewardTime,
                                    Integer rewardType,
                                    String unit,
                                 Integer userPartyId,
                                 Byte type,
                                 String name,
                                 String proofFilename,
                                 @RequestParam(required = false, defaultValue = "1") Integer cls,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo , ModelMap modelMap)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyRewardViewExample example = new PartyRewardViewExample();
        PartyRewardViewExample.Criteria criteria = example.createCriteria();
        if (type == 1){
            example.setOrderByClause("party_sort_order desc");
        }
        if (type == 2){
            example.setOrderByClause("branch_sort_order desc");
        }
        if (type == 3){
            example.setOrderByClause("user_id asc");
        }


        modelMap.put("cls", cls);

        if (id!=null) {
            criteria.andIdEqualTo(id);
        }
        if (type != null){
            criteria.andTypeEqualTo(type);
        }
        if (userPartyId != null){
            criteria.andUserPartyIdEqualTo(userPartyId);
        }

        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (rewardTime!=null) {
        criteria.andRewardTimeGreaterThan(rewardTime);
        }
        if (rewardType!=null) {
            criteria.andRewardTypeEqualTo(rewardType);
        }
        if (StringUtils.isNotBlank(unit)) {
            criteria.andUnitLike(SqlUtils.like(unit));
        }
        if (StringUtils.isNotBlank(name)){
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (StringUtils.isNotBlank(proofFilename)){
            criteria.andProofFilenameLike(SqlUtils.like(proofFilename));
        }

        /*if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            partyReward_export(example, response);
            return;
        }*/

        long count = partyRewardViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyRewardView> records= partyRewardViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(partyReward.class, partyRewardMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("partyReward:edit")
    @RequestMapping(value = "/party/partyReward_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyReward_au(PartyReward record,
                                 Integer partyId,
                                 Integer branchId,
                                 Integer userId,
                                 String rewardTime,
                                 MultipartFile _proof,
                                 HttpServletRequest request) {

        Integer id = record.getId();
        Integer pbu = branchId != null ? branchId : (partyId != null ? partyId : userId);

        Byte type = 0;
        if (branchId != null){
            type = 2;
        }else if (partyId != null){
            type = 1;
        }else if (userId != null){
            type = 3;
        }
        record.setType(type);
        if (StringUtils.isNotBlank(rewardTime)){
            record.setRewardTime(DateUtils.parseDate(rewardTime,DateUtils.YYYYMMDD_DOT));
        }
        if (_proof != null){
            String originalFilename = _proof.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR + "partyReward" + FILE_SEPARATOR + "file" + FILE_SEPARATOR + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_proof, new File(springProps.uploadPath + savePath));

            record.setProofFilename(originalFilename);
            record.setProof(savePath);
        }

        if (id == null) {
            
            partyRewardService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PARTY, "添加党内奖励信息：{0}", record.getId()));
        } else {

            partyRewardService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PARTY, "更新党内奖励信息：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyReward:edit")
    @RequestMapping("/party/partyReward_au")
    public String partyReward_au(Integer id,
                                 Integer partyId,
                                 Integer branchId,
                                 Integer userId,
                                 Integer list,
                                 @RequestParam(defaultValue = "1")int cls,
                                 ModelMap modelMap) {

        modelMap.put("list", list);
        modelMap.put("cls", cls);
        PartyRewardView partyRewardView = null;
        if (id != null){
            partyRewardView = partyRewardService.getById(id);
            Party party = partyMapper.selectByPrimaryKey(partyRewardView.getPartyId());
            if (partyRewardView.getBranchId() != null){
                Party branchParty = partyMapper.selectByPrimaryKey(partyRewardView.getBranchPartyId());
                modelMap.put("branchParty", branchParty);
            }
            Branch branch = branchMapper.selectByPrimaryKey(partyRewardView.getBranchId());
            SysUserView user = new SysUserView();
            if (partyRewardView.getUserId() != null) {
                user = sysUserService.findById(partyRewardView.getUserId());
            }
            modelMap.put("user", user);
            modelMap.put("branch", branch);
            modelMap.put("party", party);
        }else if (partyId != null || branchId != null || userId != null) {
            Party party = partyMapper.selectByPrimaryKey(partyId);
            Branch branch = branchMapper.selectByPrimaryKey(branchId);
            SysUserView user = new SysUserView();
            if (userId != null) {
                user = sysUserService.findById(userId);
            }
            modelMap.put("user", user);
            modelMap.put("branch", branch);
            modelMap.put("party", party);
        }

        modelMap.put("partyReward", partyRewardView);

        return "party/partyReward/partyReward_au";
    }

    @RequiresPermissions("partyReward:edit")
    @RequestMapping(value = "/party/partyReward_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map partyReward_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            partyRewardService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PARTY, "批量删除党内奖励信息：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
