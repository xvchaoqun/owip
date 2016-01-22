package controller.party;

import controller.BaseController;
import domain.*;
import domain.BranchExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
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
    public String branch_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "ow_branch") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    String code,
                                    String name,
                                    Integer partyId,
                                    Integer typeId,
                                    Integer unitTypeId,
                                    String _foundTime,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

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
            return null;
        }

        int count = branchMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<Branch> Branchs = branchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("branchs", Branchs);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(code)) {
            searchStr += "&code=" + code;
        }
        if (StringUtils.isNotBlank(name)) {
            searchStr += "&name=" + name;
        }
        if (partyId!=null) {
            searchStr += "&partyId=" + partyId;
        }
        if (typeId!=null) {
            searchStr += "&typeId=" + typeId;
        }
        if (unitTypeId!=null) {
            searchStr += "&unitTypeId=" + unitTypeId;
        }
        if (StringUtils.isNotBlank(_foundTime)) {
            searchStr += "&_foundTime=" + _foundTime;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("typeMap", metaTypeService.metaTypes("mc_branch_type"));
        modelMap.put("staffTypeMap", metaTypeService.metaTypes("mc_branch_staff_type"));
        modelMap.put("unitTypeMap", metaTypeService.metaTypes("mc_branch_unit_type"));

        return "party/branch/branch_page";
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

        if (id == null) {
            record.setCreateTime(new Date());
            branchService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加党支部：%s", record.getId()));
        } else {

            branchService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新党支部：%s", record.getId()));
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

        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("typeMap", metaTypeService.metaTypes("mc_branch_type"));
        modelMap.put("staffTypeMap", metaTypeService.metaTypes("mc_branch_staff_type"));
        modelMap.put("unitTypeMap", metaTypeService.metaTypes("mc_branch_unit_type"));

        return "party/branch/branch_au";
    }

    @RequiresPermissions("branch:del")
    @RequestMapping(value = "/branch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            branchService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除党支部：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branch:del")
    @RequestMapping(value = "/branch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            branchService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除党支部：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branch:changeOrder")
    @RequestMapping(value = "/branch_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branch_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        branchService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_OW, "党支部调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void branch_export(BranchExample example, HttpServletResponse response) {

        List<Branch> branchs = branchMapper.selectByExample(example);
        int rownum = branchMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"编号","名称","所属党总支","类别","单位属性","联系电话","传真","邮箱","成立时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            Branch branch = branchs.get(i);
            String[] values = {
                        branch.getCode(),
                                            branch.getName(),
                                            branch.getPartyId()+"",
                                            branch.getTypeId()+"",
                                            branch.getUnitTypeId()+"",
                                            branch.getPhone(),
                                            branch.getFax(),
                                            branch.getEmail(),
                                            DateUtils.formatDate(branch.getFoundTime(), DateUtils.YYYY_MM_DD)
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "党支部_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
