package controller.party;

import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.dispatch.DispatchUnit;
import domain.party.*;
import domain.party.BranchMemberGroupExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sys.constants.LogConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.*;

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
    public String branchMemberGroup(@RequestParam(required = false, defaultValue = "1") Byte status,
                                    Integer userId,
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
        if(userId!=null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (status == 2) {
            return "party/branchMemberGroup/branchMember";
        }

        return "party/branchMemberGroup/branchMemberGroup_page";
    }

    @RequiresPermissions("branchMemberGroup:list")
    @RequestMapping("/branchMemberGroup_data")
    public void branchMemberGroup_data(HttpServletResponse response,
                                       @RequestParam(required = false, defaultValue = "1") Byte status,
                                       Integer partyId,
                                       Integer branchId,
                                       String name,
                                       Boolean isPresent,
                                       @RequestDateRange DateRange _appointTime,
                                       @RequestDateRange DateRange _tranTime,
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

        BranchMemberGroupViewExample example = new BranchMemberGroupViewExample();
        BranchMemberGroupViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_present desc, party_sort_order desc, branch_sort_order desc, appoint_time desc");

        criteria.andIsDeletedEqualTo(status == -1);

        if (isPresent != null) {
            criteria.andIsPresentEqualTo(isPresent);
        }

        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }

        //===========权限
        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());
        /*if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

            List<Integer> partyIdList = loginUserService.adminPartyIdList();
            if (partyIdList.size() > 0)
                criteria.andPartyIdIn(partyIdList);
            else criteria.andPartyIdIsNull();
        }*/

        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (_appointTime.getStart()!=null) {
            criteria.andAppointTimeGreaterThanOrEqualTo(_appointTime.getStart());
        }

        if (_appointTime.getEnd()!=null) {
            criteria.andAppointTimeLessThanOrEqualTo(_appointTime.getEnd());
        }

        if (_tranTime.getStart()!=null) {
            criteria.andTranTimeGreaterThanOrEqualTo(_tranTime.getStart());
        }

        if (_tranTime.getEnd()!=null) {
            criteria.andTranTimeLessThanOrEqualTo(_tranTime.getEnd());
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));

            branchMemberGroup_export(example, response);
            return;
        }

        int count = (int) branchMemberGroupViewMapper.countByExample(example);
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

        if (StringUtils.isNotBlank(_tranTime)) {
            record.setTranTime(DateUtils.parseDate(_tranTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_actualTranTime)) {
            record.setActualTranTime(DateUtils.parseDate(_actualTranTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_appointTime)) {
            record.setAppointTime(DateUtils.parseDate(_appointTime, DateUtils.YYYY_MM_DD));
        }

        record.setIsPresent((record.getIsPresent() == null) ? false : record.getIsPresent());

        if (id == null) {
            branchMemberGroupService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PARTY, "添加支部委员会：%s", record.getId()));
        } else {

            if (record.getFid() != null && record.getFid().intValue() == record.getId()) {
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

            if (branchMemberGroup.getFid() != null) {
                modelMap.put("fBranchMemberGroup", branchMemberGroupMapper.selectByPrimaryKey(branchMemberGroup.getFid()));
            }

            Branch branch = branchMap.get(branchMemberGroup.getBranchId());
            modelMap.put("branch", branch);
            modelMap.put("party", partyMap.get(branch.getPartyId()));

            Integer dispatchUnitId = branchMemberGroup.getDispatchUnitId();
            if (dispatchUnitId != null) {
                DispatchUnit dispatchUnit = dispatchUnitMapper.selectByPrimaryKey(dispatchUnitId);
                if (dispatchUnit != null) {
                    modelMap.put("dispatch", dispatchUnit.getDispatch());
                }
            }
        } else if (branchId != null){
            Branch branch = branchMap.get(branchId);
            modelMap.put("branch", branch);
            modelMap.put("party", partyMap.get(branch.getPartyId()));
        }

        return "party/branchMemberGroup/branchMemberGroup_au";
    }

    @RequiresPermissions("branchMemberGroup:edit")
    @RequestMapping("/branchMemberGroup_import")
    public String branchMemberGroup_import(ModelMap modelMap) {

        return "party/branchMemberGroup/branchMemberGroup_import";
    }

    @RequiresPermissions("branchMemberGroup:edit")
    @RequestMapping(value = "/branchMemberGroup_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMemberGroup_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<BranchMemberGroup> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            BranchMemberGroup record = new BranchMemberGroup();
            row++;
            String name = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(name)) {
                throw new OpException("第{0}行支部委员会名称为空", row);
            }
            record.setName(name);

            String branchCode = StringUtils.trimToNull(xlsRow.get(2));
            if (StringUtils.isBlank(branchCode)) {
                throw new OpException("第{0}行所在党支部编号为空", row);
            }
            Branch branch = branchService.getByCode(branchCode);
            if (branch == null) {
                throw new OpException("第{0}行所在党支部编号[{1}]不存在", row, branchCode);
            }
            record.setBranchId(branch.getId());

            record.setIsPresent(StringUtils.contains(xlsRow.get(3), "是"));

            String appointTime = StringUtils.trimToNull(xlsRow.get(4));
            record.setAppointTime(DateUtils.parseStringToDate(appointTime));

            String tranTime = StringUtils.trimToNull(xlsRow.get(5));
            record.setTranTime(DateUtils.parseStringToDate(tranTime));

            if (record.getIsPresent()) {
                String actualTranTime = StringUtils.trimToNull(xlsRow.get(6));
                record.setActualTranTime(DateUtils.parseStringToDate(actualTranTime));
            }

            record.setIsDeleted(false);
            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = branchMemberGroupService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入支部委员会成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
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
                                          @RequestParam(required = false, defaultValue = "1") boolean isDeleted,
                                          @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            branchMemberGroupService.batchDel(ids, isDeleted);
            logger.info(addLog(LogConstants.LOG_PARTY, "撤销支部委员会：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 完全删除已撤销的班子
    @RequiresPermissions("branchMemberGroup:realDel")
    @RequestMapping(value = "/branchMemberGroup_realDel", method = RequestMethod.POST)
    @ResponseBody
    public Map branchMemberGroup_realDel(HttpServletRequest request,
                                         @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            branchMemberGroupService.realDel(ids);
            logger.info(addLog(LogConstants.LOG_PARTY, "删除支部委员会：%s",
                    StringUtils.join(ids, ",")));
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

        List<String> titles = new ArrayList<>(Arrays.asList("名称|250|left", "所属分党委|250|left", "所属党支部|250|left",
                "是否现任班子|70", "应换届时间|100", "实际换届时间|100", "任命时间|100"));

        Map<Integer, MetaType> branchMemberTypeMap = metaTypeService.metaTypes("mc_branch_member_type");
        for (MetaType branchMemberType : branchMemberTypeMap.values()) {
            titles.add(branchMemberType.getName());
        }

        List<List<String>> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            BranchMemberGroupView record = records.get(i);
            /*Dispatch dispatch = null;
            if(record.getDispatchUnitId()!=null) {
                DispatchUnit dispatchUnit = CmTag.getDispatchUnit(record.getDispatchUnitId());
                if(dispatchUnit!=null)
                    dispatch = dispatchUnit.getDispatch();
            }*/
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            List<String> values = new ArrayList<>(Arrays.asList(
                    record.getName(),
                    partyId == null ? "" : partyService.findAll().get(partyId).getName(),
                    branchId == null ? "" : branchService.findAll().get(branchId).getName(),
                    BooleanUtils.isTrue(record.getIsPresent()) ? "是" : "否",
                    DateUtils.formatDate(record.getTranTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getActualTranTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getAppointTime(), DateUtils.YYYY_MM_DD)));

            Map<Integer, List<BranchMember>> branchMemberListMap = branchMemberGroupService.getBranchMemberListMap(record.getId());

            for (MetaType branchMemberType : branchMemberTypeMap.values()) {
                int typeId = branchMemberType.getId();
                List<BranchMember> branchMemberList = branchMemberListMap.get(typeId);
                String realname = "";
                if (branchMemberList != null) {
                    List<String> realnames = new ArrayList<>();
                    for (BranchMember branchMember : branchMemberList) {
                        SysUserView uv = sysUserService.findById(branchMember.getUserId());
                        realnames.add(uv.getRealname());
                    }
                    realname = StringUtils.join(realnames, ",");
                }

                values.add(realname);
            }

            valuesList.add(values);
        }
        String fileName = "支部委员会(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 仅查询某支部下的委员会
    @RequestMapping("/branchMemberGroup_selects")
    @ResponseBody
    public Map branchMemberGroup_selects(Integer branchId, Integer pageSize, Integer pageNo, String searchStr) throws IOException {

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

        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }else{
            criteria.andIdIsNull();
        }

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        int count = branchMemberGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<BranchMemberGroup> branchMemberGroups = branchMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != branchMemberGroups && branchMemberGroups.size() > 0) {

            for (BranchMemberGroup branchMemberGroup : branchMemberGroups) {

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
    public String branch_member(Integer id, Integer memberId, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (memberId != null) {

            BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(memberId);
            modelMap.put("branchMember", branchMember);
        }

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
            example.setOrderByClause("sort_order desc");

            int count = (int) branchMemberMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<BranchMember> branchMembers = branchMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("branchMembers", branchMembers);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (id != null) {
                searchStr += "&id=" + id;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);

            BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(id);
            modelMap.put("branchMemberGroup", branchMemberGroup);
        }

        return "party/branchMemberGroup/branch_member";
    }
}
