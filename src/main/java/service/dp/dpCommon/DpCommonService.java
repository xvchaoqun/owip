package service.dp.dpCommon;

import controller.global.OpException;
import domain.base.MetaClass;
import domain.base.MetaType;
import domain.cadre.*;
import domain.dp.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.dp.DpNprMapper;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.dp.DpBaseMapper;
import service.sys.SysUserService;
import sys.HttpResponseMethod;
import sys.constants.CadreConstants;
import sys.constants.DpConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import javax.management.relation.Role;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DpCommonService extends DpBaseMapper  implements HttpResponseMethod {

    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    private SysUserService sysUserService;

    public DpParty getDpPartyByPartyId(Integer partyId) {

        DpPartyExample example = new DpPartyExample();
        example.createCriteria().andIdEqualTo(partyId);
        List<DpParty> dpParties = dpPartyMapper.selectByExampleWithRowbounds(example,new RowBounds(0, 1));

        return (dpParties.size() > 0) ? dpParties.get(0) : null;
    }

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

    public void findOrCreateCadre(int userId){

        Cadre cadre = CmTag.getCadre(userId);
        if (cadre == null){
            cadreService.addTempCadre(userId);
        }
    }

    //更新统战成员角色
    public void updateMemberRole(Integer userId){

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

        MetaType metaType= CmTag.getMetaTypeByCode("mt_dp_qz");  //群众
        int crowdId = metaType.getId();
        CadrePartyExample cadrePartyExample = new CadrePartyExample();
        cadrePartyExample.createCriteria().andTypeEqualTo(CadreConstants.CADRE_PARTY_TYPE_DP)
                .andClassIdNotEqualTo(crowdId).andUserIdEqualTo(userId);

        List<CadreParty> cadrePartyList = cadrePartyMapper.selectByExample(cadrePartyExample);

        if (dpMember == null && dpNpmList.size() == 0 && dpNprList.size() == 0 && dpOmList.size() == 0 && cadrePartyList.size() == 0){
            sysUserService.delRole(userId, RoleConstants.ROLE_CADRE_DP);
        }else {
            sysUserService.addRole(userId, RoleConstants.ROLE_CADRE_DP);
        }
    }
}
