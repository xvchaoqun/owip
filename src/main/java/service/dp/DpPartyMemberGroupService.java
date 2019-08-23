package service.dp;


import controller.global.OpException;
import domain.dp.*;
import domain.sys.SysRole;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.sys.SysRoleService;
import service.sys.SysUserService;
import sys.constants.RoleConstants;

import java.util.*;

@Service
public class DpPartyMemberGroupService extends DpBaseMapper {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private DpOrgAdminService dpOrgAdminService;
    @Autowired
    private SysUserService sysUserService;

    // 查找历任班子（根据任命时间查找，用于导入数据）
    public DpPartyMemberGroup getHistoryGroup(int partyId, Date appointTime){

        DpPartyMemberGroupExample _example = new DpPartyMemberGroupExample();
        _example.createCriteria().andPartyIdEqualTo(partyId)
                .andIsPresentEqualTo(false)
                .andAppointTimeEqualTo(appointTime);
        List<DpPartyMemberGroup> dpPartyMemberGroups =
                dpPartyMemberGroupMapper.selectByExampleWithRowbounds(_example, new RowBounds(0,1));

        return dpPartyMemberGroups.size()==1?dpPartyMemberGroups.get(0):null;
    }


    @Transactional
    public int bacthImport(List<DpPartyMemberGroup> records) {

        int addCount = 0;
        for (DpPartyMemberGroup record : records) {

            DpPartyMemberGroup _record = null;
            if(record.getIsPresent()) {
                _record = getPresentGroup(record.getPartyId());
            }else if(record.getAppointTime()!=null){

                _record = getHistoryGroup(record.getPartyId(), record.getAppointTime());
            }

            if(_record==null){

                insertSelective(record);
                addCount++;
            }else{
                record.setId(_record.getId());
                updateByPrimaryKeySelective(record);

                if(record.getIsPresent()==false){
                    commonMapper.excuteSql("update dp_party_member_group " +
                            "set actual_tran_time=null where id="+_record.getId());
                }
            }
        }

        return addCount;
    }

