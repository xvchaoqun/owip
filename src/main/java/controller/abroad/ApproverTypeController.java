package controller.abroad;

import controller.BaseController;
import domain.abroad.ApproverBlackList;
import domain.abroad.ApproverType;
import domain.abroad.ApproverTypeExample;
import domain.abroad.ApproverTypeExample.Criteria;
import domain.cadre.Cadre;
import domain.cadre.CadreLeader;
import domain.cadre.CadreView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class ApproverTypeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approverType/selectCadres_tree")
    @ResponseBody
    public Map selectCadres_tree(Integer id, byte type) throws IOException {

        Map<String, Object> resultMap = success();

        if (type == SystemConstants.APPROVER_TYPE_UNIT) { // 本单位正职

            resultMap.put("tree", cadreCommonService.getMainPostCadreTree());

        }else if (type == SystemConstants.APPROVER_TYPE_LEADER) { // 分管校领导

            // 分管校领导 黑名单（即二次编辑中没有选择的干部）
            ApproverType leaderApproverType = approverTypeService.getLeaderApproverType();
            Integer leaderApproverTypeId = leaderApproverType.getId();
            Map<Integer, ApproverBlackList> blackListMap = approverBlackListService.findAll(leaderApproverTypeId);
            Set<Integer> unselectCadreSet = new HashSet<>();
            for (ApproverBlackList approverBlackList : blackListMap.values()) {
                unselectCadreSet.add(approverBlackList.getCadreId());
            }

            Set<CadreView> cadreSet = new LinkedHashSet<>();
            Set<Integer> selectCadreSet = new HashSet<>();
            Map<Integer, CadreLeader> leaderMap = cadreLeaderService.findAll();
            for (CadreLeader leader : leaderMap.values()) {
                CadreView cadre = leader.getCadre();
                cadreSet.add(cadre);
                if(!unselectCadreSet.contains(cadre.getId()))
                    selectCadreSet.add(cadre.getId());
            }

            TreeNode tree = cadreCommonService.getTree(cadreSet, selectCadreSet, null, true, true);
            resultMap.put("tree", tree);
        }else{ // 其他审批身份

            Set<Integer> selectIdSet = approverTypeService.findApproverCadreIds(id);
            //Set<Integer> disabledIdSet = approverTypeService.findApproverCadreIds(null);
            //disabledIdSet.removeAll(selectIdSet);
            TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<CadreView>(cadreService.findAll().values()), selectIdSet, null);
            resultMap.put("tree", tree);
        }

        return resultMap;
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approverType/selectCadres")
    public String select_cadres(Integer id, byte type, ModelMap modelMap) throws IOException {

        if (type != SystemConstants.APPROVER_TYPE_UNIT && type != SystemConstants.APPROVER_TYPE_LEADER) {
            ApproverType approverType = approverTypeMapper.selectByPrimaryKey(id);
            modelMap.put("approverType", approverType);
        }
        return "abroad/approverType/selectCadres";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approverType/selectCadres", method = RequestMethod.POST)
    @ResponseBody
    public Map do_select_cadres(Integer id, byte type, @RequestParam(value = "cadreIds[]", required = false) Integer[] cadreIds) {

        if(type==SystemConstants.APPROVER_TYPE_UNIT){
            // 本单位正职身份
            ApproverType mainPostApproverType = approverTypeService.getMainPostApproverType();
            Integer mainPostTypeId = mainPostApproverType.getId();
            approverBlackListService.updateCadreIds(mainPostTypeId, cadreIds);
        }else if(type==SystemConstants.APPROVER_TYPE_LEADER){
            // 分管校领导身份
            ApproverType leaderApproverType = approverTypeService.getLeaderApproverType();
            Integer leaderApproverTypeId = leaderApproverType.getId();
            approverBlackListService.updateCadreIds(leaderApproverTypeId, cadreIds);
        }else {
            // 其他身份
            approverTypeService.updateApproverCadreIds(id, cadreIds);
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approverType")
    public String approverType(HttpServletResponse response,
                                    @RequestParam(defaultValue = "1") int cls,
                                    String name,
                                    Byte type,
                                    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApproverTypeExample example = new ApproverTypeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        int count = approverTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApproverType> approverTypes = approverTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("approverTypes", approverTypes);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(name)) {
            searchStr += "&name=" + name;
        }
        if (type != null) {
            searchStr += "&type=" + type;
        }

        modelMap.put("cls", cls);
        searchStr += "&cls=" + cls;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "abroad/approverType/approverType_page";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approverType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approverType_au(ApproverType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (approverTypeService.idDuplicate(id, record.getName(), record.getType())) {
            return failed("添加重复");
        }
        if (id == null) {
            approverTypeService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "添加审批人分类：%s", record.getId()));
        } else {

            approverTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "更新审批人分类：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approverType_au")
    public String approverType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApproverType approverType = approverTypeMapper.selectByPrimaryKey(id);
            modelMap.put("approverType", approverType);
        }
        return "abroad/approverType/approverType_au";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approverType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approverType_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            approverTypeService.del(id);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "删除审批人分类：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approverType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approverType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        approverTypeService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ABROAD, "审批人分类调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approverType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            approverTypeService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "批量删除审批人分类：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    /*@RequestMapping("/approverType_selects")
    @ResponseBody
    public Map approverType_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApproverTypeExample example = new ApproverTypeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = approverTypeMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ApproverType> approverTypes = approverTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != approverTypes && approverTypes.size()>0){

            for(ApproverType approverType:approverTypes){

                Select2Option option = new Select2Option();
                option.setText(approverType.getName());
                option.setId(approverType.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
