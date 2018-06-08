package controller.cla;

import domain.cadre.CadreExample;
import domain.cadre.CadreView;
import domain.cla.ClaApplicatType;
import domain.cla.ClaApplicatTypeExample;
import domain.cla.ClaApprovalOrder;
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
import sys.constants.ClaConstants;
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
@RequestMapping("/cla")
public class ClaApplicatTypeController extends ClaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 查询还没有分配申请人身份的干部
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/claApplicatType/selectCadresEscape_tree")
    @ResponseBody
    public Map selectCadresEscape_tree() throws IOException {

        Set<Integer> hasAssignedCadreIdSet = claApplicatTypeService.getCadreIds(null);
        Set<CadreView> cadreSet = new LinkedHashSet<>();
        Set<Integer> disabledIdSet = new HashSet<>(); // 显示时不需要选择框
        for (CadreView cadre : cadreService.findAll().values()) {

            if(!hasAssignedCadreIdSet.contains(cadre.getId().intValue())){
                cadreSet.add(cadre);
                disabledIdSet.add(cadre.getId());
            }
        }

        TreeNode tree = cadreCommonService.getTree(cadreSet,
                ClaConstants.CLA_APPLICAT_CADRE_STATUS_SET, null, disabledIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    // 查询还没有分配申请人身份的干部
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/claApplicatType/selectCadresEscape")
    public String selectCadresEscape() throws IOException {

        return "cla/claApplicatType/selectCadresEscape";
    }

    // 查看某类申请人身份下的干部
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/claApplicatType/selectCadres")
    public String selectCadres(int id, ModelMap modelMap) throws IOException {

        ClaApplicatType applicatType = claApplicatTypeMapper.selectByPrimaryKey(id);
        modelMap.put("applicatType", applicatType);
        return "cla/claApplicatType/selectCadres";
    }

    // 查看某类申请人身份下的干部
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/claApplicatType/selectCadres_tree")
    @ResponseBody
    public Map selectCadres_tree(int id) throws IOException {

        Set<Integer> selectIdSet = claApplicatTypeService.getCadreIds(id);
        Set<Integer> disabledIdSet = claApplicatTypeService.getCadreIds(null);
        disabledIdSet.removeAll(selectIdSet);
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<CadreView>(cadreService.findAll().values()),
                ClaConstants.CLA_APPLICAT_CADRE_STATUS_SET, selectIdSet, disabledIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    // 更新某类申请人身份下的干部
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/claApplicatType/selectCadres", method = RequestMethod.POST)
    @ResponseBody
    public Map do_selectCadres(Integer id, @RequestParam(value = "cadreIds[]", required = false) Integer[] cadreIds) {

        claApplicatTypeService.updateCadreIds(id, cadreIds);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/claApplicatType/claApprovalOrder")
    public String claApprovalOrder(int id, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int count = iClaMapper.countApprovalOrders(id);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ClaApprovalOrder> approvalOrders = iClaMapper.selectApprovalOrderList(id,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("approvalOrders", approvalOrders);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;
        searchStr += "&id=" + id;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        ClaApplicatType applicatType = claApplicatTypeMapper.selectByPrimaryKey(id);
        modelMap.put("applicatType", applicatType);

        return "cla/claApplicatType/claApproval_order";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/claApplicatType/claApprovalOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApprovalOrder(Integer approverTypeId, Integer applicatTypeId) {

        if (claApprovalOrderService.idDuplicate(null, applicatTypeId, approverTypeId)) {
            return failed("添加重复");
        }

        ClaApprovalOrder record = new ClaApprovalOrder();
        record.setApproverTypeId(approverTypeId);
        record.setApplicatTypeId(applicatTypeId);
        claApprovalOrderService.insertSelective(record);

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/claApplicatType/claApprovalOrder_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApprovalOrder_del(int id) {

        claApprovalOrderService.del(id);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/claApplicatType/claApprovalOrder_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApprovalOrder_changeOrder(int applicatTypeId, Integer id, Integer addNum, HttpServletRequest request) {

        claApprovalOrderService.changeOrder(applicatTypeId, id, addNum);
        logger.info(addLog(LogConstants.LOG_CLA, "申请人身份关联的审批顺序调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/claApplicatType")
    public String claApplicatType(HttpServletResponse response,
                                        @SortParam(required = false, defaultValue = "sort_order", tableName = "cla_applicat_type") String sort,
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

        ClaApplicatTypeExample example = new ClaApplicatTypeExample();
        ClaApplicatTypeExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (name != null) {
            criteria.andNameEqualTo(name);
        }

        if (export == 1) {
            applicatType_export(example, response);
            return null;
        }

        long count = claApplicatTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ClaApplicatType> applicatTypes = claApplicatTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
        cadreExample.createCriteria().andStatusIn(new ArrayList<>(ClaConstants.CLA_APPLICAT_CADRE_STATUS_SET));
        modelMap.put("escapeCount", cadreMapper.countByExample(cadreExample)- claApplicatTypeService.getCadreIds(null).size());

        return "cla/claApplicatType/claApplicatType_page";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/claApplicatType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApplicatType_au(ClaApplicatType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (claApplicatTypeService.idDuplicate(id, record.getName())) {
            return failed("添加重复");
        }
        if (id == null) {
            claApplicatTypeService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CLA, "添加申请人身份：%s", record.getId()));
        } else {

            claApplicatTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CLA, "更新申请人身份：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/claApplicatType_au")
    public String claApplicatType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ClaApplicatType applicatType = claApplicatTypeMapper.selectByPrimaryKey(id);
            modelMap.put("applicatType", applicatType);
        }
        return "cla/claApplicatType/claApplicatType_au";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/claApplicatType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApplicatType_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            claApplicatTypeService.del(id);
            logger.info(addLog(LogConstants.LOG_CLA, "删除申请人身份：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/claApplicatType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map claApplicatType_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            claApplicatTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CLA, "批量删除申请人身份：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/claApplicatType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApplicatType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        claApplicatTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CLA, "申请人身份调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void applicatType_export(ClaApplicatTypeExample example, HttpServletResponse response) {

        List<ClaApplicatType> applicatTypes = claApplicatTypeMapper.selectByExample(example);
        long rownum = claApplicatTypeMapper.countByExample(example);

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

            ClaApplicatType applicatType = applicatTypes.get(i);
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
