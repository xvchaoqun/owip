package service.ps;

import domain.ps.PsAdminParty;
import domain.ps.PsAdminPartyExample;
import org.apache.commons.lang3.BooleanUtils;
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
        if(BooleanUtils.isFalse(record.getIsHistory())){
            commonMapper.excuteSql("update ps_admin_party set end_date=null where id="+ record.getId());
        }
    }

    public boolean idDuplicate(Integer id, Integer partyId, int adminId){

        PsAdminPartyExample example = new PsAdminPartyExample();
        PsAdminPartyExample.Criteria criteria = example.createCriteria()
                                                .andPartyIdEqualTo(partyId)
                                                .andAdminIdEqualTo(adminId)
                                                .andIsHistoryEqualTo(false);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return psAdminPartyMapper.countByExample(example) > 0;
    }
}
