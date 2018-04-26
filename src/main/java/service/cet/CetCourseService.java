package service.cet;

import controller.global.OpException;
import domain.cet.CetCourse;
import domain.cet.CetCourseExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.constants.CetConstants;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class CetCourseService extends BaseMapper {

    public boolean idDuplicate(Integer id, int year, byte type, int num){

        CetCourseExample example = new CetCourseExample();
        CetCourseExample.Criteria criteria = example.createCriteria()
                .andYearEqualTo(year).andTypeEqualTo(type).andNumEqualTo(num);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetCourseMapper.countByExample(example) > 0;
    }

    // C20181001
    public int genNum(int year, byte type){

        int num ;
        CetCourseExample example = new CetCourseExample();
        example.createCriteria().andYearEqualTo(year).andTypeEqualTo(type);
        example.setOrderByClause("num desc");
        List<CetCourse> records = cetCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(records.size()>0){
            num = records.get(0).getNum() + 1;
        }else{
            num = 1;
        }

        return num;
    }

    @Cacheable(value = "CetCourse", key = "#id")
    public CetCourse get(int id){

        return cetCourseMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="CetCourse", key = "#record.id")
    public void insertSelective(CetCourse record){

        Assert.isTrue(record.getFoundDate()!=null
                && CetConstants.CET_COURSE_TYPE_MAP.containsKey(record.getType()), "null");

        record.setYear(DateUtils.getYear(record.getFoundDate()));
        if(record.getNum()==null){
            record.setNum(genNum(record.getYear(), record.getType()));
        }

        if(idDuplicate(null, record.getYear(), record.getType(), record.getNum())){
            throw new OpException("编号重复。");
        }

        record.setSortOrder(getNextSortOrder("cet_course", "type="+ record.getType()));
        cetCourseMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="CetCourse", allEntries = true)
    public void fakeDel(Integer[] ids, boolean del){

        if(ids==null || ids.length==0) return;

        CetCourseExample example = new CetCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        CetCourse record = new CetCourse();
        record.setIsDeleted(del);

        cetCourseMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    @CacheEvict(value="CetCourse", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetCourseExample example = new CetCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        cetCourseMapper.deleteByExample( example);
    }

    @Transactional
    @CacheEvict(value="CetCourse", key = "#record.id")
    public int updateByPrimaryKeySelectiveWithNum(CetCourse record){

        Assert.isTrue(record.getFoundDate()!=null
                && CetConstants.CET_COURSE_TYPE_MAP.containsKey(record.getType()), "null");

        record.setYear(DateUtils.getYear(record.getFoundDate()));
        if(record.getNum()==null){
            record.setNum(genNum(record.getYear(), record.getType()));
        }
        if (idDuplicate(record.getId(), record.getYear(), record.getType(), record.getNum())) {
            throw new OpException("编号重复。");
        }

        return cetCourseMapper.updateByPrimaryKeySelective(record);
    }

    // 更新（不改变编号）
    @Transactional
    @CacheEvict(value="CetCourse", key = "#record.id")
    public int updateByPrimaryKeySelectiveWithoutNum(CetCourse record){

        record.setYear(null);
        record.setType(null);
        record.setNum(null);

        return cetCourseMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value="CetCourse", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CetCourse entity = get(id);
        Integer baseSortOrder = entity.getSortOrder();
        Byte type = entity.getType();
        CetCourseExample example = new CetCourseExample();
        if (addNum > 0) {

            example.createCriteria().andTypeEqualTo(type).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andTypeEqualTo(type).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetCourse> overEntities = cetCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetCourse targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("cet_course", "type="+ type, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_course", "type="+ type, baseSortOrder, targetEntity.getSortOrder());

            CetCourse record = new CetCourse();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetCourseMapper.updateByPrimaryKeySelective(record);
        }
    }
}
