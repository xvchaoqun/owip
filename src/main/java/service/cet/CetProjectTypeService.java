package service.cet;

import domain.cet.CetProjectType;
import domain.cet.CetProjectTypeExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.CetConstants;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetProjectTypeService extends CetBaseMapper {


    @Transactional
    @CacheEvict(value="CetProjectType", allEntries = true)
    public void insertSelective(CetProjectType record){

        record.setSortOrder(getNextSortOrder("cet_project_type",
                "type=" +  record.getType()
                        + " and is_party_project=" + record.getIsPartyProject()));

        cetProjectTypeMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="CetProjectType", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetProjectTypeExample example = new CetProjectTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetProjectTypeMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="CetProjectType", allEntries = true)
    public int updateByPrimaryKeySelective(CetProjectType record){

        return cetProjectTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="CetProjectType", key = "#cls")
    public Map<Integer, CetProjectType> findAll(byte cls) {

        boolean isPartyProject = (cls==3 || cls==4);
        byte type = CetConstants.CET_PROJECT_TYPE_SPECIAL;
        if(cls==2 || cls==4) {
            type = CetConstants.CET_PROJECT_TYPE_DAILY;
        }

        CetProjectTypeExample example = new CetProjectTypeExample();
        example.createCriteria().andIsPartyProjectEqualTo(isPartyProject).andTypeEqualTo(type);
        example.setOrderByClause("sort_order asc");
        List<CetProjectType> records = cetProjectTypeMapper.selectByExample(example);
        Map<Integer, CetProjectType> map = new LinkedHashMap<>();
        for (CetProjectType record : records) {
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
    @CacheEvict(value = "CetProjectType", allEntries = true)
    public void changeOrder(int id, int addNum) {

        CetProjectType cetProjectType = cetProjectTypeMapper.selectByPrimaryKey(id);
        byte type = cetProjectType.getType();
        boolean isPartyProject = cetProjectType.getIsPartyProject();

        changeOrder("cet_project_type",
                "type=" + type + " and is_party_project=" + isPartyProject, ORDER_BY_ASC, id, addNum);
    }
}
