package controller.sys;

import controller.BaseController;
import domain.sys.SysResource;
import domain.sys.SysResourceExample;
import domain.sys.SysRole;
import mixin.MixinUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import org.springframework.web.util.HtmlUtils;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tool.jackson.Select2Option;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class SysResourceController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sysResource:list")
    @RequestMapping("/sysResource_roles")
    public String sysResource_roles(int resourceId, ModelMap modelMap) {

        SysResource sysResource = sysResourceMapper.selectByPrimaryKey(resourceId);
        modelMap.put("sysResource", sysResource);

        Map<Integer, SysRole> sysRoleMap = sysRoleService.findAll();
        modelMap.put("sysRoleMap", sysRoleMap);

        return "sys/sysResource/sysResource_roles";
    }

    @RequiresPermissions("sysResource:list")
    @RequestMapping("/sysResource")
    public String sysResource(@RequestParam(required = false, defaultValue = "0") boolean isMobile, ModelMap modelMap) {

        modelMap.put("isMobile", isMobile);
        return "sys/sysResource/sysResource_page";
    }

    @RequiresPermissions("sysResource:list")
    @RequestMapping("/sysResource_data")
    @ResponseBody
    public Map sysResource_data(@RequestParam(required = false, defaultValue = "0") boolean isMobile,
                                HttpServletResponse response,
                                String name,
                                String permission,
                                String url,
                                Integer nodeid) throws IOException {

        SysResourceExample example = new SysResourceExample();
        SysResourceExample.Criteria criteria = example.createCriteria().andIsMobileEqualTo(isMobile)
                .andAvailableEqualTo(SystemConstants.AVAILABLE);

        Set<Integer> idSet = new HashSet<>();
        if (StringUtils.isNotBlank(name)
                || StringUtils.isNotBlank(permission)
                || StringUtils.isNotBlank(url)) {

            SysResourceExample example2 = new SysResourceExample();
            SysResourceExample.Criteria criteria2 = example2.createCriteria().andIsMobileEqualTo(isMobile)
                    .andAvailableEqualTo(SystemConstants.AVAILABLE);

            if (StringUtils.isNotBlank(name)) {
                criteria2.andNameLike(SqlUtils.like(name));
            }
            if (StringUtils.isNotBlank(permission)) {
                criteria2.andPermissionLike(SqlUtils.like(permission));
            }
            if (StringUtils.isNotBlank(url)) {
                criteria2.andUrlLike(SqlUtils.like(url));
            }
            List<SysResource> sysResources = sysResourceMapper.selectByExample(example2);
            for (SysResource sysResource : sysResources) {

                idSet.add(sysResource.getId());
                String parentIds = sysResource.getParentIds();
                for (String _parentId : parentIds.split("/")) {
                    Integer parentId = Integer.valueOf(_parentId);
                    idSet.add(parentId);
                }
            }
            if (idSet.size() > 0) {
                criteria.andIdIn(new ArrayList<>(idSet));
            } else {
                criteria.andIdIsNull();
            }
        }

        if (nodeid != null) {
            criteria.andParentIdEqualTo(nodeid);
        } else {
            criteria.andParentIdIsNull();
        }
        example.setOrderByClause("sort_order desc");
        List<SysResource> records = sysResourceMapper.selectByExample(example);

        Map resultMap = success(FormUtils.SUCCESS);
        resultMap.put("rows", records);

        return resultMap;
    }

    @RequiresPermissions("sysResource:edit")
    @RequestMapping(value = "/sysResource_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysResource_au(Byte[] countCacheKeys,
                                 SysResource record, HttpServletRequest request) {

        if (countCacheKeys != null && countCacheKeys.length > 0) {
            record.setCountCacheKeys(StringUtils.join(countCacheKeys, ","));
        }

        if (sysResourceService.idDuplicate(record.getId(), record.getPermission(), record.getUrl())) {
            return failed(FormUtils.DUPLICATE);
        }

        record.setUrl(HtmlUtils.htmlUnescape(record.getUrl()));
        record.setIsMobile(BooleanUtils.isTrue(record.getIsMobile()));

        Integer parentId = record.getParentId();
        SysResource parent = sysResourceMapper.selectByPrimaryKey(parentId);
        record.setParentIds(parent.getParentIds() + parentId + "/");
        record.setIsMobile(parent.getIsMobile());

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        if (record.getId() == null) {

            record.setIsLeaf(true);
            record.setAvailable(SystemConstants.AVAILABLE);
            sysResourceService.insert(record);

            resultMap.put("data", record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加资源：%s", JSONUtils.toString(record, MixinUtils.baseMixins(), false)));

        } else {

            sysResourceService.updateByPrimaryKeySelective(record);
            resultMap.put("data", sysResourceMapper.selectByPrimaryKey(record.getId()));

            logger.info(addLog(LogConstants.LOG_ADMIN, "更新资源：%s", JSONUtils.toString(record, MixinUtils.baseMixins(), false)));
        }

        return resultMap;
    }

    @RequiresPermissions("sysResource:edit")
    @RequestMapping("/sysResource_au")
    public String sysResource_au(Integer id, Boolean isMobile, Integer parentId, ModelMap modelMap) {

        if (id != null) {

            SysResource sysResource = sysResourceMapper.selectByPrimaryKey(id);
            isMobile = sysResource.getIsMobile();
            modelMap.put("sysResource", sysResource);
            modelMap.addAttribute("op", "修改");

            Integer _parentId = sysResource.getParentId();
            if (_parentId != null && _parentId > 0) {
                SysResource parent = sysResourceMapper.selectByPrimaryKey(_parentId);
                modelMap.addAttribute("parent", parent);
            }
        } else if (parentId != null) {

            SysResource parent = sysResourceMapper.selectByPrimaryKey(parentId);
            isMobile = parent.getIsMobile();
            modelMap.addAttribute("parent", parent);
            SysResource child = new SysResource();
            child.setParentId(parentId);

            //child.setParentIds(parent.getParentIds() + parent.getId() + "/");
            modelMap.addAttribute("sysResource", child);
            modelMap.addAttribute("op", "新增子节点");
        }

        modelMap.addAttribute("isMobile", isMobile);

        return "sys/sysResource/sysResource_au";
    }

    @RequiresPermissions("sysResource:del")
    @RequestMapping(value = "/sysResource_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysResource_del(Integer id, HttpServletRequest request) {

        if (id != null) {

            sysResourceService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除资源：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    // 给角色添加或删除某个资源
    @RequiresPermissions("sysResource:addRole")
    @RequestMapping(value = "/sysResource_updateRole", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysResource_updateRole(Integer[] ids, int resourceId, boolean addOrDel, HttpServletRequest request) {

        sysRoleService.updateRole(ids, resourceId, addOrDel);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysResource:list")
    @RequestMapping("/sysResource_selects")
    @ResponseBody
    public Map sysResource_selects(boolean isMobile, String[] type, String searchStr,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SysResourceExample example = new SysResourceExample();
        SysResourceExample.Criteria criteria = example.createCriteria().andIsMobileEqualTo(isMobile)
                .andAvailableEqualTo(SystemConstants.AVAILABLE);
        //criteria.andTypeNotEqualTo(SystemConstants.RESOURCE_TYPE_FUNCTION);
        if (type != null && type.length > 0)
            criteria.andTypeIn(Arrays.asList(type));
        example.setOrderByClause("parent_id asc, sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = sysResourceMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysResource> sysResources = sysResourceMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        Map<Integer, SysResource> resourceMap = sysResourceService.getSortedSysResources(isMobile);
        List<Select2Option> options = new ArrayList<Select2Option>();
        for (SysResource sysResource : sysResources) {
            String text = "";
            String parentIds = sysResource.getParentIds();

            for (String _parentId : parentIds.split("/")) {
                Integer parentId = Integer.valueOf(_parentId);
                if (parentId > 0) {
                    SysResource _sysResource = resourceMap.get(parentId);
                    if (_sysResource != null) text += _sysResource.getName() + "-";
                }
            }

            Select2Option option = new Select2Option();
            option.setText(text + sysResource.getName() + "(" + sysResource.getPermission() + ")");
            option.setId(sysResource.getId() + "");

            options.add(option);
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
