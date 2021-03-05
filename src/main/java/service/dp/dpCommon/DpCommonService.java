package service.dp.dpCommon;

import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadreParty;
import domain.cadre.CadrePartyExample;
import domain.dp.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.dp.DpBaseMapper;
import service.sys.SysUserService;
import sys.HttpResponseMethod;
import sys.constants.CadreConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.ArrayList;
import java.util.List;

@Service
public class DpCommonService extends DpBaseMapper implements HttpResponseMethod {

    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    private SysUserService sysUserService;

    public DpParty getDpPartyByPartyId(Integer partyId) {

        DpPartyExample example = new DpPartyExample();
        example.createCriteria().andIdEqualTo(partyId);
        List<DpParty> dpParties = dpPartyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (dpParties.size() > 0) ? dpParties.get(0) : null;
    }

    public DpParty getDpPartyByGroupId(Integer groupId) {

        DpPartyMemberGroupExample dpPartyMemberGroupExample = new DpPartyMemberGroupExample();
        dpPartyMemberGroupExample.createCriteria().andIdEqualTo(groupId);
        List<DpPartyMemberGroup> dpPartyMemberGroups = dpPartyMemberGroupMapper.selectByExample(dpPartyMemberGroupExample);
        List<Integer> partyIds = new ArrayList<>();
        for (DpPartyMemberGroup dpPartyMemberGroup : dpPartyMemberGroups) {
            partyIds.add(dpPartyMemberGroup.getPartyId());
        }

        DpPartyExample example = new DpPartyExample();
        example.createCriteria().andIdIn(partyIds);
        List<DpParty> dpParties = dpPartyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (dpParties.size() > 0) ? dpParties.get(0) : null;
    }

    public Cadre findOrCreateCadre(int userId) {

        Cadre cadre = CmTag.getCadre(userId);
        if (cadre == null) {
            CadreService cadreService = CmTag.getBean(CadreService.class);
            cadre = cadreService.addTempCadre(userId);
        }

        return cadre;
    }

    //更新统战成员角色
    public void updateMemberRole(Integer userId) {

        DpMember dpMember = dpMemberMapper.selectByPrimaryKey(userId);

        DpNpmExample dpNpmExample = new DpNpmExample();
        dpNpmExample.createCriteria().andUserIdEqualTo(userId);
        List<DpNpm> dpNpmList = dpNpmMapper.selectByExample(dpNpmExample);

        DpNprExample dpNprExample = new DpNprExample();
        dpNprExample.createCriteria().andUserIdEqualTo(userId);
        List<DpNpr> dpNprList = dpNprMapper.selectByExample(dpNprExample);

        DpOmExample dpOmExample = new DpOmExample();
        dpOmExample.createCriteria().andUserIdEqualTo(userId);
        List<DpOm> dpOmList = dpOmMapper.selectByExample(dpOmExample);

        MetaType metaType = CmTag.getMetaTypeByCode("mt_dp_qz");  //群众
        int crowdId = metaType.getId();
        CadrePartyExample cadrePartyExample = new CadrePartyExample();
        cadrePartyExample.createCriteria().andTypeEqualTo(CadreConstants.CADRE_PARTY_TYPE_DP)
                .andClassIdNotEqualTo(crowdId).andUserIdEqualTo(userId);

        List<CadreParty> cadrePartyList = cadrePartyMapper.selectByExample(cadrePartyExample);

        if (dpMember == null && dpNpmList.size() == 0 && dpNprList.size() == 0 && dpOmList.size() == 0 && cadrePartyList.size() == 0) {
            sysUserService.delRole(userId, RoleConstants.ROLE_CADRE_DP);
        } else {
            sysUserService.addRole(userId, RoleConstants.ROLE_CADRE_DP);
        }
    }
}
