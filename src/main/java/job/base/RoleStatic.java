package job.base;

import domain.sys.SysRole;
import domain.sys.SysRoleExample;
import domain.sys.SysUserExample;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.sys.SysRoleMapper;
import persistence.sys.SysUserMapper;

import java.util.List;

public class RoleStatic implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        // 只更新数据库字段user_count，不影响系统缓存
        try {
            logger.debug("角色用户数量统计...");
            List<SysRole> sysRoles = sysRoleMapper.selectByExample(new SysRoleExample());
            for (SysRole sysRole : sysRoles) {
                int roleId = sysRole.getId();

                SysUserExample example = new SysUserExample();
                example.createCriteria().andRoleIdsLike("%,"+roleId+",%");
                int userCount = (int) sysUserMapper.countByExample(example);

                if(sysRole.getUserCount()==null || sysRole.getUserCount()!=userCount){

                    SysRole record = new SysRole();
                    record.setId(roleId);
                    record.setUserCount(userCount);

                    sysRoleMapper.updateByPrimaryKeySelective(record);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
