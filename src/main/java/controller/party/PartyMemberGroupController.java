package controller.party;

import controller.BaseController;
import controller.global.OpException;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchUnit;
import domain.party.*;
import domain.party.PartyMemberGroupExample.Criteria;
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
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class PartyMemberGroupController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("partyMemberGroup:list")
    @RequestMapping("/partyMemberGroup_view")
    public String partyMemberGroup_view() {

        /*PartyMemberGroupExample example = new PartyMemberGroupExample();
        example.createCriteria().andPartyIdEqualTo(partyId);
        example.setOrderByClause(String.format("%s %s", sort, order));
        List<PartyMemberGroup> PartyMemberGroups = partyMemberGroupMapper.selectByExample(example);
        modelMap.put("partyMemberGroups", PartyMemberGroups);*/
        return "party/partyMemberGroup/partyMemberGroup_view";
    }

    @RequiresPermissions("partyMemberGroup:list")
    @RequestMapping("/partyMemberGroup")
    public String partyMemberGroup(@RequestParam(required = false, defaultValue = "1") Byte status,
                                   Integer partyId,
                                   Integer userId,
                                   @RequestParam(required = false, value = "typeIds") Integer[] typeIds,
                                   ModelMap modelMap) {

        modelMap.put("status", status);

        if (partyId != null) {
            modelMap.put("party", partyService.findAll().get(partyId));
        }
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (status == 2) {
            if (typeIds != null) {
                List<Integer> _typeIds = Arrays.asList(typeIds);
                modelMap.put("selectedTypeIds", _typeIds);
            }
            return "party/partyMemberGroup/partyMember";
        }

        return "party/partyMemberGroup/partyMemberGroup_page";
    }

    @RequiresPermissions("partyMemberGroup:list")
    @RequestMapping("/partyMemberGroup_data")
    public void partyMemberGroup_data(HttpServletResponse response,
                                      @RequestParam(required = false, defaultValue = "1") Byte status,
                                      String name,
                                      Integer partyId,
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

        PartyMemberGroupViewExample example = new PartyMemberGroupViewExample();
        PartyMemberGroupViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_present desc, party_sort_order desc, appoint_time desc");

        criteria.andIsDeletedEqualTo(status == -1);

        //===========权限
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

            //if (!ShiroHelper.isPermitted("party:list")) { // 有查看基层党组织的权限的话，则可以查看所有的

            List<Integer> partyIdList = loginUserService.adminPartyIdList();
            if (partyIdList.size() > 0)
                criteria.andPartyIdIn(partyIdList);
            else criteria.andPartyIdIsNull();
            //}
        }

        if (isPresent != null) {
            criteria.andIsPresentEqualTo(isPresent);
        }

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
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
            partyMemberGroup_export(example, response);
            return;
        }

        int count = (int) partyMemberGroupViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyMemberGroupView> PartyMemberGroups = partyMemberGroupViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", PartyMemberGroups);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;

    }

    @RequiresPermissions("partyMemberGroup:edit")
    @RequestMapping(value = "/partyMemberGroup_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMemberGroup_au(PartyMemberGroup record,
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
            partyMemberGroupService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PARTY, "添加基层党组织领导班子：%s", record.getId()));
        } else {

            if (record.getFid() != null && record.getFid().intValue() == record.getId()) {
                return failed("不能选择自身为上一届班子");
            }

            partyMemberGroupService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PARTY, "更新基层党组织领导班子：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMemberGroup:edit")
    @RequestMapping("/partyMemberGroup_au")
    public String partyMemberGroup_au(Integer id, Integer partyId, ModelMap modelMap) {

        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("partyMap", partyMap);

        if (id != null) {
            PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(id);
            modelMap.put("partyMemberGroup", partyMemberGroup);
            if (partyMemberGroup.getFid() != null) {
                modelMap.put("fPartyMemberGroup", partyMemberGroupMapper.selectByPrimaryKey(partyMemberGroup.getFid()));
            }

            Party party = partyMap.get(partyMemberGroup.getPartyId());
            modelMap.put("party", party);
            Integer dispatchUnitId = partyMemberGroup.getDispatchUnitId();
            if (dispatchUnitId != null) {
                DispatchUnit dispatchUnit = dispatchUnitMapper.selectByPrimaryKey(dispatchUnitId);
                if (dispatchUnit != null) {
                    modelMap.put("dispatch", dispatchUnit.getDispatch());
                }
            }
        } else if (partyId != null) {
            Party party = partyMap.get(partyId);
            modelMap.put("party", party);
        }

        return "party/partyMemberGroup/partyMemberGroup_au";
    }

    @RequiresPermissions("partyMemberGroup:edit")
    @RequestMapping("/partyMemberGroup_import")
    public String partyMemberGroup_import(ModelMap modelMap) {

        return "party/partyMemberGroup/partyMemberGroup_import";
    }

    @RequiresPermissions("partyMemberGroup:edit")
    @RequestMapping(value = "/partyMemberGroup_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMemberGroup_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<PartyMemberGroup> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            PartyMemberGroup record = new PartyMemberGroup();
            row++;
            String name = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(name)) {
                throw new OpException("第{0}行班子名称为空", row);
            }
            record.setName(name);

            String partyCode = StringUtils.trimToNull(xlsRow.get(2));
            if (StringUtils.isBlank(partyCode)) {
                throw new OpException("第{0}行所属分党委编码为空", row);
            }
            Party party = partyService.getByCode(partyCode);
            if (party == null) {
                throw new OpException("第{0}行所属分党委编码[{1}]不存在", row, partyCode);
            }
            record.setPartyId(party.getId());

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

        int addCount = partyMemberGroupService.bacthImport(records);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    /*@RequiresPermissions("partyMemberGroup:del")
    @RequestMapping(value = "/partyMemberGroup_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMemberGroup_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            partyMemberGroupService.del(id);
            logger.info(addLog(LogConstants.LOG_PARTY, "删除基层党组织领导班子：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("partyMemberGroup:del")
    @RequestMapping(value = "/partyMemberGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map partyMemberGroup_batchDel(HttpServletRequest request,
                                         @RequestParam(required = false, defaultValue = "1") boolean isDeleted,
                                         @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            partyMemberGroupService.batchDel(ids, isDeleted);
            logger.info(addLog(LogConstants.LOG_PARTY, "撤销基层党组织领导班子：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 完全删除已撤销的班子
    @RequiresPermissions("partyMemberGroup:realDel")
    @RequestMapping(value = "/partyMemberGroup_realDel", method = RequestMethod.POST)
    @ResponseBody
    public Map partyMemberGroup_realDel(HttpServletRequest request,
                                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            partyMemberGroupService.realDel(ids);
            logger.info(addLog(LogConstants.LOG_PARTY, "删除基层党组织领导班子：%s",
                    StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMemberGroup:changeOrder")
    @RequestMapping(value = "/partyMemberGroup_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMemberGroup_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        partyMemberGroupService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PARTY, "基层党组织领导班子调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void partyMemberGroup_export(PartyMemberGroupViewExample example, HttpServletResponse response) {

        List<PartyMemberGroupView> records = partyMemberGroupViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"名称|350|left", "所属分党委|350|left", "是否现任班子|70", "应换届时间|100", "实际换届时间|110", "任命时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PartyMemberGroupView record = records.get(i);
            Integer partyId = record.getPartyId();

            String dispatchCode = "";
            DispatchUnit dispatchUnit = CmTag.getDispatchUnit(record.getDispatchUnitId());
            if (dispatchUnit != null) {
                Dispatch dispatch = dispatchUnit.getDispatch();
                if (dispatch != null)
                    dispatchCode = CmTag.getDispatchCode(dispatch.getCode(), dispatch.getDispatchTypeId(), dispatch.getYear());
            }
            String[] values = {
                    record.getName(),
                    partyId == null ? "" : partyService.findAll().get(partyId).getName(),
                    BooleanUtils.isTrue(record.getIsPresent()) ? "是" : "否",
                    DateUtils.formatDate(record.getTranTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getActualTranTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getAppointTime(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = "领导班子_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 仅查询某分党委下的班子
    @RequestMapping("/partyMemberGroup_selects")
    @ResponseBody
    public Map partyMemberGroup_selects(Integer partyId, Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyMemberGroupExample example = new PartyMemberGroupExample();
        Criteria criteria = example.createCriteria();
        if(partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }else{
            criteria.andIdIsNull();
        }
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        int count = partyMemberGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyMemberGroup> partyMemberGroups = partyMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != partyMemberGroups && partyMemberGroups.size() > 0) {

            for (PartyMemberGroup partyMemberGroup : partyMemberGroups) {

                Select2Option option = new Select2Option();
                option.setText(partyMemberGroup.getName());
                option.setId(partyMemberGroup.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }


    /*@RequiresPermissions("partyMember:list")
    @RequestMapping("/party_member")
    public String party_member(Integer id,  Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id != null) {
            if (null == pageSize) {
                pageSize = springProps.pageSize;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            PartyMemberExample example = new PartyMemberExample();
            PartyMemberExample.Criteria criteria = example.createCriteria().andGroupIdEqualTo(id);
            example.setOrderByClause(String.format("%s %s", "sort_order", "desc"));

            int count = partyMemberMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<PartyMember> partyMembers = partyMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("partyMembers", partyMembers);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (id!=null) {
                searchStr += "&id=" + id;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);

            PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(id);
            modelMap.put("partyMemberGroup", partyMemberGroup);
        }

        return "party/partyMemberGroup/party_member";
    }

    @RequiresPermissions("partyMember:list")
    @RequestMapping("/party_member_form")
    public String party_member_form(Integer partyMemberId, ModelMap modelMap) {


        if (partyMemberId != null) {

            PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(partyMemberId);
            modelMap.put("partyMember", partyMember);
            SysUserView uv = sysUserService.findById(partyMember.getUserId());
            modelMap.put("uv", uv);
        }

        return "party/partyMemberGroup/party_member_form";
    }*/
}
