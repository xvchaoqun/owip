package service.pm;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.member.MemberViewMapper;
import persistence.party.BranchViewMapper;
import persistence.party.PartyViewMapper;
import persistence.pm.*;
import service.CoreBaseMapper;

public class PmBaseMapper extends CoreBaseMapper {

    @Autowired(required = false)
    protected Pm3MeetingMapper pm3MeetingMapper;
    @Autowired(required = false)
    protected Pm3GuideMapper pm3GuideMapper;
    @Autowired(required = false)
    protected MemberViewMapper memberViewMapper;
    @Autowired
    protected PmMeetingFileMapper pmMeetingFileMapper;
    @Autowired
    protected PmMeetingMapper pmMeetingMapper;
    @Autowired
    protected PmMeeting2Mapper pmMeeting2Mapper;
    @Autowired
    protected PmQuarterMapper pmQuarterMapper;
    @Autowired
    protected PmQuarterPartyMapper pmQuarterPartyMapper;
    @Autowired
    protected PmQuarterBranchMapper pmQuarterBranchMapper;
    @Autowired
    protected PmExcludeBranchMapper pmExcludeBranchMapper;
    @Autowired
    protected IPmMapper iPmMapper;

    @Autowired(required = false)
    protected BranchViewMapper branchViewMapper;
    @Autowired(required = false)
    protected PartyViewMapper partyViewMapper;
}
