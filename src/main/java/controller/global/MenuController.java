package controller.global;

import controller.BaseController;
import domain.sys.SysResource;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MenuController extends BaseController {

    //private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/menu")
    public String menu(ModelMap modelMap) {

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        List<SysResource> userMenus = sysUserService.makeMenus(shiroUser.getPermissions());

        modelMap.put("menus", userMenus);
        return "menu";
    }

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping("/menu_preview")
    public String menu_preview(@RequestParam(value = "resIds[]", required = false) Integer[] resIds, ModelMap modelMap) {

        Set<String> permissions = new HashSet<String>();
        Map<Integer, SysResource> sysResources = sysResourceService.getSortedSysResources();
        if(resIds!=null) {
            for (Integer resId : resIds) {
                SysResource sysResource = sysResources.get(resId);
                if (sysResource != null && StringUtils.isNotBlank(sysResource.getPermission())) {
                    permissions.add(sysResource.getPermission().trim());
                }
            }
        }
        List<SysResource> userMenus = sysUserService.makeMenus(permissions);
        modelMap.put("menus", userMenus);
        return "menu";
    }

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping("/menu_preview_byRoleId")
    public String menu_preview_byRoleId(Integer roleId, ModelMap modelMap) {

        if (roleId != null) {
            Set<String> rolePermissions = sysRoleService.getRolePermissions(roleId);
            List<SysResource> userMenus = sysUserService.makeMenus(rolePermissions);
            modelMap.put("menus", userMenus);
        }

        return "menu";
    }

    @RequestMapping("/menu_breadcrumbs")
    @ResponseBody
    public Map menu_breadcrumbs(String url, ModelMap modelMap) {

        url = HtmlUtils.htmlUnescape(url);

        // 只允许最多带一个参数作为资源地址
        if(url.contains("&")) url = url.split("&")[0];
        SysResource cur = sysResourceService.getByUrl(url);
        if (cur == null && url.contains("?")) {
            url = url.split("\\?")[0];
            cur = sysResourceService.getByUrl(url);
        }

        Set parentIdSet = CmTag.getParentSet(url);
        Map<String, Object> resultMap = success();
        resultMap.put("cur", cur);
        resultMap.put("parents", parentIdSet);

        return resultMap;
    }
}
