package service.pmd;

import domain.party.Party;
import domain.pmd.PmdPayParty;
import domain.pmd.PmdPayPartyExample;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.party.PartyService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PmdPayPartyService extends BaseMapper {

    @Autowired
    private PartyService partyService;

    public Map<Integer, PmdPayParty> getAllPayPartyIdSet(Boolean excludeDeletedParty){

        Map<Integer, PmdPayParty> resultMap = new HashMap<>();
        List<PmdPayParty> pmdPayParties = pmdPayPartyMapper.selectByExample(new PmdPayPartyExample());
        for (PmdPayParty pmdPayParty : pmdPayParties) {

            int partyId = pmdPayParty.getPartyId();
            if(BooleanUtils.isTrue(excludeDeletedParty)){
                Party party = partyService.findAll().get(partyId);
                if(BooleanUtils.isTrue(party.getIsDeleted())) continue;
            }

            resultMap.put(partyId, pmdPayParty);
        }

        return resultMap;
    }

    @Transactional
    public void insertSelective(PmdPayParty record) {

        pmdPayPartyMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer partyId) {

        pmdPayPartyMapper.deleteByPrimaryKey(partyId);
    }

    @Transactional
    public void batchDel(Integer[] partyIds) {

        if (partyIds == null || partyIds.length == 0) return;

        PmdPayPartyExample example = new PmdPayPartyExample();
        example.createCriteria().andPartyIdIn(Arrays.asList(partyIds));
        pmdPayPartyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PmdPayParty record) {

        return pmdPayPartyMapper.updateByPrimaryKeySelective(record);
    }
}
