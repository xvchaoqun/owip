package service.party;

import domain.base.MetaType;
import domain.party.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.sys.SysConfigService;
import sys.tags.CmTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PartyMemberService extends BaseMapper {

    @Autowired
    private OrgAdminService orgAdminService;
    @Autowired
    private PartyMemberAdminService partyMemberAdminService;
   @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    protected PartyMemberGroupService partyMemberGroupService;
    @Autowired
    protected SysConfigService sysConfigService;

    // 获取现任分党委委员（拥有某种分工的）
    public List<PartyMemberView> getPartyMemberViews(int partyId,  int typeId){

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if(presentGroup==null) return new ArrayList<>();

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId())
                .andTypeIdsIn(Arrays.asList(typeId));

        return partyMemberViewMapper.selectByExample(example);
    }

    // 获取现任分党委委员（拥有某种分工的）
    public PartyMemberView getPartyMemberView(int partyId, int userId, int typeId){

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if(presentGroup==null) return null;

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId()).andUserIdEqualTo(userId)
                .andTypeIdsIn(Arrays.asList(typeId));
        List<PartyMemberView> records = partyMemberViewMapper.selectByExample(example);
        return records.size()==0?null:records.get(0);
    }

    // 获取现任分党委委员
    public PartyMemberView getPartyMemberView(int partyId, int userId){

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if(presentGroup==null) return null;

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId()).andUserIdEqualTo(userId);

        List<PartyMemberView> records = partyMemberViewMapper.selectByExample(example);
        return records.size()==0?null:records.get(0);
    }

    // 获取现任分党委书记
    public PartyMemberView getPartySecretary(int partyId){

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if(presentGroup==null) return null;

        MetaType partySecretaryType = CmTag.getMetaTypeByCode("mt_party_secretary");

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId()).andPostIdEqualTo(partySecretaryType.getId());

        List<PartyMemberView> records = partyMemberViewMapper.selectByExample(example);
        return records.size()==0?null:records.get(0);
    }

    

    // 查询用户是否是现任分党委、党总支、直属党支部班子的管理员
    public boolean isPresentAdmin(Integer userId, Integer partyId) {
        if (userId == null || partyId == null) return false;
        return iPartyMapper.isPartyAdmin(userId, partyId) > 0;
    }

    // 删除分党委管理员
    @Transactional
    public void delAdmin(int userId, int partyId) {

        List<PartyMember> partyMembers = iPartyMapper.findPartyAdminOfPartyMember(userId, partyId);
        for (PartyMember partyMember : partyMembers) { // 理论上只有一个
            partyMemberAdminService.toggleAdmin(partyMember);
        }
        List<OrgAdmin> orgAdmins = iPartyMapper.findPartyAdminOfOrgAdmin(userId, partyId);
        for (OrgAdmin orgAdmin : orgAdmins) { // 理论上只有一个
            orgAdminService.del(orgAdmin.getId(), orgAdmin.getUserId());
        }
    }

    public boolean idDuplicate(Integer id, int groupId, int userId, int postId) {

        {
            // 同一个人不可以在同一个委员会
            PartyMemberExample example = new PartyMemberExample();
            PartyMemberExample.Criteria criteria = example.createCriteria()
                    .andGroupIdEqualTo(groupId).andUserIdEqualTo(userId);
            if (id != null) criteria.andIdNotEqualTo(id);

            if (partyMemberMapper.countByExample(example) > 0) return true;
        }

        MetaType metaType = metaTypeService.findAll().get(postId);
        if (StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_party_secretary")) {

            // 每个委员会只有一个书记
            PartyMemberExample example = new PartyMemberExample();
            PartyMemberExample.Criteria criteria = example.createCriteria()
                    .andGroupIdEqualTo(groupId).andPostIdEqualTo(postId);
            if (id != null) criteria.andIdNotEqualTo(id);

            if (partyMemberMapper.countByExample(example) > 0) return true;
        }

        return false;
    }

    @Transactional
    public int insertSelective(PartyMember record, boolean autoAdmin) {

        record.setIsAdmin(false);
        record.setSortOrder(getNextSortOrder("ow_party_member", "group_id=" + record.getGroupId()));
        partyMemberMapper.insertSelective(record);

        if (autoAdmin) {
            partyMemberAdminService.toggleAdmin(record);
        }
        return 1;
    }

    @Transactional
    public void del(Integer id) {
        PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
        if (partyMember.getIsAdmin()) {
            partyMemberAdminService.toggleAdmin(partyMember);
        }
        partyMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;
        for (Integer id : ids) {
            PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
            if (partyMember.getIsAdmin()) {
                partyMemberAdminService.toggleAdmin(partyMember);
            }
        }
        PartyMemberExample example = new PartyMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyMemberMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKey(PartyMember record, boolean autoAdmin) {

        PartyMember old = partyMemberMapper.selectByPrimaryKey(record.getId());
        record.setIsAdmin(old.getIsAdmin());
        record.setSortOrder(old.getSortOrder());
        record.setGroupId(old.getGroupId());
        partyMemberMapper.updateByPrimaryKeySelective(record);

        // 如果以前不是管理员，但是选择的类别是自动设定为管理员
        if (!record.getIsAdmin() && autoAdmin) {
            record.setUserId(old.getUserId());
            record.setGroupId(old.getGroupId());
            partyMemberAdminService.toggleAdmin(record);
        }

        if(record.getTypeIds()==null){
            commonMapper.excuteSql("update ow_party_member set type_ids=null where id="+record.getId());
        }

        return 1;
    }

    /*@Cacheable(value="PartyMember:ALL")
    public Map<Integer, PartyMember> findAll() {

        PartyMemberExample example = new PartyMemberExample();
        example.setOrderByClause("sort_order desc");
        List<PartyMember> partyMemberes = partyMemberMapper.selectByExample(example);
        Map<Integer, PartyMember> map = new LinkedHashMap<>();
        for (PartyMember partyMember : partyMemberes) {
            map.put(partyMember.getId(), partyMember);
        }

        return map;
    }*/

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

        PartyMember entity = partyMemberMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer groupId = entity.getGroupId();

        PartyMemberExample example = new PartyMemberExample();
        if (addNum > 0) {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PartyMember> overEntities = partyMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PartyMember targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("ow_party_member", "group_id=" + groupId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_party_member", "group_id=" + groupId, baseSortOrder, targetEntity.getSortOrder());

            PartyMember record = new PartyMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            partyMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
