package service.oa;

import controller.global.OpException;
import persistence.oa.common.TaskUser;
import domain.oa.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.OaConstants;
import sys.constants.RoleConstants;
import sys.utils.ContextHelper;
import sys.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class OaTaskService extends OaBaseMapper {

    @Autowired
    private OaTaskUserService oaTaskUserService;
    @Autowired
    private SysUserService sysUserService;

    // 任务附件
    public List<OaTaskFile> getTaskFiles(int taskId) {

        OaTaskFileExample example = new OaTaskFileExample();
        example.createCriteria().andTaskIdEqualTo(taskId);
        example.setOrderByClause("create_time asc");
        List<OaTaskFile> oaTaskFiles = oaTaskFileMapper.selectByExample(example);

        return oaTaskFiles;
    }

    @Transactional
    public void insertSelective(OaTask record) {

        checkAuth(null);

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

        checkAuth(id);
        oaTaskMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            checkAuth(id);

            OaTask record = new OaTask();
            record.setId(id);
            record.setIsDelete(true);
            oaTaskMapper.updateByPrimaryKeySelective(record);

            // 确定“协同办公”角色
            OaTaskUserExample example = new OaTaskUserExample();
            example.createCriteria().andTaskIdEqualTo(id);
            List<OaTaskUser> oaTaskUsers = oaTaskUserMapper.selectByExample(example);
            for (OaTaskUser oaTaskUser : oaTaskUsers) {
                oaTaskUserService.refreshTaskUserRole(oaTaskUser.getUserId());
                oaTaskUserService.refreshTaskUserRole(oaTaskUser.getAssignUserId());
            }
        }
    }

    // 更新任务对象列表
    @Transactional
    public void updateTaskUsers(int taskId, List<TaskUser> taskUsers) {

        checkAuth(taskId);

        // 先删除未选择的任务对象（假删除），如果存在的话
        {
            List<Integer> userIds = new ArrayList<>();
            for (TaskUser taskUser : taskUsers) {
                userIds.add(taskUser.getUserId());
            }
            OaTaskUserExample example = new OaTaskUserExample();
            OaTaskUserExample.Criteria criteria = example.createCriteria().andTaskIdEqualTo(taskId);
            if (userIds.size() > 0) {
                criteria.andUserIdNotIn(userIds);
            }
            OaTaskUser record = new OaTaskUser();
            record.setIsDelete(true);
            oaTaskUserMapper.updateByExampleSelective(record, example);
        }

        for (int i = 0; i < taskUsers.size(); i++) {

            TaskUser taskUser = taskUsers.get(i);

            int userId = taskUser.getUserId();
            String mobile = taskUser.getMobile();
            String title = taskUser.getTitle();

            // 先判断是否已经被指定为该任务负责人了，如果是，则不允许设定为任务对象
            OaTaskUserView ouv = oaTaskUserService.getTaskUser(taskId, userId);
            if (ouv != null && ouv.getAssignUserId() != null && ouv.getAssignUserId() == userId) {

                throw new OpException("{0}已经被{1}指定为该任务的负责人，不可添加为任务对象。",
                        ouv.getAssignRealname(), ouv.getRealname());
            }

            if (ouv == null) {

                OaTaskUser record = new OaTaskUser();
                record.setTaskId(taskId);
                record.setUserId(userId);
                record.setMobile(mobile);
                record.setTitle(title);
                record.setHasReport(false);
                record.setIsDelete(false);
                record.setSortOrder(i + 1);
                oaTaskUserMapper.insertSelective(record);
            } else {

                // 如果任务对象已经存在，则更新为未删除状态
                int id = ouv.getId();
                OaTaskUser record = new OaTaskUser();
                record.setId(id);
                record.setMobile(mobile);
                record.setTitle(title);
                record.setIsDelete(false);
                record.setSortOrder(i + 1);
                oaTaskUserMapper.updateByPrimaryKeySelective(record);

                // 删除指定负责人
                commonMapper.excuteSql("update oa_task_user set assign_user_id=null, assign_user_mobile=null " +
                        "where id=" + id);
            }
        }

        // 确定“协同办公”角色
        OaTaskUserExample example = new OaTaskUserExample();
        example.createCriteria().andTaskIdEqualTo(taskId);
        List<OaTaskUser> oaTaskUsers = oaTaskUserMapper.selectByExample(example);
        for (OaTaskUser oaTaskUser : oaTaskUsers) {
            oaTaskUserService.refreshTaskUserRole(oaTaskUser.getUserId());
            oaTaskUserService.refreshTaskUserRole(oaTaskUser.getAssignUserId());
        }

        iOaTaskMapper.refreshCount(taskId);
    }

    @Transactional
    public void batchAbolish(Integer[] ids, boolean isAbolish) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            checkAuth(id);

            OaTask record = new OaTask();
            record.setId(id);
            record.setStatus(isAbolish?OaConstants.OA_TASK_STATUS_ABOLISH:OaConstants.OA_TASK_STATUS_PUBLISH);

            oaTaskMapper.updateByPrimaryKeySelective(record);

            // 确定“协同办公”角色
            OaTaskUserExample example = new OaTaskUserExample();
            example.createCriteria().andTaskIdEqualTo(id);
            List<OaTaskUser> oaTaskUsers = oaTaskUserMapper.selectByExample(example);
            for (OaTaskUser oaTaskUser : oaTaskUsers) {
                oaTaskUserService.refreshTaskUserRole(oaTaskUser.getUserId());
                oaTaskUserService.refreshTaskUserRole(oaTaskUser.getAssignUserId());
            }
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(OaTask record) {

        checkAuth(record.getId());

        record.setType(null);
        oaTaskMapper.updateByPrimaryKeySelective(record);

        // 确定“协同办公”角色
        OaTaskUserExample example = new OaTaskUserExample();
        example.createCriteria().andTaskIdEqualTo(record.getId());
        List<OaTaskUser> oaTaskUsers = oaTaskUserMapper.selectByExample(example);
        for (OaTaskUser oaTaskUser : oaTaskUsers) {
            oaTaskUserService.refreshTaskUserRole(oaTaskUser.getUserId());
            oaTaskUserService.refreshTaskUserRole(oaTaskUser.getAssignUserId());
        }
    }

    // 任务完结
    @Transactional
    public void finish(int taskId, boolean isFinish) {

        checkAuth(taskId);

        /*OaTaskUserExample example = new OaTaskUserExample();
        example.createCriteria().andTaskIdEqualTo(taskId)
                .andIsDeleteEqualTo(false)
                .andStatusNotEqualTo(OaConstants.OA_TASK_USER_STATUS_PASS);
        long notPassCount = oaTaskUserMapper.countByExample(example);
        if (notPassCount > 0) {
            throw new OpException("还有{0}个未完成任务的任务对象", notPassCount);
        }*/

        OaTask record = new OaTask();
        record.setId(taskId);
        record.setStatus(isFinish?OaConstants.OA_TASK_STATUS_FINISH:OaConstants.OA_TASK_STATUS_PUBLISH);

        updateByPrimaryKeySelective(record);
    }

    // 共享任务
    @Transactional
    public void share(int taskId, int userId, boolean share) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        Set<Integer> userIdSet = NumberUtils.toIntSet(oaTask.getUserIds(), ",");
        if (share) {
            userIdSet.add(userId);
        } else {
            userIdSet.remove(userId);
        }

        OaTask record = new OaTask();
        record.setId(taskId);
        record.setUserIds(StringUtils.join(userIdSet, ","));
        updateByPrimaryKeySelective(record);

        if (share) {

            sysUserService.addRole(userId, RoleConstants.ROLE_OA_ADMIN);
        }
    }

    public List<Integer> getAdminAnduserId(Integer taskId){

        OaTaskUserViewExample example = new OaTaskUserViewExample();
        example.createCriteria().andTaskIdEqualTo(taskId)
                .andTaskIsDeleteEqualTo(false).andTaskIsPublishEqualTo(true);

        List<Integer> userIdList = new ArrayList<>();
        List<OaTaskUserView> oaTaskUserViews = oaTaskUserViewMapper.selectByExample(example);

        for (OaTaskUserView oaTaskUserView : oaTaskUserViews) {

            if (oaTaskUserView.getUserId() != null) {//任务对象
                userIdList.add(oaTaskUserView.getUserId());
            }
            if (oaTaskUserView.getAssignUserId() != null) {//被指定负责人
                userIdList.add(oaTaskUserView.getAssignUserId());
            }
            if (oaTaskUserView.getTaskUserId() != null) {//创建人
                userIdList.add(oaTaskUserView.getTaskUserId());
            }
            String userIds = oaTaskUserView.getTaskUserIds();
            if (StringUtils.isNotBlank(userIds)) {//共享任务人

                for (String userId : userIds.split(",")) {
                    userIdList.add(Integer.parseInt(userId));
                }
            }
        }
        return userIdList;
    }
}
