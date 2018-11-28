package controller.party;

import controller.BaseController;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchUnit;
import domain.party.*;
import domain.party.BranchMemberGroupExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class BranchMemberGroupController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("branchMemberGroup:list")
    @RequestMapping("/branchMemberGroup_view")
    public String branchMemberGroup_view() {

        /*BranchMemberGroupExample example = new BranchMemberGroupExample();
        example.createCriteria().andBranchIdEqualTo(branchId);
        example.setOrderByClause(String.format("%s %s", sort, order));
        List<BranchMemberGroup> BranchMemberGroups = branchMemberGroupMapper.selectByExample(example);
        modelMap.put("branchMemberGroups", BranchMemberGroups);*/
        return "party/branchMemberGroup/branchMemberGroup_view";
    }

    @RequiresPermissions("branchMemberGroup:list")
    @RequestMapping("/branchMemberGroup")
    public String branchMemberGroup(@RequestParam(required = false, defaultValue = "1")Byte status,
                                         Integer partyId,
                                         Integer branchId, ModelMap modelMap) {

        modelMap.put("status", status);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }

        return "party/branchMemberGroup/branchMemberGroup_page";
    }
    @RequiresPermissions("branchMemberGroup:list")
    @RequestMapping("/branchMemberGroup_data")
    public void branchMemberGroup_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "ow_branch_member_group") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                       @RequestParam(required = false, defaultValue = "1")Byte status,
                                    Integer partyId,
                                    Integer branchId,
                                    String name,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchMemberGroupViewExample example = new BranchMemberGroupViewExample();
        BranchMemberGroupViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        criteria.andIsDeletedEqualTo(status == -1);

        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }

        //===========权限
        //criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(RoleConstants.ROLE_ADMIN)
                && !subject.hasRole(RoleConstants.ROLE_ODADMIN)) {

            if(!subject.isPermitted("party:list")) { // 有查看基层党组织的权限的话，则可以查看所有的支部

                List<Integer> partyIdList = loginUserService.adminPartyIdList();
                if (partyIdList.size() > 0)
                    criteria.andPartyIdIn(partyIdList);
                else criteria.andPartyIdIsNull();
            }
        }

        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            branchMemberGroup_export(example, response);
            return;
        }

        int count = branchMemberGroupViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<BranchMemberGroupView> BranchMemberGroups = branchMemberGroupViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);


        Map resultMap = new HashMap();
        resultMap.put("rows", BranchMemberGroups);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("branchMemberGroup:edit")
    @RequestMapping(value = "/branchMemberGroup_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMemberGroup_au(BranchMemberGroup record,
                                       String _tranTime,
                                       String _actualTranTime,
                                       String _appointTime,
                                       HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_tranTime)){
            record.setTranTime(DateUtils.parseDate(_tranTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_actualTranTime)){
            record.setActualTranTime(DateUtils.parseDate(_actualTranTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_appointTime)){
            record.setAppointTime(DateUtils.parseDate(_appointTime, DateUtils.YYYY_MM_DD));
        }

        record.setIsPresent((record.getIsPresent() == null) ? false : record.getIsPresent());

        if (id == null) {
            branchMemberGroupService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PARTY, "添加支部委员会：%s", record.getId()));
        } else {

            if(record.getFid()!=null && record.getFid().intValue()==record.getId()){
                return failed("不能选择自身为上一届委员会");
            }

            branchMemberGroupService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PARTY, "更新支部委员会：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchMemberGroup:edit")
    @RequestMapping("/branchMemberGroup_au")
    public String branchMemberGroup_au(Integer id, Integer branchId, ModelMap modelMap) {

        Map<Integer, Branch> branchMap = branchService.findAll();
        modelMap.put("branchMap", branchMap);
        Map<Integer, Party> partyMap = partyService.findAll();

        if (id != null) {
            BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(id);
            modelMap.put("branchMemberGroup", branchMemberGroup);

            if(branchMemberGroup.getFid()!=null){
                modelMap.put("fBranchMemberGroup", branchMemberGroupMapper.selectByPrimaryKey(branchMemberGroup.getFid()));
            }

            Branch branch = branchMap.get(branchMemberGroup.getBranchId());
            modelMap.put("branch", branch);
            modelMap.put("party", partyMap.get(branch.getPartyId()));

            Integer dispatchUnitId = branchMemberGroup.getDispatchUnitId();
            if(dispatchUnitId != null) {
                DispatchUnit dispatchUnit = dispatchUnitMapper.selectByPrimaryKey(dispatchUnitId);
                if(dispatchUnit!= null) {
                    modelMap.put("dispatch", dispatchUnit.getDispatch());
                }
            }
        }else{
            if(branchId == null) throw  new IllegalArgumentException("参数错误");
            Branch branch = branchMap.get(branchId);
            modelMap.put("branch", branch);
            modelMap.put("party", partyMap.get(branch.getPartyId()));
        }
        return "party/branchMemberGroup/branchMemberGroup_au";
    }

    /*@RequiresPermissions("branchMemberGroup:del")
    @RequestMapping(value = "/branchMemberGroup_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMemberGroup_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            branchMemberGroupService.del(id);
            logger.info(addLog(LogConstants.LOG_PARTY, "删除支部委员会：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("branchMemberGroup:del")
    @RequestMapping(value = "/branchMemberGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map branchMemberGroup_batchDel(HttpServletRequest request,
                        @RequestParam(required = false, defaultValue = "1")boolean isDeleted,
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            branchMemberGroupService.batchDel(ids, isDeleted);
            logger.info(addLog(LogConstants.LOG_PARTY, "批量删除支部委员会：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchMemberGroup:changeOrder")
    @RequestMapping(value = "/branchMemberGroup_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMemberGroup_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        branchMemberGroupService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PARTY, "支部委员会调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void branchMemberGroup_export(BranchMemberGroupViewExample example, HttpServletResponse response) {

        List<BranchMemberGroupView> records = branchMemberGroupViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"名称", "所属分党委", "所属党支部", "是否现任班子","应换届时间","实际换届时间","任命时间","发文"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            BranchMemberGroupView record = records.get(i);
            Dispatch dispatch = null;
            if(record.getDispatchUnitId()!=null) {
                DispatchUnit dispatchUnit = CmTag.getDispatchUnit(record.getDispatchUnitId());
                if(dispatchUnit!=null)
                    dispatch = dispatchUnit.getDispatch();
            }
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            String[] values = {
                    record.getName(),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    BooleanUtils.isTrue(record.getIsPresent())?"是":"否",
                    DateUtils.formatDate(record.getTranTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getActualTranTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getAppointTime(), DateUtils.YYYY_MM_DD),
                    dispatch==null?"":CmTag.getDispatchCode(dispatch.getCode(), dispatch.getDispatchTypeId(), dispatch.getYear())
            };
            valuesList.add(values);
        }
        String fileName = "支部委员会_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/branchMemberGroup_selects")
    @ResponseBody
    public Map branchMemberGroup_selects(Integer branchId, Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchMemberGroupExample example = new BranchMemberGroupExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = branchMemberGroupMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<BranchMemberGroup> branchMemberGroups = branchMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != branchMemberGroups && branchMemberGroups.size()>0){

            for(BranchMemberGroup branchMemberGroup:branchMemberGroups){

                Select2Option option = new Select2Option();
                option.setText(branchMemberGroup.getName());
                option.setId(branchMemberGroup.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("branchMember:list")
    @RequestMapping("/branch_member")
    public String branch_member(Integer id,  Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id != null) {
            if (null == pageSize) {
                pageSize = springProps.pageSize;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            BranchMemberExample example = new BranchMemberExample();
            BranchMemberExample.Criteria criteria = example.createCriteria().andGroupIdEqualTo(id);
            example.setOrderByClause(String.format("%s %s", "sort_order", "desc"));

            int count = branchMemberMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<BranchMember> branchMembers = branchMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("branchMembers", branchMembers);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (id!=null) {
                searchStr += "&id=" + id;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);

            BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(id);
            modelMap.put("branchMemberGroup", branchMemberGroup);
            modelMap.put("typeMap", metaTypeService.metaTypes("mc_branch_member_type"));
        }

        return "party/branchMemberGroup/branch_member";
    }
}
