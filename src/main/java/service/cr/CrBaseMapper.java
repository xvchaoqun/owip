package service.cr;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.cr.*;
import persistence.cr.common.ICrMapper;
import service.CoreBaseMapper;

public class CrBaseMapper extends CoreBaseMapper {

    /**
     * 干部招聘
     */
    @Autowired(required = false)
    protected CrApplicantMapper crApplicantMapper;
    @Autowired(required = false)
    protected CrApplicantCheckMapper crApplicantCheckMapper;
    @Autowired(required = false)
    protected CrInfoMapper crInfoMapper;
    @Autowired(required = false)
    protected CrMeetingMapper crMeetingMapper;
    @Autowired(required = false)
    protected CrPostMapper crPostMapper;
    @Autowired(required = false)
    protected CrRequireMapper crRequireMapper;
    @Autowired(required = false)
    protected CrRequireRuleMapper crRequireRuleMapper;
    @Autowired(required = false)
    protected CrRuleItemMapper crRuleItemMapper;

    @Autowired(required = false)
    protected ICrMapper iCrMapper;
}
