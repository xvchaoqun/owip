package service.party;

import domain.BranchMember;
import domain.BranchMemberExample;
import domain.BranchMemberGroup;
import domain.SysUser;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class BranchMemberService extends BaseMapper {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BranchMemberAdminService branchMemberAdminService;

    // 查询用户是否是支部管理员
    public boolean isPresentAdmin(Integer userId, Integer branchId){
        if(userId==null || branchId == null) return false;
        return commonMapper.isBranchAdmin(userId, branchId)>0;
    }

    public boolean idDuplicate(Integer id, int groupId, int userId){

        BranchMemberExample example = new BranchMemberExample();
        BranchMemberExample.Criteria criteria = example.createCriteria()
                .andGroupIdEqualTo(groupId).andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return branchMemberMapper.countByExample(example) > 0;
    }

    @Transactional
    public int insertSelective(BranchMember record, boolean autoAdmin){

        record.setIsAdmin(false);
        branchMemberMapper.insertSelective(record);

        Integer id = record.getId();
        BranchMember _record = new BranchMember();
        _record.setId(id);
        _record.setSortOrder(id);
        branchMemberMapper.updateByPrimaryKeySelective(_record);

        if(autoAdmin){
            branchMemberAdminService.toggleAdmin(record);
        }
        return 1;
    }
    @Transactional
    public void del(Integer id){

        BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);
        if(branchMember.getIsAdmin()){
            branchMemberAdminService.toggleAdmin(branchMember);
        }
        branchMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        for (Integer id : ids) {
            BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);
            if(branchMember.getIsAdmin()){
                branchMemberAdminService.toggleAdmin(branchMember);
            }
        }
        BranchMemberExample example = new BranchMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        branchMemberMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(BranchMember record, boolean autoAdmin){
        BranchMember old = branchMemberMapper.selectByPrimaryKey(record.getId());
        record.setIsAdmin(old.getIsAdmin());
        branchMemberMapper.updateByPrimaryKeySelective(record);

        // 如果以前不是管理员，但是选择的类别是自动设定为管理员
        if(!record.getIsAdmin() && autoAdmin){
            record.setUserId(old.getUserId());
            record.setGroupId(old.getGroupId());
            branchMemberAdminService.toggleAdmin(record);
        }
        return 1;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        BranchMember entity = branchMemberMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        BranchMemberExample example = new BranchMemberExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<BranchMember> overEntities = branchMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            BranchMember targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("ow_branch_member", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_branch_member", baseSortOrder, targetEntity.getSortOrder());

            BranchMember record = new BranchMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            branchMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
