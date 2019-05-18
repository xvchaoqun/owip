package service;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.cadre.common.StatCadreMapper;
import persistence.crp.CrpRecordMapper;
import persistence.modify.ModifyBaseApplyMapper;
import persistence.modify.ModifyBaseItemMapper;
import persistence.modify.ModifyCadreAuthMapper;
import persistence.modify.ModifyTableApplyMapper;
import persistence.party.*;
import persistence.partySchool.PartySchoolMapper;
import persistence.verify.VerifyAgeMapper;
import persistence.verify.VerifyWorkTimeMapper;

public class BaseMapper extends CoreBaseMapper{

    @Autowired(required = false)
    protected CrpRecordMapper crpRecordMapper;

    /**
     * 档案认定
     */
    @Autowired(required = false)
    protected VerifyAgeMapper verifyAgeMapper;
    @Autowired(required = false)
    protected VerifyWorkTimeMapper verifyWorkTimeMapper;

    @Autowired(required = false)
    protected StatCadreMapper statCadreMapper;

    @Autowired(required = false)
    protected PartySchoolMapper partySchoolMapper;

    @Autowired(required = false)
    protected OrganizerGroupMapper organizerGroupMapper;
    @Autowired(required = false)
    protected OrganizerGroupUnitMapper organizerGroupUnitMapper;
    @Autowired(required = false)
    protected OrganizerGroupUserMapper organizerGroupUserMapper;
    @Autowired(required = false)
    protected OrganizerMapper organizerMapper;

    @Autowired(required = false)
    protected PartyPublicMapper partyPublicMapper;
    @Autowired(required = false)
    protected PartyPublicUserMapper partyPublicUserMapper;

    /**
     * 干部信息修改申请
     */
    @Autowired(required = false)
    protected ModifyCadreAuthMapper modifyCadreAuthMapper;
    @Autowired(required = false)
    protected ModifyBaseApplyMapper modifyBaseApplyMapper;
    @Autowired(required = false)
    protected ModifyBaseItemMapper modifyBaseItemMapper;
    @Autowired(required = false)
    protected ModifyTableApplyMapper modifyTableApplyMapper;

}
