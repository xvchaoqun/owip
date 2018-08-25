package service.cet;

import domain.cet.CetUpperTrainAdmin;
import domain.cet.CetUpperTrainAdminExample;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysUserService;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CetUpperTrainAdminService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    // 管理的单位
    public Set<Integer> adminUnitIdSet(int userId){

        CetUpperTrainAdminExample example = new CetUpperTrainAdminExample();
        example.createCriteria().andUserIdEqualTo(userId).andUnitIdIsNotNull();
        List<CetUpperTrainAdmin> cetUpperTrainAdmins = cetUpperTrainAdminMapper.selectByExample(example);
        Set<Integer> adminUnitIds = new HashSet<>();
        for (CetUpperTrainAdmin cetUpperTrainAdmin : cetUpperTrainAdmins) {
            adminUnitIds.add(cetUpperTrainAdmin.getUnitId());
        }

        return adminUnitIds;
    }

    // 管理的校领导
    public Set<Integer> adminLeaderUserIdSet(int userId){

        CetUpperTrainAdminExample example = new CetUpperTrainAdminExample();
        example.createCriteria().andUserIdEqualTo(userId).andLeaderUserIdIsNotNull();
        List<CetUpperTrainAdmin> cetUpperTrainAdmins = cetUpperTrainAdminMapper.selectByExample(example);
        Set<Integer> adminLeaderUserIds = new HashSet<>();
        for (CetUpperTrainAdmin cetUpperTrainAdmin : cetUpperTrainAdmins) {
            adminLeaderUserIds.add(cetUpperTrainAdmin.getLeaderUserId());
        }

        return adminLeaderUserIds;
    }

    @Transactional
    public void insertSelective(CetUpperTrainAdmin record){

        cetUpperTrainAdminMapper.insertSelective(record);

        int userId = record.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        if(!CmTag.hasRole(uv.getUsername(), RoleConstants.ROLE_CET_ADMIN_UNIT)) {
            sysUserService.addRole(userId, RoleConstants.ROLE_CET_ADMIN_UNIT);
        }
    }

    @Transactional
    public void del(Integer id){

        CetUpperTrainAdmin cetUpperTrainAdmin = cetUpperTrainAdminMapper.selectByPrimaryKey(id);
        int userId = cetUpperTrainAdmin.getUserId();
        cetUpperTrainAdminMapper.deleteByPrimaryKey(id);

        CetUpperTrainAdminExample example = new CetUpperTrainAdminExample();
        example.createCriteria().andUserIdEqualTo(userId);
        if(cetUpperTrainAdminMapper.countByExample(example)==0){
            sysUserService.delRole(userId, RoleConstants.ROLE_CET_ADMIN_UNIT);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {
            del(id);
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(CetUpperTrainAdmin record){

        CetUpperTrainAdmin oldRecord = cetUpperTrainAdminMapper.selectByPrimaryKey(record.getId());

        cetUpperTrainAdminMapper.updateByPrimaryKeySelective(record);

        int userId = oldRecord.getUserId();
        if(record.getUserId()!=null && userId!=record.getUserId()){

            CetUpperTrainAdminExample example = new CetUpperTrainAdminExample();
            example.createCriteria().andUserIdEqualTo(userId);
            if(cetUpperTrainAdminMapper.countByExample(example)==0){
                sysUserService.delRole(userId, RoleConstants.ROLE_CET_ADMIN_UNIT);
            }

            int newUserId = record.getUserId();
            SysUserView uv = sysUserService.findById(newUserId);
            if(!CmTag.hasRole(uv.getUsername(), RoleConstants.ROLE_CET_ADMIN_UNIT)) {
                sysUserService.addRole(newUserId, RoleConstants.ROLE_CET_ADMIN_UNIT);
            }
        }
    }
}
