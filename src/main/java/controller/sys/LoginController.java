package controller.sys;

import bean.LoginUser;
import controller.BaseController;
import domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.helper.ShiroSecurityHelper;
import shiro.ShiroUser;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by fafa on 2016/5/2.
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
    @Autowired
    private SessionDAO sessionDAO;

    @RequiresPermissions("login:list")
    @RequestMapping("/users")
    public String party() {

        return "index";
    }

    @RequiresPermissions("login:list")
    @RequestMapping("/users_page")
    public String party_page(ModelMap modelMap) {

        return "sys/login/users_page";
    }

    @RequiresPermissions("login:list")
    @RequestMapping("/users_data")
    @ResponseBody
    public void users_data(Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        List<LoginUser> loginUsers = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for(Session session:sessions){
            LoginUser loginUser = new LoginUser();
            loginUser.setSid(String.valueOf(session.getId()));
            loginUser.setIp(session.getHost());
            loginUser.setStartTimestamp(session.getStartTimestamp());
            loginUser.setLastAccessTime(session.getLastAccessTime());
            loginUser.setTimeOut(session.getTimeout());
            PrincipalCollection principals = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(principals!=null) {
                ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
                loginUser.setShiroUser(shiroUser);
                loginUsers.add(loginUser);
            }
        }
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
    @RequiresPermissions("login:kickout")
    @RequestMapping(value = "/kickout", method = RequestMethod.POST)
    @ResponseBody
    public Map do_kickout(@RequestParam(value = "ids[]")int[] ids) {

        Set<String> usernames = new HashSet<>();
        for (int id : ids) {
            SysUser sysUser = sysUserService.findById(id);
            usernames.add(sysUser.getUsername());
        }
        ShiroSecurityHelper.kickOutUser(usernames);
        return success();
    }

}