    //查找现任委员会
    public DpPartyMemberGroup getPresentGroup(int partyId){

        DpPartyMemberGroupExample example = new DpPartyMemberGroupExample();
        example.createCriteria().andPartyIdEqualTo(partyId).andIsPresentEqualTo(true);
        List<DpPartyMemberGroup> dpPartyMemberGroups = dpPartyMemberGroupMapper.selectByExample(example);
        int size = dpPartyMemberGroups.size();
        if(size>1){
            throw new OpException("数据异常，现任委员会不唯一");
        }

        if (size == 1) return dpPartyMemberGroups.get(0);

        return null;
    }

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DpPartyMemberGroupExample example = new DpPartyMemberGroupExample();
        DpPartyMemberGroupExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dpPartyMemberGroupMapper.countByExample(example) > 0;
    }

    private void clearPresentGroup(int partyId) {

        DpPartyMemberGroup presentGroup = getPresentGroup(partyId);
        if(presentGroup==null) return;

        // 去掉以前设置的现任委员会状态
        Integer groupId = presentGroup.getId();
        DpPartyMemberGroup _record = new DpPartyMemberGroup();
        _record.setId(groupId);
        _record.setIsPresent(false);
        dpPartyMemberGroupMapper.updateByPrimaryKeySelective(_record);

        for (DpPartyMember dpPartyMember : getGroupAdmins(groupId)) {
            DpPartyMember record = new DpPartyMember();
            record.setId(dpPartyMember.getId());
            record.setGroupId(groupId);
            record.setUserId(dpPartyMember.getUserId());
            record.setIsAdmin(false);
            record.setPresentMember(false);
            dpPartyMemberMapper.updateByPrimaryKey(record);
            int userId = dpPartyMember.getUserId();
            // 删除账号的"民主党派管理员"角色
            // 如果他只是该党派的管理员，则删除账号所属的"党派管理员"角色； 否则不处理
            List<Integer> partyIdList = iDpPartyMapper.adminDpPartyIdList(userId);
            if(partyIdList.size()==0) {
                sysUserService.delRole(userId, RoleConstants.ROLE_DP_PARTY);
            }
        }
    }

    // 查找委员会的所有管理员
    public List<DpPartyMember> getGroupAdmins(int groupId){

        DpPartyMemberExample _example = new DpPartyMemberExample();
        _example.createCriteria().andGroupIdEqualTo(groupId).andIsAdminEqualTo(true);
        return dpPartyMemberMapper.selectByExample(_example);
    }

    // 更新委员会为现任委员会时，需要把该委员会的所有管理员添加“党派管理员”角色
    private void rebuildPresentGroupAdmin(int groupId){
            for (DpPartyMember dpPartyMember : getGroupAdmins(groupId)) {
                int userId = dpPartyMember.getUserId();
                SysUserView sysUser = sysUserService.findById(userId);
                // 添加账号的"党派管理员"角色
                // 如果账号是现任委员会的管理员， 且没有"党派管理员"角色，则添加
                Set<String> roles = sysUserService.findRoles(sysUser.getUsername());
                SysRole roleId = sysRoleService.getByRole(RoleConstants.ROLE_DP_PARTY);
                if (!roles.contains(roleId)) {
                    sysUserService.addRole(userId, RoleConstants.ROLE_DP_PARTY);
                }
                DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(groupId);
                dpOrgAdminService.addDpPartyAdmin(userId, dpPartyMemberGroup.getPartyId());
            }
    }

    @Transactional
    @CacheEvict(value="DpPartyMemberGroup:ALL", allEntries = true)
    public int insertSelective(DpPartyMemberGroup record){

        if (record.getIsPresent()){
            clearPresentGroup(record.getPartyId());
        }
        record.setIsDeleted(false);
        //Assert.isTrue(!idDuplicate(null,String.valueOf(record.getId())), "duplicate");
        record.setSortOrder(getNextSortOrder("dp_party_member_group", null));
        return dpPartyMemberGroupMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="DpPartyMemberGroup:ALL", allEntries = true)
    public void del(Integer id){

        dpPartyMemberGroupMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, boolean isDeleted){

        if(ids==null || ids.length==0) return;
        for (Integer id : ids){
            DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(id);
            if (dpPartyMemberGroup.getIsPresent()){
                clearPresentGroup(dpPartyMemberGroup.getPartyId());
            }

            if (!isDeleted){ //恢复委员会
                DpParty dpParty = dpPartyMapper.selectByPrimaryKey(dpPartyMemberGroup.getPartyId());
                if (dpParty.getIsDeleted()){
                    throw new OpException(String.format("恢复委员会失败，委员会所属的党派【%s】已删除。",dpParty.getName()));
                }
            }
        }

        DpPartyMemberGroupExample example = new DpPartyMemberGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        DpPartyMemberGroup record = new DpPartyMemberGroup();
        record.setIsDeleted(isDeleted);
        dpPartyMemberGroupMapper.updateByExampleSelective(record,example);
    }

    //删除已撤销的委员,删除委员的groupId
    @Transactional
    public void realDel(Integer[] ids){

        if (ids == null || ids.length == 0) return;;

            DpPartyMemberExample dpPartyMemberExample = new DpPartyMemberExample();
            dpPartyMemberExample.createCriteria().andGroupIdIn(Arrays.asList(ids));
            List<DpPartyMember> dpPartyMembers = dpPartyMemberMapper.selectByExample(dpPartyMemberExample);
        for (DpPartyMember dpPartyMember : dpPartyMembers){
            Integer userId = dpPartyMember.getUserId();
            DpPartyMember record = new DpPartyMember();
            record.setUserId(userId);
            record.setGroupId(null);
        }

        DpPartyMemberGroupExample example = new DpPartyMemberGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andIsDeletedEqualTo(true);
        dpPartyMemberGroupMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="DpPartyMemberGroup:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(DpPartyMemberGroup record){
        DpPartyMemberGroup presentGroup = getPresentGroup(record.getPartyId());
        if(presentGroup==null || (presentGroup.getId().intValue()== record.getId() && !record.getIsPresent())){
            clearPresentGroup(record.getPartyId());
        }
        if(presentGroup==null && record.getIsPresent()){
            rebuildPresentGroupAdmin(record.getId());
        }
        if (presentGroup!=null && presentGroup.getId().intValue()!= record.getId() && record.getIsPresent()) {
            clearPresentGroup(record.getPartyId());
            rebuildPresentGroupAdmin(record.getId());
        }
        if(record.getActualTranTime()==null){
            commonMapper.excuteSql("update dp_party_member_group set actual_tran_time=null where id="+ record.getId());
        }
        //if(StringUtils.isNotBlank(String.valueOf(record.getId())))
           // Assert.isTrue(!idDuplicate(record.getId(), String.valueOf(record.getId())), "duplicate");
        return dpPartyMemberGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="DpPartyMemberGroup:ALL")
    public Map<Integer, DpPartyMemberGroup> findAll() {

        DpPartyMemberGroupExample example = new DpPartyMemberGroupExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DpPartyMemberGroup> records = dpPartyMemberGroupMapper.selectByExample(example);
        Map<Integer, DpPartyMemberGroup> map = new LinkedHashMap<>();
        for (DpPartyMemberGroup record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "DpPartyMemberGroup:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        DpPartyMemberGroup entity = dpPartyMemberGroupMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        DpPartyMemberGroupExample example = new DpPartyMemberGroupExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<DpPartyMemberGroup> overEntities = dpPartyMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            DpPartyMemberGroup targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("dp_party_member_group", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("dp_party_member_group", null, baseSortOrder, targetEntity.getSortOrder());

            DpPartyMemberGroup record = new DpPartyMemberGroup();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            dpPartyMemberGroupMapper.updateByPrimaryKeySelective(record);
        }
    }
}
