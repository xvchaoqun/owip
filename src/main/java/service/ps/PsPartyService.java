package service.ps;

import domain.ps.PsInfo;
import domain.ps.PsInfoExample;
import domain.ps.PsParty;
import domain.ps.PsPartyExample;
import domain.unit.UnitPost;
import domain.unit.UnitPostExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.PsInfoConstants;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PsPartyService extends PsBaseMapper {

    @Transactional
    public void insertSelective(PsParty record){

        record.setSortOrder(getNextSortOrder("ps_party",
                String.format("ps_id=%s and is_host=%s and is_finish=%s",
                        record.getPsId(), record.getIsHost(),record.getIsFinish())));
        psPartyMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        psPartyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PsPartyExample example = new PsPartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        psPartyMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PsParty record){

        psPartyMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void history(Integer[] ids, String _endDate){

        if(ids==null || ids.length==0) return;

        PsPartyExample example = new PsPartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        PsParty record = new PsParty();
        if (StringUtils.isNotBlank(_endDate)){
            record.setEndDate(DateUtils.parseDate(_endDate,DateUtils.YYYYMM));
        }
        record.setIsFinish(true);
        psPartyMapper.updateByExampleSelective(record, example);
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

        byte orderBy = ORDER_BY_DESC;

        PsParty entity = psPartyMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        PsPartyExample example = new PsPartyExample();
        if (addNum * orderBy > 0) {

            example.createCriteria().andPsIdEqualTo(entity.getPsId()).andIsHostEqualTo(entity.getIsHost())
                    .andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andPsIdEqualTo(entity.getPsId()).andIsHostEqualTo(entity.getIsHost())
                    .andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PsParty> overEntities = psPartyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PsParty targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum * orderBy > 0)
                commonMapper.downOrder("ps_party", String.format("ps_id=%s and is_host=%s", entity.getPsId(), entity.getIsHost()),
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ps_party", String.format("ps_id=%s and is_host=%s", entity.getPsId(), entity.getIsHost()),
                        baseSortOrder, targetEntity.getSortOrder());

            PsParty record = new PsParty();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            psPartyMapper.updateByPrimaryKeySelective(record);
        }
    }

    //是否拥有主建单位
    public boolean isHaveHostUnit(Integer psId){
        PsPartyExample psPartyExample = new PsPartyExample();
        psPartyExample.createCriteria()
                .andPsIdEqualTo(psId)
                .andIsFinishEqualTo(false)
                .andIsHostEqualTo(true);
        return psPartyMapper.selectByExample(psPartyExample).size() > 0;
    }

    //更新建设单位状态
    public void updatePartyStatus(Integer[] ids,String _endDate,Boolean isFinish,Boolean isHost){
        if(ids==null || ids.length==0) return;

        Date endDate = null;
        if (StringUtils.isNotBlank(_endDate)){
            endDate = DateUtils.parseDate(_endDate,DateUtils.YYYYMM);
        }
        for (Integer id : ids){
            PsParty psParty = new PsParty();
            psParty.setEndDate(endDate);
            psParty.setIsFinish(isFinish);
            psParty.setIsHost(isHost);
            psParty.setId(id);
            psParty.setSortOrder(getNextSortOrder("ps_party","is_finish="+isFinish+" and is_host="+isHost));

            psPartyMapper.updateByPrimaryKeySelective(psParty);
        }
    }
}
