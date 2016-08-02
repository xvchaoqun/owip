package controller.sys;

import bean.LoginUser;
import controller.BaseController;
import domain.sys.SysOnlineStatic;
import domain.sys.SysUser;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.helper.ShiroSecurityHelper;
import service.sys.SysOnlineStaticService;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by fafa on 2016/5/2.
 */
@Controller
public class SysOnlineLogController extends BaseController {

    @Autowired
    private  SysOnlineStaticService sysOnlineStaticService;

    @RequiresPermissions("sysOnlineLog:list")
    @RequestMapping("/sysOnlineLog")
    public String party() {

        return "index";
    }

    @RequiresPermissions("sysOnlineLog:list")
    @RequestMapping("/sysOnlineLog_page")
    public String sysOnlineLog_page(ModelMap modelMap) {

        modelMap.put("_onlineCount", sysLoginLogService.getLoginUsers().size());
        modelMap.put("_most", sysOnlineStaticService.getMost());

        return "sys/sysOnlineLog/sysOnlineLog_page";
    }

    @RequiresPermissions("sysOnlineLog:list")
    @RequestMapping("/sysOnlineLog_data")
    @ResponseBody
    public void sysOnlineLog_data(Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        List<LoginUser> loginUsers = sysLoginLogService.getLoginUsers();

        Collections.sort(loginUsers, new Comparator<LoginUser>() {
            @Override
            public int compare(LoginUser o1, LoginUser o2) {
                return o1.getStartTimestamp().before(o2.getStartTimestamp())?1:-1;
            }
        });

        int count = loginUsers.size();
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", loginUsers.subList((pageNo - 1) * pageSize, (pageNo*pageSize)>count?count:(pageNo*pageSize)));
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresRoles("admin")
    @RequiresPermissions("sysOnlineLog:kickout")
    @RequestMapping(value = "/sysOnlineLog_kickout", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysOnlineLog_kickout(@CurrentUser SysUser loginUser, @RequestParam(value = "ids[]")String[] ids) {

        String currentUsername = loginUser.getUsername();
        Set<String> usernames = new HashSet<>();
        for (String id : ids) {
            String username = ShiroSecurityHelper.getUsername(id);
            if(username!=null && !StringUtils.equals(username, currentUsername))  // 不能踢自己
                usernames.add(username);
        }

        if(usernames.size()>0) {
            ShiroSecurityHelper.kickOutUser(usernames);
            logService.log(SystemConstants.LOG_USER, "踢下线：" + StringUtils.join(usernames, ","));
        }

        return success();
    }

}
