package service.dp.dpCommon;

import domain.dp.DpParty;
import domain.dp.DpPartyExample;
import domain.dp.DpPartyMemberGroup;
import domain.dp.DpPartyMemberGroupExample;
import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import service.dp.DpBaseMapper;
import sys.HttpResponseMethod;

import java.util.ArrayList;
import java.util.List;

@Service
public class DpCommonService extends DpBaseMapper  implements HttpResponseMethod {

    @Cacheable(value = "DpParty:ALL", key = "#partyId")
    public DpParty getDpPartyByPartyId(Integer partyId) {

        DpPartyExample example = new DpPartyExample();
        example.createCriteria().andIdEqualTo(partyId);
        List<DpParty> dpParties = dpPartyMapper.selectByExampleWithRowbounds(example,new RowBounds(0, 1));

        return (dpParties.size() > 0) ? dpParties.get(0) : null;
    }

    @Cacheable(value = "DpPartyMember:ALL",key = "#groupId")
    public DpParty getDpPartyByGroupId(Integer groupId) {

        DpPartyMemberGroupExample dpPartyMemberGroupExample = new DpPartyMemberGroupExample();
        dpPartyMemberGroupExample.createCriteria().andIdEqualTo(groupId);
        List<DpPartyMemberGroup> dpPartyMemberGroups = dpPartyMemberGroupMapper.selectByExample(dpPartyMemberGroupExample);
        List<Integer> partyIds = new ArrayList<>();
        for (DpPartyMemberGroup dpPartyMemberGroup : dpPartyMemberGroups){
            partyIds.add(dpPartyMemberGroup.getPartyId());
        }

        DpPartyExample example = new DpPartyExample();
        example.createCriteria().andIdIn(partyIds);
        List<DpParty> dpParties = dpPartyMapper.selectByExampleWithRowbounds(example,new RowBounds(0, 1));

        return (dpParties.size() > 0) ? dpParties.get(0) : null;
    }

    @Cacheable(value = "SysUserView:ID_", key = "#id")
    public SysUserView findById(int id) {

        Byte type = 1;
        SysUserViewExample example = new SysUserViewExample();
        example.createCriteria().andIdEqualTo(id).andTypeEqualTo(type);
        List<SysUserView> users = sysUserViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (users.size() > 0) ? users.get(0) : null;
    }
}
