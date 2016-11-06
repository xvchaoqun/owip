package service.party;

import domain.party.OrgAdmin;
import domain.party.PartyMember;
import domain.party.PartyMemberExample;
import domain.sys.MetaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.MetaTypeService;

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

    // 查询用户是否是现任分党委、党总支、直属党支部班子的管理员
    public boolean isPresentAdmin(Integer userId, Integer partyId){
        if(userId==null || partyId == null) return false;
        return commonMapper.isPartyAdmin(userId, partyId)>0;
    }

    // 删除分党委管理员
    @Transactional
    public void delAdmin(int userId, int partyId){

        List<PartyMember> partyMembers = commonMapper.findPartyAdminOfPartyMember(userId, partyId);
        for (PartyMember partyMember : partyMembers) { // 理论上只有一个
            partyMemberAdminService.toggleAdmin(partyMember);
        }
        List<OrgAdmin> orgAdmins = commonMapper.findPartyAdminOfOrgAdmin(userId, partyId);
        for (OrgAdmin orgAdmin : orgAdmins) { // 理论上只有一个
            orgAdminService.del(orgAdmin.getId(), orgAdmin.getUserId());
        }
    }

    public boolean idDuplicate(Integer id, int groupId, int userId, int typeId){

        {
            // 同一个人不可以在同一个委员会
            PartyMemberExample example = new PartyMemberExample();
            PartyMemberExample.Criteria criteria = example.createCriteria()
                    .andGroupIdEqualTo(groupId).andUserIdEqualTo(userId);
            if (id != null) criteria.andIdNotEqualTo(id);

            if(partyMemberMapper.countByExample(example) > 0) return true;
        }

        MetaType metaType = metaTypeService.findAll().get(typeId);
        if(StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_party_secretary")){

            // 每个委员会只有一个书记
            PartyMemberExample example = new PartyMemberExample();
            PartyMemberExample.Criteria criteria = example.createCriteria()
                    .andGroupIdEqualTo(groupId).andTypeIdEqualTo(typeId);
            if (id != null) criteria.andIdNotEqualTo(id);

            if(partyMemberMapper.countByExample(example) > 0) return true;
        }

        return false;
    }

    @Transactional
    public int insertSelective(PartyMember record, boolean autoAdmin){

        record.setIsAdmin(false);
        partyMemberMapper.insertSelective(record);

        Integer id = record.getId();
        PartyMember _record = new PartyMember();
        _record.setId(id);
        _record.setSortOrder(id);
        partyMemberMapper.updateByPrimaryKeySelective(_record);

        if(autoAdmin){
            partyMemberAdminService.toggleAdmin(record);
        }
        return 1;
    }

    @Transactional
    public void del(Integer id){
        PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
        if(partyMember.getIsAdmin()){
            partyMemberAdminService.toggleAdmin(partyMember);
        }
        partyMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        for (Integer id : ids) {
            PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
            if(partyMember.getIsAdmin()){
                partyMemberAdminService.toggleAdmin(partyMember);
            }
        }
        PartyMemberExample example = new PartyMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyMemberMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PartyMember record, boolean autoAdmin){

        PartyMember old = partyMemberMapper.selectByPrimaryKey(record.getId());
        record.setIsAdmin(old.getIsAdmin());
        partyMemberMapper.updateByPrimaryKeySelective(record);

        // 如果以前不是管理员，但是选择的类别是自动设定为管理员
        if(!record.getIsAdmin() && autoAdmin){
            record.setUserId(old.getUserId());
            record.setGroupId(old.getGroupId());
            partyMemberAdminService.toggleAdmin(record);
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
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        PartyMember entity = partyMemberMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer groupId = entity.getGroupId();

        PartyMemberExample example = new PartyMemberExample();
        if (addNum > 0) {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PartyMember> overEntities = partyMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            PartyMember targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder_partyMember(groupId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder_partyMember(groupId, baseSortOrder, targetEntity.getSortOrder());

            PartyMember record = new PartyMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            partyMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
