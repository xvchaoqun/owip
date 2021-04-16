package controller.party;

import controller.BaseController;
import domain.party.*;
import domain.party.BranchGroupExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.party.BranchGroupMapper;
import service.party.BranchGroupService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class BranchGroupController extends BaseController {

    @Autowired
    private BranchGroupMapper branchGroupMapper;
    @Autowired
    private BranchGroupService branchGroupService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("branchGroup:list")
    @RequestMapping("/branchGroup")
    public String branchGroup(Integer branchId,ModelMap modelMap) {

        modelMap.put("branch",branchMapper.selectByPrimaryKey(branchId));

        return "party/branchGroup/branchGroup_page";
    }

    @RequiresPermissions("branchGroup:list")
    @RequestMapping("/branchGroup_data")
    @ResponseBody
    public void branchGroup_data(HttpServletResponse response,
                                    String name,
                                    Integer userId,
                                    Integer branchId,
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

        BranchGroupExample example = new BranchGroupExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (branchId!=null){
            criteria.andBranchIdEqualTo(branchId);
        }
        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            branchGroup_export(CmTag.getBranch(branchId), example, response);
            return;
        }

        long count = branchGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<BranchGroup> records= branchGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(branchGroup.class, branchGroupMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("branchGroup:edit")
    @RequestMapping(value = "/branchGroup_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchGroup_au(BranchGroup record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            record.setUserId(ShiroHelper.getCurrentUserId());
            record.setCreateDate(new Date());

            branchGroupService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PARTY, "添加党小组：{0}", record.getId()));
        } else {

            branchGroupService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PARTY, "更新党小组：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchGroup:edit")
    @RequestMapping("/branchGroup_au")
    public String branchGroup_au(Integer id,Integer branchId, ModelMap modelMap) {

        if (id != null) {
            BranchGroup branchGroup = branchGroupMapper.selectByPrimaryKey(id);
            branchId = branchGroup.getBranchId();
            modelMap.put("branchGroup", branchGroup);
            Integer userId = branchGroup.getUserId();
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        modelMap.put("branchId",branchId);
        return "party/branchGroup/branchGroup_au";
    }

    @RequiresPermissions("branchGroup:changeOrder")
    @RequestMapping(value = "/branchGroup_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchGroup_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        branchGroupService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_CG, "党小组调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchGroup:del")
    @RequestMapping(value = "/branchGroup_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchGroup_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            branchGroupService.del(id);
            logger.info(log( LogConstants.LOG_PARTY, "删除党小组：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchGroup:del")
    @RequestMapping(value = "/branchGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map branchGroup_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            branchGroupService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PARTY, "批量删除党小组：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void branchGroup_export(Branch branch, BranchGroupExample example, HttpServletResponse response) {

        List<BranchGroup> records = branchGroupMapper.selectByExample(example);
        Party party = new Party();
        if (branch != null)
        party = CmTag.getParty(branch.getPartyId());

        int rownum = records.size();
        String[] titles = {"所在党组织|300","所属支部|200","党校小组名称|250","学工号|150","姓名|100","类别|100"};
        List<String[]> valuesList = new ArrayList<>();

        for (BranchGroup record : records) {

            List<BranchGroupMember> branchGroupMembers = branchGroupService.getBranchGroupMembers(record.getId());

            for (BranchGroupMember branchGroupMember : branchGroupMembers){

                SysUserView sysUserView = CmTag.getUserById(branchGroupMember.getUserId());

                String[] values = {
                        party.getName(),
                        branch.getName(),
                        record.getName(),
                        sysUserView.getCode(),
                        sysUserView.getRealname(),
                        branchGroupMember.getIsLeader()?"组长":"成员"
                };
                valuesList.add(values);
            }
        }
        String fileName = String.format("党小组(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/branchGroup_selects")
    @ResponseBody
    public Map branchGroup_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchGroupExample example = new BranchGroupExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = branchGroupMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<BranchGroup> records = branchGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(BranchGroup record:records){

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
