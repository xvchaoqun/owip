package controller.abroad;

import controller.BaseController;
import domain.ApplicatPost;
import domain.ApplicatPostExample;
import domain.ApplicatPostExample.Criteria;
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
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ApplicatPostController extends BaseController {

   /* private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatPost")
    public String applicatPost() {

        return "index";
    }
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatPost_page")
    public String applicatPost_page(HttpServletResponse response,
                                    @SortParam(required = false, defaultValue = "sort_order", tableName = "abroad_applicat_post") String sort,
                                    @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer typeId,
                                    Integer postId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplicatPostExample example = new ApplicatPostExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (postId!=null) {
            criteria.andPostIdEqualTo(postId);
        }

        if (export == 1) {
            applicatPost_export(example, response);
            return null;
        }

        int count = applicatPostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplicatPost> applicatPosts = applicatPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("applicatPosts", applicatPosts);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (typeId!=null) {
            searchStr += "&typeId=" + typeId;
        }
        if (postId!=null) {
            searchStr += "&postId=" + postId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "abroad/applicatPost/applicatPost_page";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applicatPost_au(ApplicatPost record, HttpServletRequest request) {

        Integer id = record.getId();

        if (applicatPostService.idDuplicate(id, record.getPostId())) {
            return failed("添加重复");
        }
        if (id == null) {
            applicatPostService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "添加申请人职务属性分组：%s", record.getId()));
        } else {

            applicatPostService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "更新申请人职务属性分组：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatPost_au")
    public String applicatPost_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApplicatPost applicatPost = applicatPostMapper.selectByPrimaryKey(id);
            modelMap.put("applicatPost", applicatPost);
        }
        return "abroad/applicatPost/applicatPost_au";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatPost_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applicatPost_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            applicatPostService.del(id);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "删除申请人职务属性分组：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            applicatPostService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "批量删除申请人职务属性分组：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatPost_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applicatPost_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        applicatPostService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ABROAD, "申请人职务属性分组调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void applicatPost_export(ApplicatPostExample example, HttpServletResponse response) {

        List<ApplicatPost> applicatPosts = applicatPostMapper.selectByExample(example);
        int rownum = applicatPostMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"申请人身份","职务属性"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            ApplicatPost applicatPost = applicatPosts.get(i);
            String[] values = {
                        applicatPost.getTypeId()+"",
                                            applicatPost.getPostId()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "申请人职务属性分组_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/
}
