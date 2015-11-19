package controller;

import domain.Party;
import domain.PartyExample;
import domain.PartyExample.Criteria;
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
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class PartyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("party:list")
    @RequestMapping("/party")
    public String party() {

        return "index";
    }
    @RequiresPermissions("party:list")
    @RequestMapping("/party_page")
    public String party_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "sort_order") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    String code,
                                    String name,
                                    Integer unitId,
                                    Integer classId,
                                    Integer typeId,
                                    Integer unitTypeId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyExample example = new PartyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

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

        if (export == 1) {
            party_export(example, response);
            return null;
        }

        int count = partyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Party> Partys = partyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("partys", Partys);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(code)) {
            searchStr += "&code=" + code;
        }
        if (StringUtils.isNotBlank(name)) {
            searchStr += "&name=" + name;
        }
        if (unitId!=null) {
            searchStr += "&unitId=" + unitId;
        }
        if (classId!=null) {
            searchStr += "&classId=" + classId;
        }
        if (typeId!=null) {
            searchStr += "&typeId=" + typeId;
        }
        if (unitTypeId!=null) {
            searchStr += "&unitTypeId=" + unitTypeId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("unitMap", unitService.findAll());
        modelMap.put("classMap", metaTypeService.metaTypes("mc_party_class"));
        modelMap.put("typeMap", metaTypeService.metaTypes("mc_part_type"));
        modelMap.put("unitTypeMap", metaTypeService.metaTypes("mc_party_unit_type"));
        return "party/party_page";
    }

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
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加基层党组织：%s", record.getId()));
        } else {

            partyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新基层党组织：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("party:edit")
    @RequestMapping("/party_au")
    public String party_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Party party = partyMapper.selectByPrimaryKey(id);
            modelMap.put("party", party);
        }

        modelMap.put("unitMap", unitService.findAll());
        modelMap.put("classMap", metaTypeService.metaTypes("mc_party_class"));
        modelMap.put("typeMap", metaTypeService.metaTypes("mc_part_type"));
        modelMap.put("unitTypeMap", metaTypeService.metaTypes("mc_party_unit_type"));

        return "party/party_au";
    }

    @RequiresPermissions("party:del")
    @RequestMapping(value = "/party_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_party_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            partyService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除基层党组织：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("party:del")
    @RequestMapping(value = "/party_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partyService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除基层党组织：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("party:changeOrder")
    @RequestMapping(value = "/party_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_party_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        partyService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_OW, "基层党组织调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void party_export(PartyExample example, HttpServletResponse response) {

        List<Party> partys = partyMapper.selectByExample(example);
        int rownum = partyMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"编号","名称","简称","网址","所属单位","党总支类别","组织类别","所在单位属性","联系电话","邮箱"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            Party party = partys.get(i);
            String[] values = {
                        party.getCode(),
                                            party.getName(),
                                            party.getShortName(),
                                            party.getUrl(),
                                            party.getUnitId()+"",
                                            party.getClassId()+"",
                                            party.getTypeId()+"",
                                            party.getUnitTypeId()+"",
                                            party.getPhone(),
                                            party.getEmail()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "基层党组织_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("/party_selects")
    @ResponseBody
    public Map party_selects(Integer pageSize, Integer pageNo, Integer classId, String searchStr) throws IOException {

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

        if(classId!=null) criteria.andClassIdEqualTo(classId);

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
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
            options.add(option);
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
