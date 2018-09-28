package service.sys;

import domain.sys.SysLog;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import shiro.ShiroHelper;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by fafa on 2015/11/13.
 */
@Service
public class LogService extends BaseMapper {

    // 登录后的操作日志
    public String log(Integer logType, String content){

        HttpServletRequest request = ContextHelper.getRequest();
        ShiroUser shiroUser = ShiroHelper.getShiroUser();

        Object switchUser = request.getSession().getAttribute("_switchUser");
        if(switchUser!=null){
            content = String.format("[切换账号：%s]", switchUser) + content;
        }
        SysLog record = new SysLog();
        record.setUserId(shiroUser.getId());
        record.setOperator(shiroUser.getUsername());
        record.setContent(content);
        record.setCreateTime(new Date());
        record.setIp(IpUtils.getRealIp(request));
        record.setAgent(RequestUtils.getUserAgent(request));
        String api = request.getRequestURI().substring(1);
        record.setApi(api);
        record.setTypeId(logType);
        record.setStatus(SystemConstants.AVAILABLE);

        sysLogMapper.insertSelective(record );

        return String.format("账号：%s, 类别：%s, %s", shiroUser.getUsername(),
                SystemConstants.USER_TYPE_MAP.get(shiroUser.getType()), content );
    }

    // 未登录的操作日志
    public String log(Integer userId, String username, Integer logType, String content){

        HttpServletRequest request = ContextHelper.getRequest();

        SysLog record = new SysLog();
        record.setUserId(userId);
        record.setOperator(username);
        record.setContent(content);
        record.setCreateTime(new Date());
        record.setIp(IpUtils.getRealIp(request));
        record.setAgent(RequestUtils.getUserAgent(request));
        String api = request.getRequestURI().substring(1);
        record.setApi(api);
        record.setTypeId(logType);
        record.setStatus(SystemConstants.AVAILABLE);

        sysLogMapper.insertSelective(record );

        return String.format("账号：%s,  %s", username, content );
    }

    // 系统日志
    public String addlog(Integer logType, String content){

        SysLog record = new SysLog();
        record.setContent(content);
        record.setCreateTime(new Date());
        record.setAgent("系统日志");
        record.setTypeId(logType);
        record.setStatus(SystemConstants.AVAILABLE);

        sysLogMapper.insertSelective(record );

        return content;
    }
}
