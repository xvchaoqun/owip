package service.oa;

import domain.oa.OaGrid;
import domain.oa.OaGridExample;
import domain.oa.OaGridParty;
import domain.oa.OaGridPartyExample;
import domain.party.Party;
import domain.party.PartyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.PartyService;
import sys.constants.OaConstants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OaGridService extends OaBaseMapper {

    @Autowired
    public PartyService partyService;

    @Transactional
    public void insertSelective(OaGrid record){

        oaGridMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids, Byte delete){

        if(ids==null || ids.length==0) return;

        OaGrid record = new OaGrid();
        record.setStatus(delete);
        OaGridExample example = new OaGridExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        oaGridMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void realDel(Integer[] ids) {

        if(ids==null || ids.length==0) return;

        OaGridExample example = new OaGridExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        oaGridMapper.deleteByExample(example);
    }

    @Transactional
    public void back(Integer[] ids) {

        if(ids==null || ids.length==0) return;

        OaGrid record = new OaGrid();
        record.setStatus(OaConstants.OA_GRID_HASDELETED);
        OaGridExample example = new OaGridExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        oaGridMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(OaGrid record){

        oaGridMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void batchRelease(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PartyExample partyExample = new PartyExample();
        partyExample.createCriteria().andIsDeletedEqualTo(false);
        partyExample.setOrderByClause("sort_order desc");
        List<Party> partyList = partyMapper.selectByExample(partyExample);
        //所有分党委id
        List<Integer> partyIdList = partyList.stream().map(Party::getId).collect(Collectors.toList());

        for (Integer id : ids) {

            OaGridPartyExample example = new OaGridPartyExample();
            example.createCriteria().andGridIdEqualTo(id);
            List<OaGridParty> oaGridPartyList = oaGridPartyMapper.selectByExample(example);
            //已经发布数据模板的分党委id
            List<Integer> _partyIdList = oaGridPartyList.stream().map(OaGridParty::getPartyId).collect(Collectors.toList());
            if (_partyIdList != null && _partyIdList.size() >= 0)
                partyIdList.removeAll(_partyIdList);

            OaGrid oaGrid = oaGridMapper.selectByPrimaryKey(id);

            for (Integer partyId : partyIdList) {
                OaGridParty record = new OaGridParty();
                record.setGridId(id);
                record.setYear(oaGrid.getYear());
                record.setPartyId(partyId);
                record.setGridName(oaGrid.getName());
                record.setPartyName(partyMapper.selectByPrimaryKey(partyId).getName());
                record.setStatus(OaConstants.OA_GRID_PARTY_INI);

                oaGridPartyMapper.insertSelective(record);
            }
        }
    }
}
