package controller.parttime;

import domain.cadre.Cadre;
import domain.leader.Leader;
import domain.parttime.ParttimeApproverBlackList;
import domain.parttime.ParttimeApproverType;
import domain.parttime.ParttimeApproverTypeExample;
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
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class ParttimeApproverTypeController extends ParttimeBaseController {

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApproverType")
    public String parttimeApproverType(HttpServletResponse response,
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

        ParttimeApproverTypeExample example = new ParttimeApproverTypeExample();
        ParttimeApproverTypeExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        long count = parttimeApproverTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ParttimeApproverType> approverTypes = parttimeApproverTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
        return "parttime/parttimeApproverType/parttimeApproverType_page";
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApproverType_au")
    public String parttimeApproverType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ParttimeApproverType approverType = parttimeApproverTypeMapper.selectByPrimaryKey(id);
            modelMap.put("approverType", approverType);
        }
        return "parttime/parttimeApproverType/parttimeApproverType_au";
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttimeApproverType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApproverType_au(ParttimeApproverType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (parttimeApproverTypeService.idDuplicate(id, record.getName(), record.getType())) {
            return failed("添加重复");
        }
        if (id == null) {
            parttimeApproverTypeService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "添加审批人分类：%s", record.getId()));
        } else {
            parttimeApproverTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "更新审批人分类：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttimeApproverType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApproverType_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            parttimeApproverTypeService.del(id);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "删除审批人分类：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttimeApproverType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApproverType_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            parttimeApproverTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "批量删除审批人分类：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttime/parttimeApproverType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApproverType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        parttimeApproverTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "审批人分类调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApproverType/selectCadres")
    public String selectCadres(Integer id, byte type, ModelMap modelMap) throws IOException {

        if (type != ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT && type != ParttimeConstants.PARTTIME_APPROVER_TYPE_LEADER) {
            ParttimeApproverType approverType = parttimeApproverTypeMapper.selectByPrimaryKey(id);
            modelMap.put("approverType", approverType);
        }
        return "parttime/parttimeApproverType/selectCadres";
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApproverType/selectCadres_tree")
    @ResponseBody
    public Map selectCadres_tree(Integer id, byte type) throws IOException {

        Map<String, Object> resultMap = success();

        if (type == ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT) {

            resultMap.put("tree", parttimeApproverTypeService.getMainPostCadreTree());

        } else if (type == ParttimeConstants.PARTTIME_APPROVER_TYPE_LEADER) { // 分管校领导

            // 分管校领导 黑名单（即二次编辑中没有选择的干部）
            ParttimeApproverType leaderApproverType = parttimeApproverTypeService.getLeaderApproverType();
            Integer leaderApproverTypeId = leaderApproverType.getId();
            Map<Integer, ParttimeApproverBlackList> blackListMap = parttimeApproverTypeService.findAllBlackList(leaderApproverTypeId);
            Set<Integer> unselectCadreSet = new HashSet<>();
            for (ParttimeApproverBlackList approverBlackList : blackListMap.values()) {
                unselectCadreSet.add(approverBlackList.getCadreId());
            }

            Set<Cadre> cadreSet = new LinkedHashSet<>();
            Set<Integer> selectCadreSet = new HashSet<>();
            Map<Integer, Leader> leaderMap = cadreLeaderService.findAll();
            for (Leader leader : leaderMap.values()) {

                Cadre cadre = CmTag.getCadre(leader.getUserId());
                cadreSet.add(cadre);
                if(!unselectCadreSet.contains(cadre.getId()))
                    selectCadreSet.add(cadre.getId());
            }

            TreeNode tree = cadreCommonService.getTree(cadreSet, ParttimeConstants.PARTTIME_APPLICAT_CADRE_STATUS_SET,
                    selectCadreSet, null, true, true, true);
            resultMap.put("tree", tree);
        }else{ // 其他审批身份

            Set<Integer> selectIdSet = parttimeApproverTypeService.findApproverCadreIds(id);
            //Set<Integer> disabledIdSet = claApproverTypeService.findApproverCadreIds(null);
            //disabledIdSet.removeAll(selectIdSet);
            TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<Cadre>(cadreService.getCadres()),
                    ParttimeConstants.PARTTIME_APPLICAT_CADRE_STATUS_SET, selectIdSet, null);
            resultMap.put("tree", tree);
        }

        return resultMap;
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttimeApproverType/selectCadres", method = RequestMethod.POST)
    @ResponseBody
    public Map do_selectCadres(Integer id, byte type, Integer[] cadreIds) {
        if(type==ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT){
            // 院系党组织
            ParttimeApproverType mainPostApproverType = parttimeApproverTypeService.getMainPostApproverType();
            Integer mainPostTypeId = mainPostApproverType.getId();
            parttimeApproverTypeService.updateCadreIds(mainPostTypeId, cadreIds);
        }else if(type==ParttimeConstants.PARTTIME_APPROVER_TYPE_LEADER){
            // 分管校领导身份
            ParttimeApproverType leaderApproverType = parttimeApproverTypeService.getLeaderApproverType();
            Integer leaderApproverTypeId = leaderApproverType.getId();
            parttimeApproverTypeService.updateCadreIds(leaderApproverTypeId, cadreIds);
        }else {
            // 其他身份
            parttimeApproverTypeService.updateApproverCadreIds(id, cadreIds);
        }
        return success(FormUtils.SUCCESS);
    }


}
