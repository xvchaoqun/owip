package service;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.cadre.common.StatCadreMapper;
import persistence.crp.CrpRecordMapper;
import persistence.modify.ModifyBaseApplyMapper;
import persistence.modify.ModifyBaseItemMapper;
import persistence.modify.ModifyCadreAuthMapper;
import persistence.modify.ModifyTableApplyMapper;
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
