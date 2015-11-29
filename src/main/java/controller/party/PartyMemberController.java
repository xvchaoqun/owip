package controller.party;

import controller.BaseController;
import domain.PartyMember;
import domain.PartyMemberExample;
import domain.PartyMemberExample.Criteria;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class PartyMemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("partyMember:list")
    @RequestMapping("/partyMember")
    public String partyMember() {

        return "index";
    }
    @RequiresPermissions("partyMember:list")
    @RequestMapping("/partyMember_page")
    public String partyMember_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "sort_order") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer groupId,
                                    Integer userId,
                                    Integer typeId,
                                    Boolean isAdmin,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyMemberExample example = new PartyMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (groupId!=null) {
            criteria.andGroupIdEqualTo(groupId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (isAdmin!=null) {
            criteria.andIsAdminEqualTo(isAdmin);
        }

        if (export == 1) {
            partyMember_export(example, response);
            return null;
        }

        int count = partyMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyMember> PartyMembers = partyMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("partyMembers", PartyMembers);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (groupId!=null) {
            searchStr += "&groupId=" + groupId;
        }
        if (userId!=null) {
            searchStr += "&userId=" + userId;
        }
        if (typeId!=null) {
            searchStr += "&typeId=" + typeId;
        }
        if (isAdmin!=null) {
            searchStr += "&isAdmin=" + isAdmin;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("partyMemberGroupMap", partyMemberGroupService.findAll());
        modelMap.put("typeMap", metaTypeService.metaTypes("mc_party_member_type"));

        return "party/partyMember/partyMember_page";
    }

    @RequiresPermissions("partyMember:edit")
    @RequestMapping(value = "/partyMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMember_au(PartyMember record, HttpServletRequest request) {

        Integer id = record.getId();

        if (partyMemberService.idDuplicate(id, record.getGroupId(), record.getUserId())) {
            return failed("添加重复");
        }
        if (id == null) {

            record.setIsAdmin(false);
            partyMemberService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加基层党组织成员：%s", record.getId()));
        } else {

            partyMemberService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新基层党组织成员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:edit")
    @RequestMapping("/partyMember_au")
    public String partyMember_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
            modelMap.put("partyMember", partyMember);
        }

        modelMap.put("partyMemberGroupMap", partyMemberGroupService.findAll());
        modelMap.put("typeMap", metaTypeService.metaTypes("mc_party_member_type"));

        return "party/partyMember/partyMember_au";
    }

    @RequiresPermissions("partyMember:edit")
    @RequestMapping(value = "/partyMember_admin", method = RequestMethod.POST)
    @ResponseBody
    public Map partyMember_admin(HttpServletRequest request, Integer id) {

        if (id != null) {

            PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
            PartyMember record = new PartyMember();
            record.setId(partyMember.getId());
            record.setIsAdmin(!partyMember.getIsAdmin());
            partyMemberService.updateByPrimaryKeySelective(record);

            String op = partyMember.getIsAdmin()?"删除":"添加";
            logger.info(addLog(request, SystemConstants.LOG_OW, "%s基层党组织成员管理员权限，memberId=%s", op, id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:del")
    @RequestMapping(value = "/partyMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMember_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            partyMemberService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除基层党组织成员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:del")
    @RequestMapping(value = "/partyMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partyMemberService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除基层党组织成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyMember:changeOrder")
    @RequestMapping(value = "/partyMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        partyMemberService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_OW, "基层党组织成员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void partyMember_export(PartyMemberExample example, HttpServletResponse response) {

        List<PartyMember> partyMembers = partyMemberMapper.selectByExample(example);
        int rownum = partyMemberMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属班子","账号","类别","是否管理员"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            PartyMember partyMember = partyMembers.get(i);
            String[] values = {
                        partyMember.getGroupId()+"",
                                            partyMember.getUserId()+"",
                                            partyMember.getTypeId()+"",
                                            partyMember.getIsAdmin()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "基层党组织成员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("/partyMember_selects")
    @ResponseBody
    public Map partyMember_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyMemberExample example = new PartyMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }*/

        int count = partyMemberMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PartyMember> partyMembers = partyMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != partyMembers && partyMembers.size()>0){

            for(PartyMember partyMember:partyMembers){

                Select2Option option = new Select2Option();
                //option.setText(partyMember.getName());
                option.setId(partyMember.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
