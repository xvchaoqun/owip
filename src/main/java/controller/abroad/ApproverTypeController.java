package controller.abroad;

import domain.abroad.ApproverBlackList;
import domain.abroad.ApproverType;
import domain.abroad.ApproverTypeExample;
import domain.abroad.ApproverTypeExample.Criteria;
import domain.cadre.CadreView;
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
import sys.constants.AbroadConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/abroad")
public class ApproverTypeController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approverType/selectCadres_tree")
    @ResponseBody
    public Map selectCadres_tree(Integer id) throws IOException {

        Map<String, Object> resultMap = success();
        ApproverType approverType = approverTypeMapper.selectByPrimaryKey(id);
        byte type = approverType.getType();

        if (type == AbroadConstants.ABROAD_APPROVER_TYPE_UNIT_PRINCIPAL) { // 本单位正职

            resultMap.put("tree", abroadAdditionalPostService.getUnitPrincipalCadreTree(id));

        }else if (type == AbroadConstants.ABROAD_APPROVER_TYPE_LEADER) { // 分管校领导

            // 分管校领导 黑名单（即二次编辑中没有选择的干部）
            Map<String, ApproverBlackList> blackListMap = approverBlackListService.findAll(id);
            Set<String> unselectCadreSet = blackListMap.keySet();

            Set<CadreView> cadreSet = new LinkedHashSet<>();
            Set<String> selectCadreSet = new HashSet<>();
            Map<Integer, Leader> leaderMap = cadreLeaderService.findAll();
            for (Leader leader : leaderMap.values()) {
                CadreView cadre = leader.getCadre();
                cadreSet.add(cadre);
                String key = cadre.getId() + "_" + cadre.getUnitId();
                if(!unselectCadreSet.contains(key))
                    selectCadreSet.add(key);
            }

            TreeNode tree = cadreCommonService.getTree2(cadreSet, AbroadConstants.ABROAD_APPLICAT_CADRE_STATUS_SET,
                    selectCadreSet, null, true, true);
            resultMap.put("tree", tree);
        }else{ // 其他审批身份

            Set<String> selectIdSet = approverTypeService.findApproverCadreIds(id);
            //Set<Integer> disabledIdSet = claApproverTypeService.findApproverCadreIds(null);
            //disabledIdSet.removeAll(selectIdSet);
            TreeNode tree = cadreCommonService.getTree2(new LinkedHashSet<CadreView>(cadreService.findAll().values()),
                    AbroadConstants.ABROAD_APPLICAT_CADRE_STATUS_SET, selectIdSet, null, true, false);
            resultMap.put("tree", tree);
        }

        return resultMap;
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approverType/selectCadres")
    public String select_cadres(Integer id, ModelMap modelMap) throws IOException {

        //if (type != AbroadConstants.ABROAD_APPROVER_TYPE_UNIT_PRINCIPAL && type != AbroadConstants.ABROAD_APPROVER_TYPE_LEADER) {
            ApproverType approverType = approverTypeMapper.selectByPrimaryKey(id);
            modelMap.put("approverType", approverType);
        //}
        return "abroad/approverType/selectCadres";
    }

    // cadreIds = cadreId_unitId
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approverType/selectCadres", method = RequestMethod.POST)
    @ResponseBody
    public Map do_select_cadres(Integer id, String[] cadreIds) {

        ApproverType approverType = approverTypeMapper.selectByPrimaryKey(id);
        byte type = approverType.getType();

        if(type==AbroadConstants.ABROAD_APPROVER_TYPE_UNIT_PRINCIPAL || type==AbroadConstants.ABROAD_APPROVER_TYPE_LEADER){
            approverBlackListService.updateCadreIds(id, cadreIds);
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
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        long count = approverTypeMapper.countByExample(example);
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
    public Map do_approverType_au(ApproverType record,
                                  Byte[] auth,
                                  HttpServletRequest request) {

        Integer id = record.getId();

        if(auth!=null && auth.length>0){
            record.setAuth(StringUtils.join(auth, ","));
        }
        if (id == null) {
            approverTypeService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "添加审批人分类：%s", record.getId()));
        } else {

            approverTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "更新审批人分类：%s", record.getId()));
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
            logger.info(addLog(LogConstants.LOG_ABROAD, "删除审批人分类：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approverType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approverType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        approverTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ABROAD, "审批人分类调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approverType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            approverTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ABROAD, "批量删除审批人分类：%s", StringUtils.join(ids, ",")));
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
            criteria.andNameLike("%"+searchStr.trim()+"%");
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
