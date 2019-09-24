package service.party;

import domain.party.BranchGroup;
import domain.party.BranchGroupMember;
import domain.party.BranchGroupMemberExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.party.BranchGroupMapper;
import persistence.party.BranchGroupMemberMapper;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BranchGroupMemberService extends BaseMapper {

    @Autowired
    private BranchGroupMemberMapper branchGroupMemberMapper;
    @Autowired
    private BranchGroupMapper branchGroupMapper;

    public boolean idDuplicate(Integer id, Integer groupId, Integer userId,Boolean isLeader){

        BranchGroupMemberExample example = new BranchGroupMemberExample();
        BranchGroupMemberExample.Criteria criteria = example.createCriteria();
        criteria.andGroupIdEqualTo(groupId);
        if (isLeader){
            criteria.andIsLeaderEqualTo(isLeader);
        }else {
            criteria.andUserIdEqualTo(userId);
        }
        if(id!=null) criteria.andIdNotEqualTo(id);

        return branchGroupMemberMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(BranchGroupMember record){

        record.setSortOrder(getNextSortOrder("ow_branch_group_member", "group_id="+record.getGroupId()));
        branchGroupMemberMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        branchGroupMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        BranchGroupMemberExample example = new BranchGroupMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        branchGroupMemberMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(BranchGroupMember record){

        branchGroupMemberMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, BranchGroupMember> findAll() {

        BranchGroupMemberExample example = new BranchGroupMemberExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<BranchGroupMember> records = branchGroupMemberMapper.selectByExample(example);
        Map<Integer, BranchGroupMember> map = new LinkedHashMap<>();
        for (BranchGroupMember record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        BranchGroupMember entity = branchGroupMemberMapper.selectByPrimaryKey(id);
        changeOrder("ow_branch_group_member", "group_id="+entity.getGroupId(), ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public void updateCountMenber(Integer groupId){

        BranchGroupMemberExample branchGroupMemberExample = new BranchGroupMemberExample();
        branchGroupMemberExample.createCriteria().andGroupIdEqualTo(groupId);
        Integer countMember = (int)branchGroupMemberMapper.countByExample(branchGroupMemberExample);

        BranchGroup branchGroup = new BranchGroup();
        branchGroup.setId(groupId);
        branchGroup.setCountMember(countMember);

        branchGroupMapper.updateByPrimaryKeySelective(branchGroup);
    }
}
