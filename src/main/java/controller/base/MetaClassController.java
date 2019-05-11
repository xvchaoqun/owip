package controller.base;

import controller.BaseController;
import domain.base.MetaClass;
import domain.base.MetaClassExample;
import domain.base.MetaClassExample.Criteria;
import domain.base.MetaType;
import domain.base.MetaTypeExample;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MetaClassController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("metaClass:list")
    @RequestMapping("/metaClass")
    public String metaClass() {

        return "base/metaClass/metaClass_page";
    }

    @RequiresPermissions("metaClass:list")
    @RequestMapping("/metaClass_data")
    @ResponseBody
    public void metaClass_data(@CurrentUser SysUserView loginUser,
                               HttpServletResponse response,
                               HttpServletRequest request,
                               @SortParam(required = false, defaultValue = "sort_order", tableName = "base_meta_class") String sort,
                               @OrderParam(required = false, defaultValue = "desc") String order,
                               String name, String code,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MetaClassExample example = new MetaClassExample();
        Criteria criteria = example.createCriteria().andAvailableEqualTo(true);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (!ShiroHelper.hasRole(RoleConstants.ROLE_ADMIN)) {

            Set<Integer> roleIdSet = sysUserService.getUserRoleIdSet(loginUser.getRoleIds());
            criteria.andRoleIdIn(new ArrayList<>(roleIdSet));
        }

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }
        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            metaClass_export(example, request, response);
            return;
        }

        long count = metaClassMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MetaClass> MetaClasss = metaClassMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", MetaClasss);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(MetaClass.class, DispatchTypeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("metaClass:edit")
    @RequestMapping(value = "/metaClass_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaClass_au(MetaClass record, HttpServletRequest request) {

        Integer id = record.getId();
        record.setCode(StringUtils.lowerCase(record.getCode()));
        if (StringUtils.isBlank(record.getCode())) {
            record.setCode(metaClassService.genCode());
        } else if (!metaClassService.codeAvailable(id, record.getCode())) {
            return failed("编程代码重复");
        }

        if (id == null) {
            record.setAvailable(true);
            metaClassService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加元数据：%s", id));
        } else {

            metaClassService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新元数据：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("metaClass:edit")
    @RequestMapping("/metaClass_au")
    public String metaClass_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MetaClass metaClass = metaClassMapper.selectByPrimaryKey(id);
            modelMap.put("metaClass", metaClass);
        }
        return "base/metaClass/metaClass_au";
    }

    @RequiresPermissions("metaClass:del")
    @RequestMapping(value = "/metaClass_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaClass_del(Integer id, HttpServletRequest request) {

        if (id != null) {
            metaClassService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除元数据：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("metaClass:del")
    @RequestMapping(value = "/metaClass_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            metaClassService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除元数据类型：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("metaClass:changeOrder")
    @RequestMapping(value = "/metaClass_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaClass_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        metaClassService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "元数据调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping(value = "/metaClassRole", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaClassRole(int id,
                                Integer roleId,
                                HttpServletRequest request) {

        if (roleId == null) {
            roleId = -1;
        }
        metaClassService.updateRoles(id, roleId);
        logger.info(addLog(LogConstants.LOG_ADMIN, "更新元数据所属角色 %s, %s", id, roleId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping("/metaClassRole")
    public String metaClassRole(Integer id, ModelMap modelMap) throws IOException {

        Set<Integer> selectIdSet = new HashSet<Integer>();
        if (id != null) {

            MetaClass metaClass = metaClassMapper.selectByPrimaryKey(id);
            selectIdSet.add(metaClass.getRoleId());
            modelMap.put("metaClass", metaClass);
        }

        TreeNode tree = sysRoleService.getTree(selectIdSet, false);
        modelMap.put("tree", JSONUtils.toString(tree));

        return "base/metaClass/metaClassRole";
    }

    public void metaClass_export(MetaClassExample example, HttpServletRequest request,
                                 HttpServletResponse response) {

        List<MetaClass> records = metaClassMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"ID|150", "名称|150", "代码|180", "所属一级目录|150",
                "所属二级目录|150", "布尔属性名称|150", "附加属性名称|150"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MetaClass record = records.get(i);
            String[] values = {
                    record.getId() + "",
                    record.getName(),
                    record.getCode(),
                    record.getFirstLevel(),
                    record.getSecondLevel(),
                    record.getBoolAttr(),
                    record.getExtraAttr()};
            valuesList.add(values);
        }
        String fileName = "元数据类别";
        SXSSFWorkbook wb = new SXSSFWorkbook(500);
        ExportHelper.createSheet(fileName, wb, titles, valuesList);

        for (int i = 0; i < rownum; i++) {

            MetaClass record = records.get(i);

            MetaTypeExample _example = new MetaTypeExample();
            _example.createCriteria().andClassIdEqualTo(record.getId());
            _example.setOrderByClause("sort_order asc");
            Map<String, Object> metaTypeExportData = getMetaTypeExportData(record, _example);
            List<String> _titles = (List<String>) metaTypeExportData.get("titles");
            List<List<String>> _valuesList = (List<List<String>>) metaTypeExportData.get("valuesList");
            ExportHelper.createSheet(record.getName(), wb, _titles, _valuesList);
        }

        ExportHelper.output(wb, fileName+ ".xlsx", request, response);
    }

    @RequestMapping("/metaClass_selects")
    @ResponseBody
    public Map metaClass_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MetaClassExample example = new MetaClassExample();
        Criteria criteria = example.createCriteria().andAvailableEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike("%" + searchStr + "%");
        }

        long count = metaClassMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MetaClass> metaClasss = metaClassMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != metaClasss && metaClasss.size() > 0) {

            for (MetaClass metaClass : metaClasss) {

                Select2Option option = new Select2Option();
                option.setText(metaClass.getName());
                option.setId(metaClass.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("metaClassType:list")
    @RequestMapping("/metaClass_type")
    public String metaClass_type(Integer id,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 HttpServletResponse response,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id != null) {
            if (null == pageSize) {
                pageSize = 8;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            MetaTypeExample example = new MetaTypeExample();
            MetaTypeExample.Criteria criteria = example.createCriteria().andClassIdEqualTo(id);
            example.setOrderByClause("sort_order asc");

            int count = metaTypeMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }

            MetaClass metaClass = metaClassMapper.selectByPrimaryKey(id);
            modelMap.put("metaClass", metaClass);
            modelMap.put("metaClassMap", metaClassService.findAll());

            if (export == 1) {
                metaClassTypes_export(metaClass, example, response);
                return null;
            }

            List<MetaType> metaTypes = metaTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("metaTypes", metaTypes);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (id != null) {
                searchStr += "&id=" + id;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);
        }

        return "base/metaClass/metaClass_type";
    }

    public void metaClassTypes_export(MetaClass metaClass, MetaTypeExample example, HttpServletResponse response) {

        Map<String, Object> metaTypeExportData = getMetaTypeExportData(metaClass, example);
        List<String> titles = (List<String>) metaTypeExportData.get("titles");
        List<List<String>> valuesList = (List<List<String>>) metaTypeExportData.get("valuesList");

        String fileName = "元数据属性（" + metaClass.getName() + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    private Map<String, Object> getMetaTypeExportData(MetaClass metaClass, MetaTypeExample example) {

        String boolName = StringUtils.defaultIfBlank(metaClass.getBoolAttr(), "布尔属性");
        String extraName = StringUtils.defaultIfBlank(metaClass.getExtraAttr(), "附加属性");
        List<MetaType> records = metaTypeMapper.selectByExample(example);
        int rownum = records.size();
        List<String> titles = new ArrayList<>(Arrays.asList(new String[]{"ID|90", "名称|150|left",
                "代码|150", "所属类别ID|80", "所属类别|180", "所属类别代码|180"}));
        if (StringUtils.isNotBlank(metaClass.getBoolAttr())) {
            titles.add(boolName + "|150");
        }
        if (StringUtils.isNotBlank(metaClass.getExtraAttr())) {
            titles.add(extraName + "|150");
        }
        titles.add("备注|150");

        List<List<String>> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MetaType record = records.get(i);
            List values = new ArrayList<>(Arrays.asList(new String[]{
                    record.getId() + "",
                    record.getName(),
                    record.getCode(),
                    metaClass.getId() + "",
                    metaClass.getName(),
                    metaClass.getCode()}));

            if (StringUtils.isNotBlank(metaClass.getBoolAttr())) {
                values.add(record.getBoolAttr() != null ? BooleanUtils.toString(record.getBoolAttr(),
                        "是", "否") : "--");
            }
            if (StringUtils.isNotBlank(metaClass.getExtraAttr())) {
                values.add(record.getExtraAttr());
            }
            values.add(record.getRemark());

            valuesList.add(values);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("titles", titles);
        map.put("valuesList", valuesList);

        return map;
    }
}
