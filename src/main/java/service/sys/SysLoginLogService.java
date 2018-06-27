package service.sys;

import bean.LoginUser;
import domain.sys.SysConfig;
import domain.sys.SysLoginLog;
import domain.sys.SysLoginLogExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.shiro.OnlineSession;
import sys.utils.ContentUtils;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
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
    private SysConfigService sysConfigService;
    @Autowired
    private SessionDAO sessionDAO;

    // 给登录用户设置登录超时
    public void setTimeout(Subject subject){

        if(subject==null) return;

        SysConfig sysConfig = sysConfigService.get();
        Integer loginTimeout = sysConfig.getLoginTimeout(); // 系统设置的登录超时

        ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
        if(shiroUser==null) return;

        Integer timeout = shiroUser.getTimeout(); // 给单个用户设置的登录超时
        if(timeout!=null && timeout>0){

            subject.getSession().setTimeout(timeout*60*1000);
        }else if(loginTimeout!=null && loginTimeout>0){

            subject.getSession().setTimeout(loginTimeout*60*1000);
        }
    }

    // 记录当前用户登录日记 , 如果没登录成功，那么userId=null
    public String log(Integer userId, String username, byte type, boolean isSuccess, String remark) {

        Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
        OnlineSession session = (OnlineSession) sessionDAO.readSession(sessionId);

        String userAgent = session.getUserAgent();
        String ip = session.getHost();

        SysLoginLog _loginLog = new SysLoginLog();
        _loginLog.setUserId(userId);
        _loginLog.setUsername(ContentUtils.substr(username, 0, 50, "..."));
        _loginLog.setAgent(userAgent);
        _loginLog.setLoginIp(ip);
        _loginLog.setCountry(session.getCountry());
        _loginLog.setArea(session.getArea());
        _loginLog.setType(type);
        _loginLog.setSuccess(isSuccess);
        _loginLog.setRemark(remark);
        _loginLog.setLoginTime(new Date());

        SysLoginLogExample example = new SysLoginLogExample();
        example.createCriteria().andUsernameEqualTo(username);
        example.setOrderByClause("login_time desc");
        List<SysLoginLog> sysLoginLogs = sysLoginLogMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (sysLoginLogs.size() == 1) {
            SysLoginLog lastLoginLog = sysLoginLogs.get(0);
            _loginLog.setLastLoginIp(lastLoginLog.getLoginIp());
            _loginLog.setLastLoginTime(lastLoginLog.getLoginTime());
            _loginLog.setLastCountry(lastLoginLog.getCountry());
            _loginLog.setLastArea(lastLoginLog.getArea());
        }

        sysLoginLogMapper.insertSelective(_loginLog);

        return String.format("账号：%s %s登录,结果：%s, %s, %s, %s",
                username, SystemConstants.LOGIN_TYPE_MAP.get(type), isSuccess, remark, sessionId, userAgent);
    }

    // 记录评课用户登录日记 , 如果没登录成功，那么userId=null
    public String trainInspectorLoginlog(Integer userId, String username, boolean isSuccess, String remark) {

        HttpServletRequest request = ContextHelper.getRequest();
        /*  ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();*/

        String userAgent = RequestUtils.getUserAgent(request);
        String ip = IpUtils.getRealIp(request);

        byte type = SystemConstants.LOGIN_TYPE_TRAIN_INSPECTOR;
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
        example.createCriteria().andTypeEqualTo(type).andUsernameEqualTo(username);
        example.setOrderByClause("login_time desc");
        List<SysLoginLog> sysLoginLogs = sysLoginLogMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (sysLoginLogs.size() == 1) {
            SysLoginLog lastLoginLog = sysLoginLogs.get(0);
            _loginLog.setLastLoginIp(lastLoginLog.getLoginIp());
            _loginLog.setLastLoginTime(lastLoginLog.getLoginTime());
        }

        sysLoginLogMapper.insertSelective(_loginLog);

        return String.format("账号：%s %s登录,结果：%s, %s, %s", username, SystemConstants.LOGIN_TYPE_MAP.get(type), isSuccess, remark, userAgent);
    }

    // 获取当前在线用户
    public List<LoginUser> getLoginUsers() {

        List<LoginUser> loginUsers = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {

            OnlineSession onlineSession = (OnlineSession) session;
            LoginUser loginUser = new LoginUser();
            loginUser.setSid(String.valueOf(onlineSession.getId()));
            loginUser.setIp(onlineSession.getHost());
            loginUser.setCountry(onlineSession.getCountry());
            loginUser.setArea(onlineSession.getArea());
            loginUser.setUserAgent(onlineSession.getUserAgent());
            loginUser.setStartTimestamp(onlineSession.getStartTimestamp());
            loginUser.setLastAccessTime(onlineSession.getLastAccessTime());
            loginUser.setTimeOut(onlineSession.getTimeout());
            PrincipalCollection principals = (PrincipalCollection) onlineSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (principals != null) {
                ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
                loginUser.setShiroUser(shiroUser);
                loginUsers.add(loginUser);
            }
        }

        return loginUsers;
    }

    // 获取用户登录状态
    public boolean isOnline(String username) {

        Collection<Session> sessions = sessionDAO.getActiveSessions();

        for (Session session : sessions) {
            OnlineSession onlineSession = (OnlineSession) session;
            PrincipalCollection principals = (PrincipalCollection) onlineSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (principals != null) {
                ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
                if(StringUtils.equals(username, shiroUser.getUsername())){
                    return true;
                }
            }
        }
        return false;
    }
}
