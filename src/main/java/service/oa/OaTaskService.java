package service.oa;

import controller.global.OpException;
import controller.oa.TaskUser;
import domain.base.MetaType;
import domain.oa.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.constants.OaConstants;
import sys.tags.CmTag;
import sys.utils.ContextHelper;

import java.util.*;

@Service
public class OaTaskService extends OaBaseMapper {

    @Autowired
    private OaTaskUserService oaTaskUserService;

    // 任务附件
    public List<OaTaskFile> getTaskFiles(int taskId) {

        OaTaskFileExample example = new OaTaskFileExample();
        example.createCriteria().andTaskIdEqualTo(taskId);
        example.setOrderByClause("create_time asc");
        List<OaTaskFile> oaTaskFiles = oaTaskFileMapper.selectByExample(example);

        return oaTaskFiles;
    }

    // 获取管理员权限对应的响应的工作类型
    public List<Integer> getOaTaskTypes() {

        Set<Integer> oaTaskTypes = new HashSet<>();
        Map<Integer, MetaType> oaTaskTypeMap = CmTag.getMetaTypes("mc_oa_task_type");

        for (MetaType oaTaskType : oaTaskTypeMap.values()) {
            String permission = oaTaskType.getExtraAttr();
            if(StringUtils.isNotBlank(permission) && ShiroHelper.isPermitted(permission)){
                oaTaskTypes.add(oaTaskType.getId());
            }
        }

        return new ArrayList<>(oaTaskTypes);
    }

    // 检查操作权限
    public void checkAuth(int type) {

        MetaType oaTaskType = metaTypeMapper.selectByPrimaryKey(type);
        if(oaTaskType==null){
            throw new OpException("工作类型为空。");
        }

        SecurityUtils.getSubject().checkPermission(oaTaskType.getExtraAttr());
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

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        checkAuth(oaTask.getType());

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

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(record.getId());
        checkAuth(oaTask.getType());

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
    public void finish(int taskId) {

        OaTaskUserExample example = new OaTaskUserExample();
        example.createCriteria().andTaskIdEqualTo(taskId)
                .andIsDeleteEqualTo(false)
                .andStatusNotEqualTo(OaConstants.OA_TASK_USER_STATUS_PASS);
        long notPassCount = oaTaskUserMapper.countByExample(example);
        if (notPassCount > 0) {
            throw new OpException("还有{0}个未完成任务的任务对象", notPassCount);
        }

        OaTask record = new OaTask();
        record.setId(taskId);
        record.setStatus(OaConstants.OA_TASK_STATUS_FINISH);

        updateByPrimaryKeySelective(record);
    }
}
