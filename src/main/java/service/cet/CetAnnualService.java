package service.cet;

import domain.cet.CetAnnual;
import domain.cet.CetAnnualExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class CetAnnualService extends CetBaseMapper {

    /*public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        CetAnnualExample example = new CetAnnualExample();
        CetAnnualExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetAnnualMapper.countByExample(example) > 0;
    }*/

    @Transactional
    public void insertSelective(CetAnnual record){

        cetAnnualMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetAnnualMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetAnnualExample example = new CetAnnualExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetAnnualMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetAnnual record){
        return cetAnnualMapper.updateByPrimaryKeySelective(record);
    }

   /* public Map<Integer, CetAnnual> findAll() {

        CetAnnualExample example = new CetAnnualExample();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<CetAnnual> cetAnnuales = cetAnnualMapper.selectByExample(example);
        Map<Integer, CetAnnual> map = new LinkedHashMap<>();
        for (CetAnnual cetAnnual : cetAnnuales) {
            map.put(cetAnnual.getId(), cetAnnual);
        }

        return map;
    }*/
}
