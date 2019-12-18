package service.dp;

import domain.dp.DpFamily;
import domain.dp.DpFamilyExample;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DpFamilyService extends DpBaseMapper {

   /* public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DpFamilyExample example = new DpFamilyExample();
        DpFamilyExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dpFamilyMapper.countByExample(example) > 0;
    }*/

    @Transactional
    public void insertSelective(DpFamily record){

        if (BooleanUtils.isTrue(record.getWithGod())) {
            record.setBirthday(null);
        }
        //Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate");
        record.setSortOrder(getNextSortOrder("dp_family", null));
        dpFamilyMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        dpFamilyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DpFamilyExample example = new DpFamilyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dpFamilyMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DpFamily record){
        if (BooleanUtils.isTrue(record.getWithGod())) {
            commonMapper.excuteSql("update dp_family set birthday=null where id=" + record.getId());
        }
            //Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate");
        dpFamilyMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, DpFamily> findAll() {

        DpFamilyExample example = new DpFamilyExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DpFamily> records = dpFamilyMapper.selectByExample(example);
        Map<Integer, DpFamily> map = new LinkedHashMap<>();
        for (DpFamily record : records) {
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

        DpFamily entity = dpFamilyMapper.selectByPrimaryKey(id);
        Integer userId = entity.getUserId();

        changeOrder("dp_family", "user_id=" + userId + " and status=" + SystemConstants.RECORD_STATUS_FORMAL,
                ORDER_BY_ASC, id, addNum);
    }
}
