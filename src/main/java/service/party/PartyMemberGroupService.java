package service.party;

import controller.global.OpException;
import domain.party.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.LoginUserService;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PartyMemberGroupService extends BaseMapper {

    @Autowired
    protected LoginUserService loginUserService;
    // 查找现任班子
    public PartyMemberGroup getPresentGroup(int partyId){

        PartyMemberGroupExample _example = new PartyMemberGroupExample();
        _example.createCriteria().andPartyIdEqualTo(partyId).andIsDeletedEqualTo(false);
        List<PartyMemberGroup> partyMemberGroups = partyMemberGroupMapper.selectByExample(_example);
        int size = partyMemberGroups.size();
        if(size>1){
            throw new OpException("数据请求错误：现任班子不唯一。");
        }

        if(size==1) return partyMemberGroups.get(0);

        return null;
    }

    // 查找历任班子（根据任命时间查找，用于导入数据）
    public PartyMemberGroup getHistoryGroup(int partyId, Date appointTime){

        PartyMemberGroupExample _example = new PartyMemberGroupExample();
        _example.createCriteria().andPartyIdEqualTo(partyId)
                .andIsDeletedEqualTo(true)
                .andAppointTimeEqualTo(appointTime);
        List<PartyMemberGroup> partyMemberGroups =
                partyMemberGroupMapper.selectByExampleWithRowbounds(_example, new RowBounds(0,1));

        return partyMemberGroups.size()==1?partyMemberGroups.get(0):null;
    }


    // 查找班子的所有管理员
    public List<PartyMember> getGroupAdmins(int groupId){

        PartyMemberExample _example = new PartyMemberExample();
        _example.createCriteria().andGroupIdEqualTo(groupId).andIsAdminEqualTo(true);
        return partyMemberMapper.selectByExample(_example);
    }

   /* private void clearPresentGroup(int partyId) {

        PartyMemberGroup presentGroup = getPresentGroup(partyId);
        if(presentGroup==null) return;

        // 去掉以前设置的现任班子状态
        Integer groupId = presentGroup.getId();
        PartyMemberGroup _record = new PartyMemberGroup();
        _record.setId(groupId);
        _record.setIsPresent(false);
        partyMemberGroupMapper.updateByPrimaryKeySelective(_record);

        for (PartyMember partyMember : getGroupAdmins(groupId)) {
            int userId = partyMember.getUserId();
            // 删除账号的"分党委管理员"角色
            // 如果他只是该分党委的管理员，则删除账号所属的"分党委管理员"角色； 否则不处理
            if(partyAdminService.adminPartyIdCount(userId)==0) {
                sysUserService.delRole(userId, RoleConstants.ROLE_PARTYADMIN);
            }
        }
    }
    // 更新班子为现任班子时，需要把该班子的所有管理员添加“分党委管理员”角色
    private void rebuildPresentGroupAdmin(int groupId){

        for (PartyMember partyMember : getGroupAdmins(groupId)) {
            int userId = partyMember.getUserId();
            SysUserView sysUser = sysUserService.findById(userId);
            // 添加账号的"分党委管理员"角色
            // 如果账号是现任班子的管理员， 且没有"分党委管理员"角色，则添加
            if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_PARTYADMIN)) {
                sysUserService.addRole(userId, RoleConstants.ROLE_PARTYADMIN);
            }
        }
    }*/

    @Transactional
    public int insertSelective(PartyMemberGroup record) {

        record.setIsDeleted(false);
        // 排序还未用
        record.setSortOrder(getNextSortOrder("ow_party_member_group", null));
        return partyMemberGroupMapper.insertSelective(record);
    }

    @Transactional
    public int bacthImport(List<PartyMemberGroup> records) {

        int addCount = 0;
        for (PartyMemberGroup record : records) {

            PartyMemberGroup _record = getPresentGroup(record.getPartyId());
            if(_record==null && record.getAppointTime()!=null){

                _record = getHistoryGroup(record.getPartyId(), record.getAppointTime());
            }

            if(_record==null){

                insertSelective(record);
                addCount++;
            }else{
                record.setId(_record.getId());
                updateByPrimaryKeySelective(record);
            }

            if(BooleanUtils.isNotTrue(record.getIsDeleted())){
                commonMapper.excuteSql("update ow_party_member_group " +
                        "set actual_tran_time=null where id="+_record.getId());
            }
        }

        return addCount;
    }

   /* @Transactional
    public void del(Integer id) {

        PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(id);
        if (partyMemberGroup.getIsPresent()) {
            clearPresentGroup(partyMemberGroup.getPartyId());
        }
        partyMemberGroupMapper.deleteByPrimaryKey(id);
    }*/

    @Transactional
    public void batchDel(Integer[] ids, boolean isDeleted, String _actualTranTime) {

        if (ids == null || ids.length == 0) return;
        for (Integer id : ids) {

            PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(id);
            int partyId = partyMemberGroup.getPartyId();

            if(!isDeleted){ // 恢复班子
                Party party = partyMapper.selectByPrimaryKey(partyId);
                if(party.getIsDeleted())
                    throw new OpException(String.format("恢复班子失败，班子所属的分党委【%s】已删除。", party.getName()));

                PartyMemberGroup presentGroup = getPresentGroup(partyId);
                if(presentGroup!=null){
                    throw new OpException(String.format("恢复班子失败，班子已存在【%s】", party.getName()));
                }
            }
        }
        PartyMemberGroupExample example = new PartyMemberGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        PartyMemberGroup record = new PartyMemberGroup();
        record.setIsDeleted(isDeleted);
        if (isDeleted && StringUtils.isNotBlank(_actualTranTime)) {
            record.setActualTranTime(DateUtils.parseDate(_actualTranTime, DateUtils.YYYY_MM_DD));
        }
        partyMemberGroupMapper.updateByExampleSelective(record, example);
    }

    // 删除已撤销的班子
    @Transactional
    public void realDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PartyMemberGroupExample example = new PartyMemberGroupExample();
        example.createCriteria()
                .andIdIn(Arrays.asList(ids))
                .andIsDeletedEqualTo(true);
        partyMemberGroupMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PartyMemberGroup record) {

        if(record.getActualTranTime()==null){
            commonMapper.excuteSql("update ow_party_member_group set actual_tran_time=null where id="+ record.getId());
        }
        return partyMemberGroupMapper.updateByPrimaryKeySelective(record);
    }

