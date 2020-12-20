package service.sys;

import bean.LoginUser;
import domain.sys.SysOnlineStatic;
import domain.sys.SysOnlineStaticExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import shiro.ShiroUser;
import sys.constants.SystemConstants;

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
        if(latest==null || count != latest.getOnlineCount()){

            int bks = 0;
            int yjs = 0;
            int jzg = 0;
            for (LoginUser loginUser : loginUsers) {
                ShiroUser shiroUser = loginUser.getShiroUser();
                switch (shiroUser.getType().byteValue()){
                    case SystemConstants.USER_TYPE_BKS:
                        bks++; break;
                    case SystemConstants.USER_TYPE_SS:
                        yjs++; break;
                    case SystemConstants.USER_TYPE_JZG:
                        jzg++; break;
                }
            }

            SysOnlineStatic record = new SysOnlineStatic();
            record.setOnlineCount(count);
            record.setYjs(yjs);
            record.setBks(bks);
            record.setJzg(jzg);
            record.setCreateTime(new Date());
            sysOnlineStaticMapper.insertSelective(record);
        }
    }
    // 获取最近一次统计结果
    public SysOnlineStatic getLatest(){

        SysOnlineStaticExample example = new SysOnlineStaticExample();
        example.setOrderByClause("create_time desc");

        List<SysOnlineStatic> sysOnlineStatics = sysOnlineStaticMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(sysOnlineStatics.size()==1) return sysOnlineStatics.get(0);
        return null;
    }

    // 获取在线人数最多的时间点
    public SysOnlineStatic getMost(){

        SysOnlineStaticExample example = new SysOnlineStaticExample();
        example.setOrderByClause("online_count desc");
        List<SysOnlineStatic> sysOnlineStatics = sysOnlineStaticMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(sysOnlineStatics.size()==1) return sysOnlineStatics.get(0);
        return null;
    }
}
