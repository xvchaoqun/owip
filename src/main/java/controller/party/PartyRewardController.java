package controller.party;

import controller.BaseController;
import domain.party.Branch;
import domain.party.Party;
import domain.party.PartyReward;
import domain.party.PartyRewardExample;
import domain.party.PartyRewardExample.Criteria;
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

        PartyRewardExample example = new PartyRewardExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");


        if (id!=null) {
            criteria.andIdEqualTo(id);
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

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            partyReward_export(example, response);
            return;
        }

        long count = partyRewardMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyReward> records= partyRewardMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
                                 @RequestParam(defaultValue = "1")int cls,
                                 ModelMap modelMap) {

        modelMap.put("cls", cls);
        Party party = partyMapper.selectByPrimaryKey(partyId);
        Branch branch = branchMapper.selectByPrimaryKey(branchId);
        SysUserView user = new SysUserView();
        if (userId != null) {
            user = sysUserService.findById(userId);
        }
        modelMap.put("user", user);
        modelMap.put("branch", branch);
        modelMap.put("party", party);
        if (id != null) {
            PartyReward partyReward = partyRewardMapper.selectByPrimaryKey(id);
            modelMap.put("partyReward", partyReward);
        }

        return "party/partyReward/partyReward_au";
    }

    @RequestMapping(value = "/partyReward_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyReward_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            partyRewardService.del(id);
            logger.info(log( LogConstants.LOG_PARTY, "删除党内奖励信息：{0}", id));
        }
        return success(FormUtils.SUCCESS);
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

    @RequestMapping(value = "/partyReward_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyReward_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        partyRewardService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_PARTY, "党内奖励信息调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void partyReward_export(PartyRewardExample example, HttpServletResponse response) {

        List<PartyReward> records = partyRewardMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"类型|100","party_id|100","branch_id|100","user_id|100","获奖日期|100","获奖类型|100","获得奖项|100","颁奖单位|100","获奖证书|100","获奖证书文件名|100","备注|100","排序|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PartyReward record = records.get(i);
            String[] values = {
                record.getType()+"",
                            record.getPartyId()+"",
                            record.getBranchId()+"",
                            record.getUserId()+"",
                            DateUtils.formatDate(record.getRewardTime(), DateUtils.YYYY_MM_DD),
                            record.getRewardType()+"",
                            record.getName(),
                            record.getUnit(),
                            record.getProof(),
                            record.getProofFilename(),
                            record.getRemark(),
                            record.getSortOrder()+""
            };
            valuesList.add(values);
        }
        String fileName = String.format("党内奖励信息(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/partyReward_selects")
    @ResponseBody
    public Map partyReward_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyRewardExample example = new PartyRewardExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = partyRewardMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PartyReward> records = partyRewardMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PartyReward record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
