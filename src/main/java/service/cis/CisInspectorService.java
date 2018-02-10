package service.cis;

import domain.cis.CisInspector;
import domain.cis.CisInspectorExample;
import domain.cis.CisInspectorView;
import domain.cis.CisInspectorViewExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.CisConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class CisInspectorService extends BaseMapper {

    public CisInspectorView getInspector(int id){

        return cisInspectorViewMapper.selectByPrimaryKey(id);
    }

    public List<CisInspectorView> getInspectors(byte status){

        CisInspectorViewExample example = new CisInspectorViewExample();
        example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");
        return cisInspectorViewMapper.selectByExample(example);
    }

    @Transactional
    public void insertSelective(CisInspector record) {

        record.setSortOrder(getNextSortOrder("cis_inspector", "status=" + record.getStatus()));
        cisInspectorMapper.insertSelective(record);
    }

    public void abolish(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CisInspector record = new CisInspector();
        record.setStatus(CisConstants.CIS_INSPECTOR_STATUS_HISTORY);

        CisInspectorExample example = new CisInspectorExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        cisInspectorMapper.updateByExampleSelective(record, example);
    }

    public void reuse(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CisInspector record = new CisInspector();
        record.setStatus(CisConstants.CIS_INSPECTOR_STATUS_NOW);

        CisInspectorExample example = new CisInspectorExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        cisInspectorMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CisInspector record = new CisInspector();
        record.setStatus(CisConstants.CIS_INSPECTOR_STATUS_DELETE);

        CisInspectorExample example = new CisInspectorExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        cisInspectorMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CisInspector record) {
        return cisInspectorMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        CisInspector entity = cisInspectorMapper.selectByPrimaryKey(id);
        Byte status = entity.getStatus();
        Integer baseSortOrder = entity.getSortOrder();

        CisInspectorExample example = new CisInspectorExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(status).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andStatusEqualTo(status).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CisInspector> overEntities = cisInspectorMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CisInspector targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("cis_inspector", "status=" + status, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cis_inspector", "status=" + status, baseSortOrder, targetEntity.getSortOrder());

            CisInspector record = new CisInspector();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cisInspectorMapper.updateByPrimaryKeySelective(record);
        }
    }
}
