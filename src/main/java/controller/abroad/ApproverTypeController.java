package controller.abroad;

import controller.BaseController;
import domain.ApproverType;
import domain.ApproverTypeExample;
import domain.ApproverTypeExample.Criteria;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class ApproverTypeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approverType/selectCadres_tree")
    @ResponseBody
    public Map selectCadres_tree(int id) throws IOException {

        Set<Integer> selectIdSet = approverTypeService.getCadreIds(id);
        //Set<Integer> disabledIdSet = approverTypeService.getCadreIds(null);
        //disabledIdSet.removeAll(selectIdSet);
        TreeNode tree = cadreService.getTree(selectIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approverType/select_cadres")
    public String select_cadres(int id, ModelMap modelMap) throws IOException {

        ApproverType approverType = approverTypeMapper.selectByPrimaryKey(id);
        modelMap.put("approverType", approverType);
        return "abroad/approverType/select_cadres";
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value="/approverType/select_cadres", method=RequestMethod.POST)
    @ResponseBody
    public Map do_select_cadres(Integer id, @RequestParam(value="cadreIds[]",required=false) Integer[] cadreIds) {

        approverTypeService.updateCadreIds(id, cadreIds);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approverType")
    public String approverType() {

        return "index";
    }
    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approverType_page")
    public String approverType_page(HttpServletResponse response,
                                    @RequestParam(defaultValue = "1")int cls,
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
        if (type!=null) {
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
        if (type!=null) {
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
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "添加审批人分类：%s", record.getId()));
        } else {

            approverTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新审批人分类：%s", record.getId()));
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
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "删除审批人分类：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approverType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_approverType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        approverTypeService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ABROAD, "审批人分类调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping(value = "/approverType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            approverTypeService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "批量删除审批人分类：%s", StringUtils.join(ids, ",")));
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
