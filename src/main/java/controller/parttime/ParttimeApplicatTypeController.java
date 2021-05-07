package controller.parttime;

import domain.cadre.Cadre;
import domain.cadre.CadreExample;
import domain.parttime.ParttimeApplicatType;
import domain.parttime.ParttimeApplicatTypeExample;
import domain.parttime.ParttimeApprovalOrder;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.constants.ParttimeConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class ParttimeApplicatTypeController extends ParttimeBaseController {

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApplicatType")
    public String parttimeApplicatType(HttpServletResponse response,
                                           @SortParam(required = false, defaultValue = "sort_order", tableName = "parttime_applicat_type") String sort,
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

        ParttimeApplicatTypeExample example = new ParttimeApplicatTypeExample();
        ParttimeApplicatTypeExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (name != null) {
            criteria.andNameEqualTo(name);
        }

        if (export == 1) {
//            applicatType_export(example, response);
            return null;
        }

        long count = parttimeApplicatTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ParttimeApplicatType> applicatTypes = parttimeApplicatTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
        cadreExample.createCriteria().andStatusIn(new ArrayList<>(ParttimeConstants.PARTTIME_APPLICAT_CADRE_STATUS_SET));
        modelMap.put("escapeCount", cadreMapper.countByExample(cadreExample)- parttimeApplicatTypeService.getCadreIds(null).size());

        return "parttime/parttimeApplicatType/parttimeApplicatType_page";
    }

    // 查看某类申请人身份下的干部
    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApplicatType/selectCadres")
    public String selectCadres(int id, ModelMap modelMap) throws IOException {

        ParttimeApplicatType applicatType = parttimeApplicatTypeMapper.selectByPrimaryKey(id);
        modelMap.put("applicatType", applicatType);
        return "parttime/parttimeApplicatType/selectCadres";
    }

    // 更新某类申请人身份下的干部
    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttimeApplicatType/selectCadres", method = RequestMethod.POST)
    @ResponseBody
    public Map selectCadres(Integer id, Integer[] cadreIds) {

        parttimeApplicatTypeService.updateCadreIds(id, cadreIds);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApplicatType_au")
    public String parttimeApplicatType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ParttimeApplicatType parttimeApplicatType = parttimeApplicatTypeMapper.selectByPrimaryKey(id);
            modelMap.put("applicatType", parttimeApplicatType);
        }
        return "parttime/parttimeApplicatType/parttimeApplicatType_au";
    }

    // 查询还没有分配申请人身份的干部
    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApplicatType/selectCadresEscape")
    public String selectCadresEscape() throws IOException {

        return "parttime/parttimeApplicatType/selectCadresEscape";
    }

    // 查询还没有分配申请人身份的干部
    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApplicatType/selectCadresEscape_tree")
    @ResponseBody
    public Map selectCadresEscape_tree() throws IOException {

        Set<Integer> hasAssignedCadreIdSet = parttimeApplicatTypeService.getCadreIds(null);
        Set<Cadre> cadreSet = new LinkedHashSet<>();
        Set<Integer> disabledIdSet = new HashSet<>(); // 显示时不需要选择框
        for (Cadre cadre : cadreService.getCadres()) {

            if(!hasAssignedCadreIdSet.contains(cadre.getId().intValue())){
                cadreSet.add(cadre);
                disabledIdSet.add(cadre.getId());
            }
        }

        TreeNode tree = cadreCommonService.getTree(cadreSet,
                ParttimeConstants.PARTTIME_APPLICAT_CADRE_STATUS_SET, null, disabledIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttimeApplicatType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApplicatType_au(ParttimeApplicatType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (parttimeApplicatTypeService.idDuplicate(id, record.getName())) {
            return failed("添加重复");
        }
        if (id == null) {
            parttimeApplicatTypeService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "添加申请人身份：%s", record.getId()));
        } else {

            parttimeApplicatTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "更新申请人身份：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttimeApplicatType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApplicatType_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            parttimeApplicatTypeService.del(id);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "删除申请人身份：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttimeApplicatType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApplicatType_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            parttimeApplicatTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "批量删除申请人身份：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttimeApplicatType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApplicatType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        parttimeApplicatTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "申请人身份调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    // 查看某类申请人身份下的干部
    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApplicatType/selectCadres_tree")
    @ResponseBody
    public Map selectCadres_tree(int id) throws IOException {

        Set<Integer> selectIdSet = parttimeApplicatTypeService.getCadreIds(id);
        Set<Integer> disabledIdSet = parttimeApplicatTypeService.getCadreIds(null);
        disabledIdSet.removeAll(selectIdSet);
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<Cadre>(cadreService.getCadres()),
                ParttimeConstants.PARTTIME_APPLICAT_CADRE_STATUS_SET, selectIdSet, disabledIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttime/parttimeApprovalOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApprovalOrder(Integer approverTypeId, Integer applicatTypeId) {

        if (parttimeApprovalOrderService.idDuplicate(null, applicatTypeId, approverTypeId)) {
            return failed("添加重复");
        }

        ParttimeApprovalOrder record = new ParttimeApprovalOrder();
        record.setApproverTypeId(approverTypeId);
        record.setApplicateTypeId(applicatTypeId);
        parttimeApprovalOrderService.insertSelective(record);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttime/parttimeApprovalOrder")
    public String parttimeApprovalOrder(int id, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);


        int count = iParttimeMapper.countApprovalOrders(id);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ParttimeApprovalOrder> approvalOrders = iParttimeMapper.selectApprovalOrderList(id,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("approvalOrders", approvalOrders);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;
        searchStr += "&id=" + id;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        ParttimeApplicatType applicatType = parttimeApplicatTypeMapper.selectByPrimaryKey(id);
        modelMap.put("applicatType", applicatType);

        return "parttime/parttimeApplicatType/parttimeApproval_order";
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttimeApplicatType/parttimeApprovalOrder_del", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApprovalOrder_del(int id) {

        parttimeApprovalOrderService.del(id);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttimeApplicatType/parttimeApprovalOrder_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApprovalOrder_changeOrder(int applicatTypeId, Integer id, Integer addNum, HttpServletRequest request) {

        parttimeApprovalOrderService.changeOrder(applicatTypeId, id, addNum);
        logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "申请人身份关联的审批顺序调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
