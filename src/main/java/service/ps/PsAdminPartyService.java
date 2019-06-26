package service.ps;

import domain.ps.PsAdminParty;
import domain.ps.PsAdminPartyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class PsAdminPartyService extends PsBaseMapper {

    @Transactional
    public void insertSelective(PsAdminParty record){

        psAdminPartyMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        psAdminPartyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PsAdminPartyExample example = new PsAdminPartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        psAdminPartyMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PsAdminParty record){

        psAdminPartyMapper.updateByPrimaryKeySelective(record);
    }
}
