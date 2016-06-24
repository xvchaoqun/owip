package controller.abroad;

import controller.BaseController;
import domain.*;
import domain.ApplicatTypeExample.Criteria;
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
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class ApplicatTypeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType/selectCadresEscape_tree")
    @ResponseBody
    public Map selectCadresEscapse_tree() throws IOException {

        Set<Integer> disabledIdSet = applicatTypeService.getCadreIds(null);
        Set<Cadre> cadreSet = new HashSet<>();
        Set<Integer> cadreIdSet = new HashSet<>();
        for (Cadre cadre : cadreService.findAll().values()) {

            if(!disabledIdSet.contains(cadre.getId().intValue())){
                cadreSet.add(cadre);
                cadreIdSet.add(cadre.getId());
            }
        }

        TreeNode tree = cadreService.getTree(cadreSet, null, cadreIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType/selectCadresEscape")
    public String selectCadres_escapse() throws IOException {

        return "abroad/applicatType/selectCadresEscape";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType/selectCadres_tree")
    @ResponseBody
    public Map selectCadres_tree(int id) throws IOException {

        Set<Integer> selectIdSet = applicatTypeService.getCadreIds(id);
        Set<Integer> disabledIdSet = applicatTypeService.getCadreIds(null);
        disabledIdSet.removeAll(selectIdSet);
        TreeNode tree = cadreService.getTree(new HashSet<Cadre>(cadreService.findAll().values()), selectIdSet, disabledIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType/selectCadres")
    public String select_posts(int id, ModelMap modelMap) throws IOException {

        ApplicatType applicatType = applicatTypeMapper.selectByPrimaryKey(id);
        modelMap.put("applicatType", applicatType);
        return "abroad/applicatType/selectCadres";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatType/selectCadres", method = RequestMethod.POST)
    @ResponseBody
    public Map do_select_posts(Integer id, @RequestParam(value = "cadreIds[]", required = false) Integer[] cadreIds) {

        applicatTypeService.updateCadreIds(id, cadreIds);
        return success(FormUtils.SUCCESS);
    }


    /*@RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType/selectPosts_tree")
    @ResponseBody
    public Map selectPosts_tree(int id) throws IOException {

        Set<Integer> selectIdSet = applicatTypeService.getPostIds(id);
        Set<Integer> disabledIdSet = applicatTypeService.getPostIds(null);
        disabledIdSet.removeAll(selectIdSet);
        TreeNode tree = metaTypeService.getTree("mc_post", selectIdSet, disabledIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType/select_posts")
    public String select_posts(int id, ModelMap modelMap) throws IOException {

        ApplicatType applicatType = applicatTypeMapper.selectByPrimaryKey(id);
        modelMap.put("applicatType", applicatType);
        return "abroad/applicatType/select_posts";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatType/select_posts", method = RequestMethod.POST)
    @ResponseBody
    public Map do_select_posts(Integer id, @RequestParam(value = "postIds[]", required = false) Integer[] postIds) {

        applicatTypeService.updatePostIds(id, postIds);
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType/approvalOrder")
    public String approvalOrder(int id, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int count = selectMapper.countApprovalOrders(id);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApprovalOrder> approvalOrders = selectMapper.selectApprovalOrderList(id,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("approvalOrders", approvalOrders);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;
        searchStr += "&id=" + id;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        ApplicatType applicatType = applicatTypeMapper.selectByPrimaryKey(id);
        modelMap.put("applicatType", applicatType);

        return "abroad/applicatType/approval_order";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatType/approvalOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approvalOrder(Integer approverTypeId, Integer applicatTypeId) {

        if (approvalOrderService.idDuplicate(null, applicatTypeId, approverTypeId)) {
            return failed("添加重复");
        }

        ApprovalOrder record = new ApprovalOrder();
        record.setApproverTypeId(approverTypeId);
        record.setApplicatTypeId(applicatTypeId);
        approvalOrderService.insertSelective(record);

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatType/approvalOrder_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approvalOrder_del(int id) {

        approvalOrderService.del(id);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatType/approvalOrder_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approvalOrder_changeOrder(int applicatTypeId, Integer id, Integer addNum, HttpServletRequest request) {

        approvalOrderService.changeOrder(applicatTypeId, id, addNum);
        logger.info(addLog(SystemConstants.LOG_ABROAD, "申请人身份关联的审批顺序调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType")
    public String applicatType() {

        return "index";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType_page")
    public String applicatType_page(HttpServletResponse response,
                                        @SortParam(required = false, defaultValue = "sort_order", tableName = "abroad_applicat_type") String sort,
                                        @OrderParam(required = false, defaultValue = "desc") String order,
                                        @RequestParam(defaultValue = "1") int cls,
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

        ApplicatTypeExample example = new ApplicatTypeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (name != null) {
            criteria.andNameEqualTo(name);
        }

        if (export == 1) {
            applicatType_export(example, response);
            return null;
        }

        int count = applicatTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplicatType> applicatTypes = applicatTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("applicatTypes", applicatTypes);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (name != null) {
            searchStr += "&name=" + name;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }

        modelMap.put("cls", cls);
        searchStr += "&cls=" + cls;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        CadreExample cadreExample = new CadreExample();
        cadreExample.createCriteria().andStatusEqualTo(SystemConstants.CADRE_STATUS_NOW);
        modelMap.put("escapeCount", cadreMapper.countByExample(cadreExample)- applicatCadreMapper.countByExample(new ApplicatCadreExample()));


        return "abroad/applicatType/applicatType_page";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applicatType_au(ApplicatType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (applicatTypeService.idDuplicate(id, record.getName())) {
            return failed("添加重复");
        }
        if (id == null) {
            applicatTypeService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "添加申请人身份：%s", record.getId()));
        } else {

            applicatTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "更新申请人身份：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType_au")
    public String applicatType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApplicatType applicatType = applicatTypeMapper.selectByPrimaryKey(id);
            modelMap.put("applicatType", applicatType);
        }
        return "abroad/applicatType/applicatType_au";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applicatType_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            applicatTypeService.del(id);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "删除申请人身份：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            applicatTypeService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "批量删除申请人身份：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applicatType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        applicatTypeService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ABROAD, "申请人身份调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void applicatType_export(ApplicatTypeExample example, HttpServletResponse response) {

        List<ApplicatType> applicatTypes = applicatTypeMapper.selectByExample(example);
        int rownum = applicatTypeMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"申请人身份"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            ApplicatType applicatType = applicatTypes.get(i);
            String[] values = {
                    applicatType.getName() + ""
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "申请人身份_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
