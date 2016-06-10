package service.sys;

import bean.LoginUser;
import domain.SysOnlineStatic;
import domain.SysOnlineStaticExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import shiro.ShiroUser;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2016/6/10.
 */
@Service
public class SysOnlineStaticService extends BaseMapper {

    @Autowired
    private SysLoginLogService sysLoginLogService;

    // 统计在线人数，如果和上次统计数量不同，则记录下来
    public void stat(){
        SysOnlineStatic latest = getLatest();
        List<LoginUser> loginUsers = sysLoginLogService.getLoginUsers();
        int count = loginUsers.size();
        if(latest==null || count != latest.getCount()){

            int bks = 0;
            int yjs = 0;
            int jzg = 0;
            for (LoginUser loginUser : loginUsers) {
                ShiroUser shiroUser = loginUser.getShiroUser();
                switch (shiroUser.getType().byteValue()){
                    case SystemConstants.USER_TYPE_BKS:
                        bks++; break;
                    case SystemConstants.USER_TYPE_YJS:
                        yjs++; break;
                    case SystemConstants.USER_TYPE_JZG:
                        jzg++; break;
                }
            }

            SysOnlineStatic record = new SysOnlineStatic();
            record.setCount(count);
            record.setYjs(yjs);
            record.setBks(bks);
            record.setJzg(jzg);
            record.setCreateTime(new Date());
            sysOnlineStaticMapper.insertSelective(record);
        }
    }

    public SysOnlineStatic getLatest(){

        SysOnlineStaticExample example = new SysOnlineStaticExample();
        example.setOrderByClause("create_time desc");

        List<SysOnlineStatic> sysOnlineStatics = sysOnlineStaticMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(sysOnlineStatics.size()==1) return sysOnlineStatics.get(0);
        return null;
    }
}
