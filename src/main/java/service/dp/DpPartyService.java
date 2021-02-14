package service.dp;

import domain.dp.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DpPartyService extends DpBaseMapper {

    @Autowired
    private DpPartyMemberService dpPartyMemberService;
    @Autowired
    private DpOrgAdminService dpOrgAdminService;
    @Autowired
    private DpPartyMemberGroupService dpPartyMemberGroupService;

    @Transactional
    public int bacthImport(List<DpParty> records){
        int addCount = 0;
        for (DpParty record : records){
            String code = record.getCode();
            DpParty _record = getByCode(code);
            if (_record == null){
                insertSelective(record);
                addCount++;
            } else {
                record.setId(_record.getId());
                updateByPrimaryKeySelective(record);
            }
        }
        return addCount;
    }

    public boolean idDuplicate(Integer id, String code){

        //Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DpPartyExample example = new DpPartyExample();
        DpPartyExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dpPartyMapper.countByExample(example) > 0;
    }

    public DpPartyView getDpPartyViewById(Integer partyId){

        DpPartyViewExample example = new DpPartyViewExample();
        DpPartyViewExample.Criteria criteria = example.createCriteria().andIdEqualTo(partyId);
        List<DpPartyView> records = dpPartyViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return records.size()==1?records.get(0):null;
    }

    public DpParty getById(Integer partyId){

        DpPartyExample example = new DpPartyExample();
        DpPartyExample.Criteria criteria = example.createCriteria().andIdEqualTo(partyId);
        List<DpParty> records = dpPartyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return records.size()==1?records.get(0):null;
    }

    public DpParty getByCode(String code){

        DpPartyExample example = new DpPartyExample();
        DpPartyExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        List<DpParty> records = dpPartyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return records.size()==1?records.get(0):null;
    }

    @Transactional
    public void insertSelective(DpParty record){

        Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate");
        record.setSortOrder(getNextSortOrder("dp_party", null));
        dpPartyMapper.insertSelective(record);
   }

    @Transactional
    public void del(List<Integer> partyIds){

        if (partyIds != null) {
            for (Integer partyId : partyIds) {
                dpPartyMapper.deleteByPrimaryKey(partyId);
            }
        }
    }

    @Transactional
    public void batchDel(Integer[] ids, boolean isDeleted){

        if(ids==null || ids.length==0) return;
        for (Integer id : ids){
            DpParty record = new DpParty();
            record.setId(id);
            record.setIsDeleted(isDeleted);

            if (isDeleted){
                //删除所有的委员会
                {
                    DpPartyMemberGroupExample example = new DpPartyMemberGroupExample();
                    example.createCriteria().andPartyIdEqualTo(id);
                    List<DpPartyMemberGroup> dpPartyMemberGroups = dpPartyMemberGroupMapper.selectByExample(example);
                    if (dpPartyMemberGroups.size() > 0){
                        List<Integer> groupIds = new ArrayList<>();
                        for (DpPartyMemberGroup dpPartyMemberGroup : dpPartyMemberGroups){
                            groupIds.add(dpPartyMemberGroup.getId());
                        }
                        dpPartyMemberGroupService.recover(groupIds.toArray(new Integer[]{}),true);
                        dpPartyMemberService.cancelMember(groupIds);
                    }
                }
                //删除所有的党派管理员
                dpOrgAdminService.delAllOrgAdmin(id);
            } else{
                record.setSortOrder(getNextSortOrder("dp_party","is_deleted"));//恢复更新排序
            }
            dpPartyMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(DpParty record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate");
        dpPartyMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, DpParty> findAll() {

        /*DpPartyExample example = new DpPartyExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");*/
        List<DpParty> records = dpPartyMapper.selectByExample(new DpPartyExample());
        Map<Integer, DpParty> map = new LinkedHashMap<>();
        for (DpParty record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    public DpParty findById(Integer id){
        DpPartyExample example = new DpPartyExample();
        example.createCriteria().andIdEqualTo(id);
        List<DpParty> dpParties = dpPartyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (dpParties.size() > 0) ? dpParties.get(0) : null;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        changeOrder("dp_party", "is_deleted=0", ORDER_BY_DESC, id, addNum);

    }
}
