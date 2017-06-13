package service.dispatch;

import domain.dispatch.Dispatch;
import domain.dispatch.DispatchExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DispatchService extends BaseMapper {

    @Autowired
    private DispatchTypeService dispatchTypeService;
    public static String CODE_REG_STR = ".*\\[\\d{4}\\]([\\d.]*)号";
    // String numStr = PatternUtils.withdraw(CODE_REG_STR, code);
    // String numStr = NumberUtils.frontCompWithZore(num, 2);
    // String.format("%s[%s]%s号", dispatchType.getName(), year, numStr);

    @Transactional
    @CacheEvict(value="Dispatch:ALL", allEntries = true)
    public void update_dispatch_real_count(){

        iDispatchMapper.update_dispatch_real_count();
    }

    public boolean idDuplicate(Integer id, int dispatchTypeId, int year, int code){

        DispatchExample example = new DispatchExample();
        DispatchExample.Criteria criteria = example.createCriteria()
                .andCodeEqualTo(code)
                .andDispatchTypeIdEqualTo(dispatchTypeId).andYearEqualTo(year);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dispatchMapper.countByExample(example) > 0;
    }

    // 师党干[2015]01号
    public int genCode(int dispatchTypeId, int year){

        //Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
       //DispatchType dispatchType = dispatchTypeMap.get(dispatchTypeId);
        int num ;
        DispatchExample example = new DispatchExample();
        example.createCriteria().andYearEqualTo(year).andDispatchTypeIdEqualTo(dispatchTypeId);
        example.setOrderByClause("code desc");
        List<Dispatch> dispatches = dispatchMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(dispatches.size()>0){
            int code = dispatches.get(0).getCode();
            num = code + 1;
        }else{
            num = 1;
        }
        return num;
    }

    @Transactional
    @CacheEvict(value="Dispatch:ALL", allEntries = true)
    public int insertSelective(Dispatch record){

        if(record.getCode()!=null) {
            Assert.isTrue(!idDuplicate(null, record.getDispatchTypeId(), record.getYear(), record.getCode()), "duplicate");
        }

        record.setSortOrder(getNextSortOrder("dispatch", "1=1"));
        record.setHasChecked(false);
        return dispatchMapper.insertSelective(record);

        /*Integer id = record.getId();
        Dispatch _record = new Dispatch();
        _record.setId(id);
        _record.setSortOrder(id);
        _record.setMeetingTime(record.getMeetingTime());
        return dispatchMapper.updateByPrimaryKeySelective(_record);*/
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
        iDispatchMapper.del_dispatch_file(id);
    }

    @Transactional
    @CacheEvict(value="Dispatch:ALL", allEntries = true)
    public void delPpt(int id){
        iDispatchMapper.del_dispatch_ppt(id);
    }

    @Transactional
    @CacheEvict(value="Dispatch:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Dispatch record){

        if(record.getCode()!=null) {
            Assert.isTrue(!idDuplicate(record.getId(),record.getDispatchTypeId(), record.getYear(), record.getCode()), "duplicate");
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
                commonMapper.downOrder("dispatch", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("dispatch", null, baseSortOrder, targetEntity.getSortOrder());

            Dispatch record = new Dispatch();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            record.setMeetingTime(entity.getMeetingTime());
            dispatchMapper.updateByPrimaryKeySelective(record);
        }
    }
}
