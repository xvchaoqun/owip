package service.sc.scDispatch;

import domain.sc.scDispatch.ScDispatchUser;
import domain.sc.scDispatch.ScDispatchUserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ScDispatchUserService extends BaseMapper {

    @Transactional
    public void insertSelective(ScDispatchUser record){

        record.setSortOrder(getNextSortOrder("sc_dispatch_user", "type=" + record.getType()));
        scDispatchUserMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scDispatchUserMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScDispatchUserExample example = new ScDispatchUserExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scDispatchUserMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScDispatchUser record){

        return scDispatchUserMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ScDispatchUser entity = scDispatchUserMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Byte type = entity.getType();

        ScDispatchUserExample example = new ScDispatchUserExample();
        if (addNum > 0) {

            example.createCriteria().andTypeEqualTo(type).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andTypeEqualTo(type).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ScDispatchUser> overEntities = scDispatchUserMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ScDispatchUser targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("sc_dispatch_user", "type=" + type, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("sc_dispatch_user", "type=" + type, baseSortOrder, targetEntity.getSortOrder());

            ScDispatchUser record = new ScDispatchUser();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            scDispatchUserMapper.updateByPrimaryKeySelective(record);
        }
    }
}
