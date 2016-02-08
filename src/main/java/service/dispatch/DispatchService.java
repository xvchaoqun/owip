package service.dispatch;

import domain.Dispatch;
import domain.DispatchExample;
import domain.DispatchType;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.utils.NumberUtils;
import sys.utils.PatternUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DispatchService extends BaseMapper {

    @Autowired
    private DispatchTypeService dispatchTypeService;
    public static String CODE_REG_STR = ".*\\[\\d{4}\\]([\\d.]*)号";

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        DispatchExample example = new DispatchExample();
        DispatchExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dispatchMapper.countByExample(example) > 0;
    }

    // 师党干[2015]01号
    public String genCode(int dispatchTypeId, int year){

        Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
        DispatchType dispatchType = dispatchTypeMap.get(dispatchTypeId);
        int num ;
        DispatchExample example = new DispatchExample();
        example.createCriteria().andYearEqualTo(year).andDispatchTypeIdEqualTo(dispatchTypeId);
        example.setOrderByClause("code desc");
        List<Dispatch> dispatches = dispatchMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(dispatches.size()>0){
            String code = dispatches.get(0).getCode();
            String numStr = PatternUtils.withdraw(CODE_REG_STR, code);
            num = Integer.parseInt(numStr) + 1;
        }else{
            num = 1;
        }
        String numStr = NumberUtils.frontCompWithZore(num, 2);
        return String.format("%s[%s]%s号", dispatchType.getName(), year, numStr);
    }

    @Transactional
    @CacheEvict(value="Dispatch:ALL", allEntries = true)
    public int insertSelective(Dispatch record){

        if(StringUtils.isNotBlank(record.getCode())) {
            if (!PatternUtils.match(CODE_REG_STR, record.getCode())) {
                throw new RuntimeException("发文号格式有误");
            }
            Assert.isTrue(!idDuplicate(null, record.getCode()));
        }

        dispatchMapper.insertSelective(record);

        Integer id = record.getId();
        Dispatch _record = new Dispatch();
        _record.setId(id);
        _record.setSortOrder(id);
        return dispatchMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="Dispatch:ALL", allEntries = true)
    public void del(Integer id){

        dispatchMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="Dispatch:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DispatchExample example = new DispatchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dispatchMapper.deleteByExample(example);
    }
    @Transactional
    @CacheEvict(value="Dispatch:ALL", allEntries = true)
    public void delFile(int id){
        updateMapper.del_dispatch_file(id);
    }

    @Transactional
    @CacheEvict(value="Dispatch:ALL", allEntries = true)
    public void delPpt(int id){
        updateMapper.del_dispatch_ppt(id);
    }

    @Transactional
    @CacheEvict(value="Dispatch:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Dispatch record){

        if(StringUtils.isNotBlank(record.getCode())) {
            if(!PatternUtils.match(CODE_REG_STR, record.getCode())){
                throw new RuntimeException("发文号格式有误");
            }
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()));
        }

        return dispatchMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="Dispatch:ALL")
    public Map<Integer, Dispatch> findAll() {

        DispatchExample example = new DispatchExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<Dispatch> dispatches = dispatchMapper.selectByExample(example);
        Map<Integer, Dispatch> map = new LinkedHashMap<>();
        for (Dispatch dispatch : dispatches) {
            map.put(dispatch.getId(), dispatch);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "Dispatch:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        Dispatch entity = dispatchMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        DispatchExample example = new DispatchExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Dispatch> overEntities = dispatchMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            Dispatch targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("base_dispatch", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("base_dispatch", baseSortOrder, targetEntity.getSortOrder());

            Dispatch record = new Dispatch();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            dispatchMapper.updateByPrimaryKeySelective(record);
        }
    }
}
