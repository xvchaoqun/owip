package service.pm;

import domain.pm.PmQuarterParty;
import domain.pm.PmQuarterPartyExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PmQuarterPartyService extends PmBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        PmQuarterPartyExample example = new PmQuarterPartyExample();
        PmQuarterPartyExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return pmQuarterPartyMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="PmQuarterParty:ALL", allEntries = true)
    public void insertSelective(PmQuarterParty record){

        pmQuarterPartyMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="PmQuarterParty:ALL", allEntries = true)
    public void del(Integer id){

        pmQuarterPartyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="PmQuarterParty:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PmQuarterPartyExample example = new PmQuarterPartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmQuarterPartyMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="PmQuarterParty:ALL", allEntries = true)
    public void updateByPrimaryKeySelective(PmQuarterParty record){

        pmQuarterPartyMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="PmQuarterParty:ALL")
    public Map<Integer, PmQuarterParty> findAll() {

        PmQuarterPartyExample example = new PmQuarterPartyExample();
        example.setOrderByClause("sort_order desc");
        List<PmQuarterParty> records = pmQuarterPartyMapper.selectByExample(example);
        Map<Integer, PmQuarterParty> map = new LinkedHashMap<>();
        for (PmQuarterParty record : records) {
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
    @CacheEvict(value = "PmQuarterParty:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        changeOrder("pm_quarter_party", null, ORDER_BY_DESC, id, addNum);
    }
}
