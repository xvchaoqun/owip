package controller.global;

import controller.BaseController;
import domain.sys.SysResource;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.tags.CmTag;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MenuController extends BaseController {

    //private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/menu")
    public String menu(String username, Boolean isPreview, boolean isMobile, ModelMap modelMap) {

        if(BooleanUtils.isTrue(isPreview) && StringUtils.isBlank(username)) {
            return "menu";
        }
        // 管理员才允许查看他人的菜单
        if(StringUtils.isBlank(username) || !ShiroHelper.isPermitted("menu:preview")){
            username = ShiroHelper.getCurrentUsername();
        }

        Set<String> permissions = sysUserService.findPermissions(username, isMobile);
        List<SysResource> userMenus = sysUserService.makeMenus(permissions, isMobile);
        modelMap.put("menus", userMenus);

        return "menu";
    }

    @RequiresPermissions("menu:preview")
    @RequestMapping("/menu_preview")
    public String menu_preview(boolean isMobile, @RequestParam(value = "resIds[]", required = false) Integer[] resIds,
                               @RequestParam(value = "minusResIds[]", required = false) Integer[] minusResIds,
                               ModelMap modelMap) {

        Set<String> permissions = new HashSet<String>();
        Map<Integer, SysResource> sysResources = sysResourceService.getSortedSysResources(isMobile);
        if(resIds!=null) {
            for (Integer resId : resIds) {
                SysResource sysResource = sysResources.get(resId);
                if (sysResource != null && StringUtils.isNotBlank(sysResource.getPermission())) {
                    permissions.add(sysResource.getPermission().trim());
                }
            }
        }
        if(minusResIds!=null) {
            for (Integer minusResId : minusResIds) {
                SysResource sysResource = sysResources.get(minusResId);
                if (sysResource != null && StringUtils.isNotBlank(sysResource.getPermission())) {
                    permissions.remove(sysResource.getPermission().trim());
                }
            }
        }
        List<SysResource> userMenus = sysUserService.makeMenus(permissions, isMobile);
        modelMap.put("menus", userMenus);
        return "menu";
    }

    @RequiresPermissions("menu:preview")
    @RequestMapping("/menu_preview_byRoleId")
    public String menu_preview_byRoleId(boolean isMobile, Integer roleId, ModelMap modelMap) {

        if (roleId != null) {
            Set<String> rolePermissions = sysRoleService.getRolePermissions(roleId, isMobile);
            List<SysResource> userMenus = sysUserService.makeMenus(rolePermissions, isMobile);
            modelMap.put("menus", userMenus);
        }

        return "menu";
    }

    @RequiresPermissions("menu:preview")
    @RequestMapping("/menu_preview_byUser")
    public String menu_preview_byUser(boolean isMobile, Integer userId, ModelMap modelMap) {

        if (userId != null) {
            Set<String> userPermissions  = sysUserService.findUserPermission(userId,isMobile);
            List<SysResource> userMenus = sysUserService.makeMenus(userPermissions, isMobile);
            modelMap.put("menus", userMenus);
        }

        return "menu";
    }

    @RequestMapping("/menu_breadcrumbs")
    @ResponseBody
    public Map menu_breadcrumbs(String url, ModelMap modelMap) throws UnsupportedEncodingException {

        url = new String(Base64.decodeBase64(url), "UTF-8");
        SysResource cur;
        do {
            cur = sysResourceService.getByUrl(url);
            if(cur != null) break;

            int idx = url.lastIndexOf("&");
            if (idx == -1) {
                idx = url.indexOf("?");
                if(idx==-1) break;
            }
            url = url.substring(0, idx);

        }while (cur == null);

        //url = HtmlUtils.htmlUnescape(url);

        // 只允许最多带一个参数作为资源地址
        /*if(url.contains("&")) url = url.split("&")[0];
        SysResource cur = sysResourceService.getByUrl(url);
        if (cur == null && url.contains("?")) {
            url = url.split("\\?")[0];
            cur = sysResourceService.getByUrl(url);
        }*/

        Set parentIdSet = CmTag.getParentSet(url);
        Map<String, Object> resultMap = success();
        resultMap.put("cur", cur);
        resultMap.put("parents", parentIdSet);

        return resultMap;
    }
}
