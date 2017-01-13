package service.party;

import domain.party.*;
import domain.base.MetaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.base.MetaTypeService;

import java.util.*;

@Service
public class PartyService extends BaseMapper {
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private PartyMemberGroupService partyMemberGroupService;
    @Autowired
    private OrgAdminService orgAdminService;

    // 是否直属党支部
    public boolean isDirectBranch(int partyId) {

        Party party = findAll().get(partyId);
        MetaType metaType = metaTypeService.findAll().get(party.getClassId());
        return StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_direct_branch");
    }

    // 是否分党委
    public boolean isParty(int partyId) {

        Party party = findAll().get(partyId);
        MetaType metaType = metaTypeService.findAll().get(party.getClassId());
        return StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_party");
    }

    // 是否党总支
    public boolean isPartyGeneralBranch(int partyId) {

        Party party = findAll().get(partyId);
        MetaType metaType = metaTypeService.findAll().get(party.getClassId());
        return StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_party_general_branch");
    }


    public boolean idDuplicate(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code));

        PartyExample example = new PartyExample();
        PartyExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return partyMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value = "Party:ALL", allEntries = true)
    public int insertSelective(Party record) {

        Assert.isTrue(!idDuplicate(null, record.getCode()));
        record.setSortOrder(getNextSortOrder("ow_party", "is_deleted=0"));
        record.setIsDeleted(false);
        return partyMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value = "Party:ALL", allEntries = true)
    public void batchDel(Integer[] ids, boolean isDeleted) {

        if (ids == null || ids.length == 0) return;


        for (Integer id : ids) {

            Party record = new Party();
            record.setId(id);
            record.setIsDeleted(isDeleted);
            if (isDeleted) {
                // 删除所有的领导班子
                {
                    PartyMemberGroupExample example = new PartyMemberGroupExample();
                    example.createCriteria().andPartyIdEqualTo(id);
                    List<PartyMemberGroup> partyMemberGroups = partyMemberGroupMapper.selectByExample(example);
                    if (partyMemberGroups.size() > 0) {
                        List<Integer> groupIds = new ArrayList<>();
                        for (PartyMemberGroup partyMemberGroup : partyMemberGroups) {
                            groupIds.add(partyMemberGroup.getId());
                        }
                        partyMemberGroupService.batchDel(groupIds.toArray(new Integer[]{}), true);
                    }
                }
                // 删除所有的支部
                {
                    BranchExample example = new BranchExample();
                    example.createCriteria().andPartyIdEqualTo(id);
                    List<Branch> branchs = branchMapper.selectByExample(example);
                    if (branchs.size() > 0) {
                        List<Integer> branchIds = new ArrayList<>();
                        for (Branch branch : branchs) {
                            branchIds.add(branch.getId());
                        }
                        branchService.batchDel(branchIds.toArray(new Integer[]{}), true);
                    }
                }

                // 删除所有的分党委管理员
                orgAdminService.delAllOrgAdmin(id, null);
            }else{
                record.setSortOrder(getNextSortOrder("ow_party", "is_deleted=0")); // 恢复：更新排序
            }

            partyMapper.updateByPrimaryKeySelective(record);
        }


        /*PartyExample example = new PartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        Party record = new Party();
        record.setIsDeleted(isDeleted);
        partyMapper.updateByExampleSelective(record, example);*/
    }

    @Transactional
    @CacheEvict(value = "Party:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Party record) {
        if (StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()));
        return partyMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "Party:ALL")
    public Map<Integer, Party> findAll() {

        PartyExample example = new PartyExample();
        example.setOrderByClause("sort_order desc");
        List<Party> partyes = partyMapper.selectByExample(example);
        Map<Integer, Party> map = new LinkedHashMap<>();
        for (Party party : partyes) {
            map.put(party.getId(), party);
        }

        return map;
    }

    @Transactional
    @CacheEvict(value = "Party:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        Party entity = partyMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        PartyExample example = new PartyExample();
        if (addNum > 0) {

            example.createCriteria().andIsDeletedEqualTo(false).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andIsDeletedEqualTo(false).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Party> overEntities = partyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            Party targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("ow_party", "is_deleted=0", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_party", "is_deleted=0", baseSortOrder, targetEntity.getSortOrder());

            Party record = new Party();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            partyMapper.updateByPrimaryKeySelective(record);
        }
    }
}
