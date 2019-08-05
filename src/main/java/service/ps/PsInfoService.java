package service.ps;

import domain.ps.PsInfo;
import domain.ps.PsInfoExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.PsInfoConstants;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PsInfoService extends PsBaseMapper {

    @Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public void insertSelective(PsInfo record){

        record.setSortOrder(getNextSortOrder("ps_info",""));
        psInfoMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public void del(Integer id){

        psInfoMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public void history(Integer[] ids, String _abolishDate){

        if(ids==null || ids.length==0) return;

        PsInfoExample example = new PsInfoExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        PsInfo record = new PsInfo();
        if (StringUtils.isNotBlank(_abolishDate)){
            record.setAbolishDate(DateUtils.parseDate(_abolishDate,DateUtils.YYYYMMDD_DOT));
        }
        record.setIsHistory(PsInfoConstants.PS_STATUS_IS_HISTORY);
        psInfoMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PsInfoExample example = new PsInfoExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        psInfoMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(PsInfo record){

        return psInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="PsInfo:ALL")
    public Map<Integer, PsInfo> findAll() {

        PsInfoExample example = new PsInfoExample();
        example.createCriteria().andIsHistoryEqualTo(false);
        example.setOrderByClause("sort_order desc");
        List<PsInfo> psInfoes = psInfoMapper.selectByExample(example);
        Map<Integer, PsInfo> map = new LinkedHashMap<>();
        for (PsInfo psInfo : psInfoes) {
            map.put(psInfo.getId(), psInfo);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "PsInfo:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        PsInfo entity = psInfoMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Boolean isHistory = entity.getIsHistory();

        PsInfoExample example = new PsInfoExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andIsHistoryEqualTo(isHistory).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andIsHistoryEqualTo(isHistory).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PsInfo> overEntities = psInfoMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            PsInfo targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("ps_info", "is_history="+isHistory, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ps_info", "is_history="+isHistory, baseSortOrder, targetEntity.getSortOrder());

            PsInfo record = new PsInfo();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            psInfoMapper.updateByPrimaryKeySelective(record);
        }
    }
}
