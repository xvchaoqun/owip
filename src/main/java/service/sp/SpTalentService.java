package service.sp;

import domain.cadre.CadreView;
import domain.sp.SpTalent;
import domain.sp.SpTalentExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpTalentService extends SpBaseMapper {

    public boolean idDuplicate(Integer id, Integer userId){


        SpTalentExample example = new SpTalentExample();
        SpTalentExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return spTalentMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(SpTalent record){

        record.setSortOrder(getNextSortOrder("sp_talent", null));
        spTalentMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        spTalentMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        SpTalentExample example = new SpTalentExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        spTalentMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(SpTalent record){

        spTalentMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, SpTalent> findAll() {

        SpTalentExample example = new SpTalentExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<SpTalent> records = spTalentMapper.selectByExample(example);
        Map<Integer, SpTalent> map = new LinkedHashMap<>();
        for (SpTalent record : records) {
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
    public void changeOrder(int id, int addNum) {

        changeOrder("sp_talent", null, ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public int bacthImport(List<SpTalent> records) {

        int addCount = 0;
        for (SpTalent record : records) {

            insertSelective(record);
            addCount++;
        }
        return addCount;
    }

    @Transactional
    public void updateRecord(SpTalent spTalent){

        spTalent.setIsCadre(false);
        CadreView cadre = CmTag.getCadreByUserId(spTalent.getUserId());

        if (cadre !=null){

            spTalent.setIsCadre(isCurrentCadre(cadre.getStatus()));
            spTalent.setAdminPost(cadre.getTitle());
        }
    }
}
