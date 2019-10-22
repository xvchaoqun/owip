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
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class PartyPunishController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("partyPunish:list")
    @RequestMapping("/party/partyPunish")
    public String partyPunish(@RequestParam(defaultValue = "1") Integer cls,
                              Integer type,
                              Integer partyId,
                              HttpServletResponse response,
                              ModelMap modelMap) {
        Party party = partyMapper.selectByPrimaryKey(partyId);
        modelMap.put("party", party);
        cls = type;
        modelMap.put("cls", cls);
        return "party/partyPunish/partyPunish_page";
    }

    @RequiresPermissions("partyPunish:list")
    @RequestMapping("/party/partyPunish_data")
    @ResponseBody
    public void partyPunish_data(HttpServletResponse response,
                                    Integer id,
                                    Integer partyId,
                                    Integer branchId,
                                    Integer userId,
                                    Date punishTime,
                                    Date endTime,
                                    String unit,
                                    Byte type,
                                 Integer userPartyId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyPunishViewExample example = new PartyPunishViewExample();
        PartyPunishViewExample.Criteria criteria = example.createCriteria();
        if (type == 1){
            example.setOrderByClause("party_sort_order desc");
        }
        if (type == 2){
            example.setOrderByClause("branch_sort_order desc");
        }
        if (type == 3){
            example.setOrderByClause("user_id asc");
        }

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
        if (punishTime!=null) {
        criteria.andPunishTimeGreaterThan(punishTime);
        }
        if (endTime!=null) {
        criteria.andEndTimeGreaterThan(endTime);
        }
        if (StringUtils.isNotBlank(unit)) {
            criteria.andUnitLike(SqlUtils.like(unit));
        }

        /*if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            partyPunish_export(example, response);
            return;
        }*/

        long count = partyPunishViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyPunishView> records= partyPunishViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(partyPunish.class, partyPunishMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("partyPunish:edit")
    @RequestMapping(value = "/party/partyPunish_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyPunish_au(PartyPunish record,
                                 Integer partyId,
                                 Integer branchId,
                                 Integer userId,
                                 String punishTime,
                                 String endTime,
                                 HttpServletRequest request) {

        Integer id = record.getId();
        Integer pbu = partyId != null ? partyId : (branchId != null ? branchId : userId);

        Byte type = 0;
        if (partyId != null){
            type = 1;
        }else if (branchId != null){
            type = 2;
        }else if (userId != null){
            type = 3;
        }
        record.setType(type);
        if (StringUtils.isNotBlank(punishTime)){
            record.setPunishTime(DateUtils.parseDate(punishTime,DateUtils.YYYYMMDD_DOT));
        }
        if (StringUtils.isNotBlank(endTime)){
            record.setEndTime(DateUtils.parseDate(endTime,DateUtils.YYYYMMDD_DOT));
        }
        if (id == null) {
            
            partyPunishService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PARTY, "添加党内惩罚信息：{0}", record.getId()));
        } else {

            partyPunishService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PARTY, "更新党内惩罚信息：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyPunish:edit")
    @RequestMapping("/party/partyPunish_au")
    public String partyPunish_au(Integer id,
                                 Integer partyId,
                                 Integer branchId,
                                 Integer userId,
                                 @RequestParam(defaultValue = "1")int cls,
                                 ModelMap modelMap) {

        modelMap.put("cls", cls);
        PartyPunishView partyPunishView = new PartyPunishView();
        if (partyId != null || branchId != null || userId != null) {
            Party party = partyMapper.selectByPrimaryKey(partyId);
            Branch branch = branchMapper.selectByPrimaryKey(branchId);
            SysUserView user = new SysUserView();
            if (userId != null) {
                user = sysUserService.findById(userId);
            }
            modelMap.put("user", user);
            modelMap.put("branch", branch);
            modelMap.put("party", party);
        }else if (id != null){
            partyPunishView = partyPunishService.getById(id);
            Party party = partyMapper.selectByPrimaryKey(partyPunishView.getPartyId());
            if (partyPunishView.getBranchId() != null){
                Party branchParty = partyMapper.selectByPrimaryKey(partyPunishView.getBranchPartyId());
                modelMap.put("branchParty", branchParty);
            }
            Branch branch = branchMapper.selectByPrimaryKey(partyPunishView.getBranchId());
            SysUserView user = new SysUserView();
            if (partyPunishView.getUserId() != null) {
                user = sysUserService.findById(partyPunishView.getUserId());
            }
            modelMap.put("user", user);
            modelMap.put("branch", branch);
            modelMap.put("party", party);
        }
        modelMap.put("partyPunish", partyPunishView);
        return "party/partyPunish/partyPunish_au";
    }

    @RequiresPermissions("partyPunish:edit")
    @RequestMapping(value = "/party/partyPunish_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map partyPunish_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partyPunishService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PARTY, "批量删除党内惩罚信息：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
