package service.oa;

import controller.global.OpException;
import domain.oa.OaTask;
import domain.oa.OaTaskFile;
import domain.oa.OaTaskFileExample;
import domain.oa.OaTaskUser;
import domain.oa.OaTaskUserExample;
import domain.oa.OaTaskUserView;
import domain.oa.OaTaskUserViewExample;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.OaConstants;
import sys.constants.RoleConstants;
import sys.utils.ContextHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OaTaskService extends BaseMapper {

    @Autowired
    private OaTaskUserService oaTaskUserService;

    // 任务附件
    public List<OaTaskFile> getTaskFiles(int taskId){

        OaTaskFileExample example = new OaTaskFileExample();
        example.createCriteria().andTaskIdEqualTo(taskId);
        example.setOrderByClause("create_time asc");
        List<OaTaskFile> oaTaskFiles = oaTaskFileMapper.selectByExample(example);

        return oaTaskFiles;
    }

    // 只能管理自己所属角色（干部管理员、党建管理员、培训管理员）发布的任务
    public Set<Byte> getAdminTypes(){

        Set<Byte> types = new HashSet<>();
        if(ShiroHelper.hasRole(RoleConstants.ROLE_CADREADMIN)){
            types.add(OaConstants.OA_TASK_TYPE_CADRE);
        }
        if(ShiroHelper.hasRole(RoleConstants.ROLE_ODADMIN)){
            types.add(OaConstants.OA_TASK_TYPE_OW);
        }
        if(ShiroHelper.hasRole(RoleConstants.ROLE_CET_ADMIN)){
            types.add(OaConstants.OA_TASK_TYPE_TRAIN);
        }

        return types;
    }
    // 检查操作权限
    public void checkAuth(byte type){

        Set<Byte> adminTypes = getAdminTypes();
        if(!adminTypes.contains(type)){
            throw new UnauthorizedException();
        }
    }

    @Transactional
    public void insertSelective(OaTask record) {

        record.setUserId(ShiroHelper.getCurrentUserId());
        record.setStatus(OaConstants.OA_TASK_STATUS_INIT);
        record.setIsPublish(false);
        record.setIsDelete(false);
        record.setCreateTime(new Date());
        record.setIp(ContextHelper.getRealIp());

        oaTaskMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {
        oaTaskMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            OaTask oaTask = oaTaskMapper.selectByPrimaryKey(id);
            checkAuth(oaTask.getType());

            OaTask record = new OaTask();
            record.setId(oaTask.getId());
            record.setIsDelete(true);
            oaTaskMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void batchAbolish(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            OaTask oaTask = oaTaskMapper.selectByPrimaryKey(id);
            checkAuth(oaTask.getType());

            OaTask record = new OaTask();
            record.setId(oaTask.getId());
            record.setStatus(OaConstants.OA_TASK_STATUS_ABOLISH);

            oaTaskMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public int updateByPrimaryKeySelective(OaTask record) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(record.getId());
        checkAuth(oaTask.getType());

        record.setType(null);
        return oaTaskMapper.updateByPrimaryKeySelective(record);
    }

    // 更新任务对象列表
    @Transactional
    public void updateTaskUserIds(int taskId, Integer[] userIds) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        checkAuth(oaTask.getType());

        if(userIds!=null && userIds.length>0){
            // 先删除未选择的任务对象（假删除）
            OaTaskUserExample example = new OaTaskUserExample();
            example.createCriteria().andTaskIdEqualTo(taskId)
                    .andUserIdNotIn(Arrays.asList(userIds));
            OaTaskUser record = new OaTaskUser();
            record.setIsDelete(true);
            oaTaskUserMapper.updateByExampleSelective(record, example);
        }

        if (userIds == null || userIds.length == 0) return;

        for (Integer userId : userIds) {

            {
                // 先判断是否已经被指定为该任务负责人了，如果是，则不允许设定为任务对象
                OaTaskUserViewExample example = new OaTaskUserViewExample();
                example.createCriteria().andTaskIdEqualTo(taskId)
                        .andIsDeleteEqualTo(false)
                        .andAssignUserIdEqualTo(userId);
                List<OaTaskUserView> oaTaskUserViews = oaTaskUserViewMapper.selectByExample(example);
                if(oaTaskUserViews.size()>0){
                    OaTaskUserView ouv = oaTaskUserViews.get(0);
                    throw new OpException("{0}已经被{1}指定为该任务的负责人，不可添加为任务对象。",
                            ouv.getAssignRealname(), ouv.getRealname());
                }
            }

            OaTaskUser oaTaskUser = oaTaskUserService.getOaTaskUser(taskId, userId);

            if(oaTaskUser==null) {
                OaTaskUser record = new OaTaskUser();
                record.setTaskId(taskId);
                record.setUserId(userId);
                record.setHasReport(false);
                record.setIsDelete(false);
                oaTaskUserMapper.insertSelective(record);
            }else{
                // 如果任务对象已经存在，则更新为未删除状态
                int id = oaTaskUser.getId();
                OaTaskUser record = new OaTaskUser();
                record.setId(id);
                record.setIsDelete(false);
                oaTaskUserMapper.updateByPrimaryKeySelective(record);

                // 删除指定负责人
                commonMapper.excuteSql("update oa_task_user set assign_user_id=null, assign_user_mobile=null " +
                        "where id=" + id);
            }
        }
    }

    // 任务完结
    @Transactional
    public void finish(int taskId) {

        OaTaskUserExample example = new OaTaskUserExample();
        example.createCriteria().andTaskIdEqualTo(taskId)
                .andIsDeleteEqualTo(false)
                .andStatusNotEqualTo(OaConstants.OA_TASK_USER_STATUS_PASS);
        long notPassCount = oaTaskUserMapper.countByExample(example);
        if(notPassCount>0){
            throw new OpException("还有{0}个未完成任务的任务对象", notPassCount);
        }

        OaTask record = new OaTask();
        record.setId(taskId);
        record.setStatus(OaConstants.OA_TASK_STATUS_FINISH);

        updateByPrimaryKeySelective(record);
    }
}
