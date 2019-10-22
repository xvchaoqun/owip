package service.sp;

import domain.cadre.CadreView;
import domain.sp.SpNpc;
import domain.sp.SpNpcExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpNpcService extends SpBaseMapper {

    public boolean idDuplicate(Integer id, String code) {

        SpNpcExample example = new SpNpcExample();
        SpNpcExample.Criteria criteria = example.createCriteria();
        if (id != null) criteria.andIdNotEqualTo(id);

        return spNpcMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(SpNpc record) {

        record.setSortOrder(getNextSortOrder("sp_npc", "is_history=" + record.getIsHistory()));
        spNpcMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        spNpcMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        SpNpcExample example = new SpNpcExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        spNpcMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(SpNpc record) {

        spNpcMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, SpNpc> findAll() {

        SpNpcExample example = new SpNpcExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<SpNpc> records = spNpcMapper.selectByExample(example);
        Map<Integer, SpNpc> map = new LinkedHashMap<>();
        for (SpNpc record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        SpNpc record = spNpcMapper.selectByPrimaryKey(id);
        changeOrder("sp_npc", "is_history=" + record.getIsHistory(), ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public int bacthImport(List<SpNpc> records) {

        int addCount = 0;
        for (SpNpc record : records) {

            insertSelective(record);
            addCount++;
        }
        return addCount;
    }

    @Transactional
    public void setIsCadre(SpNpc spNpc) {

        spNpc.setIsCadre(false);
        CadreView cadre = CmTag.getCadreByUserId(spNpc.getUserId());
        if (cadre != null){

            spNpc.setIsCadre(isCurrentCadre(cadre.getStatus()));
        }
    }
}
