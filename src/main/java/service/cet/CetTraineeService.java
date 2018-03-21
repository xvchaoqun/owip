package service.cet;

import domain.cet.CetTrainee;
import domain.cet.CetTraineeCadreView;
import domain.cet.CetTraineeCadreViewExample;
import domain.cet.CetTraineeExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CetTraineeService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;

    public CetTraineeCadreView get(int userId, int trainId){

        CetTraineeCadreViewExample example = new CetTraineeCadreViewExample();
        example.createCriteria().andUserIdEqualTo(userId).andTrainIdEqualTo(trainId);
        List<CetTraineeCadreView> cetTrainees = cetTraineeCadreViewMapper.selectByExample(example);

        return cetTrainees.size()>0?cetTrainees.get(0):null;
    }

    @Transactional
    public void del(Integer id){

        CetTrainee cetTrainee = cetTraineeMapper.selectByPrimaryKey(id);
        int userId = cetTrainee.getUserId();
        cetTraineeMapper.deleteByPrimaryKey(id);

        delRoleIfNotTrainee(userId);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        for (Integer id : ids) {
            del(id);
        }
    }

    // 检查是否删除参训人员角色
    public void delRoleIfNotTrainee(int userId){

        CetTraineeExample example = new CetTraineeExample();
        example.createCriteria().andUserIdEqualTo(userId);
        if(cetTraineeMapper.countByExample(example)==0) {
            sysUserService.delRole(userId, RoleConstants.ROLE_CET_TRAINEE);
        }
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetTrainee record){

        return cetTraineeMapper.updateByPrimaryKeySelective(record);
    }

    public Set<Integer> getSelectedTraineeUserIdSet(int trainId) {

        Set<Integer> userIdSet = new HashSet<>();

        CetTraineeExample example = new CetTraineeExample();
        example.createCriteria().andTrainIdEqualTo(trainId);
        List<CetTrainee> cetTrainees = cetTraineeMapper.selectByExample(example);

        for (CetTrainee cetTrainee : cetTrainees) {

            userIdSet.add(cetTrainee.getUserId());
        }

        return userIdSet;
    }

    @Transactional
    public void addOrUpdate(int trainId, int traineeTypeId, Integer[] userIds) {

        if(userIds==null || userIds.length==0) return;

        // 已选人员
        Set<Integer> selectedTraineeUserIdSet = getSelectedTraineeUserIdSet(trainId);
        // 提交更新人员
        Set<Integer> traineeUserIdSet = new HashSet<>();
        traineeUserIdSet.addAll(Arrays.asList(userIds));

        for (Integer userId : userIds) {

            if(selectedTraineeUserIdSet.contains(userId)) continue;

            CetTrainee record = new CetTrainee();
            record.setTrainId(trainId);
            record.setUserId(userId);
            record.setTraineeTypeId(traineeTypeId);
            cetTraineeMapper.insertSelective(record);

            sysUserService.addRole(userId, RoleConstants.ROLE_CET_TRAINEE);

            sysApprovalLogService.add(record.getId(), record.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                    "可选课人员", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    "新建");
        }

        // 需要删除的人员
        selectedTraineeUserIdSet.removeAll(traineeUserIdSet);
        for (Integer userId : selectedTraineeUserIdSet) {
            CetTraineeExample example = new CetTraineeExample();
            example.createCriteria().andTrainIdEqualTo(trainId).andUserIdEqualTo(userId);
            cetTraineeMapper.deleteByExample(example);

            delRoleIfNotTrainee(userId);
        }
    }
}
