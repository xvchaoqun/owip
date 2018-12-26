package service.sc.scGroup;

import domain.sc.scGroup.*;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.sc.ScBaseMapper;
import service.sys.SysUserService;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class ScGroupService extends ScBaseMapper {

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

    public TreeNode getTree(Set<Integer> selectIdSet){

        if(null == selectIdSet) selectIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "选择参会人";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        List<TreeNode> nowMembers = new ArrayList<TreeNode>();
        List<TreeNode> historyMembers = new ArrayList<TreeNode>();

        TreeNode node = new TreeNode();
        node.title = "现有成员";
        node.isFolder = true;
        node.expand = true;
        node.children = nowMembers;
        rootChildren.add(node);

        node = new TreeNode();
        node.title = "过去成员";
        node.isFolder = true;
        node.children = historyMembers;
        rootChildren.add(node);

        ScGroupMemberViewExample example = new ScGroupMemberViewExample();
        example.setOrderByClause("is_current desc, sort_order desc");
        List<ScGroupMemberView> scGroupMemberViews = scGroupMemberViewMapper.selectByExample(example);

        for (ScGroupMemberView sgm : scGroupMemberViews) {
            node = new TreeNode();
            node.title = sgm.getRealname() + (StringUtils.isNotBlank(sgm.getTitle())?"("+ sgm.getTitle()+")":"");
            node.key = sgm.getUserId() + "";
            if (selectIdSet.contains(sgm.getUserId().intValue())) {
                node.select = true;
            }

            if(sgm.getIsCurrent()){
                nowMembers.add(node);
            }else{
                historyMembers.add(node);
            }
        }

        return root;
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
