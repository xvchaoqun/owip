package controller.party;

import controller.BaseController;
import domain.*;
import domain.PartyMemberGroupExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.PartyMemberGroupMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
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
import sys.constants.SystemConstants;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class PartyMemberGroupController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("partyMemberGroup:list")
    @RequestMapping("/partyMemberGroup")
    public String partyMemberGroup() {

        return "index";
    }

    @RequiresPermissions("partyMemberGroup:list")
    @RequestMapping("/partyMemberGroup_view")
    public String partyMemberGroup_view(
                                        @SortParam(required = false, defaultValue = "sort_order", tableName = "ow_party_member_group") String sort,
                                        @OrderParam(required = false, defaultValue = "desc") String order,
                                        Integer partyId, ModelMap modelMap) {

        PartyMemberGroupExample example = new PartyMemberGroupExample();
        example.createCriteria().andPartyIdEqualTo(partyId);
        example.setOrderByClause(String.format("%s %s", sort, order));
        List<PartyMemberGroup> PartyMemberGroups = partyMemberGroupMapper.selectByExample(example);
        modelMap.put("partyMemberGroups", PartyMemberGroups);
        return "party/partyMemberGroup/partyMemberGroup_view";
    }

    @RequiresPermissions("partyMemberGroup:list")
    @RequestMapping("/partyMemberGroup_page")
    public String partyMemberGroup_page(HttpServletResponse response,
                                        String name,
                                        Integer partyId,
                                        Integer pageSize, Integer pageNo, ModelMap modelMap) {


        return "party/partyMemberGroup/partyMemberGroup_page";
    }
    @RequiresPermissions("partyMemberGroup:list")
    @RequestMapping("/partyMemberGroup_data")
    public void partyMemberGroup_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "ow_party_member_group") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    String name,
                                    Integer partyId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyMemberGroupExample example = new PartyMemberGroupExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if(partyId!=null){
            criteria.andPartyIdEqualTo(partyId);
        }

        if (export == 1) {
            partyMemberGroup_export(example, response);
            return;
        }

        int count = partyMemberGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyMemberGroup> PartyMemberGroups = partyMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", PartyMemberGroups);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(PartyMemberGroup.class, PartyMemberGroupMixin.class);
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

    @RequiresPermissions("partyMemberGroup:del")
    @RequestMapping(value = "/partyMemberGroup_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMemberGroup_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            partyMemberGroupService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除基层党组织领导班子：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMemberGroup:del")
    @RequestMapping(value = "/partyMemberGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partyMemberGroupService.batchDel(ids);
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

    public void partyMemberGroup_export(PartyMemberGroupExample example, HttpServletResponse response) {

        List<PartyMemberGroup> partyMemberGroups = partyMemberGroupMapper.selectByExample(example);
        int rownum = partyMemberGroupMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"名称","是否现任班子","应换届时间","实际换届时间","任命时间","发文"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            PartyMemberGroup partyMemberGroup = partyMemberGroups.get(i);
            String[] values = {
                        partyMemberGroup.getName(),
                                            partyMemberGroup.getIsPresent()+"",
                                            DateUtils.formatDate(partyMemberGroup.getTranTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(partyMemberGroup.getActualTranTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(partyMemberGroup.getAppointTime(), DateUtils.YYYY_MM_DD),
                                            partyMemberGroup.getDispatchUnitId()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "基层党组织领导班子_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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


    @RequiresPermissions("partyMember:list")
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
}