/*    @Cacheable(value = "PartyMemberGroup:ALL")
    public Map<Integer, PartyMemberGroup> findAll() {

        PartyMemberGroupExample example = new PartyMemberGroupExample();
        example.setOrderByClause("sort_order desc");
        List<PartyMemberGroup> partyMemberGroupes = partyMemberGroupMapper.selectByExample(example);
        Map<Integer, PartyMemberGroup> map = new LinkedHashMap<>();
        for (PartyMemberGroup partyMemberGroup : partyMemberGroupes) {
            map.put(partyMemberGroup.getId(), partyMemberGroup);
        }
        return map;
    }*/

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        PartyMemberGroup entity = partyMemberGroupMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        PartyMemberGroupExample example = new PartyMemberGroupExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PartyMemberGroup> overEntities = partyMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PartyMemberGroup targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("ow_party_member_group", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_party_member_group", null, baseSortOrder, targetEntity.getSortOrder());

            PartyMemberGroup record = new PartyMemberGroup();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            partyMemberGroupMapper.updateByPrimaryKeySelective(record);
        }
    }

    //按照partyId统计应换届的党委班子的数量
    public int count(int partyId) {

        PartyMemberGroupExample example = new PartyMemberGroupExample();
        PartyMemberGroupExample.Criteria criteria = example.createCriteria()
                .andIsDeletedEqualTo(false).andTranTimeLessThanOrEqualTo(new Date());
        List<Integer> partyIdList = loginUserService.adminPartyIdList();
        if (partyIdList.contains(partyId)) {
            criteria.andPartyIdEqualTo(partyId);
        }
        else {
            criteria.andPartyIdIsNull();
        }

        return (int) partyMemberGroupMapper.countByExample(example);
    }
}
