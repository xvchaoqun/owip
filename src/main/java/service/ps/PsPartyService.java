package service.ps;

import domain.ps.PsParty;
import domain.ps.PsPartyExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;

@Service
public class PsPartyService extends PsBaseMapper {

    public boolean idDuplicate(Integer id, int psId, int partyId){

        //判断选择单位是否已经是建设单位
        PsPartyExample psPartyExample = new PsPartyExample();
        PsPartyExample.Criteria criteria = psPartyExample.createCriteria()
                .andIsFinishEqualTo(false)
                .andPartyIdEqualTo(partyId)
                .andPsIdEqualTo(psId);

        if (id != null) criteria.andIdNotEqualTo(id);

        return psPartyMapper.countByExample(psPartyExample)>0;
    }

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

        PsParty entity = psPartyMapper.selectByPrimaryKey(id);
        changeOrder("ps_party", String.format("ps_id=%s and is_host=%s and is_finish=%s",
                entity.getPsId(), entity.getIsHost(), entity.getIsFinish()), ORDER_BY_DESC, id, addNum);
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
    @Transactional
    public void updatePartyStatus(Integer[] ids, Date endDate, boolean isFinish){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids){

            PsParty psParty = psPartyMapper.selectByPrimaryKey(id);
            Boolean isHost = psParty.getIsHost();
            PsParty record = new PsParty();
            record.setId(id);
            record.setEndDate(endDate);
            record.setIsFinish(isFinish);
            record.setSortOrder(getNextSortOrder("ps_party",
                    "is_finish="+ isFinish +" and is_host="+ isHost+" and ps_id="+psParty.getPsId()));

            psPartyMapper.updateByPrimaryKeySelective(record);
        }
    }
}
