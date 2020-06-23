package service.party;

import controller.global.OpException;
import domain.party.*;
import domain.sys.SysUserView;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysUserService;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;

import java.util.*;

@Service
public class BranchMemberGroupService extends BaseMapper {
    
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BranchAdminService branchAdminService;
    
    // 查找现任委员会
    public BranchMemberGroup getPresentGroup(int branchId) {
        
        BranchMemberGroupExample _example = new BranchMemberGroupExample();
        _example.createCriteria().andBranchIdEqualTo(branchId).andIsPresentEqualTo(true);
        List<BranchMemberGroup> branchMemberGroups = branchMemberGroupMapper.selectByExample(_example);
        int size = branchMemberGroups.size();
        if (size > 1) {
            throw new OpException("数据请求错误：现任班子不唯一。");
        }
        if (size == 1) return branchMemberGroups.get(0);
        
        return null;
    }
    
    // 查找班子的所有管理员
    public List<BranchMember> getGroupAdmins(int groupId) {
        
        BranchMemberExample _example = new BranchMemberExample();
        _example.createCriteria().andGroupIdEqualTo(groupId).andIsAdminEqualTo(true);
        return branchMemberMapper.selectByExample(_example);
    }
    
    private void clearPresentGroup(int branchId) {
        
        BranchMemberGroup presentGroup = getPresentGroup(branchId);
        if (presentGroup == null) return;
        
        // 去掉以前设置的现任班子状态
        Integer groupId = presentGroup.getId();
        BranchMemberGroup _record = new BranchMemberGroup();
        _record.setId(groupId);
        _record.setIsPresent(false);
        branchMemberGroupMapper.updateByPrimaryKeySelective(_record);
        
        for (BranchMember branchMember : getGroupAdmins(groupId)) {
            int userId = branchMember.getUserId();
            // 删除账号的"党支部管理员"角色
            // 如果他只是该党支部的管理员，则删除账号所属的"党支部管理员"角色； 否则不处理
            if (branchAdminService.adminBranchIdCount(userId) == 0) {
                sysUserService.delRole(userId, RoleConstants.ROLE_BRANCHADMIN);
            }
        }
    }
    
