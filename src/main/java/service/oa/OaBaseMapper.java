package service.oa;

import controller.global.OpException;
import domain.oa.OaTask;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.oa.*;
import persistence.oa.common.IOaTaskMapper;
import service.CoreBaseMapper;
import shiro.ShiroHelper;
import sys.utils.NumberUtils;

import java.util.HashSet;
import java.util.Set;

public class OaBaseMapper extends CoreBaseMapper {

    //党统
    @Autowired
    protected OaGridMapper oaGridMapper;
    @Autowired(required = false)
    protected OaGridPartyMapper oaGridPartyMapper;
    @Autowired(required = false)
    protected OaGridPartyDataMapper oaGridPartyDataMapper;

    /**
     * 协同办公
     */
    @Autowired(required = false)
    protected OaTaskMapper oaTaskMapper;
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
    @Autowired(required = false)
    protected IOaTaskMapper iOaTaskMapper;

    // 检查操作权限
    public void checkAuth(Integer taskId) {

        Integer currentUserId = ShiroHelper.getCurrentUserId();

        // 拥有全部权限
        if (ShiroHelper.isPermitted("oaTaskShowAll:*")) return;

        if (taskId != null) {
            OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
            Integer userId = oaTask.getUserId();
            Set<Integer> relateUserIds = new HashSet<>();
            relateUserIds.add(userId);
            relateUserIds.addAll(NumberUtils.toIntSet(oaTask.getUserIds(), ","));

            if (!relateUserIds.contains(currentUserId)) {
                throw new OpException("没有权限操作。");
            }
        }
    }
}
