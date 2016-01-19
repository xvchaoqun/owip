package service.party;

import domain.PartyMember;
import domain.PartyMemberExample;
import domain.PartyMemberGroup;
import domain.SysUser;
import org.apache.ibatis.session.RowBounds;
import org.eclipse.jdt.internal.core.Assert;
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
public class PartyMemberService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PartyMemberAdminService partyMemberAdminService;

    // 查询用户是否是现任分党委班子的管理员
    public boolean isPresentAdmin(Integer userId, Integer partyId){
        if(userId==null || partyId == null) return false;
        return commonMapper.isPartyAdmin(userId, partyId)>0;
    }

    public boolean idDuplicate(Integer id, int groupId, int userId){

        PartyMemberExample example = new PartyMemberExample();
        PartyMemberExample.Criteria criteria = example.createCriteria()
                .andGroupIdEqualTo(groupId).andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return partyMemberMapper.countByExample(example) > 0;
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

        PartyMemberExample example = new PartyMemberExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PartyMember> overEntities = partyMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            PartyMember targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("ow_party_member", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_party_member", baseSortOrder, targetEntity.getSortOrder());

            PartyMember record = new PartyMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            partyMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
