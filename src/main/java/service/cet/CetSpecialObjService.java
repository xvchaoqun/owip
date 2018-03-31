package service.cet;

import domain.cet.CetSpecialObj;
import domain.cet.CetSpecialObjExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CetSpecialObjService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    
    @Transactional
    public void insertSelective(CetSpecialObj record){

        cetSpecialObjMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetSpecialObjMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetSpecialObjExample example = new CetSpecialObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetSpecialObjMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetSpecialObj record){

        return cetSpecialObjMapper.updateByPrimaryKeySelective(record);
    }

    public Set<Integer> getSelectedSpecialObjUserIdSet(int specialId) {

        Set<Integer> userIdSet = new HashSet<>();

        CetSpecialObjExample example = new CetSpecialObjExample();
        example.createCriteria().andSpecialIdEqualTo(specialId);
        List<CetSpecialObj> cetSpecialObjs = cetSpecialObjMapper.selectByExample(example);

        for (CetSpecialObj cetSpecialObj : cetSpecialObjs) {
            userIdSet.add(cetSpecialObj.getUserId());
        }

        return userIdSet;
    }

    @Transactional
    public void addOrUpdate(int specialId, byte type, Integer[] userIds) {

        if(userIds==null || userIds.length==0) return;

        // 已选人员
        Set<Integer> selectedSpecialObjUserIdSet = getSelectedSpecialObjUserIdSet(specialId);
        // 提交更新人员
        Set<Integer> specialeeUserIdSet = new HashSet<>();
        specialeeUserIdSet.addAll(Arrays.asList(userIds));

        for (Integer userId : userIds) {

            if(selectedSpecialObjUserIdSet.contains(userId)) continue;

            CetSpecialObj record = new CetSpecialObj();
            record.setSpecialId(specialId);
            record.setUserId(userId);
            record.setType(type);
            cetSpecialObjMapper.insertSelective(record);

            //sysUserService.addRole(userId, RoleConstants.ROLE_CET_TRAINEE);

            sysApprovalLogService.add(record.getId(), record.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_SPECIAL_OBJ,
                    "添加培训对象", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    "新建");
        }

        // 需要删除的人员
        selectedSpecialObjUserIdSet.removeAll(specialeeUserIdSet);
        for (Integer userId : selectedSpecialObjUserIdSet) {
            CetSpecialObjExample example = new CetSpecialObjExample();
            example.createCriteria().andSpecialIdEqualTo(specialId).andUserIdEqualTo(userId);
            cetSpecialObjMapper.deleteByExample(example);

            //delRoleIfNotSpecialObj(userId);
        }
    }

    @Transactional
    public void quit(boolean isQuit, Integer[] ids) {

        CetSpecialObj record = new CetSpecialObj();
        record.setIsQuit(isQuit);

        CetSpecialObjExample example = new CetSpecialObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andIsQuitEqualTo(!isQuit);
        cetSpecialObjMapper.updateByExampleSelective(record, example);
    }

    // 检查是否删除培训对象角色
   /* public void delRoleIfNotSpecialObj(int userId){

        CetSpecialObjExample example = new CetSpecialObjExample();
        example.createCriteria().andUserIdEqualTo(userId);
        if(cetSpecialObjMapper.countByExample(example)==0) {
            sysUserService.delRole(userId, RoleConstants.ROLE_CET_TRAINEE);
        }
    }*/
}
