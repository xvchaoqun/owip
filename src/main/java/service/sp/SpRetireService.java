package service.sp;

import domain.sp.SpRetire;
import domain.sp.SpRetireExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpRetireService extends SpBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        SpRetireExample example = new SpRetireExample();
        SpRetireExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return spRetireMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(SpRetire record){

        record.setSortOrder(getNextSortOrder("sp_retire", null));
        spRetireMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        spRetireMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        SpRetireExample example = new SpRetireExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        spRetireMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(SpRetire record){

        spRetireMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, SpRetire> findAll() {

        SpRetireExample example = new SpRetireExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<SpRetire> records = spRetireMapper.selectByExample(example);
        Map<Integer, SpRetire> map = new LinkedHashMap<>();
        for (SpRetire record : records) {
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

        changeOrder("sp_retire", null, ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public void insert(Integer[] userIds,String remark){

        for (Integer userId : userIds){

            SpRetire spRetire = new SpRetire();
            spRetire.setSortOrder(getNextSortOrder("sp_retire", null));
            spRetire.setUserId(userId);
            spRetire.setRemark(remark);
            insertSelective(spRetire);
        }
    }

    @Transactional
    public void UpdateRecord(){


    }
}
