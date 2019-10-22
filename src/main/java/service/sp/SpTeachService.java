package service.sp;

import domain.cadre.CadreView;
import domain.sp.SpTeach;
import domain.sp.SpTeachExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpTeachService extends SpBaseMapper {

    public boolean idDuplicate(Integer id, Integer userId){

        SpTeachExample example = new SpTeachExample();
        SpTeachExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return spTeachMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(SpTeach record){

        record.setSortOrder(getNextSortOrder("sp_teach", null));
        spTeachMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        spTeachMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        SpTeachExample example = new SpTeachExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        spTeachMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(SpTeach record){
        /*if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate");*/
        spTeachMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, SpTeach> findAll() {

        SpTeachExample example = new SpTeachExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<SpTeach> records = spTeachMapper.selectByExample(example);
        Map<Integer, SpTeach> map = new LinkedHashMap<>();
        for (SpTeach record : records) {
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

        changeOrder("sp_teach", null, ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public void updateRecord(SpTeach spTeach){

        spTeach.setIsCadre(false);
        CadreView cadre = CmTag.getCadreByUserId(spTeach.getUserId());

        if (cadre != null){

            spTeach.setIsCadre(isLeaveCadre(cadre.getStatus()));
            spTeach.setAdminPost(cadre.getTitle());
        }
    }
}
