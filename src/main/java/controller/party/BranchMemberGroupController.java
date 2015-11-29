package controller.party;

import controller.BaseController;
import domain.*;
import domain.BranchMemberGroupExample.Criteria;
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
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class BranchMemberGroupController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("branchMemberGroup:list")
    @RequestMapping("/branchMemberGroup")
    public String branchMemberGroup() {

        return "index";
    }
    @RequiresPermissions("branchMemberGroup:list")
    @RequestMapping("/branchMemberGroup_page")
    public String branchMemberGroup_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "sort_order") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer branchId,
                                    String name,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchMemberGroupExample example = new BranchMemberGroupExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            branchMemberGroup_export(example, response);
            return null;
        }

        int count = branchMemberGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<BranchMemberGroup> BranchMemberGroups = branchMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("branchMemberGroups", BranchMemberGroups);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (branchId!=null) {
            searchStr += "&branchId=" + branchId;
        }
        if (StringUtils.isNotBlank(name)) {
            searchStr += "&name=" + name;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("branchMap", branchService.findAll());
        modelMap.put("dispatchUnitMap", dispatchUnitService.findAll());
        modelMap.put("dispatchMap", dispatchService.findAll());

        return "party/branchMemberGroup/branchMemberGroup_page";
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
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加支部委员会：%s", record.getId()));
        } else {

            if(record.getFid()!=null && record.getFid().intValue()==record.getId()){
                return failed("不能选择自身为上一届委员会");
            }

            branchMemberGroupService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新支部委员会：%s", record.getId()));
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
                if(dispatchUnit!= null)
                    modelMap.put("dispatch", dispatchService.findAll().get(dispatchUnit.getDispatchId()));
            }
        }else{
            if(branchId == null) throw  new IllegalArgumentException("参数错误");
            Branch branch = branchMap.get(branchId);
            modelMap.put("branch", branch);
            modelMap.put("party", partyMap.get(branch.getPartyId()));
        }
        return "party/branchMemberGroup/branchMemberGroup_au";
    }

    @RequiresPermissions("branchMemberGroup:del")
    @RequestMapping(value = "/branchMemberGroup_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMemberGroup_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            branchMemberGroupService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除支部委员会：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchMemberGroup:del")
    @RequestMapping(value = "/branchMemberGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            branchMemberGroupService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除支部委员会：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchMemberGroup:changeOrder")
    @RequestMapping(value = "/branchMemberGroup_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMemberGroup_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        branchMemberGroupService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_OW, "支部委员会调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void branchMemberGroup_export(BranchMemberGroupExample example, HttpServletResponse response) {

        List<BranchMemberGroup> branchMemberGroups = branchMemberGroupMapper.selectByExample(example);
        int rownum = branchMemberGroupMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属党支部","应换届时间","实际换届时间","任命时间","发文"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            BranchMemberGroup branchMemberGroup = branchMemberGroups.get(i);
            String[] values = {
                        branchMemberGroup.getBranchId()+"",
                                            DateUtils.formatDate(branchMemberGroup.getTranTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(branchMemberGroup.getActualTranTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(branchMemberGroup.getAppointTime(), DateUtils.YYYY_MM_DD),
                                            branchMemberGroup.getDispatchUnitId()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "支部委员会_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("/branchMemberGroup_selects")
    @ResponseBody
    public Map branchMemberGroup_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

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
