package service.sys;

import domain.MetaType;
import domain.SysLog;
import domain.SysLoginLog;
import domain.SysLoginLogExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import service.BaseMapper;
import service.sys.MetaTypeService;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.utils.IpUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2015/11/13.
 */
@Service
public class LogService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;

    public String log(String logType, String content){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

        SysLog record = new SysLog();
        record.setUserId(shiroUser.getId());
        record.setOperator(shiroUser.getUsername());
        record.setContent(content);
        record.setCreateTime(new Date());
        record.setIp(IpUtils.getRealIp(request));
        record.setAgent(RequestUtils.getUserAgent(request));
        String api = request.getRequestURI().substring(1);
        record.setApi(api);
        MetaType metaType = metaTypeService.codeKeyMap().get(logType);
        if(metaType!=null)
            record.setTypeId(metaType.getId());
        record.setStatus(SystemConstants.AVAILABLE);

        sysLogMapper.insertSelective(record );

        if(StringUtils.equals(logType, SystemConstants.LOG_LOGIN)){

            SysLoginLog _loginLog = new SysLoginLog();
            _loginLog.setUserId(shiroUser.getId());
            _loginLog.setAgent(record.getAgent());
            _loginLog.setLoginIp(record.getIp());
            _loginLog.setLoginTime(record.getCreateTime());

            SysLoginLogExample example = new SysLoginLogExample();
            example.createCriteria().andUserIdEqualTo(shiroUser.getId());
            example.setOrderByClause("login_time desc");
            List<SysLoginLog> sysLoginLogs = sysLoginLogMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
            if(sysLoginLogs.size()==1){
                SysLoginLog lastLoginLog = sysLoginLogs.get(0);
                _loginLog.setLastLoginIp(lastLoginLog.getLoginIp());
                _loginLog.setLastLoginTime(lastLoginLog.getLoginTime());
            }

            sysLoginLogMapper.insertSelective(_loginLog);
        }

        return String.format("账号：%s, 类别：%s, %s", shiroUser.getUsername(),
                SystemConstants.USER_TYPE_MAP.get(shiroUser.getType()), content );
    }
}
