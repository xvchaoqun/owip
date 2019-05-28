package service.oa;

import controller.global.OpException;
import domain.oa.OaTask;
import domain.oa.OaTaskAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.oa.*;
import service.CoreBaseMapper;
import shiro.ShiroHelper;
import sys.utils.NumberUtils;

import java.util.HashSet;
import java.util.Set;

public class OaBaseMapper extends CoreBaseMapper {

    /**
     * 协同办公
     */
    @Autowired(required = false)
    protected OaTaskMapper oaTaskMapper;
    @Autowired(required = false)
    protected OaTaskAdminMapper oaTaskAdminMapper;
    @Autowired(required = false)
    protected OaTaskViewMapper oaTaskViewMapper;
    @Autowired(required = false)
    protected OaTaskFileMapper oaTaskFileMapper;
    @Autowired(required = false)
    protected OaTaskMsgMapper oaTaskMsgMapper;
    @Autowired(required = false)
    protected OaTaskRemindMapper oaTaskRemindMapper;
    @Autowired(required = false)
    protected OaTaskUserMapper oaTaskUserMapper;
    @Autowired(required = false)
    protected OaTaskUserViewMapper oaTaskUserViewMapper;
    @Autowired(required = false)
    protected OaTaskUserFileMapper oaTaskUserFileMapper;

    // 检查操作权限
    public void checkAuth(Integer taskId, Integer type) {

        Integer currentUserId = ShiroHelper.getCurrentUserId();

        if(type!=null) {
            OaTaskAdmin oaTaskAdmin = oaTaskAdminMapper.selectByPrimaryKey(currentUserId);
            Set<Integer> adminTypeSet = NumberUtils.toIntSet(oaTaskAdmin.getTypes(), ",");

            if (!adminTypeSet.contains(type)) {
                throw new OpException("没有权限添加该工作类型。");
            }
        }

        if(taskId!=null) {
            OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
            Integer userId = oaTask.getUserId();
            Set<Integer> relateUserIds = new HashSet<>();
            relateUserIds.add(userId);
            relateUserIds.addAll(NumberUtils.toIntSet(oaTask.getUserIds(), ","));

            if(!relateUserIds.contains(currentUserId)){
                throw new OpException("没有权限操作。");
            }
        }
    }
}