    // 更新班子为现任班子时，需要把该班子的所有管理员添加“党支部管理员”角色
    private void rebuildPresentGroupAdmin(int groupId) {
        
        for (BranchMember branchMember : getGroupAdmins(groupId)) {
            int userId = branchMember.getUserId();
            SysUserView sysUser = sysUserService.findById(userId);
            // 添加账号的"党支部管理员"角色
            // 如果账号是现任班子的管理员， 且没有"党支部管理员"角色，则添加
            if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_BRANCHADMIN)) {
                sysUserService.addRole(userId, RoleConstants.ROLE_BRANCHADMIN);
            }
        }
    }
    
    // 获取支部委员列表 <branchMemberType, List<BranchMember>>
    public Map<Integer,  List<BranchMember>> getBranchMemberListMap(int groupId) {
        
        BranchMemberExample example = new BranchMemberExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        example.setOrderByClause("sort_order desc");
        List<BranchMember> branchMembers = branchMemberMapper.selectByExample(example);
        
        Map<Integer, List<BranchMember>> branchMemberMap = new LinkedHashMap<>();
        for (BranchMember branchMember : branchMembers) {
            Integer typeId = branchMember.getTypeId();
            if(branchMemberMap.get(typeId)==null){
                List<BranchMember> branchMemberList = new ArrayList<>();
                branchMemberMap.put(typeId, branchMemberList);
            }
            List<BranchMember> branchMemberList = branchMemberMap.get(typeId);
            branchMemberList.add(branchMember);
        }
        
        return branchMemberMap;
    }
    
    @Transactional
    public int insertSelective(BranchMemberGroup record) {
        
        Branch branch = branchMapper.selectByPrimaryKey(record.getBranchId());
        PartyHelper.checkAuth(branch.getPartyId(), branch.getId());
        
        if (record.getIsPresent()) {
            clearPresentGroup(record.getBranchId());
        }
        record.setIsDeleted(false);
        record.setSortOrder(getNextSortOrder("ow_branch_member_group", null));
        return branchMemberGroupMapper.insertSelective(record);
    }

    // 查找历任委员会（根据任命时间查找，用于导入数据）
    public BranchMemberGroup getHistoryGroup(int branchId, Date appointTime){

        BranchMemberGroupExample _example = new BranchMemberGroupExample();
        _example.createCriteria().andBranchIdEqualTo(branchId)
                .andIsPresentEqualTo(false)
                .andAppointTimeEqualTo(appointTime);
        List<BranchMemberGroup> branchMemberGroups =
                branchMemberGroupMapper.selectByExampleWithRowbounds(_example, new RowBounds(0,1));

        return branchMemberGroups.size()==1?branchMemberGroups.get(0):null;
    }

    @Transactional
    public int bacthImport(List<BranchMemberGroup> records) {

        int addCount = 0;
        for (BranchMemberGroup record : records) {

            BranchMemberGroup _record = null;
            if(record.getIsPresent()) {
                _record = getPresentGroup(record.getBranchId());
            }else if(record.getAppointTime()!=null){

                _record = getHistoryGroup(record.getBranchId(), record.getAppointTime());
            }

            if(_record==null){

                insertSelective(record);
                addCount++;
            }else{
                record.setId(_record.getId());
                updateByPrimaryKeySelective(record);

                if(record.getIsPresent()==false){
                    commonMapper.excuteSql("update ow_branch_member_group " +
                            "set actual_tran_time=null where id="+_record.getId());
                }
            }
        }

        return addCount;
    }

   /* @Transactional
    public void del(Integer id){

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(id);

        Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
        checkAuth(branch.getPartyId());

        if (branchMemberGroup.getIsPresent()) {
            clearPresentGroup(branchMemberGroup.getBranchId());
        }
        branchMemberGroupMapper.deleteByPrimaryKey(id);
    }*/
    
    @Transactional
    public void batchDel(Integer[] ids, boolean isDeleted) {
        
        if (ids == null || ids.length == 0) return;
        
        for (Integer id : ids) {
            BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(id);
            Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
            PartyHelper.checkAuth(branch.getPartyId(), branch.getId());
            
            if (!isDeleted) { // 恢复支部委员会
                if (branch.getIsDeleted())
                    throw new OpException(String.format("恢复支部委员会失败，支部委员会所属的支部【%s】已删除。", branch.getName()));
                else {
                    Party party = partyMapper.selectByPrimaryKey(branch.getPartyId());
                    if (party.getIsDeleted())
                        throw new OpException(String.format("恢复支部委员会失败，支部委员会所属分党委【%s】已删除。", party.getName()));
                }
            }
            
            if (branchMemberGroup.getIsPresent()) {
                clearPresentGroup(branchMemberGroup.getBranchId());
            }
        }
        BranchMemberGroupExample example = new BranchMemberGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        BranchMemberGroup record = new BranchMemberGroup();
        record.setIsDeleted(isDeleted);
        branchMemberGroupMapper.updateByExampleSelective(record, example);
    }

    // 删除已撤销的班子
    @Transactional
    public void realDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        BranchMemberGroupExample example = new BranchMemberGroupExample();
        example.createCriteria()
                .andIdIn(Arrays.asList(ids))
                .andIsDeletedEqualTo(true);
        branchMemberGroupMapper.deleteByExample(example);
    }
    
    @Transactional
    public int updateByPrimaryKeySelective(BranchMemberGroup record) {
        
        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(record.getId());
        Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
        PartyHelper.checkAuth(branch.getPartyId(), branch.getId());
        
        BranchMemberGroup presentGroup = getPresentGroup(record.getBranchId());
        
        if (presentGroup == null || (presentGroup.getId().intValue() == record.getId() && !record.getIsPresent())) {
            clearPresentGroup(record.getBranchId());
        }
        if (presentGroup == null && record.getIsPresent()) {
            rebuildPresentGroupAdmin(record.getId());
        }
        if (presentGroup != null && presentGroup.getId().intValue() != record.getId() && record.getIsPresent()) {
            clearPresentGroup(record.getBranchId());
            rebuildPresentGroupAdmin(record.getId());
        }
        return branchMemberGroupMapper.updateByPrimaryKeySelective(record);
    }
    
    
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {
        
        if (addNum == 0) return;
        
        BranchMemberGroup entity = branchMemberGroupMapper.selectByPrimaryKey(id);
        
        Branch branch = branchMapper.selectByPrimaryKey(entity.getBranchId());
        PartyHelper.checkAuth(branch.getPartyId());
        
        Integer baseSortOrder = entity.getSortOrder();
        
        BranchMemberGroupExample example = new BranchMemberGroupExample();
        if (addNum > 0) {
            
            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {
            
            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }
        
        List<BranchMemberGroup> overEntities = branchMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {
            
            BranchMemberGroup targetEntity = overEntities.get(overEntities.size() - 1);
            
            if (addNum > 0)
                commonMapper.downOrder("ow_branch_member_group", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_branch_member_group", null, baseSortOrder, targetEntity.getSortOrder());
            
            BranchMemberGroup record = new BranchMemberGroup();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            branchMemberGroupMapper.updateByPrimaryKeySelective(record);
        }
    }
}
