package service.pmd;

import domain.pmd.PmdPartyDonate;
import domain.pmd.PmdPartyDonateExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.pmd.PmdPartyDonateMapper;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PmdPartyDonateService extends BaseMapper {

    @Autowired
    private PmdPartyDonateMapper pmdPartyDonateMapper;

    public boolean idDuplicate(Integer id, String code){

        //Assert.isTrue(StringUtils.isNotBlank(code), "null");

        PmdPartyDonateExample example = new PmdPartyDonateExample();
        PmdPartyDonateExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return pmdPartyDonateMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PmdPartyDonate record){

        //Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate");
        //record.setSortOrder(getNextSortOrder("pmd_party_donate", null));
        pmdPartyDonateMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        pmdPartyDonateMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PmdPartyDonateExample example = new PmdPartyDonateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdPartyDonateMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PmdPartyDonate record){
        //if(StringUtils.isNotBlank(record.getCode()))
            //Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate");
        pmdPartyDonateMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, PmdPartyDonate> findAll() {

        PmdPartyDonateExample example = new PmdPartyDonateExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<PmdPartyDonate> records = pmdPartyDonateMapper.selectByExample(example);
        Map<Integer, PmdPartyDonate> map = new LinkedHashMap<>();
        for (PmdPartyDonate record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
