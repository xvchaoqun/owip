package service.dp;


import domain.base.MetaType;
import domain.dp.*;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.base.MetaTypeService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DpPartyMemberService extends DpBaseMapper {


    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private DpOrgAdminService dpOrgAdminService;
    @Autowired
    private DpPartyMemberAdminService dpPartyMemberAdminService;
    @Autowired
    private DpPartyMemberGroupService dpPartyMemberGroupService;

    @Transactional
    public void batcheRecover(DpPartyMember record){

        record.setPresentMember(true);
        dpPartyMemberMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void cancelMember(List<Integer> groupIds){

        DpPartyMemberExample example = new DpPartyMemberExample();
        example.createCriteria().andGroupIdIn(groupIds);
        List<DpPartyMember> dpPartyMembers = dpPartyMemberMapper.selectByExample(example);
        for (DpPartyMember dpPartyMember : dpPartyMembers){
            dpPartyMember.setPresentMember(false);
            dpPartyMemberMapper.updateByPrimaryKey(dpPartyMember);
        }
    }

    public Integer findByGroupId(Integer groupId){

        Integer partyId = null;
        DpPartyMemberGroupExample dpPartyMemberGroupExample = new DpPartyMemberGroupExample();
        dpPartyMemberGroupExample.createCriteria().andIdEqualTo(groupId);
        List<DpPartyMemberGroup> dpPartyMemberGroups = dpPartyMemberGroupMapper.selectByExample(dpPartyMemberGroupExample);
        if (dpPartyMemberGroups.size() > 0) {
            for (DpPartyMemberGroup dpPartyMemberGroup : dpPartyMemberGroups) {
                partyId = dpPartyMemberGroup.getPartyId();
            }
        }

        return partyId;
    }

    public boolean isNpm(Integer userId){
        DpNpmExample example = new DpNpmExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<DpNpm> dpNpms = dpNpmMapper.selectByExample(example);

        if (dpNpms.size() > 0){
            return true;
        }
        return false;
    }

    public boolean isDuplicate(Integer id,  int userId, int groupId, int postId) {

        //同一个人可能存在兼职情况
        {
            //但同一个人不可以在同一个委员会任同一个职务
            DpPartyMemberExample example = new DpPartyMemberExample();
            DpPartyMemberExample.Criteria criteria = example.createCriteria()
                    .andPostIdEqualTo(postId).andUserIdEqualTo(userId).andGroupIdEqualTo(groupId);
            if (id != null) criteria.andIdNotEqualTo(id);

            if (dpPartyMemberMapper.countByExample(example) > 0) return true;
        }

        MetaType metaType = metaTypeService.findAll().get(postId);
        if (StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_dp_zw")){

            //每个委员会只有一个主委
            DpPartyMemberExample example = new DpPartyMemberExample();
            DpPartyMemberExample.Criteria criteria = example.createCriteria()
                  .andPostIdEqualTo(postId).andGroupIdEqualTo(groupId);
            if (id != null) criteria.andIdNotEqualTo(id);

            if (dpPartyMemberMapper.countByExample(example) > 0) return true;
        }
        return false;
    }

    @Transactional
    public int updateByPrimaryKey(DpPartyMember record, boolean autoAdmin) {

       DpPartyMember old = dpPartyMemberMapper.selectByPrimaryKey(record.getId());
       record.setIsAdmin(old.getIsAdmin());
       record.setSortOrder(old.getSortOrder());
       record.setGroupId(old.getGroupId());
       dpPartyMemberMapper.updateByPrimaryKeySelective(record);

       //如果以前不是管理员，但是选择的类别是自动设定为管理员
        if(!record.getIsAdmin() && autoAdmin){
            record.setUserId(old.getUserId());
            record.setGroupId(old.getGroupId());
            dpPartyMemberAdminService.toggleAdmin(record);
        }
        if (record.getTypeIds() == null){
            commonMapper.excuteSql("update dp_party_member set type_ids=null where id=" + record.getId());
        }

        return 1;
    }

    // 根据委员会、用户ID、职务获取 委员会成员
    public DpPartyMember get(int groupId, int userId, int postId) {

        DpPartyMemberExample example = new DpPartyMemberExample();
        DpPartyMemberExample.Criteria criteria = example.createCriteria()
                .andGroupIdEqualTo(groupId).andPostIdEqualTo(postId).andUserIdEqualTo(userId);

        List<DpPartyMember> dpPartyMembers = dpPartyMemberMapper.selectByExample(example);
        return dpPartyMembers.size() == 1 ? dpPartyMembers.get(0) : null;
    }

    @Transactional
    public int bacthImport(List<DpPartyMember> records) {

        int addCount = 0;
        for (DpPartyMember record : records) {

            DpPartyMember _record = get(record.getGroupId(), record.getUserId(), record.getPostId());

            Integer postId = record.getPostId();
            MetaType metaType = CmTag.getMetaType(postId);
            boolean isAdmin = ((org.apache.commons.lang3.StringUtils.equals(metaType.getCode(), "mt_dp_zw")
                    || org.apache.commons.lang3.StringUtils.equals(metaType.getCode(), "mt_dp_fzw")));

            if (_record == null) {

                insertSelective(record, isAdmin);
                addCount++;
            } else {

                if(_record.getIsAdmin()){
                    // 先清除管理员
                    dpPartyMemberAdminService.toggleAdmin(_record);
                }

                record.setId(_record.getId());
                updateByPrimaryKey(record, isAdmin);
            }
        }

        return addCount;
    }

    @Transactional
    @CacheEvict(value="DpPartyMember:ALL", allEntries = true)
    public int insertSelective(DpPartyMember record, boolean autoAdmin){

        record.setIsAdmin(autoAdmin);
        record.setSortOrder(getNextSortOrder("dp_party_member", "group_id=" + record.getGroupId()));
        dpPartyMemberMapper.insertSelective(record);
        if(!autoAdmin){
            // 删除账号的"民主党派管理员"角色
            // 如果他只是该民主党派的管理员，则删除账号所属的"民主党派管理员"角色； 否则不处理
            List<Integer> partyIdList = iDpPartyMapper.adminDpPartyIdList(record.getUserId());
            if(partyIdList.size()==0) {
                sysUserService.delRole(record.getUserId(), RoleConstants.ROLE_DP_PARTY);
            }
        }else{
            // 添加账号的"民主党派管理员"角色
            // 如果账号是现任委员会的管理员， 且没有"民主党派管理员"角色，则添加
            SysUserView sysUser = sysUserService.findById(record.getUserId());
            if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_DP_PARTY)) {
                sysUserService.addRole(record.getUserId(), RoleConstants.ROLE_DP_PARTY);
            }
        }

        if (autoAdmin) {
            dpPartyMemberAdminService.toggleAdmin(record);
        }
        return 1;
    }

    @Transactional
    @CacheEvict(value="DpPartyMember:ALL", allEntries = true)
    public void del(Integer[] ids){

        if (ids == null || ids.length == 0)return;
        DpPartyMemberExample example = new DpPartyMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<DpPartyMember> dpPartyMembers = dpPartyMemberMapper.selectByExample(example);
        dpPartyMemberMapper.deleteByExample(example);
        for (DpPartyMember dpPartyMember : dpPartyMembers){
            if(dpPartyMember.getIsAdmin()){
                // 删除账号的"民主党派管理员"角色
                // 如果他只是该民主党派的管理员，则删除账号所属的"民主党派管理员"角色； 否则不处理
                List<Integer> partyIdList = iDpPartyMapper.adminDpPartyIdList(dpPartyMember.getUserId());
                if(partyIdList.size()==0) {
                    sysUserService.delRole(dpPartyMember.getUserId(),RoleConstants.ROLE_DP_PARTY);
                }
            }
        }
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids){
            DpPartyMember dpPartyMember = dpPartyMemberMapper.selectByPrimaryKey(id);

            //权限控制
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)){
                Integer groupId = dpPartyMember.getGroupId();
                DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(groupId);
                Integer partyId = dpPartyMemberGroup.getPartyId();

                //要求是民主党派管理员
                if (!isPresentAdmin(ShiroHelper.getCurrentUserId(),partyId)){
                    throw new UnauthorizedException();
                }
            }

            if (dpPartyMember.getIsAdmin()){
                dpPartyMemberAdminService.toggleAdmin(dpPartyMember);
            }
            DpPartyMember record = new DpPartyMember();
            record.setId(id);
            record.setGroupId(dpPartyMember.getGroupId());
            record.setPresentMember(false);
            dpPartyMemberMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    @CacheEvict(value="DpPartyMember:ALL", allEntries = true)
    public void updateByPrimaryKeySelective(DpPartyMember record, boolean autoAdmin){
        DpPartyMember old = dpPartyMemberMapper.selectByPrimaryKey(record.getId());
        record.setIsAdmin(old.getIsAdmin());
        record.setSortOrder(old.getSortOrder());
        record.setGroupId(old.getGroupId());
        dpPartyMemberMapper.updateByPrimaryKeySelective(record);

        //如果以前不是管理员，但是选择的类别是自动设定为管理员
        if (!record.getIsAdmin() && autoAdmin){
            record.setUserId(old.getUserId());
            record.setGroupId(old.getGroupId());
            dpPartyMemberAdminService.toggleAdmin(record);
        }

        if (record.getTypeIds() == null){
            commonMapper.excuteSql("update dp_party_member set type_ids=null where id=" + record.getId());
        }
    }

    @Cacheable(value="DpPartyMember:ALL")
    public Map<Integer, DpPartyMember> findAll() {

        DpPartyMemberExample example = new DpPartyMemberExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DpPartyMember> records = dpPartyMemberMapper.selectByExample(example);
        Map<Integer, DpPartyMember> map = new LinkedHashMap<>();
        for (DpPartyMember record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    public DpPartyMemberView getById(Integer id){

        DpPartyMemberViewExample example = new DpPartyMemberViewExample();
        example.createCriteria().andIdEqualTo(id);
        List<DpPartyMemberView> dpPartyMemberViews = dpPartyMemberViewMapper.selectByExample(example);

        return dpPartyMemberViews.size() > 0 ? dpPartyMemberViews.get(0) : null;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "DpPartyMember:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        DpPartyMember entity = dpPartyMemberMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer groupId = entity.getGroupId();

        DpPartyMemberExample example = new DpPartyMemberExample();
        if (addNum > 0) {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<DpPartyMember> overEntities = dpPartyMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size() > 0) {

            DpPartyMember targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("dp_party_member", "group_id=" + groupId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("dp_party_member", "group_id=" + groupId, baseSortOrder, targetEntity.getSortOrder());

            DpPartyMember record = new DpPartyMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            dpPartyMemberMapper.updateByPrimaryKeySelective(record);
        }
    }

    //查看用户是都是现任的党派管理员
    public boolean isPresentAdmin(Integer userId, Integer partyId) {
        if (userId == null || partyId == null) return false;
        return iDpPartyMapper.isDpPartyAdmin(userId, partyId) > 0;
    }

    //删除党派管理员
    @Transactional
    public void delAdmin(int userId, int partyId){
        List<DpPartyMember> dpPartyMembers = iDpPartyMapper.findDpPartyAdminOfDpPartyMember(userId,partyId);
        for (DpPartyMember dpPartyMember : dpPartyMembers){//理论上只有一个
            dpPartyMemberAdminService.toggleAdmin(dpPartyMember);
        }
        List<DpOrgAdmin> dpOrgAdmins = iDpPartyMapper.findDpPartyAdminOfOrgAdmin(userId,partyId);
        for(DpOrgAdmin dpOrgAdmin : dpOrgAdmins){//理论上只有一个
            dpOrgAdminService.del(dpOrgAdmin.getId(),dpOrgAdmin.getUserId());
        }
    }
}
