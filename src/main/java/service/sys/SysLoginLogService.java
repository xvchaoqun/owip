package service.sys;

import bean.LoginUser;
import domain.sys.SysLoginLog;
import domain.sys.SysLoginLogExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.helper.ContextHelper;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.utils.ContentUtils;
import sys.utils.IpUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2016/6/10.
 */
@Service
public class SysLoginLogService extends BaseMapper {

    @Autowired
    private SessionDAO sessionDAO;

    // 记录当前用户登录日记 , 如果没登录成功，那么userId=null
    public String log(Integer userId, String username, byte type, boolean isSuccess, String remark){

        HttpServletRequest request = ContextHelper.getRequest();
        /*  ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();*/

        String userAgent = RequestUtils.getUserAgent(request);
        String ip = IpUtils.getRealIp(request);

        SysLoginLog _loginLog = new SysLoginLog();
        _loginLog.setUserId(userId);
        _loginLog.setUsername(ContentUtils.substr(username, 0, 50, "..."));
        _loginLog.setAgent(userAgent);
        _loginLog.setLoginIp(ip);
        _loginLog.setType(type);
        _loginLog.setSuccess(isSuccess);
        _loginLog.setRemark(remark);
        _loginLog.setLoginTime(new Date());

        SysLoginLogExample example = new SysLoginLogExample();
        example.createCriteria().andUsernameEqualTo(username);
        example.setOrderByClause("login_time desc");
        List<SysLoginLog> sysLoginLogs = sysLoginLogMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(sysLoginLogs.size()==1){
            SysLoginLog lastLoginLog = sysLoginLogs.get(0);
            _loginLog.setLastLoginIp(lastLoginLog.getLoginIp());
            _loginLog.setLastLoginTime(lastLoginLog.getLoginTime());
        }

        sysLoginLogMapper.insertSelective(_loginLog);

        return String.format("账号：%s %s登录,结果：%s, %s, %s", username, SystemConstants.LOGIN_TYPE_MAP.get(type), isSuccess, remark, userAgent);
    }

    // 获取当前在线用户
    public List<LoginUser> getLoginUsers(){

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

        return loginUsers;
    }
}
