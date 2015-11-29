package controller.party;

import controller.BaseController;
import domain.BranchMember;
import domain.BranchMemberExample;
import domain.BranchMemberExample.Criteria;
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
public class BranchMemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("branchMember:list")
    @RequestMapping("/branchMember")
    public String branchMember() {

        return "index";
    }
    @RequiresPermissions("branchMember:list")
    @RequestMapping("/branchMember_page")
    public String branchMember_page(HttpServletResponse response,
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

        BranchMemberExample example = new BranchMemberExample();
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
            branchMember_export(example, response);
            return null;
        }

        int count = branchMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<BranchMember> BranchMembers = branchMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("branchMembers", BranchMembers);

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
        return "party/branchMember/branchMember_page";
    }

    @RequiresPermissions("branchMember:edit")
    @RequestMapping(value = "/branchMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMember_au(BranchMember record, HttpServletRequest request) {

        Integer id = record.getId();

        if (branchMemberService.idDuplicate(id, record.getGroupId(), record.getUserId())) {
            return failed("添加重复");
        }
        if (id == null) {

            record.setIsAdmin(false);
            branchMemberService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加基层党组织成员：%s", record.getId()));
        } else {

            branchMemberService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新基层党组织成员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchMember:edit")
    @RequestMapping("/branchMember_au")
    public String branchMember_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);
            modelMap.put("branchMember", branchMember);
        }
        return "party/branchMember/branchMember_au";
    }

    @RequiresPermissions("branchMember:del")
    @RequestMapping(value = "/branchMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMember_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            branchMemberService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除基层党组织成员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchMember:del")
    @RequestMapping(value = "/branchMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            branchMemberService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除基层党组织成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchMember:changeOrder")
    @RequestMapping(value = "/branchMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        branchMemberService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_OW, "基层党组织成员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchMember:edit")
    @RequestMapping(value = "/branchMember_admin", method = RequestMethod.POST)
    @ResponseBody
    public Map branchMember_admin(HttpServletRequest request, Integer id) {

        if (id != null) {

            BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);
            BranchMember record = new BranchMember();
            record.setId(branchMember.getId());
            record.setIsAdmin(!branchMember.getIsAdmin());
            branchMemberService.updateByPrimaryKeySelective(record);

            String op = branchMember.getIsAdmin()?"删除":"添加";
            logger.info(addLog(request, SystemConstants.LOG_OW, "%s党支部委员管理员权限，memberId=%s", op, id));
        }
        return success(FormUtils.SUCCESS);
    }

    public void branchMember_export(BranchMemberExample example, HttpServletResponse response) {

        List<BranchMember> branchMembers = branchMemberMapper.selectByExample(example);
        int rownum = branchMemberMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属支部委员会","账号","类别","是否管理员"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            BranchMember branchMember = branchMembers.get(i);
            String[] values = {
                        branchMember.getGroupId()+"",
                                            branchMember.getUserId()+"",
                                            branchMember.getTypeId()+"",
                                            branchMember.getIsAdmin()+""
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

    @RequestMapping("/branchMember_selects")
    @ResponseBody
    public Map branchMember_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchMemberExample example = new BranchMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

       /* if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }*/

        int count = branchMemberMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<BranchMember> branchMembers = branchMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != branchMembers && branchMembers.size()>0){

            for(BranchMember branchMember:branchMembers){

                Select2Option option = new Select2Option();
                //option.setText(branchMember.getName());
                option.setId(branchMember.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
