package controller.abroad;

import domain.abroad.ApplicatType;
import domain.abroad.ApplicatTypeExample;
import domain.abroad.ApplicatTypeExample.Criteria;
import domain.abroad.ApprovalOrder;
import domain.cadre.CadreExample;
import domain.cadre.CadreView;
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
import sys.constants.AbroadConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/abroad")
public class ApplicatTypeController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 查询还没有分配申请人身份的干部
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType/selectCadresEscape_tree")
    @ResponseBody
    public Map selectCadresEscape_tree() throws IOException {

        Set<Integer> hasAssignedCadreIdSet = applicatTypeService.getCadreIds(null);
        Set<CadreView> cadreSet = new LinkedHashSet<>();
        Set<Integer> disabledIdSet = new HashSet<>(); // 显示时不需要选择框
        for (CadreView cadre : cadreService.findAll().values()) {

            if(!hasAssignedCadreIdSet.contains(cadre.getId().intValue())){
                cadreSet.add(cadre);
                disabledIdSet.add(cadre.getId());
            }
        }

        TreeNode tree = cadreCommonService.getTree(cadreSet,
                AbroadConstants.ABROAD_APPLICAT_CADRE_STATUS_SET, null, disabledIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    // 查询还没有分配申请人身份的干部
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType/selectCadresEscape")
    public String selectCadresEscape() throws IOException {

        return "abroad/applicatType/selectCadresEscape";
    }

    // 查看某类申请人身份下的干部
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType/selectCadres")
    public String selectCadres(int id, ModelMap modelMap) throws IOException {

        ApplicatType applicatType = applicatTypeMapper.selectByPrimaryKey(id);
        modelMap.put("applicatType", applicatType);
        return "abroad/applicatType/selectCadres";
    }

    // 查看某类申请人身份下的干部
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType/selectCadres_tree")
    @ResponseBody
    public Map selectCadres_tree(int id) throws IOException {

        Set<Integer> selectIdSet = applicatTypeService.getCadreIds(id);
        Set<Integer> disabledIdSet = applicatTypeService.getCadreIds(null);
        disabledIdSet.removeAll(selectIdSet);
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<CadreView>(cadreService.findAll().values()),
                AbroadConstants.ABROAD_APPLICAT_CADRE_STATUS_SET, selectIdSet, disabledIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    // 更新某类申请人身份下的干部
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatType/selectCadres", method = RequestMethod.POST)
    @ResponseBody
    public Map do_selectCadres(Integer id, @RequestParam(value = "cadreIds[]", required = false) Integer[] cadreIds) {

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

        int count = iAbroadMapper.countApprovalOrders(id);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApprovalOrder> approvalOrders = iAbroadMapper.selectApprovalOrderList(id,
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
        logger.info(addLog(LogConstants.LOG_ABROAD, "申请人身份关联的审批顺序调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/applicatType")
    public String applicatType(HttpServletResponse response,
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
        cadreExample.createCriteria().andStatusIn(new ArrayList<>(AbroadConstants.ABROAD_APPLICAT_CADRE_STATUS_SET));
        modelMap.put("escapeCount", cadreMapper.countByExample(cadreExample)- applicatTypeService.getCadreIds(null).size());

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
            logger.info(addLog(LogConstants.LOG_ABROAD, "添加申请人身份：%s", record.getId()));
        } else {

            applicatTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "更新申请人身份：%s", record.getId()));
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
            logger.info(addLog(LogConstants.LOG_ABROAD, "删除申请人身份：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            applicatTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ABROAD, "批量删除申请人身份：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/applicatType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applicatType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        applicatTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ABROAD, "申请人身份调序：%s,%s", id, addNum));
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
        String fileName = "申请人身份_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
