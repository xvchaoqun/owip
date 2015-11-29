package controller.sys;

import controller.BaseController;
import domain.*;
import domain.MetaClassExample.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
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
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
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

        return "index";
    }

    @RequiresPermissions("metaClass:list")
    @RequestMapping("/metaClass_page")
    public String metaClass_page(@CurrentUser SysUser loginUser,
                                 HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "sort_order") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                 String name, String code,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

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

        if (!SecurityUtils.getSubject().hasRole(SystemConstants.ROLE_ADMIN)) {

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
            metaClass_export(example, response);
            return null;
        }

        int count = metaClassMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MetaClass> MetaClasss = metaClassMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("metaClasss", MetaClasss);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;
        if (StringUtils.isNotBlank(name)) {
            searchStr += "&name=" + name;
        }
        if (StringUtils.isNotBlank(code)) {
            searchStr += "&code=" + code;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "sys/metaClass/metaClass_page";
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
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加元数据：%s", id));
        } else {

            metaClassService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新元数据：%s", id));
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
        return "sys/metaClass/metaClass_au";
    }

    @RequiresPermissions("metaClass:del")
    @RequestMapping(value = "/metaClass_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaClass_del(Integer id, HttpServletRequest request) {

        if (id != null) {
            metaClassService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除元数据：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("metaClass:del")
    @RequestMapping(value = "/metaClass_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            metaClassService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除元数据类型：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("metaClass:changeOrder")
    @RequestMapping(value = "/metaClass_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaClass_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        metaClassService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "元数据调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "/metaClassRole", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaClassRole(int id,
                                Integer roleId,
                                HttpServletRequest request) {

        if (roleId == null) {
            roleId = -1;
        }
        metaClassService.updateRoles(id, roleId);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新元数据所属角色 %s, %s", id, roleId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("admin")
    @RequestMapping("/metaClassRole")
    public String metaClassRole(Integer id, ModelMap modelMap) throws IOException {

        Set<Integer> selectIdSet = new HashSet<Integer>();
        if (id != null) {

            MetaClass metaClass = metaClassMapper.selectByPrimaryKey(id);
            selectIdSet.add(metaClass.getRoleId());
            modelMap.put("metaClass", metaClass);
        }

        TreeNode tree = sysRoleService.getTree(selectIdSet);
        modelMap.put("tree", JSONUtils.toString(tree));

        return "sys/metaClass/metaClassRole";
    }

    public void metaClass_export(MetaClassExample example, HttpServletResponse response) {

        List<MetaClass> metaClasss = metaClassMapper.selectByExample(example);
        int rownum = metaClassMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);
        String[] titles = {"名称", "代码", "布尔属性名称", "附加属性名称"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MetaClass metaClass = metaClasss.get(i);
            String[] values = {
                    metaClass.getName(),
                    metaClass.getCode(),
                    metaClass.getBoolAttr(),
                    metaClass.getExtraAttr()};

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "元数据分类_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

        int count = metaClassMapper.countByExample(example);
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
    public String metaClass_type(Integer id, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id != null) {
            if (null == pageSize) {
                pageSize = 10;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            MetaTypeExample example = new MetaTypeExample();
            MetaTypeExample.Criteria criteria = example.createCriteria().andClassIdEqualTo(id);
            example.setOrderByClause(String.format("%s %s", "sort_order", "desc"));

            int count = metaTypeMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
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

            MetaClass metaClass = metaClassMapper.selectByPrimaryKey(id);
            modelMap.put("metaClass", metaClass);
            modelMap.put("metaClassMap", metaClassService.findAll());
        }

        return "sys/metaClass/metaClass_type";
    }
}
