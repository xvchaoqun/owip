package service.pm;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.member.MemberViewMapper;
import persistence.pm.*;
import persistence.pmd.*;
import persistence.pmd.common.IPmdMapper;
import service.CoreBaseMapper;

public class PmBaseMapper extends CoreBaseMapper {

    @Autowired(required = false)
    protected MemberViewMapper memberViewMapper;
    @Autowired
    protected PmMeetingFileMapper pmMeetingFileMapper;
    @Autowired
    protected PmMeetingMapper pmMeetingMapper;
    @Autowired
    protected PmQuarterMapper pmQuarterMapper;
    @Autowired
    protected PmQuarterPartyMapper pmQuarterPartyMapper;
    @Autowired
    protected PmQuarterBranchMapper pmQuarterBranchMapper;
    @Autowired
    protected PmExcludeBranchMapper pmExcludeBranchMapper;
}
