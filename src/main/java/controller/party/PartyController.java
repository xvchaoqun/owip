package controller.party;

import controller.BaseController;
import domain.party.*;
import domain.party.PartyExample.Criteria;
import domain.sys.MetaType;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
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
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class PartyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 基本信息
    @RequiresPermissions("party:list")
    @RequestMapping("/party_base")
    public String party_base(Integer id, ModelMap modelMap) {

        Party party = partyMapper.selectByPrimaryKey(id);
        modelMap.put("party", party);
        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(id);
        if(presentGroup!=null) {
            PartyMemberExample example = new PartyMemberExample();
            example.createCriteria().andGroupIdEqualTo(presentGroup.getId());
            example.setOrderByClause("sort_order desc");
            List<PartyMember> PartyMembers = partyMemberMapper.selectByExample(example);
            modelMap.put("partyMembers", PartyMembers);
        }
        modelMap.put("adminIds", commonMapper.findPartyAdmin(id));

        return "party/party_base";
    }

    @RequiresPermissions("party:list")
    @RequestMapping("/party_view")
    public String party_show_page(HttpServletResponse response,  ModelMap modelMap) {

        return "party/party_view";
    }

    @RequiresPermissions("party:list")
    @RequestMapping("/party")
    public String party() {

        return "index";
    }

    @RequiresRoles(value = {"admin", "odAdmin"}, logical = Logical.OR)
    @RequiresPermissions("party:list")
    @RequestMapping("/party_page")
    public String party_page(ModelMap modelMap,@RequestParam(required = false, defaultValue = "1")Byte status) {

        modelMap.put("status", status);

        return "party/party_page";
    }

    @RequiresRoles(value = {"admin", "odAdmin"}, logical = Logical.OR)
    @RequiresPermissions("party:list")
    @RequestMapping("/party_data")
    public void party_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "ow_party") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                           @RequestParam(required = false, defaultValue = "1")Byte status,
                                    String code,
                                    String name,
                                    Integer unitId,
                                    Integer classId,
                                    Integer typeId,
                                    Integer unitTypeId,
                                    Boolean isEnterpriseBig,
                                    Boolean isEnterpriseNationalized,
                                    Boolean isSeparate,
                                    String _foundTime,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyViewExample example = new PartyViewExample();
        PartyViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        criteria.andIsDeletedEqualTo(status==-1);

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (classId!=null) {
            criteria.andClassIdEqualTo(classId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (unitTypeId!=null) {
            criteria.andUnitTypeIdEqualTo(unitTypeId);
        }
        if(isEnterpriseBig!=null){
            criteria.andIsEnterpriseBigEqualTo(isEnterpriseBig);
        }
        if(isEnterpriseNationalized!=null){
            criteria.andIsEnterpriseNationalizedEqualTo(isEnterpriseNationalized);
        }
        if(isSeparate!=null){
            criteria.andIsSeparateEqualTo(isSeparate);
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
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            party_export(example, response);
            return;
        }

        int count = partyViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyView> Partys = partyViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", Partys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }

    @RequiresRoles(value = {"admin", "odAdmin"}, logical = Logical.OR)
    @RequiresPermissions("party:edit")
    @RequestMapping(value = "/party_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_party_au(Party record, String _foundTime, HttpServletRequest request) {

        Integer id = record.getId();
        if (partyService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }
        if(StringUtils.isNotBlank(_foundTime)){
            record.setFoundTime(DateUtils.parseDate(_foundTime, DateUtils.YYYY_MM_DD));
        }
        record.setIsEnterpriseBig((record.getIsEnterpriseBig()==null)?false:record.getIsEnterpriseBig());
        record.setIsEnterpriseNationalized((record.getIsEnterpriseNationalized() == null) ? false : record.getIsEnterpriseNationalized());
        record.setIsSeparate((record.getIsSeparate() == null) ? false : record.getIsSeparate());

        if (id == null) {
            record.setCreateTime(new Date());
            partyService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "添加基层党组织：%s", record.getId()));
        } else {

            partyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新基层党组织：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {"admin", "odAdmin"}, logical = Logical.OR)
    @RequiresPermissions("party:edit")
    @RequestMapping("/party_au")
    public String party_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Party party = partyMapper.selectByPrimaryKey(id);
            modelMap.put("party", party);
        }

        return "party/party_au";
    }

    /*@RequiresRoles(value = {"admin", "odAdmin"}, logical = Logical.OR)
    @RequiresPermissions("party:del")
    @RequestMapping(value = "/party_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_party_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            partyService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除基层党组织：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresRoles(value = {"admin", "odAdmin"}, logical = Logical.OR)
    @RequiresPermissions("party:del")
    @RequestMapping(value = "/party_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        @RequestParam(required = false, defaultValue = "1")boolean isDeleted,
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partyService.batchDel(ids, isDeleted);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除基层党组织：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {"admin", "odAdmin"}, logical = Logical.OR)
    @RequiresPermissions("party:changeOrder")
    @RequestMapping(value = "/party_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_party_changeOrder(Integer id, Integer sortOrder, HttpServletRequest request) {

        partyService.changeOrder(id, sortOrder);
        logger.info(addLog(SystemConstants.LOG_OW, "基层党组织调序：%s,%s", id, sortOrder));
        return success(FormUtils.SUCCESS);
    }

    public void party_export(PartyViewExample example, HttpServletResponse response) {

        List<PartyView> records = partyViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号","名称","简称","所属单位","党总支类别","组织类别","所在单位属性","联系电话","邮箱", "成立时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PartyView record = records.get(i);
            String[] values = {
                    record.getCode(),
                    record.getName(),
                    record.getShortName(),
                    record.getUnitId()==null?"":unitService.findAll().get(record.getUnitId()).getName(),
                    metaTypeService.getName(record.getClassId()),
                    metaTypeService.getName(record.getTypeId()),
                    metaTypeService.getName(record.getUnitTypeId()),
                    record.getPhone(),
                    record.getEmail(),
                    DateUtils.formatDate(record.getFoundTime(), DateUtils.YYYY_MM_DD),
            };
            valuesList.add(values);
        }
        String fileName = "基层党组织_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/party_selects")
    @ResponseBody
    public Map party_selects(Integer pageSize, Boolean auth, Boolean notDirect,
                             Boolean del,
                             Boolean notBranchAdmin,
                             Integer pageNo, Integer classId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyExample example = new PartyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(del!=null){
            criteria.andIsDeletedEqualTo(del);
        }

        if(classId!=null) criteria.andClassIdEqualTo(classId);

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }
        if(BooleanUtils.isTrue(notDirect)){
            MetaType mtDirectBranch = CmTag.getMetaTypeByCode("mt_direct_branch");
            criteria.andClassIdNotEqualTo(mtDirectBranch.getId());
        }

        //===========权限
        if(BooleanUtils.isTrue(auth)) {
            Subject subject = SecurityUtils.getSubject();
            if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                    && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {

                List<Integer> partyIdList = loginUserService.adminPartyIdList();
                if(BooleanUtils.isNotTrue(notBranchAdmin)) { // 读取管理党支部所属的分党委，供查询；
                                                             // 如果账号只是支部管理员，无需读取分党委，则设置notBranchAdmin=1；
                    List<Integer> branchIdList = loginUserService.adminBranchIdList();
                    Map<Integer, Branch> branchMap = branchService.findAll();
                    for (Integer branchId : branchIdList) {
                        Branch branch = branchMap.get(branchId);
                        if (branch != null) {
                            partyIdList.add(branch.getPartyId());
                        }
                    }
                }
                if (partyIdList.size() > 0)
                    criteria.andIdIn(partyIdList);
                else
                    criteria.andIdIsNull();
            }
        }

        int count = partyMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Party> partys = partyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));
        List<Map<String, Object>> options = new ArrayList<>();
        for(Party party:partys){

            Map<String, Object> option = new HashMap<>();
            option.put("text", party.getName());
            option.put("id", party.getId());
            option.put("class", party.getClassId());
            option.put("del", party.getIsDeleted());
            options.add(option);
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
