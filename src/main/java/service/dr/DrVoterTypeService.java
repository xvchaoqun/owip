package service.dr;

import domain.dr.DrVoterType;
import domain.dr.DrVoterTypeExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrVoterTypeService extends DrBaseMapper {

    /*public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DrVoterTypeExample example = new DrVoterTypeExample();
        DrVoterTypeExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return drVoterTypeMapper.countByExample(example) > 0;
    }*/

    @Transactional
    @CacheEvict(value="DrVoterType:ALL", allEntries = true)
    public void insertSelective(DrVoterType record){

        String whereSql = "tpl_id=" + record.getTplId();
        record.setSortOrder(getNextSortOrder("dr_voter_type", whereSql));
        drVoterTypeMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="DrVoterType:ALL", allEntries = true)
    public void del(Integer id){

        drVoterTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="DrVoterType:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DrVoterTypeExample example = new DrVoterTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drVoterTypeMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="DrVoterType:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(DrVoterType record){
        return drVoterTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="DrVoterType:ALL")
    public Map<Integer, DrVoterType> findAll(int tplId) {

        DrVoterTypeExample example = new DrVoterTypeExample();
        example.createCriteria().andTplIdEqualTo(tplId);
        example.setOrderByClause("sort_order asc");
        List<DrVoterType> drVoterTypees = drVoterTypeMapper.selectByExample(example);
        Map<Integer, DrVoterType> map = new LinkedHashMap<>();
        for (DrVoterType drVoterType : drVoterTypees) {
            map.put(drVoterType.getId(), drVoterType);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "DrVoterType:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        DrVoterType entity = drVoterTypeMapper.selectByPrimaryKey(id);
        changeOrder("dr_voter_type", "tpl_id=" + entity.getTplId(), ORDER_BY_ASC, id, addNum);
    }
}
