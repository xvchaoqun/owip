package controller.cla;

import domain.cadre.CadreView;
import domain.cla.ClaApproverBlackList;
import domain.cla.ClaApproverType;
import domain.cla.ClaApproverTypeExample;
import domain.leader.Leader;
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
import sys.constants.ClaConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cla")
public class ClaApproverTypeController extends ClaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("claApprovalAuth:*")
    @RequestMapping("/claApproverType/selectCadres_tree")
    @ResponseBody
    public Map selectCadres_tree(Integer id, byte type) throws IOException {

        Map<String, Object> resultMap = success();

        if (type == ClaConstants.CLA_APPROVER_TYPE_UNIT) { // 本单位正职

            resultMap.put("tree", claAdditionalPostService.getMainPostCadreTree());

        }else if (type == ClaConstants.CLA_APPROVER_TYPE_LEADER) { // 分管校领导

            // 分管校领导 黑名单（即二次编辑中没有选择的干部）
            ClaApproverType leaderApproverType = claApproverTypeService.getLeaderApproverType();
            Integer leaderApproverTypeId = leaderApproverType.getId();
            Map<Integer, ClaApproverBlackList> blackListMap = claApproverBlackListService.findAll(leaderApproverTypeId);
            Set<Integer> unselectCadreSet = new HashSet<>();
            for (ClaApproverBlackList approverBlackList : blackListMap.values()) {
                unselectCadreSet.add(approverBlackList.getCadreId());
            }

            Set<CadreView> cadreSet = new LinkedHashSet<>();
            Set<Integer> selectCadreSet = new HashSet<>();
            Map<Integer, Leader> leaderMap = cadreLeaderService.findAll();
            for (Leader leader : leaderMap.values()) {
                CadreView cadre = leader.getCadre();
                cadreSet.add(cadre);
                if(!unselectCadreSet.contains(cadre.getId()))
                    selectCadreSet.add(cadre.getId());
            }

            TreeNode tree = cadreCommonService.getTree(cadreSet, ClaConstants.CLA_APPLICAT_CADRE_STATUS_SET,
                    selectCadreSet, null, true, true, true);
            resultMap.put("tree", tree);
        }else{ // 其他审批身份

            Set<Integer> selectIdSet = claApproverTypeService.findApproverCadreIds(id);
            //Set<Integer> disabledIdSet = claApproverTypeService.findApproverCadreIds(null);
            //disabledIdSet.removeAll(selectIdSet);
            TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<CadreView>(cadreService.findAll().values()),
                    ClaConstants.CLA_APPLICAT_CADRE_STATUS_SET, selectIdSet, null);
            resultMap.put("tree", tree);
        }

        return resultMap;
    }

    @RequiresPermissions("claApprovalAuth:*")
    @RequestMapping("/claApproverType/selectCadres")
    public String selectCadres(Integer id, byte type, ModelMap modelMap) throws IOException {

        if (type != ClaConstants.CLA_APPROVER_TYPE_UNIT && type != ClaConstants.CLA_APPROVER_TYPE_LEADER) {
            ClaApproverType approverType = claApproverTypeMapper.selectByPrimaryKey(id);
            modelMap.put("approverType", approverType);
        }
        return "cla/claApproverType/selectCadres";
    }

    @RequiresPermissions("claApprovalAuth:*")
    @RequestMapping(value = "/claApproverType/selectCadres", method = RequestMethod.POST)
    @ResponseBody
    public Map do_selectCadres(Integer id, byte type, @RequestParam(value = "cadreIds[]", required = false) Integer[] cadreIds) {

        if(type==ClaConstants.CLA_APPROVER_TYPE_UNIT){
            // 本单位正职身份
            ClaApproverType mainPostApproverType = claApproverTypeService.getMainPostApproverType();
            Integer mainPostTypeId = mainPostApproverType.getId();
            claApproverBlackListService.updateCadreIds(mainPostTypeId, cadreIds);
        }else if(type==ClaConstants.CLA_APPROVER_TYPE_LEADER){
            // 分管校领导身份
            ClaApproverType leaderApproverType = claApproverTypeService.getLeaderApproverType();
            Integer leaderApproverTypeId = leaderApproverType.getId();
            claApproverBlackListService.updateCadreIds(leaderApproverTypeId, cadreIds);
        }else {
            // 其他身份
            claApproverTypeService.updateApproverCadreIds(id, cadreIds);
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("claApprovalAuth:*")
    @RequestMapping("/claApproverType")
    public String claApproverType(HttpServletResponse response,
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

        ClaApproverTypeExample example = new ClaApproverTypeExample();
        ClaApproverTypeExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        long count = claApproverTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ClaApproverType> approverTypes = claApproverTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
        return "cla/claApproverType/claApproverType_page";
    }

    @RequiresPermissions("claApprovalAuth:*")
    @RequestMapping(value = "/claApproverType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApproverType_au(ClaApproverType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (claApproverTypeService.idDuplicate(id, record.getName(), record.getType())) {
            return failed("添加重复");
        }
        if (id == null) {
            claApproverTypeService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CLA, "添加审批人分类：%s", record.getId()));
        } else {

            claApproverTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CLA, "更新审批人分类：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("claApprovalAuth:*")
    @RequestMapping("/claApproverType_au")
    public String claApproverType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ClaApproverType approverType = claApproverTypeMapper.selectByPrimaryKey(id);
            modelMap.put("approverType", approverType);
        }
        return "cla/claApproverType/claApproverType_au";
    }

    @RequiresPermissions("claApprovalAuth:*")
    @RequestMapping(value = "/claApproverType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApproverType_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            claApproverTypeService.del(id);
            logger.info(addLog(LogConstants.LOG_CLA, "删除审批人分类：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("claApprovalAuth:*")
    @RequestMapping(value = "/claApproverType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApproverType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        claApproverTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CLA, "审批人分类调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("claApprovalAuth:*")
    @RequestMapping(value = "/claApproverType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map claApproverType_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            claApproverTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CLA, "批量删除审批人分类：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
