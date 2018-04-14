package service.sc.scGroup;

import domain.sc.scGroup.ScGroup;
import domain.sc.scGroup.ScGroupExample;
import domain.sc.scGroup.ScGroupParticipant;
import domain.sc.scGroup.ScGroupParticipantExample;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.sys.SysUserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScGroupService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScGroupExample example = new ScGroupExample();
        ScGroupExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scGroupMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScGroup record, Integer[] userIds){

        scGroupMapper.insertSelective(record);

        updateMemberUserIds(record.getId(), userIds);
    }

    @Transactional
    public void del(Integer id){

        scGroupMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScGroupExample example = new ScGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scGroupMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScGroup record, Integer[] userIds){

        scGroupMapper.updateByPrimaryKeySelective(record);
        updateMemberUserIds(record.getId(), userIds);
    }

    // 获取所有参会人
    public List<ScGroupParticipant> getMemberList(int groupId) {

        ScGroupParticipantExample example = new ScGroupParticipantExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        example.setOrderByClause("id asc");

        return scGroupParticipantMapper.selectByExample(example);
    }

    // 获取所有参会人
    public List<SysUserView> getMemberUserList(int groupId) {

        List<ScGroupParticipant> scGroupParticipants = getMemberList(groupId);
        List<SysUserView> userList = new ArrayList<>();
        for (ScGroupParticipant scGroupParticipant : scGroupParticipants) {

            SysUserView uv = sysUserService.findById(scGroupParticipant.getUserId());
            userList.add(uv);
        }

        return userList;
    }

    // 获取所有参会人userId
    public Set<Integer> getMemberUserIds(int groupId) {

        Set<Integer> userIds = new HashSet<>();
        List<ScGroupParticipant> scGroupParticipants = getMemberList(groupId);
        for (ScGroupParticipant scGroupParticipant : scGroupParticipants) {
            userIds.add(scGroupParticipant.getUserId());
        }

        return userIds;
    }

    // 更新所有参会人userId
    @Transactional
    public void updateMemberUserIds(Integer groupId, Integer[] userIds) {

        {
            ScGroupParticipantExample example = new ScGroupParticipantExample();
            ScGroupParticipantExample.Criteria criteria = example.createCriteria().andGroupIdEqualTo(groupId);

            scGroupParticipantMapper.deleteByExample(example);
        }

        if (userIds == null || userIds.length == 0) return;

        for (Integer userId : userIds) {

            ScGroupParticipant record = new ScGroupParticipant();
            record.setGroupId(groupId);
            record.setUserId(userId);
            scGroupParticipantMapper.insertSelective(record);
        }

    }

}
