package controller.party;

import controller.BaseController;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchUnit;
import domain.party.*;
import domain.party.PartyMemberGroupExample.Criteria;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.constants.SystemConstants;
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
    public String partyMemberGroup(@RequestParam(required = false, defaultValue = "1")Byte status,
                                        @RequestParam(required = false, value = "typeIds")Integer[] typeIds,
                                        ModelMap modelMap) {

        modelMap.put("status", status);
        if(status==2){
            if (typeIds!=null) {
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

                                      @RequestParam(required = false, defaultValue = "1")Byte status,
                                    String name,
                                    Integer partyId,
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
        example.setOrderByClause("party_sort_order desc");

        criteria.andIsDeletedEqualTo(status==-1);

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if(partyId!=null){
            criteria.andPartyIdEqualTo(partyId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            partyMemberGroup_export(example, response);
            return;
        }

        int count = partyMemberGroupViewMapper.countByExample(example);
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

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        JSONUtils.jsonp(resultMap, sourceMixins);
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
            partyMemberGroupService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "添加基层党组织领导班子：%s", record.getId()));
        } else {

            if(record.getFid()!=null && record.getFid().intValue()==record.getId()){
                return failed("不能选择自身为上一届班子");
            }

            partyMemberGroupService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新基层党组织领导班子：%s", record.getId()));
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
            if(partyMemberGroup.getFid()!=null){
                modelMap.put("fPartyMemberGroup", partyMemberGroupMapper.selectByPrimaryKey(partyMemberGroup.getFid()));
            }

            Party party = partyMap.get(partyMemberGroup.getPartyId());
            modelMap.put("party", party);
            Integer dispatchUnitId = partyMemberGroup.getDispatchUnitId();
            if(dispatchUnitId != null) {
                DispatchUnit dispatchUnit = dispatchUnitMapper.selectByPrimaryKey(dispatchUnitId);
                if(dispatchUnit!= null)
                    modelMap.put("dispatch", dispatchService.findAll().get(dispatchUnit.getDispatchId()));
            }
        }else{
            if(partyId == null) throw  new IllegalArgumentException("参数错误");
            Party party = partyMap.get(partyId);
            modelMap.put("party", party);
        }

        modelMap.put("dispatchUnitMap", dispatchUnitService.findAll());
        modelMap.put("dispatchMap", dispatchService.findAll());

        return "party/partyMemberGroup/partyMemberGroup_au";
    }

    /*@RequiresPermissions("partyMemberGroup:del")
    @RequestMapping(value = "/partyMemberGroup_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMemberGroup_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            partyMemberGroupService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除基层党组织领导班子：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("partyMemberGroup:del")
    @RequestMapping(value = "/partyMemberGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        @RequestParam(required = false, defaultValue = "1")boolean isDeleted,
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partyMemberGroupService.batchDel(ids, isDeleted);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除基层党组织领导班子：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMemberGroup:changeOrder")
    @RequestMapping(value = "/partyMemberGroup_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMemberGroup_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        partyMemberGroupService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_OW, "基层党组织领导班子调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void partyMemberGroup_export(PartyMemberGroupViewExample example, HttpServletResponse response) {

        List<PartyMemberGroupView> records = partyMemberGroupViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"名称","所属分党委", "是否现任班子","应换届时间","实际换届时间","任命时间","发文"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PartyMemberGroupView record = records.get(i);
            Integer partyId = record.getPartyId();

            String dispatchCode = "";
            DispatchUnit dispatchUnit = dispatchUnitService.findAll().get(record.getDispatchUnitId());
            if(dispatchUnit!=null) {
                Dispatch dispatch = dispatchUnit.getDispatch();
               if(dispatch!=null)
                   dispatchCode = CmTag.getDispatchCode(dispatch.getCode(), dispatch.getDispatchTypeId(), dispatch.getYear());
            }
            String[] values = {
                    record.getName(),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    BooleanUtils.isTrue(record.getIsPresent())?"是":"否",
                    DateUtils.formatDate(record.getTranTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getActualTranTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getAppointTime(), DateUtils.YYYY_MM_DD),
                    dispatchCode
            };
            valuesList.add(values);
        }
        String fileName = "领导班子_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/partyMemberGroup_selects")
    @ResponseBody
    public Map partyMemberGroup_selects(int partyId, Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyMemberGroupExample example = new PartyMemberGroupExample();
        Criteria criteria = example.createCriteria().andPartyIdEqualTo(partyId);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = partyMemberGroupMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PartyMemberGroup> partyMemberGroups = partyMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != partyMemberGroups && partyMemberGroups.size()>0){

            for(PartyMemberGroup partyMemberGroup:partyMemberGroups){

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
