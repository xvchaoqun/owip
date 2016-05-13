package controller.party;

import controller.BaseController;
import domain.*;
import domain.BranchExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.BranchMixin;
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
import service.helper.ExportHelper;
import sys.constants.SystemConstants;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class BranchController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    // 基本信息
    @RequiresPermissions("branch:list")
    @RequestMapping("/branch_base")
    public String branch_base(Integer id, ModelMap modelMap) {

        Branch branch = branchMapper.selectByPrimaryKey(id);
        modelMap.put("branch", branch);
        BranchMemberGroup presentGroup = branchMemberGroupService.getPresentGroup(id);
        if(presentGroup!=null) {
            BranchMemberExample example = new BranchMemberExample();
            example.createCriteria().andGroupIdEqualTo(presentGroup.getId());
            example.setOrderByClause("sort_order desc");
            List<BranchMember> BranchMembers = branchMemberMapper.selectByExample(example);
            modelMap.put("branchMembers", BranchMembers);
        }

        modelMap.put("typeMap", metaTypeService.metaTypes("mc_branch_member_type"));
        return "party/branch/branch_base";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch_view")
    public String branch_show_page(HttpServletResponse response,  ModelMap modelMap) {

        return "party/branch/branch_view";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch")
    public String branch() {

        return "index";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch_page")
    public String branch_page(ModelMap modelMap) {

        modelMap.put("typeMap", metaTypeService.metaTypes("mc_branch_type"));


        return "party/branch/branch_page";
    }

    @RequiresPermissions("branch:list")
    @RequestMapping("/branch_data")
    public void branch_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "ow_branch") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    String code,
                                    String name,
                                    Integer partyId,
                                    Integer typeId,
                                    Integer unitTypeId,
                                    String _foundTime,
                                    Boolean isStaff,
                                    Boolean isPrefessional,
                                    Boolean isBaseTeam,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchExample example = new BranchExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (unitTypeId!=null) {
            criteria.andUnitTypeIdEqualTo(unitTypeId);
        }
        if(isStaff!=null){
            criteria.andIsStaffEqualTo(isStaff);
        }
        if(isPrefessional!=null){
            criteria.andIsPrefessionalEqualTo(isPrefessional);
        }
        if(isBaseTeam!=null){
            criteria.andIsBaseTeamEqualTo(isBaseTeam);
        }
        if(StringUtils.isNotBlank(_foundTime)) {
            String foundTimeStart = _foundTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String foundTimeEnd = _foundTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(foundTimeStart)) {
                criteria.andFoundTimeGreaterThanOrEqualTo(DateUtils.parseDate(foundTimeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(foundTimeEnd)) {
                criteria.andFoundTimeLessThanOrEqualTo(DateUtils.parseDate(foundTimeEnd, DateUtils.YYYY_MM_DD));
            }
        }

        if (export == 1) {
            branch_export(example, response);
            return;
        }

        int count = branchMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<Branch> Branchs = branchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", Branchs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(Branch.class, BranchMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("branch:edit")
    @RequestMapping(value = "/branch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_au(Branch record, String _foundTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (branchService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }

        if(StringUtils.isNotBlank(_foundTime)){
            record.setFoundTime(DateUtils.parseDate(_foundTime, DateUtils.YYYY_MM_DD));
        }

        record.setIsEnterpriseBig((record.getIsEnterpriseBig()==null)?false:record.getIsEnterpriseBig());
        record.setIsEnterpriseNationalized((record.getIsEnterpriseNationalized() == null) ? false : record.getIsEnterpriseNationalized());
        record.setIsUnion((record.getIsUnion() == null) ? false : record.getIsUnion());
        record.setIsStaff((record.getIsStaff() == null) ? false : record.getIsStaff());
        record.setIsPrefessional((record.getIsPrefessional() == null) ? false : record.getIsPrefessional());
        record.setIsBaseTeam((record.getIsBaseTeam() == null) ? false : record.getIsBaseTeam());
        if(!record.getIsStaff()){
            record.setIsPrefessional(false);
        }
        if(!record.getIsPrefessional()){
            record.setIsBaseTeam(false);
        }

        if (id == null) {
            record.setCreateTime(new Date());
            branchService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "添加党支部：%s", record.getId()));
        } else {

            branchService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新党支部：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branch:edit")
    @RequestMapping("/branch_au")
    public String branch_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Branch branch = branchMapper.selectByPrimaryKey(id);
            modelMap.put("branch", branch);
        }

        modelMap.put("typeMap", metaTypeService.metaTypes("mc_branch_type"));

        return "party/branch/branch_au";
    }

    @RequiresPermissions("branch:del")
    @RequestMapping(value = "/branch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            branchService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除党支部：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branch:del")
    @RequestMapping(value = "/branch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            branchService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除党支部：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branch:changeOrder")
    @RequestMapping(value = "/branch_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        branchService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_OW, "党支部调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void branch_export(BranchExample example, HttpServletResponse response) {

        List<Branch> records = branchMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号","名称","简称","所属分党委","类别","单位属性","联系电话","传真","邮箱","成立时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            Branch record = records.get(i);
            String[] values = {
                    record.getCode(),
                    record.getName(),
                    record.getShortName(),
                    record.getPartyId()==null?"":partyService.findAll().get(record.getPartyId()).getName(),
                    metaTypeService.getName(record.getTypeId()),
                    metaTypeService.getName(record.getUnitTypeId()),
                    record.getPhone(),
                    record.getFax(),
                    record.getEmail(),
                    DateUtils.formatDate(record.getFoundTime(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = "党支部_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/branch_selects")
    @ResponseBody
    public Map branch_selects(Integer pageSize, Integer pageNo, Integer partyId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchExample example = new BranchExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = branchMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Branch> branchs = branchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != branchs && branchs.size()>0){

            for(Branch branch:branchs){

                Select2Option option = new Select2Option();
                option.setText(branch.getName());
                option.setId(branch.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
