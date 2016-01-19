package service.party;

import domain.*;
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
public class BranchMemberGroupService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    
    // 查找现任班子
    private  BranchMemberGroup getPresentGroup(int branchId){

        BranchMemberGroupExample _example = new BranchMemberGroupExample();
        _example.createCriteria().andBranchIdEqualTo(branchId).andIsPresentEqualTo(true);
        List<BranchMemberGroup> branchMemberGroups = branchMemberGroupMapper.selectByExample(_example);
        int size = branchMemberGroups.size();
        if(size>1){
            throw new RuntimeException("数据异常：现任班子不唯一。");
        }

        if(size==1) return branchMemberGroups.get(0);

        return null;
    }
    // 查找班子的所有管理员
    public List<BranchMember> getGroupAdmins(int groupId){

        BranchMemberExample _example = new BranchMemberExample();
        _example.createCriteria().andGroupIdEqualTo(groupId).andIsAdminEqualTo(true);
        return branchMemberMapper.selectByExample(_example);
    }

    private void clearPresentGroup(int branchId) {

        BranchMemberGroup presentGroup = getPresentGroup(branchId);
        if(presentGroup==null) return;

        // 去掉以前设置的现任班子状态
        Integer groupId = presentGroup.getId();
        BranchMemberGroup _record = new BranchMemberGroup();
        _record.setId(groupId);
        _record.setIsPresent(false);
        branchMemberGroupMapper.updateByPrimaryKeySelective(_record);

        for (BranchMember branchMember : getGroupAdmins(groupId)) {
            int userId = branchMember.getUserId();
            SysUser sysUser = sysUserService.findById(userId);
            // 删除账号的"党支部管理员"角色
            // 如果他只是该党支部的管理员，则删除账号所属的"党支部管理员"角色； 否则不处理
            List<Integer> branchIdList = commonMapper.adminBranchIdList(userId);
            if(branchIdList.size()==0) {
                sysUserService.delRole(userId, SystemConstants.ROLE_BRANCHADMIN, sysUser.getUsername());
            }
        }
    }
    // 更新班子为现任班子时，需要把该班子的所有管理员添加“党支部管理员”角色
    private void rebuildPresentGroupAdmin(int groupId){

        for (BranchMember branchMember : getGroupAdmins(groupId)) {
            int userId = branchMember.getUserId();
            SysUser sysUser = sysUserService.findById(userId);
            // 添加账号的"党支部管理员"角色
            // 如果账号是现任班子的管理员， 且没有"党支部管理员"角色，则添加
            Set<String> roleStrSet = sysUserService.findRoles(sysUser.getUsername());
            if (!roleStrSet.contains(SystemConstants.ROLE_BRANCHADMIN)) {
                sysUserService.addRole(userId, SystemConstants.ROLE_BRANCHADMIN, sysUser.getUsername());
            }
        }
    }
    @Transactional
    public int insertSelective(BranchMemberGroup record){

        if (record.getIsPresent()) {
            clearPresentGroup(record.getBranchId());
        }
        branchMemberGroupMapper.insertSelective(record);

        Integer id = record.getId();
        BranchMemberGroup _record = new BranchMemberGroup();
        _record.setId(id);
        _record.setSortOrder(id);
        return branchMemberGroupMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    public void del(Integer id){

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(id);
        if (branchMemberGroup.getIsPresent()) {
            clearPresentGroup(branchMemberGroup.getBranchId());
        }
        branchMemberGroupMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        for (Integer id : ids) {
            BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(id);
            if (branchMemberGroup.getIsPresent()) {
                clearPresentGroup(branchMemberGroup.getBranchId());
            }
        }
        BranchMemberGroupExample example = new BranchMemberGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        branchMemberGroupMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(BranchMemberGroup record){

        BranchMemberGroup presentGroup = getPresentGroup(record.getBranchId());
        if(presentGroup==null || (presentGroup.getId().intValue()== record.getId() && !record.getIsPresent())){
            clearPresentGroup(record.getBranchId());
        }
        if(presentGroup==null && record.getIsPresent()){
            rebuildPresentGroupAdmin(record.getId());
        }
        if (presentGroup!=null && presentGroup.getId().intValue()!= record.getId() && record.getIsPresent()) {
            clearPresentGroup(record.getBranchId());
            rebuildPresentGroupAdmin(record.getId());
        }
        return branchMemberGroupMapper.updateByPrimaryKeySelective(record);
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

        BranchMemberGroup entity = branchMemberGroupMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        BranchMemberGroupExample example = new BranchMemberGroupExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<BranchMemberGroup> overEntities = branchMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            BranchMemberGroup targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("ow_branch_member_group", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_branch_member_group", baseSortOrder, targetEntity.getSortOrder());

            BranchMemberGroup record = new BranchMemberGroup();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            branchMemberGroupMapper.updateByPrimaryKeySelective(record);
        }
    }
}
