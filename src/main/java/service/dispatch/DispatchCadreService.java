package service.dispatch;

import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class DispatchCadreService extends BaseMapper {

    @Autowired
    private DispatchService dispatchService;
    // 按类别统计某个发文下的录入人数
    public int count(int dispatchId, byte type){

        DispatchCadreExample example = new DispatchCadreExample();
        example.createCriteria().andDispatchIdEqualTo(dispatchId).andTypeEqualTo(type);
        return (int) dispatchCadreMapper.countByExample(example);
    }

    @Transactional
    public void insertSelective(DispatchCadre record){

        record.setSortOrder(getNextSortOrder("dispatch_cadre", null));
        dispatchCadreMapper.insertSelective(record);

        dispatchService.update_dispatch_real_count();
    }
    @Transactional
    public void del(Integer id){

        dispatchCadreMapper.deleteByPrimaryKey(id);

        dispatchService.update_dispatch_real_count();
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DispatchCadreExample example = new DispatchCadreExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dispatchCadreMapper.deleteByExample(example);

        dispatchService.update_dispatch_real_count();
    }

    @Transactional
    public void updateByPrimaryKeySelective(DispatchCadre record){

        dispatchCadreMapper.updateByPrimaryKeySelective(record);

        dispatchService.update_dispatch_real_count();
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    /*@Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        DispatchCadre entity = dispatchCadreMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        DispatchCadreExample example = new DispatchCadreExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<DispatchCadre> overEntities = dispatchCadreMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            DispatchCadre targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("dispatch_cadre", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("dispatch_cadre", null, baseSortOrder, targetEntity.getSortOrder());

            DispatchCadre record = new DispatchCadre();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            dispatchCadreMapper.updateByPrimaryKeySelective(record);
        }
    }*/
}
