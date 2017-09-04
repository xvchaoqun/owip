package service.pcs;

import domain.pcs.PcsPartyView;
import domain.pcs.PcsPartyViewExample;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.util.List;

/**
 * Created by lm on 2017/9/4.
 */
@Service
public class PcsPartyViewService extends BaseMapper {

    public PcsPartyView get(int partyId){

        PcsPartyViewExample example = new PcsPartyViewExample();
        example.createCriteria().andIdEqualTo(partyId);

        List<PcsPartyView> pcsPartyViews = pcsPartyViewMapper.selectByExample(example);
        return pcsPartyViews.size()==0?null:pcsPartyViews.get(0);
    }

}
