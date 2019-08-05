package service.ps;

import domain.ps.PsInfo;
import domain.ps.PsInfoExample;
import domain.ps.PsParty;
import domain.ps.PsPartyExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.PsInfoConstants;
import sys.utils.DateUtils;

import java.util.Arrays;

@Service
public class PsPartyService extends PsBaseMapper {

    @Transactional
    public void insertSelective(PsParty record){

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

}
